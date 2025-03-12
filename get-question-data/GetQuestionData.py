import requests
import pymysql

# 获取课程、章节、小节只需要一个人的账号调用同一个接口，课程id从school_question_data中获取
# 将这些获取到的数据存储在exam_system中，
# 然后再根据传入的小节去获取问题和答案的数据

port = 3306
host = 'mysql-school-question'
sqlUser = 'smileshark'
password = 'smileshark123456'
# port = 3306
# host = 'localhost'
# sqlUser = 'root'
# password = '123456'

# 从school_question_data数据库的user表中获取用户并登录得到token
# 登陆位置
logUrl = 'https://ai.cqzuxia.com/connect/token'
# 课程细节
courseUrl = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetCourseInfoByCourseId'
# 问题通过小节
questionUrl = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetMistakeQuestion'


def get_headers(token):
    # 设置验证的头部
    return {
        'Accept': 'application/json, text/plain, */*',
        'Accept-Encoding': 'gzip, deflate, br, zstd',
        'Accept-Language': 'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
        'Authorization': token['token_type'] + ' ' + token['access_token'],
        'Priority': 'u=1, i',
        'Referer': 'https://ai.cqzuxia.com/',
        'Sec-Ch-Ua': '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
        'Sec-Ch-Ua-Mobile': '?0',
        'Sec-Ch-Ua-Platform': '"Windows"',
        'Sec-Fetch-Dest': 'empty',
        'Sec-Fetch-Mode': 'cors',
        'Sec-Fetch-Site': 'same-origin',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'
    }


# 上一步执行完后可以开始从数据库中获取小节的id
def get_subsection_id():
    db = pymysql.connect(
        port=port,
        host=host,
        user=sqlUser,
        password=password,
        database='exam_system'
    )
    cursor = db.cursor()
    sql = "select subsection_id from subsection"
    cursor.execute(sql)
    subsectionList = cursor.fetchall()
    db.close()
    return subsectionList


# 根据获取到的小节去获取问题和答案
def save_question_answer(token, subsectionId):
    resp = requests.get(questionUrl, params={'knowledgeId': subsectionId}, headers=get_headers(token))
    student_question = resp.json()
    resp.close()
    if student_question['code'] != -1:
        print("存储问题", end=' ')
        db = pymysql.connect(
            port=port,
            host=host,
            user=sqlUser,
            password=password,
            database='exam_system',
            autocommit=True
        )
        cursor = db.cursor()
        for question in student_question['data']:
            # 先判断是否答对了
            if not question['hasTrue']:
                break
            select_question_sql = "select * from question where question_id='{}'".format(question['questionID'])
            # 先判断数据库中是否有该问题
            cursor.execute(select_question_sql)
            result = cursor.fetchall()
            if not result:
                question_sql = "insert into question (question_id, subsection_id, question, question_type) VALUES ('{}','{}','{}','{}')".format(
                    question['questionID'], subsectionId,
                    question['questionTitle'].replace('\\', '\\\\').replace("'", "\\'"), question['questionType']
                )
                print('\n' + question_sql)
                cursor.execute(question_sql)
                if question['questionType'] == 1:
                    studentAnswerList = question['studentAnswer'].split(",")
                    for answer in question['answerList']:
                        isTrue = False
                        for studentAnswerId in studentAnswerList:
                            if studentAnswerId == answer['id']:
                                isTrue = True
                                break
                        answer_sql = "insert into answer (answer_id, question_id, answer, is_true) VALUES ('{}','{}','{}',{})".format(
                            answer['id'], question['questionID'],
                            answer['oppentionContent'].replace('\\', '\\\\').replace("'", "\\'"), isTrue
                        )
                        print('\n' + answer_sql)
                        cursor.execute(answer_sql)
                else:
                    studentAnswer = question['studentAnswer']
                    for answer in question['answerList']:
                        isTrue = studentAnswer == answer['id']
                        answer_sql = "insert into answer (answer_id, question_id, answer, is_true) VALUES ('{}','{}','{}',{})".format(
                            answer['id'], question['questionID'],
                            answer['oppentionContent'].replace('\\', '\\\\').replace("'", "\\'"), isTrue
                        )
                        print('\n' + answer_sql)
                        cursor.execute(answer_sql)
        cursor.close()
        db.close()
    else:
        print("用户没有该答案", end=" ")


# 根据课程id和用户token获取课程数据，并存入数据库中
def save_course_data(token, courseId):
    resp = requests.get(courseUrl, params={"CourseID": courseId}, headers=get_headers(token))
    course = resp.json()
    resp.close()
    db = pymysql.connect(
        port=port,
        host=host,
        user=sqlUser,
        password=password,
        database='exam_system',
        autocommit=True
    )
    cursor = db.cursor()
    sql = (
        "insert into course (course_id,course_name,course_image,course_describe) values ('{}','{}','https://ai.cqzuxia.com/oss/api/ImageViewer/{}.jpg?percent=1&quality=100','{}')"
        .format(course['data']['id'], course['data']['courseName'], course['data']['pic'], course['data']['overview']))
    cursor.execute(sql)
    # 循环存储章节数据
    for chapter in course['data']['chapters']:
        chapter_sql = (
            "insert into chapter (chapter_id, course_id, chapter_title, chapter_name,`order`) VALUES ('{}','{}','{}','{}',{})"
            .format(chapter['id'], course['data']['id'], chapter['title'], chapter['titleContent'], chapter['order']))
        cursor.execute(chapter_sql)
        # 循环存储小节数据
        for subsection in chapter['knowledgeList']:
            subsection_sql = "insert into subsection (subsection_id, chapter_id, subsection_name) VALUES ('{}','{}','{}')".format(
                subsection['id'], chapter['id'], subsection['knowledge']
            )
            cursor.execute(subsection_sql)
    cursor.close()
    db.close()


# 从数据库中获取用户账号和密码
def get_user_info():
    db = pymysql.connect(
        port=port,
        host=host,
        user=sqlUser,
        password=password,
        database='school_question_data'
    )
    cursor = db.cursor()
    sql = "select user_id,user_password from user"
    cursor.execute(sql)
    results = cursor.fetchall()
    db.close()
    return results


# 从数据库中获取课程id
def get_course_id():
    db = pymysql.connect(
        port=port,
        host=host,
        user=sqlUser,
        password=password,
        database='school_question_data'
    )
    cursor = db.cursor()
    sql = "select course_id from course"
    cursor.execute(sql)
    results = cursor.fetchall()
    db.close()
    return results


# 获取登录数据
def get_token(userId, userPassword):
    data = {
        'username': userId,  # 账号
        'password': userPassword,  # 密码
        'code': '2341',
        'vid': '',
        'client_id': '43215cdff2d5407f8af074d2d7e589ee',
        'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
        'grant_type': 'password',
        'tenant_id': '32'
    }
    resp = requests.post(logUrl, data=data)
    token = resp.json()
    resp.close()
    try:
        if token['token_type']:
            return token
        else:
            print('登录失败')
            return None
    except:
        print('登录失败')
        return None


# 获取课程数据
if __name__ == '__main__':
    user_info = get_user_info()
    courseList = get_course_id()
    # 得到第一个用户的登录信息
    # token = get_token(user_info[0][0], user_info[0][1])
    # for courseId in courseList:
    #     save_course_data(token, courseId)

    subsectionIdList = get_subsection_id()
    # 获取所有用户的问题
    for user in user_info:
        token = None
        try:
            token = get_token(user[0], user[1])
            var = token['token_type']
        except Exception as e:
            print(e)
            print("用户{}登录失败".format(user[0]))
            continue

        for subsectionId in subsectionIdList:
            try:
                save_question_answer(token, subsectionId[0])
            except Exception as e:
                print("用户{}小节{}获取失败".format(user[0], subsectionId[0]))
                print(e)
