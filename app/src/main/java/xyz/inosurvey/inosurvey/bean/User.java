package xyz.inosurvey.inosurvey.bean;

public class User {
    int id;
    String userId, password, email, nickName;
    int gender, age, jobId;

    public User(int id,
                String userId,
                String password,
                String email,
                String nickName,
                int gender,
                int age,
                int jobId)
    {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.age = age;
        this.jobId = jobId;
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

    public void setUser_id(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


}
