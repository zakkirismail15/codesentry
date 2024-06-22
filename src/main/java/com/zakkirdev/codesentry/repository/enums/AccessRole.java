package com.zakkirdev.codesentry.repository.enums;

public enum AccessRole {

    ROLE_ADMIN,
    ROLE_PROJECT_MANAGER,
    ROLE_DEVELOPER,
    ROLE_TESTER,
    ROLE_USER;

    public static AccessRole getRoleByName(String role){
        for(AccessRole accessRole : AccessRole.values()){
            if(accessRole.name().equals(role)){
                return accessRole;
            }
        }
        return null;
    }
}
