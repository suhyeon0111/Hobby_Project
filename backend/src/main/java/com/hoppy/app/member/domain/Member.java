package com.hoppy.app.member.domain;

import com.hoppy.app.login.oauth.SocialType;
import com.hoppy.app.member.Role;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

//    @Id
//    @GeneratedValue
//    @Column(name = "member_id")
//    private Long id;
//    private String username;
//    private String password;
//    private String name;
//
//    private String socialId;
//
//    @Enumerated(EnumType.STRING)
//    private SocialType socialType;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
    // 클래스 추가. 내글, 관심글, 모임 등등

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기존 인스턴스 start
    private String email;
    private String username;
    private String socialId;
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;
    // end

    @OneToMany(mappedBy = "member")
    private Set<MemberMeeting> myMeetings = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private Set<MemberMeetingLike> myMeetingLikes = new HashSet<>();


}
