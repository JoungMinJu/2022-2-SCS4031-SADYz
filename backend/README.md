# 메인 Spring Boot 서버 🐳

`독거노인 관리자용 웹 대시보드` 개발에 필요한 __메인 서버✏__ 입니다.
__안드로이드__, __리액트__, __IoT__ 와 API로 통신하고, JPA를 활용하여 __DB__ 와 데이터를 주고 받습니다.


<br>
---
<br>

## client
- Client와 관련된 로직이 모여있는 패키지입니다.
- 해당 서비스에서 사용하는 `RestTemplate` 로직도 포함되어 있으며
- `DoorClosedTime`과 `LastMovedTime` 관련 로직도 포함되어 있습니다.

<br><br>

## conversation
- 대화와 관련된 로직이 모여있는 패키지입니다.

<br><br>

## emergency
- 응급콜과 관련된 로직이 모여있는 패키지입니다.
- SSE 전송을 위한 `Notificaition` 관련 로직도 포함되어 있고
- SMS 전송을 위한 `Message`,`SMS` 관련 로직도 포함되어 있습니다.

<br><br>

## global

### baseEntity
- 생성 시간, 수정 시간 자동 적용을 위한 엔티티입니다.

### fcm
- FCM을 위한 패키지입니다.

### S3
- AMAZON S3를 위한 패키지입니다.

### scheduler
- Spring Boot scheduler를 위한 패키지입니다.