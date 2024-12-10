package com.example.creskill.Controller;

import com.example.creskill.ApiRespose.ApiResponse;
import com.example.creskill.Model.Projects;
import com.example.creskill.Service.ProjectsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/project")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @GetMapping("/get")
    public ResponseEntity getProject (){
        return ResponseEntity.status(200).body(projectsService.getProjects());
    }

    @PostMapping("/add")
    public ResponseEntity add (@RequestBody @Valid Projects projects ){
projectsService.add(projects)
;        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity update (@PathVariable Integer projectId ,@RequestBody @Valid Projects projects){
        projectsService.update(projectId,projects);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity delete (@PathVariable Integer projectId){
projectsService.delete(projectId);
return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }
    //#15
    @GetMapping("/projectByOwnerId/{userid}")
    public ResponseEntity projectByOwnerId (@PathVariable Integer userid){
        return ResponseEntity.status(200).body(projectsService.projectByOwnerId(userid));
    }
    //#16
    @PutMapping("/deliverByTalent/{projectId}/{talentId}")
    public ResponseEntity deliverByTalent (@PathVariable Integer projectId ,@PathVariable Integer talentId) {
        projectsService.deliverByTalent(projectId,talentId);
        return ResponseEntity.status(200).body(new ApiResponse("project delivered successfully"));
    }
    //#17
    @PutMapping("/completeProject/{projectId}/{projectOwnerId}")
public ResponseEntity completeProject (@PathVariable Integer projectId ,@PathVariable Integer projectOwnerId) {
        projectsService.completeProject(projectId,projectOwnerId);
    return ResponseEntity.status(200).body(new ApiResponse("project completed successfully , and points updates"));
}
//#18
@GetMapping("findProjectMatchSkills/{talentId}")
public ResponseEntity findProjectMatchSkills (@PathVariable Integer talentId) {
    List<Projects> recommendedSkill = projectsService.findProjectMatchSkills(talentId);
    return ResponseEntity.status(200).body(recommendedSkill);
}
//#19-1.1show project with specific budget or less
@GetMapping("/projectsLessBudget/{budget}")
public ResponseEntity getProjectsLessBudget (@PathVariable Double budget){
        return ResponseEntity.status(200).body(projectsService.getProjectsLessBudget(budget));
}

//#19-2.show project with specific budget or more
    @GetMapping("/projectsMoreBudget/{budget}")
    public ResponseEntity getProjectsMoreBudget (@PathVariable Double budget){
        return ResponseEntity.status(200).body(projectsService.getProjectsMoreBudget(budget));
    }



}
