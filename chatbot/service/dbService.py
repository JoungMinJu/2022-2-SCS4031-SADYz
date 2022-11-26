from controller.dbController import db
from datetime import datetime, timedelta
from pytz import timezone
from model.client import Client, LastMovedTime, DoorClosedTime


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
