package ua.com.gentleware.zaietsv.manager;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;

/**
 * Created by Voww on 18.12.2015.
 */
public class DefaultManager extends AbstractManager<Client> {

    public DefaultManager() {
        super();
    }

    @Override
    public void manage() {
        System.out.println("Enter any command below listed or type 'help' to view this message again or type 'exit' to resume:");
        System.out.println("Client section: createClient, readClient, updateClient, deleteClient, readClientsList, " +
                "getClientBalance, getClientWithMinBalance, getClientWithMaxBalance, getClientWithMinBalanceLastName, " +
                "getClientWithMaxBalanceLastName;");
        System.out.println("Account section: createAccount, readAccount, updateAccount, updateAccountAmount, " +
                "deleteAccount, readAccountsList; readClientAccounts");
        System.out.println("Payment section: createPayment, readPayment, readPaymentsList;");
    }
}
