import java.util.Scanner;

public class SendMessage extends HttpRequest {
    Scanner input = new Scanner(System.in);
    private String _id;
    private String _username;
    private String _email;
    private String _hashPassword;
    private String _usernameGetter;
    private String _idGetter;
    private String _message;
    boolean continueExecution = true;

    public SendMessage(String id, String username, String email, String hashPassword) {
        _id = id;
        _username = username;
        _email = email;
        _hashPassword = hashPassword;
    }


    public void mainRun() {
        while (continueExecution) {
            System.out.print(">> Enter username that you want to send message: ");
            _usernameGetter = input.nextLine();

            String response = executeQuery(String.format("select username from users where username = '%s';", _usernameGetter));

            if (response.equals("No results found.")) {
                System.out.println(">> This username does not exist.");
                continueExecution = askToContinue();
            } else if (_username.equals(_usernameGetter)) {
                System.out.println(">> You can't send message for your self");
                continueExecution = askToContinue();
            } else {
                System.out.print(">> Write your message then Enter to send: ");
                _message = input.nextLine();
                if (_message.isEmpty()) {
                    System.out.println(">> Message can't be empty!");
                    continueExecution = askToContinue();
                } else {
                    String _idGetter = executeQuery(String.format("select id from users where username = '%s';", _usernameGetter));
                    String query = String.format("INSERT INTO messages (sender_id, receiver_id, message) VALUES ('%s', '%s', '%s');", _id, _idGetter, _message);
                    executeQuery(query);
                    System.out.println(">> Your message successfully sent!");
                    Dashbord dashbord = new Dashbord(_id, _username, _email, _hashPassword);
                    dashbord.mainRun();
                    continueExecution = false;
                    break;
                }
            }
        }
    }

    private boolean askToContinue() {
        System.out.print(">> Do you want to continue? (y/n): ");
        char ch = input.next().charAt(0);
        input.nextLine(); // Consume the newline character
        switch (ch) {
            case 'y':
                return true;
            case 'n':
                Dashbord dashbord = new Dashbord(_id, _username, _email, _hashPassword);
                dashbord.mainRun();
                return false;
            default: {
                System.out.println(">> Invalid choice");
                System.out.println(">> Press Enter to continue... ");
                return askToContinue();
            }
        }
    }
}
