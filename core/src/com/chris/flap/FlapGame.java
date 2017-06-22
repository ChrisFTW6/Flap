package com.chris.flap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.chris.helpers.AssetLoader;
import com.chris.screens.GameScreen;

public class FlapGame extends Game {

    @Override
    public void create() {
        Gdx.app.log("FlapGame", "created");
        AssetLoader.load();
        setScreen(new GameScreen());
    }
    @Override
    public void dispose(){
        super.dispose();
        AssetLoader.dispose();
    }
}
