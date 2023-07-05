package org.store;

import java.util.Map;
import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static LoginInfo logininfo = new LoginInfo();
    static Display display = new Display();


    public static void main(String[] args) {
        display.mainDisplay(scanner);
    }

    public static int LoginMember(int kind, String name, String password) {
        Member login = new Member();
        Map<String, MemberInfo> logins = login.loadMembersFromFile();
        MemberInfo member = logins.get(name);

        if (member != null && (member.getPassword().equals(password)) && member.getUserCase() == kind) {
            if(kind == 1){
                return 1;
            }
            else if (kind ==2) {
                return 2;
            }
        }
        return 0;
    }

    public static void Register(Scanner scanner) {
        Member member = new Member();
        boolean t = true;
        int userCase;
        String name="";
        logininfo.loginMembers = member.loadMembersFromFile();
        while (t) {
            System.out.print("회원 ID: ");
            name = scanner.nextLine().trim();
            try{
                if (!logininfo.loginMembers.containsKey(name)) {
                    System.out.println("가능한 ID입니다.");
                    t = false;
                    break;
                } else {
                    System.out.println("이미 존재하는 ID입니다.");
                }
            }catch (NullPointerException e){
                System.out.println("member.csv 파일이 비어있습니다.");
            }
        }
        System.out.print("회원 비밀번호: ");
        String password = scanner.nextLine().trim();
        System.out.print("회원 전화번호: ");
        String phoneNum = scanner.nextLine().trim();
        System.out.print("회원 주소: ");
        String address = scanner.nextLine().trim();
        System.out.print("유형(1.관리자 | 2.고객): ");
        userCase = scanner.nextInt();
        scanner.nextLine();
        try{
            if (userCase == 1 || userCase == 2) {
                System.out.println("가능한 회원 유형입니다.");
            } else {
                System.out.println("불가능한 회원 유형입니다.");
            }
        }catch (IllegalStateException e){
            System.out.println("숫자로 입력하여 주세요. ");
        }
        Member.createMember(name, password, phoneNum, address, userCase);
    }

    public static void AutoClearConsole() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
    public static void EnterClearConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("계속 진행하려면 Enter를 누르세요....");
        scanner.nextLine();
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}