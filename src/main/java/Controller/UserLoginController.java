package Controller;

import Repository.Connection;
import UI.login;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserLoginController {
	private final Connection connection;
    public UserLoginController(login login) throws IOException {
		connection = new Connection();
    }


    private boolean isLoginSuccess = false;
    public void getUserByEmail(String email,String pass) {
        try {
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String encodedPassword = URLEncoder.encode(pass, StandardCharsets.UTF_8);
            String url = "https://still-cliffs-55450-6c9d6b2dff57.herokuapp.com/admin/checkAdmin?userEmail=" + email + "&password=" + encodedPassword;
            connection.setUrl(new URL(url));

            connection.openGetConnection();

            int status = connection.con.getResponseCode();

            if(status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // close connections
                in.close();
                connection.closeConnection();

                isLoginSuccess = true;
            } else if (status == 500) {
                System.out.println("Server error. Please try again later.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

}
