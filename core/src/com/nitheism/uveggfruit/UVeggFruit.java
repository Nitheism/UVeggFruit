package com.nitheism.uveggfruit;

import com.badlogic.gdx.Game;
import com.nitheism.uveggfruit.Stages.MenuStage;



public class UVeggFruit extends Game {


    @Override
    public void create() {
        MenuStage menuStage = new MenuStage(this);
        setScreen(menuStage);
    }


}

