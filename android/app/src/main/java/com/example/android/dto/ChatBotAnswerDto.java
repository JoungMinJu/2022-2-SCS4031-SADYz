package com.example.android.dto;

public class ChatBotAnswerDto {

    private String input;
    private String phone_number;
    private int dialog_type;
    private int conv_id;

    public ChatBotAnswerDto(String input, String phone_number, int dialog_type, int conv_id) {
        this.input = input;
        this.phone_number = phone_number;
        this.dialog_type = dialog_type;
        this.conv_id = conv_id;
    }
}
