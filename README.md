
### 프로젝트 설명
- 명지대학교 스프링부트 스터디를 통해 구현하는 쇼핑몰입니다.
- 쇼핑몰의 기능은 사용자 입장에서는 [조회, 장바구니, 결제] 입니다.
- 쇼핑몰의 기능은 판매자 입장에서는 [물품등록, 재고확인, 잔고확인] 입니다.
dd

### 프로젝트 설정
- Gradle
- Java 11

### dependency 
- Spring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- Oauth2-client
- Thymeleaf


### 참고사항
- MariaDB 사용
- Application.properties 파일은 resources 안에다가 만들고 아래 코드 입력하시면 됩니다.

Application.properties
```aidl
server.port=8080

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.username=아이디
spring.datasource.password=패스워드
spring.datasource.url=jdbc:mysql://localhost:3306/스키마명

# create, update, none, create-drop, validate,
spring.jpa.hibernate.ddl-auto=update

## Jpa Properties
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

## Logging Level
logging.level.org.hibernate.type.descriptor.sql=debug

spring.security.user.name=root
spring.security.user.password=root
spring.security.user.roles=ADMIN
```
