package org.store;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class Payment {
    static Map<Integer, OrderInfo> payOrders;
    static Map<Integer, PaymentInfo> pays;

    //나중에 결제 후 수정된 물품정보들을 저장하기 위한 map
    static Map<String, ProductInfo> payAfterProduct;

    static int payNum = 0;
    static LoginInfo loginInfo = new LoginInfo();
    static String payDate;

    public Payment() {
        payOrders = new HashMap<>();
        payAfterProduct = new HashMap<>();
        pays = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        payDate = dateFormat.format(date);
    }

    //결제
    public void payProduct(Scanner scanner) throws FileNotFoundException {
        Order payOrder = new Order();
        Payment payment = new Payment();
        Member member = new Member();

        Map<String, MemberInfo> members = member.loadMembersFromFile();
        MemberInfo memberInfo = members.get(loginInfo.getLoginName());

        int totalPrice = 0;
        String checkOrder;
        //전체 주문 목록(해당 유저)
        //<상품명, 주문정보>
        payOrders = payOrder.loadOrderFile("basket.csv");

        //해댱 유저 장바구니 목록
        if (payOrders.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
        } else {
            System.out.println("===== 장바구니 목록 =====");
            for (OrderInfo order : payOrders.values()) {
                System.out.println(order.toOrderString());
                totalPrice += order.getOrderPrice();
            }
        }

        //결제하겠냐고 물어보기
        System.out.print("결제 하시겠습니까?(Y | N): ");
        String pay_yn = scanner.nextLine();
        if (Pattern.matches("^[a-zA-Z]*$", pay_yn)) {
            pay_yn = pay_yn.toUpperCase();
            if (pay_yn.equals("Y")) {
                for (OrderInfo order : payOrders.values()) {
                    checkOrder = "상품명: " + order.getOrderProduct() + " 수량: " + order.getOrderQuantity() +
                            " 가격: " + order.getOrderPrice();
                    System.out.println(checkOrder);
                }
                System.out.print("주문하실 배송지를 입력하여 주세요(1. 회원 배송지 | 2. 신규배송지): ");
                int addr = scanner.nextInt();
                scanner.nextLine();
                switch (addr) {
                    case 1:
                        String oldAddress = memberInfo.getAddress();
                        System.out.println("회원님의 배송지는 " + oldAddress + " 입니다.");
                        for (OrderInfo order : payOrders.values()) {
                            checkOrder = "상품명: " + order.getOrderProduct() + " 수량: " + order.getOrderQuantity() +
                                    " 가격: " + order.getOrderPrice();
                            System.out.println(checkOrder);
                            PaymentInfo paymentInfo = new PaymentInfo(loginInfo.getLoginName(), order.getOrderProduct(), order.getOrderQuantity(),
                                    order.getOrderPrice(), order.getOrderManageId(), totalPrice, payDate, oldAddress, payNum);
                            pays.put(payNum, paymentInfo);
                            payNum++;
                        }
                        break;
                    case 2:
                        System.out.print("원하는 배송지를 입력하여 주세요.: ");
                        String newAddress = scanner.nextLine();
                        for (OrderInfo order : payOrders.values()) {
                            checkOrder = "상품명: " + order.getOrderProduct() + " 수량: " + order.getOrderQuantity() +
                                    " 가격: " + order.getOrderPrice();
                            System.out.println(checkOrder);
                            PaymentInfo newpaymentInfo = new PaymentInfo(loginInfo.getLoginName(), order.getOrderProduct(), order.getOrderQuantity(),
                                    order.getOrderPrice(), order.getOrderManageId(), totalPrice, payDate, newAddress, payNum);
                            pays.put(payNum, newpaymentInfo);
                            payNum++;
                        }
                        break;
                    default:
                        System.out.println("1 또는 2를 입력하여 주세요.");
                        break;
                }
                System.out.println("====== 결제 내역 ======");
                for (PaymentInfo entry : pays.values()) {
                    System.out.println(entry.toPayString());
                }
                System.out.println("===== 총 가격: " + totalPrice + " 원 ======");
                payment.savePaymentFile(pays, payDate);
                payment.afterPayment();
                payment.deleteBasketFile();
            } else if (pay_yn.equals("N")) {
                System.out.println("이전 화면으로 돌아갑니다.");
            } else {
                System.out.println("Y 또는 N을 입력하여 주세요.");
            }
        } else {
            System.out.println("알파벳만 입력하여 주세요.");
        }

    }

    public void deletePay(Scanner scanner) {

        System.out.print("주문을 삭제할 회원의 ID를 입력하세요: ");
        String delName = scanner.nextLine();
        pays = loadPaymentFile(payDate);
        for (PaymentInfo entry : pays.values()) {
            if (entry.getPayName().equals(delName)) {
                pays.remove(entry.getPayNum());
                break;
            }
        }
        System.out.println("삭제가 완료되었습니다.");
    }

    //상품 수량 변경
    public void afterPayment() {
        Product afterProd = new Product();
        Payment payment = new Payment();
        payAfterProduct = afterProd.loadProductFile();
        pays = payment.loadPaymentFile(payDate);
        for (Map.Entry<Integer, PaymentInfo> entry : pays.entrySet()) {
            System.out.println(entry.getValue().toPayString());
        }
        for (PaymentInfo entry : pays.values()) {
            if (entry.getPayName().equals(loginInfo.getLoginName())) {
                try {
                    ProductInfo after = payAfterProduct.get(entry.getPayProduct());
                    System.out.println(after.toProdString());
                    int newQuantity = after.getQuantity() - entry.getPayQuantity();
                    after.setQuantity(Math.max(newQuantity, 0));
                }catch (NullPointerException n) {
                    System.out.println("상품파일이 비어있습니다.");
                }
            }
            afterProd.saveProductFile(payAfterProduct);
        }
    }
    //회원이 결제한 내역 조회
    public void cusReadPayment (String name){
        Payment readPay = new Payment();
        pays = readPay.loadPaymentFile(payDate);
        for (PaymentInfo payInfo : pays.values()) {
            if (payInfo.getPayName().equals(name)) {
                System.out.println(payInfo.toPayString());
            }
        }
    }
    //관리자는 모든 결제내역 볼수 있게 조회
    public void manageReadPayment () {
        Payment manageReadPay = new Payment();
        pays = manageReadPay.loadPaymentFile(payDate);
        for (PaymentInfo payInfo : pays.values()) {
            System.out.println(payInfo.toPayString());
        }
    }
    public void updateAddress(Scanner scanner, String name){
        Payment payment = new Payment();
        pays = payment.loadPaymentFile(payDate);
        System.out.print("변경할 주소를 입력하여 주세요: ");
        String newAddress = scanner.nextLine();
        for(Map.Entry<Integer,PaymentInfo> entry : pays.entrySet()){
            if(entry.getValue().getPayName().equals(name)){
                entry.getValue().setPayAddress(newAddress);
                System.out.println(entry.getValue().getPayAddress());
            }
        }
    }
    public Map<Integer, PaymentInfo> loadPaymentFile (String date){

        String folder = "C:\\data\\" + date;
        File folderPath = new File(folder);
        if (!folderPath.exists()) {
            if (folderPath.mkdirs()) {
                System.out.println(date + "_payment 폴더가 생성되었습니다.");
            } else {
                System.out.println("payment 폴더가 생성되지 못했습니다.");
            }
        }
        String path = folder + "\\payment.csv";

        File pay_file = new File(path);

        //파일 없으면 새로 생성해주기
        if (!pay_file.exists()) {
            try {
                if (pay_file.createNewFile()) {
                    System.out.println("Payment 파일이 새로 생성되었습니다.");
                } else {
                    System.out.println("Payment 파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pay_file, Charset.forName("UTF-8")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] pay_fileds = line.split(",");
                String pay_name = pay_fileds[0];
                String payProduct = pay_fileds[1];
                int payQuantity = Integer.parseInt(pay_fileds[2]);
                int payPrice = Integer.parseInt(pay_fileds[3]);
                String pay_manageId = pay_fileds[4];
                int totalPrice = Integer.parseInt(pay_fileds[5]);
                String pay_date = pay_fileds[6];
                String pay_adddres = pay_fileds[7];
                int pay_Num = Integer.parseInt(pay_fileds[8]);
                PaymentInfo paymentInfo = new PaymentInfo(pay_name, payProduct, payQuantity, payPrice, pay_manageId, totalPrice, pay_date, pay_adddres, pay_Num);
                pays.put(pay_Num, paymentInfo);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException n) {
            System.out.println("Payment 파일이 비어있습니다.");
        }
        return pays;
    }

    //주문 파일 저장하기
    public void savePaymentFile (Map < Integer, PaymentInfo > pay, String date){
        String folder = "C:\\data\\" + date;
        File folderPath = new File(folder);
        if (!folderPath.exists()) {
            if (folderPath.mkdirs()) {
                System.out.println(date + "_payment 폴더가 생성되었습니다.");
            } else {
                System.out.println("payment 폴더가 생성되지 못했습니다.");
            }
        }
        String path = folder + "\\payment.csv";

        File payFilePath = new File(path);
        if (!payFilePath.exists()) {
            try {
                if (payFilePath.createNewFile()) {
                    System.out.println("주문 파일이 새로 생성되었습니다.");
                } else {
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(payFilePath, true))) {
            for (PaymentInfo payment : pay.values()) {
                String w_line = payment.getPayName() + "," + payment.getPayProduct() + "," + payment.getPayQuantity() + "," + payment.getPayPrice() + ","
                        + payment.getPayProductManageId() + "," + payment.getPayPrice() + "," +
                        payment.getPayDate() + "," + payment.getPayAddress() + "," + payment.getPayNum();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
            System.out.println("Payment 파일 저장이 완료되었습니다.");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void deleteBasketFile () {
        String folder_path = "C:\\data\\" + loginInfo.getLoginName();
        File folder = new File(folder_path);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("");
            } else {
                System.out.println("basket폴더 생성에 실패했습니다.");
            }
        }
        String filename = folder_path + "basket.csv";
        File o_file = new File(folder, filename);

        //파일 없으면 새로 생성해주기
        if (!o_file.exists()) {
            try {
                if (o_file.createNewFile()) {
                    System.out.println("");
                } else {
                    System.out.println("basket파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                System.out.println("basket파일이 생성되지 않았습니다.");
            }
        }
        Path name = Paths.get(filename);
        try {
            Files.deleteIfExists(name);
        } catch (IOException e) {
            System.out.println("basket 파일을 삭제하는데 실패하였습니다.");
        }
    }
}
