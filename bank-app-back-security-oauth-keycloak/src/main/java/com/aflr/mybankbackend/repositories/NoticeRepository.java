package com.aflr.mybankbackend.repositories;

import com.aflr.mybankbackend.entities.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Integer> {
    @Query("from Notice n where CURRENT_DATE BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();
}
