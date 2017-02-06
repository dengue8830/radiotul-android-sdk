package com.amla.radiotulsdk.access;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class User {
    private Long id;
    /** The id for the RadioTul's social login register */
    private Long radiotulSocialLoginId;
    /** The id of the social network user account */
    private String socialId;
    /** Token of the user's social network account */
    private String socialToken;
    /** The id of the profile on RadioTul account */
    private Long profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String sex;
    /** Unique identifier for the person. This key is provided by the country's people register of the user */
    private String dni;
    /** Birthday on format: yyyy-MM-dd */
    private String parsedBirthDay;
    /** RadioTul's url to retrive the user's profile picture */
    private String profilePictureUrl;
    private String phoneCompany;

    public User(){}

    public User(Long id, Long radiotulSocialLoginId, String socialId, String token, Long profileId, String firstName, String lastName, String email, String password, String phone, String sex, String dni, String parsedBirthDay, String profilePictureUrl, String phoneCompany) {
        this.id = id;
        this.radiotulSocialLoginId = radiotulSocialLoginId;
        this.socialId = socialId;
        this.socialToken = token;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRadiotulSocialLoginId() {
        return radiotulSocialLoginId;
    }

    public void setRadiotulSocialLoginId(Long radiotulSocialLoginId) {
        this.radiotulSocialLoginId = radiotulSocialLoginId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialToken() {
        return socialToken;
    }

    public void setSocialToken(String socialToken) {
        this.socialToken = socialToken;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
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
