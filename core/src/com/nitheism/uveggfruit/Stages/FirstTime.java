package com.nitheism.uveggfruit.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitheism.uveggfruit.UVeggFruit;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;


public class FirstTime implements Screen {
    private Stage stage;
    private UVeggFruit uVeggFruit;
    private Music music;
    private OrthographicCamera camera;
    private float densityT = 60 * Gdx.graphics.getDensity();
    private BitmapFont bitmapFont;


    public FirstTime(UVeggFruit uVeggFruit, Music music) {
        this.uVeggFruit = uVeggFruit;
        this.music = music;
    }


    @Override
    public void show() {
        stage = new Stage();
        camera = new OrthographicCamera();
        Viewport vp = new StretchViewport(1280, 720, camera);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        SceneLoader sc = new SceneLoader();
        CompositeItemVO sceneComposites = new CompositeItemVO(sc.loadScene("Login").composite);
        CompositeActor UI = new CompositeActor(sceneComposites, sc.getRm());
        music.setLooping(true);
        music.play();
        stage.addActor(UI);
        UI.getItem("regisbutton").addListener(buttonClickListener);
        UI.getItem("loginbutton").addListener(buttonClickListener);
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("Karmakooma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.color = Color.WHITE;
        FTFP.size = Math.round(densityT);
        bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        Skin defaultSkin = new Skin(Gdx.files.internal("uiskin.json"));
        defaultSkin.add("default-font", bitmapFont, BitmapFont.class);
        final TextField username = new TextField("", defaultSkin);
        final TextField password = new TextField("", defaultSkin);
        username.setSize(250, 50);
        password.setSize(250, 50);
        password.setPasswordCharacter('*');
        password.setPasswordMode(true);
        password.setPosition(500, 300);
        username.setPosition(500, 400);
        stage.addActor(username);
        stage.addActor(password);
        UI.getItem("regisbutton").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences prefs = Gdx.app.getPreferences("UVeggFruit");
                prefs.putString("user", username.getText());
                prefs.putString("password", password.getText());
                prefs.flush();
                MenuStage m = new MenuStage(uVeggFruit, music, true);
                stage.dispose();
                uVeggFruit.setScreen(m);
            }
        });
        UI.getItem("loginbutton").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences prefs = Gdx.app.getPreferences("UVeggFruit");
                prefs.putString("user", username.getText());
                prefs.putString("password", password.getText());
                prefs.flush();
                MenuStage m = new MenuStage(uVeggFruit, music, true);
                stage.dispose();
                uVeggFruit.setScreen(m);
            }
        });

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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
