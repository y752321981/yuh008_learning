package cn.edu.ujn.yuh008.service;

import cn.edu.ujn.yuh008.pojo.request.LoginCheckRequest;
import cn.edu.ujn.yuh008.pojo.response.LoginCheckResponse;

import javax.servlet.http.HttpServletResponse;

public interface ILoginService {
    LoginCheckResponse loginCheck(LoginCheckRequest request);

    boolean tokenCheck(String token, HttpServletResponse response);
}
