package com.nitheism.uveggfruit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.nitheism.uveggfruit.Stages.MenuStage;



public class UVeggFruit extends Game {
    private Music music;

    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("onlymeith_-_1033.mp3"));
        music.play();
        //FirstTime firstTime = new FirstTime(this,music);
        //setScreen(firstTime);
        MenuStage menuStage = new MenuStage(this, music, true);
        setScreen(menuStage);
    }

    @Override
    public void dispose() {
        music.dispose();
    }
}

