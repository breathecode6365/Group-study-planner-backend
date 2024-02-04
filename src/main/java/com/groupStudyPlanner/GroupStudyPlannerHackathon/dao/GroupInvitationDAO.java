package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.enumerators.InvitationStatus;
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
@Table(name = "invitation")
public class GroupInvitationDAO {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    private UUID invId;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "uid", nullable = false, updatable = false)
    private UserDAO sender;

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "uid", nullable = false, updatable = false)
    private UserDAO receiver;

    @ManyToOne
    @JoinColumn(name = "invitedTo", referencedColumnName = "gid", nullable = false, updatable = false)
    private GroupDAO invitedTo;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "createdOn", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();


}
