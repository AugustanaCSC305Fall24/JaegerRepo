module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;
    requires tyrus.standalone.client;
    requires java.desktop;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
