package com.example.stampitserver.contest;

import lombok.Getter;

@Getter
public enum Prize {
    ABOVE_5("5천만원이상"),
    BTW_5_TO_3("5천만원~3천만원"),
    BTW_3_TO_1("3천만원~1천만원"),
    UNDER_1("1천만원이하"),
    EMPLOYMENT("취업특전"),
    ADD_POINT("입사시가산점"),
    INTERN("인턴채용"),
    EMPLOYEE("정직원채용"),
    ETC("기타");

    private final String prize;

    Prize(String s){
        this.prize = s;
    }
}
