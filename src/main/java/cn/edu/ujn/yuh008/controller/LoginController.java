package cn.edu.ujn.yuh008.controller;

import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;
import cn.edu.ujn.yuh008.pojo.request.RegisterRequset;
import cn.edu.ujn.yuh008.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/loginCheck")
    private Result<LoginCheckResponse> loginCheck(@RequestBody LoginCheckRequest request) {
        if (request == null) {
            return Result.success(new LoginCheckResponse(99, "请求体出错", null));
        }
         return Result.success(loginService.loginCheck(request));
    }

    @PostMapping("/logout")
    private Result<Map<String, Object>> logout(HttpServletRequest httpServletRequest) {
        return Result.success(loginService.logout(httpServletRequest));
    }

    @PostMapping("/register")
    private Result<Map<String, Object>> register(@RequestBody RegisterRequset request) {
        if (request == null) {
            return null;
        }
        return Result.success(loginService.register(request));
    }

    @PostMapping("/hello")
    private Result<String> hello() {
        return Result.success("hello");
    }
}
