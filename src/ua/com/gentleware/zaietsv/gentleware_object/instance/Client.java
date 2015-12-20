package ua.com.gentleware.zaietsv.gentleware_object.instance;

/**
 * A Client class
 * Created by Voww on 18.12.2015.
 */
public class Client extends AbstractInstance {


    /** a client email */
    private String email;

    /** a client first name */
    private String firstName;

    /** a client last name */
    private String lastName;

    /**
     * An empty constructor for Client gentleware_object
     * */
    public Client() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equalsIgnoreID(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equalsIgnoreID(o)) return false;

        Client client = (Client) o;

        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(client.lastName) : client.lastName != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(client.lastName) : client.lastName != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "} " + super.toString();
    }
}
