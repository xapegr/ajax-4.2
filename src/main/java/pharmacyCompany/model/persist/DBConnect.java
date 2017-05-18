/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacyCompany.model.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

/**
 * <strong>DBConnect.java</strong>
 * Encapsulates connection procedure to database.
 * Implements singleton pattern.
 * @author Jose Moreno
 */
public final class DBConnect {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String BD_URL = "jdbc:mysql://"+"127.0.0.1/pharmacyCompany";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static DBConnect instance = null;
    
    private static Logger logger;

    public Logger getLogger() {
        return logger;
    }
    
    protected DBConnect() throws ClassNotFoundException {
        Class.forName(DRIVER);
        logger = Logger.getLogger(DBConnect.class.getName());
        configLogger();
    }
    
    /**
     * <strong>getInstance()</strong>
     * gets an unique instance of DBConnect.
     * @return an instance of DBConnect.
     * @throws ClassNotFoundException if database driver cannot be loaded.
     */
    public static DBConnect getInstance() throws ClassNotFoundException {
      if(instance == null) {
         instance = new DBConnect();
      }
      return instance;
    }
    
    /**
     * <strong>getConnection()</strong>
     * establishes a connection to the database.
     * @return Connection to database.
     * @throws SQLException if connection error occurs.
     */
    public Connection getConnection() throws SQLException  {
            return DriverManager.getConnection(BD_URL, USER, PASSWORD);
    }
      
    /**
     * <strong>configLogger()</strong>
     * configures looger for database errors.
     */
    public void configLogger()  {
        try {
            // create log file manager.
            FileHandler fh = new FileHandler(
                    "resources/log/app%g.log", //pattern
                    10485760, //limit
                    3, // count
                    true); //append
            fh.setLevel(Level.ALL); // level
            fh.setFormatter(new SimpleFormatter()); //formatter
            //fh.setFormatter(new XMLFormatter()); //formatter
            // add log file manager.
            logger.addHandler(fh);
            // console manager is added automatically.
            // change if necessary the level of reporting.
            //Logger.getGlobal().getHandlers()[0].setLevel(Level.SEVERE);
            //Logger.getGlobal().getHandlers()[0].setLevel(Level.OFF); //turn off logging to console.
            // set the logging level.
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.INFO);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
