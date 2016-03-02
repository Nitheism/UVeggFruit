package com.nitheism.uveggfruit.Online;


import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class CollisionRequest {
    private ArrayList<IActorScript> enemies;
    private ArrayList<IActorScript> allies;

    public CollisionRequest(ArrayList<IActorScript> e, ArrayList<IActorScript> a) {
        enemies = e;
        allies = a;
    }

    public ArrayList<IActorScript> getEnemies() {
        return enemies;
    }

    public ArrayList<IActorScript> getAllies() {
        return allies;
    }
}
