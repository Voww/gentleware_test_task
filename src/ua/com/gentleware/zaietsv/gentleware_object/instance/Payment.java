package ua.com.gentleware.zaietsv.gentleware_object.instance;

import java.sql.Date;

/**
 * A PaymentService class
 * Created by Voww on 18.12.2015.
 */
public class Payment extends AbstractInstance {

    /** a payment date */
    private Date date;

    /** a payment amount */
    private double amount;

    /** an account to be paid from */
    private long fromAccountID;

    /** an account to be paid to */
    private long toAccountID;

    /** a payment comment */
    private String comment;

    /**
     * An empty constructor for Client gentleware_object
     * */
    public Payment() {
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

    public long getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(long fromAccountID) {
        this.fromAccountID = fromAccountID;
    }

    public long getToAccountID() {
        return toAccountID;
    }

    public void setToAccountID(long toAccountID) {
        this.toAccountID = toAccountID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equalsIgnoreID(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsIgnoreID(o)) return false;

        Payment payment = (Payment) o;

        if (amount != payment.amount) return false;
        if (fromAccountID != payment.fromAccountID) return false;
        if (toAccountID != payment.toAccountID) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        return !(comment != null ? !comment.equals(payment.comment) : payment.comment != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.amount, amount) != 0) return false;
        if (fromAccountID != payment.fromAccountID) return false;
        if (toAccountID != payment.toAccountID) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        return !(comment != null ? !comment.equals(payment.comment) : payment.comment != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (fromAccountID ^ (fromAccountID >>> 32));
        result = 31 * result + (int) (toAccountID ^ (toAccountID >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "date=" + date +
                ", amount=" + amount +
                ", fromAccountID=" + fromAccountID +
                ", toAccountID=" + toAccountID +
                ", comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
