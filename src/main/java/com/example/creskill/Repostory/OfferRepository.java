package com.example.creskill.Repostory;

import com.example.creskill.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    Offer findOfferByOfferId (Integer offerId);
Offer findOffersByProjectId (Integer projectId);
    List<Offer> findOfferByProjectIdOrderByBudget (Integer projectId);
@Query("select o from Offer o where o.projectId=?1 and o.status=?2")
    List<Offer> findOffersByProjectIdAndAndStatus (Integer projectId ,String status);
}
