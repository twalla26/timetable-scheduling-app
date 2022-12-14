from how_about_this_time import db # __init__.py 파일에서 db객체 import
from sqlalchemy.ext.mutable import MutableList
from sqlalchemy import PickleType


plan_member = db.Table( # ManyToMany 관계를 적용하기 위해서는 db.Table을 통해 N:N 관계를 의미하는 테이블을 먼저 생성
    # question_voter는 사용자 id와 질문 id를 쌍으로 갖는 테이블 객체
    'plan_member', 
    db.Column('user_id', db.Integer, db.ForeignKey('user.id', ondelete='CASCADE'), primary_key=True),
    db.Column('plan_id', db.Integer, db.ForeignKey('plan.id', ondelete='CASCADE'), primary_key=True)
    # 사용자 id와 질문 id가 모두 PK(primary_key)이므로 ManyToMany관계가 성립된다.
)


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.String(150), unique=True, nullable=False)
    password = db.Column(db.String(200), nullable=False)
    username = db.Column(db.String(150), nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)


class Plan(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(150), nullable=False)
    first_day = db.Column(db.String(150), nullable=False)
    duration = db.Column(db.String(150), nullable=False)
    needed_time = db.Column(db.String(150), nullable=False)
    detail = db.Column(db.Text(), nullable=True)
    code = db.Column(db.String(150), unique = True, nullable=False)
    create_date = db.Column(db.DateTime(), nullable=False)
    member = db.relationship('User', secondary=plan_member, backref=db.backref('plan_set'))

    overlapped_schedule = db.Column(MutableList.as_mutable(PickleType), default=[])
    best_time = db.Column(db.Text(), nullable=True)


class Schedule(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    timetable_array = db.Column(MutableList.as_mutable(PickleType), default=[])
    create_date = db.Column(db.DateTime(), nullable=False)
    modify_date = db.Column(db.DateTime(), nullable=True)

    user_id = db.Column(db.Integer, db.ForeignKey('user.id', ondelete='CASCADE'), nullable=False) # 작성자 고유 아이디
    user = db.relationship('User', backref=db.backref('my_schedule_set')) # 작성자 모델 참조, 역참조 설정

    plan_id = db.Column(db.Integer, db.ForeignKey('plan.id', ondelete='CASCADE'), nullable=False)
    plan = db.relationship('Plan', backref=db.backref('plan_schedule_set'))

