from flask import Blueprint, request
from service import koelectra_qa, kogptchat
from service import responseService, dbService, emotion_classification
from util.response import *

emotion = Blueprint("emotion", __name__, url_prefix="/emotion")

emotions = []

@emotion.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END)
    question = responseService.get_call_to_say_hi()
    emotions.clear()
    return return_answer(question, DT_EMOTION)  # 응답 시간 기록하기 -> 정상 비정상 여부
    # -> 배치로 n분 동안 대답 x인 사람 움직임 X인 사람 알려주기


@emotion.post("/chat")
def chat_emotion():
    input = request.values.get("input")
    input_split = input.split(" ")
    quit_answer = responseService.is_quit_answer(input_split)
    if quit_answer:
        return return_answer(QUIT, DT_END)  # 종료 -> 감정값 전달
    phone_number = request.values.get("phone_number")
    # 긍정 부정 판단
    classification = emotion_classification.predict(input)
    emotions.append(classification) # 감정 값 추가
    is_negative = responseService.is_negative_answer(classification)

    if is_negative:
        answer = koelectra_qa.get_answer(input) 
    else:
        answer = kogptchat.predict(input)
    return return_answer(answer + IS_QUIT, DT_EMOTION) # 종료 -> 응답 했다고 갱신
