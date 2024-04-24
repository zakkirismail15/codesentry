package com.zakkirdev.codesentry.repository.enums;

public enum Priority {

    LOW,
    MEDIUM,
    HIGH;


    public static Priority getPriorityEnum(String priorityValue){
        for(Priority value : Priority.values()){
            if(value.name().equalsIgnoreCase(priorityValue)) {
                return value;
            }
        }
        return null;
    }
}
