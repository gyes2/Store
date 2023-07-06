package org.store;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Product {
    static Map<String, ProductInfo> products;
    static LoginInfo loginInfo;

    public Product(){
        products = new HashMap<>();
        loginInfo = new LoginInfo();
    }

    public void createProduct(Scanner scanner){
        int n = 0;
        while(n != 2){
            System.out.print("1. 등록 | 2. 종료 :");
            n = scanner.nextInt();
            scanner.nextLine();
            try{
                switch (n) {
                    case 1:
                        try{
                            System.out.print("등록을 원하시는 상품의 <종류,상품명,가격,수량>을 입력하세요: ");
                            String register = scanner.nextLine();
                            String[] fields = register.split(",");
                            String n_category = fields[0].trim();
                            String n_productName = fields[1].trim();
                            int n_price = Integer.parseInt(fields[2].trim());
                            int n_quantity = Integer.parseInt(fields[3].trim());
                            String n_manageId = loginInfo.getLoginName();
                            ProductInfo n_product = new ProductInfo(n_category, n_productName, n_price, n_quantity,n_manageId);
                            products.put(n_productName, n_product);
                            System.out.println(products);
                            break;
                        }catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("종류,상품명,가격,수량 형태로 입력하여 주세요.");
                            break;
                        }
                    case 2:
                        saveProductFile(products);
                        System.out.println("등록을 종료합니다.");
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하여 주세요.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("올바른 메뉴를 선택하여 주세요.");
                break;
            }
        }

    }

    /* 수정 */
    public void readProduct(Scanner scanner){
        //수정 부분
        products = loadProductFile();
        int m;
        System.out.print("1. 전체 상품 조회 | 2. 상품명으로 조회 | 3. 카테고리로 조회 | 0.종료: ");
        m = scanner.nextInt();
        scanner.nextLine();
        try{
            switch (m) {
                case 1:
                    System.out.println("====전체 조회====");
                    for(Map.Entry<String, ProductInfo> entrySet : products.entrySet()){
                        System.out.println(entrySet.getValue().toProdString()); /* 수정함 */
                    }
                    break;
                case 2:
                    System.out.print("어떤 상품 정보를 조회하시겠습니까? ");
                    String findProduct = scanner.nextLine();
                    if(products.containsKey(findProduct)){
                        // System.out.println(findProduct);
                        ProductInfo f_product = products.get(findProduct);
                        System.out.println(f_product.toProdString());
                    }
                    else{
                        System.out.println("해당 상품은 판매하고 있지 않은 제품입니다.");
                    }
                    break;
                case 3:
                    System.out.print("어떤 카테고리로 조회하시겠습니까?(농산물 | 수산물): ");
                    String findCategory = scanner.nextLine();
                    System.out.println("====="+findCategory+"인 상품 목록=====");
                    for(Map.Entry<String,ProductInfo> entry : products.entrySet()){
                        if(entry.getValue().getCategory().equals(findCategory)) {
                            System.out.println(entry.getValue().toProdString());
                        }
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하여 주세요.");
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("올바른 메뉴를 선택하여 주세요.");

        }
    }
    public void updateProduct(Scanner scanner){
        scanner.nextLine();
        if(products == null){
            System.out.println("현재 회원 목록 파일이 비어있습니다.");
        }
        System.out.print("수정하고 싶은 상품명을 입력해주세요: ");
        String beforeName = scanner.nextLine();
        if (products.containsKey(beforeName)) {
            ProductInfo productInfo = products.get(beforeName);
            System.out.println(productInfo.toProdString());
            try {
                int num = -1;
                while(num != 0) {
                    System.out.print("수정할 정보를 선택해 주세요: 1. 카테고리 2. 상품명 3. 가격 4. 수량 \n 수정 종료를 원할 시 0을 눌러주세요.");
                    num = scanner.nextInt();
                    scanner.nextLine();
                    switch (num) {
                        case 1:
                            String u_category = scanner.nextLine();
                            productInfo.setCategory(u_category);
                            break;
                        case 2:
                            String u_productName = scanner.nextLine();
                            productInfo.setProductName(u_productName);
                            break;
                        case 3:
                            int u_price = Integer.parseInt(scanner.nextLine().trim());
                            productInfo.setPrice(u_price);
                            break;
                        case 4:
                            int u_quantity = Integer.parseInt(scanner.nextLine().trim());
                            productInfo.setQuantity(u_quantity);
                            break;
                        case 0:
                            System.out.println("수정을 종료합니다.");
                            break;
                        default:
                            System.out.println("매뉴에 있는 번호를 눌러주세요.");
                            break;
                    }
                }
            }catch(InputMismatchException e){
                System.out.println("올바른 입력을 해주세요.");
            }
        }
        else{
            System.out.println("해당 상품이 존재하지 않습니다.");
        }
    }
    public void deleteProduct(Scanner scanner){
        while(true){
            try{
                System.out.println("====상품 삭제==== \n 삭제 종료를 원할 시 종료 를 입력해주세요.");
                System.out.print("삭제하고 싶은 상품명을 입력해 주세요.: ");
                String del_prod = scanner.nextLine();
                if(products.containsKey(del_prod) && !del_prod.equals("종료")) {
                    ProductInfo del_product = products.get(del_prod);
                    System.out.println("삭제할 상품 정보: " + del_product.toProdString());
                    System.out.print("정말 삭제하시겠습니까?(Y/N): ");
                    String yn = scanner.nextLine();
                    yn = yn.toUpperCase();
                    if (yn.equals("Y")) {
                        products.remove(del_prod);
                        System.out.println("삭제하였습니다.");
                        System.out.print("계속 삭제하시겠습니까?(Y/N): ");
                        String t = scanner.nextLine();
                        t = t.toUpperCase();
                        if (t.equals("N")) {
                            break;
                        }
                    }
                }
                else if (del_prod.equals("종료")){
                    System.out.print("정말 종료하시겠습니까?(Y/N): ");
                    String t = scanner.nextLine();
                    if (t.equals("Y")) {
                        break;
                    }
                }
                else {
                    System.out.println("삭제 가능한 상품이 없습니다.");
                }

            }
            catch (InputMismatchException e){
                System.out.println("입력이 잘못되었습니다.");
                break;
            }
        }

    }
    public Map<String,ProductInfo> loadProductFile(){
        File p_file = new File("C:\\data\\product.csv");

        //파일 없으면 새로 생성해주기
        if(!p_file.exists()){
            try{
                if(p_file.createNewFile()){
                    System.out.println("");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                System.out.println("파일 생성 exception.");
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p_file, Charset.forName("UTF-8")))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] p_fileds = line.split(",");
                String category = p_fileds[0];
                String productName = p_fileds[1];
                int price = Integer.parseInt(p_fileds[2]);
                int quantity = Integer.parseInt(p_fileds[3]);
                String p_manageId = loginInfo.getLoginName();

                ProductInfo productInfo = new ProductInfo(category,productName, price, quantity,p_manageId);
                products.put(productName, productInfo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            System.out.println("IOException");
        }catch (NullPointerException n ){
            System.out.println("Product 파일이 비어있습니다.");
        }
        return products;
    }

    public void saveProductFile(Map<String,ProductInfo> product){
        File p_file = new File("C:\\data\\product.csv");
        if(!p_file.exists()){
            try{
                if(p_file.createNewFile()){
                    System.out.println("");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                System.out.println("파일 생성 exception.");
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(p_file))){
            for(ProductInfo productInfo : product.values()){
                String w_line = productInfo.getCategory() + "," + productInfo.getProductName() + "," + productInfo.getPrice() + "," + productInfo.getQuantity();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
            System.out.println("파일 저장이 완료되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}