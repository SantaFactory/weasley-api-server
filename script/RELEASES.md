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
