package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.FrameParts.GUI.GUIGraphFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class GUIGraph extends GUIObject {

    ArrayList<Float> points = new ArrayList<>();

    float maxHeight;
    float minHeight;
    int maxPoints;

    GUIGraphFramePart framePart;
    GUIFreeTextDrawer freeTextDrawer;

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight, int maxPoints, Color color, Color color2) {
        super(position, size);
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxPoints = maxPoints > 0 ? maxPoints : 1;

        this.freeTextDrawer = new GUIFreeTextDrawer("Times New Roman",0,Math.min(Math.max(size.y / 10, 10),24),true);

        framePart = new GUIGraphFramePart(position, size, points, maxHeight, minHeight, maxPoints, color, color2, freeTextDrawer);
    }

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight, int maxPoints, Color color) {
        this(position,size,maxHeight,minHeight,maxPoints,color,new Color(color.red,color.green,color.blue,color.alpha / 3f));
    }

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight, int maxPoints) {
        this(position, size, maxHeight, minHeight, maxPoints, new Color(1, 1, 1, 1));
    }

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight) {
        this(position, size, maxHeight, minHeight, size.x);
    }

    public GUIGraph(Vector2i position, Vector2i size) {
        this(position, size, 100, 0);
    }

    public void addPoint(float height) {
        if (maxPoints != 0 && maxPoints == points.size()) {
            points.remove(0);
        }

        points.add(Math.min(maxHeight, Math.max(minHeight, height)));
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
        freeTextDrawer.update(partsBuilder,properties);
        partsBuilder.addPart(framePart);
    }


}
