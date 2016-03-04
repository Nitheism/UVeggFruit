package com.nitheism.uveggfruit.Online;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryo.Kryo;
import com.nitheism.uveggfruit.ActorScripts.CollisionTask;
import com.nitheism.uveggfruit.ActorScripts.FruitScript;
import com.nitheism.uveggfruit.ActorScripts.PearScript;
import com.nitheism.uveggfruit.ActorScripts.TomatoScript;
import com.nitheism.uveggfruit.ActorScripts.VeggieScript;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

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
                NewCreepRequest.class,
                TomatoScript.class,
                PearScript.class,
                CompositeActor.class,
                Stage.class
        };
        for (Class clazz : classes) {
            k.register(clazz);
        }
    }
}
