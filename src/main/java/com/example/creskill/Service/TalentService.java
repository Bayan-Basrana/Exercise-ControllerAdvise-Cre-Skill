package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.Rating;
import com.example.creskill.Model.Talent;
import com.example.creskill.Repostory.ProjectsRepository;
import com.example.creskill.Repostory.RatingRepository;
import com.example.creskill.Repostory.SkillExchangeRepository;
import com.example.creskill.Repostory.TalentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TalentService {
    private final TalentRepository talentRepository;
    private final RatingRepository ratingRepository;
    private final SkillExchangeRepository skillExchangeRepository;
    private final ProjectsRepository projectsRepository;


    public List<Talent> getUser() {
        return talentRepository.findAll();
    }


    public void add(Talent talent) {
        talentRepository.save(talent);
    }
public void add2 (List<Talent> talents){
        talentRepository.saveAll(talents);
    }

    public void update(Integer userid, Talent talent) {
        Talent old = talentRepository.findTalentByUserid(userid);

        if (old == null) {
            throw new ApiException("user id not found");
        }
        old.setName(talent.getName());
        old.setEmail(talent.getEmail());
        old.setBio(talent.getBio());
        old.setPassword(talent.getPassword());
        old.setSkillsOffered(talent.getSkillsOffered());
        old.setSkillsWanted(talent.getSkillsWanted());

        talentRepository.save(old);
    }


    public void delete(Integer userid) {
        Talent talent = talentRepository.findTalentByUserid(userid);
        if (talent == null) {
            throw new ApiException("user id not found");
        }
        talentRepository.delete(talent);
    }

//#1
    public Integer getUserPoint(Integer userId) {
        Talent talent = talentRepository.findTalentByUserid(userId);
        if (talent == null) {
            throw new ApiException("user id not found");
        }
        return talent.getPoints();
    }
//#2
    public Map<String, Object> performanceForUser(Integer userid) {
        Double avrRating = ratingRepository.findAverageForUser(userid);
        if(avrRating==null){
            avrRating=0.0;
        }
        Integer countRating = ratingRepository.countRatingForUser(userid);
        Integer completedProject = projectsRepository.countCompletedForTalentUser(userid);
        Integer completedSkillExchange = skillExchangeRepository.countCompletedForUser(userid);

        Map<String, Object> performance = new HashMap<>();
        performance.put("Average Rating", avrRating);
        performance.put("Number of Ratings", countRating);
        performance.put("completed projects", completedProject);
        performance.put("completed skill exchange", completedSkillExchange);

        return performance;

    }

//#3
    public void checkAndVerifyTalent (Integer userid){
        Talent talent = talentRepository.findTalentByUserid(userid);
        if(talent==null){
            throw new ApiException("user id not found");
        }
        Integer completedProject = projectsRepository.countCompletedForTalentUser(userid);
        if (completedProject<2){
            throw new ApiException("contention not met,must complete at least 3 projects");
        }
        if (talent.getPoints()<100 ) {
            throw new ApiException("contention not met, points must be grater than 150");
        }
        Double avgForUser=ratingRepository.findAverageForUser(userid);
        if (avgForUser<3.5 || avgForUser==null){
            throw new ApiException("contention not met, rating must be at lest 4");
        }
        talent.setIsVerified(true);
        talentRepository.save(talent);
    }
}
