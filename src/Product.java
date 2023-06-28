import java.io.*;
import java.util.*;

public class Product {
    private static Map<String, ProductInfo> products;

    public Product(){
        products = new HashMap<>();
    }

    public void createProduct(Scanner scanner){
        int n = 0;
        while(n != 2){
            System.out.print("1. 등록 | 2. 종료 :");
            n = scanner.nextInt();
            String str = scanner.nextLine();
            try{
                switch (n) {
                    case 1:
                        try{
                            System.out.print("등록을 원하시는 상품의 <종류,상품명,가격,수량>을 입력하세요: ");
                            String register = scanner.nextLine();
                            String fields[] = register.split(",");
                            String n_category = fields[0].trim();
                            String n_productName = fields[1].trim();
                            int n_price = Integer.parseInt(fields[2].trim());
                            int n_quantity = Integer.parseInt(fields[3].trim());
                            ProductInfo n_product = new ProductInfo(n_category, n_productName, n_price, n_quantity);
                            products.put(n_productName, n_product);
                            System.out.println(products);
                            break;
                        }catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("종류,상품명,가격,수량 형태로 입력하여 주세요.");
                            break;
                        }
                    case 2:
                        saveProductFile();
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
    };
    public void readProduct(Scanner scanner){
        int m = 0;
        while(m != 2){
            System.out.print("1. 전체 상품 조회 | 2. 상품명으로 조회 | 3. 카테고리로 조회: ");
            m = scanner.nextInt();
            String str = scanner.nextLine();
            try{
                switch (m) {
                    case 1:
                        System.out.println("====전체 조회====");
                        for(Map.Entry<String, ProductInfo> entrySet : products.entrySet()){
                            System.out.println(entrySet.getValue());
                        }
                        break;
                    case 2:
                        System.out.print("어떤 상품 정보를 조회하시겠습니까? ");
                        String findProduct = scanner.nextLine();
                        if(products.containsKey(findProduct)){
                            System.out.println(findProduct);
                            ProductInfo f_product = products.get(findProduct);
                            System.out.println(f_product.toString());
                        }
                        else{
                            System.out.println("해당 상품은 판매하고 있지 않은 제품입니다.");
                        }
                        break;
                    case 3:
                        System.out.print("어떤 카테고리로 조회하시겠습니까?(농산물 | 수산물): ");
                        String findCategory = scanner.nextLine();
//                        if(products.containsKey(findCategory)){
//                            Map.Entry<String, ProductInfo> entry : products.get(findCategory).
//                            ProductInfo f_product = products.get(findProduct);
//                            System.out.println(f_product.toString());
//                        }
//                        else{
//                            System.out.println("해당 상품은 판매하고 있지 않은 제품입니다.");
//                        }
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
    };
    public void updateProduct(Scanner scanner){
        if(products == null){
            System.out.println("현재 회원 목록 파일이 비어있습니다.");
        }
        System.out.println("수정하고 싶은 상품명을 입력해주세요: ");
        String beforeName = scanner.nextLine();
        if (products.containsKey(beforeName)) {
            ProductInfo productInfo = products.get(beforeName);
            System.out.println(productInfo.toString());
            int c = -1;
            while(c != 0) {
                try {
                    System.out.println("수정할 정보를 선택해 주세요: 1. 카테고리 2. 상품명 3. 가격 4. 수량 \n 수정 종료를 원할 시 0을 눌러주세요.");
                    int num = scanner.nextInt();
                    String str = scanner.nextLine();
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
                        default:
                            System.out.println("매뉴에 있는 번호를 눌러주세요.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("올바른 입력을 해주세요.");
                    break;
                }
            }

        }
        else{
            System.out.println("해당 상품이 존재하지 않습니다.");
        }
    };
    public void deleteProduct(Scanner scanner){
        boolean check = true;
        while(check){
            try{
                System.out.println("====상품 삭제==== \n 삭제 종료를 원할 시 종료 를 입력해주세요.");
                System.out.println("삭제하고 싶은 상품명을 입력해 주세요.: ");
                String del_prod = scanner.nextLine();
                if(products.containsKey(del_prod) && !del_prod.equals("종료")) {
                    ProductInfo del_product = products.get(del_prod);
                    System.out.println("삭제할 상품 정보: " + del_product.toString());
                    System.out.println("정말 삭제하시겠습니까?(Y/N): ");
                    String yn = scanner.nextLine();
                    if (yn.equals("Y")) {
                        products.remove(del_product);
                        System.out.println("삭제하였습니다.");
                    } else {
                        System.out.println("계속 삭제하시겠습니까?(Y/N): ");
                        String t = scanner.nextLine();
                        if (t.equals("Y")) {
                            continue;
                        }
                        else {
                            check = false;
                            break;
                        }
                    }
                }
                else if (del_prod.equals("종료")){
                    System.out.println("정말 종료하시겠습니까?(Y/N): ");
                    String t = scanner.nextLine();
                    if (t.equals("Y")) {
                        check = false;
                        break;
                    }
                    else {
                        continue;
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

    };
    public Map<String,ProductInfo> loadProductFile(){
        File p_file = new File("C:\\data\\product.csv");

        //파일 없으면 새로 생성해주기
        if(!p_file.exists()){
            try{
                if(p_file.createNewFile()){
                    System.out.println("파일이 새로 생성되었습니다.");
                }
                else{
                    System.out.println("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //파일 있으면 파일내용 읽어오기
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p_file))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                String p_fileds[] = line.split(",");
                String category = p_fileds[0];
                String productName = p_fileds[1];
                int price = Integer.parseInt(p_fileds[2]);
                int quantity = Integer.parseInt(p_fileds[3]);

                ProductInfo productInfo = new ProductInfo(category,productName, price, quantity);
                products.put(productName, productInfo);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public void saveProductFile(){
        File p_file = new File("C:\\data\\product.csv");
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(p_file))){
            for(ProductInfo productInfo : products.values()){
                String w_line = productInfo.getCategory() + "," + productInfo.getProductName() + "," + productInfo.getPrice() + "," + productInfo.getQuantity();
                bufferedWriter.write(w_line);
                bufferedWriter.newLine();
            }
            System.out.println("파일 저장이 완료되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void productMain(){

        Product product = new Product();
        products = product.loadProductFile();

        Scanner scanner = new Scanner(System.in);
        System.out.println("----물품관리 시스템----");
        System.out.println("1. 상품 등록");
        System.out.println("2. 상품 조회");
        System.out.println("3. 상품 정보 수정");
        System.out.println("4. 상품 삭제");
        System.out.println("0. 종료");

        int p_choice = -1;
        while(p_choice != 0 ){
            try {
                System.out.print("원하는 작업을 선택해주세요: ");
                p_choice = scanner.nextInt();
                switch (p_choice) {
                    case 1:
                        createProduct(scanner);
                        saveProductFile();
                        break;
                    case 2:
                        readProduct(scanner);
                        break;
                    case 3:
                        updateProduct(scanner);
                        break;
                    case 4:
                        deleteProduct(scanner);
                        break;
                    case 0:
                        System.out.println("상품관리 시스템을 종료하시겠습니까?(Y | N): ");
                        String y = scanner.nextLine();
                        if (y.equals("Y")) {
                            saveProductFile();
                            break;
                        }
                    default:
                        System.out.println("메뉴에 존재하는 번호를 입력하여 주세요.");
                        break;
                }//switch 끝
            }catch (InputMismatchException e){
                System.out.println("숫자를 입력하여 주세요.");
                break;
            }
        }
    }
}