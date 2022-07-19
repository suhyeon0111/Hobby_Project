package com.hoppy.app.member.domain;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter  // 회원 정보 수정을 위해 추가
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {

    @Id
    private Long id;  //  == socialId

    private String email;
    private String username;
//    private String socialId; // id와 개념이 통일되었음
    private String profileImageUrl;
    private String intro;
    private String password;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    @Builder.Default
    @ToString.Exclude
    private Set<MemberMeeting> myMeetings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    @Builder.Default
    @ToString.Exclude
    private Set<MemberMeetingLike> myMeetingLikes = new HashSet<>();
}
