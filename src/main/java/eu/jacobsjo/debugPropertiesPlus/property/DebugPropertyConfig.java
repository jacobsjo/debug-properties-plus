package eu.jacobsjo.debugPropertiesPlus.property;

public record DebugPropertyConfig (
        boolean per_world,
        boolean server,
        boolean singleplayer_only,
        boolean require_op,
        boolean is_renderer
) {
    public static final DebugPropertyConfig CLIENT = new DebugPropertyConfig(false, false, false, false, false);
    public static final DebugPropertyConfig RENDERER = new DebugPropertyConfig(false, false, false, true, true);
    public static final DebugPropertyConfig SINGLEPLAYER = new DebugPropertyConfig(false, false, true, false, false);
    public static final DebugPropertyConfig SERVER_GLOBAL = new DebugPropertyConfig(false, true, false, false, false);
    public static final DebugPropertyConfig SERVER = new DebugPropertyConfig(true, false, false, false, false);
}
