from flask import Blueprint, request, jsonify

from service import responseService, dbService
from util.response import *

bathroom = Blueprint("bathroom", __name__, url_prefix="/bathroom")


@bathroom.post("/start")
def start_bathroom():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END) # -> -1
    # 화장실 이용 횟수 파악
    bathroom_count = dbService.get_today_bathroom_count(phone_number)
    print(bathroom_count)
    if bathroom_count < 4 :
        return return_answer(TOO_FEW_VISITS_BATHROOM, DT_BATHROOM) # 상태 값 추가
    if bathroom_count > 8 :
        return return_answer(TOO_MUCH_VISITS_BATHROOM, DT_BATHROOM_HEALTH) # 상태 값 추가
    return return_answer(NO_BATHROOM_PROBLEM, DT_END)

@bathroom.post("/chat")
def bathroom_chat():
    input = request.values.get("input")
    phone_number = request.values.get("phone_number")
    dialog_type = int(request.values.get("dialog_type"))
    input_list = input.split(" ")
    if dialog_type == 3 :
        status = responseService.check_constipation_status(input_list)

        if status == "OK" :
            return return_answer(STATUS_IS_OK, DT_END_SPEAK)
        if status == "ERROR":
            return return_answer(NOT_UNDERSTAND, DT_BATHROOM)
        if status == "NOT_OK":
            return return_answer(PROBLEM_IN_CONSTIPATION, DT_END) # 상태 값 추가

    elif dialog_type == 4 :
        status = responseService.check_health_status(input_list)

        if status == "OK" :
            return return_answer(STATUS_IS_OK, DT_END_SPEAK)
        if status == "ERROR":
            return return_answer(NOT_UNDERSTAND, DT_BATHROOM_HEALTH)
        if status == "NOT_OK":
            return return_answer(PROBLEM_IN_HEALTH, DT_END_SPEAK) # 상태 값 추가


