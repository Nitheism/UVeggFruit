package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;


public class PearScript extends FruitScript implements IActorScript {

    Batch batch;
    BitmapFont bitmapFont;
    private CompositeActor pear;
    private int hp = 50;
    private boolean collision = false;
    private Rectangle bounds;
    private VeggiePlayer veggiePlayer;
    private ArrayList<VeggieScript> veggies;
    private Sound punch;
    private boolean musicOn;
    private int dmg = 10;
    private ArrayList<FruitScript> fruits;

    public PearScript(Batch batch, BitmapFont bitmapFont, VeggiePlayer veggiePlayer, ArrayList<VeggieScript> veggies,ArrayList<FruitScript> fruits, boolean musicOn) {
        //initializing
        this.batch = batch;
        this.bitmapFont = bitmapFont;
        this.veggiePlayer = veggiePlayer;
        this.veggies = veggies;
        this.fruits = fruits;
        this.musicOn = musicOn;

    }


    @Override
    public void init(CompositeActor entity) {
        //setting the actor position,bounds,drawing the needed text, initializing punch audio
        this.pear = entity;
        pear.setPosition(1150, 129);
        bounds = new Rectangle(pear.getX(), pear.getY(), pear.getWidth(), pear.getHeight());
        drawHp();
        punch = Gdx.audio.newSound(Gdx.files.internal("punch_or_whack_-Vladimir.mp3"));
    }

    @Override
    public void act(float delta) {
        //if there is a collision play punch if music is on and then draw HP
        if (collision) {
            if(musicOn){
                punch.play();
            }
            drawHp();
        } else {
            //if there is no collision move forward and draw hp
            int speed = -150;
            pear.setX(pear.getX() + speed * delta);
            bounds.setX(pear.getX());
            drawHp();
            //if entity is out if bounds destroy it
            if (pear.getX() <= 130) {
                veggiePlayer.setHealth(100);
                fruits.remove(this);
                pear.remove();
                pear.getScripts().clear();
            }

        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        bitmapFont.dispose();
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
        hp = hp - dmg;
    }

    @Override
    public void setCollision(boolean inCollision) {
        this.collision = inCollision;
    }

    @Override
    public boolean dead() {
        return hp <= 0;
    }

    @Override
    public CompositeActor getEntity() {
        return pear;
    }

    public void drawHp() {
        //draws the entities hp
        batch.begin();
        bitmapFont.draw(batch, Integer.toString(hp), pear.getX() + 20, pear.getY() + 95);
        batch.end();
    }

    @Override
    public ArrayList<VeggieScript> getVeggieList() {
        return veggies;
    }

    @Override
    public int moneyGain() {
        return dmg;
    }
}
