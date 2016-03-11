package com.nitheism.uveggfruit.Online;


import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class CollisionRequest {
    private ArrayList<IActorScript> enemies;
    private ArrayList<IActorScript> allies;

    public CollisionRequest(ArrayList<IActorScript> enemies, ArrayList<IActorScript> allies) {
        this.enemies = enemies;
        this.allies = allies;
    }

    public ArrayList<IActorScript> getEnemies() {
        return enemies;
    }

    public ArrayList<IActorScript> getAllies() {
        return allies;
    }
}
