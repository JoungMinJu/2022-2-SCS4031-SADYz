aws_db = {
    "user": "admin",
    "password" : "sadyz1234!",
    "host" : "sadyz-database.cskg3bhzvpnw.ap-northeast-2.rds.amazonaws.com",
    "port" : "3306",
    "database" : "sadyz_database",
}

minju_db = {
    "user": "root",
    "password" : "alswn12",
    "host" : "localhost",
    "port" : "3306",
    "database" : "minjudb",
}

SQLALCHEMY_TRACK_MODIFICATIONS = False
SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://{minju_db['user']}:{minju_db['password']}@{minju_db['host']}:{minju_db['port']}/{minju_db['database']}?charset=utf8"