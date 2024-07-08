package com.example.stampitserver.contest;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContestFindAllResponseDTO {
    private List<ContestDTO> contests;

    public ContestFindAllResponseDTO(Page<Contest> contests){
        this.contests = contests.stream()
                .map(ContestDTO::new)
                .collect(Collectors.toList());
    }

    @Getter
    static class ContestDTO{
        private Long id;
        private String contestName;
        private String host;
        private int remainDays;

        public ContestDTO(Contest contest){
            this.id = contest.getId();
            this.contestName = contest.getContestName();
            this.host = contest.getHost();
            this.remainDays = contest.getRemainDays();
        }
    }
}
