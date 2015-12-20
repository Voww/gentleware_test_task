package ua.com.gentleware.zaietsv.gentleware_object.relation;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.service.PaymentService;

import java.sql.Date;
import java.util.List;

/**
 * Created by Voww on 20.12.2015.
 */
public class PaymentRelation extends AbstractRelation<Account, Account> implements PaymentService {

    private long id;
    private Date date;
    private double amount;
    private String comment;

    public PaymentRelation() {
    }

    public PaymentRelation(Account base, List<Account> related) {
        super(base, related);
    }

    public void pay() {
        Account accountBase = this.getBase();
        List<Account> accounts = this.getRelated();
        for (Account accountRelated : accounts) {
            accountBase.setAmount(accountBase.getAmount() - amount);
            accountRelated.setAmount(accountRelated.getAmount() + amount);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "PaymentRelation{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
