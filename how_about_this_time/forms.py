from flask_wtf import FlaskForm
from wtforms import StringField, TextAreaField, PasswordField, EmailField, IntegerField, FieldList

class UserCreateForm(FlaskForm): # 회원가입 폼
    user_id = StringField()
    password = PasswordField()
    username = StringField()
    email = EmailField()

class UserLoginForm(FlaskForm): # 로그인 폼
    user_id = StringField()
    password = PasswordField()

class CheckDupForm(FlaskForm): # 아이디 중복확인 폼
    user_id = StringField()

class PlanCreateForm(FlaskForm): # 약속 생성 폼
    title = StringField()
    first_day = IntegerField()
    duration = StringField()
    needed_time = StringField()
    detail = TextAreaField()

class PlanCodeForm(FlaskForm): # 코드 전송 폼
    code = StringField()

class UserScheduleForm(FlaskForm): # 스케쥴 입력 폼
     timetable_array = StringField() # 이걸로 한번 해보장
