

//주문 클래스
class OrderInfo {
    private String orderName;
    private String orderManageId;
    private String orderProduct;
    private int orderQuantity;
    private int orderPrice;
    private int orderNum;

    public OrderInfo(String orderName, String orderManageId, String orderProduct, int orderQuantity, int orderPrice, int orderNum){
        this.orderName = orderName;
        this.orderManageId = orderManageId;
        this.orderProduct = orderProduct;
        this.orderQuantity  = orderQuantity;
        this.orderPrice = orderPrice;
        this.orderNum = orderNum;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderManageId() {
        return orderManageId;
    }

    public void setOrderManageId(String orderManageId) {
        this.orderManageId = orderManageId;
    }

    public String getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        this.orderProduct = orderProduct;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderNum(){
        return this.orderNum;
    }

    public void setOrderNum(int orderNum){
        this.orderNum = orderNum;
    }
    public String toString(){
        return "종류: " + orderName + " / 상품명: "+  orderProduct + " / 수량: " + orderQuantity+" / 가격: " + orderPrice ;
    }
}
