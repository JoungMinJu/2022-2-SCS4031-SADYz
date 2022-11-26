from flask import Blueprint

emotion = Blueprint("emotion", __name__, url_prefix="/emotion")

# @emotion.post("/start")
# def start_emotion():
#     # RDS에서 정보 찾기
