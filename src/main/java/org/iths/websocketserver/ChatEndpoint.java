package org.iths.websocketserver;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//alla importer från paketet jakarta.websocket
@ServerEndpoint("/ws/chat")
public class ChatEndpoint {
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session session) {
        // Körs när en klient kopplar upp sig
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Körs när servern tar emot ett textmeddelande
        for (Session s : sessions) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    System.out.println("Neh");
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        // Körs när klienten kopplar
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Körs vid fel
    }
}
