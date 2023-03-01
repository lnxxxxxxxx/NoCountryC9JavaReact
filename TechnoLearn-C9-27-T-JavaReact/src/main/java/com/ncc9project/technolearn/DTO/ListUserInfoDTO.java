package com.ncc9project.technolearn.DTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ListUserInfoDTO {
    private Set<UserInfoDTO> userInfo;
}
