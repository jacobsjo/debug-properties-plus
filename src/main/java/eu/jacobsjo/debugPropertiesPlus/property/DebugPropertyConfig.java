package eu.jacobsjo.debugPropertiesPlus.property;

public record DebugPropertyConfig (
        boolean per_world,
        boolean server,
        boolean singleplayer_only,
        boolean require_op
) {
    public static final DebugPropertyConfig CLIENT = new DebugPropertyConfig(false, false, false, false);
    public static final DebugPropertyConfig CLIENT_OP = new DebugPropertyConfig(false, false, false, true);
    public static final DebugPropertyConfig SINGLEPLAYER = new DebugPropertyConfig(false, false, true, false);
    public static final DebugPropertyConfig SERVER_GLOBAL = new DebugPropertyConfig(false, true, false, false);
    public static final DebugPropertyConfig SERVER = new DebugPropertyConfig(true, false, false, false);
}
