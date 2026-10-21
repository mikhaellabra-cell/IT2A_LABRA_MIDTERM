package dao;

import model.Allocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocationDao {

    public boolean insert(Allocation a) {
        String sql = "INSERT INTO allocations (student_name, building, room_no, monthly_rent) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.studentName());
            ps.setString(2, a.building());
            ps.setString(3, a.roomNo());
            ps.setDouble(4, a.monthlyRent());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            return false;
        }
    }

    public List<Allocation> findAll() {
        List<Allocation> list = new ArrayList<>();
        String sql = "SELECT id, student_name, building, room_no, monthly_rent "
                + "FROM allocations ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Allocation(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("building"),
                        rs.getString("room_no"),
                        rs.getDouble("monthly_rent")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch failed: " + e.getMessage());
        }
        return list;
    }
}