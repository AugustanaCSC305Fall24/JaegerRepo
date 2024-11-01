module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;
    requires tyrus.standalone.client;
    requires java.desktop;
    requires jdk.jfr;

    opens edu.augustana.Application.UI to javafx.fxml;
    exports edu.augustana.Application.UI;

}
