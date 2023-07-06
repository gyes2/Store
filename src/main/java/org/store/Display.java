package org.store;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Display {
    static LoginInfo logininfo;

    static Member member;

    static Main main;
    public Display(){
        logininfo = new LoginInfo();
        member = new Member();
        main = new Main();
    }

    public static void mainDisplay(Scanner scanner){
        int choice = -1;
        while (choice != 0) {
            main.AutoClearConsole();
            System.out.println("=== 마트 시스템 ===");
            System.out.println("1. 로그인");
            System.out.println("2. 등록");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    Display.LoginDisplay(scanner);
                    main.EnterClearConsole();
                    break;
                case 2:
                    main.Register(scanner);
                    main.EnterClearConsole();
                    break;
                case 0:
                    System.out.print("정말 종료하시겠습니까? (Y | N): ");
                    String s = scanner.nextLine();
                    if(s.equals("Y")){
                        System.out.println("시스템 종료하겠습니다.");
                    }
                    else{
                        choice = -1;
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void managementDisplay(Scanner scanner) {
        int choice = -1;
        while (choice != 0) {
            main.AutoClearConsole();
            System.out.println("====== 관리자 페이지 입니다. ======");
            System.out.println("1. 회원 관리 시스템");
            System.out.println("2. 상품 관리 시스템");
            System.out.println("3. 주문 관리 시스템");  //주문 조회,주문 삭제,주문 수정
            System.out.println("0. 로그아웃");
            System.out.print("원하는 작업을 선택하세요: ");
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        userDisplay(scanner);
                        System.out.println("회원 관리 시스템이 종료되었습니다. ");
                        break;
                    case 2:
                        productDisplay();
                        break;
                    case 3:
                        paymentDisplay(scanner);
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

    public static void customerDisplay(Scanner scanner) {
        Order order = new Order();

        int choice = -1;
        try {
            while (choice != 0) {
                main.AutoClearConsole();
                System.out.println("===== 회원 페이지 입니다. =====");
                System.out.println("====== 회원 메뉴 입니다. ======");
                System.out.println("1. 마이 페이지");
                System.out.println("2. 주문 ");
                System.out.println("3. 장바구니 ");
                System.out.println("0. 종료");
                System.out.print("원하는 작업을 선택하세요: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        myPageDisplay(scanner);
                        main.EnterClearConsole();
                        break;
                    case 2:
                        order.createOrder(scanner);
                        main.EnterClearConsole();
                        break;
                    case 3:
                        Display.basketDisplay(scanner);
                        main.EnterClearConsole();
                        break;
                    case 0:
                        System.out.println("종료합니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴번호를 입력해주세요.");
                        main.EnterClearConsole();
                        break;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("숫자를 입력해주세요. ");
        }
        System.out.println("회원 페이지가 종료되었습니다.");
    }

    public static void LoginDisplay(Scanner scanner){

        int loginKind;
        //저장된 멤버 로드
        try{
            System.out.print("유형을 입력해주세요(1. 관리자 | 2. 회원): ");
            int kind = scanner.nextInt();
            scanner.nextLine();
            System.out.print("ID를 입력해주세요: ");
            logininfo.setLoginName(scanner.nextLine().trim());
            System.out.print("비밀번호를 입력해주세요: ");
            logininfo.setLoginPw(scanner.nextLine().trim());
            try{
                //로그인 함수 호출
                loginKind = main.LoginMember(kind,logininfo.getLoginName(),logininfo.getLoginPw());
                if(loginKind == 1){
                    managementDisplay(scanner);
                }
                else if (loginKind == 2){
                    customerDisplay(scanner);
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

    public static void myPageDisplay(Scanner scanner){
        Member myMember = new Member();
        Payment payment = new Payment();
        int my_choice = -1;

        while (my_choice != 0) {
            main.AutoClearConsole();
            System.out.println("== 마이 페이지 ==");
            System.out.println("1. 정보 조회");
            System.out.println("2. 정보 수정");
            System.out.println("3. 결제 내역 조회");
            System.out.println("4. 회원 탈퇴");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            try{
                my_choice = scanner.nextInt();
                scanner.nextLine();
                switch (my_choice) {
                    case 1:
                        System.out.println("1. 정보 조회");
                        myMember.readMember(logininfo.getLoginName(),logininfo.getLoginPw());
                        main.EnterClearConsole();
                        break;
                    case 2:
                        System.out.println("2. 정보 수정");
                        myMember.Update(scanner);
                        main.EnterClearConsole();
                        break;
                    case 3:
                        System.out.println("3. 결제 내역 조회");
                        payment.cusReadPayment(logininfo.getLoginName());
                        main.EnterClearConsole();
                        break;
                    case 4:
                        System.out.println("4. 회원 탈퇴");
                        myMember.deleteMember(logininfo.getLoginName());
                        mainDisplay(scanner);
                        main.EnterClearConsole();
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

    public static void userDisplay(Scanner scanner) {
        Member memberCRUD = new Member();
        // 회원 정보 파일로부터 읽어오기
        member.members = memberCRUD.loadMembersFromFile();
        System.out.println("파일로드");

        int choice = -1;
        while (choice != 0) {
            Main.AutoClearConsole();
            System.out.println("=== 회원 관리 시스템 ===");
            System.out.println("1. 회원 조회");
            System.out.println("2. 회원 수정");
            System.out.println("3. 회원 탈퇴");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                switch (choice) {
                    case 1:
                        member.Search(scanner);
                        main.EnterClearConsole();
                        break;
                    case 2:
                        member.Update(scanner);
                        main.EnterClearConsole();
                        break;
                    case 3:
                        member.Delete(scanner);
                        main.EnterClearConsole();
                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                        main.EnterClearConsole();
                        break;
                }
            }catch(InputMismatchException ime){
                scanner = new Scanner(System.in);
                System.out.println("잘못된 값이 입력되었습니다. 다시 입력해주세요");
            }
        }

    }

    public static void productDisplay(){
        Product product = new Product();
        product.products = product.loadProductFile();

        Scanner scanner = new Scanner(System.in);

        int p_choice = -1;
        while(p_choice != 0 ){
            main.AutoClearConsole();
            System.out.println("----상품관리 시스템----");
            System.out.println("1. 상품 등록");
            System.out.println("2. 상품 조회");
            System.out.println("3. 상품 정보 수정");
            System.out.println("4. 상품 삭제");
            System.out.println("0. 종료");
            try {
                System.out.print("원하는 작업을 선택해주세요: ");
                p_choice = scanner.nextInt();
                scanner.nextLine();
                switch (p_choice) {
                    case 1:
                        product.createProduct(scanner);
                        product.saveProductFile(product.products);
                        main.EnterClearConsole();
                        break;
                    case 2:
                        product.readProduct(scanner);
                        main.EnterClearConsole();
                        break;
                    case 3:
                        product.updateProduct(scanner);
                        main.EnterClearConsole();
                        break;
                    case 4:
                        product.deleteProduct(scanner);
                        main.EnterClearConsole();
                        break;
                    case 0:
                        System.out.println("상품관리 시스템을 종료하시겠습니까?(Y | N): ");
                        String y = scanner.nextLine();
                        if (y.equals("Y")) {
                            product.saveProductFile(product.products);
                            break;
                        }
                    default:
                        System.out.println("메뉴에 존재하는 번호를 입력하여 주세요.");
                        main.EnterClearConsole();
                        break;
                }//switch 끝
            }catch (InputMismatchException e){
                System.out.println("숫자를 입력하여 주세요.");
                break;
            }
        }
    }
    public static void basketDisplay(Scanner scanner){
        Payment basketToPay = new Payment();
        Order order = new Order();
        int b_choice = -1;
        while (b_choice != 0) {
            main.AutoClearConsole();
            System.out.println("===== 장바구니 =====");
            System.out.println("1. 장바구니 수정");
            System.out.println("2. 결제 ");
            System.out.println("3. 장바구니 조회");
            System.out.println("0. 장바구니 빠져나가기");
            System.out.print("원하는 작업을 선택하세요: ");

            try{
                b_choice = scanner.nextInt();
                scanner.nextLine();
                switch (b_choice) {
                    case 1:
                        System.out.println("1. 수량 변경 | 2. 주문 삭제 | 0. 종료: ");
                        try{
                            int num = scanner.nextInt();
                            scanner.nextLine();
                            switch (num){
                                case 1:
                                    System.out.print("변경하실 상품명과 수량을 적어주세요: ");
                                    String[] update = scanner.nextLine().split(",");
                                    String updateProduct = update[0].trim();
                                    int updateQuantity = Integer.parseInt(update[1].trim());
                                    order.updateOrder(updateProduct,updateQuantity);
                                    break;
                                case 2:
                                    System.out.print("삭제할 주문의 상품명을 적어주세요: ");
                                    String delProduct = scanner.nextLine().trim();
                                    order.updateOrder(delProduct);
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("1 또는 2를 입력하여 주세요.");
                            }
                        }catch (InputMismatchException e){
                            System.out.println("숫자를 입력하여 주세요.");
                        }

                        System.out.println("장바구니를 빠져나가겠습니다.");
                        main.EnterClearConsole();
                        break;
                    case 2:
                        basketToPay.payProduct(scanner);
                        main.EnterClearConsole();
                        break;
                    case 3:
                        order.readOrder();
                        main.EnterClearConsole();
                    case 0:
                        System.out.println("장바구니를 빠져나가겠습니다.");
                        main.EnterClearConsole();
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

    public static void paymentDisplay(Scanner scanner) {
        Payment payment = new Payment();
        int choice = -1;
        while (choice != 0) {
            main.EnterClearConsole();
            System.out.println("====== 주문 관리 페이지 입니다. ======");
            System.out.println("1. 주문 조회");
            System.out.println("2. 배송지 수정");
            System.out.println("3. 주문 삭제");
            System.out.println("0. 주문 관리 종료");
            System.out.print("원하는 작업을 선택하세요: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("주문 조회(1. 전체 주문 조회 | 2. 회원 주문 조회): ");
                        int r = scanner.nextInt();
                        scanner.nextLine();
                        switch (r) {
                            case 1:
                                payment.manageReadPayment();
                                break;
                            case 2:
                                payment.pays = payment.loadPaymentFile(payment.payDate);
                                System.out.print("조회할 회원 ID를 입력하여주세요: ");
                                String pay_name = scanner.nextLine();
                                payment.cusReadPayment(pay_name);
                                break;
                            default:
                                System.out.println("1 또는 2를 입력하여 주세요.");
                                break;
                        }
                        System.out.println("주문 관리 시스템이 종료되었습니다. ");
                        break;
                    case 2:
                        System.out.print("배송지를 수정할 회원의 ID를 입력해 주세요.: ");
                        String newAddressId = scanner.nextLine();
                        payment.updateAddress(scanner,newAddressId);
                        break;
                    case 3:
                        payment.deletePay(scanner);
                        break;
                    case 0:
                        System.out.println("메뉴가 종료되었습니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                        break;
                }
            } catch (IllegalStateException e) {
                System.out.println("숫자를 입력하여 주세요. ");
            }
        }
    }

}