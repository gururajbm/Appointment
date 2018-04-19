package Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDao {

    /**
     * Create a slot
     * @param slot
     * @return
     */
    public boolean createSlot(Slot slot) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement ps
                = connection.prepareStatement(
                    "INSERT INTO slot VALUES (NULL, ?, ?, ?, ?)"
                );
            ps.setString(1, slot.getStartTime());
            ps.setString(2, slot.getEndTime());
            ps.setInt(3, slot.getCreateUserId());
            ps.setString(4, "free");
            int result = ps.executeUpdate();
            if (result == 1) {

                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Create a slot
     * @param slot
     * @return
     */
    public boolean createAppointment(Slot slot) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement ps
                    = connection.prepareStatement(
                    "INSERT INTO slot VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, slot.getStartTime());
            ps.setString(2, slot.getEndTime());
            ps.setInt(3, slot.getCreateUserId());
            ps.setString(4, "booked");
            ps.setInt(5, slot.getBookedUserId());
            int result = ps.executeUpdate();
            if (result == 1) {

                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean deleteSlot(Integer id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM slot WHERE id=" + id);
            if (i == 1) {

                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Get all available slots between give date range
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Slot> getSlotsBetweenStartAndEndTime(String startTime, String endTime) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement ps
                = connection.prepareStatement(
                    "SELECT * FROM slot WHERE start_time " +
                    " BETWEEN ? AND ? OR ? BETWEEN start_time AND end_time"
                );
            ps.setString(1, startTime);
            ps.setString(2, endTime);
            ps.setString(3, startTime);
            ResultSet rs = ps.executeQuery();

            List slots = new ArrayList();

            while (rs.next()) {
                Slot slot = extractSlotFromResultSet(rs);
                slots.add(slot);
            }

            return slots;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * hydrate db result set to object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Slot extractSlotFromResultSet(ResultSet rs) throws SQLException {
        Slot slot = new Slot();
        slot.setId( rs.getInt("id") );
        slot.setStartTime( rs.getString("start_time") );
        slot.setEndTime( rs.getString("end_time") );
        slot.setCreateUserId(rs.getInt("create_user_id") );
        slot.setStatus(rs.getString("status") );
        slot.setBookedUserId(rs.getInt("booked_user_id") );

        return slot;
    }
}