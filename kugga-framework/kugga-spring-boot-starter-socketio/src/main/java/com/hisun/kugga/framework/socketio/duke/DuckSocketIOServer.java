package com.hisun.kugga.framework.socketio.duke;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.namespace.NamespacesHub;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class DuckSocketIOServer extends SocketIOServer {

    private static final Field FIELD_CONFIGCOPY = ReflectionUtils.findField(SocketIOServer.class, "configCopy");
    private static final Field FIELD_NAMESPACEHUB = ReflectionUtils.findField(SocketIOServer.class, "namespacesHub");

    static {
        assert FIELD_CONFIGCOPY != null;
        ReflectionUtils.makeAccessible(FIELD_CONFIGCOPY);
        assert FIELD_NAMESPACEHUB != null;
        ReflectionUtils.makeAccessible(FIELD_NAMESPACEHUB);
    }

    public DuckSocketIOServer(Configuration configuration) {
        super(configuration);
    }

    private Configuration getConfigCopy() {
        return (Configuration) ReflectionUtils.getField(FIELD_CONFIGCOPY, this);
    }

    private NamespacesHub getNamespacesHub() {
        return (NamespacesHub) ReflectionUtils.getField(FIELD_NAMESPACEHUB, this);
    }

    @Override
    public BroadcastOperations getRoomOperations(String room) {
        Iterable<SocketIOClient> clients = getNamespacesHub().getRoomClients(room);
        return new DukeBroadcastOperations(clients, getConfigCopy().getStoreFactory(), room);
    }

}
