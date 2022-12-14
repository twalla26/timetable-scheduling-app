from flask import Blueprint, jsonify, request, session, g
from werkzeug.security import generate_password_hash, check_password_hash

from datetime import datetime

from how_about_this_time import db
from how_about_this_time.forms import UserCreateForm, UserLoginForm, CheckDupForm
from how_about_this_time.models import User


bp = Blueprint('auth', __name__, url_prefix='/auth')


@bp.route('/signup/', methods=["POST"])
def signup(): # 회원가입 함수
    form = UserCreateForm()
    if request.method == 'POST':
        ip = request.remote_addr
        date = datetime.now()
        user = User.query.filter_by(user_id=form.user_id.data).first() # first(): 첫번째로 매칭되는 인스턴스만 달라
        if not user: # 매칭되는 인스턴스가 없으면 -> 회원가입 진행
            user = User(user_id=form.user_id.data, 
                        password=generate_password_hash(form.password.data), 
                        username=form.username.data, 
                        email=form.email.data) # 새로운 유저 저장
            db.session.add(user) # 디비에 신규 유저 저장
            db.session.commit() # 디비 저장
            print(date)
            print("[로그]회원가입에 성공했습니다. (IP:", str(ip)+")")
            return jsonify({"signup" : "success"})
        else: # 이미 회원가입 되어있는 유저인 경우
            print(date)
            print("[로그]회원가입에 실패했습니다. (IP:", str(ip)+")")
            return jsonify({"signup" : "error: user already exists"})


@bp.route('/login/', methods=["POST"])
def login(): # 로그인 함수
    form = UserLoginForm()
    if request.method == 'POST':
        ip = request.remote_addr
        date = datetime.now()
        user = User.query.filter_by(user_id=form.user_id.data).first()
        if not user: # 가입된 유저가 아닌 경우
            print(date)
            print("[로그]로그인 시도 중 아이디를 잘못 입력했습니다. (IP:", str(ip)+")")
            return jsonify({"error" : "wrong ID"})
        elif not (check_password_hash(user.password, form.password.data)): # 비밀번호가 틀렸을 경우
            print(date)
            print("[로그]로그인 시도 중 비밀번호를 잘못 입력했습니다. (IP:", str(ip)+")")
            return jsonify({"error" : "wrong password"})
        else: # 아이디, 비번 모두 제대로 입력했을 경우
            session.clear() # 세션 초기화한 후
            session['user_id'] = user.id # 세션에 현재 로그인한 유저의 고유 번호 저장
            print(date)
            print("[로그]로그인에 성공했습니다. (IP:", str(ip)+")") # 로그 기록
            return jsonify({"login" : "success"})


@bp.route('/logout/') # 로그아웃 함수
def logout():
    ip = request.remote_addr
    date = datetime.now()
    print(date)
    print('[로그]로그아웃 했습니다. (IP:', str(ip)+")")
    session.clear()
    return jsonify({"logout" : "success"})


@bp.before_app_request # 모든 라우팅 함수가 실행되기 전에 실행되는 함수
def load_logged_in_user(): # 사용자의 로그인 유무를 변수에 저장
    user_id = session.get('user_id') 
    if user_id is None: # 세션이 비었다면 로그아웃 상태
        g.user = None
    else:
        g.user = User.query.filter_by(id=user_id).first()


@bp.route('/check_session/') # 사용자 로그인 유무를 알려줌
def check_session():
    if g.user == None:
        return jsonify({"result" : "logout"})
    else:
        return jsonify({"result" : "login"})


@bp.route('/signup/check_dup/', methods=["POST"])
def check_dup():
    form = CheckDupForm()
    user = User.query.filter_by(user_id=form.user_id.data).first()
    if not user: # 사용자가 입력한 아이디가 디비에 없음 -> 사용 가능
        return jsonify({"_id" : "usable"})
    else: # 디비에 있음 -> 사용 불가능
        return jsonify({"_id" : "unusable"})