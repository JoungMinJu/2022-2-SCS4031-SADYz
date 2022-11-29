from flask import Blueprint, request
from service import koelectra_qa, kogptchat
from service import responseService, dbService, emotion_classification
from util.response import *

emotion = Blueprint("emotion", __name__, url_prefix="/emotion")

@emotion.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    client_id = dbService.get_client_id(phone_number)
    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END, -1)
    question = responseService.get_call_to_say_hi()
    conv_id = dbService.create_conversation(question, client_id)
    dbService.change_response_status(phone_number, False)
    return return_answer(question, DT_EMOTION, conv_id)  # 응답 시간 기록하기 -> 정상 비정상 여부
    # -> 배치로 n분 동안 대답 x인 사람 움직임 X인 사람 알려주기


@emotion.post("/chat")
def chat_emotion():
    input = request.values.get("input")
    input_split = input.split(" ")
    phone_number = request.values.get('phone_number')
    conv_id = request.values.get("conv_id")
    quit_answer = responseService.is_quit_answer(input_split)
    dbService.change_response_status(phone_number, True)
    print(quit_answer)
    if quit_answer:
        dbService.update_conversation_emotion(conv_id)
        return return_answer(QUIT, DT_END, conv_id)  # 종료 -> 감정값 전달
    phone_number = request.values.get("phone_number")
    # 긍정 부정 판단
    classification = emotion_classification.predict(input)
    print(classification)
    is_negative = responseService.is_negative_answer(classification)
    if is_negative:
        emotion = "부정"
        answer = koelectra_qa.get_answer(input) 
    else:
        emotion = "긍정"
        answer = kogptchat.predict(input)
    dbService.update_conversation(conv_id, emotion, (input + "," + answer), "")
    return return_answer(answer + IS_QUIT, DT_EMOTION, conv_id) # 종료 -> 응답 했다고 갱신
