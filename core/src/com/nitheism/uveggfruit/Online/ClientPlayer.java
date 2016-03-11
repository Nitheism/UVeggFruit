package com.nitheism.uveggfruit.Online;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class ClientPlayer {
    private Object side;
    private ArrayList<IActorScript> enemiePlayers;
    private Stage stage;
    private FruitPlayer fp;
    private VeggiePlayer vp;

    public ClientPlayer(){

    }

    public ClientPlayer(Object side, ArrayList<IActorScript> enemiePlayers, Stage stage, FruitPlayer fp, VeggiePlayer vp) {
        this.side = side;
        this.enemiePlayers = enemiePlayers;
        this.stage = stage;
        this.vp = vp;
        this.fp = fp;
    }

    public Object getSide() {
        return side;
    }

    public void setSide(Object side) {
        this.side = side;
    }

    public ArrayList<IActorScript> getEnemiePlayers() {
        return enemiePlayers;
    }

    public void setEnemiePlayers(ArrayList<IActorScript> enemiePlayers) {
        this.enemiePlayers = enemiePlayers;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FruitPlayer getFp() {
        return fp;
    }

    public void setFp(FruitPlayer fp) {
        this.fp = fp;
    }

    public VeggiePlayer getVp() {
        return vp;
    }

    public void setVp(VeggiePlayer vp) {
        this.vp = vp;
    }
}
