package ru.lokincompany.lokengine.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;
import ru.lokincompany.lokengine.loaders.ShaderLoader;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokengine.tools.vectori.Vector3i;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements Saveable {

    public int program;
    public String vertPath;
    public String fragPath;

    HashMap<String, Integer> uniformsName = new HashMap<>();

    public Shader() {
    }

    public Shader(int program, String vertPath, String fragPath) {
        this.program = program;
        this.vertPath = vertPath;
        this.fragPath = fragPath;
    }

    public boolean equals(Object obj) {
        Shader objs = (Shader) obj;
        return objs.program == program;
    }

    protected int getUniformLocationID(String name){
        if (!uniformsName.containsKey(name)){
            int id = glGetUniformLocation(program, name);
            uniformsName.put(name, id);
            return id;
        }else{
            return uniformsName.get(name);
        }
    }

    public void setUniformData(String uniformName, int data) {
        glUniform1i(getUniformLocationID(uniformName), data);
    }

    public void setUniformData(String uniformName, Vector2i data) {
        glUniform2i(getUniformLocationID(uniformName), data.x, data.y);
    }

    public void setUniformData(String uniformName, Vector3i data) {
        glUniform3i(getUniformLocationID(uniformName), data.x, data.y, data.z);
    }

    public void setUniformData(String uniformName, Vector4i data) {
        glUniform4i(getUniformLocationID(uniformName), data.x, data.y, data.z, data.w);
    }

    public void setUniformData(String uniformName, float data) {
        glUniform1f(getUniformLocationID(uniformName), data);
    }

    public void setUniformData(String uniformName, Vector2f data) {
        glUniform2f(getUniformLocationID(uniformName), data.x, data.y);
    }

    public void setUniformData(String uniformName, Vector3f data) {
        glUniform3f(getUniformLocationID(uniformName), data.x, data.y, data.z);
    }

    public void setUniformData(String uniformName, Vector4f data) {
        glUniform4f(getUniformLocationID(uniformName), data.x, data.y, data.z, data.w);
    }

    public void setUniformData(String uniformName, Matrix2f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(8);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix2fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    public void setUniformData(String uniformName, Matrix3f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(12);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix3fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    public void setUniformData(String uniformName, Matrix4f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    @Override
    public String save() {
        return Base64.toBase64(vertPath + "\n" + fragPath);
    }

    @Override
    public Saveable load(String savedString) {
        String[] patches = Base64.fromBase64(savedString).split("\n");

        try {
            Shader loadedShader = ShaderLoader.loadShader(patches[0], patches[1]);
            this.program = loadedShader.program;
            this.vertPath = loadedShader.vertPath;
            this.fragPath = loadedShader.fragPath;
        } catch (Exception e) {
            Logger.warning("Fail load shader from save!", "LokEngine_Shader");
            Logger.printException(e);
        }
        return this;
    }
}
