package gr.tylr.level;

import org.newdawn.slick.tiled.TiledMap;

public class Level {

    TiledMap map;

    public Level(TiledMap map)  {
        this.map = map;
    }

    public void render() {
        map.render(0, 0);
    }

    public TiledMap getTileMap() {
        return map;
    }
}