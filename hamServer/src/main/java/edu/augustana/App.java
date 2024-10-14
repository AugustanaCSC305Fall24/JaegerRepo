package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        int port = 8080; //server port
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();  // Chấp nhận kết nối từ client
                System.out.println("New client connected");

                // Tạo các luồng vào và ra để giao tiếp với client
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // Đọc dữ liệu dạng byte từ client
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);  // Đọc dữ liệu từ client

                // Xử lý và in dữ liệu
                System.out.println("Received from client (byte[]): " + new String(buffer, 0, bytesRead));

                // Gửi lại dữ liệu tới client (echo)
                outputStream.write(buffer, 0, bytesRead);  // Gửi lại chính dữ liệu vừa nhận
                outputStream.flush();  // Đảm bảo tất cả dữ liệu được gửi

                socket.close();  // Đóng kết nối sau khi gửi lại dữ liệu
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}