package com.example.creskill.Repostory;

import com.example.creskill.Model.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TalentRepository extends JpaRepository<Talent,Integer> {

    Talent findTalentByUserid (Integer userid);


}
