package pharmacyCompany.model.persist;

import pharmacyCompany.model.Entity;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import pharmacyCompany.model.Product;

public class ProductADO implements EntityInterface {

    private Properties queries;
    private static String PROPS_FILE;
    private static DBConnect dataSource;

    public ProductADO() throws IOException, ClassNotFoundException {
        //http://stackoverflow.com/questions/2161054/where-to-place-and-how-to-read-configuration-resource-files-in-servlet-based-app
        queries = new Properties();
        PROPS_FILE = "product_queries.properties";
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
            String name = res.getString("name");
            double price = res.getDouble("price");
            c = (Entity) new Product(id, name, price);
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
     * <strong>findByNick()</strong>
     *
     * @param entity entity to find.
     * @return entity found or null if not found.
     */
    public Collection<Entity> findByNick(Entity entity) {
        return null;
    }

    /**
     * <strong>insert()</strong>
     *
     * @param entity to insert.
     * @return number of entities inserted.
     */
    @Override
    public int insert(Entity entity) {
        int rowsAffected;
        Product product = (Product) entity;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());

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
        Product p = (Product) entity;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setInt(3, p.getId());

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
        Product p = (Product) entity;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, p.getId());
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
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));
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
