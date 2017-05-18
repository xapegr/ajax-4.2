package pharmacyCompany.model.persist;

import pharmacyCompany.model.Entity;
import pharmacyCompany.model.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import pharmacyCompany.model.Purchase;

public class PurchaseADO implements EntityInterface {

    private Properties queries;
    private static String PROPS_FILE;
    private static DBConnect dataSource;

    public PurchaseADO() throws IOException, ClassNotFoundException {
        //http://stackoverflow.com/questions/2161054/where-to-place-and-how-to-read-configuration-resource-files-in-servlet-based-app
        queries = new Properties();
        PROPS_FILE = "purchase_queries.properties";
        queries.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPS_FILE));
        dataSource = DBConnect.getInstance();
    }

    @Override
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    /**
     * <strong>getDataSource()</strong>
     *
     * @return object to connect to database.
     */
    public static DBConnect getDataSource() {
        return dataSource;
    }

    @Override
    public Entity fromResultSet(ResultSet res) {
        int id;
        Entity c;
        try {
            id = res.getInt("id");
            int idUser = res.getInt("idUser");
            int idProduct = res.getInt("idProduct");
            String deliveryDate = res.getString("deliveryDate");
            String specialRequests = res.getString("specialRequests");
            String specialInstructions = res.getString("specialInstructions");
            c = (Entity) new Purchase(id, idUser, idProduct, deliveryDate, specialRequests, specialInstructions);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            c = null;
        }
        return c;
    }

    @Override
    public Collection<Entity> fromResultSetList(ResultSet result) {
        Collection<Entity> list = new ArrayList<>();
        try {
            while (result.next()) {
                Entity entity = fromResultSet(result);
                if (entity != null) {
                    list.add(entity);
                }
            }
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * <strong>insert()</strong>
     *
     * @param entity to insert.
     * @return number of entities inserted.
     */
    @Override
    public int insert(Entity entity) {
        //SimpleDateFormat sdf = new SimpleDateFormat();
        int rowsAffected;
        Purchase purchase = (Purchase) entity;
        try {
            Connection conn = dataSource.getConnection();

            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setInt(1, purchase.getIdUser());
            st.setInt(2, purchase.getIdProduct());
            st.setString(3, purchase.getDeliveryDate());
            st.setString(4, purchase.getSpecialRequests());
            st.setString(5, purchase.getSpecialInstructions());
            rowsAffected = st.executeUpdate();
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    /**
     * <strong>update()</strong>
     *
     * @param oldEntity old values of entity
     * @param newEntity new values of entity
     * @return number of entities updated.
     */
    @Override
    public int update(Entity entity) {
        int rowsAffected;
        Purchase purchase = (Purchase) entity;
        try {
            Connection conn = dataSource.getConnection();

            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            st.setInt(1, purchase.getIdUser());
            st.setInt(2, purchase.getIdProduct());
            st.setString(3, purchase.getDeliveryDate());
            st.setString(4, purchase.getSpecialRequests());
            st.setString(5, purchase.getSpecialInstructions());
            st.setInt(6, purchase.getId());

            rowsAffected = st.executeUpdate();
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    /**
     * <strong>remove()</strong>
     *
     * @param entity to be removed.
     * @return number of entities removed.
     */
    @Override
    public int remove(Entity entity) {
        int rowsAffected;
        Purchase purchase = (Purchase) entity;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, purchase.getId());
            rowsAffected = st.executeUpdate();
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    @Override
    public Collection<Entity> findAll() {
        Collection<Entity> list = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("FIND_ALL");
            list = fromResultSetList(res);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Entity find(Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
