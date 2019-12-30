package restApi;

public class Client {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Client(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public static final class ClientBuilder
    {
        String id;
        String firstName;
        String lastName;
        String email;

        private ClientBuilder() {
        }

        public static ClientBuilder aClient(){
            return new ClientBuilder();
        }

        public ClientBuilder withID(String id)
        {
            this.id = id;
            return this;
        }

        public ClientBuilder withFirstName(String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public ClientBuilder withLastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public ClientBuilder withEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Client build()
        {
            return new Client(id,firstName,lastName,email);
        }
    }

}
