import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Random;

public class Register extends HttpRequest {
    private String _id, _username, _email, _password, _repeatPassword, _hashPassword;
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final HomeScreen homeScreen = new HomeScreen();
    Scanner input = new Scanner(System.in);
    boolean continueExecution = true;


    public void mainRun() {
        while (continueExecution) {
            System.out.print(">> Enter username: ");
            _username = input.nextLine();
            System.out.print(">> Enter Email: ");
            _email = input.nextLine();
            System.out.print(">> Enter password: ");
            _password = input.nextLine();
            System.out.print(">> Repeat password: ");
            _repeatPassword = input.nextLine();

            String response_username = executeQuery(String.format("select username from users where username = '%s';", _username));
            String response_email = executeQuery(String.format("select email from users where email = '%s';", _email));

            if (_username.length() < 4) {
                System.out.println("Username must be at least 4 characters");
                continueExecution = askToContinue();
            } else if (response_username.contains(_username)) {
                System.out.println("This username already exists");
                continueExecution = askToContinue();
            } else if (!isValidEmail(_email)) {
                System.out.println("Invalid Email");
                continueExecution = askToContinue();
            } else if (response_email.contains(_email)) {
                System.out.println("This Email already exists");
                continueExecution = askToContinue();
            } else if (!_password.equals(_repeatPassword)) {
                System.out.println("Passwords do not match!");
                continueExecution = askToContinue();
            } else if (_password.length() < 4) {
                System.out.println("Password must be at least 4 characters");
                continueExecution = askToContinue();
            } else {
                System.out.print(">> Enter the code we just sent your Email: ");
                Random random = new Random();
                int code = 1000 + random.nextInt(9000);
                sendEmail(_email, "template_azeusee", _username, String.valueOf(code));
                int currentCode = input.nextInt();
                input.nextLine();
                if (currentCode == code) {
                    _hashPassword = hashPassword(_password);
                    String query = String.format("insert into users(email, username, password) values('%s', '%s', '%s');", _email, _username, _hashPassword);
                    executeQuery(query);
                    System.out.println(">> You have successfully logged in!");
                    continueExecution = false;
                    _id = executeQuery(String.format("select id from users where username = '%s';", _username));
                    Dashbord dashbord = new Dashbord(_id, _username, _email, _hashPassword);
                    dashbord.mainRun();
                } else {
                    System.out.println("Invalid Code");
                    continueExecution = askToContinue();
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
                homeScreen.mainRun();
                return false;
            default: {
                System.out.println(">> Invalid choice");
                System.out.println(">> Press Enter to continue... ");
                return askToContinue();
            }
        }
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
