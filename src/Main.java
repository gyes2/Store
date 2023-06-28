import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Member member = new Member();

        System.out.println("=== 마트 시스템 ===");
        System.out.println("1. 로그인");
        System.out.println("2. 등록");
        System.out.println("0. 종료");

        int choice = -1;
        while (choice != 0) {
            System.out.print("원하는 작업을 선택하세요: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    member.LoginDisplay(scanner);
                    break;
                case 2:
                    member.Register(scanner);
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
        while (choice != 0) {
            System.out.println("===관리자 페이지===");
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
                    System.out.println("메뉴가 종료되었습니다.");
                    choice = 0;
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
                    break;
            }
        }

    }
    public static void Customer_display(Scanner sc) {
        Member member = new Member();
        int choice = -1;
        while (choice != 0) {
            System.out.println("===회원페이지===");
            System.out.println("1. 회원 관리");
            System.out.println("2. 상품 조회");
            System.out.println("3. 주문 관리");
            System.out.println("4. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    member.main(sc);
                    break;
                case 2:
                    System.out.println("2. 상품조회");
                    break;
                case 3:
                    System.out.println("3. 주문관리");
                    break;
                case 4:
                    System.out.println("메뉴가 종료되었습니다.");
                    choice = 0;
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
                    break;
            }
        }
    }
}
