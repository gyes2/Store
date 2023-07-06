package org.store;
class MemberInfo {

    private String name;
    private String password;
    private String phoneNum;
    private String address;
    private int userCase;

    public MemberInfo(String name, String password, String phoneNum, String address, int userCase) {
        this.name = name;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.userCase= userCase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserCase() {
        return userCase;
    }

    public String toMemberString(int userCase) {
        String userCasename = "";
        if (userCase == 1) {
            userCasename = "관리자";
        } else {
            userCasename = "회원";
        }
        return "ID: " + name + ", PW: " + password + ", 전화번호: " + phoneNum + ", 주소: " + address + ", 유형: " + userCasename;
    }
}