package com.ecl.punch.repositories;

import com.ecl.punch.models.PunchCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunchCardRepository extends JpaRepository<PunchCard, Integer> {

    List<PunchCard> findAllByUserId(Integer userId);


}