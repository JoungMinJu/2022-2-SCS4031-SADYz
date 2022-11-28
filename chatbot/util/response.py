NOT_IN_THE_HOUSE = {"answer": "외출하셨습니다.", "dialog_type": 0}
NO_BATHROOM_PROBLEM = {"answer": "문제가 없으세요.", "dialog_type": 0}
NOT_UNDERSTAND = {"answer": "잘 알아듣지 못했어요. 다시 말씀해주세요", "dialog_type": 1}
NOT_UNDERSTAND_CONSTIPATION = {"answer": "잘 알아듣지 못했어요. 다시 말씀해주세요", "dialog_type": 1}
NOT_UNDERSTAND_HEALTH = {"answer": "잘 알아듣지 못했어요. 다시 말씀해주세요", "dialog_type": 2}

# 식사
MEAL_STATUS_QUESTION = {"answer": "안녕하세요. 식사는 잘 하셨나요?", "dialog_type": 1}
OFFER_MEAL = {"answer": "식사를 제때 챙겨드시는게 제일 중요해요. 귀찮으시더라도 꼭 식사 하시길 권해드릴게요", "dialog_type": 0}
OFFER_MEAL_WHEN_NOT_MOVED = {"answer": "주방에 움직임이 감지되지 않았어요. 혹시 식사를 챙겨드시지 않으셨다면 꼭 챙겨드시길 바랄게요. 건강을 지키는 것이 가장 중요해요",
                             "dialog_type": 0}
OFFER_MEAL_WHEN_MOVED = {"answer": "외출 기록이 있으시네요. 외식을 하셨나봐요. 혹시 그것이 아니라면 꼭 식사를 챙겨드시길 바랄게요. 건강을 지키는 것이 가장 중요해요",
                         "dialog_type": 0}
NORMAL_STATUS = {"answer": "잘하셨어요! 식사를 잘 챙겨드시는게 가장 중요해요", "dialog_type": 0}

# 건강
TOO_FEW_VISITS_BATHROOM = {"answer": "화장실 가신 횟수가 매우 작아요. 혹시 변비 증상이 있으신가요?", "dialog_type": 1}
TOO_MUCH_VISITS_BATHROOM = {"answer": "화장실을 자주 가신 것을 확인돼요. 혹시 몸이 아프신가요?", "dialog_type": 2}
STATUS_IS_OK = {"answer": "괜찮으시다니 다행이에요. 문제가 있으시면 응급콜을 눌러주세요", "dialog_type": 0}
PROBLEM_IN_CONSTIPATION = {"answer": "문제가 있으시군요. 관련 내용 담당자에게 전달 드릴게요. 건강한 식습관이 우선이니 잘 챙겨드시길 바랄게요", "dialog_type": 0}
PROBLEM_IN_HEALTH = {"answer": "문제가 있으시군요. 관련 내용 담당자에게 전달 드릴게요. 위급한 상황이면 응급콜을 눌러주세요.", "dialog_type": 0}

# 안부
IS_QUIT = "대화를 더 진행하시려면 말씀해주시고, 아니면 종료를 말씀해주세요"
QUIT = {"answer" : "종료하셨습니다.", "dialog_type" : 0}



def call_to_say_hi(question):
    return {"answer": question, "dialog_type": 1}


def return_answer(answer):
    return {"answer": answer + IS_QUIT, "dialog_type": 2}

