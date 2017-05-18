package pharmacyCompany.model.persist;

import java.sql.ResultSet;
import java.util.Collection;
import pharmacyCompany.model.Entity;

/**
 * <strong>EntityInterface.java</strong>
 * Encapsulates the common characteristics of an entity.
 * @author Jose Moreno
 */
public interface EntityInterface {
    public String getQuery(String queryName);
    /**
     * <strong>fromResultSet()</strong>
     * reads current row from resultset and returns as an object.
     * @param result resultset object to get data from.
     * @return object entity from current position of cursor.
     */
    public Entity fromResultSet(ResultSet result);
    /**
     * <strong>fromResultSetList()</strong>
     * iterates through resultset and returns list of objects.
     * @param result resultset object to get data from.
     * @return list of entity objects with data contained in resultset.
     */
    public Collection<Entity> fromResultSetList(ResultSet result);   
    /**
     * <strong>find()</strong>
     * looks up in database the object 'entity'
     * @param entity to search for
     * @return EntityInterface object found or null if not found.
     */
    public Entity find(Entity entity);
    
    /**
     * <strong>insert()</strong>
     * inserts into the database 'entity'
     * @param entity to insert
     * @return number of rows affected.
     */
    public int insert(Entity entity);
    /**
     * <strong>update()</strong>
     * updates data for oldEntity, changing them to newEntity ones.
     * @param oldEntity old values
     * @param newEntity new values
     * @return number of rows affected.
     */
    public int update(Entity entity);
    /**
     * <strong>remove()</strong>
     * deletes the entity from the database.
     * @param entity to be removed
     * @return number of rows affected.
     */
    public int remove(Entity entity);
    /**
     * <strong>getQuery()</strong>
     * gets the sql code of query named as 'queryName'.
     * @param queryName name of the query to be retrieved.
     * @return query 
     */
    /**
     * <strong>findAll()</strong>
     * gets all entities from database.
     * @return list of entity objects from query.
     */
    public Collection<Entity> findAll();
}
