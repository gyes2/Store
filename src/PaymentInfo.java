
import java.util.List;

public class PaymentInfo {

    private String payName;

    private List<OrderList> payInfo;

    private int totalPrice;
    private String payDate;

    public PaymentInfo(String payName, List<OrderList> payInfo, int totalPrice,String payDate) {
        this.payName = payName;
        this.payInfo = payInfo;
        this.payDate = payDate;
        this.totalPrice = totalPrice;
    }
    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }


    public List<OrderList> getPayInfo() {return payInfo;}

    public void setPayInfo(List<OrderList> payInfo) {this.payInfo = payInfo;}
    public int getTotalPrice() {return totalPrice;}

    public void setTotalPrice(int totalPrice) {this.totalPrice = totalPrice;}

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String toString(){
        return "회원 ID: " + payName + " / 결제내역: "+ payInfo + " / 총 결제 가격: " + totalPrice+" / 결제 날짜: " + payDate ;
    }


}
