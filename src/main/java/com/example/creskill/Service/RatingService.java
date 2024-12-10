package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.Projects;
import com.example.creskill.Model.Rating;
import com.example.creskill.Model.SkillExchange;
import com.example.creskill.Repostory.ProjectsRepository;
import com.example.creskill.Repostory.RatingRepository;
import com.example.creskill.Repostory.SkillExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final ProjectsRepository projectsRepository;
    private final SkillExchangeRepository skillExchangeRepository;

    public List<Rating> getRating (){
        return ratingRepository.findAll();
    }

//#9
    public void  addProjectRating (Integer projectId ,Integer raterUserId, Integer rating , String comments){
        Projects project =projectsRepository.findProjectsByProjectId(projectId);
        if(project==null){
            throw new ApiException("projectId not found");
        }
        Integer ownerId = project.getUserid();
        Integer projectTalentId =project.getTalentId();
        if(!raterUserId.equals(projectTalentId) && !raterUserId.equals(ownerId)){
            throw new ApiException("only project owner or talent can rate the project ");
        }
        Integer ratedUserId =(project.getUserid().equals(raterUserId))?project.getTalentId():project.getUserid();
if(rating<1 || rating>5){
    throw new ApiException("rating must be between 1 and 5");
}
if (comments==null){
    throw new ApiException("comment can not be empty");
}
        Rating newRating = new Rating();
        newRating.setProjectId(projectId);
        newRating.setSkillExchangeId(null);
        newRating.setRatedUserId(ratedUserId);
        newRating.setRaterUserId(raterUserId);
        newRating.setRating(rating);
        newRating.setComments(comments);
        newRating.setCreatedAt(LocalDate.now());
        ratingRepository.save(newRating);
    }

//#10
    public void  addSkillExchangeRating (Integer skillExchangeId ,Integer raterUserId, Integer rating , String comments){
        SkillExchange skillExchange =skillExchangeRepository.findSkillExchangeBySkillExchangeId(skillExchangeId);
        if(skillExchange==null){
            throw new ApiException("skillExchangeId not found");
        }
        Integer ratedUserId =(skillExchange.getUserId().equals(raterUserId)?skillExchange.getPartnerId():skillExchange.getUserId());
        if(rating<1 || rating>5){
            throw new ApiException("rating must be between 1 and 5");
        }
        if (comments==null){
            throw new ApiException("comment can not be empty");
        }
        Rating newRating = new Rating();
        newRating.setProjectId(null);
        newRating.setSkillExchangeId(skillExchangeId);
        newRating.setRatedUserId(ratedUserId);
        newRating.setRaterUserId(raterUserId);
        newRating.setRating(rating);
        newRating.setComments(comments);
        newRating.setCreatedAt(LocalDate.now());

        ratingRepository.save(newRating);
    }

//#11
    public void updateRating (Integer ratingId ,Integer newRating ,String newComment){
        Rating rating1 = ratingRepository.findRatingByRatingId(ratingId);
        if (rating1==null){
            throw new ApiException("ratingId not found");
        }
        //وقت السماح  بالتعديل 48 ساعة فقط
        LocalDate createdAt = rating1.getCreatedAt();
        Duration duration= Duration.between(createdAt, LocalDateTime.now());
        if(duration.toHours()>48){
            throw new ApiException("you can update the rating only within 48 H");
        }
        if(newRating<1 || newRating>5){
            throw new ApiException("rating must be between 1 and 5");
        }
        if (newComment==null){
            throw new ApiException("comment can not be empty");
        }

        rating1.setRating(newRating);
        rating1.setComments(newComment);
        ratingRepository.save(rating1);
    }
//#12
    public List<Rating> getRatingsForUser (Integer ratedUserId){
        List<Rating> ratingsForUser= ratingRepository.findRatingByRatedUserId(ratedUserId);
        if (ratingsForUser==null){
            throw new ApiException("No Rating found");
        }

        return ratingsForUser;
    }
//#13
    public List<Map<String,Object>>  topRatingUser (){
return ratingRepository.tobRating().stream().limit(3)
     .map(result ->Map.of("userid",result[0],"average", result[1], "total",result[2])).toList();
    }

//#14
    public List<Rating> getMaxRating (Integer rating){
        List<Rating> maxRating =ratingRepository.getByRating(rating);
        if (rating==null){
            throw new ApiException("not found");
        }
        return maxRating;
    }
}
