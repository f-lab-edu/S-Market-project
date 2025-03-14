# java 애플리케이션 실행을 위한 jdk17 버전의 베이스 이미지 구성
FROM meddream/jdk17:latest

# ./build/libs/ 디렉터리의 모든 *SNAPSHOT.jar 파일을 Docker 이미지 내의 cicdtest.jar 이름으로 복사
COPY ./build/libs/*SNAPSHOT.jar cicdtest.jar

ENV JASYPT_ENCRYPTOR_PASSWORD="" 
#JASYPT 패스워드를 명시적으로 "" 선언함으로써, 변수가 주입되지 않으면 빈 문자열이 주입

# 컨테이너가 실행될 때 기본적으로 실행될 명령을 정의 java -jar cicdtest.jar
ENTRYPOINT ["java", "-Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD}","-jar", "/app.jar"]
# "-Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD}" Jasypt 패스워드 주입 (Github Actions)
