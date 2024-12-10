package com.example.creskill.Controller;

import com.example.creskill.ApiRespose.ApiResponse;
import com.example.creskill.Model.ProjectOwner;
import com.example.creskill.Model.Projects;
import com.example.creskill.Model.Talent;
import com.example.creskill.Service.ProjectOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/owner")
@RequiredArgsConstructor
public class ProjectOwnerController {
    private final ProjectOwnerService projectOwnerService;



    @GetMapping("/get")
    public ResponseEntity get (){
        return ResponseEntity.status(200).body(projectOwnerService.getOwners());
    }
    @PostMapping("/add2")
    public ResponseEntity add2 (@RequestBody @Valid List<ProjectOwner> owners){
        projectOwnerService.add2(owners);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }
    @PostMapping("/add")
    public ResponseEntity add (@RequestBody @Valid ProjectOwner projectOwner){
        projectOwnerService.add(projectOwner);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{ownerId}")
    public ResponseEntity update (@PathVariable Integer ownerId ,@RequestBody @Valid ProjectOwner projectOwner ){
        projectOwnerService.update(ownerId,projectOwner);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity delete (@PathVariable Integer ownerId){
        projectOwnerService.delete(ownerId);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }


}
