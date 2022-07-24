package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;
import model.Staff;
import model.StaffInfo;
import model.UserRole;
import shared.mapper.StaffMapper;

public class StaffService {
	private final DBConfig dbconfig;
	private final StaffMapper staffmapper;
	
	public   StaffService() {
		// TODO Auto-generated constructor stub
		this.dbconfig=new DBConfig();
		this.staffmapper = new StaffMapper();
		
	}
	public void createStaff(Staff staff) {
		try {
			PreparedStatement ps=this.dbconfig.getConnection()
					.prepareStatement("INSERT INTO staff (staff_name,staff_address,staff_NRC,staff_phone,role,status) VALUES (?,?,?,?,?,?);");
			ps.setString(1, staff.getStaffName());
			ps.setString(2, staff.getStaffAddress());
			ps.setString(3, staff.getStaffNRC());
			ps.setString(4, staff.getStdaffPhno());
		    ps.setString(5, staff.getRole());
			ps.setInt(6, 1);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}

}
	
	 public List<Staff> findAllStaff() {
	        List<Staff> stafflist = new ArrayList<>();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM staff where status=1";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	 Staff staff=new Staff();
	            	stafflist.add(this.staffmapper.mapToStaff(staff, rs));	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return stafflist;
	    }

	    public Staff findById(String id) {
	    	 Staff staff=new Staff();

	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM staff WHERE staff_no = " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	
	            	this.staffmapper.mapToStaff(staff, rs);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        
			return staff;
	    }
	    
	    public void updateStaff(String id, Staff staff) {
		    try {
		        PreparedStatement ps = this.dbconfig.getConnection()
		                .prepareStatement("UPDATE staff SET staff_name=?,staff_address=?,staff_NRC=?,staff_phone=?,username=?,password=?,role=?,status=? WHERE staff_no=?;");

		        ps.setString(1, staff.getStaffName());
		        ps.setString(2, staff.getStaffAddress());
		        ps.setString(3,staff.getStaffNRC());
		        ps.setString(4, staff.getStdaffPhno());
		        ps.setString(5, staff.getUsername());
				ps.setString(6,staff.getPassword());
				ps.setString(7,staff.getRole());
				ps.setBoolean(8, true);
		        ps.setString(9, id);

		        ps.executeUpdate();
		        ps.close();
		    } catch (Exception e) {

		    	e.printStackTrace();
		    }
		}
	    
	    
	    public void deleteStaff(String id) {
	  	  try {
	  	        PreparedStatement ps = this.dbconfig.getConnection()
	  	                .prepareStatement("update staff SET status=? WHERE staff_no=?");
              
	  	       ps.setInt(1, 0);
	  	        ps.setString(2, id);

	  	        ps.executeUpdate();
	  	        ps.close();
	  	    } catch (Exception e) {

	  	    	e.printStackTrace();
	  	    }
	  }
}