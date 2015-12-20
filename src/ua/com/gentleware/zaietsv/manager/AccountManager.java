package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.AccountInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.ClientInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.relation_dao.ClientToAccountRelationDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;
import ua.com.gentleware.zaietsv.gentleware_object.relation.ClientToAccountRelation;
import ua.com.gentleware.zaietsv.gentleware_object.relation.Relation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Voww on 18.12.2015.
 */
public class AccountManager extends AbstractManager<Account> {

    private AccountInstanceDAO accountDAO;
    private ClientInstanceDAO clientDAO;
    private ClientToAccountRelationDAO relationDAO;

    public AccountManager(Connection connection) {
        super(connection);
        accountDAO = new AccountInstanceDAO(connection);
        clientDAO = new ClientInstanceDAO(connection);
        relationDAO = new ClientToAccountRelationDAO(connection);
    }

    @Override
    public void manage() throws ManagerException {
        try {
            switch (command) {
                case "createAccount":
                        Relation<Client, Account> relation = createAccount();
                        System.out.println("Succes on " + command + ": " + relation);
                    break;
                case "readAccount":
                    System.out.println(readAccount());
                    break;
                case "updateAccount":
                    System.out.println(updateAccount());
                    break;
                case "updateAccountAmount":
                    System.out.println(updateAccountAmount());
                    break;
                case "deleteAccount":
                    System.out.println(deleteAccount());
                    break;
                case "readAccountsList":
                    System.out.println(readAccountsList());
                    break;
                case "readClientAccounts":
                    System.out.println(readClientAccounts());
                    break;

                default:
                    break;
            }
        } catch (ManagerException e) {
            throw new ManagerException("Fault on " + command, e);
        }

    }

    private Relation<Client, Account> createAccount() throws ManagerException {
        System.out.print("Enter client ID:");
        long clientID = scanner.nextLong();
        Client client = clientDAO.read(clientID);
        ClientToAccountRelation relation;
        if (client == null) {
            throw new ManagerException("The client ID '" + clientID + "' does not exists");
        } else {
            relation = new ClientToAccountRelation();
            relation.setBase(client);
            List<Account> accounts = new ArrayList<>();
            System.out.println("Enter Account fields:");
            System.out.print("code:");
            String code = scanner.next();
            System.out.print("alias:");
            String alias = scanner.next();
            System.out.print("amount:");
            String amount = scanner.next();
            System.out.print("description:");
            String description = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                description = in.readLine();
            }
            catch(Exception e) {
            }

            Account account = new Account();
            account.setCode(code);
            account.setAlias(alias);
            try {
                account.setAmount(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                System.out.println("Amount is not a number");
                throw new ManagerException("Amount is not a number");
            }
            account.setDescription(description);
            accounts.add(account);
            relation.setRelated(accounts);
            relationDAO.insert(relation);
        }
        return relation;
    }

    private Account readAccount() {
        System.out.print("Enter Account ID:");
        long id = scanner.nextLong();
        Account account = accountDAO.read(id);
        return account;
    }

    private Account updateAccount() throws ManagerException {
        System.out.print("Enter Account ID:");
        long id = scanner.nextLong();
        Account account = accountDAO.read(id);
        System.out.println(account);
        System.out.println("Type a new field value or asterisk * to skip a field.");
        System.out.print("code:");
        String code = scanner.next();
        System.out.print("alias:");
        String alias = scanner.next();
        System.out.print("amount:");
        String amount = scanner.next();
        System.out.print("description:");
        String description = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            description = in.readLine();
        }
        catch(Exception e) {
        }
        if (! code.equals("*")) {
            account.setCode(code);
        }
        if (! alias.equals("*")) {
            account.setAlias(alias);
        }
        if (! amount.equals("*")) {
            try {
                account.setAmount(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                throw new ManagerException("Amount is not a number");
            }
        }
        if (! description.equals("*")) {
            account.setDescription(description);
        }
        if (accountDAO.update(account) <= 0) {
            throw new ManagerException("Cannot update a account in a database");
        } else {
            System.out.println("An account was updated succesfully");
        }
        return account;
    }

    private Account updateAccountAmount() throws ManagerException {
        System.out.print("Enter Account ID:");
        long id = scanner.nextLong();
        Account account = accountDAO.read(id);
        System.out.println(account);
        System.out.println("Type a new field value or asterisk * to skip a field.");
        System.out.print("amount:");
        String amount = scanner.next();

        if (! amount.equals("*")) {
            try {
                account.setAmount(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                throw new ManagerException("Amount is not a number");
            }
        }

        if (accountDAO.update(account) <= 0) {
            throw new ManagerException("Cannot update a account in a database");
        } else {
            System.out.println("An account amount was updated succesfully");
        }
        return account;
    }

    private Account deleteAccount() throws ManagerException {
        System.out.print("Enter Account ID:");
        long id = scanner.nextLong();
        Account account = accountDAO.read(id);

        if (!accountDAO.delete(account)) {
            throw new ManagerException("Cannot delete a account from a database");
        } else {
            System.out.println("An account was deleted succesfully");
        }
        return account;
    }

    private List<Account> readAccountsList() throws ManagerException {
        List<Account> list = accountDAO.readAll();
        if (list == null) {
            throw new ManagerException("Cannot read accounts list from a database");
        }
        return list;
    }

    private ClientToAccountRelation readClientAccounts() throws ManagerException {
        ClientToAccountRelation relation;
        System.out.print("Enter client ID:");
        long clientID = scanner.nextLong();
        relation = relationDAO.read(clientID);

/*        Client client = clientDAO.read(clientID);
        if (client == null) {
            throw new ManagerException("The client ID '" + clientID + "' does not exists");
        }
        System.out.println(client);
        list = accountDAO.readClientAccounts(clientID);
        if (list == null) {
            throw new ManagerException("Cannot read accounts list from a database");
        }*/
        return relation;
    }
}
