package cn.edu.ujn.yuh008.common;

import cn.edu.ujn.yuh008.service.ILoginService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private ILoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String token = request.getHeader("token");

        if (token == null || "".equals(token)) {
            try(OutputStream outputStream = response.getOutputStream()) {
                Map<String, String> result = new HashMap<>();
                result.put("msg", "无token，请登录");
                result.put("loginStatus", "99");
                outputStream.write(JSONObject.toJSONString(Result.success(result)).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return this.loginService.tokenCheck(token, response);
    }
}
