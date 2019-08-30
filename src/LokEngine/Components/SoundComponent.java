package LokEngine.Components;

import LokEngine.Loaders.SoundLoader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Components.AdditionalObjects.Sound;
import LokEngine.Tools.Utilities.SoundType;
import org.lwjgl.openal.AL10;

public class SoundComponent extends Component {

    public Sound sound;
    private int source;

    public SoundComponent(String path){
        sound = SoundLoader.loadSound(path, SoundType.WAV);
        source = AL10.alGenSources();

        AL10.alSourcef(this.source,AL10.AL_GAIN,1);
        AL10.alSourcef(this.source,AL10.AL_PITCH,1);
        AL10.alSource3f(this.source,AL10.AL_POSITION,0,0,0);
    }

    public void setSound(Sound sound){
        this.sound = sound;
    }

    public void setPitch(float pitch){
        AL10.alSourcef(this.source,AL10.AL_PITCH,pitch);
    }

    public void play(){
        AL10.alSourcei(source,AL10.AL_BUFFER,sound.buffer);
        AL10.alSourcePlay(source);
    }

    public void setLooping(boolean loop){
        AL10.alSourcei(source,AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public boolean isPlaying(){
        return AL10.alGetSourcei(source,AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVolume(float volume){
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
    }

    public void pause(){
        AL10.alSourcePause(source);
    }

    public void stop(){
        AL10.alSourceStop(source);
    }

    @Override
    public String getName(){
        return "Sound Component";
    }

    @Override
    public void update(SceneObject source){
        AL10.alSource3f(this.source,AL10.AL_POSITION,source.position.x,source.position.y,0);
    }

}
