package pharmacyCompany.model.persist;

import pharmacyCompany.model.Entity;
import pharmacyCompany.model.User;
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

public class UserADO implements EntityInterface {

    private Properties queries;
    private static String PROPS_FILE;
    private static DBConnect dataSource;

    public UserADO() throws IOException, ClassNotFoundException {
        //http://stackoverflow.com/questions/2161054/where-to-place-and-how-to-read-configuration-resource-files-in-servlet-based-app
        queries = new Properties();
        PROPS_FILE = "user_queries.properties";
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
            String surname1 = res.getString("surname1");
            String nick = res.getString("nick");
            String password = res.getString("password");
            String address = res.getString("address");
            String telephone = res.getString("telephone");
            String mail = res.getString("mail");
            String birthDate = res.getString("birthDate");
            String entryDate = res.getString("entryDate");
            String dropOutDate = res.getString("dropOutDate");
            int active = res.getInt("active");
            String image = res.getString("image");
            int userType = res.getInt("userType");
            c = (Entity) new User(id, name, surname1, nick, password, address, telephone, mail, birthDate, entryDate, dropOutDate, active, image, userType);
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
     * <strong>find()</strong>
     *
     * @param entity entity to find.
     * @return entity found or null if not found.
     */
    @Override
    public Entity find(Entity entity) {
        User user = (User) entity;
        Entity entityFound;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND"));
            st.setInt(1, user.getId());
            ResultSet res = st.executeQuery();
            entityFound = fromResultSet(res);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            entityFound = null;
        }
        return entityFound;
    }

    /**
     * <strong>loggin()</strong>
     *
     * @param entity entity to find.
     * @return entity found or null if not found.
     */
    public Collection<Entity> loggin(Entity entity) {
        User user = (User) entity;
        //Entity entityFound;
        Collection<Entity> list = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            //PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE nick = ? and password = ?;");            
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_LOGGIN"));
            st.setString(1, user.getNick());
            st.setString(2, user.getPassword());
            ResultSet res = st.executeQuery();
            list = fromResultSetList(res);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            list = null;
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
        User user = (User) entity;
        //Entity entityFound;
        Collection<Entity> list = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            //PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE nick = ?;");            
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_BYNICK"));
            st.setString(1, user.getNick());
            ResultSet res = st.executeQuery();
            list = fromResultSetList(res);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            list = null;
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
        int rowsAffected;
        User user = (User) entity;
        try {
            Connection conn = dataSource.getConnection();
            //String insertSQL = "INSERT INTO users (name,surname1,nick,password,address,telephone,mail,birthDate,entryDate,dropOutDate,active,image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            //PreparedStatement st = conn.prepareStatement(insertSQL);
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setString(1, user.getName());
            st.setString(2, user.getSurname1());
            st.setString(3, user.getNick());
            st.setString(4, user.getPassword());
            st.setString(5, user.getAddress());
            st.setString(6, user.getTelephone());
            st.setString(7, user.getMail());
            st.setString(8, user.getBirthDate());
            st.setString(9, user.getEntryDate());
            st.setString(10, user.getDropOutDate());
            st.setInt(11, user.getActive());
            st.setString(12, user.getImage());
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
        User user = (User) entity;
        try {
            Connection conn = dataSource.getConnection();
            //String updateSQL= "UPDATE users SET name = ?, surname1 = ?, nick =?, password =?, address =?, telephone = ?, mail = ?, birthDate = ?, entryDate = ?, dropOutDate = ?, active = ?, image = ? WHERE id = ?;";

            //PreparedStatement st = conn.prepareStatement(updateSQL);
            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            st.setString(1, user.getName());
            st.setString(2, user.getSurname1());
            st.setString(3, user.getNick());
            st.setString(4, user.getPassword());
            st.setString(5, user.getAddress());
            st.setString(6, user.getTelephone());
            st.setString(7, user.getMail());
            st.setString(8, user.getBirthDate());
            st.setString(9, user.getEntryDate());
            st.setString(10, user.getDropOutDate());
            st.setInt(11, user.getActive());
            st.setString(12, user.getImage());
            st.setInt(13, user.getId());

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
        User user = (User) entity;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, user.getId());
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

}
