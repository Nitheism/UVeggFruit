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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitheism.uveggfruit.ActorScripts.FirstLevelScript;
import com.nitheism.uveggfruit.ActorScripts.SecondLevelScript;
import com.nitheism.uveggfruit.GameMessages;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;
import com.nitheism.uveggfruit.UVeggFruit;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;


public class PlayStage implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont bitmapFont;
    private FruitPlayer fruitPlayer;
    private VeggiePlayer veggiePlayer;
    private UVeggFruit uvf;
    private CompositeActor UI;
    private float densityT = 18;
    private Music music;
    private boolean musicOn;
    private SceneLoader stageLoader;
    private Screen thisScreen = this;
    private GameMessages gameMessages = new GameMessages();
    private int level;


    public PlayStage(UVeggFruit uvf, Music music, boolean musicOn, int level) {
        this.uvf = uvf;
        this.music = music;
        this.musicOn = musicOn;
        this.level = level;
    }


    public void draw() {
        if (veggiePlayer.getHealth() <= 0) {
            UI.getScripts().clear();
            switch (level) {
                case 1:
                    UI.getScripts().clear();
                    startDialog(gameMessages.getLoseFirstLevel(), false);
                    break;
                case 2:
                    UI.getScripts().clear();
                    startDialog(gameMessages.getLoseSecondLevel(), false);
                    break;
            }

        }

        if (fruitPlayer.getHealth() <= 0) {
            UI.getScripts().clear();
            switch (level) {
                case 1:
                    UI.getScripts().clear();
                    startDialog(gameMessages.getWinFirstLevel(), true);
                    break;
                case 2:
                    UI.getScripts().clear();
                    startDialog(gameMessages.getWinSecondLevel(), true);
                    break;
            }


        }

        stage.getBatch().begin();
        bitmapFont.draw(stage.getBatch(), "UNITS: ", 500, 60);
        bitmapFont.draw(stage.getBatch(), Integer.toString(fruitPlayer.getHealth()), 1200, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(veggiePlayer.getHealth()), 50, 390);
        bitmapFont.draw(stage.getBatch(), Integer.toString(veggiePlayer.getMoney()), 60, 420);
        stage.getBatch().end();


    }


    public void startDialog(String message, final boolean gameWon) {
        ButtonClickListener btnclicklisten = new ButtonClickListener();
        //Setting up listeners for the dialog's buttons
        final CompositeActor dialog = new CompositeActor(stageLoader.loadVoFromLibrary("dialog"), stageLoader.getRm());
        dialog.getItem("nextbtn").addListener(btnclicklisten);
        dialog.getItem("nextbtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
                if (gameWon) {
                    switch (level) {
                        case 1:
                            level++;
                            Preferences prefs = Gdx.app.getPreferences("UVeggFruit");
                            prefs.putInteger("level",level);
                            prefs.flush();
                            dialog.remove();
                            stage.dispose();
                            thisScreen.hide();
                            PlayStage playStage = new PlayStage(uvf, music, musicOn, level);
                            uvf.setScreen(playStage);
                            break;
                    }
                } else {
                    //adding script in order to play the game and add it to the UI
                    dialog.remove();
                    switch (level) {
                        case 1:
                            FirstLevelScript firstLevelScript = new FirstLevelScript(stageLoader, bitmapFont, stage, veggiePlayer, fruitPlayer, musicOn);
                            UI.addScript(firstLevelScript);
                            break;
                        case 2:
                            SecondLevelScript secondLevelScript = new SecondLevelScript(stageLoader, bitmapFont, stage, veggiePlayer, fruitPlayer, musicOn);
                            UI.addScript(secondLevelScript);
                            break;
                    }

                }
            }
        });
        dialog.getItem("menubtn").addListener(btnclicklisten);
        dialog.getItem("menubtn").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //disposing/removing all unneeded things and setting the screen to menu screen
                dialog.remove();
                stage.dispose();
                thisScreen.hide();
                MenuStage m = new MenuStage(uvf, music, musicOn);
                uvf.setScreen(m);
            }
        });
        //gets the label of the dialog and sets the given text, position and add it to the stage
        Label text = (Label) dialog.getItem("textfield");
        text.setWrap(true);
        text.setText(message);
        dialog.setPosition(450, 320);
        stage.addActor(dialog);


    }


    @Override
    public void show() {
        //setting camera in order to support multiple screen sizes
        camera = new OrthographicCamera();
        Viewport vp = new StretchViewport(1280, 720, camera);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        //setting players
        fruitPlayer = new FruitPlayer();
        veggiePlayer = new VeggiePlayer();
        //loading font and stage graphics
        stageLoader = new SceneLoader();
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("Karmakooma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.color = Color.WHITE;
        FTFP.size = Math.round(densityT);
        bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        CompositeItemVO sceneComposites = new CompositeItemVO(stageLoader.loadScene("MainScene").composite);
        UI = new CompositeActor(sceneComposites, stageLoader.getRm());
        stage.addActor(UI);
        draw();
        //if music is allowed play it
        if (musicOn) {
            music.setLooping(true);
            music.play();
        }
        switch (level) {
            case 1:
                startDialog(gameMessages.getWelcomeMessage(), false);
                break;
            case 2:
                startDialog(gameMessages.getWelcomeMessagSecond(), false);

        }


    }

    @Override
    public void render(float delta) {
        //update camera every frame
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //call stage.draw every frame and stage.act and draw();
        stage.act(delta);
        stage.draw();
        draw();


    }

    @Override
    public void resize(int width, int height) {
        //resize the screen when needed
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
