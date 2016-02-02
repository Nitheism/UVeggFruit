package com.nitheism.uveggfruit.Stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitheism.uveggfruit.ActorScripts.FirstLevelScript;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.nitheism.uveggfruit.UVeggFruit;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;


public class PlayStage implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont bitmapFont;
    private FruitPlayer fruitPlayer;
    private VeggiePlayer veggiePlayer;
    private UVeggFruit uvf;
    private CompositeActor UI;
    private float densityT = 30 * Gdx.graphics.getDensity();


    public PlayStage(UVeggFruit uvf) {
        this.uvf = uvf;

    }


    public void draw() {
        MenuStage mStage;
        if (veggiePlayer.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "DEFEAT", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf);
                this.hide();
                uvf.setScreen(mStage);
            }

        }
        if (fruitPlayer.getHealth() <= 0) {
            stage.getBatch().begin();
            bitmapFont.draw(stage.getBatch(), "VICTORY", 640, 360);
            stage.getBatch().end();
            UI.getScripts().clear();
            if (Gdx.input.justTouched()) {
                mStage = new MenuStage(uvf);
                this.hide();
                uvf.setScreen(mStage);
            }

        }
        stage.getBatch().begin();
        bitmapFont.draw(stage.getBatch(), Integer.toString(fruitPlayer.getHealth()), 1200, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(veggiePlayer.getHealth()), 50, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(veggiePlayer.getMoney()), 60, 420);
        stage.getBatch().end();


    }


    @Override
    public void show() {
        camera = new OrthographicCamera();
        Viewport vp = new StretchViewport(1280, 720, camera);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        fruitPlayer = new FruitPlayer();
        veggiePlayer = new VeggiePlayer();
        SceneLoader stageLoader = new SceneLoader();
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("Karmakooma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.color = Color.WHITE;
        FTFP.size = Math.round(densityT);
        bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        CompositeItemVO sceneComposites = new CompositeItemVO(stageLoader.loadScene("MainScene").composite);
        UI = new CompositeActor(sceneComposites, stageLoader.getRm());
        FirstLevelScript firstLevelScript = new FirstLevelScript(stageLoader, bitmapFont, stage, veggiePlayer, fruitPlayer);
        UI.addScript(firstLevelScript);
        stage.addActor(UI);
        draw();

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        draw();


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


    }
}
