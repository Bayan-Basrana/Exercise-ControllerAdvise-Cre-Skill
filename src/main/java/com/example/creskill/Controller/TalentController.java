package com.example.creskill.Controller;

import com.example.creskill.ApiRespose.ApiResponse;
import com.example.creskill.Model.Talent;
import com.example.creskill.Service.TalentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/talent")
@RequiredArgsConstructor
public class TalentController {

    private final TalentService talentService;

    @GetMapping("/get")
    public ResponseEntity getUser (){
        return ResponseEntity.status(200).body(talentService.getUser());
    }
    @PostMapping("/add2")
    public ResponseEntity add2 (@RequestBody @Valid List<Talent> talents ){
        talentService.add2(talents);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }
    @PostMapping("/add")
    public ResponseEntity add (@RequestBody @Valid Talent talent ){
        talentService.add(talent);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{userid}")
    public ResponseEntity update (@PathVariable Integer userid ,@RequestBody @Valid Talent talent  ){
        talentService.update(userid,talent);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{userid}")
    public ResponseEntity delete (@PathVariable Integer userid){
        talentService.delete(userid);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

    @GetMapping("/getUserPoint/{userid}")
    public ResponseEntity getUserPoint (@PathVariable Integer userid){
        return ResponseEntity.status(200).body( talentService.getUserPoint(userid));
    }
//#1
    @GetMapping("/performanceForUser/{userid}")
public ResponseEntity performanceForUser (@PathVariable Integer userid){
        return ResponseEntity.status(200).body(talentService.performanceForUser(userid));
}
//#2
@PutMapping("/checkAndVerifyTalent/{userid}")
public ResponseEntity checkAndVerifyTalent (@PathVariable Integer userid){
        talentService.checkAndVerifyTalent(userid);
        return ResponseEntity.status(200).body(new ApiResponse("Account verified successfully"));
}

}
