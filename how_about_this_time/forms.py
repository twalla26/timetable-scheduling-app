from flask_wtf import FlaskForm
from wtforms import StringField, TextAreaField, PasswordField, EmailField, IntegerField

class UserCreateForm(FlaskForm):
    user_id = StringField()
    password = PasswordField()
    username = StringField()
    email = EmailField()

class UserLoginForm(FlaskForm):
    user_id = StringField()
    password = PasswordField()

class CheckDupForm(FlaskForm):
    user_id = StringField()

class PlanCreateForm(FlaskForm):
    title = StringField()
    first_day = IntegerField()
    duration = StringField()
    needed_time = StringField()
    detail = TextAreaField()

class PlanCodeForm(FlaskForm):
    code = StringField()

class UserScheduleForm(FlaskForm):
    timetable_array = TextAreaField()
