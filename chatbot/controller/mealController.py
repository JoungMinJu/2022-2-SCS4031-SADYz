from flask import Blueprint, request

from service import responseService, dbService
from util.response import *



meal = Blueprint("meal", __name__, url_prefix="/meal")

@meal.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)

    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END)
    return return_answer(MEAL_STATUS_QUESTION, DT_MEAL)


@meal.post("/chat")
def emotion_chat():
    input = request.values.get("input")
    phone_number = request.values.get("phone_number")
    input_list = input.split(" ")
    status = responseService.check_eating_status(input_list)

    if status == "ERROR":
        return return_answer(NOT_UNDERSTAND, DT_MEAL)# 대화 종료 -> 2
    if status == "NO":
        return return_answer(OFFER_MEAL, DT_END_SPEAK) # 대화 종료 -> 상태 값 추가 -> 0
    if status == "YES":
        kitchen_moved_history = dbService.get_kitchen_moved_history(phone_number)

        if not kitchen_moved_history :
            door_closed_history = dbService.get_door_closed_history(phone_number)

            if not door_closed_history:
                return return_answer(OFFER_MEAL_WHEN_NOT_MOVED, DT_END_SPEAK) # 대화 종료 -> 정보 전달 -> 상태 값 추가 -> 9

            return return_answer(OFFER_MEAL_WHEN_MOVED, DT_END_SPEAK) # 대화 종료 -> 정보 전달 -> 0
        return return_answer(NORMAL_STATUS, DT_END_SPEAK) # 대화 종료 -> 0
