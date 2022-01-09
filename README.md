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
- JPA & Hibernate 
- gradle
- h2 (local)
- postgresql (prod)
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


### login architecture

<img width="1467" alt="스크린샷 2021-12-26 오후 7 18 40" src="https://user-images.githubusercontent.com/53357210/147405254-af2af1a4-a9f9-4fc2-93d9-83b7dc24dc5c.png">

