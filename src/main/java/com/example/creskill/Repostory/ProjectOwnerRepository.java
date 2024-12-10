package com.example.creskill.Repostory;

import com.example.creskill.Model.ProjectOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectOwnerRepository extends JpaRepository<ProjectOwner,Integer> {
    ProjectOwner findProjectOwnerByOwnerId (Integer ownerId);


}
