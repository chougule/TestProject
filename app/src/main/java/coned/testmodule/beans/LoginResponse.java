package coned.testmodule.beans;

public class LoginResponse {

    private String token="";
    private String user_detials="";
    String userid="";
    String full_name="";
    String mobile="";
    String user_email="";
    String user_type="";


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_detials() {
        return user_detials;
    }

    public void setUser_detials(String user_detials) {
        this.user_detials = user_detials;
    }
}