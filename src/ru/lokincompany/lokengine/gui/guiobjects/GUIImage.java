package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUIImageFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIImage extends GUIObject {

    protected GUIImageFramePart framePart;

    public GUIImage(Vector2i position, Vector2i size) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size);
    }

    public GUIImage(Vector2i position, Vector2i size, String path) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size, path);
    }

    public GUIImage() {
        this(new Vector2i(), new Vector2i());
    }

    public GUIImage(String path) {
        this(new Vector2i(), new Vector2i(), path);
    }

    public Texture getTexture() {
        return framePart.texture;
    }

    public void setTexture(Texture texture) {
        framePart.texture = texture;
    }

    public Color getColor() {
        return framePart.color;
    }

    public void setColor(Color color) {
        framePart.color = color;
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }

}
