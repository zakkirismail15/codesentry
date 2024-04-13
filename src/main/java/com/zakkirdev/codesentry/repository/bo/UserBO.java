package com.zakkirdev.codesentry.repository.bo;

import com.zakkirdev.codesentry.repository.enums.AccessRole;
import lombok.Data;

@Data
public class UserBO {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private AccessRole role;
}
