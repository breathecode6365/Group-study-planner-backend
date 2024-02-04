package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "user_to_group_map")
public class UserToGroupMapDAO {
    @Id
    @UuidGenerator
    @GeneratedValue(generator = "UUID")
    @Column(name = "ugid", unique = true, updatable = false, nullable = false)
    private UUID ugid;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "uid", nullable = false, updatable = false)
    private UserDAO userId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "gid", nullable = false, updatable = false)
    private GroupDAO groupId;
}
