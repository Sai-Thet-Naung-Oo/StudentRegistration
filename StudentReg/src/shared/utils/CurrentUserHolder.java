package shared.utils;

import model.Staff;

public class CurrentUserHolder {

	private static Staff staff;
	
	private CurrentUserHolder() {}
	
	// static method
	public static Staff getCurrentUser() {
		return staff;
	}
	// static method to keep login user
	public static void setLoggedInUser(Staff staff) {
		CurrentUserHolder.staff = staff;
	}
}
