from flask import Blueprint, request, jsonify

from service import responseService, dbService
from util import response

toilet = Blueprint("toilet", __name__, url_prefix="/toilet")


@toilet.post("/start")
def start_toilet():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return response.NOT_IN_THE_HOUSE
    # 화장실 이용 횟수 파악
    toilet_count = dbService.get_today_toilet_count(phone_number)
    print(toilet_count)
    if toilet_count < 4 :
        return response.TOO_FEW_VISITS_TOILET
    if toilet_count > 8 :
        return response.TOO_MUCH_VISITS_TOILET
    return response.NO_TOILET_PROBLEM

@toilet.post("/chat")
def toilet_chat():
    input = request.values.get("input")
    phone_number = request.values.get("phone_number")
    dialog_type = int(request.values.get("dialog_type"))
    input_list = input.split(" ")
    if dialog_type == 1 :
        status = responseService.check_constipation_status(input_list)

        if status == "OK" :
            return response.STATUS_IS_OK # 대화 종료 -> 증상 전달
        if status == "ERROR":
            return response.NOT_UNDERSTAND_CONSTIPATION
        if status == "NOT_OK":
            return response.PROBLEM_IN_CONSTIPATION
    elif dialog_type == 2 :
        status = responseService.check_health_status(input_list)

        if status == "OK" :
            return response.STATUS_IS_OK # 대화 종료 -> 증상 전달
        if status == "ERROR":
            return response.NOT_UNDERSTAND_HEALTH
        if status == "NOT_OK":
            return response.PROBLEM_IN_HEALTH


