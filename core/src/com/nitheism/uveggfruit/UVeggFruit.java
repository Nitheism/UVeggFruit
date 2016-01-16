package com.nitheism.uveggfruit;

import com.badlogic.gdx.Game;
import com.nitheism.uveggfruit.Stages.MenuStage;



public class UVeggFruit extends Game {
    private MenuStage menuStage;



    @Override
    public void create() {
        menuStage = new MenuStage(this);
        setScreen(menuStage);
    }


}

