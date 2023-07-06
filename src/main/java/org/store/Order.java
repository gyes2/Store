package org.store;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Order {
    private static Map<Integer, OrderInfo> orders;
    static int orderNum = 0;
    static LoginInfo loginInfo = new LoginInfo();
    public Order(){
        orders = new HashMap<>();
    }
    //주문 생성 - 고객이 상품목록을 보고 장바구니 추가,
    public void createOrder(Scanner scanner){
        Product product =  new Product();
        Payment payment = new Payment();

        //<상품명, 상품정보>
        Map<String, ProductInfo> orderProducts = product.loadProductFile();

        try {
            int shop = -1;
            while (shop != 0) {
                System.out.println("====== 상품 ======");
                for(Map.Entry<String, ProductInfo> entrySet : orderProducts.entrySet()){
                    System.out.println(entrySet.getValue().toProdString());
                }
                System.out.print("1. 주문(장바구니 담기) | 2. 결제 | 0. 종료: ");
                shop = scanner.nextInt();
                scanner.nextLine();
                switch (shop){
                    case 1:
                        System.out.print("상품명, 수량: ");
                        String tmpOrder = scanner.nextLine();
                        String[] tmpOrders = tmpOrder.split(",");
                        String orderName = loginInfo.getLoginName();
                        String orderProduct = tmpOrders[0].trim();
                        try {
                            ProductInfo productInfo = orderProducts.get(orderProduct);
                            if (productInfo.getQuantity() == 0) {
                                System.out.println("현재 상품은 재고가 없습니다.다른 상품을 골라주세요");
                                break;
                            }
                            int orderQuantity = Integer.parseInt(tmpOrders[1].trim());
                            int orderPrice = (productInfo.getPrice()*orderQuantity);
                            String orderManageId = productInfo.getManageID();
                            String orderCategory = productInfo.getCategory();
                            OrderInfo orderInfo = new OrderInfo(orderName, orderManageId, orderProduct, orderQuantity, orderPrice, orderNum,orderCategory);
                            orders.put(orderNum, orderInfo);
                            orderNum++;
                            saveOrderFile(orders, "basket.csv");
                        }catch (NullPointerException n){
                            System.out.println("해당 상품은 현재 저희 쇼핑몰에 존재하지 않습니다.");
                        }
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
    public void readOrder() throws FileNotFoundException {
        System.out.println("주문 목록: ");
        Order rOrder = new Order();
        orders = rOrder.loadOrderFile("basket.csv");
        for (Map.Entry<Integer, OrderInfo> entry : orders.entrySet()){
            System.out.println(entry.getValue().toOrderString());
        }
    }
    //장바구니 - 주문 수정/삭제
    //수량변경
    public void updateOrder(String product, int quantity) throws FileNotFoundException {
        System.out.println("1. 수량 변경");
        Order rOrder = new Order();

        orders = rOrder.loadOrderFile("basket.csv");
        for (OrderInfo oList : orders.values()){
            if(oList.getOrderProduct().equals(product)){
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
            if(oList.getOrderProduct().equals(del_productName)){
                orders.remove(oList.getOrderNum());
                System.out.println("삭제되었습니다.");
                break;
            }
        }
        //삭제된거 장바구니 파일에 반영
        saveOrderFile(orders,"basket.csv");
    }




    //주문 파일 로드
    public Map<Integer,OrderInfo> loadOrderFile(String filename) {
        String folder_path = "C:\\data\\" + loginInfo.getLoginName();
        File folder = new File(folder_path);
        if(!folder.exists()) {
            System.out.println("");
            if (folder.mkdirs()) {
                System.out.println("");
            } else {
                System.out.println("사용자 이름 폴더 생성에 실패했습니다.");
            }
        }
        File o_file = new File(folder,filename);

        //파일 없으면 새로 생성해주기
        if(!o_file.exists()){
            try{
                if(o_file.createNewFile()){
                    System.out.println("");
                }
                else{
                    System.out.println("사용자 장바구니파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                System.out.println("사용자 장바구니 파일이 생성되지 않았습니다.");
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(o_file, Charset.forName("UTF-8")))){
            String line;

            while((line = bufferedReader.readLine()) != null){
                String[] o_fileds = line.split(",");

                String orderName = o_fileds[0];
                String orderManage = o_fileds[1];
                String productName= o_fileds[2];
                int orderQuantity= Integer.parseInt(o_fileds[3].trim()) ;
                int orderPrice = Integer.parseInt(o_fileds[4].trim());
                int orderNum = Integer.parseInt(o_fileds[5].trim());
                String orderCategory = o_fileds[6];

                OrderInfo orderInfo = new OrderInfo(orderName, orderManage, productName,orderQuantity,orderPrice,orderNum,orderCategory);
                orders.put(orderNum, orderInfo);
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException n){
            System.out.println("파일이 비어있습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    //파일 저장
    public void saveOrderFile(Map<Integer, OrderInfo> saveOrders, String filename){
        String s_path = "C:\\data\\" + loginInfo.getLoginName();
        File s_folder = new File(s_path);
        if(!s_folder.exists()){
            if(s_folder.mkdirs()){
                System.out.println("");
            }
            else {
                System.out.println("Order 파일 생성 실패");
            }
        }
        String s_opath = s_folder + "\\"+filename;
        File s_ofile = new File(s_opath);
        if(!s_ofile.exists()){
            try{
                if(s_ofile.createNewFile()){
                    System.out.println("");
                }
                else{
                    System.out.println("Order 파일을 생성하는데 실패하였습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(s_ofile))){
            for(OrderInfo orderInfo : saveOrders.values()){
                String w_line = orderInfo.getOrderName()+","+orderInfo.getOrderManageId()+ "," + orderInfo.getOrderProduct()
                        +","+orderInfo.getOrderQuantity()+"," +orderInfo.getOrderPrice() +","+orderInfo.getOrderNum()+","+orderInfo.getOrderCatetory();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
            System.out.println("Order 파일 저장이 완료되었습니다.");
        } catch (IOException e) {
            System.out.println("Order 파일저장이 되지 못했습니다.");
        }
    }
}
