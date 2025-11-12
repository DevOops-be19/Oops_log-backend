package com.devoops.oopslog.member.command.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class UserImpl extends User {
    private Long id;
    private String memberId;
    private String email;
    private String name;
    private String birth;
    private Character gender;
    private String signUpDate;

    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public void setUserInfo(UserDetailInfoDTO userDetailInfoDTO) {

        this.id = userDetailInfoDTO.getId();
        this.memberId = userDetailInfoDTO.getMemberId();
        this.email = userDetailInfoDTO.getEmail();
        this.name = userDetailInfoDTO.getName();
        this.birth = userDetailInfoDTO.getBirth();
        this.gender = userDetailInfoDTO.getGender();
        this.signUpDate = userDetailInfoDTO.getSignUpDate();
    }
}
