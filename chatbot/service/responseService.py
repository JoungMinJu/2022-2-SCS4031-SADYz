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
