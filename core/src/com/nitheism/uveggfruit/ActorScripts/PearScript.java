package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;


public class PearScript extends FruitScript implements IActorScript {

    private CompositeActor pear;
    Batch batch;
    BitmapFont bitmapFont;
    private int hp = 50;
    private boolean collision = false;
    private Rectangle bounds;

    public PearScript(Batch batch, BitmapFont bitmapFont) {
        this.batch = batch;
        this.bitmapFont = bitmapFont;

    }


    @Override
    public void init(CompositeActor entity) {
        this.pear = entity;
        pear.setPosition(1150, 129);
        bounds = new Rectangle(pear.getX(), pear.getY(), pear.getWidth(), pear.getHeight());
        drawHp();
    }

    @Override
    public void act(float delta) {
        if (collision) {
           drawHp();
        } else {
            int speed = -100;
            pear.setX(pear.getX() + speed * delta);
            bounds.setX(pear.getX());
            drawHp();
            if (pear.getX() <= 0 || dead()) {
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
        return 10;
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

    public void drawHp(){
        batch.begin();
        bitmapFont.draw(batch, Integer.toString(hp), pear.getX() + 20, pear.getY() + 95);
        batch.end();
    }
}
