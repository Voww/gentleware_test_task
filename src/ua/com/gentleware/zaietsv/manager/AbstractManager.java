package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_object.instance.AbstractInstance;

import java.sql.Connection;
import java.util.Scanner;

/**
 * An abstract superclass for Manager instances
 * Created by Voww on 18.12.2015.
 */
public abstract class AbstractManager<I extends AbstractInstance> implements Manager<I> {

    protected Connection connection;
    protected Scanner scanner;
    protected String command;

    public AbstractManager() {
    }

    public AbstractManager(Connection connection) {
        this.connection = connection;
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
