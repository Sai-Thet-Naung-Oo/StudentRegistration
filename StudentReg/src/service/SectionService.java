package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;
import model.Course;
import model.Section;

import shared.mapper.SectionMapper;

public class SectionService {

	private final DBConfig dbconfig;
	private final SectionMapper sectionmapper;

	public SectionService() {
		// TODO Auto-generated constructor stub
		dbconfig = new DBConfig();
		sectionmapper = new SectionMapper();
	}

	public List<Section> findAllSection() {
		List<Section> sectionlist = new ArrayList<>();
		try (Statement st = this.dbconfig.getConnection().createStatement()) {

			String query = "SELECT * FROM section";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Section section = new Section();
				sectionlist.add(this.sectionmapper.mapToSection(section, rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sectionlist;
	}

}
