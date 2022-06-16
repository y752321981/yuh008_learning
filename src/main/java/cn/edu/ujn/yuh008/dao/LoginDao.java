package cn.edu.ujn.yuh008.dao;

import cn.edu.ujn.yuh008.pojo.entity.Token;
import cn.edu.ujn.yuh008.pojo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {

    User queryUserByUsername(@Param("username") String username);

    void insertToken(Token token);

    Token queryToken(@Param("token") String token);

    void exceedToken(@Param("token") String token);
}
