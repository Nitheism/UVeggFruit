package com.nitheism.uveggfruit.Online;

import com.esotericsoftware.kryo.Kryo;
import com.nitheism.uveggfruit.ActorScripts.CollisionTask;
import com.nitheism.uveggfruit.ActorScripts.FruitScript;
import com.nitheism.uveggfruit.ActorScripts.VeggieScript;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;

public class NetManager {
    public static final int udpPort = 37733;
    public static final int tcpPort = 37737;
    public static String host = "localhost";

    public static void RegisterClasses(Kryo k) {
        Class[] classes = new Class[]{
                ClientPlayer.class,
                CollisionTask.class,
                VeggiePlayer.class,
                VeggieScript.class,
                FruitPlayer.class,
                FruitScript.class,
                CollisionRequest.class,
                NewCreepRequest.class
        };
        for (Class clazz : classes) {
            k.register(clazz);
        }
    }
}
