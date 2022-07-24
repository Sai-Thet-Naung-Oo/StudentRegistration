package shared.mapper;

import java.sql.ResultSet;

import model.Course;



public class CourseMapper {

	public Course mapToCourse(Course course, ResultSet rs) {
        try {
        course.setCourse_id(rs.getInt("course_id"));
        course.setCourseName(rs.getString("course_name"));
        course.setPrice(rs.getInt("course_price"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    return course;
	}
}
