import os
# os(Operating System)은 운영체제에서 제공되는 여러가지 기능을 파이썬에서 수행할 수 있게 해줌.

BASE_DIR = os.path.dirname(__file__) # BASE_DIR에 현재 파일의 디렉터리 주소 저장
# __file__은 파이썬 내장변수로서, 현재 수행하고 있는 코드를 담고있는 파일이 위치한 경로를 반환함.

SQLALCHEMY_DATABASE_URI = 'sqlite:///{}'.format(os.path.join(BASE_DIR, 'how_about_this_time.db'))
# SQLALCHEMY_DATABASE_URI에는 데이터베이스 접속 주소가 저장됨.
# os.path.join: 인수에 전달된 2개의 문자열을 결합하여, 1개의 경로로 만들어줌.
# SQLALCHEMY_DATABASE_URI 설정으로 SQLite 데이터베이스가 사용되고 데이터베이스 파일은 앞서 언급한 BASE_DIR 바로 밑에 how_about_this_time.db파일로 저장됨.
# SQLite는 파이썬 기본 패키지에 포함된 데이터베이스로, 주로 소규모 프로젝트에서 사용하는 가벼운 파일을 기반으로 한 데이터베이스다.

SQLALCHEMY_TRACK_MODIFICATIONS = False
# SQLALCHEMY_TRACK_MODIFICATIONS는 수정사항을 추적하고 신호를 내보내는 기능이나, 추가적인 메모리를 필요로 하고 현 프로젝트에서 불필요한 기능이니 비활성화.

SECRET_KEY = "dev"
# SECRET_KEY는 CSRF(cross-site request forgery)라는 웹 사이트 취약점 공격을 방지하는 데 사용됨.
# CSRF: 사용자의 요청을 위조하는 웹 사이트 공격 기법, SECRET_KEY를 기반으로 생성되는 CSRF 토큰은 폼으로 전송된 데이터가 실제 웹 페이지에서 작성된 데이터인지를 판단해 주는 가늠자 역할을 함