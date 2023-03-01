package com.ncc9project.technolearn.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDTO {

    private String toEmail;

    private String subject;

    private String body;
}
