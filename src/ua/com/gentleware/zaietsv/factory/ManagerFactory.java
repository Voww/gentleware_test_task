package ua.com.gentleware.zaietsv.factory;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.InstanceDAO;
import ua.com.gentleware.zaietsv.manager.*;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Voww on 18.12.2015.
 */
public class ManagerFactory {

    private Connection connection;
    private Scanner scanner;
    private String command;
    private ClientManager clientManager;
    private AccountManager accountManager;
    private PaymentManager paymentManager;
    private DefaultManager defaultManager;



    public ManagerFactory() {
    }

    public Manager produce() {
        Manager manager;
        InstanceDAO dao;
        switch (command) {
            case "createClient": case "readClient": case "updateClient": case "deleteClient": case "readClientsList":
            case "getClientBalance": case "getClientWithMaxBalance": case "getClientWithMinBalance":
            case "getClientWithMinBalanceLastName": case "getClientWithMaxBalanceLastName":
                if (clientManager == null) {
                    manager = new ClientManager(connection);
                    manager.setScanner(scanner);
                    manager.setCommand(command);
                } else {
                    manager = clientManager;
                }
                break;
            case "createAccount": case "readAccount": case "updateAccount": case "updateAccountAmount":
            case "deleteAccount": case "readAccountsList": case "readClientAccounts":
                if (accountManager == null) {
                    manager = new AccountManager(connection);
                    manager.setScanner(scanner);
                    manager.setCommand(command);
                } else {
                    manager = accountManager;
                }
                break;
            case "createPayment": case "readPayment": case "updatePayment": case "readPaymentsList":
                if (paymentManager == null) {
                    manager = new PaymentManager(connection);
                    manager.setScanner(scanner);
                    manager.setCommand(command);
                } else {
                    manager = paymentManager;
                }
                break;
            case "help":
            default:
                if (defaultManager == null) {
                    manager = new DefaultManager();
                } else {
                    manager = defaultManager;
                }

        }
        return manager;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
