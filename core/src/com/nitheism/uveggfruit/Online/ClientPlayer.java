package com.nitheism.uveggfruit.Online;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class ClientPlayer {
    Object side;
    ArrayList<IActorScript> enemiePlayers;
    Stage stage;
    FruitPlayer fp;
    VeggiePlayer vp;

    public ClientPlayer() {

    }

    public ClientPlayer(Object side, ArrayList<IActorScript> enemiePlayers, Stage stage, FruitPlayer fp, VeggiePlayer vp) {
        this.side = side;
        this.enemiePlayers = enemiePlayers;
        this.stage = stage;
        this.vp = vp;
        this.fp = fp;
    }
}
