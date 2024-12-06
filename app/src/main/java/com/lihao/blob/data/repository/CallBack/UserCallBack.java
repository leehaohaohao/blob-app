package com.lihao.blob.data.repository.CallBack;

import com.lihao.blob.data.model.UserInfoDto;

/**
 * 用户回调
 *
 * @author lihao
 * &#064;date  2024/12/6--18:26
 * @since 1.0
 */
public interface UserCallBack {
    void getUserInfo(UserInfoDto userInfoDto);
}
