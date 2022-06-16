package cn.edu.ujn.yuh008.service.impl;

import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.dao.LoginDao;
import cn.edu.ujn.yuh008.pojo.entity.Token;
import cn.edu.ujn.yuh008.pojo.entity.User;
import cn.edu.ujn.yuh008.pojo.enums.ResultEnum;
import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;
import cn.edu.ujn.yuh008.service.ILoginService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
        if (token1 == null) {
            try(OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(JSONObject.toJSONString(Result.success("无效的token")).getBytes(StandardCharsets.UTF_8));
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
}
