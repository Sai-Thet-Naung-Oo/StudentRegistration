package shared.mapper;

import java.sql.ResultSet;


import model.Room;

public class RoomMapper {

	public Room mapToRoom(Room room, ResultSet rs) {
		try {
			room.setRoom_id(rs.getInt("room_id"));
			room.setRoom_name(rs.getString("room_name"));
			room.setRoom_pc(rs.getInt("room_pc"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return room;
	}
}
