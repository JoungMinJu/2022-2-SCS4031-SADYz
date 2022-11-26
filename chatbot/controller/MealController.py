from flask import Blueprint, request, jsonify
from dbController import db
from model.Client import Client, LastMovedTime

meal = Blueprint("meal", __name__, url_prefix="/meal")

@meal.post("/start")
def start_emotion():
    phone_number = request.values.get("phone_number")
    # 사람 찾기
    client = db.session.query(Client).filter_by(phonenumber = phone_number).first()
    # 외출 여부 파악
    stay = client.stay
    if not stay:
        return {"answer" : "외출하셨습니다."}
    return {"answer" : "안녕하세요. 식사는 잘 하셨나요?",
                "dialog_type" : 1}



