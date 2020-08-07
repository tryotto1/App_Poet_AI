from django.shortcuts import render
from community.forms import *

#example


# def write(request):
#     if request.method == 'POST': #request가 post
#         form = Form(request.POST) # request를 form에 넣음.
#         if form.is_valid():
#             form.save() #data base에 저장이 됨
#     else:
#         form = Form() # post가 아니면 그냥 보여주기만 함.
#     return render(request, 'write.html', {'form':form} ) #html에 form 추가


def write(request):
    if request.method == 'POST':
        form = Form(request.POST)
        if form.is_valid():
            form.save()
    else:
        form = Form()

    return render(request, 'write.html', {'form':form})



def list(request):
    articleList = Article.objects.all() # objects.all() : Article을 전부 가져와서 list를 가져와줌
    return render(request, 'list.html', {'articleList':articleList}) # list.html에 변수 전달 됨.

# 시 작성 관련
def write_poem(request):    
    # 1) 버튼 눌렀을때 -> post 방식 -> save 함 (=DB에 저장)
    # 2) 버튼 안 눌렀을 -> 기본적인 form 틀은 html에 올려야 함 -> form 객체만 받아준다
    if request.method == 'POST':
        _write_poem_form = write_poem_form(request.POST)
        if _write_poem_form.is_valid():
            _write_poem_form.save() # 데이터베이스(sqlite)에 저장해준다
    else:
        _write_poem_form = write_poem_form()
    return render(request, 'write_poem.html', {'form':_write_poem_form})

def poem_all_list(request): 
    poems_list = Poems.objects.all()
    return render(request, 'poem_all_list.html', {'poems_list':poems_list})
    
def poem_one(request, num="1"):
    _poem_one = Poems.objects.get(id=num)
    return render(request, 'poem_one.html', {'poem_one':_poem_one})


# 로그인 + 회원가입 관련
def signin(request):
    if request.method == 'POST':
        _singin_form = singin_form(request.POST)
        if _singin_form.is_valid():
            _singin_form.save() 
    else:
        _singin_form = singin_form()
    return render(request, 'signin.html', {'form':_singin_form})

def login(request):
    if request.method == 'POST':
        _login_form = login_form(request.POST)
        if _login_form.is_valid():
            _login_form.save() 
    else:
        _login_form = login_form()
    return render(request, 'login.html', {'form':_login_form})

# 팔로우 추가
def new_follow(request):
    if request.method == 'POST':
        _new_follow_form = new_follow_form(request.POST)
        if _new_follow_form.is_valid():
            _new_follow_form.save() 
    else:
        _new_follow_form = new_follow_form()
    return render(request, 'new_follow_form.html', {'form':_new_follow_form})