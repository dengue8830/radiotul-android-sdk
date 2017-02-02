package com.amla.radiotulsdk.access;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class User {
    private int userId;
    //TODO: Esto que es?
    private int socialLoginId;
    //TODO: Esto debe ser string? creo que si porque creo que es el uuid que te da facebook
    private String socialId;
    private String token;
    private int profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String sex;
    private String dni;
    private String parsedBirthDay;
    private String profilePictureUrl;
    private String phoneCompany;

    public User(){}

    public User(int userId, int socialLoginId, String socialId, String token, int profileId, String firstName, String lastName, String email, String password, String phone, String sex, String dni, String parsedBirthDay, String profilePictureUrl, String phoneCompany) {
        this.userId = userId;
        this.socialLoginId = socialLoginId;
        this.socialId = socialId;
        this.token = token;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.sex = sex;
        this.dni = dni;
        this.parsedBirthDay = parsedBirthDay;
        this.profilePictureUrl = profilePictureUrl;
        this.phoneCompany = phoneCompany;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSocialLoginId() {
        return socialLoginId;
    }

    public void setSocialLoginId(int socialLoginId) {
        this.socialLoginId = socialLoginId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getParsedBirthDay() {
        return parsedBirthDay;
    }

    public void setParsedBirthDay(String parsedBirthDay) {
        this.parsedBirthDay = parsedBirthDay;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }
}
