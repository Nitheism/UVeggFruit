package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

public class OnionScript extends VeggieScript implements IActorScript {
    private static final int speed = 150;
    ArrayList<FruitScript> fruits;
    private CompositeActor onion;
    private BitmapFont bFont;
    private Batch bch;
    private int hp = 70;
    private Rectangle bounds;
    private boolean collision = false;
    private FruitPlayer fruitPlayer;
    private Sound punch;
    private boolean musicOn;
    private final int dmg = 30;
    private final int cost = 20;
    private ArrayList<VeggieScript> veggies;

    public OnionScript(Batch bch, BitmapFont bFont, ArrayList<FruitScript> fruits, ArrayList<VeggieScript> veggies, FruitPlayer fruitPlayer, boolean musicOn) {
        //initializing
        this.bch = bch;
        this.bFont = bFont;
        this.fruits = fruits;
        this.fruitPlayer = fruitPlayer;
        this.veggies = veggies;
        this.musicOn = musicOn;

    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public int getDmg() {
        return dmg;
    }

    @Override
    public void setHp(int dmg) {
        hp -= dmg;
    }

    @Override
    public void setCollision(boolean inCollision) {
        collision = inCollision;
    }

    @Override
    public boolean dead() {
        return hp <= 0;
    }

    @Override
    public CompositeActor getEntity() {
        return onion;
    }

    @Override
    public void drawHp() {
        //draws the hp of the entity
        bch.begin();
        bFont.draw(bch, Integer.toString(hp), onion.getX() + 20, onion.getY() + 95);
        bch.end();
    }

    @Override
    public ArrayList<FruitScript> getFruitsList() {
        return fruits;
    }


    @Override
    public int moneyGain() {
        return cost;
    }

    @Override
    public void init(CompositeActor entity) {
        //setting the actor position,bounds,drawing the needed text, initializing punch audio
        onion = entity;
        onion.setPosition(100, 129);
        bounds = new Rectangle(onion.getX(), onion.getY(), onion.getWidth(), onion.getHeight());
        drawHp();
        punch = Gdx.audio.newSound(Gdx.files.internal("punch_or_whack_-Vladimir.mp3"));
    }

    @Override
    public void act(float delta) {
        //if there is a collision play punch if music is on and then draw HP
        if (collision) {
            drawHp();
            if (musicOn) {
                punch.play();
            }
        } else {
            //if there is no collision move forward and draw hp
            onion.setX(onion.getX() + speed * delta);
            bounds.setX(onion.getX());
            drawHp();
            //if entity is out of bounds destroy it
            if (onion.getX() >= 1090) {
                int dmgToBase = 200;
                fruitPlayer.setHealth(dmgToBase);
                veggies.remove(this);
                onion.remove();
                onion.getScripts().clear();
            }

        }
    }

    @Override
    public void dispose() {
        bch.dispose();
        bFont.dispose();
        punch.dispose();
    }
}
