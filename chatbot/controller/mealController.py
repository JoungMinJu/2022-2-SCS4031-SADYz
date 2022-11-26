from flask import Blueprint, request

from service import responseService, dbService
from util import response


meal = Blueprint("meal", __name__, url_prefix="/meal")

@meal.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    # 사람 찾기 -> 외출 여부 파악
    client = dbService.get_client(phone_number)
    stay = dbService.is_client_at_home(phone_number)

    if not stay:
        return response.NOT_IN_THE_HOUSE
    return response.MEAL_STATUS_QUESTION


@meal.post("/chat")
def emotion_chat():
    input = request.values.get("input")
    phone_number = request.values.get("phone_number")
    input_list = input.split(" ")
    status = responseService.check_eating_status(input_list)

    if status == "ERROR":
        return response.NOT_UNDERSTAND # 대화 종료
    if status == "NO":
        return response.OFFER_MEAL # 대화 종료
    if status == "YES":
        kitchen_moved_history = dbService.get_kitchen_moved_history(phone_number)

        if not kitchen_moved_history :
            door_closed_history = dbService.get_door_closed_history(phone_number)

            if not door_closed_history:
                return response.OFFER_MEAL_WHEN_NOT_MOVED # 대화 종료 -> 정보 전달

            return response.OFFER_MEAL_WHEN_MOVED # 대화 종료 -> 정보 전달
        return response.NORMAL_STATUS # 대화 종료
