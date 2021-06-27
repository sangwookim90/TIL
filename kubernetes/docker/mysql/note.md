## MySQL 컨테이너 생성 명령
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=ROOT_PASSWORD -d -p 3306:3306 mysql:latest

## MySQL 컨테이너 생성 후 외부 접속을 위한 과정
##### mysql8 부터 아래 에러 발생
##### ['Authentication plugin 'caching_sha2_password' cannot be loaded: dlopen(/usr/local/mysql/lib/plugin/caching_sha2_password.so, 2): image not found'] 에러 발생
    ALTER USER '계정명'@'%' IDENTIFIED WITH mysql_native_password BY 'ROOT_PASSWORD';
    
    # localhost 및 원격접속용 계정 생성
    CREATE USER 'swkim'@'%' IDENTIFIED BY 'qwerasdf';
    CREATE USER 'swkim'@'localhost' IDENTIFIED BY 'qwerasdf';
    
    # Database schema의 권한 부여
    grant all privileges on secret.* to 'swkim'@'%';

    
