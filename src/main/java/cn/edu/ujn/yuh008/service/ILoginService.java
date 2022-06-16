package cn.edu.ujn.yuh008.service;

import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.request.RegisterRequset;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ILoginService {
    LoginCheckResponse loginCheck(LoginCheckRequest request);

    boolean tokenCheck(String token, HttpServletResponse response);

    Map<String, Object> logout(HttpServletRequest httpServletRequest);

    Map<String, Object> register(RegisterRequset request);
}
