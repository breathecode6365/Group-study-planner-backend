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
@Table(name = "resources")
public class ResourcesDAO {
    @Id
    @UuidGenerator
    @GeneratedValue(generator = "UUID")
    @Column(name = "rid", unique = true, updatable = false, nullable = false)
    private UUID rid;

    @Column(name = "name", nullable = false)
    private String resourceName;

    @Column(name = "link", nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "owner",referencedColumnName = "uid",nullable = false, updatable = false)
    private UserDAO owner;

    @ManyToOne
    @JoinColumn(name = "groupId",referencedColumnName = "gid", nullable = false, updatable = false)
    private GroupDAO groupId;

    @Column(name = "createdOn", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();
}
