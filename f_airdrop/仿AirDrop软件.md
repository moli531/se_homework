#  仿 AirDrop 软件

##  关键功能

1. 在同一局域网下手机电脑互传文件
2. 多文件上传
3. 显示文件目录并实时更新目录
5. 文件列表可以分页展示

## 关键代码

`main.py`

```python
def GetNameByEveryDir(file_dir): 
    FileNameWithPath = []
    FileName = []
    FileDir = []
    for root, dirs, files in os.walk(file_dir):
        for file in files:
            FileNameWithPath.append(os.path.join(root, file))  # 保存路径
            FileName.append(file)  # 保存名称
            FileDir.append(root[len(file_dir):])  
            # 保存所在文件夹
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
            ## 只放入IPv4的地址
            if AddressFamily.AF_INET == addr.family :
                #if '192' not in addr.address:
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
        return json.dumps({'code': "502"}, ensure_ascii=False)

def run_sever():
    app.run(host='0.0.0.0', port=5000, debug=True)
```



## 单元测试

<img src="https://raw.githubusercontent.com/moli531/img/main/202206120056987.png" alt="image-20220612005601961" style="zoom: 50%;" />



## 基本原理

* 基于flask的http文件传输服务，文件都上传到运行目录下的phone_win_file文件夹中

* pyinstaller将项目打包做成可执行文件main.exe(位于项目的dist文件中)

  

## 使用教程

* 首先让手机电脑处于同一局域网下，在电脑上运行main.exe或者在python环境下运行main.py，打开手机电脑互传网站，可以看到各个IPv4地址

  ![image-20220612000521092](https://raw.githubusercontent.com/moli531/img/main/202206120005524.png)

  <img src="https://raw.githubusercontent.com/moli531/img/main/202206112245209.png" alt="image-20220611223511076" style="zoom: 67%;" />

* 在地址下方可以看到文件上传部分，点击选择文件，弹出文件选择的窗口，完成后点击上传文件，在底部的互传文件框中就会显示出刚上传的文件

  ![img](https://raw.githubusercontent.com/moli531/img/main/202206120001327.png)

* 然后在手机上可以打开这些ip地址，其中有一个地址可以显示与电脑上相同的网页，只需要点击互传文件框中的文件，就能进行下载

  <img src="https://raw.githubusercontent.com/moli531/img/main/202206120002105.jpg" alt="img" style="zoom: 33%;" />

* 手机上传文件与电脑同理，电脑也只需要在互传文件框中下载文件即可

  ![image-20220612000328896](https://raw.githubusercontent.com/moli531/img/main/202206120003526.png)

* 文件上传成功则会显示并返回主界面

  *手机端页面：*

  <img src="https://raw.githubusercontent.com/moli531/img/main/202206120002883.jpg" alt="img" style="zoom:33%;" />

  *电脑端界面：*

  <img src="https://raw.githubusercontent.com/moli531/img/main/202206112253177.png" alt="image-20220611225322238" style="zoom:50%;" />
  
  ## 遇到的问题
  
  用pyinstaller -F 打包flask项目成exe文件时报错jinja2.exceptions.TemplateNotFound，
  
  是因为打包没有添加资源文件，所以在命令后添加` --add-data=templates;templates`