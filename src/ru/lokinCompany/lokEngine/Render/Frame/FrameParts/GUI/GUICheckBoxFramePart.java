package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import ru.lokinCompany.lokEngine.Loaders.TextureLoader;
import ru.lokinCompany.lokEngine.Render.Enums.FramePartType;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Render.Frame.FramePart;
import ru.lokinCompany.lokEngine.Render.Texture;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.ColorRGB;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class GUICheckBoxFramePart extends FramePart {

    public GUIImageFramePart imageFramePart;
    public Vector2i position;
    public Vector2i size;
    public Color color;
    public boolean status;

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, String path, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.imageFramePart = new GUIImageFramePart(position, size, path);
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, Texture texture, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.imageFramePart.texture = texture;
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.size = size;
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.position = position;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.size = size;
        this.color = new ColorRGB(219,160,37,255);
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        glColor4f(color.red, color.green, color.blue, color.alpha);
        imageFramePart.position = position;
        imageFramePart.size = size;
        if (status){
            glBegin(GL_QUADS);

            glVertex3f(position.x, position.y, 0);
            glVertex3f(size.x + position.x, position.y, 0);
            glVertex3f(size.x + position.x, size.y + position.y, 0);
            glVertex3f(position.x, size.y + position.y, 0);

            glEnd();

            imageFramePart.partRender(builderProperties);
        }else{
            glBegin(GL_LINE_STRIP);

            glVertex3f(position.x, position.y, 0);
            glVertex3f(size.x + position.x, position.y, 0);
            glVertex3f(size.x + position.x, size.y + position.y - 1, 0);
            glVertex3f(position.x + 1, size.y + position.y, 0);
            glVertex3f(position.x, position.y, 0);

            glEnd();
        }
    }
}
