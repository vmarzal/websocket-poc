package es.hideout.dev.websocket.raw.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.logging.SocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gsonBuilder = new GsonBuilder().create();

        Map<String, Object> value = gsonBuilder.fromJson(message.getPayload(), Map.class);

        Double doubleRepeat = (Double) value.get("repeat");
        int repeat = (int) Math.round(doubleRepeat);

        logger.info(">> repeat " + repeat + " times!");

        StringBuilder strConcatenated = new StringBuilder();
        strConcatenated.append("Squawk! ");

        if (repeat > 1) {
            for (int i = 0; i < repeat; i++) {
                strConcatenated.append(value.get("message")).append(" ");
            }
        } else {
            strConcatenated.append(value.get("message")).append(" ");
        }
        session.sendMessage(new TextMessage(strConcatenated.toString()));
    }
}
