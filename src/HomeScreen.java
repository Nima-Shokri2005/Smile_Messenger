import java.util.Scanner;

public class HomeScreen {
    Scanner input = new Scanner(System.in);

    public void mainRun() {
        menu();
    }

    private void menu() {
        int choice = -1;
        while (choice != 3) {
            System.out.println(">> Welcome to the Smile Messenger!");
            System.out.println(">> 1. Login");
            System.out.println(">> 2. Register");
            System.out.println(">> 3. Exit");
            System.out.print(">> Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1: {
                    Login login = new Login();
                    login.mainRun();
                    choice = 3;
                    break;
                }
                case 2: {
                    Register register = new Register();
                    register.mainRun();
                    choice = 3;
                    break;
                }
                case 3: {
                    System.out.println(">> Goodbye!");
                    System.exit(0);
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
