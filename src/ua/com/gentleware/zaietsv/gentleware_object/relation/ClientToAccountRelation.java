package ua.com.gentleware.zaietsv.gentleware_object.relation;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;

import java.util.List;

/**
 * Created by Voww on 20.12.2015.
 */
public class ClientToAccountRelation extends AbstractRelation<Client, Account> {

    public ClientToAccountRelation() {
    }

    public ClientToAccountRelation(Client base, List<Account> related) {
        super(base, related);
    }

    @Override
    public String toString() {
        return "ClientToAccountRelation{} " + super.toString();
    }
}
