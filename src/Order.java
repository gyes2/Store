import java.io.*;
import java.util.*;

public class Order {
    private static Map<Integer, OrderInfo> orders;
    private static Map<String, ProductInfo> orderProducts;
    static int orderNum = 0;
    public Order(){
        orders = new HashMap<>();
    }
    //주문 생성 - 고객이 상품목록을 보고 장바구니 추가,
    public void createOrder(Scanner scanner){
        Product product =  new Product();
        Payment payment = new Payment();

        //<상품명, 상품정보>
        orderProducts = product.loadProductFile();
        System.out.println("====== 상품 ======");
        for(Map.Entry<String, ProductInfo> entrySet : orderProducts.entrySet()){
            System.out.println(entrySet.getValue());
        }
        try {
            int shop = -1;
            while (shop != 0) {
                System.out.print("1. 주문(장바구니 담기) | 2. 결제 | 0. 종료: ");
                shop = scanner.nextInt();
                scanner.nextLine();
                switch (shop){
                    case 1:
                        System.out.print("상품명, 수량: ");
                        String tmpOrder = scanner.nextLine();
                        String tmpOrders[] = tmpOrder.split(",");
                        String orderName = Main.getLoginName();
                        String orderProduct = tmpOrders[0].trim();
                        ProductInfo productInfo = orderProducts.get(orderProduct);
                        int orderQuantity = Integer.parseInt(tmpOrders[1].trim());
                        if (productInfo.getQuantity() == 0) {
                            System.out.println("현재 상품은 재고가 없습니다.다른 상품을 골라주세요");
                            break;
                        }
                        else if(productInfo.getQuantity() < orderQuantity ){
                            System.out.println("재고가 "+productInfo.getQuantity()+"이므로 현재 입력하신 수량은 주문하기 어렵습니다.");
                        }

                        int orderPrice = (productInfo.getPrice()*orderQuantity);
                        String orderManageId = productInfo.getManageID();
                        OrderInfo orderInfo = new OrderInfo(orderName, orderManageId, orderProduct, orderQuantity, orderPrice, orderNum);
                        //orderList.add(orderInfo);
                        orders.put(orderNum, orderInfo);
                        orderNum++;
                        saveOrderFile(orders, "basket.csv");
                        break;
                    case 2:
                        //장바구니 파일에 저장.
                        saveOrderFile(orders, "basket.csv");
                        //결제 함수 부르기.
                        payment.payProduct(scanner);
                        break;
                    case 3:
                        saveOrderFile(orders, "basket.csv");
                        System.out.println("종료합니다.");
                        break;

                }
            }
        }catch (InputMismatchException e)  {
            System.out.println("숫자를 입력하여 주세요.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //주문 조회 - 장바구니(로그인한 유저마다 파일로 생성)
    public void readOrder(Scanner scanner) throws FileNotFoundException {
        System.out.println("주문 목록: ");
        Order rOrder = new Order();
        orders = rOrder.loadOrderFile("basket.csv");
        for (Map.Entry<Integer, OrderInfo> entry : orders.entrySet()){
            System.out.println(entry.getValue());
        }
        saveOrderFile(orders,"basket.csv");
    }
    //장바구니 - 주문 수정/삭제
    //수량변경
    public void updateOrder(String product, int quantity) throws FileNotFoundException {
        System.out.println("1. 수량 변경");
        Order rOrder = new Order();
        orders = rOrder.loadOrderFile("basket.csv");
        for (OrderInfo oList : orders.values()){
            if(oList.getOrderProduct() == product){
                oList.setOrderQuantity(quantity);
                System.out.println("수량이 변경되었습니다.");
                break;
            }
        }
        //수량 변경 저장
        saveOrderFile(orders,"basket.csv");
    }

    //상품 삭제
    public void updateOrder(String del_productName) throws FileNotFoundException {
        System.out.println("2. 상품 삭제");

        Order rOrder = new Order();
        orders = rOrder.loadOrderFile("basket.csv");
        for (OrderInfo oList : orders.values()){
            if(oList.getOrderProduct() == del_productName){
                orders.remove(oList);
                System.out.println("삭제되었습니다.");
                break;
            }
        }
        //삭제된거 장바구니 파일에 반영
        saveOrderFile(orders,"basket.csv");
    }




    //주문 파일 로드
    public Map<Integer,OrderInfo> loadOrderFile(String filename) throws FileNotFoundException {
        String folder_path = "C:\\data\\" + Main.getLoginName();
        File folder = new File(folder_path);
        if(!folder.exists()) {
            System.out.println("파일없어서 생성 중");
            if (folder.mkdirs()) {
                System.out.println("폴더가 새로 생성되었습니다.");
            } else {
                System.out.println("파일 생성에 실패했습니다.");
            }
        }
        File o_file = new File(folder,filename);

        //파일 없으면 새로 생성해주기
        if(!o_file.exists()){
            System.out.println("파일없어서 생성 중");
            try{
                if(o_file.createNewFile()){
                    System.out.println("파일이 새로 생성되었습니다.");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                System.out.println("파일이 생성되지 않았습니다.");
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(o_file))){
            String line;

            while((line = bufferedReader.readLine()) != null){
                String o_fileds[] = line.split(",");

                String orderName = o_fileds[0];
                String orderManage = o_fileds[1];
                String productName= o_fileds[2];
                int orderQuantity= Integer.parseInt(o_fileds[3].trim()) ;
                int orderPrice = Integer.parseInt(o_fileds[4].trim());
                int orderNum = Integer.parseInt(o_fileds[5].trim());

                OrderInfo orderInfo = new OrderInfo(orderName, orderManage, productName,orderQuantity,orderPrice,orderNum);
                orders.put(orderNum, orderInfo);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    //파일 저장
    public void saveOrderFile(Map<Integer, OrderInfo> saveOrders, String filename){
        String s_path = "C:\\data\\" + Main.getLoginName() + "\\" + filename;
        File s_ofile = new File(s_path);
        if(!s_ofile.exists()){
            try{
                if(s_ofile.createNewFile()){
                    System.out.println("파일을 생성하였습니다.");
                }
                else {
                    System.out.println("파일 생성 실패");
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(s_ofile))){
            for(OrderInfo orderInfo : saveOrders.values()){
                String w_line = orderInfo.getOrderName()+","+orderInfo.getOrderManageId()+ ","+ orderInfo.getOrderProduct()
                        +","+orderInfo.getOrderQuantity()+"," +orderInfo.getOrderPrice()+","+orderInfo.getOrderNum();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
            System.out.println("파일 저장이 완료되었습니다.");
        } catch (IOException e) {
            System.out.println("파일저장이 되지 못했습니다.");
        }
    }
}
