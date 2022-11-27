from service import koelectra_qa

def is_positive(input):
    positive_answer = ["네", "넵", "응", "어", "맞아", "엉", "웅", "옹", "그래"]
    for p in positive_answer:
        if p in input:
            return True
    return False


def is_positive_meal(input):
    positive_meal_answer = ["먹었어", "먹었습니다"]
    for pm in positive_meal_answer:
        if pm in input:
            return True
    return False


def is_negative(input):
    negative_answer = ["아니요", "아니오", "아뇨", "아니", "아닝", "안"]
    for n in negative_answer:
        if n in input:
            return True
    return False


def is_negative_meal(input):
    negative_meal_answer = ["안먹었어", "안먹었습니다"]
    for nm in negative_meal_answer:
        if nm in input:
            return True
    return False


def check_eating_status(input):
    positive = is_positive(input)
    positive_meal = is_positive_meal(input)
    negative = is_negative(input)
    negative_meal = is_negative_meal(input)

    if (positive or positive_meal) and not negative and not negative_meal:
        return "YES"
    elif negative or negative_meal:
        return "NO"
    return "ERROR"

def is_positive_constipation(input):
    result_category = koelectra_qa.get_result(" ".join(input))
    return result_category == "증상/소화불량"

def is_negative_constipation(input):
    negative_constipation_answer = [ "괜찮아", "괜찮아요"]
    for nc in negative_constipation_answer:
        if nc in input:
            return True
    return False

def check_constipation_status(input) :
    positive = is_positive(input)
    positive_constipation = is_positive_constipation(input)
    negative = is_negative(input)
    negative_constipation = is_positive_constipation(input)

    if positive or positive_constipation :
        return "NOT_OK"
    if negative or negative_constipation :
        return "OK"
    return "ERROR"


def is_negative_health(input) :
    result_category = koelectra_qa.get_result(" ".join(input)).split("/")
    return result_category[0] == "증상"

def is_positive_health(input):
    positive_health_answer = [ "괜찮아", "괜찮아요"]
    for pa in positive_health_answer:
        if pa in input:
            return True
    return False



def check_health_status(input):
    positive = is_positive(input)
    positive_problem= is_negative_health(input)
    negative = is_negative(input)
    negative_problem = is_positive_health(input)

    if positive or positive_problem :
        return "NOT_OK"
    if negative or negative_problem :
        return "OK"
    return "ERROR"