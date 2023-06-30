

//주문 목록 클래스
public class OrderList {
    private String payProduct;
    private int payQuantity;
    private int payPrice;
    private String payProductManageId;

    public OrderList(String payProduct, int payQuantity, int payPrice, String payProductManageId){
        this.payProduct = payProduct;
        this.payQuantity = payQuantity;
        this.payPrice = payPrice;
        this.payProductManageId = payProductManageId;
    }

    public String getPayProduct() {
        return payProduct;
    }


    public int getPayQuantity() {
        return payQuantity;
    }

    public void setPayQuantity(int payQuantity) {
        this.payQuantity = payQuantity;
    }

    public int getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(int payPrice) {
        this.payPrice = payPrice;
    }
    public String getPayProductManageId() {
        return payProductManageId;
    }

    public void setPayProductManageId(String payProductManageId) {
        this.payProductManageId = payProductManageId;
    }

}
