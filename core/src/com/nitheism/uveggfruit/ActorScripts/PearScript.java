package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;


public class PearScript extends FruitScript implements IActorScript {

    private CompositeActor pear;
    private final int speed = -100;
    Batch batch;
    BitmapFont bitmapFont;
    private final int dmg = 15;
    private int hp = 50;
    private boolean col = false;
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
        if (col) {
           drawHp();
        } else {
            pear.setX(pear.getX() + speed * delta);
            bounds.setX(pear.getX());
            drawHp();
            if (pear.getX() <= 0) {
                pear.remove();
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
    public void setCol(boolean col) {
        this.col = col;
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
