package com.akshat.taskmanager.controller;

import com.akshat.taskmanager.dto.CreateProjectRequest;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.service.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.akshat.taskmanager.dto.ProjectResponse;
import com.akshat.taskmanager.dto.AddNewMemberRequest;
import com.akshat.taskmanager.dto.AddMemberResponse;
import java.util.stream.Collectors;
import com.akshat.taskmanager.dto.AddMemberRequest;
import com.akshat.taskmanager.dto.UpdateRoleRequest;
import com.akshat.taskmanager.dto.RemoveMemberRequest;
import com.akshat.taskmanager.dto.UpdateProjectRequest;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(
            @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {

        return projectService.createProject(
                request,
                authentication
        );
    }

    @GetMapping
    public List<ProjectResponse> getProjects(
            Authentication authentication
    ) {

        return projectService.getProjects(authentication)
                .stream()
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getDeadline(),
                        project.getOwner(),
                        projectService.mapMembers(project),
                        project.getCreated_at(),
                        project.getCreatedAt(),
                        project.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectResponse getProject(
            @PathVariable String id
    ) {

        Project project = projectService.getProject(id);

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getOwner(),
                projectService.mapMembers(project),
                project.getCreated_at(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    @PostMapping("/add-new-member")
    public AddMemberResponse addNewMember(
            @RequestBody AddNewMemberRequest request
    ) {
        return projectService.addNewMember(request);
    }

    @PatchMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable String id,
            @RequestBody UpdateProjectRequest request,
            Authentication authentication
    ) {

        Project project =
                projectService.updateProject(
                        id,
                        request,
                        authentication
                );

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getOwner(),
                projectService.mapMembers(project),
                project.getCreated_at(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    @PostMapping("/add-member")
    public ProjectResponse addMember(
            @RequestBody AddMemberRequest request
    ) {

        Project project =
                projectService.addMember(request);

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getOwner(),
                projectService.mapMembers(project),
                project.getCreated_at(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    @PatchMapping("/update-role")
    public ProjectResponse updateRole(
            @RequestBody UpdateRoleRequest request
    ) {

        Project project =
                projectService.updateRole(request);

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getOwner(),
                projectService.mapMembers(project),
                project.getCreated_at(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    @PatchMapping("/members/remove")
    public ProjectResponse removeMember(
            @RequestBody RemoveMemberRequest request
    ) {

        Project project =
                projectService.removeMember(request);

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getOwner(),
                projectService.mapMembers(project),
                project.getCreated_at(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}