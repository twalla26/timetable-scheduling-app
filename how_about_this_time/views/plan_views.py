from flask import Blueprint, jsonify, request, session, g
from werkzeug.security import generate_password_hash, check_password_hash
import uuid, json

from datetime import datetime

from how_about_this_time import db
from how_about_this_time.forms import PlanCreateForm, PlanCodeForm, UserScheduleForm
from how_about_this_time.models import Plan, Schedule, User

bp = Blueprint('plan', __name__, url_prefix='/plan')


@bp.route('/make', methods=['POST'])
def make_plan():
    print(request.headers)
    form = PlanCreateForm()
    if request.method == 'POST':
        ip = request.remote_addr
        date = datetime.now()
        code = uuid.uuid1(node=None, clock_seq=None).hex[:8] # 현재 시각을 기반으로 랜덤 문자열 생성
        overlapped_schedule = []
        if form.duration.data == 'week':
            for i in range(7):
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
            return jsonify({"error" : "wrong code"})
        else:
            print(date)
            print("[로그]코드를 통해 타임테이블에 입장했습니다. (IP:", str(ip)+")")
            return jsonify({"result" : "success", "code" : plan.code, "duration" : plan.duration})


@bp.route('/timetable/<string:code>', methods=['GET', 'POST'])
def timetable(code):
    print(request.headers)
    plan = Plan.query.filter_by(code=code).first()
    if request.method == 'GET': # 해당 타임테이블 정보 요청

        # 입력받은 여러 타임테이블로 총 가중치 계산
        overlapped_schedule = plan.overlapped_schedule
        for schedule in plan.plan_schedule_set:
            schedule = schedule.timetable_array
            #schedule = schedule.replace(" ", "")
            #schedule = schedule[1 : -1].split(',')
            for i in range(30):
                schedule[i] = float(schedule[i])
            for i in range(30):
                overlapped_schedule[i] += schedule[i]
        # 참여한 사용자 리스트
        members = []
        for member in plan.member:
            member = User.query.filter_by(user_id=member.user_id).first()
            member = {"username" : member.username}
            member = json.dumps(member, ensure_ascii=False)
            member = json.loads(member)
            members.append(member)

        return jsonify({"id" : plan.id,
                        "title" : plan.title,
                        "first_day" : plan.first_day,
                        "duration" : plan.duration,
                        "needed_time" : plan.needed_time,
                        "detail" : plan.detail,
                        "overlapped_schedule" : plan.overlapped_schedule,
                        "create_date" : plan.create_date.strftime('%Y년 %m월 %d일 %H:%M'),
                        "member" : members,
                        "member_cnt" : len(members)})

    else: # 타임테이블에 본인 스케쥴 입력
        form = UserScheduleForm()
        ip = request.remote_addr
        date = datetime.now()
        a = form.timetable_array.data
        a = a.replace(" ", "")
        a = a.replace("[", "")
        a = a.replace("]", "")
        a = a.split(",")
        schedule = Schedule(plan_id=plan.id,
                            user_id=g.user.id,
                            timetable_array=a,
                            create_date=datetime.now())
        db.session.add(schedule)
        plan.member.append(g.user)
        db.session.commit()
        print(date)
        print("[로그]유저가 타임테이블에 스케줄을 입력했습니다. (IP:", str(ip)+")")
        return jsonify({"result" : "success", "code" : plan.code})


@bp.route('/api/test/<string:code>')
def test_api(code):
    plan = Plan.query.filter_by(code=code).first()

    for i in plan.plan_schedule_set:
        i = i.timetable_array
        print(type(i), i)
    return "Test"

@bp.route('/timetable/<string:code>/best_time', methods=['GET', 'POST'])
def best_time(code):
    plan = Plan.query.filter_by(code=code).first()
    ''' 입력 1, 5
        {
            [17, 18, 18, 17, 19, 18, 20, 19, 18, 16]

            3
        }
    '''

    ''' 로직
    
    '''

    ''' 출력 3, 15
        {
            {3 : [4]}, {2 : [3, 5]}, {1 : [2, 6]}, {0 : [0, 1, 7]}
        }
    '''
    overlapped_schedule = plan.overlapped_schedule
    if plan.duration == 'week':
        return jsonify({"1st" : "몰라", "2nd" : "몰라용", "3rd" : "아 몰라"})
    else: # 'month'인 경우
        print(request.headers)
        for schedule in plan.plan_schedule_set:
            schedule = schedule.timetable_array
            for i in range(30):
                schedule[i] = float(schedule[i])
            for i in range(30):
                overlapped_schedule[i] += schedule[i]
            
        best_time_dict = {} # {num : [index, ...]}
        needed_time = int(plan.needed_time)
        for i in range(0, (30 - needed_time + 1)): # 한달치 값 불러와서
            num = 0
            for j in range(0, needed_time): # 필요한 날짜만큼 더해주기
                num += overlapped_schedule[i + j]
            if num in best_time_dict:
                best_time_dict[num].append(i)
            else:
                best_time_dict[num] = []
                best_time_dict[num].append(i)

        print(best_time_dict)
        sorted(best_time_dict.items(), reverse=True)
        return jsonify({"1st" : "2022년 1월 5일 ~ 2022년 1월 7일", "2nd" : "2022년 1월 4일 ~ 2022년 1월 6일", "3rd" : "2022년 1월 6일 ~ 2022년 1월 8일"})
        #return jsonify(best_time_dict)



# user's plan에 기간도 넣어서 주기,(제목, 코드, 기간)