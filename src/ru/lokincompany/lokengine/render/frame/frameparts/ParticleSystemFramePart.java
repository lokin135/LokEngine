package ru.lokincompany.lokengine.render.frame.frameparts;

import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.loaders.BufferLoader;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.frame.FramePart;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class ParticleSystemFramePart extends FramePart {

    public Shader shader;
    Sprite sourceSprite;

    int positionsBuffer;
    int sizesBuffer;
    int count;

    public ParticleSystemFramePart(Sprite sourceSprite, Shader shader) {
        super(FramePartType.Scene);
        this.sourceSprite = sourceSprite;

        this.shader = shader;
    }

    public void setSourceSprite(Sprite sprite) {
        this.sourceSprite = sprite;
    }

    public void update(ArrayList<Float> positions, ArrayList<Float> sizes) {
        count = sizes.size();

        BufferLoader.unload(positionsBuffer);
        BufferLoader.unload(sizesBuffer);

        if (count > 0) {
            positionsBuffer = BufferLoader.load(positions);
            sizesBuffer = BufferLoader.load(sizes);
        }
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (count > 0) {
            if (shader == null)
                shader = builderProperties.getParticlesShader();

            if (builderProperties.getActiveShader() == null || !builderProperties.getActiveShader().equals(shader)) {
                builderProperties.useShader(shader);
            }

            int uvBuffer = sourceSprite.uvBuffer != -1 ? sourceSprite.uvBuffer : builderProperties.getUVBuffer();
            int textureBuffer = sourceSprite.texture.buffer != -1 ? sourceSprite.texture.buffer : builderProperties.getUnknownTexture().buffer;

            builderProperties.getBuilderWindow().getCamera().updateView();

            glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, sourceSprite.vertexBuffer);
            glVertexAttribPointer(
                    0,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(0, 0);

            glEnableVertexAttribArray(1);
            glBindBuffer(GL_ARRAY_BUFFER, uvBuffer);
            glVertexAttribPointer(
                    1,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(1, 0);

            glEnableVertexAttribArray(2);
            glBindBuffer(GL_ARRAY_BUFFER, sizesBuffer);
            glVertexAttribPointer(
                    2,
                    1,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(2, 1);

            glEnableVertexAttribArray(3);
            glBindBuffer(GL_ARRAY_BUFFER, positionsBuffer);
            glVertexAttribPointer(
                    3,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(3, 1);

            glBindTexture(GL_TEXTURE_2D, textureBuffer);

            glDrawArraysInstanced(GL_QUADS, 0, 8, count);

            glBindTexture(GL_TEXTURE_2D, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glDisableVertexAttribArray(3);
        }
    }
}
