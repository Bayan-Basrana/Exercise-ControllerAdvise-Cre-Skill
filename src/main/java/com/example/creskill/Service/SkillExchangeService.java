package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.SkillExchange;
import com.example.creskill.Model.Talent;
import com.example.creskill.Repostory.SkillExchangeRepository;
import com.example.creskill.Repostory.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillExchangeService {
    private final SkillExchangeRepository skillExchangeRepository;
    private final TalentRepository talentRepository;



    public List<SkillExchange> getSkillExchange (){
        return skillExchangeRepository.findAll();
    }

//#4-request for skill exchange
    public void addRequest(SkillExchange request){
        if (talentRepository.findTalentByUserid(request.getUserId())==null)
       {
            throw new ApiException("user id not found");
        }
    SkillExchange skillExchange =SkillExchange.builder().userId(request.getUserId())
            .talentSkill(request.getTalentSkill())
            .partnerSkill(request.getPartnerSkill())
            .location(request.getLocation())
            .sessionLink(request.getSessionLink())
            .date(request.getDate())
            .status("pending")
            .build();

        if (request.getLocation().equalsIgnoreCase("online") && request.getSessionLink()==null){
             throw new ApiException("session link is mandatory");
        }
        skillExchangeRepository.save(skillExchange);
          }

    public void update (Integer skillExchangeId , SkillExchange skillExchange){
        SkillExchange old = skillExchangeRepository.findSkillExchangeBySkillExchangeId(skillExchangeId);

        if (old==null){
            throw new ApiException("skillExchange id not found");
        }
        if (talentRepository.findTalentByUserid(skillExchange.getUserId())==null){
            throw new ApiException("user id not found");
        }

      old.setLocation(skillExchange.getLocation());
        old.setSessionLink(skillExchange.getSessionLink());
        old.setDate(skillExchange.getDate());
        old.setTalentSkill(skillExchange.getTalentSkill());
        old.setPartnerSkill(skillExchange.getPartnerSkill());

        if (skillExchange.getLocation().equalsIgnoreCase("online") && skillExchange.getSessionLink()==null){
            throw new ApiException("session link is mandatory");
        }
        skillExchangeRepository.save(old) ;
    }


    public void delete (Integer skillExchangeId ){
        SkillExchange skillExchange = skillExchangeRepository.findSkillExchangeBySkillExchangeId(skillExchangeId);
        if (skillExchange==null){
            throw new ApiException("skillExchangeId not found");
        }
        skillExchangeRepository.delete(skillExchange);
    }
    //#5get all Available skill exchange
    public List<SkillExchange> findAvailableExchange (){
        return skillExchangeRepository.findAvailableExchange("pending");
    }

    //قبول الطلب-6#
    public void startSkillExchange (Integer skillExchangeId,Integer partnerId){
        SkillExchange skillExchange =
                skillExchangeRepository.findSkillExchangeBySkillExchangeId(skillExchangeId);
        if (skillExchange==null){
            throw new ApiException("skillExchangeId not found");
        }
        if(talentRepository.findTalentByUserid(partnerId)==null){
            throw new ApiException("user Id not found");
        }
        if (!talentRepository.findTalentByUserid(partnerId).getSkillsOffered().contains(skillExchange.getPartnerSkill())){
            throw new ApiException("partner dose not have the required skill: "+ skillExchange.getPartnerSkill());
        }
        if(skillExchange.getStatus().equalsIgnoreCase("completed")){
            throw new ApiException("skill exchange completed chose another");
        }
skillExchange.setPartnerId(partnerId);
        skillExchange.setDate(LocalDate.now());
        skillExchange.setStatus("started");
        skillExchangeRepository.save(skillExchange);

    }

//#7
    public void completeSkillExchange (Integer skillExchangeId,Integer userId){
        SkillExchange skillExchange =
                skillExchangeRepository.findSkillExchangeBySkillExchangeId(skillExchangeId);
        if (skillExchange==null){
            throw new ApiException("skillExchangeId not found");
        }
        if(talentRepository.findTalentByUserid(userId)==null){
            throw new ApiException("user Id not found");
        }
        if (!skillExchange.getStatus().equalsIgnoreCase("started")){
            throw new ApiException("skill exchange can only be completed if is started");
        }
        skillExchange.setStatus("completed");
        skillExchangeRepository.save(skillExchange);
        Talent creator = talentRepository.findTalentByUserid(userId);
        if (creator.getPoints()==null){
            creator.setPoints(50);
        }else creator.setPoints(creator.getPoints()+50);
        talentRepository.save(creator);

        Talent partner = talentRepository.findTalentByUserid(skillExchange.getPartnerId());
        if (partner.getPoints()==null){
            partner.setPoints(50);
        }else partner.setPoints(partner.getPoints()+50);
        talentRepository.save(partner);
    }
//#8
    public SkillExchange randomNewSkillExchange (Integer partnerId){
        List<SkillExchange> findPendingSkillExchange = skillExchangeRepository.findPendingSkillExchange();
        if (findPendingSkillExchange == null || findPendingSkillExchange.isEmpty()) {
            throw new ApiException("No available sessions");
        }
        // فلترة الجلسات لإزالة الجلسات التي يكون فيها الشريك نفس المستخدم
        List<SkillExchange> validSessions = findPendingSkillExchange.stream()
                .filter(session -> session.getPartnerId() == null || !session.getPartnerId().equals(partnerId))
                .collect(Collectors.toList());
        if (validSessions.isEmpty()) {
            throw new ApiException("No valid sessions available for this partner");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(validSessions.size());
        SkillExchange selected = validSessions.get(randomIndex);
        selected.setPartnerId(partnerId);
        selected.setStatus("started");
        selected.setDate(LocalDate.now());
        skillExchangeRepository.save(selected);

        return selected;
    }
}
