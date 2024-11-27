package com.lihao.blob.data.model;

/**
 * 登陆dto
 *
 * @author lihao
 * &#064;date  2024/11/27--9:36
 * @since 1.0
 */
public class LoginDto {
    private String email;
    private String password;
    public LoginDto(){

    }
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
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
}
