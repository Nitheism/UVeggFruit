package com.nitheism.uveggfruit.Stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nitheism.uveggfruit.ActorScripts.FruitScript;
import com.nitheism.uveggfruit.ActorScripts.PearScript;
import com.nitheism.uveggfruit.ActorScripts.TomatoScript;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

import java.util.ArrayList;


public class PlayStage extends Stage implements Screen {

    private SceneLoader stageLoader;
    private CompositeActor tomato;
    private CompositeActor pear;
    private TomatoScript tScript;
    private PearScript pScript;
    private ArrayList<FruitScript> fruits;
    private FreeTypeFontGenerator FTFG;
    private FreeTypeFontGenerator.FreeTypeFontParameter FTFP;
    private BitmapFont bitmapFont;


    public PlayStage() {
        initGame();
    }


    public void initGame() {
        fruits = new ArrayList<FruitScript>();
        stageLoader = new SceneLoader();
        stageLoader.loadScene("MainScene");
        FTFG = new FreeTypeFontGenerator(Gdx.files.internal("Karmakooma.ttf"));
        FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.color = Color.WHITE;
        FTFP.size = 24;
        bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        getBatch().begin();

    }

    @Override
    public void draw() {
        super.draw();
        stageLoader.getEngine().update(Gdx.graphics.getDeltaTime());


    }

    @Override
    public void act() {
        super.act();
        if (Gdx.input.justTouched()) {
            tScript = new TomatoScript(getBatch(), bitmapFont, fruits);
            pScript = new PearScript(getBatch(), bitmapFont);
            tomato = new CompositeActor(stageLoader.loadVoFromLibrary("tomato"), stageLoader.getRm());
            pear = new CompositeActor(stageLoader.loadVoFromLibrary("pear"), stageLoader.getRm());
            tomato.addScript(tScript);
            pear.addScript(pScript);
            addActor(tomato);
            addActor(pear);
            fruits.add(pScript);

        }


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.act();
        this.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        super.dispose();
        bitmapFont.dispose();


    }
}
