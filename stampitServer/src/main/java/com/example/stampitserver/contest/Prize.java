package com.example.stampitserver.contest;

import com.example.stampitserver.core.error.exception.NotFondEnumException;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    ETC("다양한 혜택");

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
        return parsePrize(prize);
    }

    private static Prize parsePrize(String p){
        if(p == null || p.isEmpty()){
            return Prize.ETC;
        }
        Prize prize = Prize.fromString(p);
        if(prize != null){
            return prize;
        }
        if(p.contains("혜택")){
            return Prize.ETC;
        }

        if(p.contains("만원")){
            if(p.contains("억")){
                return Prize.ABOVE_5;
            }
            Pattern thousand = Pattern.compile("\\b\\d{4,}|천");
            Matcher thousandMatcher = thousand.matcher(p);
            if(thousandMatcher.find()){
                int start = thousandMatcher.start();
                int length = thousandMatcher.end() - start;
                return switch (length) {
                    case 1 -> switch (p.substring(start, start + 1)) {
                        case "일", "이" -> Prize.BTW_3_TO_1;
                        case "삼", "사" -> Prize.BTW_5_TO_3;
                        default -> Prize.ABOVE_5;
                    };
                    case 4 -> switch (p.substring(start, start + 1)) {
                        case "1", "2" -> Prize.BTW_3_TO_1;
                        case "3", "4" -> Prize.BTW_5_TO_3;
                        default -> Prize.ABOVE_5;
                    };
                    default -> Prize.ABOVE_5;
                };
            }
            return Prize.UNDER_1;
        }
        return Prize.ETC;
    }
}
