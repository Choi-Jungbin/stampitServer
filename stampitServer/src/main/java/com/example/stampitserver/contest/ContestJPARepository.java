package com.example.stampitserver.contest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestJPARepository extends JpaRepository<Contest, Long> {
    Page<Contest> findAll(Pageable pageable);

    Contest findByUrl(String url);
}
