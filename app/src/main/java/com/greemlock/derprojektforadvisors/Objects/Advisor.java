package com.greemlock.derprojektforadvisors.Objects;

import java.util.ArrayList;

public class Advisor {

    private String advisorUID;
    private String advisorName;
    private String advisorSurname;
    private String advisorEmail;
    private ArrayList<String> adviseeList;

    public Advisor() {
    }

    public Advisor(String advisorUID, String advisorName, String advisorSurname, String advisorEmail, ArrayList<String> adviseeList) {
        this.advisorUID = advisorUID;
        this.advisorName = advisorName;
        this.advisorSurname = advisorSurname;
        this.advisorEmail = advisorEmail;
        this.adviseeList = adviseeList;
    }

    public String getAdvisorUID() {
        return advisorUID;
    }
    public void setAdvisorUID(String advisorUID) {
        this.advisorUID = advisorUID;
    }

    public String getAdvisorName() {
        return advisorName;
    }
    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getAdvisorSurname() {
        return advisorSurname;
    }
    public void setAdvisorSurname(String advisorSurname) {
        this.advisorSurname = advisorSurname;
    }

    public String getAdvisorEmail() {
        return advisorEmail;
    }
    public void setAdvisorEmail(String advisorEmail) {
        this.advisorEmail = advisorEmail;
    }

    public ArrayList<String> getAdviseeList() {
        return adviseeList;
    }
    public void setAdviseeList(ArrayList<String> adviseeList) {
        this.adviseeList = adviseeList;
    }


}
