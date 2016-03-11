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


public class TomatoScript extends VeggieScript implements IActorScript {


    private static final int speed = 150;
    ArrayList<FruitScript> fruits;
    private CompositeActor tomato;
    private BitmapFont bFont;
    private Batch bch;
    private int hp = 50;
    private Rectangle bounds;
    private boolean collision = false;
    private FruitPlayer fruitPlayer;
    private Sound punch;
    private boolean musicOn;
    private int dmg = 10;
    private ArrayList<VeggieScript> veggies;

    public TomatoScript(Batch bch, BitmapFont bFont, ArrayList<FruitScript> fruits, ArrayList<VeggieScript> veggies, FruitPlayer fruitPlayer, boolean musicOn) {
        //initializing
        this.bch = bch;
        this.bFont = bFont;
        this.fruits = fruits;
        this.fruitPlayer = fruitPlayer;
        this.veggies = veggies;
        this.musicOn = musicOn;
    }

    public boolean dead() {
        return hp <= 0;
    }

    @Override
    public void setCollision(boolean inCollision) {
        this.collision = inCollision;
    }

    @Override
    public CompositeActor getEntity() {
        return tomato;
    }

    @Override
    public ArrayList<FruitScript> getFruitsList() {
        return fruits;
    }

    @Override
    public int moneyGain() {
        return dmg;
    }

    public void setHp(int dmg) {
        hp = hp - dmg;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public int getDmg() {
        return dmg;
    }

    @Override
    public void init(CompositeActor entity) {
        //setting the actor position,bounds,drawing the needed text, initializing punch audio
        this.tomato = entity;
        tomato.setPosition(100, 129);
        bounds = new Rectangle(tomato.getX(), tomato.getY(), tomato.getWidth(), tomato.getHeight());
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
            tomato.setX(tomato.getX() + speed * delta);
            bounds.setX(tomato.getX());
            drawHp();
            //if entity is out if bounds destroy it
            if (tomato.getX() >= 1090) {
                fruitPlayer.setHealth(100);
                veggies.remove(this);
                tomato.remove();
                tomato.getScripts().clear();
            }

        }


    }


    @Override
    public void dispose() {
        bch.dispose();
        bFont.dispose();
        punch.dispose();
    }

    public void drawHp() {
        //draws the hp of the entity
        bch.begin();
        bFont.draw(bch, Integer.toString(hp), tomato.getX() + 20, tomato.getY() + 95);
        bch.end();
    }
}
