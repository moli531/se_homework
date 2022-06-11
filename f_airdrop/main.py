# -*- coding:utf-8 -*-
from flask import *
import json, os, datetime, random
import shutil
import tkinter as tk  # 使用Tkinter前需要先导入
import _thread
import time
import tkinter.messagebox
import webbrowser

app = Flask(__name__)
app.config.from_object(__name__)
time.sleep(1)
webbrowser.open("http://127.0.0.1:5000")


def GetNameByEveryDir(file_dir):
    FileNameWithPath = []
    FileName = []
    FileDir = []
    for root, dirs, files in os.walk(file_dir):
        for file in files:
            FileNameWithPath.append(os.path.join(root, file))  # 保存路径
            FileName.append(file)  # 保存名称
            FileDir.append(root[len(file_dir):])  # 保存所在文件夹
    return FileName, FileNameWithPath, FileDir

# 首页
@app.route('/')
def index():
    # 获取所有的本机IPv4地址列表
    import psutil
    from socket import AddressFamily
    local_address = []
    for name , info in psutil.net_if_addrs().items():
        for addr in info:
            #只放入IPv4的地址
            if AddressFamily.AF_INET == addr.family :
                local_address.append("http://"+addr.address+":5000")
    filepath = os.path.join(os.path.dirname(os.path.abspath('__file__')),'phone_win_file\\')
    if not os.path.exists(filepath):
        os.makedirs(filepath)
    FileName,FileNameWithPath,FileDir = GetNameByEveryDir(filepath)
    page=1
    limit=10
    start = (page - 1) * limit
    end = page * limit if len(FileName) > page * limit else len(FileName)
    ret = [FileName[i] for i in range(start, end)]
    all_page=int(len(FileName)/limit)+1
    return render_template('up_video.html',file_list=ret,all_file=len(FileName),pages=page,all_page=all_page,address=local_address)

@app.route('/next_page/<int:page>/', methods=['GET'])
def next_page(page):
    filepath = os.path.join(os.path.dirname(os.path.abspath('__file__')), 'phone_win_file\\')
    if not os.path.exists(filepath):
        os.makedirs(filepath)
    FileName, FileNameWithPath, FileDir = GetNameByEveryDir(filepath)
    limit = 10
    all_page = int(len(FileName) / limit) + 1
    pages = int(page)
    if pages <= 0:
        pages = 1
    elif pages >= all_page:
        pages = int(all_page)
    else:
        pages = int(page)
    start = (pages - 1) * limit
    end = pages * limit if len(FileName) > pages * limit else len(FileName)
    ret = [FileName[i] for i in range(start, end)]

    return render_template('up_video.html', file_list=ret, all_file=len(FileName), pages=pages, all_page=all_page)


@app.route('/phone_win_file/<file_name>', methods=['GET'])
def phone_win_file(file_name):
    try:
        filepath = os.path.join(os.path.dirname(os.path.abspath('__file__')), 'phone_win_file')
        if not os.path.exists(filepath):
            os.makedirs(filepath)
        filename = file_name
        # print(file_name)
        file = os.path.join(filepath, filename)
        return send_file(file)
    except Exception as e:
        return json.dumps({'code': "502"}, ensure_ascii=False)

@app.route('/up_video', methods=['post'])
def up_video():
    try:
        filepath = os.path.join(os.path.dirname(os.path.abspath('__file__')), 'phone_win_file')
        if not os.path.exists(filepath):
            os.makedirs(filepath)
        print(request.files.getlist('file'))
        for f in request.files.getlist('file'):
            if f:
                filename = f.filename
                upload_path = os.path.join(filepath, filename)
                f.save(upload_path)
        return render_template('up_video_ok.html')
    except Exception as e:
        # print(e)
        return json.dumps({'code': "502"}, ensure_ascii=False)


def run_sever():
    app.run(host='0.0.0.0', port=5000, debug=True)


if __name__ == '__main__':
    run_sever()
