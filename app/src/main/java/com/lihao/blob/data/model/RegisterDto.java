package com.lihao.blob.data.model;

/**
 * 注册实体类
 *
 * @author lihao
 * &#064;date  2024/11/27--13:16
 * @since 1.0
 */
public class RegisterDto {
    private String email;
    private String password;
    private String code;
    public RegisterDto(){

    }
    public RegisterDto(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
