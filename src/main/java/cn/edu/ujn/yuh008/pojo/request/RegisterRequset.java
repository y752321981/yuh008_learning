package cn.edu.ujn.yuh008.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequset {
    private String username;
    private String password;
    private String name;
}
