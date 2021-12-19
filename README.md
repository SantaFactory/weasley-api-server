# Weasley App API Server

## Runbook

JVM Option 

```cmd
-Dspring.profiles.active=local
```

## Spec

- Spring boot 2.6.1
- Jvm version 11 ~ 13 
- kotlin 
- gradle
- h2 (local)
- myssql (prod)
- aws

## Releases

### AWS Releases

create to jar file

```cmd
./gradlew clean bootJar
```

move jar local to aws

```cmd
sudo scp -i  <key> <move_file> <aws_user>@<aws_ip>:<aws_path>
```

backgroup up jar file

```cmd
nohup java -jar <jar_file> &
```

## Architecture 


### Login Architecture

<img width="926" alt="스크린샷 2021-12-19 오전 11 06 33" src="https://user-images.githubusercontent.com/53357210/146661063-31dc0bdc-9dbc-46af-bd42-7547e163f1e9.png">

1. ios 에서 email 과 password 로 구글 oauth 인증을 통하여 access_token 과 id_token 을 리턴
2. ios 에서 받은 id_token 을 web server 로 전송
3. web server 에서 전송 받은 id_token 을 가지고 decode (kid 값) 을 사용
4. id_token 의 정보를 가지고 새로운 유저면 insert 그리고 새로운 유저가 아니면 update 
6. 유저 정보를 토대로 jwt 를 내려준다.


