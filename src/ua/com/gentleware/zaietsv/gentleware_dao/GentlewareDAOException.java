package ua.com.gentleware.zaietsv.gentleware_dao;

/**
 * An Exception class for genleware_test_task database access objects
 * Created by Voww on 20.12.2015.
 */
public class GentlewareDAOException extends Exception {

    public GentlewareDAOException() {
    }

    public GentlewareDAOException(String message) {
        super(message);
    }

    public GentlewareDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
