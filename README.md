<h1>HEALTHGG</h1>

 React.js와 Springboot Framework를 이용한 운동 기록 관리 웹 프로젝트

 ### 홈페이지 URL
 [https://white-sea-097a0c500.5.azurestaticapps.net](https://white-sea-097a0c500.5.azurestaticapps.net)

 ### 목적
 * 운동을 기록하여 루틴 관리
 * 다른 사용자의 기록 및 랭킹으로 동기부여

### 일정
* 23.07.29 ~ 23.09.06

### 인원
* FE 2인(오창호, 차준규), BE(신형진) 1인 총 3인 팀 프로젝트

### 기능
[세부 기능 보기](DETAILFUNCTION.md)
</br>
</br>

## 개발 환경
* IDE: IntelliJ (Springboot), VScode (React.js)
* JAVA: 8
* SpringBoot: 2.7.14
* React.js: v18.16.1
* MySQL: 8.0.x

</br>
</br>

## 기술 스택
### Frontend
* React.js

### Backend
* SpringBoot 
* Spring Data JPA
* Azure Database for MySQL

</br>
</br>

## Flow Chart
</br>
</br>
<img src="readme_img/health_workflow.drawio.png" />

</br>
</br>

## API
</br>

| 종류 | Endpoint | Description | Header | Request payload |
|------|---------|-------------------------| ---------|------|
| Search API | GET</br> /api/search | 유저 프로필 검색  | | - userNm: String |
| | GET</br>  /api/search/record | 유저의 모든 운동기록 요청 | | - userNm: String </br> - category: String |
| | GET</br> /api/search/pbr | 유저의 개인 최고기록 요청 ||  - userNm: String </br> - category: String |
| Ranking API| GET</br> /api/user-ranking | 카테고리별 유저 랭킹 요청 | | - category: String </br> - subcategory: String |
| Profile API | PUT</br>/api/profile | 본인의 닉네임 변경 | Authorization: ${access_token} | - category: String </br> - nickname: String |
| | GET </br>/api/profile | 개인정보 불러오기 | - Authorization: ${access_token} | |
| | POST </br> /api/auth | 관리자 권한 요청 | - Authorization: ${access_token} | - Passwd: String |
| | POST</br> /api/image | 프로필 이미지 변경 | - Authorization: ${access_token} | - file: MultipartFile  |
| Data API | PUT </br> /api/record | 사용자 운동기록 업데이트</br>{닉네임, 운동명, 중량or횟수, 위치} | - Authorization: ${access_token} | - nickname: String </br> - exerciseName: String </br> - value: Float </br> - location: String  |
|  | PUT </br>/api/category | 운동 카테고리(ex.4대운동) 업데이트</br>{카테고리식별자, 카테고리이름} | - Authorization: ${access_token} | - cid: String </br> - categoryName: String|
|  | PUT </br>/api/subcategory | 세부운동(ex.스쿼트) 업데이트</br>{세부운동식별자, 카테고리식별자, 세부운동이름} | - Authorization: ${access_token} | - eid: String </br> - cid: String </br> - exerciseName: String|
|  | DELETE </br> /api/record | 운동 기록 삭제 | - Authorization: ${access_token} | - rid: String|
|  | DELETE </br> /api/category | 운동 카테고리 삭제 | - Authorization: ${access_token} | - cid: String|
|  | DELETE </br>/api/subcategory | 세부운동 삭제 | - Authorization: ${access_token} | - eid: String|
| Login API | GET </br> /auth/kakao/callback | 로그인 요청 |  | - code: String|
| Logout API | GET </br> /auth/kakao/logout | 로그아웃 요청 | - Authorization: ${access_token} ||

## 개선하고 싶은 부분
1. 단일책임 원칙에 따라 하나의 Class가 하나의 역할만 할 수 있게 Service와 Repository의 구조 변경
2. table 구조 설계와 index 사용법을 숙지하여 table 구조 효율화 및 index 생성
