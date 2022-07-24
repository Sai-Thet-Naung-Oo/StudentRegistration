package shared.mapper;

import java.sql.ResultSet;

import model.Staff;
import model.UserRole;



public class StaffMapper {
	public Staff mapToStaff(Staff staff, ResultSet rs) {
        try {
        	staff.setStaffNo(rs.getInt("staff_no"));
        	staff.setStaffName(rs.getString("staff_name"));
        	staff.setStaffAddress(rs.getString("staff_address"));
        	staff.setStaffNRC(rs.getString("staff_NRC"));
        	staff.setStdaffPhno(rs.getString("staff_phone"));
        	staff.setUsername(rs.getString("username"));
        	staff.setPassword(rs.getString("password"));
        	staff.setRole( rs.getString("role"));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    return staff;
	}

}
