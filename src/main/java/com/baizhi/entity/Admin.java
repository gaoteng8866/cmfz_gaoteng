package com.baizhi.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private String nickname;
}
