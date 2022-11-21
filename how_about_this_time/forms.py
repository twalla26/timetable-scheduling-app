from flask_wtf import FlaskForm
from wtforms import StringField, TextAreaField, PasswordField, EmailField

class UserCreateForm(FlaskForm):
    username = StringField()
    user_id = StringField()
    password1 = PasswordField()
    password2 = PasswordField()
    email = EmailField()

class UserLoginForm(FlaskForm):
    user_id = StringField()
    password = PasswordField()

class CheckDupForm(FlaskForm):
    user_id = StringField()