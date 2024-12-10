package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.ProjectOwner;
import com.example.creskill.Repostory.ProjectOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectOwnerService {

    private final ProjectOwnerRepository projectOwnerRepository;


    public List<ProjectOwner> getOwners() {
        return projectOwnerRepository.findAll();
    }

    public void add2 (List<ProjectOwner> owners){
        projectOwnerRepository.saveAll(owners);
    }
    public void add(ProjectOwner projectOwner) {
        projectOwnerRepository.save(projectOwner);
    }

    public void update(Integer ownerId, ProjectOwner projectOwner) {
        ProjectOwner old = projectOwnerRepository.findProjectOwnerByOwnerId(ownerId);

        if (old == null) {
            throw new ApiException("owner id not found");
        }
        old.setName(projectOwner.getName());
        old.setEmail(projectOwner.getEmail());
        old.setPassword(projectOwner.getPassword());

        projectOwnerRepository.save(old);
    }


    public void delete(Integer ownerId) {
        ProjectOwner projectOwner = projectOwnerRepository.findProjectOwnerByOwnerId(ownerId);
        if (projectOwner == null) {
            throw new ApiException("owner id not found");
        }
        projectOwnerRepository.delete(projectOwner);
    }



}
