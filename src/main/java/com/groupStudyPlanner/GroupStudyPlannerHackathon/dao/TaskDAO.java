package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.enumerators.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "tasks")
public class TaskDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID tid;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deadline", nullable = false)
    private long deadline;

    @Column(name = "progress", nullable = false)
    @Builder.Default
    private double progress = 0.0;

    @Column(name = "status", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "groupId",referencedColumnName = "gid", nullable = false, updatable = false)
    private GroupDAO groupId;

    @Column(name = "createdOn", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "task_completion",
            joinColumns = @JoinColumn(name = "tid"),
            inverseJoinColumns = @JoinColumn(name = "uid")
    )
    private Set<UserDAO> completedList;


}
