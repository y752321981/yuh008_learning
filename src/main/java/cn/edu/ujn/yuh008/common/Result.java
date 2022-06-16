package cn.edu.ujn.yuh008.common;

import cn.edu.ujn.yuh008.pojo.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T> {
    private Integer code;
    private String msg;
    private T data;
    public static Result success(Object obj) {
        return getResult(ResultEnum.SUCCESS, obj);
    }

    public static Result success() {
        return getResult(ResultEnum.SUCCESS);
    }

    public static Result error(Object obj) {
        return getResult(ResultEnum.ERROR, obj);
    }

    public static Result error() {
        return getResult(ResultEnum.ERROR);
    }


    public static Result getResult(ResultEnum re) {
        return getResult(re, null);
    }

    public static Result getResult(ResultEnum re, Object obj) {
        return getResult(re.getCode(), re.getMessage(), obj);
    }

    public static Result getResult(Integer code, String msg) {
        return getResult(code, msg, null);
    }


    public static Result getResult(Integer code, String msg, Object obj) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }

}
