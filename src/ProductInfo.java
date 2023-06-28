public class ProductInfo {
    private String category;
    private String productName;
    private int price;
    private int quantity;

    public ProductInfo (String category, String productName, int price, int quantity){
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return "종류:" + category + "상품명: "+ productName +"가격: " + price + "수량: "
                + quantity;
    }

}