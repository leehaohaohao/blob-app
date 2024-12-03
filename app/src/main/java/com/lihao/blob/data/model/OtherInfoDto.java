package com.lihao.blob.data.model;

import okhttp3.Interceptor;

/**
 * 其他用户信息
 *
 * @author lihao
 * &#064;date  2024/12/3--12:16
 * @since 1.0
 */
public class OtherInfoDto {
    private UserInfoDto userInfoDto;
    private Integer status;

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        this.userInfoDto = userInfoDto;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
