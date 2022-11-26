from controller.dbController import db

class Client(db.Model):
    __tablename__ = 'client'
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True)
    stay = db.Column(db.Boolean)
    phonenumber = db.Column(db.String(50))

    def __init__(self, id, stay, phonenumber):
        self.id = id
        self.stay = stay
        self.phonenumber = phonenumber

class LastMovedTime(db.Model):
    __tablename__ = "last_moved_time"
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True)
    last_moved_time = db.Column(db.DateTime)
    client_id = db.Column(db.Integer)
    location = db.Column(db.String(50))

    def __init__(self, id, last_moved_time, client_id, location):
        self.id = id
        self.last_moved_time = last_moved_time
        self.client_id = client_id
        self.location = location

class DoorClosedTime(db.Model):
    __tablename__ = "door_closed_time"
    __table_args__ = {'mysql_collate':'utf8_general_ci'}

    id = db.Column(db.Integer, primary_key = True)
    door_closed_time = db.Column(db.DateTime)
    is_out = db.Column(db.Boolean)
    phonenumber = db.Column(db.String(50))
    client_id = db.Column(db.Integer)

    def __init__(self, id, door_closed_time, is_out, phonenumber, client_id):
        self.id = id
        self.door_closed_time = door_closed_time
        self.is_out = is_out
        self.phonenumber = phonenumber
        self.client_id = client_id