package com.example.creskill.Repostory;

import com.example.creskill.Model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {

    Projects findProjectsByProjectId (Integer projectId);

    Projects findProjectsByProjectIdAndUserid (Integer projectId, Integer userId);
    @Query("SELECT p FROM Projects p WHERE p.status = 'open' AND EXISTS " +
            "(SELECT skill FROM p.requiredSkills skill WHERE skill IN :skills)")
    List<Projects> findProjectMatchSkills (List<String> skills);

    @Query("select p from Projects p where p.budget<=?1")
    List<Projects> getProjectsLessBudget (Double budget);

    @Query("select p from Projects p where p.budget>=?1")
    List<Projects> getProjectsMoreBudget (Double budget);
//حساب عدد المشاريع المكتملة ليوزر مبدع
    @Query("select count (p.projectId) from Projects p where p.talentId=?1 and p.status='completed'")
    Integer countCompletedForTalentUser (Integer talentId);

    @Query ("select p.requiredSkills from Projects p where p.projectId=?1")
    List<String> findRequiredSkills (Integer projectId);
    @Query("select p from Projects p where p.projectId=?1 and p.talentId=?2")
    Projects findProjectsByProjectIdAndAndTalentId (Integer projectId , Integer talentId);

    @Query("select p from Projects p where p.userid=?1")
    List<Projects> projectByOwnerId (Integer userid);
}
