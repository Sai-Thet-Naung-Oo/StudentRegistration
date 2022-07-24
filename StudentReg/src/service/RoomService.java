package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;
import model.Course;
import model.Room;
import shared.mapper.CourseMapper;
import shared.mapper.RoomMapper;

public class RoomService {
	private final DBConfig dbconfig;
	private final RoomMapper roommapper;

	public RoomService() {
		// TODO Auto-generated constructor stub
		dbconfig = new DBConfig();
		roommapper = new RoomMapper();
	}

	public List<Room> findAllRoom() {
		List<Room> roomlist = new ArrayList<>();
		try (Statement st = this.dbconfig.getConnection().createStatement()) {

			String query = "SELECT * FROM room";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Room room = new Room();
				roomlist.add(this.roommapper.mapToRoom(room, rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roomlist;
	}
}
