DROP TABLE BOARD;

CREATE TABLE BOARD(
    NUM NUMBER(5) PRIMARY KEY, -- �� ��ȣ(���� ��������)
    WRITER VARCHAR2(30) NOT NULL, -- �ۼ���
    SUBJECT VARCHAR2(100) NOT NULL, -- ����
    CONTENT VARCHAR2(1000) NOT NULL, -- ���� *���Ѵ� : CLOB
    EMAIL VARCHAR2(30), -- �ۼ��� �̸��� �ּ�
    HIT NUMBER(5) DEFAULT 0, -- ��ȸ�� 
    PW VARCHAR2(30) NOT NULL, -- ��й�ȣ(�� ���� �� �ʿ�)
    REF NUMBER(5) NOT NULL, -- �� �׷� 
    RE_STEP NUMBER(5) NOT NULL, -- ���� �� �׷쳢�� ��� ����(����:0)
    RE_LEVEL NUMBER(2) NOT NULL, -- �亯 ���� ��� �鿩���� ����
    IP VARCHAR2(20) NOT NULL, -- �ۼ����� IP�ּ�
    RDATE DATE DEFAULT SYSDATE -- �� �� ����(��¥+�ð�)
    );

-- �� ����(�� ����� 0�� �ƴϸ� �ܾ�÷��� ��)
SELECT COUNT(*) FROM BOARD; -- Arraylist�� ���̷� �����ص� �Ǳ� �� 
-- �� ��� (�ֽ� ���� ����)
SELECT * FROM BOARD ORDER BY REF DESC;
-- �۾���
SELECT NVL(MAX(NUM),0)+1 FROM BOARD; -- ���ο� �ۿ� �Ҵ��� �� ��ȣ 
INSERT INTO BOARD (NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_LEVEL, IP)
    VALUES ((SELECT NVL(MAX(NUM),0)+1 FROM BOARD), '������', 'ù ��° ���� ����', 'ù ��° ���� ����', 'barkji0@naver.com', '111', (SELECT NVL(MAX(NUM),0)+1 FROM BOARD), 0, 0, '192.168.20.41');
SELECT * FROM BOARD ORDER BY NUM DESC;
-- �� ��ȣ�� ��(DTO) ��������
SELECT * FROM BOARD WHERE NUM=1;
-- ��ȸ��(HIT) �ø���
UPDATE BOARD SET HIT = HIT+1 WHERE NUM=1;
-- �� �����ϱ�
UPDATE BOARD SET WRITER = '������', SUBJECT = '�ٲ� ����', CONTENT = '�ٲ� ����', EMAIL = '�ٲ۸���@.COM', PW='222', IP = '111.111.11.11' WHERE NUM=1;
-- �� �����ϱ�
DELETE FROM BOARD WHERE NUM=1 AND PW='222' ;

COMMIT;

-- paging ó��
-- 1. ��ȸ�� ����
UPDATE BOARD SET HIT = MOD(NUM,12);
-- 2. TOP-N ����
SELECT * FROM BOARD ORDER BY REF DESC; -- 1�ܰ� (listBoard())
SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC) A; -- 2�ܰ�
SELECT * FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC) A) 
    WHERE RN BETWEEN 110 AND 120; -- TOP-N���� (listBoard(s,e))

-- �亯�� ó��
-- 300�� ����
INSERT INTO BOARD (NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_LEVEL, IP)
    VALUES (300, '������', 'ù ��° ���� ����', 'ù ��° ���� ����', 'barkji0@naver.com', '111', 300, 0, 0, '192.168.20.41');
-- 300�� �ۿ� ���� �亯��    
-- �� ����
UPDATE BOARD SET RE_STEP = RE_STEP+1 WHERE REF=1 AND RE_STEP>0; -- ���۰� ref�� ����, ������ RE_STEP���� ū ���� +1 �÷���
-- �亯 �� ����
INSERT INTO BOARD (NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_LEVEL, IP) 
    VALUES ((SELECT NVL(MAX(NUM),0)+1 FROM BOARD), '�ڼҿ�', '�亯 ����', '�亯 ����', 'DD@DD.DD', '111',
        1, 0+1, 0+1, '192.168.20.41');
-- �亯 �� ���        
SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP; 

COMMIT;

DELETE FROM BOARD;