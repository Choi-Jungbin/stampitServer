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

    // 한글 문자열을 Prize enum으로 변환하는 메서드
    public static Prize fromString(String prize) {
        for (Prize p : Prize.values()) {
            if (p.getPrize().equals(prize)) {
                return p;
            }
        }
        throw new IllegalArgumentException("해당 enum 내용은 prize에 없습니다: " + prize);
    }
}
