package com.example.stampitserver.contest;

import lombok.Getter;

import java.sql.Date;
import java.util.Set;

@Getter
public class ContestRegisterRequestDTO {
    private String contestName;
    private Set<Field> fields;
    private Set<Applicant> applicant;
    private String host;
    private String sponsor;
    private Date receptionStart;
    private Date receptionEnd;
    private Prize prize;
    private String firstPrize;
    private String url;
    private String content;
}
