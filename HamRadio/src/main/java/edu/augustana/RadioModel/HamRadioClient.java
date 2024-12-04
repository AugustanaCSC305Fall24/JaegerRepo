package edu.augustana.RadioModel;

import com.google.gson.Gson;

import javax.websocket.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@ClientEndpoint
public class HamRadioClient implements HamRadioClientInterface {
    private Session session;
    private ServerSignalListener listener;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // Establish connection to the server
    public void connectToServer(String serverUri, ServerSignalListener listener) throws Exception {
        this.listener = listener;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, new URI(serverUri));
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server: " + session.getRequestURI());
    }

    @OnMessage
    public void onMessage(String message) {
        executor.execute(() -> {
            try {
                ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
                listener.onSignalReceived(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error processing signal: " + e.getMessage());
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Connection closed: " + closeReason.getReasonPhrase());
        executor.shutdown();
    }

    @Override
    public void sendBufferToServer(byte[] buffer) throws Exception {
        if (session != null && session.isOpen()) {
            ByteBuffer messageBuffer = ByteBuffer.wrap(buffer);
            session.getAsyncRemote().sendBinary(messageBuffer);
        } else {
            throw new IllegalStateException("Session is not open");
        }
    }
    
    @Override
    public void sendChatMessageToServer(ChatMessage chatMessage) {
        Gson gson = new Gson();
        String jsonText = gson.toJson(chatMessage);
        session.getAsyncRemote().sendText(jsonText);
    }
}
