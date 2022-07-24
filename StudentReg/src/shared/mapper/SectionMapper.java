package shared.mapper;

import java.sql.ResultSet;

import model.Section;

public class SectionMapper {

	public Section mapToSection(Section section, ResultSet rs) {
		try {
			section.setSection_id(rs.getInt("section_id"));
			section.setSection_DayTime(rs.getString("section_DayTime"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return section;
	}
}
