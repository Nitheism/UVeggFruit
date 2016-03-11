package com.nitheism.uveggfruit.Stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitheism.uveggfruit.ActorScripts.FruitScript;
import com.nitheism.uveggfruit.ActorScripts.PearScript;
import com.nitheism.uveggfruit.ActorScripts.TomatoScript;
import com.nitheism.uveggfruit.ActorScripts.VeggieScript;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.nitheism.uveggfruit.UVeggFruit;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

import java.util.ArrayList;

public class OnlineStage implements Screen {
    private Stage stage;
    private OrthographicCamera camera;
    private ArrayList<FruitScript> fruits;
    private ArrayList<VeggieScript> veggies;
    private SceneLoader sc;
    private CompositeActor UI;
    private BitmapFont bitmapFont;
    private Music music;
    private boolean musicOn;
    private FruitPlayer fp;
    private VeggiePlayer vp;
    private float densityT = 30 * Gdx.graphics.getDensity();
    private UVeggFruit uvf;


    public OnlineStage(UVeggFruit uvf, Music music, boolean musicOn) {
        this.music = music;
        this.musicOn = musicOn;
        this.uvf = uvf;

    }

    @Override
    public void show() {
        if (musicOn) {
            music.setLooping(true);
            music.play();
        }
        camera = new OrthographicCamera();
        Viewport vp = new StretchViewport(1280, 720, camera);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        sc = new SceneLoader();
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("Karmakooma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.color = Color.WHITE;
        FTFP.size = Math.round(densityT);
        bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        if (Gdx.app.getPreferences("UVeggFruit").getString("side").equals("Fruit")) {
            FruitScreen();
            drawF();
        } else if (Gdx.app.getPreferences("UVeggFruit").getString("side").equals("Veggie")) {
            VeggieScreen();
            drawV();
        }
    }

    private void drawF() {
        MenuStage mStage;
        if (vp.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "VICTORY", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf, music, musicOn);
                this.hide();
                uvf.setScreen(mStage);
            }

        }
        if (fp.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "DEFEAT", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf, music, musicOn);
                this.hide();
                uvf.setScreen(mStage);
            }

        }
        stage.getBatch().begin();
        bitmapFont.draw(stage.getBatch(), Integer.toString(fp.getHealth()), 1200, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(vp.getHealth()), 50, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(fp.getMoney()), 1190, 420);
        stage.getBatch().end();


    }

    private void drawV() {
        MenuStage mStage;
        if (vp.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "DEFEAT", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf, music, musicOn);
                this.hide();
                uvf.setScreen(mStage);
            }

        }
        if (fp.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "VICTORY", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf, music, musicOn);
                this.hide();
                uvf.setScreen(mStage);
            }

        }

        stage.getBatch().begin();
        bitmapFont.draw(stage.getBatch(), Integer.toString(fp.getHealth()), 1200, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(vp.getHealth()), 50, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(vp.getMoney()), 60, 420);
        stage.getBatch().end();


    }

    private void FruitScreen() {
        fruits = new ArrayList<FruitScript>();
        veggies = new ArrayList<VeggieScript>();
        fp = new FruitPlayer();
        vp = new VeggiePlayer();
        sc = new SceneLoader();
        CompositeItemVO sceneComposites = new CompositeItemVO(sc.loadScene("FruitScene").composite);
        UI = new CompositeActor(sceneComposites, sc.getRm());
        UI.getItem("pearbtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (fp.getMoney() >= 10) {
                    CompositeActor pear = new CompositeActor(sc.loadVoFromLibrary("pear"), sc.getRm());
                    PearScript ps = new PearScript(stage.getBatch(), bitmapFont, vp, veggies, fruits, musicOn);
                    fruits.add(ps);
                    pear.addScript(ps);
                    stage.addActor(pear);
                    fp.setMoney(-10);
                }

            }
        });
        stage.addActor(UI);
    }

    private void VeggieScreen() {
        fruits = new ArrayList<FruitScript>();
        veggies = new ArrayList<VeggieScript>();
        fp = new FruitPlayer();
        vp = new VeggiePlayer();
        sc = new SceneLoader();
        CompositeItemVO sceneComposites = new CompositeItemVO(sc.loadScene("MainScene").composite);
        UI = new CompositeActor(sceneComposites, sc.getRm());
        UI.getItem("tomatobtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (vp.getMoney() >= 10) {
                    CompositeActor tomato = new CompositeActor(sc.loadVoFromLibrary("tomato"), sc.getRm());
                    TomatoScript ts = new TomatoScript(stage.getBatch(), bitmapFont, fruits, veggies, fp, musicOn);
                    veggies.add(ts);
                    tomato.addScript(ts);
                    stage.addActor(tomato);
                    vp.setMoney(-10);
                }

            }
        });
        stage.addActor(UI);
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (Gdx.app.getPreferences("UVeggFruit").getString("side").equals("Fruit")) {
            drawF();
        } else if (Gdx.app.getPreferences("UVeggFruit").getString("side").equals("Veggie")) {
            drawV();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        bitmapFont.dispose();
        music.dispose();

    }
}
