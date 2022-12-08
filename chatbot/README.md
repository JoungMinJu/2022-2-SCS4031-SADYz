# ChatBot용 Flask 서버 🐳

`독거노인 관리자용 웹 대시보드 ` 개발에 필요한 __챗봇🎙__ 서버입니다.

__안드로이드__ 와 API로 통신하고, 그 결과를 DB에 저장합니다.


<br>

---

### app.py
- 플라스크 실행 

### config.py
- ORM 설정 파일

<br><br>

## checkpoint
### chatbot_kogpt2.ckpt
- KoGPT2 학습 모델

### emotion_p3.pth
- KoBERT 학습 모델

### koelectra-wellness-text-classification...pth
- KoELECTRA 학습 모델

<br><br>

## controller

### dbController.py
- ORM을 위한 컨트롤러

### bathroomController.py
- 대화를 시작하기 위한 로직 __start_bathroom__
  - client의 화장실 빈도수를 체크하고 그에 맞는 질문을 return
- 대화 진행에 사용되는 로직 __bathroom_chat__
  - client의 답변에 따라 DB에 저장하는 로직
  - (변비 증상 호소)와 같은 특이사항이 있을 경우 같이 저장합니다.
  
### emotionController.py
- 대화를 시작하기 위한 로직 __start_emotion__
  - 랜덤으로 안부 질문을 선택해서 return
- 대화 진행에 사용되는 로직 __chat_emotion__
  - 종료면 대화 종료 로직 실행
    - 최빈 감정값 추출해서 업데이트
  - 그게 아니면
    - 사용자 답변에서 긍정, 부정을 인식하여
      - 긍정이면 답변 생성 모델 KoGPT2 활용하여 답변 생성
      - 부정이면 답변 카테고리 기반 추출 모델 KoELECTRA 활용하여 답변 생성
      
### mealController.py
- 대화를 시작하기 위한 로직 __start_meal__
  - 식사 질문 return
- 대화 진행에 사용되는 로직 __meal_chat__
  - 사용자 답변의 긍부정 판단
  - 필요에 따라 주방 움직임 데이터 및 문닫힘 데이터 파악


<br><br>

## data
- 학습에 사용되는 데이터

<br><br>

## model

### client.py
- ORM 모델

### koelectra.py
- KoELECTRA 모델

<br><br>

## service
### dbService.py
- db 조작과 관련된 서비스 로직

### emotion_classification.py
- 사용자 답변 감정 (긍/부정) 추출 위한 KoBERT 로직

### koelectra_qa.py
- KoELECTRA 기반 답변 추출 로직

### kogptchat.py
- KoGPT2 기반 답변 추출 로직

### responseService.py
- 답변 판별을 위한 서비스 로직

<br><br>

## util

### response.py
- 답변 상수 모음
- JSON 변환 로직
