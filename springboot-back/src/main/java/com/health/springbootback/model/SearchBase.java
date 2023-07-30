package com.health.springbootback.model;

public class SearchBase {
    private String userNm;

    public SearchBase(String userNm) {
        this.userNm = userNm;
    }
    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
                "userNm='" + userNm + '\'' +
                '}';
    }
}
