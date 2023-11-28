/**
  * Copyright 2023 bejson.com 
  */
package com.xxxy.yjw.handerandokhttp.model;
import java.util.List;

/**
 * Auto-generated: 2023-11-27 15:36:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int id;
    private String shopName;
    private int saleNum;
    private int offerPrice;
    private int distributionCost;
    private String welfare;
    private String time;
    private String shopPic;
    private String shopNotice;
    private List<FoodList> foodList;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setShopName(String shopName) {
         this.shopName = shopName;
     }
     public String getShopName() {
         return shopName;
     }

    public void setSaleNum(int saleNum) {
         this.saleNum = saleNum;
     }
     public int getSaleNum() {
         return saleNum;
     }

    public void setOfferPrice(int offerPrice) {
         this.offerPrice = offerPrice;
     }
     public int getOfferPrice() {
         return offerPrice;
     }

    public void setDistributionCost(int distributionCost) {
         this.distributionCost = distributionCost;
     }
     public int getDistributionCost() {
         return distributionCost;
     }

    public void setWelfare(String welfare) {
         this.welfare = welfare;
     }
     public String getWelfare() {
         return welfare;
     }

    public void setTime(String time) {
         this.time = time;
     }
     public String getTime() {
         return time;
     }

    public void setShopPic(String shopPic) {
         this.shopPic = shopPic;
     }
     public String getShopPic() {
         return shopPic;
     }

    public void setShopNotice(String shopNotice) {
         this.shopNotice = shopNotice;
     }
     public String getShopNotice() {
         return shopNotice;
     }

    public void setFoodList(List<FoodList> foodList) {
         this.foodList = foodList;
     }
     public List<FoodList> getFoodList() {
         return foodList;
     }

}