# madcamp-week4

### 2020 여름 KAIST 몰입캠프 4주차 공통과제

- [프로젝트 이름] : 쉽게 쓰여진 시

<p align="center">
<img src="https://user-images.githubusercontent.com/67998133/91695682-7dc0a900-eba9-11ea-8164-8695bfc040f6.jpg"  width="200" hspace=20>
<img src="https://user-images.githubusercontent.com/67998133/91695773-9e88fe80-eba9-11ea-890f-9858558fd7d3.jpg"  width="200" hspace=20>
<img src="https://user-images.githubusercontent.com/67998133/91695826-afd20b00-eba9-11ea-872d-d7b3c2b5f35a.jpg"  width="200" hspace=20>
</p>

### 앱 설명

- 유저가 쓴 수필, 시를 공유할 수 있는 sns 구축
- 좋아요, 팔로우 기능을 구현
- Kogpt2 모델을 사용하여 인공지능이 써주는 시 기능을 구현



### 앱 구조

- MainTab
   - <1번 탭> 
      - 유저가 글을 쓸 글감 제시

   - <2번 탭>
      - 해당 글감을 이용한 대표적인 시를 보여줌 

   - <3번 탭> 
      - 타 유저가 쓴 시 list   
      <p align="center">
      <img src="https://user-images.githubusercontent.com/67998133/91734533-02311d00-ebe6-11ea-8a4f-4574714ad47a.jpg"  width="180">
      <img src="https://user-images.githubusercontent.com/67998133/91734620-1bd26480-ebe6-11ea-840a-96993ebd916c.jpg"  width="180">
      <img src="https://user-images.githubusercontent.com/67998133/91734765-4f14f380-ebe6-11ea-81fd-b9b8d5e3dfe0.jpg"  width="180">
      <img src="https://user-images.githubusercontent.com/67998133/91734871-72d83980-ebe6-11ea-8345-5af7f6d742a7.jpg"  width="180">
      </p>

- SubTab
  - <글쓰기 탭> 
    - 주제, 내용을 써서 나의 글에 추가하는 탭
    - 인공지능으로 쓰는 시 기능
    
  - <나의 글 탭> 
    - 내가 썼던 글 표시 

  - <좋아요 탭>
    - 내가 좋아요한 글 표시

  - <팔로우 탭> 
    - 내가 팔로워한 유저 표시   

    <p align="center">
    <img src="https://user-images.githubusercontent.com/67998133/91735262-027de800-ebe7-11ea-9a13-9c1ee439fd21.PNG"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91735783-c39c6200-ebe7-11ea-9080-ec7a9c594e0e.jpg"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91735918-f181a680-ebe7-11ea-8aa8-55b9785d4217.jpg"  width="200" hspace=20>   
    </p>

    <p align="center">
    <img src="https://user-images.githubusercontent.com/67998133/91736051-183fdd00-ebe8-11ea-95d8-d5d6de844413.jpg"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91736083-21c94500-ebe8-11ea-9372-83bddddd656c.jpg"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91736195-46bdb800-ebe8-11ea-8faf-ebe176fcc5e2.jpg"  width="200" hspace=20>   
    </p>
    
    <p align="center">
    <img src="https://user-images.githubusercontent.com/67998133/91736051-183fdd00-ebe8-11ea-95d8-d5d6de844413.jpg"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91736284-63f28680-ebe8-11ea-9f68-0141feef69dd.jpg"  width="200" hspace=20>
    <img src="https://user-images.githubusercontent.com/67998133/91736357-7c62a100-ebe8-11ea-9283-b363b589d380.jpg"  width="200" hspace=20>   
    </p>

### 서버 구성

- 어플리케이션 내부 전달용 서버
  - 사용한 방식 : AsyncTask 
  - 동기식 방식을 비동기식으로 임시 처리 : 
- 유저 데이터베이스 전달용 서버
  - 구현 기능
    - 특정 시에 대한 "좋아요 "기능, 해당 시인을 "팔로우"하는 기능
      - "좋아요" 누른 시들을 한번에 볼 수 있는 기능
      - "팔로우" 한 시인들의 모든 시들을 볼 수 있는 기능
    - 유저 로그인/회원가입용 기능
    - "오늘의 글감", "글감 대표 시" 를 데이터베이스로부터 가져오는 기능
  - 사용한 서버 종류 : Django 서버
  - 서버 송/수신 방식 : RestAPI
    - Post, Get 방식을 혼용하여 사용



- KoGPT2 모델용 서버
  - 구동 원리 : Kaggle 서버를 이용해서 모델을 학습한 뒤, 해당 모델에 대한 정보를 .tar 파일 형식으로 저장해둔다
    - 모델 training set : 현대시, 1980~2000년대 유명 가수들 가사
    - .tar 파일 내용물 : 딥러닝 모델 안에 있는 여러 개의 파라미터들(=기울기 값) 을 저장해둠
  - 사용한 서버 종류 : Django 서버
    - KoGPT2 실행 모델이 파이썬으로 작성되어 있기에, 편리한 연동을 위해 Django를 선택
  - 서버 송/수신 방식 : RestAPI 
    - 쓰고싶은 시의 내용을 작성한 뒤 해당 내용을 KoGPT2 모델에 Post방식으로 전달
    - AI가 생성한 시를 어플리케이션에 답신





### 데이터베이스 구성

- 데이터베이스 종류 : MySQL  (Django 서버에 연결)
- 테이블 종류 (* 표시된 것은 primary key)
  - 유저 정보 : *id, pwd, 필명
  - 시 정보 : *시 id, 필명, 좋아요 횟수, 시 쓴 시간
  - 팔로잉 정보 : *(팔로우 대상 필명, 팔로우 하는 사람 필명)
  - 좋아요 정보 : *(좋아요 누른 시 id, 좋아요 누른 사람 id)
  - 글감 정보 : *글감, 글감 해당 시
    - 매달 30개의 글감을 업데이트 
    - 하루에 한 개씩 글감을 테이블로부터 가져와서 어플에 표현
    
### 실제 시연 영상 (youtube)
https://www.youtube.com/watch?v=EKnkZ25WmeY
