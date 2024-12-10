package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.*;
import com.example.creskill.Repostory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ProjectOwnerRepository projectOwnerRepository;
    private final TalentRepository talentRepository;
    private final OfferRepository offerRepository;
    private final ArtworkRepository artworkRepository;

    public List<Projects> getProjects (){
        return projectsRepository.findAll();
    }

    public void  add (Projects projects){
        if (projectOwnerRepository.findProjectOwnerByOwnerId(projects.getUserid())==null){
            throw new ApiException("owner id not found");
        }
        projects.setStatus("open");
        projects.setCreatedAt(LocalDate.now());
projectsRepository.save(projects) ;   }

    public void update (Integer projectId , Projects projects){
        Projects old = projectsRepository.findProjectsByProjectId(projectId);

        if (old==null){
            throw new ApiException("project id not found");
        }
        if (projectOwnerRepository.findProjectOwnerByOwnerId(projects.getUserid())==null){
            throw new ApiException("user id not found");
        }
   old.setTitle(projects.getTitle());
        old.setDescription(projects.getDescription());
        old.setBudget(projects.getBudget());
        old.setRequiredSkills(projects.getRequiredSkills());

projectsRepository.save(old) ;
    }


    public void delete (Integer projectId ){
        Projects projects = projectsRepository.findProjectsByProjectId(projectId);
        if (projects==null){
            throw new ApiException("projectId not found");
        }
        projectsRepository.delete(projects);
    }
//# 15
public List<Projects>  projectByOwnerId (Integer userid){
        List<Projects> projectByOwnerId =projectsRepository.projectByOwnerId(userid);
        if (projectByOwnerId==null){
            throw new ApiException("you dont have any Projects");
        }
        return projectByOwnerId;
}
//#16
    public void deliverByTalent (Integer projectId , Integer talentId){
        Projects projects= projectsRepository.findProjectsByProjectIdAndAndTalentId(projectId,talentId);
            if (projects==null){
                throw new ApiException("project not found or you not allowed to deliver this project");
            }
            if (!projects.getStatus().equalsIgnoreCase("inprogress")){
                throw  new ApiException("project is not in a status allow delivery");
            }
            projects.setStatus("delivered");
            projectsRepository.save(projects);
        }

//#17
    public void completeProject (Integer projectId , Integer projectOwnerId) {
      Projects projects =projectsRepository.findProjectsByProjectIdAndUserid(projectId,projectOwnerId);
      if(projects==null){
          throw new ApiException("project not found");
      }
     if(!projects.getStatus().equalsIgnoreCase("inprogress") || projects.getStatus()==null) {
         throw new ApiException("project is not in progress");
     }
     Offer offer= offerRepository.findOffersByProjectId(projectId);
     if(offer==null){
         throw new ApiException("no offer found for this project");
     }
     offer.setStatus("completed");
     offerRepository.save(offer);
     projects.setStatus("completed");
     projectsRepository.save(projects);
     //add point to talent
     Talent talent = talentRepository.findTalentByUserid(projects.getTalentId());
        if(talent.getPoints()==null){
            talent.setPoints(100);
        }else talent.setPoints(talent.getPoints()+100);
     talentRepository.save(talent);
    }

//#18
    public List<Projects> findProjectMatchSkills (Integer talentId){
        Talent talent = talentRepository.findTalentByUserid(talentId);
        if (talent==null){
            throw new ApiException("talent id not found");
        }
        return projectsRepository.findProjectMatchSkills(talent.getSkillsOffered());
    }

//#19-1show project with specific budget or less
public List<Projects> getProjectsLessBudget (Double budget){
        List<Projects> projectsLessBudget = projectsRepository.getProjectsLessBudget(budget);
        if (projectsLessBudget==null){
            throw new ApiException("No project have budget less than "+ budget);
        }
        return projectsLessBudget;
}

//#19-2show project with specific budget or more
    public List<Projects> getProjectsMoreBudget (Double budget){
        List<Projects> projectsMoreBudget = projectsRepository.getProjectsMoreBudget(budget);
        if (projectsMoreBudget==null){
            throw new ApiException("No project have budget less than "+ budget);
        }
        return projectsMoreBudget;
    }



    }
