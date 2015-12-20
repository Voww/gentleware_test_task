package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Instance;

import java.sql.Connection;
import java.util.Scanner;

/**
 * A wrapper interface for Manager instances
 * Created by Voww on 18.12.2015.
 */
public interface Manager<I extends Instance> {

    void manage() throws ManagerException;

    public Connection getConnection();

    void setConnection(Connection connection);

    Scanner getScanner();

    void setScanner(Scanner scanner);

    String getCommand();

    void setCommand(String command);
}
