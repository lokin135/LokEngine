package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUIImageFramePart;
import LokEngine.Render.Texture;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIImage extends GUIObject {

    GUIImageFramePart framePart;

    public GUIImage(Vector2i position, Vector2i size) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size);
    }

    public GUIImage(Vector2i position, Vector2i size, String path) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size, path);
    }

    public Texture getTexture(){
        return framePart.texture;
    }

    public void setTexture(Texture texture){
        framePart.texture = texture;
    }

    @Override
    public void setPosition(Vector2i position){
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size){
        this.size = size;
        framePart.size = size;
    }

    @Override
    public void update(){
        RuntimeFields.getFrameBuilder().addPart(framePart);
    }

}
