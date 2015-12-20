package ua.com.gentleware.zaietsv.service;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;

import java.util.List;

/**
 * Created by Voww on 18.12.2015.
 */
public interface ClientService {

    double getClientBalance(Client client, List<Account> accounts);
    Client getClientWithMaxBalance(List<Account> accounts);
}
