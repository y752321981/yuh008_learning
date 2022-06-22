package cn.edu.ujn.yuh008.service.impl;

import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.dao.LoginDao;
import cn.edu.ujn.yuh008.pojo.entity.Token;
import cn.edu.ujn.yuh008.pojo.entity.User;
import cn.edu.ujn.yuh008.pojo.enums.ResultEnum;
import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.request.RegisterRequset;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;
import cn.edu.ujn.yuh008.service.ILoginService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.*;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public LoginCheckResponse loginCheck(LoginCheckRequest request) {
        LoginCheckResponse response = new LoginCheckResponse();
        User user = this.loginDao.queryUserByUsername(request.getUsername());
        if (user == null) {
            response.setLoginStatus(ResultEnum.ERROR.getCode());
            response.setMsg("用户名不存在");
        } else {
            if (user.getStatus() == 0) {
                if (request.getPassword().equals(user.getPassword())) {
                    response.setLoginStatus(ResultEnum.SUCCESS.getCode());
                    response.setMsg("登录成功");
                    Token token = new Token();
                    token.setId(UUID.randomUUID().toString());
                    token.setToken(UUID.randomUUID().toString());
                    token.setUsername(user.getUsername());
                    token.setIs_exceed(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, 10);
                    token.setExceed_time(calendar.getTime());
                    this.loginDao.insertToken(token);
                    response.setToken(token.getToken());
                } else {
                    response.setLoginStatus(ResultEnum.ERROR.getCode());
                    response.setMsg("密码错误");
                }
            } else if(user.getStatus() == 1) {
                response.setLoginStatus(ResultEnum.ERROR.getCode());
                response.setMsg("用户已被冻结");
            }
        }
        return response;
    }

    @Override
    public boolean tokenCheck(String token, HttpServletResponse response) {
        Token token1 = this.loginDao.queryToken(token);
        response.setHeader("Content-Type", "application/json");
        if (token1 == null) {
            try(OutputStream outputStream = response.getOutputStream()) {
                Map<String, String> result = new HashMap<>();
                result.put("msg", "无效的token，请登录");
                result.put("loginStatus", "99");
                outputStream.write(JSONObject.toJSONString(Result.success(result)).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        if (token1.getExceed_time().before(new Date())) {
            try(OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(JSONObject.toJSONString(Result.success("token已过期")).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.loginDao.exceedToken(token);
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Map<String, Object> result = new HashMap<>(10);
        result.put("code", ResultEnum.SUCCESS.getCode());
        result.put("msg", "已退出登录");

        if (token == null) {
            return result;
        }
        Token token1 = this.loginDao.queryToken(token);
        if (token1 == null) {
            return result;
        }
        this.loginDao.exceedTokenByUsername(token1.getUsername());
        return result;
    }


    @Override
    public Map<String, Object> register(RegisterRequset request) {
        Map<String, Object> result = new HashMap<>(10);
        User user = this.loginDao.queryUserByUsername(request.getUsername());
        if (user != null) {
            result.put("code", ResultEnum.ERROR.getCode());
            result.put("msg", "用户名已存在");
            return result;
        } else {
            user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setName(request.getName());
            user.setStatus(0);
            this.loginDao.insertUser(user);
            result.put("code", ResultEnum.SUCCESS.getCode());
            result.put("msg", "注册成功");

            return result;
        }
    }
}
