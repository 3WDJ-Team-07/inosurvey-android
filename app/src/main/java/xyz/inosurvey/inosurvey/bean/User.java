package xyz.inosurvey.inosurvey.bean;

public class User {
    int id;
    String userId, email, nickName;
    int gender, age, jobId, isDonator;

    public User(int id,
                String userId,
                String email,
                String nickName,
                int gender,
                int age,
                int jobId,
                int isDonator    )
    {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.age = age;
        this.jobId = jobId;
        this.isDonator = isDonator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getjobId() {
        return jobId;
    }

    public void setjobId(int jobId) {
        this.jobId = jobId;
    }

    public int getIsDonator() {
        return isDonator;
    }

    public void setIsDonator(int jobId) {
        this.isDonator = isDonator;
    }


}
