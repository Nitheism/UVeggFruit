package com.nitheism.uveggfruit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nitheism.uveggfruit.Stages.MenuStage;
import com.nitheism.uveggfruit.Stages.PlayStage;


public class UVeggFruit extends ApplicationAdapter {
	private MenuStage menuStage;
	
	@Override
	public void create () {
		menuStage = new MenuStage();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menuStage.act();
		menuStage.draw();

	}
}
