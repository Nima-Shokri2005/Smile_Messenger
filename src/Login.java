import java.util.Scanner;

public class Login extends HttpRequest {
    private String _id, _username, _email, _password,_hashPassword;
    private HomeScreen homeScreen = new HomeScreen();
    Scanner input = new Scanner(System.in);
    boolean continueExecution = true;

    public void mainRun() {
        while (continueExecution) {
            System.out.print(">> Enter username: ");
            _username = input.nextLine();
            System.out.print(">> Enter password: ");
            _password = input.nextLine();
            String response = executeQuery(String.format("select password from users where username = '%s';", _username));
            String hashPassword = hashPassword(_password);

            if (response.equals("No results found.")) {
                System.out.println(">> This username does not exist.");
                continueExecution = askToContinue();
            } else if (hashPassword.equals(response)) {
                System.out.println(">> You are successfully logged in!");
                _id = executeQuery(String.format("select id from users where username = '%s';", _username));
                _email = executeQuery(String.format("select email from users where username = '%s';", _username));
                _hashPassword = hashPassword(_password);
                Dashbord dashbord = new Dashbord(_id, _username, _email, _hashPassword);
                dashbord.mainRun();
                continueExecution = false;
            } else {
                System.out.println(">> Invalid password.");
                continueExecution = askToContinue();
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
                homeScreen.mainRun();
                return false;
            default: {
                System.out.println(">> Invalid choice");
                System.out.println(">> Press Enter to continue... ");
                return askToContinue();
            }
        }
    }
}
