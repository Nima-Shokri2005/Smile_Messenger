import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class HttpRequest {

    public static String executeQuery(String query) {
        String url = "https://devnima.ir/main.php";

        try {
            Map<String, String> data = new HashMap<>();
            data.put("query", query);

            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                sj.add(entry.getKey() + "=" + entry.getValue());
            }
            byte[] postDataBytes = sj.toString().getBytes("UTF-8");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(postDataBytes);
                os.flush();
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString();
            } catch (Exception e) {
                return "ERR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERR";
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmail(String email, String template, String name, String code) {
        String url = "https://api.emailjs.com/api/v1.0/email/send";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("service_id", "service_u9y1rse");
            jsonBody.put("template_id", template);
            jsonBody.put("user_id", "WilfVudVGcrV_E0vv");

            JSONObject templateParams = new JSONObject();
            templateParams.put("email", email);
            templateParams.put("name", name);
            templateParams.put("code", code);

            jsonBody.put("template_params", templateParams);

            byte[] postDataBytes = jsonBody.toString().getBytes("UTF-8");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(postDataBytes);
                os.flush();
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
