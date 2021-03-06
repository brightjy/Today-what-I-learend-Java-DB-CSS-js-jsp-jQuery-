package com.tj.studentmng;
// (멤버변수) 학번, 이름, 나이, 학과
// (메소드) modify(fn, 바꿀데이터) infoString() print()
public class Student {
	private int no; // 10878155
	private String name;
	private int age;
	private String major; // 경제학
	
	//Student s = new Student(10878155, "박지영", 30, "경제학");
	//Scanner sc = new Scanner(System.in);
	public Student() {
	}
	public Student(int no, String name, int age, String major) {
		this.no=no;
		this.name=name;
		this.age=age;
		this.major=major;
	}
	public void modify(int fn, String data) {
    //s.modify(3, "23")
		switch(fn) {
		case 1 : no = Integer.parseInt(data); // String 데이터를 Int로 바꿔줌
		break;
		case 2 : name = data;
		break;
		case 3 : age = Integer.parseInt(data); // String 데이터를 Int로 바꿔줌
		break;
		case 4 : major = data;
		break;
		default:
			System.out.println("유효한 fn 번호가 아닙니당");
		}
	}
    // System.out.println(s.infoString()) [학번]201901 [이름]홍길동 [나이]23 [전공]기계
	public String infoString() {
		return "[학번]"+no+" [이름]"+name+" [나이]"+age+ " [전공]"+major;
	}
	public void print() {
		System.out.println("[학번]"+no+" [이름]"+name+" [나이]"+age+ " [전공]"+major);
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
		
}//class

