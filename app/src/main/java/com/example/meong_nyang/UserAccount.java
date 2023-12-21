package com.example.meong_nyang;
//사용자 계정 정보 모델 클래스
public class UserAccount {
    //mEtName, mEtPass, mEtPassck , mEtEmail,mEtRegion,mEtPhoneNo;  //회원가입 입력필드
    private String idToken; //고유값
    private String name;
    private String pass;
    private String passck;
    private String email;
    private String region;
    private String phoneNo;
    public UserAccount() { } // 생성자

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassck() {
        return passck;
    }

    public void setPassck(String passck) {
        this.passck = passck;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
