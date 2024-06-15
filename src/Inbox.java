import java.util.Scanner;

public class Inbox extends HttpRequest {
    Scanner input = new Scanner(System.in);
    private String _id;
    private String _username;
    private String _email;
    private String _hashPassword;

    public Inbox(String id, String username, String email, String hashPassword) {
        _id = id;
        _username = username;
        _email = email;
        _hashPassword = hashPassword;
    }


    public void mainRun() {
        String query = String.format("""
                SELECT m.id, sender.username AS sender_username, m.message, m.timestamp
                FROM messages m
                JOIN users sender ON m.sender_id = sender.id
                JOIN users receiver ON m.receiver_id = receiver.id
                WHERE m.receiver_id = '%s'
                ORDER BY m.timestamp DESC;""", _id);

        System.out.println(executeQuery(query));
    }
}
