package com.nitheism.uveggfruit.ActorScripts;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;


public class TomatoScript extends VeggieScript implements IActorScript {


    private CompositeActor tomato;
    private static final int speed = 100;
    private BitmapFont bFont;
    private Batch bch;
    ArrayList<FruitScript> fruits;
    private final int dmg = 10;
    private int hp = 50;
    private Rectangle bounds;
    private int state = 0;
    private FruitScript collidetFruit;
    private CollisionTask collisionTask;

    public boolean dead() {
        return hp <= 0;
    }

    public void setHp(int dmg) {
        hp = hp - dmg;
    }

    public int getDmg() {
        return dmg;
    }


    public TomatoScript(Batch b, BitmapFont bf, ArrayList<FruitScript> fruits) {
        this.bch = b;
        this.bFont = bf;
        this.fruits = fruits;
    }

    @Override
    public void init(CompositeActor entity) {
        this.tomato = entity;
        tomato.setPosition(100, 129);
        bounds = new Rectangle(tomato.getX(), tomato.getY(), tomato.getWidth(), tomato.getHeight());
        drawHp();


    }

    @Override
    public void act(float delta) {
        switch (state) {
            case 0:
                tomato.setX(tomato.getX() + speed * delta);
                bounds.setX(tomato.getX());
                drawHp();
                if (tomato.getX() >= 1090) {
                    tomato.remove();
                }
                if (collisionFound()) {
                    collidetFruit.setCol(true);
                    state++;
                }
                break;
            case 1:
                if (collisionAct(collidetFruit)) {
                    if (dead()) {
                        tomato.remove();
                        if (!collidetFruit.dead()) {
                            collidetFruit.setCol(false);
                        }
                        tomato.getScripts().remove(this);

                    }
                    if (collidetFruit.dead()) {
                        collidetFruit.getEntity().remove();
                        collidetFruit.getEntity().getScripts().remove(collidetFruit);
                        fruits.remove(collidetFruit);
                        if (!dead()) {
                            state = 0;
                        }

                    }
                }
                break;


        }


    }


    private boolean collisionFound() {
        for (FruitScript f : fruits) {
            if (f.getBounds().overlaps(bounds)) {
                collidetFruit = f;
                return true;
            }

        }
        return false;

    }

    boolean collisionAct(final FruitScript f) {
        Timer.schedule(new CollisionTask(f, this), 0, 50);
        drawHp();
        f.drawHp();
        return true;

    }

    @Override
    public void dispose() {
        bch.dispose();
        bFont.dispose();
    }

    private void drawHp() {
        bch.begin();
        bFont.draw(bch, Integer.toString(hp), tomato.getX() + 20, tomato.getY() + 95);
        bch.end();
    }
}
