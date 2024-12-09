module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;
    requires tyrus.standalone.client;
    requires java.desktop;
    requires jdk.jfr;
    requires com.google.gson;
    requires jdk.unsupported;
    requires com.github.psambit9791.jdsp;

    opens edu.augustana.Application.UI to javafx.fxml;
    exports edu.augustana.Application.UI;
    exports edu.augustana.RadioModel; // Exporting the package
    opens edu.augustana.RadioModel.Practice;
    exports edu.augustana.RadioModel.Practice.SceneBuilderFactory;
    opens edu.augustana.RadioModel.Practice.SceneBuilderFactory to javafx.fxml;
    opens edu.augustana.RadioModel.Practice.BotCollections;
    opens edu.augustana.RadioModel.Practice.TaskCollection;
    opens edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame;
    opens edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame;

    requires java.net.http;
    requires swiss.ameri.gemini.api;
    requires swiss.ameri.gemini.gson;

}
