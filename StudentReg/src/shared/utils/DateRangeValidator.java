package shared.utils;

import java.time.LocalDate;

public class DateRangeValidator {

//	private final LocalDate startDate;
//	private final LocalDate endDate;

//	public DateRangeValidator(LocalDate startDate, LocalDate endDate) {
//		this.startDate = startDate;
//		this.endDate = endDate;
//	}

	public  static boolean isWithinRange(LocalDate testDate,LocalDate startDate, LocalDate endDate) {

		// exclusive startDate and endDate
		// return testDate.isBefore(endDate) && testDate.isAfter(startDate);
		// inclusive startDate and endDate
		return (testDate.isEqual(startDate) || testDate.isEqual(endDate)) 
				|| (testDate.isAfter(startDate) && testDate.isBefore(endDate)); // between start and end

	}

}
