package com.example.creskill.Repostory;

import com.example.creskill.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findRatingByRatingId (Integer ratingId);

    List<Rating> findRatingByRatedUserId (Integer ratedUserId);

    @Query(value = "select r.ratedUserId as userid ,avg(r.rating) as average ,count (r.ratingId) as total " +
            "from Rating r group by r.ratedUserId order by avg (r.rating)desc")
    List<Object[]> tobRating ();

    @Query("select r from Rating r where r.rating>=?1")
    List<Rating> getByRating (Integer rating);
    //حساب متوسط التقييمات ليوزر
    @Query("select avg (r.rating) from Rating r where r.ratedUserId=?1")
    Double findAverageForUser (Integer ratedUserId);
//عدد التقييمات
    @Query("select count (r.rating) from Rating r where r.ratedUserId=?1")
    Integer countRatingForUser (Integer ratedUserId);

}
