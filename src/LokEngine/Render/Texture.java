package LokEngine.Render;

import LokEngine.Loaders.TextureLoader;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Saveable;

import java.io.IOException;

public class Texture implements Saveable {

    public int buffer;
    public int sizeX;
    public int sizeY;

    public Texture(){}

    public Texture(int buffer, int sizeX, int sizeY){
        this.buffer = buffer;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public boolean equals(Object obj){
        Texture objt = (Texture)obj;

        return objt.buffer == buffer &&
                objt.sizeX == sizeX &&
                objt.sizeY == sizeY;
    }

    @Override
    public String save() {
        return TextureLoader.getPath(this);
    }

    @Override
    public Saveable load(String savedString) {
        try {
            Texture loadedTexture = TextureLoader.loadTexture(savedString);
            this.buffer = loadedTexture.buffer;
            this.sizeX = loadedTexture.sizeX;
            this.sizeY = loadedTexture.sizeY;
        } catch (IOException e) {
            Logger.warning("Fail load texture from save!","LokEngine_Texture");
            Logger.printException(e);
        }

        return this;
    }
}
