package com.example.creskill.Repostory;

import com.example.creskill.Model.Rating;
import com.example.creskill.Model.SkillExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillExchangeRepository extends JpaRepository<SkillExchange,Integer> {
    SkillExchange findSkillExchangeBySkillExchangeId (Integer skillExchangeId);

    @Query("select s from SkillExchange s where s.status=?1")
    List<SkillExchange> findAvailableExchange (String status);
    //حساب عدد الجلسات التي شارك فيها المبدع
    @Query("select count (c.skillExchangeId)from SkillExchange c where c.userId=?1 or c.partnerId=?1")
    Integer countCompletedForUser (Integer id);

    @Query("select s from SkillExchange s where s.status='pending'")
    List<SkillExchange> findPendingSkillExchange ();
}
