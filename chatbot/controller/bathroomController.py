from flask import Blueprint, request, jsonify

from service import responseService, dbService
from util.response import *

bathroom = Blueprint("bathroom", __name__, url_prefix="/bathroom")

@bathroom.post("/start")
def start_bathroom():
    phone_number = request.get_json()["phone_number"]
    client_id = dbService.get_client_id(phone_number)
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return return_answer(NOT_IN_THE_HOUSE, DT_END, -1) # -> -1
    # 화장실 이용 횟수 파악
    bathroom_count = dbService.get_today_bathroom_count(phone_number)
    if bathroom_count < 4 :
        conv_id = dbService.create_conversation(TOO_FEW_VISITS_BATHROOM, client_id)
        return return_answer(TOO_FEW_VISITS_BATHROOM, DT_BATHROOM, conv_id) # 상태 값 추가
    if bathroom_count > 8 :
        conv_id = dbService.create_conversation(TOO_MUCH_VISITS_BATHROOM, client_id)
        return return_answer(TOO_MUCH_VISITS_BATHROOM, DT_BATHROOM_HEALTH, conv_id) # 상태 값 추가
    return return_answer(NO_BATHROOM_PROBLEM, DT_END, -1)

@bathroom.post("/chat")
def bathroom_chat():
    json = request.get_json()
    input = json["input"]
    phone_number = json["phone_number"]
    conv_id = json["conv_id"]
    dialog_type = int(json["dialog_type"])
    input_list = input.split(" ")

    if dialog_type == 3 :
        status = responseService.check_constipation_status(input_list)
        if status == "OK" :
            dbService.update_conversation(conv_id, "", (input+","+ STATUS_IS_OK), "화장실을 너무 적게 감")
            return return_answer(STATUS_IS_OK, DT_END_SPEAK, conv_id)
        if status == "ERROR":
            return return_answer(NOT_UNDERSTAND, DT_BATHROOM, conv_id)
        if status == "NOT_OK":
            dbService.update_conversation(conv_id, "", (input + "," + PROBLEM_IN_CONSTIPATION),"변비 증상")
            return return_answer(PROBLEM_IN_CONSTIPATION, DT_END_SPEAK, conv_id )# 상태 값 추가

    elif dialog_type == 4 :
        status = responseService.check_health_status(input_list)

        if status == "OK" :
            dbService.update_conversation(conv_id, "", (input + "," + STATUS_IS_OK), "화장실을 너무 많이 감")
            return return_answer(STATUS_IS_OK, DT_END_SPEAK, conv_id)
        if status == "ERROR":
            return return_answer(NOT_UNDERSTAND, DT_BATHROOM_HEALTH, conv_id)
        if status == "NOT_OK":
            dbService.update_conversation(conv_id, "", (input + "," + PROBLEM_IN_HEALTH), "건강 이상 호소")
            return return_answer(PROBLEM_IN_HEALTH, DT_END_SPEAK, conv_id) # 상태 값 추가


