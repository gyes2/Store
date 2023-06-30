import java.io.FileNotFoundException;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static String loginName;
    private static String loginPw;
    public static String getLoginName() {return loginName;}
    public static void setLoginName(String loginName) {Main.loginName = loginName;}
    public static String getLoginPw() {return loginPw;}
    public static void setLoginPw(String loginPw) {Main.loginPw = loginPw;}
    static Map<String, MemberInfo> loginMembers;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Member member = new Member();

        int choice = -1;
        while (choice != 0) {
            System.out.println("=== 마트 시스템 ===");
            System.out.println("1. 로그인");
            System.out.println("2. 등록");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    LoginDisplay(scanner);
                    break;
                case 2:
                    Register(scanner);
                    break;
                case 0:
                    System.out.print("정말 종료하시겠습니까? (Y | N): ");
                    String s = scanner.nextLine();
                    if(s.equals("Y")){
                        System.out.println("시스템 종료하겠습니다.");
                        break;
                    }
                    else{
                        choice = -1;
                        break;
                    }
                default:
                    break;
            }
        }
    }

    public static void Management_display(Scanner sc) {
        System.out.println("====== 관리자 페이지 입니다. ======");
        Member member = new Member();
        Product product = new Product();
        Payment payment = new Payment();
        int choice = -1;
        while (choice != 0) {
            System.out.println("1. 회원 관리 시스템");
            System.out.println("2. 상품 관리 시스템");
            System.out.println("3. 주문 관리 시스템");  //주문 조회,주문 삭제,주문 수정
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");
            try{
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        member.main(sc);
                        System.out.println("회원 관리 시스템이 종료되었습니다. ");
                        break;
                    case 2:
                        product.productMain(sc);
                        break;
                    case 3:
                        payment.paymentMain(sc);
                        break;
                    case 0:
                        System.out.println("메뉴가 종료되었습니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                        break;
                }
            }catch (IllegalStateException e){
                System.out.println("숫자를 입력하여 주세요. ");
            }

        }

    }

    public static void Customer_display(Scanner sc) {
        System.out.println("===== 회원 페이지 입니다. =====");
        Member member = new Member();
        Order order = new Order();
        Payment payment = new Payment();
        int choice = -1;
        try {
            while (choice != 0) {
                System.out.println("==회원 매뉴==");
                System.out.println("1. 마이 페이지");
                System.out.println("2. 주문 ");
                System.out.println("3. 장바구니 ");
                System.out.println("0. 로그아웃");
                System.out.print("원하는 작업을 선택하세요: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        myPageDisplay(sc);
                        break;
                    case 2:
                        order.createOrder(sc);
                        break;
                    case 3:
                        basketDisplay(sc);
                        break;
                    case 0:
                        choice = 0;
                        loginName = null;
                        loginPw = null;
                        System.out.println("로그아웃되었습니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴번호를 입력해주세요.");
                        break;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("숫자를 입력해주세요. ");
        }
        System.out.println("회원 페이지가 종료되었습니다.");
    }

    //로그인 페이지
    public static void LoginDisplay(Scanner scanner){
        Member LoginCase = new Member();
        int loginKind = 0;
        //저장된 멤버 로드
        loginMembers = LoginCase.loadMembersFromFile();
        try{
            System.out.print("유형을 입력해주세요(1. 관리자 | 2. 회원): ");
            int kind = scanner.nextInt();
            scanner.nextLine();
            System.out.print("ID를 입력해주세요: ");
            loginName = scanner.nextLine();
            System.out.print("비밀번호를 입력해주세요: ");
            loginPw = scanner.nextLine();
            try{
                //로그인 함수 호출
                loginKind = LoginMember(kind,loginName,loginPw);
                if(loginKind == 1){
                    Management_display(scanner);
                }
                else if (loginKind == 2){
                    Customer_display(scanner);
                }
                else{
                    System.out.println("해당 회원을 찾을 수 없습니다. 올바른 정보를 입력하여 주세요.");
                }
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println("조회할 회원 파일이 비어있습니다.");
            }
        }catch (InputMismatchException e){
            System.out.println("다시 입력해 주세요. ");
        }

    }
    public static int LoginMember(int kind, String name, String password) {
        MemberInfo member = loginMembers.get(name);

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
        int userCase = 0;
        String name = null;
        loginMembers = member.loadMembersFromFile();
        while (t) {
            System.out.print("회원 ID: ");
            name = scanner.nextLine().trim();
            try{
                if (!loginMembers.containsKey(name)) {
                    System.out.println("가능한 ID입니다.");
                    t = false;
                    break;
                } else {
                    System.out.println("이미 존재하는 ID입니다.");
                }
            }catch (NullPointerException e){
                System.out.println("비어있습니다.");
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

    public static void myPageDisplay(Scanner scanner){
        Member myMember = new Member();
        Payment basketToOrder = new Payment();
        int my_choice = -1;

        while (my_choice != 0) {
            System.out.println("== 마이 페이지 ==");
            System.out.println("1. 정보 조회");
            System.out.println("2. 정보 수정");
            System.out.println("3. 회원 탈퇴");
            System.out.println("4. 결제 내역");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            try{
                my_choice = scanner.nextInt();
                scanner.nextLine();
                switch (my_choice) {
                    case 1:
                        System.out.println("1. 정보 조회");
                        myMember.readMember(loginName,loginPw);
                        break;
                    case 2:
                        System.out.println("2. 정보 수정");
                        myMember.Update(scanner);
                        break;
                    case 3:
                        System.out.println("3. 회원 탈퇴");
                        myMember.deleteMember(loginName);
                        break;
                    case 4:
                        System.out.println("4. 결제 내역");
                        basketToOrder.readPayment(loginName);
                        break;
                    case 0:
                        System.out.println("회원 페이지가 종료되었습니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴번호를 입력해주세요.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("숫자를 입력해주세요. ");
            }
        }
    }
    public static void basketDisplay(Scanner scanner){
        System.out.println("===== 장바구니 =====");
        Payment basketToOrder = new Payment();
        Order order = new Order();
        int b_choice = -1;
        while (b_choice != 0) {
            System.out.println("1. 장바구니 수정");
            System.out.println("2. 결제 ");
            System.out.println("3. 장바구니 내역");
            System.out.println("0. 장바구니 빠져나가기");
            System.out.print("원하는 작업을 선택하세요: ");

            try{
                b_choice = scanner.nextInt();
                scanner.nextLine();
                switch (b_choice) {
                    case 1:
                        try{
                            System.out.println("1. 수량 | 2. 주문 삭제");
                            int o = scanner.nextInt();
                            scanner.nextLine();
                            if(o == 1){
                                System.out.print("수량을 변경할 상품명, 변경할 수량: ");
                                String changes = scanner.nextLine();
                                String change[] = changes.split(",");
                                String ch_productName = change[0];
                                int ch_orderQuantity = Integer.parseInt(change[1].trim());
                                order.updateOrder(ch_productName,ch_orderQuantity);
                            }
                            else if(o == 2){
                                System.out.print("장바구니에서 삭제할 상품명을 입력해주세요: ");
                                String changes = scanner.nextLine();
                                order.updateOrder(changes);
                            }
                            else{
                                System.out.println("1 또는 2만 입력하여 주세요.");
                            }
                        }catch (InputMismatchException e){
                            System.out.println("숫자를 입력하여 주세요.");
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        basketToOrder.payProduct(scanner);
                        break;
                    case 3:
                        order.readOrder(scanner);
                    case 0:
                        System.out.println("장바구니를 빠져나가겠습니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴번호를 입력해주세요.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("숫자를 입력해주세요. ");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}