package eu.jacobsjo.debugPropertiesPlus.property;

import java.util.Optional;

public record DebugPropertyConfig (
        boolean perWorld,
        boolean onDedicatedServer,
        boolean notOnMultiplayer,
        boolean requiresOp,
        boolean updateDebugRenderer,
        boolean requiresReload
) {

    private DebugPropertyConfig(Builder builder){
        this(builder.perWorld, builder.onDedicatedServer, builder.notOnMultipleyer, builder.requiresOP, builder.updateDebugRenderer, builder.requiresReload);
    }

    public static class Builder{
        private boolean perWorld = false;
        private boolean onDedicatedServer = false;
        private boolean notOnMultipleyer = false;
        private boolean requiresOP = false;
        private boolean updateDebugRenderer = false;
        private boolean requiresReload = false;

        public Builder perWorld(){
            this.perWorld = true;
            return this;
        }

        public Builder onDedicatedServer(){
            this.onDedicatedServer = true;
            return this;
        }

        public Builder notOnMultipleyer(){
            this.notOnMultipleyer = true;
            return this;
        }

        public Builder requiresOp(){
            this.requiresOP = true;
            return this;
        }

        public Builder updateDebugRenderer(){
            this.updateDebugRenderer = true;
            return this;
        }

        public Builder requiresReload(){
            this.requiresReload = true;
            return this;
        }

        public DebugPropertyConfig build(){
            return new DebugPropertyConfig(this);
        }

    }
}
