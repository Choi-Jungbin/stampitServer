package com.example.stampitserver.contest;

import com.example.stampitserver.core.error.exception.OutOfDate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Table(name = "contests")
@Getter
@ToString
@NoArgsConstructor
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contest_name", nullable = false)
    private String contestName;

    @Column(name = "field")
    @ElementCollection(targetClass = Field.class)
    @CollectionTable(name = "contest_fields", joinColumns = @JoinColumn(name = "contest_id"))
    @Enumerated(EnumType.STRING)
    private Set<Field> fields;

    @Column
    private String applicant;

    @Column
    private String host;

    @Column
    private String sponsor;

    @Column(name = "reception_start", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date receptionStart;

    @Column(name = "reception_end", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date receptionEnd;

    @Column(nullable = false)
    private int remainDays;

    @Column
    @Enumerated(EnumType.STRING)
    private Prize prize;

    @Column
    @Enumerated(EnumType.STRING)
    private String firstPrize;

    @Column(length = 1024, nullable = false)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public Contest(String contestName, Set<Field> fields, String applicant, String host, String sponsor,
                   Date receptionStart, Date receptionEnd, Prize prize, String firstPrize, String url, String content){
        if(receptionEnd.toLocalDate().isBefore(LocalDate.now())){
            throw new OutOfDate("날짜가 지났습니다.");
        }
        this.contestName = contestName;
        this.fields = fields;
        this.applicant = applicant;
        this.host = host;
        this.sponsor = sponsor;
        this.receptionStart = receptionStart;
        this.receptionEnd = receptionEnd;
        this.remainDays = calcRemainDays(receptionEnd);
        this.prize = prize;
        this.firstPrize = firstPrize;
        this.url = url;
        this.content = content;
    }

    private int calcRemainDays(Date receptionEnd){
        LocalDate endDate = receptionEnd.toLocalDate();
        LocalDate today = LocalDate.now();
        return Period.between(today, endDate).getDays();
    }
}
