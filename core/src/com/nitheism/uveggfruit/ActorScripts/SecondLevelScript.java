package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;


public class SecondLevelScript implements IActorScript {
    private SceneLoader stageLoader;
    private float timeaux = 0;
    private BitmapFont bitmapFont;
    private Stage stage;
    private VeggiePlayer veggiePlayer;
    private FruitPlayer fruitPlayer;
    private OnionScript onionScript;
    private CompositeActor onion;
    private TomatoScript tScript;
    private CompositeActor tomato;
    private ArrayList<FruitScript> fruits;
    private ArrayList<VeggieScript> veggies;
    private boolean musicOn;

    public SecondLevelScript(SceneLoader stageLoader, BitmapFont bitmapFont, Stage stage, VeggiePlayer vp, FruitPlayer fp, boolean musicOn) {
        //initialization
        this.stageLoader = stageLoader;
        this.bitmapFont = bitmapFont;
        this.stage = stage;
        this.veggiePlayer = vp;
        this.fruitPlayer = fp;
        this.musicOn = musicOn;
    }

    @Override
    public void init(CompositeActor entity) {
        //initializing lists of fruits and vegetables
        fruits = new ArrayList<FruitScript>();
        veggies = new ArrayList<VeggieScript>();
        /*creating ButtonClickListener to change the state of the button and normal
        listener to handle the spawn of an unit and assign it to the button */
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        entity.getItem("tomatobtn").addListener(buttonClickListener);
        entity.getItem("tomatobtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (veggiePlayer.getMoney() >= 10) {
                    tScript = new TomatoScript(stage.getBatch(), bitmapFont, fruits, veggies, fruitPlayer, musicOn);
                    tomato = new CompositeActor(stageLoader.loadVoFromLibrary("tomato"), stageLoader.getRm());
                    tomato.addScript(tScript);
                    veggies.add(tScript);
                    stage.addActor(tomato);
                    veggiePlayer.setMoney(-tScript.moneyGain());

                }

            }
        });
        CompositeActor onionbtn = new CompositeActor(stageLoader.loadVoFromLibrary("onionbtn"), stageLoader.getRm());
        onionbtn.addListener(buttonClickListener);
        onionbtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (veggiePlayer.getMoney() >= 20) {
                    onion = new CompositeActor(stageLoader.loadVoFromLibrary("onion"), stageLoader.getRm());
                    onionScript = new OnionScript(stage.getBatch(), bitmapFont, fruits, veggies, fruitPlayer, musicOn);
                    onion.addScript(onionScript);
                    veggies.add(onionScript);
                    stage.addActor(onion);
                    veggiePlayer.setMoney(-onionScript.moneyGain());
                }
            }
        });
        onionbtn.setPosition(730,-15);
        stage.addActor(onionbtn);
    }

    @Override
    public void act(float delta) {
        //loops trough  the arraylists and checks if there is a collision between 2 actors
        for (VeggieScript v : veggies) {
            for (FruitScript f : fruits) {
                if (v.getBounds().overlaps(f.getBounds())) {
                    CollisionTask t = new CollisionTask(f, v, fruitPlayer, veggiePlayer);
                    Timer.schedule(t, 0, 0, 0);
                    break;
                }
            }

        }
        //spawns a pear every 20 seconds since it is 1st level
        if (timeaux >= 10) {
            PearScript pScript = new PearScript(stage.getBatch(), bitmapFont, veggiePlayer, veggies, fruits, musicOn);
            CompositeActor pear = new CompositeActor(stageLoader.loadVoFromLibrary("pear"), stageLoader.getRm());
            pear.addScript(pScript);
            stage.addActor(pear);
            fruits.add(pScript);
            timeaux = 0;
        } else {
            timeaux += delta;
        }

    }

    @Override
    public void dispose() {

    }
}


