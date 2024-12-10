package com.example.creskill.Controller;

import com.example.creskill.ApiRespose.ApiResponse;
import com.example.creskill.Model.Offer;
import com.example.creskill.Model.Projects;
import com.example.creskill.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;


    @GetMapping("/get")
    public ResponseEntity get (){
        return ResponseEntity.status(200).body(offerService.getOffer());
    }
//#20
    @PostMapping("/add")
    public ResponseEntity add (@RequestBody @Valid Offer offer ){
        offerService.add(offer)
        ;        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{offerId}")
    public ResponseEntity update (@PathVariable Integer offerId ,@RequestBody @Valid Offer offer ){
        offerService.update(offerId,offer);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity delete (@PathVariable Integer offerId){
        offerService.delete(offerId);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

//#21
    @PutMapping("/acceptOffer/{offerId}/{projectOwner}")
    public ResponseEntity acceptOffer (@PathVariable Integer offerId ,@PathVariable Integer projectOwner){
        offerService.acceptOffer(offerId,projectOwner);
        return ResponseEntity.status(200).body(new ApiResponse("changed successfully"));

    }
//#22
    //compare offers for same project order by budget
    @GetMapping("compareOfferForProject/{projectId}")
    public ResponseEntity compareOfferForProject (@PathVariable Integer projectId) {
        List<Offer> offers = offerService.compareOfferForProject(projectId);
        return ResponseEntity.status(200).body(offers);
    }

}
