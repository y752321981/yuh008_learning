package cn.edu.ujn.yuh008.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCheckResponse {
    private Integer loginStatus;
    private String msg;
    private String token;
}
