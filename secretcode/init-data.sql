INSERT INTO user(`email`,`user_name`,`password`,`phone`)
VALUES ('test@test.com','test','testetest','010-1234-5678');
INSERT INTO user(`email`,`user_name`,`password`,`phone`)
VALUES ('test2@test.com','test2','testetest','010-1234-2523');
INSERT INTO user(`email`,`user_name`,`password`,`phone`)
VALUES ('test3@test.com','test3','testetest','010-1234-1345');
INSERT INTO user(`email`,`user_name`,`password`,`phone`)
VALUES ('test4@test.com','test4','testetest','010-1234-1234');



INSERT INTO notice (`USER_ID`,`TITLE`,`CONTENTS`,`HITS`,`LIKES`)
VALUES (1,'게시글1','내용1',0,1);
INSERT INTO notice (`USER_ID`,`TITLE`,`CONTENTS`,`HITS`,`LIKES`)
VALUES (1,'게시글2','내용2',0,1);
INSERT INTO notice (`USER_ID`,`TITLE`,`CONTENTS`,`HITS`,`LIKES`)
VALUES (2,'게시글3','내용3',0,0);
INSERT INTO notice (`USER_ID`,`TITLE`,`CONTENTS`,`HITS`,`LIKES`)
VALUES (1,'게시글4','내용4',0,1);
INSERT INTO notice (`USER_ID`,`TITLE`,`CONTENTS`,`HITS`,`LIKES`)
VALUES (3,'게시글5','내용5',0,0);

INSERT INTO notice_like (`NOTICE_ID`,`USER_ID`)
VALUES (1,1);
INSERT INTO notice_like (`NOTICE_ID`,`USER_ID`)
VALUES (2,1);
INSERT INTO notice_like (`NOTICE_ID`,`USER_ID`)
VALUES (4,2);


