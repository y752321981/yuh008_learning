package cn.edu.ujn.yuh008.pojo.enums;

public enum ResultEnum {
    SUCCESS(200, null),

    ERROR(500, null);

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}