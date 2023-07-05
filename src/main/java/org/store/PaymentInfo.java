package org.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentInfo {
    private String payName;
    private String payProduct;
    private int payQuantity;
    private int payPrice;

    private String payProductManageId;
    private int totalPrice;
    private String payDate;
    private String payAddress;
    private int payNum;



    public PaymentInfo(String payName, String payProduct, int payQuantity,int payPrice,String payProductManageId, int totalPrice,String payDate, String payAddress, int payNum) {
        this.payName = payName;
        this.payProduct = payProduct;
        this.payQuantity = payQuantity;
        this.payPrice = payPrice;
        this.payProductManageId = payProductManageId;
        this.totalPrice = totalPrice;
        this.payDate = payDate;
        this.payAddress = payAddress;
        this.payNum = payNum;
    }
    public String getPayName() {

        return payName;
    }

    public void setPayName(String payName) {

        this.payName = payName;
    }
    public String getPayProduct() {
        return payProduct;
    }

    public void setPayProduct(String payProduct) {
        this.payProduct = payProduct;
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

    public int getTotalPrice(){

        return totalPrice;
    }

    public void setTotalPrice(int totalPrice){

        this.totalPrice = totalPrice;
    }
    public String getPayDate(){

        return payDate;
    }
    public void setPayDate(String payDate) {

        this.payDate = payDate;
    }
    public String getPayAddress() {

        return payAddress;
    }

    public void setPayAddress(String payAddress) {

        this.payAddress = payAddress;
    }


    public int getPayNum() {

        return payNum;
    }

    public void setPayNum(int payNum) {

        this.payNum = payNum;
    }

    public String toPayString(){
        return "회원 ID: " + payName + " | 결제물품: "+  payProduct+ " | 수량: "+payQuantity+" | 물품 결제 가격: "+payPrice+
                " | 총 결제 가격: " + totalPrice +" | 결제 날짜: " + payDate+" | 배송지: " + payAddress ;
    }


}
