package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.Offer;
import com.example.creskill.Model.Projects;
import com.example.creskill.Model.Talent;
import com.example.creskill.Repostory.OfferRepository;
import com.example.creskill.Repostory.ProjectsRepository;
import com.example.creskill.Repostory.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final TalentRepository talentRepository;
    private final ProjectsRepository projectsRepository;


    public List<Offer> getOffer (){
        return offerRepository.findAll();
    }

//#20
    public void  add (Offer offer){
        Talent talent=talentRepository.findTalentByUserid(offer.getUserid());

        if (talent==null){
            throw new ApiException("user id not found");
        }
        if (projectsRepository.findProjectsByProjectId(offer.getProjectId())==null){
            throw new ApiException("project id not found");
        }
        List<String> requiredSkill = projectsRepository.findRequiredSkills(offer.getProjectId());
        List<String> offeredSkill = talent.getSkillsOffered();
        Long muchSkill =requiredSkill.stream().filter(offeredSkill::contains).count();
        if (muchSkill==0){
            throw new ApiException("the required skill not match your skill");
        }
        offer.setStatus("pending");
        offer.setCreatedAt(LocalDate.now());
        offerRepository.save(offer) ;   }


    public void update (Integer offerId , Offer offer){
        Offer old = offerRepository.findOfferByOfferId(offerId);

        if (old==null){
            throw new ApiException("offer id not found");}
            if (talentRepository.findTalentByUserid(offer.getUserid())==null){
                throw new ApiException("user id not found");}
            if (projectsRepository.findProjectsByProjectId(offer.getProjectId())==null){
                throw new ApiException("project id not found");}

            old.setMessage(offer.getMessage());
            old.setBudget(offer.getBudget());
        offerRepository.save(old) ;
    }



    public void delete (Integer offerId ){
        Offer offer = offerRepository.findOfferByOfferId(offerId);
        if (offer==null){
            throw new ApiException("offer not found");
        }
        offerRepository.delete(offer);
    }


    //#21. accept offer or reject

    public void acceptOffer (Integer offerId , Integer projectOwner){
        Offer offer = offerRepository.findOfferByOfferId(offerId);
        if (offer==null){
            throw new ApiException("offer not found");
        }
Projects projects =projectsRepository.findProjectsByProjectIdAndUserid(offer.getProjectId(),projectOwner);

        if (projects==null){
            throw new ApiException("you are not authorized to update this offer");
        }
        if(offer.getStatus().equalsIgnoreCase("accepted") || offer.getStatus().equalsIgnoreCase("completed")){
            throw new ApiException("offer is Already Accepted or completed");
        }
        if (!projects.getStatus().equalsIgnoreCase("open")){
            throw new ApiException("you Already Accepted offer");
        }
        projects.setTalentId(offer.getUserid());
        projects.setStatus("inprogress");
        projectsRepository.save(projects);
        offer.setStatus("accepted");
        offerRepository.save(offer);
List<Offer> otherOffer = offerRepository.findOffersByProjectIdAndAndStatus(offer.getProjectId(),"pending");
for(Offer o :otherOffer){
    if (!o.getOfferId().equals(offer.getOfferId())){
        o.setStatus("rejected");
    }
}
offerRepository.saveAll(otherOffer);
    }

    //#22.compare offers for same project order by budget
public List<Offer>  compareOfferForProject (Integer projectId){
        List<Offer> offers = offerRepository.findOfferByProjectIdOrderByBudget(projectId);
        if (offers==null){
            throw new ApiException("No offers found for this project");
        }
        return offers;

}





}
