package com.example.stampitserver.contest;

import lombok.Getter;

@Getter
public enum Applicant {
    NO_RESTRICTION("제한없음"),
    GENERAL("일반인"),
    UNIVERSITY("대학생"),
    TEENAGER("청소년"),
    CHILD("어린이"),
    ETC("기타");

    private final String applicant;

    Applicant(String s){
        this.applicant = s;
    }
}
