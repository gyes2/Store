import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Payment {
    static Map<Integer, OrderInfo> payOrders;
    static Map<Integer, PaymentInfo> pays;
    static int payId = 0;

    //나중에 결제 후 수정된 물품정보들을 저장하기 위한 map
    static Map<String,ProductInfo> payAfterProduct;
    static PaymentInfo myPayment;
    static List<OrderList>  orderLists;
    public Payment(){
        payOrders = new HashMap<>();
        payAfterProduct = new HashMap<>();
        orderLists = new ArrayList<OrderList>();
    };
    //결제
    public void payProduct(Scanner scanner) throws FileNotFoundException {
        Order payOrder = new Order();
        Payment payment = new Payment();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
        String payDate = dateFormat.format(date);
        int totalPrice = 0;
        String checkOrder;
        //전체 주문 목록(해당 유저)
        //<상품명, 주문정보>
        payOrders = payOrder.loadOrderFile("basket.csv");

        //해댱 유저 장바구니 목록
        System.out.println("===== 장바구니 목록 =====");
        for(OrderInfo order : payOrders.values() ){
            System.out.println(order.toString());
            totalPrice += order.getOrderPrice();
        }
        //결제하겠냐고 물어보기
        System.out.println("결제 하시겠습니까?(Y | N): ");
        String pay_yn = scanner.nextLine();

        if(pay_yn.equals("Y")){
            for(OrderInfo order : payOrders.values() ){
                OrderList oList = new OrderList(order.getOrderProduct(),order.getOrderQuantity(),
                        order.getOrderPrice(),order.getOrderManageId());
                checkOrder = "상품명: "+ order.getOrderProduct()+" 수량: "+ order.getOrderQuantity()+
                            " 가격: "+ order.getOrderPrice();
                System.out.println(checkOrder);
                orderLists.add(oList);
            }
            System.out.println("===== 총 가격: "+ totalPrice +" 원 ======");
            PaymentInfo paymentInfo = new PaymentInfo(Main.getLoginName(), orderLists,totalPrice,payDate );
            pays.put(payId,paymentInfo);
            payment.savePaymentFile(pays);
            afterPayment();
        }
        else{
            System.out.println("장바구니 화면으로 변경됩니다.");
        }
    }
    //상품 수량 변경
    public void afterPayment(){
        Product afterProduct = new Product();
        Payment afterPay = new Payment();
        payAfterProduct = afterProduct.loadProductFile();
        pays = afterPay.loadPaymentFile();
        for(PaymentInfo p:pays.values()){
            List<OrderList> orderList = p.getPayInfo();
            for(OrderList oList:orderList){
                String pName = oList.getPayProduct();
                if(payAfterProduct.containsKey(pName)){
                    ProductInfo pInfo = payAfterProduct.get(pName);
                    int afterQuantity = pInfo.getQuantity() - oList.getPayQuantity();
                    if(afterQuantity < 0){
                        afterQuantity = 0;
                    }
                    pInfo.setQuantity(afterQuantity);
                }
            }
        }
    }
    //회원이 결제한 내역 조회
    public void readPayment(String name){
        Payment readPay = new Payment();
        pays = readPay.loadPaymentFile();
        for (PaymentInfo payInfo : pays.values()){
            if(payInfo.getPayName() == name){
                payInfo.toString();
            }
        }
    }
    //관리자는 모든 결제내역 볼수 있게 조회
    public void readPayment(){
        Payment manageReadPay = new Payment();
        pays = manageReadPay.loadPaymentFile();
        for (PaymentInfo payInfo : pays.values()){
            payInfo.toString();
        }
    }

    public void paymentMain(Scanner scanner){

        int pay_choice = -1;
        while(pay_choice != 0 ) {
            try {
                System.out.println("----주문관리 시스템----");
                System.out.println("1. 주문 조회");
                System.out.println("2. 회원 배송지 변경");
                System.out.println("3. 주문 삭제");
                System.out.println("0. 종료");
                System.out.print("원하는 작업을 선택해주세요: ");
                pay_choice = scanner.nextInt();
                switch (pay_choice) {
                    case 1:
                        try{
                            System.out.print("조회(1.전체 | 2. 특정회원): ");
                            int n = scanner.nextInt();
                            scanner.nextLine();
                            if(n == 1){
                                readPayment();
                            }
                            else if(n ==2){
                                System.out.print("회원 이름을 입력하세요: ");
                                String readName = scanner.nextLine();
                                readPayment(readName);
                            }
                            else {
                                System.out.println("1 또는 2를 입력하세요.");
                            }
                        }catch (InputMismatchException e){
                            System.out.println("정수를 입력하여 주세요.");
                        }
                        break;
                    case 2:
                        Member member = new Member();
                        System.out.print("수정해야하는 회원 ID를 입력하세요: ");
                        String updateID = scanner.nextLine();
                        Map<String,MemberInfo> updateMemInfo = member.loadMembersFromFile();
                        MemberInfo memberInfo = updateMemInfo.get(updateID);
                        System.out.println("변경 전 주소: "+memberInfo.getAddress());
                        System.out.print("변경할 주소를 적어주세요: ");
                        String updateAddr = scanner.nextLine();
                        memberInfo.setAddress(updateAddr);
                        member.saveMembersToFile();
                        break;
                    case 3:
                        Member dMember = new Member();
                        Payment payment = new Payment();
                        pays = payment.loadPaymentFile();
                        System.out.print("삭제해야하는 주문의 회원 ID를 입력하세요: ");
                        String dWantID = scanner.nextLine();
                        Map<String,MemberInfo> delMemInfo = dMember.loadMembersFromFile();
                        MemberInfo delMember = delMemInfo.get(dWantID);
                        for (PaymentInfo pay: pays.values()){
                            if(pay.getPayName() == dWantID){
                                pays.remove(pay);
                                payment.savePaymentFile(pays);
                                payment.afterPayment();
                                break;
                            }
                        }
                        break;

                    case 0:
                        System.out.println("주문관리 시스템을 종료하시겠습니까?(Y | N): ");
                        String y = scanner.nextLine();
                        if (y.equals("Y")) {
                            savePaymentFile(pays);
                            break;
                        }
                    default:
                        System.out.println("메뉴에 존재하는 번호를 입력하여 주세요.");
                        break;
                }//switch 끝
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하여 주세요.");
                break;
            }
        }
    }
    public Map<Integer,PaymentInfo> loadPaymentFile(){
        File pay_file = new File("C:\\data\\payment.csv");

        //파일 없으면 새로 생성해주기
        if(!pay_file.exists()){
            try{
                if(pay_file.createNewFile()){
                    System.out.println("주문 파일이 새로 생성되었습니다.");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pay_file))){
            String line;
            int nextId = 0;
            while((line = bufferedReader.readLine()) != null){

                String pay_fileds[] = line.split(",");
                String pay_name = pay_fileds[0];

                OrderList pay_orderList = new OrderList(pay_fileds[1],
                        Integer.parseInt(pay_fileds[2]),Integer.parseInt(pay_fileds[3]),pay_fileds[4]);
                orderLists.add(pay_orderList);
                int pay_price = Integer.parseInt(pay_fileds[5]);
                String pay_date = pay_fileds[6];
                PaymentInfo paymentInfo = new PaymentInfo(pay_name, orderLists, pay_price, pay_date);
                pays.put(nextId,paymentInfo);
                nextId++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pays;
    }

    //주문 파일 저장하기
    public void savePaymentFile(Map<Integer,PaymentInfo> payments){
        String path = null;
        path = "C:\\data\\payment.csv";

        File payFilePath = new File(path);
        if(!payFilePath.exists()){
            try{
                if(payFilePath.createNewFile()){
                    System.out.println("주문 파일이 새로 생성되었습니다.");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(payFilePath))){
            for (PaymentInfo payment: payments.values()){
                String w_line = payment.getPayName()+","+payment.getPayInfo()+","+payment.getTotalPrice()+","+payment.getPayDate();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("파일 저장이 완료되었습니다.");
    }
}
