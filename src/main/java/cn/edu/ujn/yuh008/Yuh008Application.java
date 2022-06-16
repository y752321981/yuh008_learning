package cn.edu.ujn.yuh008;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.edu.ujn.yuh008.dao")
@SpringBootApplication
public class Yuh008Application {

    public static void main(String[] args) {
        SpringApplication.run(Yuh008Application.class, args);
    }

}
