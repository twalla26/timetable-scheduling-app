from flask import Blueprint, jsonify, request, session, g
from werkzeug.security import generate_password_hash, check_password_hash
import uuid

from datetime import datetime

from how_about_this_time import db
from how_about_this_time.forms import PlanCreateForm, PlanCodeForm, UserScheduleForm
from how_about_this_time.models import Plan, Schedule

bp = Blueprint('plan', __name__, url_prefix='/plan')

@bp.route('/make', methods=['POST'])
def make_plan():
    form = PlanCreateForm()
    if request.method == 'POST':
        ip = request.remote_addr
        date = datetime.now()
        code = uuid.uuid1(node=None, clock_seq=None).hex # 현재 시각을 기반으로 랜덤 문자열 생성
        overlapped_schedule = []
        if form.duration.data == 'week':
            for i in range(1, 7):
                overlapped_schedule.append([0]*24)
        else:
            overlapped_schedule = [0 for i in range(30)]
        plan = Plan(title=form.title.data, 
                    first_day=form.first_day.data, 
                    duration=form.duration.data, 
                    needed_time=form.needed_time.data,
                    detail=form.detail.data, 
                    code=code,
                    create_date=datetime.now(),
                    overlapped_schedule=overlapped_schedule)
        plan.member.append(g.user)
        db.session.add(plan)
        db.session.commit()
        print(date)
        print("[로그]새로운 약속이 생성되었습니다. (IP:", str(ip)+")")
        return jsonify({"plan making" : "success", "code" : plan.code, "duration" : plan.duration})


@bp.route('/join', methods=['POST'])
def join_plan():
    form = PlanCodeForm()
    if request.method == 'POST':
        ip = request.remote_addr
        date = datetime.now()
        plan = Plan.query.filter_by(code=form.code.data).first()
        if not plan:
            print(date)
            print("[로그]잘못된 코드를 입력했습니다. (IP:", str(ip)+")")
            return jsonify({"result" : "wrong code"})
        else:
            print(date)
            print("[로그]코드를 통해 타임테이블에 입장했습니다. (IP:", str(ip)+")")
            return jsonify({"result" : "success", "code" : plan.code, "duration" : plan.duration})


@bp.route('/timetable/<string:code>', methods=['GET', 'POST'])
def timetable(code):
    plan = Plan.query.filter_by(code=code).first()
    members = []
    if request.method == 'GET':
        for member in plan.user_set:
            members.append(member)
        return jsonify({"id" : plan.id,
                        "title" : plan.title,
                        "first_day" : plan.first_day,
                        "duration" : plan.duration,
                        "needed_time" : plan.needed_time,
                        "detail" : plan.detail,
                        "overlapped_schedule" : plan.overlapped_schedule,
                        "create_date" : plan.create_date.strftime('%Y년 %m월 %d일 %H:%M'),
                        "member" : members})
                        # schedule 배열도 보내줘야함
    else:
        form = UserScheduleForm()
        ip = request.remote_addr
        date = datetime.now()
        schedule = Schedule(plan=plan,
                            user=g.user,
                            timetable_array=form.timetable_array,
                            create_date=datetime.now())
        db.session.add(schedule)
        db.session.commit()
        print(date)
        print("[로그]유저가 타임테이블에 스케줄을 입력했습니다. (IP:", str(ip)+")")
        return jsonify({"result" : "success"})


@bp.route('/timetable/<string:code>/best_time', methods=['GET', 'POST'])
def best_time(code):
    plan = Plan.query.filter_by(code=code).first()
    overlapped_array = plan.overlapped_array
    if plan.duration == 'week':
        return jsonify({"1st" : "몰라", "2nd" : "몰라용", "3rd" : "아 몰라"})
    else: # 'month'인 경우
        best_time_list = {} # {num : [index, ...]}
        for i in range(0, 30):
            for j in range(0, 3):
                num += overlapped_array[i + j]
            for k in best_time_list.keys:
                if num == k:
                    best_time_list.k.append(i)
            best_time_list[num] = []
            best_time_list[num].append(i)
        sorted(best_time_list.items(), reverse=True)
        return jsonify({"result" : best_time_list})



# user's plan에 기간도 넣어서 주기,(제목, 코드, 기간)