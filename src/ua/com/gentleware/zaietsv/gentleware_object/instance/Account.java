package ua.com.gentleware.zaietsv.gentleware_object.instance;

/**
 * Created by Voww on 18.12.2015.
 */
public class Account extends AbstractInstance {

    /** an account code */
    private String code;

    /** an account alias */
    private String alias;

    /** an account amount */
    private double amount;

    /** an account description */
    private String description;

    /**
     * An empty constructor for Account gentleware_object
     * */
    public Account() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equalsIgnoreID(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsIgnoreID(o)) return false;

        Account account = (Account) o;

        if (Double.compare(account.amount, amount) != 0) return false;
        if (code != null ? !code.equals(account.code) : account.code != null) return false;
        if (alias != null ? !alias.equals(account.alias) : account.alias != null) return false;
        return !(description != null ? !description.equals(account.description) : account.description != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (Double.compare(account.amount, amount) != 0) return false;
        if (code != null ? !code.equals(account.code) : account.code != null) return false;
        if (alias != null ? !alias.equals(account.alias) : account.alias != null) return false;
        return !(description != null ? !description.equals(account.description) : account.description != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "code='" + code + '\'' +
                ", alias='" + alias + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
