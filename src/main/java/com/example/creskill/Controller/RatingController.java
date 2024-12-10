package com.example.creskill.Controller;

import com.example.creskill.ApiRespose.ApiResponse;
import com.example.creskill.Model.Rating;
import com.example.creskill.Repostory.RatingRepository;
import com.example.creskill.Service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/getAllRating")
    public ResponseEntity getRating (){
        return ResponseEntity.status(200).body(ratingService.getRating());
    }
//#9
    @PostMapping("/addProjectRating/{projectId}/{raterUserId}/{rating}")
    public ResponseEntity addProjectRating(@PathVariable Integer projectId ,@PathVariable Integer raterUserId,
                                           @PathVariable Integer rating ,@RequestBody String comments){
        ratingService.addProjectRating(projectId,raterUserId,rating,comments);
        return ResponseEntity.status(200).body(new ApiResponse("added rating successfully"));
    }

//#10
    @PostMapping("/addSkillExchangeRating/{skillExchangeId}/{raterUserId}/{rating}")
    public ResponseEntity addSkillExchangeRating(@PathVariable Integer skillExchangeId ,@PathVariable Integer raterUserId,
                                           @PathVariable Integer rating ,@RequestBody String comments){
        ratingService.addSkillExchangeRating(skillExchangeId,raterUserId,rating,comments);
        return ResponseEntity.status(200).body(new ApiResponse("added rating successfully"));
    }
//#11
@PutMapping("/updateRating/{ratingId}/{newRating}")
    public ResponseEntity updateRating (@PathVariable Integer ratingId ,@PathVariable Integer newRating ,@RequestBody String newComment){
        ratingService.updateRating(ratingId,newRating,newComment);
        return ResponseEntity.status(200).body(new ApiResponse("update rating successfully"));
    }
//#12
    @GetMapping("/ratingsForUser/{ratedUserId}")
    public ResponseEntity  getRatingsForUser (@PathVariable Integer ratedUserId){
        List<Rating> ratingsForUser= ratingService.getRatingsForUser(ratedUserId);
        return ResponseEntity.status(200).body(ratingsForUser);
    }
//#13
    @GetMapping("/topRating")
    public ResponseEntity topRatingUser (){
        return ResponseEntity.status(200).body(ratingService.topRatingUser());
    }
//#14
    @GetMapping("/getMaxRating/{rating}")
    public ResponseEntity getMaxRating (@PathVariable Integer rating){
        return ResponseEntity.status(200).body(ratingService.getMaxRating(rating));
    }

}
