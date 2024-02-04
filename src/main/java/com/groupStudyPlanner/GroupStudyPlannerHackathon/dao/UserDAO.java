package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@Table(name = "users")
@Builder
public class UserDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "uid", unique = true, updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "emailId", nullable = false, unique = true)
    private String emailId;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "profilePic")
    private String profilePic = "https://www.pngitem.com/pimgs/m/22-223968_default-profile-picture-circle-hd-png-download.png";

    @Column(name = "isVerified", nullable = false)
    @Builder.Default
    private boolean isVerified = false;
}
