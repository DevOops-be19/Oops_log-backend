## 1. build된 jar파일이 있는 경우
# 미리 만들어져야 하는 부분
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#COPY build/libs/*.jar ./
#RUN mv $(ls *.jar | grep -v plain) app.jar
## img가 만들어지는 부분
#ENTRYPOINT ["java","-jar","app.jar"]
# Dockerfile이 있는 경로에서 gitbash 실행 -> docker build -t(타이틀) 이름 .(경로, . 은 현재 경로)
# -> docker run -d(백그라운드에 컨테이너 실행) -p(포트 설정) 호스트_포트:컨테이너_포트(우리가 설정한 서버 포트) --name 컨테이너_이름 이미지_이름
## 2. 자동 build 후 jar 파일로 실행되게 수정(멀티 스테이징)
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
## deamon 스레드를 쓰지 않음으로써 불필요한 리소스 낭비를 줄인다.
## gradle 이미지는 기본적으로 백그라운드에서 프로세스(daemon)를 실행
## 메모리에 JVM이나 빌드 정보를 캐싱
## 다음 빌드 시 속도가 향상
RUN gradle clean build --no-daemon -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar ./
RUN mv $(ls *.jar | grep -v plain) app.jar
ENTRYPOINT ["java","-jar","app.jar"]