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
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetServer {
    private Server server;
    private HashMap<Connection, ClientPlayer> players;

    public NetServer() {
        players = new HashMap<Connection, ClientPlayer>();
        server = new Server();
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
        Listener listener = new Listener() {
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
        return listener;
    }

    private void handleConnection(Connection conn) {
        Log.debug("connected to server: " + conn);
        ClientPlayer player = new ClientPlayer();
        players.put(conn, player);
    }

    private void handleCollisionRequest(Connection conn, CollisionRequest cr) {
        ArrayList<FruitScript> fruits;
        ArrayList<VeggieScript> veggies;
        if (players.get(conn).side instanceof FruitPlayer) {
            fruits = (ArrayList) cr.getAllies();
            veggies = (ArrayList) cr.getEnemies();
        } else {
            fruits = (ArrayList) cr.getEnemies();
            veggies = (ArrayList) cr.getAllies();
        }

        for (FruitScript f : fruits) {
            for (VeggieScript v : veggies) {
                if (f.getBounds().overlaps(v.getBounds())) {
                    CollisionTask t = new CollisionTask(f, v);
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
                entry.getValue().enemiePlayers.add(ncr.getScript());
                entry.getValue().stage.addActor(actor);
            }
        }
    }

    private void handleClientPlayer(Connection conn, ClientPlayer cp) {
        players.get(conn).stage = cp.stage;
        players.get(conn).enemiePlayers = cp.enemiePlayers;
        players.get(conn).side = cp.side;
        players.get(conn).vp = cp.vp;
        players.get(conn).fp = cp.fp;
        for (Map.Entry<Connection, ClientPlayer> entry : players.entrySet()) {
            if (!entry.getKey().equals(conn)) {
                if(cp.side.equals("Fruit")){
                    entry.getValue().fp = cp.fp;
                }
                else {
                    entry.getValue().vp = cp.vp;
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
                entry.getValue().stage.dispose();
            }
        }
        players.remove(conn);

    }

}
