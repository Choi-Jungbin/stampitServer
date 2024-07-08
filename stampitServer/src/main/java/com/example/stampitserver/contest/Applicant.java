package com.example.stampitserver.contest;

import com.example.stampitserver.core.error.exception.NotFondEnumException;
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

    // 한글 문자열을 Applicant enum으로 변환하는 메서드
    public static Applicant fromString(String applicant) {
        for (Applicant a : Applicant.values()) {
            if (a.getApplicant().equals(applicant)) {
                return a;
            }
        }
        throw new NotFondEnumException("해당 enum 내용은 applicant에 없습니다: " + applicant);
    }
}
