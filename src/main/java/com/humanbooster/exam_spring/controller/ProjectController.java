package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.service.ProjectService;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    private final ModelMapperUtil modelMapperUtil;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Project savedProject = projectService.save(modelMapperUtil.toProject(projectDTO));
        return ResponseEntity.ok(modelMapperUtil.toProjectDTO(savedProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        return projectService.findById(id)
                .map(projectDTO -> ResponseEntity.ok(modelMapperUtil.toProjectDTO(projectDTO)))
                .orElse(null);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getProjectTasks(@PathVariable Long id) {
        return ResponseEntity.ok(
                taskService.findTasksByProjectId(id)
                        .stream()
                        .map(modelMapperUtil::toTaskDTO)
                        .collect(Collectors.toList())
        );
    }

}
