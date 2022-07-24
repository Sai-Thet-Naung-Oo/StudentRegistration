package shared.mapper;

import java.sql.ResultSet;

import model.Discount;



public class DiscountMapper {
	
	public Discount mapToDiscount(Discount discount, ResultSet rs) {
        try {
        discount.setDiscount_id(rs.getInt("discount_id"));
       discount.setDiscountName(rs.getString("discount_name"));
      discount.setRate(rs.getInt("rate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    return discount;
	}
}
