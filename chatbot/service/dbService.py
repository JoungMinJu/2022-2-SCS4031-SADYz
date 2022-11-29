from controller.dbController import db
from datetime import datetime, timedelta, date
from pytz import timezone
from model.client import Client, LastMovedTime, DoorClosedTime, Conversation
from sqlalchemy import func
from collections import Counter

KST = timezone('Asia/Seoul')


def is_client_at_home(phone_number):
    return db.session.query(Client).filter_by(phonenumber=phone_number).first().stay


def get_door_closed_history(phone_number):
    current_time = datetime.now(KST)
    before_two_hours = current_time - timedelta(hours=2)

    return db.session.query(DoorClosedTime).filter_by(
        phonenumber=phone_number).filter(
        DoorClosedTime.door_closed_time > before_two_hours).all()


def get_kitchen_moved_history(phone_number):
    current_time = datetime.now(KST)
    before_two_hours = current_time - timedelta(hours=2)
    client = get_client(phone_number)

    return db.session.query(LastMovedTime).filter_by(
        client_id=client.id).filter(
        LastMovedTime.last_moved_time > before_two_hours).filter(
        LastMovedTime.location == "kitchen").all()


def get_client(phone_number):
    return db.session.query(Client).filter_by(phonenumber=phone_number).first()

def get_client_id(phone_number):
    return db.session.query(Client).filter_by(phonenumber=phone_number).first().id

def get_today_bathroom_count(phone_number):
    client = get_client(phone_number)
    return len(db.session.query(LastMovedTime).filter_by(client_id=client.id).filter(
        func.date(LastMovedTime.last_moved_time) == date.today()
    ).filter(LastMovedTime.location == "bathroom").all())

def create_conversation(full_text, client_id):
    conversation = Conversation("", full_text, "", client_id, datetime.now(KST),datetime.now(KST))
    db.session.add(conversation)
    db.session.commit()
    return conversation.id

def read_conversation(conv_id):
    return db.session.query(Conversation).filter_by(id = conv_id).first()

def update_conversation(conv_id, emotion, full_text, problem):
    conv = read_conversation(conv_id)
    conv.emotion = conv.emotion + "," + emotion
    conv.full_text = conv.full_text + "," + full_text
    conv.problem = problem
    conv.modified_date_time = datetime.now(KST)
    db.session.commit()

def update_conversation_emotion(conv_id):
    conv = db.session.query(Conversation).filter_by(id=conv_id).first()
    emotion_split = conv.emotion.rstrip(",").split(",")
    data = Counter(emotion_split)
    conv.emotion = data.most_common(1)[0][0]
    conv.modified_date_time = datetime.now(KST)
    db.session.commit()


def change_response_status(phone_number, status):
    client = db.session.query(Client).filter(Client.phonenumber == phone_number).update({"response":status})
    db.session.commit()