package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.render.text.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class GUITextFramePart extends FramePart {

    public Font font;
    public String text;
    public Vector2i position;
    public Color color;
    public TextColorShader shader;
    public Vector2i maxSize;
    public Vector2i lastRenderSize = new Vector2i();

    public GUITextFramePart(String text, Font font, Color color) {
        super(FramePartType.GUI);
        this.color = color;
        this.text = text;
        this.font = font;
    }

    public GUITextFramePart(String text, Font font, TextColorShader shader) {
        super(FramePartType.GUI);
        this.text = text;
        this.font = font;
        this.shader = shader;
    }

    public int getWidth() {
        return lastRenderSize.x - position.x + font.getHeight(text) / text.length();
    }

    public int getHeight() {
        return lastRenderSize.y - position.y + font.getFontHeight();
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (color != null) {
            lastRenderSize = font.drawText(text, new Vector2i(position.x, position.y), maxSize, color);
        } else {
            lastRenderSize = font.drawText(text, new Vector2i(position.x, position.y), maxSize, shader);
        }
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }
}
