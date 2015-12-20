package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.AccountInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.PaymentInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.relation_dao.PaymentRelationDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Payment;
import ua.com.gentleware.zaietsv.gentleware_object.relation.PaymentRelation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Voww on 18.12.2015.
 */
public class PaymentManager extends AbstractManager<Account> {

    private AccountInstanceDAO accountDAO;
    private PaymentInstanceDAO paymentDAO;
    private PaymentRelationDAO relationDAO;

    public PaymentManager(Connection connection) {
        super(connection);
        accountDAO = new AccountInstanceDAO(connection);
        paymentDAO = new PaymentInstanceDAO(connection);
        relationDAO = new PaymentRelationDAO(connection);
    }

    @Override
    public void manage() throws ManagerException {
        try {
            switch (command) {
                case "createPayment":
                        PaymentRelation payment = createPayment();
                        System.out.println("Succes on " + command + ": " + payment);
                    break;
                case "readPayment":
                    System.out.println(readPayment());
                    break;
                case "readPaymentsList":
                    System.out.println(readPaymentsList());
                    break;
                default:
                    break;
            }
        } catch (ManagerException e) {
            throw new ManagerException("Fault on " + command, e);
        }

    }

    private PaymentRelation createPayment() throws ManagerException {
        System.out.print("Enter from account ID:");
        long accountBaseID = scanner.nextLong();
        Account accountBase = accountDAO.read(accountBaseID);
        System.out.println(accountBase);
        System.out.print("Enter to account ID:");
        long accountRelatedID = scanner.nextLong();
        Account accountRelated = accountDAO.read(accountRelatedID);
        System.out.println(accountRelated);

        PaymentRelation relation;
        if (accountBase == null || accountRelated == null) {
            throw new ManagerException("The account ID does not exists");
        } else {
            relation = new PaymentRelation();
            relation.setBase(accountBase);
            List<Account> accounts = new ArrayList<>();
            accounts.add(accountRelated);
            relation.setRelated(accounts);

            System.out.println("Enter Payment fields:");
            System.out.print("amount: ");
            String amount = scanner.next();
            try {
                relation.setAmount(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                System.out.println("Amount is not a number");
                throw new ManagerException("Amount is not a number");
            }
            System.out.print("comment: ");
            String comment = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                comment = in.readLine();
                relation.setComment(comment);
            }
            catch(Exception e) {
                // Nothing to do
            }
            relation.setComment(comment);
            relation.pay();
            relationDAO.insert(relation);
        }
        return relation;
    }

    private PaymentRelation readPayment() {
        System.out.print("Enter Payment ID:");
        long id = scanner.nextLong();
        PaymentRelation payment = relationDAO.read(id);
        return payment;
    }

    private List<Payment> readPaymentsList() {
        List<Payment> list = paymentDAO.readAll();
        return list;
    }
}
