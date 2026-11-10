package cesur.dam.inventarioappg1.data;

import cesur.dam.inventarioappg1.models.Equipo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipoJdbcDAO implements DAO<Equipo> {

    private DataSource ds;

    public EquipoJdbcDAO(DataSource ds){
        this.ds = ds;
    }

    @Override
    public Optional<Equipo> findById(Long id) {
        String sql = "select * from Equipo where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Equipo> findAll() {
        String sql = "select * from Equipo";
        List<Equipo> equipos = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                equipos.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipos;
    }

    @Override
    public Equipo save(Equipo obj) {
        String sql = "insert into Equipo (nombre, categoria, disponible) " +
                     "VALUES (?,?,?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, obj.getNombre());
            pst.setString(2, obj.getCategoria());
            pst.setBoolean(3, obj.getDisponible());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    @Override
    public void update(Equipo entity) {
        String sql = "update Equipo set nombre=?, categoria=?, disponible=? " +
                     "where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, entity.getNombre());
            pst.setString(2, entity.getCategoria());
            pst.setBoolean(3, entity.getDisponible());
            pst.setLong(4, entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from Equipo where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Métodos auxiliares */

    public static Equipo mapper(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setId(rs.getLong("id"));
        equipo.setNombre(rs.getString("nombre"));
        equipo.setCategoria(rs.getString("categoria"));
        equipo.setDisponible(rs.getBoolean("disponible"));
        return equipo;
    }

}
