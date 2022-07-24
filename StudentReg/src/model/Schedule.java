package model;

import java.time.LocalDate;
import java.util.Objects;

public class Schedule {

	int schedule_id;
	private Room room;
	private Teacher teacher;
	private Course course;
	private Section section;
	private LocalDate start_date;
	LocalDate end_date;
	int regristration_count;

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public int getRegristration_count() {
		return regristration_count;
	}

	public void setRegristration_count(int regristration_count) {
		this.regristration_count = regristration_count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, room, section, teacher);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(course, other.course) && Objects.equals(room, other.room)
				&& Objects.equals(section, other.section) && Objects.equals(teacher, other.teacher);
	}

}
