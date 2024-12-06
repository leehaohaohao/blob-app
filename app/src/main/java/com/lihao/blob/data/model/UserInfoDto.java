package com.lihao.blob.data.model;

/**
 * 用户信息
 *
 * @author lihao
 * &#064;date  2024/12/1--18:19
 * @since 1.0
 */
public class UserInfoDto {
    private String userId;
    private String name;
    private int gender;
    private String photo;
    private int followers;
    private int concern;
    private int post;
    private String selfTag;
    private int status;
    private int love;
    private String telephone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getSelfTag() {
        return selfTag;
    }

    public void setSelfTag(String selfTag) {
        this.selfTag = selfTag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
