package com.nitheism.uveggfruit.Online;


import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

public class NewCreepRequest {
    private CompositeActor actor;
    private IActorScript script;

    public NewCreepRequest(CompositeActor actor, IActorScript script) {
        this.actor = actor;
        this.script = script;
    }

    public CompositeActor getActor() {
        return actor;
    }


    public IActorScript getScript() {
        return script;
    }


}
