import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== 마트 시스템 ===");
        System.out.println("1. 관리자");
        System.out.println("2. 회원");
        System.out.println("3. 등록");
        System.out.println("0. 종료");

        int choice = -1;
        while (choice != 0) {
            System.out.print("원하는 작업을 선택하세요: ");
            choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    Management_display(sc);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }
    }
    public static void Management_display(Scanner sc) {
        Member member = new Member();
        int choice = -1;
        while(choice != 0){
            System.out.println("1. 회원 관리 시스템");
            System.out.println("2. 상품 관리 시스템");
            System.out.println("3. 주문 관리 시스템");
            System.out.println("4. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    member.main(sc);
                    System.out.println("member종료");
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("4. 종료");
                    break;
                case 4:
                    System.out.println("관리자메뉴가 종료되었습니다.");
                    choice = 0;
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
                    break;
            }
        }

    }

    public static void Customer_display(Scanner sc, ArrayList<String>list_1,ArrayList<String>list_2) {
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println("=== 회원 관리 시스템 ===");
        System.out.println("1. 회원조회");
        System.out.println("2. 회원수정");
        System.out.println("3. 회원삭제");
        System.out.println("=== 상품 관리 시스템 ===");
        System.out.println("4. 상품조회");
        System.out.println("=== 주문 관리 시스템 ===");
        System.out.println("5. 결제");
        System.out.println("6. 주문조회");
        System.out.println("7. 주문변경");
        System.out.println("8. 주문삭제");
        System.out.println("0000. 종료");

        switch (choice) {
            case 1:
                System.out.println();
                addNamesAndAddresses(sc, list_1, list_2);
                printNamesAndAddresses(list_1, list_2);
                break;
            default:
                System.out.println("올바른 메뉴를 선택하세요.");
        }
    }
    public static void addNamesAndAddresses (Scanner
                                                     scanner, ArrayList < String > nameList, ArrayList < String > addressList){
        String name;
        String address;

        for (int i = 0; i < 3; i++) {
            System.out.print("이름 입력: ");
            name = scanner.nextLine();
            nameList.add(0, name);
            System.out.print("주소 입력: ");
            address = scanner.nextLine();
            addressList.add(0, address);
        }
    }

    public static void printNamesAndAddresses
            (ArrayList < String > printnameList, ArrayList < String > printaddressList){
        for (int j = 0; j < printnameList.size(); j++) {
            System.out.println("이름: " + printnameList.get(j));
            System.out.println("주소: " + printaddressList.get(j));
        }
    }
}



//        System.out.println("=== 회원 관리 시스템 ===");
//        System.out.println("1. 관리자");
//        System.out.println("2. 회원");
//        System.out.println("3. 등록");
//        System.out.println("0. 종료");
//
//        int choice = -1;
//        while (choice != 0) {
//            System.out.print("원하는 작업을 선택하세요: ");
//            choice = sc.nextInt();
//
//            sc.nextLine(); // 버퍼 비우기
//
//            switch (choice) {
//                case 1:
//                    System.out.println("1. 관리자(로그인");
//                    System.out.println("0. 뒤로가기");
//                    while (choice != 0) {
//                        System.out.print("원하는 작업을 선택하세요: ");
//                        choice = sc.nextInt();
//                        sc.nextLine(); // 버퍼 비우기
//                        switch (choice){
//                            case 1:
//                                System.out.println("1. 로그인(관리자) ");
//                                System.out.println("0. 여러가지");
//                                break;
//                            case 0:
//                                continue;
//                            default:
//                                System.out.println("올바른 메뉴를 선택하세요.");
//                        }
//                        break;
//                    }
//
//                case 2:
//                    System.out.println("1. 회원(로그인)");
//                    System.out.println("0. 뒤로가기");
//                    while (choice != 0) {
//                        System.out.print("원하는 작업을 선택하세요: ");
//                        choice = sc.nextInt();
//                        sc.nextLine(); // 버퍼 비우기
//                        switch (choice) {
//                            case 1:
//                                System.out.println("1. 여러가지");
//                                System.out.println("0. 뒤로가기");
//                            case 0:
//                                break;
//                            default:
//                                System.out.println("올바른 메뉴를 선택하세요.");
//                        }
//                    }
//                    break;
//                case 3:
//                    System.out.println("1. 등록하기");
//                    System.out.println("0. 뒤로가기");
//                    while (choice != 0) {
//                        System.out.print("원하는 작업을 선택하세요: ");
//                        choice = sc.nextInt();
//                        sc.nextLine(); // 버퍼 비우기
//                        switch (choice) {
//                            case 1:
//                                System.out.println("1. 등록하기 상세");
//                                break;
//                            case 0:
//                                break;
//                            default:
//                                System.out.println("올바른 메뉴를 선택하세요.");
//                        }
//                    }
//                    break;
//                case 0:
//                    System.out.println("프로그램을 종료합니다.");
//                    break;
//                default:
//                    System.out.println("올바른 메뉴를 선택하세요.");
//            }
//        }
//
//        sc.close();
