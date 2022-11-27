from flask import Blueprint, request, jsonify

from service import responseService, dbService, emotion_classification
from service import koelectra_qa, kogptchat
from util import response

emotion = Blueprint("emotion", __name__, url_prefix="/emotion")


@emotion.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return response.NOT_IN_THE_HOUSE
    question = responseService.get_call_to_say_hi()
    return response.call_to_say_hi(question)  # 응답 시간 기록하기 -> 정상 비정상 여부


@emotion.post("/chat")
def chat_emotion():
    input = request.values.get("input")
    input_split = input.split(" ")
    quit_answer = responseService.is_quit_answer(input_split)
    if quit_answer:
        return response.QUIT  # 종료 -> 감정값 전달

    phone_number = request.values.get("phone_number")
    # 긍정 부정 판단
    classification = emotion_classification.predict(input)
    is_negative = responseService.is_negative_answer(classification)

    if is_negative:
        answer = koelectra_qa.get_answer(input)
    else:
        answer = kogptchat.predict(input)
    return response.return_answer(answer)  # 종료
