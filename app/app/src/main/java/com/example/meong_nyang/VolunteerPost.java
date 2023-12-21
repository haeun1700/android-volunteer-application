package com.example.meong_nyang;

public class VolunteerPost {
    //봉사 이모티콘, 센터이름, 제목, 봉사날짜
    private String profile;
    private String center;
    private String title;
    private String day;
    private String content;

    public VolunteerPost(){}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "VolunteerPost{" +
          "profile='" + profile + '\'' +
          ", center='" + center + '\'' +
          ", title='" + title + '\'' +
          ", day='" + day + '\'' +
          ", content='" + content + '\'' +
          '}';
    }
}
