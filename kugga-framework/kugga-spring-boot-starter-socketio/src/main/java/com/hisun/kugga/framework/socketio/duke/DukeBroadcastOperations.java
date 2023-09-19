package com.hisun.kugga.framework.socketio.duke;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.namespace.Namespace;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.StoreFactory;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubType;

import java.util.*;

public class DukeBroadcastOperations extends BroadcastOperations {

    private final Iterable<SocketIOClient> clients;
    private final StoreFactory storeFactory;
    private final String room;

    public DukeBroadcastOperations(Iterable<SocketIOClient> clients, StoreFactory storeFactory, String room) {
        super(clients, storeFactory);
        this.clients = clients;
        this.storeFactory = storeFactory;
        this.room = room;
    }

    /**
     * 这里只给指定的Room发送消息
     */
    private void dispatch(Packet packet) {
        Map<String, Set<String>> namespaceRooms = new HashMap<>();
        for (SocketIOClient socketIOClient : clients) {
            Namespace namespace = (Namespace) socketIOClient.getNamespace();
            Set<String> rooms = namespace.getRooms(socketIOClient);

            Set<String> roomsList = namespaceRooms.computeIfAbsent(namespace.getName(), k -> new HashSet<>());
            roomsList.addAll(rooms);
        }
        for (Map.Entry<String, Set<String>> entry : namespaceRooms.entrySet()) {
            for (String room : entry.getValue()) {
                if (StrUtil.equals(room, this.room)) {
                    storeFactory.pubSubStore().publish(PubSubType.DISPATCH, new DispatchMessage(room, packet, entry.getKey()));
                }
            }
        }
    }

    @Override
    public void send(Packet packet) {
        for (SocketIOClient client : clients) {
            client.send(packet);
        }
        dispatch(packet);
    }

    public void sendEvent(String name, SocketIOClient excludedClient, Object... data) {
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(name);
        packet.setData(Arrays.asList(data));

        for (SocketIOClient client : clients) {
            if (client.getSessionId().equals(excludedClient.getSessionId())) {
                continue;
            }
            client.send(packet);
        }
        dispatch(packet);
    }


}
