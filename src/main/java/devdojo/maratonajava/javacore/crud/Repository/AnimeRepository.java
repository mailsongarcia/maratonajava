package devdojo.maratonajava.javacore.crud.Repository;

import devdojo.maratonajava.javacore.crud.conn.ConnectionFactory;
import devdojo.maratonajava.javacore.crud.dominio.Anime;
import devdojo.maratonajava.javacore.crud.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class AnimeRepository {




    public static List<Anime> findByName(String name) {
        log.info("Find all animes by name '{}'", name);
        List<Anime> animes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementFindByName(conn, name); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producer producer = Producer
                        .ProducerBuilder.
                        builder()
                        .name(rs.getString("producer_name"))
                        .id(rs.getInt("id_producer"))
                        .build();
                Anime anime = Anime.AnimeBuilder
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .episodes(rs.getInt("episodes"))
                        .producer(producer)
                        .build();
                animes.add(anime);

            }
        } catch (SQLException e) {
            log.error("Error while trying to find all animes in the database");
        }
        return animes;
    }

    private static PreparedStatement createPrepareStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = """
                select a.id, a.name, a.episodes, a.id_producer, p.name as 'producer_name' from anime_store.anime a inner join
                anime_store.producer p on a.id_producer = p.id 
                where a.name like ?""";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted anime '{}' in the database'{}'", id);
        } catch (SQLException e) {
            log.error("Error while trying Deleted anime '{}' in the database", id, e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement createPrepareStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM anime_store.anime WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void save(Anime anime) {
        log.info("Saving anime '{}'", anime);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementSave(conn, anime)) {
            ps.execute();
            log.info("Save anime '{}'", anime);
        } catch (SQLException e) {
            log.error("Error while trying insert anime '{}' in the database", anime.getId(), e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement createPrepareStatementSave(Connection conn, Anime anime) throws SQLException {
        String sql = "INSERT INTO anime_store.anime (name, episodes, id_producer) VALUES(?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getProducer().getId());
        return ps;

    }

    public static Optional<Anime> findById(Integer id) {
        log.info("Find all id by id '{}'", id);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementFindById(conn, id); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producer producer = Producer
                        .ProducerBuilder.
                        builder()
                        .name(rs.getString("producer_name"))
                        .id(rs.getInt("id_producer"))
                        .build();
                return Optional.of( Anime.AnimeBuilder
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .episodes(rs.getInt("episodes"))
                        .producer(producer)
                        .build());


            }
        } catch (SQLException e) {
            log.error("Error while trying to find all animes in the database");
        }
        return Optional.empty();
    }

    private static PreparedStatement createPrepareStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = """
                select a.id, a.name, a.episodes, a.id_producer, p.name as 'producer_name' from anime_store.anime a inner join
                anime_store.producer p on a.id_producer = p.id 
                where a.id = ?""";        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void update(Anime anime) {
        log.info("Updating anime '{}'", anime);
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = updatePrepareStatementFindById(conn, anime)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying Updated anime '{}' in the database", anime.getId(), e);
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement updatePrepareStatementFindById(Connection conn, Anime anime) throws SQLException {
        String sql = "UPDATE anime_store.anime SET name = ?, episodes = ? WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getId());
        return ps;
    }
}
