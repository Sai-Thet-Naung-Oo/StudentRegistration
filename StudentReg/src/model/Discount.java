package model;

public class Discount {
	
	int discount_id;
	String discountName;
	int rate;
	
	public Discount() {
		super();
	}
	public Discount(int discount_id, String discountName, int rate) {
		super();
		this.discount_id = discount_id;
		this.discountName = discountName;
		this.rate = rate;
	}
	public int getDiscount_id() {
		return discount_id;
	}
	public void setDiscount_id(int discount_id) {
		this.discount_id = discount_id;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	

}
