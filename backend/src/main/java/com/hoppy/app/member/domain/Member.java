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
    private String socialId;
//    private Long id;  //  == socialId

    private String email;
    private String username;
//    private String socialId;
    private String profileImageUrl;
    private String intro;
    private String password;


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

    public Member(String socialId, String email, String username, String profileImageUrl,
            String intro,
            String password, SocialType socialType, Role role) {
        this.socialId = socialId;
        this.email = email;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.intro = intro;
        this.password = password;
        this.socialType = socialType;
        this.role = role;
    }
}
