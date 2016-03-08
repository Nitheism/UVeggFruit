package com.nitheism.uveggfruit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.nitheism.uveggfruit.Stages.FirstTime;
import com.nitheism.uveggfruit.Stages.MenuStage;

import java.util.Map;


public class UVeggFruit extends Game {
    private Music music;

    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("onlymeith_-_1033.mp3"));
        music.play();
        Preferences prefs = Gdx.app.getPreferences("UVeggFruit");
        Map prefsmap = prefs.get();
        if (!prefsmap.isEmpty()) {
            MenuStage menuStage = new MenuStage(this, music, true);
            setScreen(menuStage);
        }else {
            FirstTime firstTime = new FirstTime(this, music);
            setScreen(firstTime);
        }


    }

    @Override
    public void dispose() {
        music.dispose();
    }
}

