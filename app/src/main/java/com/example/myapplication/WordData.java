package com.example.myapplication;

public class WordData {
    private String member_idProgress;
    private String member_Word;
    private String member_Inputplayer;

    public String getMember_id() {
        return member_idProgress;
    }

    public String getMember_name() {
        return member_Word;
    }

    public String getMember_country() {
        return member_Inputplayer;
    }

    public void setMember_idProgress(String member_idProgress) {
        this.member_idProgress = member_idProgress;
    }

    public void setMember_Word(String member_Word) {
        this.member_Word = member_Word;
    }

    public void setMember_Inputplayer(String member_Inputplayer) {
        this.member_Inputplayer = member_Inputplayer;
    }
}
