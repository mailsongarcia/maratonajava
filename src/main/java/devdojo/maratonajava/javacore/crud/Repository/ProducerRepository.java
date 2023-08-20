package devdojo.maratonajava.javacore.crud.Repository;

import devdojo.maratonajava.javacore.crud.conn.ConnectionFactory;
import devdojo.maratonajava.javacore.crud.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProducerRepository {


    public static List<Producer> findAll() {
        log.info("Find all producers");

        String sql = "SELECT id, name  FROM anime_store.producer";
        List<Producer> producers = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producer producer = Producer
                        .ProducerBuilder
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producers.add(producer);

            }
        } catch (SQLException e) {
            log.error("Error while trying to find all producers in the database");
        }
        return producers;
    }

    public static List<Producer> findByName(String name) {
        log.info("Find all producers by name '{}'", name);
        List<Producer> producers = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementFindByName(conn, name);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producer producer = Producer
                        .ProducerBuilder
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producers.add(producer);

            }
        } catch (SQLException e) {
            log.error("Error while trying to find all producers in the database");
        }
        return producers;
    }

    private static PreparedStatement createPrepareStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM anime_store.producer where name like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted producer '{}' in the database'{}'", id);
        } catch (SQLException e) {
            log.error("Error while trying Deleted producer '{}' in the database", id, e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement createPrepareStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM anime_store.producer WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void save(Producer producer) {
        log.info("Saving producer '{}'", producer);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementSave(conn, producer)) {
            ps.execute();
            log.info("Save producer '{}'", producer);
        } catch (SQLException e) {
            log.error("Error while trying insert producer '{}' in the database", producer.getName(), e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement createPrepareStatementSave(Connection conn, Producer producer) throws SQLException {
        String sql = "INSERT INTO anime_store.producer (name) VALUES(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        return ps;

    }

    public static Optional<Producer> findById(Integer id) {
        log.info("Find all producers by id '{}'", id);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementFindById(conn, id);
             ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();

            return Optional.of(Producer
                    .ProducerBuilder
                    .builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build());

    } catch(
    SQLException e)

    {
        log.error("Error while trying to find all producers in the database");
    }
        return Optional.empty();
}

    private static PreparedStatement createPrepareStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM anime_store.producer where id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void update(Producer producer) {
        log.info("Updating producer '{}'", producer);
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = updatePrepareStatementFindById(conn, producer)) {
                ps.execute();
            }
         catch (SQLException e) {
            log.error("Error while trying Updated producer '{}' in the database", producer.getId(), e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement updatePrepareStatementFindById(Connection conn, Producer producer) throws SQLException {
        String sql = "UPDATE anime_store.producer SET name = '?' WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        ps.setInt(1, producer.getId());
        return ps;
    }
}
