package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.*;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.model.ProjectMember;
import com.akshat.taskmanager.repository.ProjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Project createProject(
            CreateProjectRequest request,
            Authentication authentication
    ) {

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDeadline(request.getDeadline());
        project.setCreated_at(new Date());
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        project.setOwner(user.getId());

        if (request.getMembers() != null) {

            List<ProjectMember> members = request.getMembers()
                    .stream()
                    .map(memberId -> {
                        ProjectMember member = new ProjectMember();
                        member.setUser(memberId);
                        member.setRole("member");
                        return member;
                    })
                    .toList();

            project.setMembers(members);
        }

        return projectRepository.save(project);

            }

    public List<Project> getProjects(
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if ("admin".equalsIgnoreCase(user.getRole())) {
            return projectRepository.findAll();
        }

        return projectRepository.findByMember(
                user.getId()
        );
    }

    public List<ProjectMemberResponse> mapMembers(Project project) {

        return project.getMembers()
                .stream()
                .filter(member ->
                        member.getUser() != null
                )
                .map(member -> {

                    User user = userRepository
                            .findById(member.getUser())
                            .orElse(null);

                    if (user == null) {
                        return null;
                    }

                    return new ProjectMemberResponse(
                            new UserSummary(
                                    user.getId(),
                                    user.getName(),
                                    user.getEmail()
                            ),
                            member.getRole()
                    );
                })
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    public Project getProject(String id) {

        return projectRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));
    }

    public AddMemberResponse addNewMember(
            AddNewMemberRequest request
    ) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());

        User savedUser =
                userRepository.save(user);

        Project project =
                projectRepository
                        .findById(request.getProjectId())
                        .orElseThrow(() ->
                                new RuntimeException("Project not found"));

        ProjectMember member =
                new ProjectMember();

        member.setUser(savedUser.getId());
        member.setRole(request.getRole());

        project.getMembers().add(member);

        project.setUpdatedAt(new Date());

        projectRepository.save(project);

        return new AddMemberResponse(
                "User added successfully",
                new UserResponse(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail(),
                        savedUser.getRole()
                )
        );
    }

    public Project updateProject(
            String id,
            UpdateProjectRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Project project = projectRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        if (!project.getOwner().equals(user.getId())) {
            throw new RuntimeException("Not allowed");
        }

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDeadline(request.getDeadline());
        project.setUpdatedAt(new Date());

        return projectRepository.save(project);
    }

    public Project addMember(
            AddMemberRequest request
    ) {

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        boolean exists = project.getMembers()
                .stream()
                .anyMatch(m ->
                        request.getUserId()
                                .equals(m.getUser()));

        if (!exists) {

            ProjectMember member =
                    new ProjectMember();

            member.setUser(request.getUserId());

            member.setRole(
                    request.getRole() == null
                            ? "member"
                            : request.getRole()
            );

            project.getMembers().add(member);

            project.setUpdatedAt(new Date());

            projectRepository.save(project);
        }

        return project;
    }

    public Project updateRole(
            UpdateRoleRequest request
    ) {

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        ProjectMember member =
                project.getMembers()
                        .stream()
                        .filter(m ->
                                request.getUserId()
                                        .equals(m.getUser()))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        member.setRole(request.getRole());

        project.setUpdatedAt(new Date());

        return projectRepository.save(project);
    }

    public Project removeMember(
            RemoveMemberRequest request
    ) {

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        project.setMembers(

                project.getMembers()
                        .stream()
                        .filter(m ->
                                m.getUser() == null ||

                                        !m.getUser().equals(
                                                request.getUserId()
                                        ))
                        .toList()
        );

        project.setUpdatedAt(new Date());

        return projectRepository.save(project);
    }
}