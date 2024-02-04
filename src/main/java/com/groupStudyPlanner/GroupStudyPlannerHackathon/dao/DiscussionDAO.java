package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "discussion")
public class DiscussionDAO {
    @Id
    @UuidGenerator
    @GeneratedValue(generator = "UUID")
    private UUID did;
    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "uid", nullable = false, updatable = false)
    private UserDAO userId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "gid", nullable = false, updatable = false)
    private GroupDAO groupId;

    @Builder.Default
    @Column(name = "time", nullable = false, updatable = false)
    private LocalDateTime time = LocalDateTime.now();

}
