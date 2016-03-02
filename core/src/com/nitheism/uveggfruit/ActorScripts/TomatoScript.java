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

    public TomatoScript(Batch b, BitmapFont bf, ArrayList<FruitScript> fruits, FruitPlayer f, boolean musicOn) {
        bch = b;
        bFont = bf;
        this.fruits = fruits;
        fruitPlayer = f;
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

    public void setHp(int dmg) {
        hp = hp - dmg;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public int getDmg() {
        return 10;
    }

    @Override
    public void init(CompositeActor entity) {
        this.tomato = entity;
        tomato.setPosition(100, 129);
        bounds = new Rectangle(tomato.getX(), tomato.getY(), tomato.getWidth(), tomato.getHeight());
        drawHp();
        punch = Gdx.audio.newSound(Gdx.files.internal("punch_or_whack_-Vladimir.mp3"));


    }

    @Override
    public void act(float delta) {
        if (collision) {
            drawHp();
            if (musicOn) {
                punch.play();
            }
        } else {
            drawHp();
            tomato.setX(tomato.getX() + speed * delta);
            bounds.setX(tomato.getX());
            drawHp();
            if (tomato.getX() >= 1090) {
                fruitPlayer.setHealth(100);
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
        bch.begin();
        bFont.draw(bch, Integer.toString(hp), tomato.getX() + 20, tomato.getY() + 95);
        bch.end();
    }
}
