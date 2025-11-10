package com.devoops.oopslog.member.command.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class UserImpl extends User {
    private Long id;
    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public void setUserInfo(UserDetailInfoDTO userDetailInfoDTO) {
        this.id = userDetailInfoDTO.getId();
    }
}
