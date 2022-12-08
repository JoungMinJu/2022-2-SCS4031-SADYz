from flask import Blueprint, request

from service import responseService, dbService
from util.response import *



meal = Blueprint("meal", __name__, url_prefix="/meal")

@meal.post("/start")
def start_meal():
    json = request.get_json()
    phone_number = json["phone_number"]
    stay = dbService.is_client_at_home(phone_number)
    client_id = dbService.get_client_id(phone_number)
    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END, -1) # -> -1
    conv_id = dbService.create_conversation(MEAL_STATUS_QUESTION, client_id)
    return return_answer(MEAL_STATUS_QUESTION, DT_MEAL, conv_id)  # 상태 값 추가

@meal.post("/chat")
def meal_chat():
    json = request.get_json()
    input = json["input"]
    phone_number = json["phone_number"]
    conv_id = json["conv_id"]
    input_list = input.split(" ")
    status = responseService.check_eating_status(input_list)

    if status == "ERROR":
        return return_answer(NOT_UNDERSTAND, DT_MEAL, conv_id)  # 대화 종료 -> 2
    if status == "NO":
        dbService.update_conversation(conv_id, "", (input + "," + OFFER_MEAL), "밥 안드심")
        return return_answer(OFFER_MEAL, DT_END_SPEAK, conv_id)  # 대화 종료 -> 상태 값 추가 -> 0
    if status == "YES":
        kitchen_moved_history = dbService.get_kitchen_moved_history(phone_number)

        if not kitchen_moved_history:
            door_closed_history = dbService.get_door_closed_history(phone_number)

            if not door_closed_history:
                dbService.update_conversation(conv_id, "", (input + "," + OFFER_MEAL_WHEN_NOT_MOVED),
                                              "식사시간 내 주방 움직임 없음")
                return return_answer(OFFER_MEAL_WHEN_NOT_MOVED, DT_END_SPEAK, conv_id)  # 대화 종료 -> 정보 전달 -> 상태 값 추가 -> 9
            dbService.update_conversation(conv_id, "", (input + "," + OFFER_MEAL_WHEN_MOVED), "외식 예상")
            return return_answer(OFFER_MEAL_WHEN_MOVED, DT_END_SPEAK, conv_id)  # 대화 종료 -> 정보 전달 -> 0
        dbService.update_conversation(conv_id, "", (input + "," + NORMAL_STATUS), "")
        return return_answer(NORMAL_STATUS, DT_END_SPEAK, conv_id)  # 대화 종료 -> 0
