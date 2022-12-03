package com.example.android.dto;

public class EmergencyDto {

    private boolean emergencyNow;
    private String emergencyType;

    public EmergencyDto(boolean emergencyNow, String emergencyType) {
        this.emergencyNow = emergencyNow;
        this.emergencyType = emergencyType;
    }
}
