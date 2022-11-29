from flask import Flask
import config
from controller.dbController import db
from controller.mealController import meal
from controller.bathroomController import bathroom
from controller.emotionController import  emotion

app = Flask(__name__)


def create_app(test_config = None):
    if test_config is None:
        app.config.from_object(config)
    else:
        app.config.update(test_config)

    db.init_app(app)

if __name__ == '__main__':
    create_app()
    app.register_blueprint(meal)
    app.register_blueprint(bathroom)
    app.register_blueprint(emotion)
    app.run(host='0.0.0.0', debug = True)