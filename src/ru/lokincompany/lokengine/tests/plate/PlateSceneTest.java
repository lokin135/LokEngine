package ru.lokincompany.lokengine.tests.plate;

import ru.lokincompany.lokengine.applications.ApplicationPlateWorld;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateChunk;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.Mouse;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

class TestChunk extends PlateChunk {
    @Override
    public boolean generate(int chunkID, PlateScene scene) {
        for (int xc = 0; xc < 16; xc++){
            for (int yc = 0; yc < 16; yc++){
                double n = scene.noise.get((xc + (this.xPosition * 16)) / 10d, (yc + (this.yPosition * 16)) / 10d);
                if (n > 0.05)
                    scene.getChunk(chunkID).setPlate(1, xc, yc);
            }
        }
        return true;
    }
}

public class PlateSceneTest extends ApplicationPlateWorld {

    public static void main(String[] args) {
            new PlateSceneTest().start(false, true, false);
    }

    GUIText text;

    @Override
    protected void updateEvent() {
        Keyboard keyboard = window.getKeyboard();
        Camera camera = window.getCamera();
        Mouse mouse = window.getMouse();

        float scroll = -(mouse.getMouseScroll().x + mouse.getMouseScroll().y) * camera.fieldOfView / 10f;

        if (scroll != 0)
            camera.setFieldOfView(camera.fieldOfView + scroll);

        camera.position.x += keyboard.isKeyDown(GLFW_KEY_D) ? camera.fieldOfView * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.x -= keyboard.isKeyDown(GLFW_KEY_A) ? camera.fieldOfView * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.y += keyboard.isKeyDown(GLFW_KEY_W) ? camera.fieldOfView * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.y -= keyboard.isKeyDown(GLFW_KEY_S) ? camera.fieldOfView * applicationRuntime.getDeltaTime() / 90f : 0;

        text.updateText("FPS: " + applicationRuntime.getFps());
    }

    @Override
    protected void initEvent() {
        scene.registerPlate(new GrassPlateHandler());

        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++) {
                scene.loadChunk(new TestChunk(), new Vector2i(x, y));
            }
        }

        text = new GUIText();
        window.getCanvas().addObject(text);
    }

}
