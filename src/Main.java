import ua.com.gentleware.zaietsv.connector.Connector;
import ua.com.gentleware.zaietsv.connector.ConnectorException;
import ua.com.gentleware.zaietsv.connector.GentleWareTestTaskConnector;
import ua.com.gentleware.zaietsv.factory.ManagerFactory;
import ua.com.gentleware.zaietsv.manager.Manager;
import ua.com.gentleware.zaietsv.manager.ManagerException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main class for running gentleware_test_task project from console
 * Created by Voww on 18.12.2015.
 */
public class Main {

    public static void main(String[] args) {
        Connector connector = new GentleWareTestTaskConnector();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
        } catch (ConnectorException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ManagerFactory factory;
        Manager manager;
        Scanner scanner = new Scanner(System.in);
        String command = "default";
        factory = new ManagerFactory();
        factory.setConnection(connection);
        factory.setScanner(scanner);
        factory.setCommand(command);


        manager = factory.produce();
        try {
            manager.manage();
        } catch (ManagerException e) {
        }

        for (;;) {
            command = scanner.next();

            switch (command) {
                case "exit":
                    System.exit(0);
                    connector.closeConnection();
                    break;
                default:
                    factory.setCommand(command);
                    manager = factory.produce();


                    try {
                        manager.manage();
                        try {
                            connection.commit();
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (ManagerException e) {
                        System.err.println(e.getMessage());
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            System.err.println(e.getMessage());
                        }
                    }
            }
        }
    }
}
