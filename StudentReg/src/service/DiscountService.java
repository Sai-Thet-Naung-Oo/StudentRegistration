package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;
import model.Course;
import model.Discount;
import shared.mapper.DiscountMapper;

public class DiscountService {
	private DBConfig dbconfig;
	private DiscountMapper discountMapper;
	public DiscountService() {
		this.dbconfig=new DBConfig();
		this.discountMapper=new DiscountMapper();
		
	}
	
	public void createDiscount(Discount discount) {
		try {
			PreparedStatement ps=this.dbconfig.getConnection()
					.prepareStatement("INSERT INTO discount (discount_name,rate,status) VALUES (?,?,?);");
			ps.setString(1, discount.getDiscountName());
			ps.setDouble(2,discount.getRate());
			ps.setInt(3, 1);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}

}
	 public List<Discount> findAllDiscount() {
	        List<Discount> discountlist=new ArrayList<>();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM  discount WHERE status=1";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Discount discount=new Discount();
	            	discountlist.add(this.discountMapper.mapToDiscount(discount, rs));	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return discountlist;
	    }

	    public Discount   findById(String id) {
	    	Discount discount=new Discount();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM discount WHERE discount_id = " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	
	            	this.discountMapper.mapToDiscount(discount, rs);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        
			return discount;
	    }
	    public void updateDiscount(String id, Discount discount) {
		    try {
		        PreparedStatement ps = this.dbconfig.getConnection()
		                .prepareStatement("UPDATE discount SET discount_name=?,rate=? WHERE discount_id=?");

		        ps.setString(1,discount.getDiscountName());
		        ps.setDouble(2, discount.getRate());
		       
		        ps.setString(3, id);

		        ps.executeUpdate();
		        ps.close();
		    } catch (Exception e) {

		    	e.printStackTrace();
		    }
		}
	    public void deleteDiscount(String id) {
		  	  try {
		  	        PreparedStatement ps = this.dbconfig.getConnection()
		  	                .prepareStatement("UPDATE discount SET status=? WHERE discount_id=?");
		  	     
		  	        ps.setInt(1,0);
		  	        ps.setString(2, id);

		  	        ps.executeUpdate();
		  	        ps.close();
		  	    } catch (Exception e) {

		  	    	e.printStackTrace();
		  	    }
		  }
	    

}
