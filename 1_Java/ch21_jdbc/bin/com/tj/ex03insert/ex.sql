SELECT * FROM DEPT WHERE DEPTNO=30;
INSERT INTO DEPT VALUES (60,'MARKETING','SEOUL');
COMMIT;
SELECT COUNT(*) FROM DEPT WHERE DEPTNO=30;
UPDATE DEPT SET DNAME='MARKETING', LOC='SEOUL' WHERE DEPTNO=60;
ROLLBACK;
DELETE FROM DEPT WHERE DEPTNO=60; 
SELECT COUNT(*) FROM DEPT WHERE DEPTNO=70;
SELECT * FROM DEPT;
rollback;
SELECT * FROM EMP WHERE DEPTNO=(SELECT DEPTNO FROM DEPT WHERE DNAME='SALES');
UPDATE DEPT SET DNAME='dfdfd', LOC='dfdfdf' WHERE DEPTNO=60;
