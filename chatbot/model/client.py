from controller.dbController import db

class Client(db.Model):
    __tablename__ = 'client'
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True, unique=True, autoincrement=True)
    stay = db.Column(db.Boolean)
    response = db.Column(db.Boolean)
    phonenumber = db.Column(db.String(50))

    def __init__(self, id, stay, response, phonenumber):
        self.id = id
        self.stay = stay
        self.response = response
        self.phonenumber = phonenumber

class LastMovedTime(db.Model):
    __tablename__ = "last_moved_time"
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True, unique=True, autoincrement=True)
    last_moved_time = db.Column(db.DateTime)
    client_id = db.Column(db.Integer)
    location = db.Column(db.String(50))

    def __init__(self, last_moved_time, client_id, location):
        self.last_moved_time = last_moved_time
        self.client_id = client_id
        self.location = location

class DoorClosedTime(db.Model):
    __tablename__ = "door_closed_time"
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True, unique=True, autoincrement=True)
    door_closed_time = db.Column(db.DateTime)
    is_out = db.Column(db.Boolean)
    phonenumber = db.Column(db.String(50))
    client_id = db.Column(db.Integer)

    def __init__(self, door_closed_time, is_out, phonenumber, client_id):
        self.door_closed_time = door_closed_time
        self.is_out = is_out
        self.phonenumber = phonenumber
        self.client_id = client_id

class Conversation(db.Model):
    __tablename__ = "conversation"
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True, unique=True, autoincrement=True)
    emotion = db.Column(db.String(2000))
    full_text = db.Column(db.String(2000))
    problem = db.Column(db.String(50))
    client_id = db.Column(db.Integer)
    created_date_time = db.Column(db.DateTime)
    modified_date_time = db.Column(db.DateTime)

    def __init__(self, emotion, full_text, problem, client_id, created_date_time, modified_date_time):
        self.emotion = emotion
        self.full_text = full_text
        self.problem = problem
        self.created_date_time = created_date_time
        self.modified_date_time = modified_date_time
        self.client_id = client_id