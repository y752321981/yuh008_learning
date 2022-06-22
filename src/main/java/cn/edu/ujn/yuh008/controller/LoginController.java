package cn.edu.ujn.yuh008.controller;

import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;
import cn.edu.ujn.yuh008.pojo.request.RegisterRequset;
import cn.edu.ujn.yuh008.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/loginCheck")
    private Result<LoginCheckResponse> loginCheck(@RequestBody LoginCheckRequest request) {
        if (request == null) {
            return Result.success(new LoginCheckResponse(99, "请求体出错", null));
        }
         return Result.success(loginService.loginCheck(request));
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/logout")
    private Result<Map<String, Object>> logout(HttpServletRequest httpServletRequest) {
        return Result.success(loginService.logout(httpServletRequest));
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/register")
    private Result<Map<String, Object>> register(@RequestBody RegisterRequset request) {
        if (request == null) {
            return null;
        }
        return Result.success(loginService.register(request));
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/hello")
    private Result<Map<String, String>> hello() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "hello");
        return Result.success(map);
    }
}
