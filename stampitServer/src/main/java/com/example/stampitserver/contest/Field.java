package com.example.stampitserver.contest;

import lombok.Getter;

@Getter
public enum Field {
    PLANNING("기획/아이디어"),
    MARKETING("광고/마케팅"),
    PAPER("논문/리포트"),
    VIDEO("영상/UCC/사진"),
    DESIGN("디자인/캐릭터/웹툰"),
    IT("웹/모바일/IT"),
    GAME("게임/소프트웨어"),
    SCIENCE("과학/공학"),
    LITERATURE("문학/글/시나리오"),
    ARCHITECTURE("건축/건설/인테리어"),
    NAMING("네이밍/슬로건"),
    ART("예체능/미술/음악"),
    SUPPORTERS("대외활동/서포터즈"),
    VOLUNTEER("봉사활동"),
    STARTUP("취업/창업"),
    OVERSEAS("해외"),
    ETC("기타");

    private final String field;

    Field(String s) {
        this.field = s;
    }

    // 한글 문자열을 Field enum으로 변환하는 메서드
    public static Field fromString(String field) {
        for (Field f : Field.values()) {
            if (f.getField().equals(field)) {
                return f;
            }
        }
        throw new IllegalArgumentException("해당 enum 내용은 field에 없습니다: " + field);
    }
}
