package cn.edu.ujn.yuh008.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String id;
    private String token;
    private String username;
    private Date exceed_time;
    private Integer is_exceed;
}
