"""tutorial URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from django.conf.urls import include, url
from community.views import *

# url(주소, 뷰 종류, 이름?)
urlpatterns = [
    
    # url(r'^admin/', include(admin.site.urls)),
    path('admin/', admin.site.urls),

    #example
    url(r'^write/', write, name='write'),
    url(r'^list/', list, name='list'),


    url(r'^write_poem2/', write_poem, name='write'),    
    # 시 작성 + 리스트 관련
    url(r'^write_poem/', write_poem, name='write'),    
    url(r'^poem_all_list/', poem_all_list, name='poem_all_list'),
    url(r'^poem_one/(?P<num>[0-9]+)/$', poem_one, name='poem_one'),

    # 로그인 + 회원가입 관련
    url(r'^signin/', signin, name='signin'),    
    url(r'^login/', login, name='login'),

    # 팔로우 관련
    url(r'^new_follow/', new_follow, name='new_follow'),
]
