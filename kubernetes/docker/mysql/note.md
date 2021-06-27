## MySQL 컨테이너 생성 명령
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=ROOT_PASSWORD -d -p 3306:3306 mysql:latest

## MySQL 컨테이너 생성 후 외부 접속을 위한 과정
    ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'ROOT_PASSWORD'

### 설정 전 문구
    Authentication plugin 'caching_sha2_password' cannot be loaded: dlopen(/usr/local/mysql/lib/plugin/caching_sha2_password.so, 2): image not found
