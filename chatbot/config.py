
minju_db = {
    "user": "root",
    "password": "alswn12",
    "host": "localhost",
    "port": "3306",
    "database": "minjudb",
}

SQLALCHEMY_TRACK_MODIFICATIONS = False
SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://{aws_db['user']}:{aws_db['password']}@{aws_db['host']}:{aws_db['port']}/{aws_db['database']}?charset=utf8"

SQLALCHEMY_POOL_RECYCLE = 35 # 풀의 자동으로 재활용 되는 시간
SQLALCHEMY_POOL_TIMEOUT = 7 # 풀에 대한 연결 시간 초과를 초 단위로 지정
SQLALCHEMY_PRE_PING = True
SQLALCHEMY_ENGINE_OPTIONS = {'pool_recycle': SQLALCHEMY_POOL_RECYCLE, 'pool_timeout': SQLALCHEMY_POOL_TIMEOUT, 'pool_pre_ping': SQLALCHEMY_PRE_PING}
SQLALCHEMY_TRACK_MODIFICATIONS = False