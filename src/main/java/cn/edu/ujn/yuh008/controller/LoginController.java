package cn.edu.ujn.yuh008.controller;

import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;
import cn.edu.ujn.yuh008.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/hello")
    private Result<String> hello() {
        return Result.success("hello");
    }
}
