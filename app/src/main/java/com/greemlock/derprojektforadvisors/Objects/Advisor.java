package com.greemlock.derprojektforadvisors.Objects;

import java.util.ArrayList;

public class Advisor {
    private String advisorUID;
    private String advisorEmail;
    private String advisorName;
    private ArrayList<String> adviseeList;

    public Advisor() {
    }

    public Advisor(String advisorUID, String advisorEmail, String advisorName, ArrayList<String> adviseeList) {
        this.advisorUID = advisorUID;
        this.advisorEmail = advisorEmail;
        this.advisorName = advisorName;
        this.adviseeList = adviseeList;
    }

    public String getAdvisorUID() {
        return advisorUID;
    }

    public void setAdvisorUID(String advisorUID) {
        this.advisorUID = advisorUID;
    }

    public String getAdvisorEmail() {
        return advisorEmail;
    }

    public void setAdvisorEmail(String advisorEmail) {
        this.advisorEmail = advisorEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public ArrayList<String> getAdviseeList() {
        return adviseeList;
    }

    public void setAdviseeList(ArrayList<String> adviseeList) {
        this.adviseeList = adviseeList;
    }
}
