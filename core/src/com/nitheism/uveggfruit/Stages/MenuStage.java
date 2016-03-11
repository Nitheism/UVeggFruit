package com.nitheism.uveggfruit.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitheism.uveggfruit.UVeggFruit;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

public class MenuStage implements Screen {
    private PlayStage playStage;
    private UVeggFruit uVeggFruit;
    private Stage stage;
    private Music music;
    private boolean musicOn;
    private OnlineStage onlineStage;



    private OrthographicCamera camera;


    public MenuStage(UVeggFruit uVeggFruit, Music music, boolean musicOn) {
        this.uVeggFruit = uVeggFruit;
        this.music = music;
        this.musicOn = musicOn;

    }


    @Override
    public void show() {
        //gets preferences and sets camera and viewports to support multiple screens
        final Preferences prefs = Gdx.app.getPreferences("UVeggFruit");
        camera = new OrthographicCamera();
        Viewport vp = new StretchViewport(1280, 720, camera);
        stage = new Stage(vp);
        //sets all input processors and clickListeners
        Gdx.input.setInputProcessor(stage);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        SceneLoader sc = new SceneLoader();
        CompositeItemVO sceneComposites = new CompositeItemVO(sc.loadScene("MainMenu").composite);
        CompositeActor UI = new CompositeActor(sceneComposites, sc.getRm());
        //if music is allowed play it
        if (musicOn) {
            music.setLooping(true);
            music.play();
        }
        //setting all the buttons with their listeners and handling the events
        UI.getItem("playbutton").addListener(buttonClickListener);
        UI.getItem("musicbutton").addListener(buttonClickListener);
        UI.getItem("musicbutton").addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!music.isPlaying()) {
                    music.play();
                    prefs.putBoolean("musicOn",true);
                    prefs.flush();

                } else {
                    prefs.putBoolean("musicOn",false);
                    music.dispose();
                    prefs.flush();

                }

            }

        });
        UI.getItem("playbutton").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.dispose();
                music.dispose();
                playStage = new PlayStage(uVeggFruit, music, music.isPlaying());
                uVeggFruit.setScreen(playStage);
            }
        });
        UI.getItem("exitbutton").addListener(buttonClickListener);
        UI.getItem("exitbutton").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.dispose();
                stage.dispose();
                uVeggFruit.dispose();
            }
        });
        UI.getItem("onlinebutton").addListener(buttonClickListener);
        UI.getItem("onlinebutton").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    stage.dispose();
                    music.dispose();
                    onlineStage = new OnlineStage(uVeggFruit,music,musicOn);
                    uVeggFruit.setScreen(onlineStage);
            }
        });
        //adding the scene
        stage.addActor(UI);

    }

    @Override
    public void render(float delta) {
        //update every frame
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //draw and act every frame
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //resize if the screen is smaller or bigger
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
        stage.dispose();
        playStage.dispose();
        onlineStage.dispose();
    }
}
