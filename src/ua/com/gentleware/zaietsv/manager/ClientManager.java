package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.ClientInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.relation_dao.ClientToAccountRelationDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;
import ua.com.gentleware.zaietsv.gentleware_object.relation.ClientToAccountRelation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Voww on 18.12.2015.
 */
public class ClientManager extends AbstractManager<Client> {

    ClientInstanceDAO clientDAO;
    ClientToAccountRelationDAO clientToAccountDAO;

    public ClientManager(Connection connection) {
        super(connection);
        clientDAO = new ClientInstanceDAO(connection);
        clientToAccountDAO = new ClientToAccountRelationDAO(connection);
    }

    @Override
    public void manage() throws ManagerException {
        try {
            switch (command) {
                case "createClient":
                        Client client = createClient();
                        System.out.println("Succes on " + command + ": " + client);
                    break;
                case "readClient":
                    System.out.println(readClient());
                    break;
                case "updateClient":
                    System.out.println(updateClient());
                    break;
                case "deleteClient":
                    System.out.println(deleteClient());
                    break;
                case "readClientsList":
                    System.out.println(readClientsList());
                    break;
                case "getClientBalance":
                    System.out.println("Client Balance: " + getClientBalance());
                    break;
                case "getClientWithMaxBalance":
                    System.out.println("Client with maximim balance: " + getClientWithMaxBalance());
                    break;
                case "getClientWithMinBalance":
                    System.out.println("Clients with minimum balance: " + getClientWithMinBalance());
                    break;
                case "getClientWithMinBalanceLastName":
                    System.out.println("Last names of clients with minimum balance: " + getClientWithMinBalanceLastName());
                    break;
                case "getClientWithMaxBalanceLastName":
                    System.out.println("Last names of clients with maximim balance: " + getClientWithMaxBalanceLastName());
                    break;
                default:
                    break;
            }
        } catch (ManagerException e) {
            throw new ManagerException("Fault on " + command, e);
        }

    }

    private Client createClient() throws ManagerException {
        System.out.println("Enter Client fields:");
        System.out.print("email:");
        String email = scanner.next();
        System.out.print("firstName:");
        String firstName = scanner.next();
        System.out.print("lastName:");
        String lastName = scanner.next();
        Client client = new Client();
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setLastName(lastName);

        if (clientDAO.insert(client) <= 0) {
            client = null;
            throw new ManagerException("Cannot insert a client into a database");
        } else {
            clientDAO.read(client);
        }
        return client;
    }

    private Client readClient() {
        System.out.print("Enter Client ID:");
        long id = scanner.nextLong();
        Client client = clientDAO.read(id);
        return client;
    }

    private Client updateClient() throws ManagerException {
        System.out.print("Enter Client ID:");
        long id = scanner.nextLong();
        Client client = clientDAO.read(id);
        System.out.println(client);
        System.out.println("Type a new field value or asterisk * to skip a field.");
        System.out.print("email:");
        String email = scanner.next();
        System.out.print("firstName:");
        String firstName = scanner.next();
        System.out.print("lastName:");
        String lastName = scanner.next();

        if (! email.equals("*")) {
            client.setEmail(email);
        }
        if (! firstName.equals("*")) {
            client.setFirstName(firstName);
        }

        if (! lastName.equals("*")) {
            client.setLastName(lastName);
        }

        if (clientDAO.update(client) <= 0) {
            throw new ManagerException("Cannot update a client in a database");
        } else {
            System.out.println("A client was updated succesfully");
        }
        return client;
    }

    private Client deleteClient() throws ManagerException {
        System.out.print("Enter Client ID:");
        long id = scanner.nextLong();
        Client client = clientDAO.read(id);

        if (!clientDAO.delete(client)) {
            throw new ManagerException("Cannot delete a client from a database");
        } else {
            System.out.println("A client was deleted succesfully");
        }
        return client;
    }

    private List<Client> readClientsList() {
        List<Client> list = clientDAO.readAll();
        return list;
    }

    private double getClientBalance() throws ManagerException {
        System.out.print("Enter Client ID:");
        long id = scanner.nextLong();
        ClientToAccountRelation clientToAccountRelation = clientToAccountDAO.read(id);
        if (clientToAccountRelation == null) {
            throw new ManagerException("Client does not exists");
        }
        Client client = clientToAccountRelation.getBase();
        System.out.println(clientToAccountRelation);
        List<Account> accounts = clientToAccountRelation.getRelated();
        double balance = clientToAccountDAO.getClientBalance(client, accounts);
        return balance;
    }

    private List<Client> getClientWithMinBalance() {
        List<Client> clients = clientToAccountDAO.getClientWithMinBalance();
        return clients;
    }

    private Client getClientWithMaxBalance() {
        Client client = clientToAccountDAO.getClientWithMaxBalance(new ArrayList<Account>());
        return client;
    }

    private List<String> getClientWithMinBalanceLastName() {
        List<String> lastName = clientToAccountDAO.getClientWithMinBalanceLastName();
        return lastName;
    }

    private List<String> getClientWithMaxBalanceLastName() {
        List<String> lastName = clientToAccountDAO.getClientWithMaxBalanceLastName();
        return lastName;
    }
}
