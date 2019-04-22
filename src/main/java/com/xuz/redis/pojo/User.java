package com.xuz.redis.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 7854837466417999254L;
    private Integer id;
    public String vcUserName;
    public String vcUserPwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVcUserName() {
        return vcUserName;
    }

    public void setVcUserName(String vcUserName) {
        this.vcUserName = vcUserName;
    }

    public String getVcUserPwd() {
        return vcUserPwd;
    }

    public void setVcUserPwd(String vcUserPwd) {
        this.vcUserPwd = vcUserPwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", vcUserName='" + vcUserName + '\'' +
                ", vcUserPwd='" + vcUserPwd + '\'' +
                '}';
    }
}
