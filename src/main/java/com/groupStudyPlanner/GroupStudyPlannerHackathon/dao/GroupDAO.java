package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "groups")
public class GroupDAO {
    @Id
    @UuidGenerator
    @GeneratedValue(generator = "UUID")
    @Column(name = "gid", unique = true, updatable = false, nullable = false)
    private UUID gid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "overallProgress", nullable = false)
    @Builder.Default
    private double overallProgress = 0.0;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "uid", nullable = false, updatable = false)
    private UserDAO createdBy;

    @Column(name = "createdOn", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = java.time.LocalDateTime.now();
}
