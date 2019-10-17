package LokEngine.Components.AdditionalObjects.Sound;

import LokEngine.Loaders.SoundLoader;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Saveable;

public class RawWavSound extends Sound {
    public RawWavSound() {}

    public RawWavSound(String path) {
        this.path = path;
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        try {
            buffer = SoundLoader.loadWAV(savedString).buffer;
        } catch (Exception e) {
            Logger.warning("Fail load raw wav!", "LokEngine_RawWavSound");
            e.printStackTrace();
        }
        return this;
    }

}
