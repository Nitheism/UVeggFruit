package com.nitheism.uveggfruit.Online;


import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.nitheism.uveggfruit.ActorScripts.CollisionTask;
import com.nitheism.uveggfruit.ActorScripts.FruitScript;
import com.nitheism.uveggfruit.ActorScripts.VeggieScript;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetServer {
    private HashMap<Connection, ClientPlayer> players;

    public NetServer() {
        players = new HashMap<Connection, ClientPlayer>();
        Server server = new Server();
        NetManager.RegisterClasses(server.getKryo());
        try {
            server.bind(NetManager.tcpPort, NetManager.udpPort);
            server.start();
            server.addListener(createListener());
        } catch (IOException e) {
            Log.error(e.toString());
            throw new GdxRuntimeException(e);
        }
    }

    private Listener createListener() {
        return new Listener() {
            @Override
            public void connected(Connection connection) {
                handleConnection(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                handleDisconnect(connection);
            }

            @Override
            public void received(Connection connection, Object object) {
                handleRecieved(connection, object);
            }

            @Override
            public void idle(Connection connection) {
                super.idle(connection);
            }
        };
    }

    private void handleConnection(Connection conn) {
        Log.debug("connected to server: " + conn);
        ClientPlayer player = new ClientPlayer();
        players.put(conn, player);
    }

    private void handleCollisionRequest(Connection conn, CollisionRequest cr) {
        ArrayList<FruitScript> fruits;
        ArrayList<VeggieScript> veggies;
        if (players.get(conn).getSide().equals("Fruit")) {
            fruits = (ArrayList) cr.getAllies();
            veggies = (ArrayList) cr.getEnemies();
        } else {
            fruits = (ArrayList) cr.getEnemies();
            veggies = (ArrayList) cr.getAllies();
        }

        for (FruitScript f : fruits) {
            for (VeggieScript v : veggies) {
                if (f.getBounds().overlaps(v.getBounds())) {
                    CollisionTask t = new CollisionTask(f, v,players.get(conn).getFp(),players.get(conn).getVp());
                    Timer.schedule(t, 0, 0, 0);
                }
            }
        }
    }

    private void handleNewCreepRequest(Connection conn, NewCreepRequest ncr) {
        CompositeActor actor = ncr.getActor();
        actor.addScript(ncr.getScript());
        for (Map.Entry<Connection, ClientPlayer> entry : players.entrySet()) {
            if (!entry.getKey().equals(conn)) {
                entry.getValue().getEnemiePlayers().add(ncr.getScript());
                entry.getValue().getStage().addActor(actor);
            }
        }
    }

    private void handleClientPlayer(Connection conn, ClientPlayer cp) {
        players.get(conn).setStage(cp.getStage());
        players.get(conn).setEnemiePlayers(cp.getEnemiePlayers());
        players.get(conn).setSide(cp.getSide());
        players.get(conn).setVp(cp.getVp());
        players.get(conn).setFp(cp.getFp());
        for (Map.Entry<Connection, ClientPlayer> entry : players.entrySet()) {
            if (!entry.getKey().equals(conn)) {
                if(cp.getSide().equals("Fruit")){
                    entry.getValue().setFp(cp.getFp());
                }
                else {
                    entry.getValue().setVp(cp.getVp());
                }
            }
        }

    }

    private void handleRecieved(Connection conn, Object obj) {
        if (obj instanceof CollisionRequest) {
            handleCollisionRequest(conn, (CollisionRequest) obj);
        }
        if (obj instanceof NewCreepRequest) {
            handleNewCreepRequest(conn, (NewCreepRequest) obj);
        }

        if (obj instanceof ClientPlayer) {
            handleClientPlayer(conn, (ClientPlayer) obj);
        }

    }

    private void handleDisconnect(Connection conn) {
        Log.debug("disconnected from server: " + conn);
        for (Map.Entry<Connection, ClientPlayer> entry : players.entrySet()) {
            if (!entry.getKey().equals(conn)) {
                entry.getValue().getStage().dispose();
            }
        }
        players.remove(conn);

    }

}
