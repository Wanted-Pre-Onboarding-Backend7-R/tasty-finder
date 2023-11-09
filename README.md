![logo](https://github.com/Wanted-Pre-Onboarding-Backend7-R/tasty-finder/assets/110372498/76105895-828e-43b8-a8c9-4b49898034dd)
# TastyFinder - 지리기반 맛집 추천 웹 서비스

**나의 현재 위치 또는 지정한 위치를 기반**으로 맛집의 메뉴를 추천해주는 서비스입니다.

<br/>

## 목차
- [프로젝트 소개](#프로젝트-소개)
- [ERD 설계](#erd-설계)
- [API 설계](#api-설계)
- [프로젝트 일정관리](#프로젝트-일정관리)
- [구현과정(설계 및 의도)](#구현과정설계-및-의도)
- [트러블슈팅 및 회고](#트러블슈팅-및-회고)
- [Members](members)


<br/>


## 프로젝트 소개


<br/>


## Skills
<div align="center">
  
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white) 
![Java](https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) 

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white) 
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%ffffff.svg?style=for-the-badge&logo=swagger&logoColor=white)

</div>

<br/>
<br/>


## 컨벤션 규칙

<details>
<summary>Code 컨벤션 - click</summary>


- **Code 컨벤션** 
  - 변수명: boolean인 경우 형용사, 그 외 명사
  - 함수명: 동사 현재형으로 시작
  - 클래스명: 명사
  - if, for 중괄호 한 줄이라도 항상 치기
  - 커밋하기 전에
     - import 정리: `ctrl + alt(option) + o`
     - line formatting: `ctrl(command) + alt(option) + l`
     - 마지막 빈 줄 추가
    ```java
    /** 예시 **/
    public class Clazz {

        public int addCountIfValid(int count, boolean isValid) {
            if (isValid) {
                return count + 1;
            }
            return count;
        }
    }
    // 마지막 빈 줄
    ```
    
  - Optional 줄바꿈
     ```java
     Member member = memberRepository.findByEmail(dto.getEmail())
           .orElseThrow(NotFoundMemberByEmailException::new);`
     ```
  - 객체 생성 규칙
    - 외부에서 직접적인 new 지양하고 내부적으로 활용 `@Builder` 및 정적 팩토리 메서드 활용
    - 정적 팩토리 메서드 이름은 단일 인자일 경우 `from`, 다중 인자일 경우는 `of`로 명명 
    - Bean 제외 DTO, Entity들은 `@All-/@Required-ArgsContructor` 활용 제한, 직접 코드로 생성자 작성 및 private/protected 등으로 잠그기
    - 목적: 같은 타입의 필드 연속될 때 1) 잘못된 값 입력하는 human error 최소화, 2) 필드 순서를 바꿀 경우 IDE에 의한 리팩토링이 적용되지 않는 Lombok 에러 방지, 3) 가독성을 위한 작성법 통일을 위하여
    ```java
     @Getter
     @NoArgsConstructor(access = AccessLevel.PROTECTED)
     @EqualsAndHashCode(of = "accountName", callSuper = false)
     @Entity
     public class Member extends BaseEntity {
   
         @Column(nullable = false, unique = true)
         private String accountName;
   
         @Column(nullable = false)
         private String email;
     
         @Column(length = 60, nullable = false)
         private String password;
   
         @Column(length = 6, nullable = false)
         private String approvalCode;
     
         @Column(nullable = false)
         private Boolean isApproved;
     
         @Enumerated(EnumType.STRING)
         private Authority authority;
     
         @Builder
         private Member(String accountName, String email, String password, String approvalCode, Boolean isApproved) {
             this.accountName = accountName;
             this.email = email;
             this.password = password;
             this.approvalCode = approvalCode;
             this.isApproved = isApproved;
             authority = Authority.ROLE_USER;
         }
   
         public static Member of(MemberJoinRequest dto, String encodedPassword, String approvalCode) {
             return builder()
                     .accountName(dto.getAccountName())
                     .email(dto.getEmail())
                     .password(encodedPassword)
                     .approvalCode(approvalCode)
                     .isApproved(false)
                     .build();
         }
     }
  
    ```

</details>

<details>
<summary>Git 컨벤션 - click</summary>

- **git commit rules**
  
    | type     | description |
    |----------|-------------|
    | feat     | 새로운 기능 추가 |
    | fix      | 버그 및 로직 수정 |
    | refactor | 기능 변경 없는 코드 구조, 변수/메소드/클래스 이름 등 수정 |
    | style    | 코드 위치 변경 및 포맷팅, 빈 줄 추가/제거, 불필요한 import 제거 |
    | test     | 테스트 코드 작성 및 리팩토링 |
    | setup    | build.gradle, application.yml 등 환경 설정 |
    | docs     | 문서 작업 |
  
    ```bash
    # commit title format
    git commit -m "{커밋 유형} #{이슈번호}: #{내용}"
  
    # example of git conventions
    git commit -m "refactor #125: `ChatService` 중복 로직 추출
  
    예외 압축
    메소드 위치 변경
    메소드 이름 변경
    "
    ```

- **git branch rules**
    ```bash
    # branch name format
    git checkout -b "feat/#{이슈번호}-{내용}"
    ```


</details>


<br/>


## ERD 설계

<img width="900" alt="tasty-finder" src="https://github.com/sohyuneeee/wanted-pre-onboarding-backend/assets/110372498/b001dbf3-64c1-4146-9611-e4e3f1f1089b">


<br/>
<br/>


## API 설계
Swagger:

[![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)](https://lush-hen-bac.notion.site/53fe4004159d468c9a02d3810c669a93?v=cdabe84aa5ec44c1b3ac914c6a9c0db4&pvs=4)


<img width="900" alt="스크린샷 2023-11-08 오후 8 44 01" src="https://github.com/sohyuneeee/wanted-pre-onboarding-backend/assets/110372498/9ba0edf5-add8-4cf3-98e3-cd56a34f0e6b">


<br/>
<br/>

## 프로젝트 일정관리

<img width="900" alt="스크린샷 2023-11-08 오후 8 58 52" src="https://github.com/sohyuneeee/wanted-pre-onboarding-backend/assets/110372498/021c1184-044e-43bf-a699-714873be1a40">


<br/>
<br/>


## 구현과정(설계 및 의도)


<br/>
<br/>

## 트러블슈팅 및 회고


<br/>
<br/>


## Members


<div align="center">

| name | role | github | email |
|------|------|--------|-------|
| 강석원 | 팀원 | [@piopoi](https://github.com/piopoi) | kangsw1988@gmail.com |
| 모장현 | 팀원 | [@mojh7](https://github.com/mojh7) | mjh79017901@gmail.com |
| 박준승 | 팀원 | [@OldRabbit736](https://github.com/OldRabbit736) | junseung736@gmail.com |
| 이소현 | 팀원 | [@sohyuneeee](https://github.com/sohyuneeee) | sohyuney@naver.com |
| 정준희 | 팀장 | [@JoonheeJeong](https://github.com/JoonheeJeong) | jeonggoo75@gmail.com |


<br/>
<br/>

