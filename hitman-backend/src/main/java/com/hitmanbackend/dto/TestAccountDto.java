package com.hitmanbackend.dto;

public class TestAccountDto {

    private String firstName;

    private String lastName;

    private String aboutInfo;

    private String username;

    private String password;

    private String repeatedPassword;

    public TestAccountDto() {
    }

    public TestAccountDto(String firstName, String lastName, String aboutInfo, String username, String password, String repeatedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutInfo = aboutInfo;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutInfo() {
        return aboutInfo;
    }

    public void setAboutInfo(String aboutInfo) {
        this.aboutInfo = aboutInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
