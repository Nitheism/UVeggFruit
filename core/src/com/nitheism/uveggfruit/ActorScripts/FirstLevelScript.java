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


public class FirstLevelScript implements IActorScript {

    private SceneLoader stageLoader;
    private BitmapFont bitmapFont;
    private Stage stage;
    private CompositeActor pear;
    private PearScript pScript;
    private VeggiePlayer veggiePlayer;
    private FruitPlayer fruitPlayer;
    private TomatoScript tScript;
    private CompositeActor tomato;
    private ArrayList<FruitScript> fruits;

    public FirstLevelScript(SceneLoader stageLoader, BitmapFont bitmapFont, Stage stage, VeggiePlayer vp, FruitPlayer fp) {
        this.stageLoader = stageLoader;
        this.bitmapFont = bitmapFont;
        this.stage = stage;
        this.veggiePlayer = vp;
        this.fruitPlayer = fp;
    }

    @Override
    public void init(CompositeActor entity) {
        fruits = new ArrayList<FruitScript>();
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        entity.getItem("tomatobtn").addListener(buttonClickListener);
        entity.getItem("tomatobtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (veggiePlayer.getMoney() >= 10) {
                    tScript = new TomatoScript(stage.getBatch(), bitmapFont, fruits, fruitPlayer);
                    tomato = new CompositeActor(stageLoader.loadVoFromLibrary("tomato"), stageLoader.getRm());
                    tomato.addScript(tScript);
                    stage.addActor(tomato);
                    veggiePlayer.setMoney(-10);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            pear = new CompositeActor(stageLoader.loadVoFromLibrary("pear"), stageLoader.getRm());
                            pScript = new PearScript(stage.getBatch(), bitmapFont, veggiePlayer);
                            pear.addScript(pScript);
                            stage.addActor(pear);
                            fruits.add(pScript);
                        }
                    }, 2);

                }

            }
        });
    }

    @Override
    public void act(float delta) {


    }

    @Override
    public void dispose() {

    }
}
