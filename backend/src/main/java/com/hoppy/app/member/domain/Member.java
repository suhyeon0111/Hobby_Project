package com.hoppy.app.member.domain;

import com.hoppy.app.login.auth.SocialType;
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
import lombok.Setter;

@Entity
@Getter
@Setter  // 회원 정보 수정을 위해 추가
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String socialId;
    private String profileUrl;
    private String intro;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private Set<MemberMeeting> myMeetings = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private Set<MemberMeetingLike> myMeetingLikes = new HashSet<>();


}
