package com.nitheism.uveggfruit.Online;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class ClientPlayer {
    Object side;
    ArrayList<IActorScript> enemiePlayers;
    Stage stage;

    public ClientPlayer() {

    }

    public ClientPlayer(Object side, ArrayList<IActorScript> enemiePlayers, Stage stage) {
        this.side = side;
        this.enemiePlayers = enemiePlayers;
        this.stage = stage;
    }
}
