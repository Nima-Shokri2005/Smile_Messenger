import java.util.Scanner;

public class Dashbord {
    Scanner input = new Scanner(System.in);
    private String _id;
    private String _username;
    private String _email;
    private String _hashPassword;

    public Dashbord(String id, String username, String email, String hashPassword) {
        _id = id;
        _username = username;
        _email = email;
        _hashPassword = hashPassword;
    }

    public void mainRun() {
        menu();
    }


    private void menu() {
        int choice = -1;
        while (choice != 3) {
            System.out.println(">> 1. Inbox");
            System.out.println(">> 2. Send Message");
            System.out.println(">> 3. Back to Main Menu and Logout");
            System.out.print(">> Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {

                case 1: {
                    Inbox inbox = new Inbox(_id, _username, _email, _hashPassword);
                    inbox.mainRun();
                    choice = 3;
                    break;
                }

                case 2: {
                    SendMessage sendMessage = new SendMessage(_id, _username, _email, _hashPassword);
                    sendMessage.mainRun();
                    choice = 3;
                    break;
                }

                case 3: {
                    HomeScreen homeScreen = new HomeScreen();
                    homeScreen.mainRun();
                    choice = 3;
                    break;
                }
                default: {
                    System.out.println(">> Invalid choice");
                    System.out.println(">> Press Enter to continue... ");
                    input.nextLine();
                    input.nextLine();
                }
            }
        }
    }


}
