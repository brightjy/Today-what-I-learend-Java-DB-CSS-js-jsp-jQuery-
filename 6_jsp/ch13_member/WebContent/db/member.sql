DROP TABLE MEMBER;

CREATE TABLE MEMBER(
    ID VARCHAR2(30) PRIMARY KEY NOT NULL,
    PW VARCHAR2(30) NOT NULL,
    NAME VARCHAR2(30) NOT NULL,
    EMAIL VARCHAR2(30),
    BIRTH DATE NOT NULL,
    RDATE DATE NOT NULL,
    ADDRESS VARCHAR2(200));
    
SELECT * FROM MEMBER;

-- 1. 회원 가입 시 아이디 유무 확인(중복체크)
SELECT * FROM MEMBER WHERE ID='AAA'; -- 그냥 아이디 있는지 없는지만 확인

-- 2. 회원 가입 시 INSERT
INSERT INTO MEMBER (ID,PW,NAME,EMAIL,BIRTH,RDATE,ADDRESS) VALUES ('AAA','111','가가가','AAA@BB.CC','1990-01-01',SYSDATE,'서울 종로');

-- 3. 로그인 할 때 (ID/PW)
SELECT * FROM MEMBER WHERE ID='AAA'; -- 아이디로 검색해 가져온 후 PW를 PARAM PW와 체크

-- 4. ID로 DTO 가져오기
SELECT * FROM MEMBER WHERE ID='AAA'; -- DTO 가져와서 SET SESSION

-- 5. 회원 정보 수정(UPDATE)
UPDATE MEMBER SET PW='222',NAME='나나나',EMAIL='DDD@EE.FF',BIRTH='1990-02-02',ADDRESS='서울 강남' WHERE ID='AAA';

COMMIT;
