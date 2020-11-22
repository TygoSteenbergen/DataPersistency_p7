import java.util.ArrayList;

public interface ReizigerDAO {
    void save(Reiziger reiziger);
    void delete(Reiziger reiziger);
    void update(Reiziger reiziger);
    Reiziger findById(int id);
    ArrayList<Reiziger> findByGbdatum(String datum);
    ArrayList<Reiziger> findAll();
}
