package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author loq
 */
public class data {
    public static Connection dbConnection(){
        Connection con=null;
        try{
            con=DriverManager.getConnection("jdbc:oracle:thin:adv/adv@localhost:1521/XE");
            System.out.println("success");
        }
        catch(Exception ex){
            System.out.println(ex.toString());

        }
        return con;
    }


}
