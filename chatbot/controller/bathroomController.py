from flask import Blueprint, request, jsonify

from service import responseService, dbService
from util import response

bathroom = Blueprint("bathroom", __name__, url_prefix="/bathroom")


@bathroom.post("/start")
def start_bathroom():
    phone_number = request.values.get("phone_number")
    stay = dbService.is_client_at_home(phone_number)
    if not stay:
        return response.NOT_IN_THE_HOUSE
    # 화장실 이용 횟수 파악
    bathroom_count = dbService.get_today_bathroom_count(phone_number)
    print(bathroom_count)
    if bathroom_count < 4 :
        return response.TOO_FEW_VISITS_BATHROOM # 상태 값 추가
    if bathroom_count > 8 :
        return response.TOO_MUCH_VISITS_BATHROOM # 상태 값 추가
    return response.NO_BATHROOM_PROBLEM

@bathroom.post("/chat")
def bathroom_chat():
    input = request.values.get("input")
    phone_number = request.values.get("phone_number")
    dialog_type = int(request.values.get("dialog_type"))
    input_list = input.split(" ")
    if dialog_type == 1 :
        status = responseService.check_constipation_status(input_list)

        if status == "OK" :
            return response.STATUS_IS_OK
        if status == "ERROR":
            return response.NOT_UNDERSTAND_CONSTIPATION
        if status == "NOT_OK":
            return response.PROBLEM_IN_CONSTIPATION # 상태 값 추가
    elif dialog_type == 2 :
        status = responseService.check_health_status(input_list)

        if status == "OK" :
            return response.STATUS_IS_OK
        if status == "ERROR":
            return response.NOT_UNDERSTAND_HEALTH
        if status == "NOT_OK":
            return response.PROBLEM_IN_HEALTH # 상태 값 추가


