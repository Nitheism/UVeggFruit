package com.nitheism.uveggfruit.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;


public class MenuStage extends Stage {
    private SceneLoader sc;
    private CompositeActor playBtn;
    private CompositeActor onlineBtn;
    private CompositeActor exitBtn;
    private ButtonClickListener buttonClickListener;


    public MenuStage() {
        initMenu();
    }


    private void initMenu() {
        Gdx.input.setInputProcessor(this);
        buttonClickListener = new ButtonClickListener();
        sc = new SceneLoader();
        sc.loadScene("MainMenu");
        playBtn = new CompositeActor(sc.loadVoFromLibrary("playbutton"),sc.getRm());
        onlineBtn = new CompositeActor(sc.loadVoFromLibrary("onlinebutton"),sc.getRm());
        exitBtn = new CompositeActor(sc.loadVoFromLibrary("exitbutton"),sc.getRm());
        playBtn.addListener(buttonClickListener);
        onlineBtn.addListener(buttonClickListener);
        exitBtn.addListener(buttonClickListener);

    }

    @Override
    public void draw() {
        super.draw();
        sc.getEngine().update(Gdx.graphics.getDeltaTime());


    }
}
