package com.example.meong_nyang;

public class LostAnimal {

    private String title;
    private String content;
    private String lostlocation;
    private String lostdate;
    private String losttime;
    private String image1;
    private String image2;
    private String image3;
    private String name;
    private String species;
    private String callnum;

    public LostAnimal() {}

    public LostAnimal(String title, String content, String lostlocation, String lostdate, String losttime, String image1, String image2, String image3, String name, String species, String callnum) {
        this.title = title;
        this.content = content;
        this.lostlocation = lostlocation;
        this.lostdate = lostdate;
        this.losttime = losttime;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.name = name;
        this.species = species;
        this.callnum = callnum;
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

    public String getLostlocation() {
        return lostlocation;
    }

    public void setLostlocation(String lostlocation) {
        this.lostlocation = lostlocation;
    }

    public String getLostdate() {
        return lostdate;
    }

    public void setLostdate(String lostdate) {
        this.lostdate = lostdate;
    }

    public String getLosttime() {
        return losttime;
    }

    public void setLosttime(String losttime) {
        this.losttime = losttime;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCallnum() {
        return callnum;
    }

    public void setCallnum(String callnum) {
        this.callnum = callnum;
    }
}