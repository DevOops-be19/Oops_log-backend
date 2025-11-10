package com.devoops.oopslog.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllMemberDTO {
    private Long id;
    private String member_id;
    private String email;
    private String name;
    private Date birth;
    private String gender;
    private String user_state;
    private Date sign_up_date;
}
