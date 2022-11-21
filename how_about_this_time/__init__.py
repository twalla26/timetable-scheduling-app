from flask import Flask
from flask_migrate import Migrate # 파이썬 모델을 이용해 테이블을 생성하고 컬럼을 추가하는 등의 작업을 해줌.
from flask_sqlalchemy import SQLAlchemy # 플라스크 ORM 라이브러리 중 가장 많이 사용되는 라이브러리.
from sqlalchemy import MetaData # SQLite와 ORM을 함께 사용할 때 일어날 수 있는 문제점을 해결하기 위함.


import config # import.py파일을 import


naming_convention = { # SQLite와 ORM을 함께 사용할 때 일어날 수 있는 문제점을 해결하기 위함.
    "ix": 'ix_%(column_0_label)s',
    "uq": "uq_%(table_name)s_%(column_0_name)s",
    "ck": "ck_%(table_name)s_%(column_0_name)s",
    "fk": "fk_%(table_name)s_%(column_0_name)s_%(referred_table_name)s",
    "pk": "pk_%(table_name)s"
}


db = SQLAlchemy(metadata=MetaData(naming_convention=naming_convention)) # SQLAlchemy 모듈을 통해 db 생성 + (SQLite + ORM) 오류 해결
migrate = Migrate() # Migrate 모듈을 통해 migrate 생성
# 전역변수로 db와 migrate 객체 생성 -> create_app 함수 안에서 app에 등록될 것.
# 객체를 함수 밖에 생성하여, 블루프린트와 같은 다른 모듈에서도 사용할 수 있게 함.

def create_app(): # 애플리케이션 팩토리 -> 쉽게 말해 app 객체를 생성하여 반환하는 함수

    app = Flask(__name__) # 플라스크 애플리케이션을 생성하는 코드
    # __name__이라는 변수는 플라스크 내장변수로서 모듈명이 담김 -> 이 코드를 실행하면 how_about_this_time.py라는 모듈이 실행되는 것 -> __name__ 변수에는 "how_about_this_time"라는 문자열이 담김
    app.config.from_object(config) # config.py 파일에 작성된 항목을 읽기위한 코드

    # ORM
    db.init_app(app)
    if app.config['SQLALCHEMY_DATABASE_URI'].startswith("sqlite"): # SQLite와 ORM을 함께 사용할 때 일어날 수 있는 문제점을 해결하기 위한 코드
        migrate.init_app(app, db, render_as_batch=True)
    else:
        migrate.init_app(app, db)
    # init_app 메서드를 통해 app에 db와 migrate가 등록됨.
    from . import models # 걑은 경로의 models.py import
    # models.py 파일에 작성한 모델들을 플라스크의 migrate 기능이 인식하게 하기 위함.

    # 블루프린트
    from .views import main_views # 같은 경로에 있는 views폴더에서 main_views를 import
    app.register_blueprint(main_views.bp) # main_views.py에 있던 블루프린트 객체 bp를 플라스크 앱에 등록.

    return app