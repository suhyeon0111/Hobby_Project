## 백엔드


### 코딩 컨벤션
<details>
<summary>자세히 보기</summary>
<div markdown="1">
프로젝트에 사용할 JAVA 코딩 컨벤션을 설정한다.


### intellij에 google-style-guide 적용
1. https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml 다운
2. intellij -> File -> Settings -> Editor -> Code style -> Java -> Scheme -> 톱니바퀴 버튼 -> Import Scheme -> Intellij IDEA... 선택
3. 다운 받은 xml 파일 선택 및 적용
4. 해당 설정 화면에서 Tab size, Indent는 4로 변경, Continuation indent는 8로 변경 및 적용
5. Intellij 재시작
6. 항상 코딩 작업을 마친 후에는 (Control + Alt + L) 로 코딩 스타일 적용

### Structure, Clean Code
- 메소드는 최소한의 역할만 수행한다
- 클래스는 필요한 책임만 할당한다
- 불필요한 주석을 지양한다
- Service는 Interface로 작성하고 DIP를 적용하여 약한 결합력을 유지한다.
- 데이터를 받고 보내는 객체는 무조건 엔티티가 아닌 Dto 혹은 일반 변수여야 한다
- 엔티티와 Dto를 매핑할 때에는 Mapstruct의 Mapper를 이용한다
- Controller에서는 최대한 어떤 Service를 호출할지 결정하는 역할과 Exception 처리만을 담당한다
- Rest API는 커스텀하게 정의한 ResponseDto를 사용한다
- 예외는 반드시 처리한다 (처리하지 않을 경우 주석으로 이유를 설명한다)

### Testing
- Controller와 Mapper는 반드시 개발 전에 테스트 코드를 작성해야 하며, Service 또한 테스트 코드를 작성하는 것을 권장한다.
- F.I.R.S.T. 규칙을 따르는 테스트코드를 작성한다.

### 네이밍 컨벤션
> 의도를 분명히 알 수 있도록 간결하고 명확하게 작성한다. 약어는 사용하지 않는다. 이중적인 의미의 단어는 지양한다.
- 메소드 : LowerCamelCase 사용, 동사가 앞에 와서 메소드의 동작을 암시한다.
- 변수 : LowerCamelCase 사용
- 파라미터 : LowerCamelCase 사용
- 클래스 : UpperCamelCase 사용
- 패키지 : 영어 소문자
- 컬렉션 : 컬렉션을 명시한다 (userList)
- Enum, 상수 : CONSTANT_CASE 사용 (영어 대문자) 

#### reference
https://myeonguni.tistory.com/1596
https://github.com/dnd-side-project/dnd-5th-5-backend
https://jobc.tistory.com/212
Clean Code
</div>
</details>
