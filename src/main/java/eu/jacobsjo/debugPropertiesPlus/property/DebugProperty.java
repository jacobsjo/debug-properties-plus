package eu.jacobsjo.debugPropertiesPlus.property;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import io.netty.buffer.ByteBuf;
import net.minecraft.SharedConstants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DebugProperty<T> implements Comparable<DebugProperty<?>> {

    public final Class<T> type;
    public final Codec<T> valueCodec;
    public final StreamCodec<ByteBuf, T> valueStreamCodec;
    public final ArgumentType<T> argument;
    public final String name;
    public final DebugPropertyConfig config;
    private final Consumer<T> setter;
    public final T defaultValue;

    private DebugProperty(
        Class<T> type,
        Codec<T> valueCodec,
        StreamCodec<ByteBuf, T> valueStreamCodec,
        ArgumentType<T> argument,
        String name,
        DebugPropertyConfig config,
        Consumer<T> setter,
        T defaultValue
    ){
        this.type = type;
        this.valueCodec = valueCodec;
        this.valueStreamCodec = valueStreamCodec;
        this.argument = argument;
        this.name = name;
        this.config = config;
        this.setter = setter;
        this.defaultValue = defaultValue;
    }

    public void set(Object value){
        if (!this.type.isInstance(value)){
            throw new IllegalArgumentException("Trying to set value of wrong type");
        }
        DebugPropertiesPlus.LOGGER.info("Set debug property {} to {}", this.name, value);
        //noinspection unchecked
        this.setter.accept((T) value);
    }

    @Override
    public int compareTo(DebugProperty<?> other){
        if (this.config.perWorld() && !other.config.perWorld()) return 1;
        if (!this.config.perWorld() && other.config.perWorld()) return -1;
        if (this.config.onDedicatedServer() && !other.config.onDedicatedServer()) return 1;
        if (!this.config.onDedicatedServer() && other.config.onDedicatedServer()) return -1;

        return this.name.compareTo(other.name);
    }

    public static final Map<String, DebugProperty<?>> PROPERTIES = new HashMap<>();


    public static final Codec<DebugProperty<?>> BY_NAME_CODEC = Codec.STRING.xmap(PROPERTIES::get, DebugProperty::name);
    public static final StreamCodec<ByteBuf, DebugProperty<?>> BY_NAME_STREAM_CODEC = ByteBufCodecs.stringUtf8(64).map(PROPERTIES::get, DebugProperty::name);

    private String name() {
        return this.name;
    }

    private static <T> void create(
            Class<T> type,
            Codec<T> valueCodec,
            StreamCodec<ByteBuf, T> valueStreamCodec,
            ArgumentType<T> argument,
            String name,
            DebugPropertyConfig config,
            Consumer<T> setter,
            T defaultValue
    ){
        PROPERTIES.put(name, new DebugProperty<>(type, valueCodec, valueStreamCodec, argument, name, config, setter, defaultValue));
    }

    private static void createBoolean(
            String name,
            DebugPropertyConfig config,
            Consumer<Boolean> setter
    ){
        String propertyString = System.getProperty("MC_DEBUG_" + name);
        boolean defaulValue = propertyString != null && (propertyString.isEmpty() || Boolean.parseBoolean(propertyString));

        create(Boolean.class, Codec.BOOL, ByteBufCodecs.BOOL, BoolArgumentType.bool(), name, config, setter, defaulValue);
    }

    @SuppressWarnings("SameParameterValue")
    private static void createInteger(
            String name,
            DebugPropertyConfig config,
            Consumer<Integer> setter
    ){
        String propertyString = System.getProperty("MC_DEBUG_" + name);
        int defaulValue = propertyString == null ? 0 : Integer.parseInt(propertyString);

        create(Integer.class, Codec.INT, ByteBufCodecs.INT, IntegerArgumentType.integer(), name, config, setter, defaulValue);
    }

    private static final DebugPropertyConfig CLIENT = new DebugPropertyConfig.Builder().build();
    private static final DebugPropertyConfig SINGLEPLAYER = new DebugPropertyConfig.Builder().notOnMultipleyer().build();
    private static final DebugPropertyConfig RENDERER = new DebugPropertyConfig.Builder().updateDebugRenderer().requiresOp().build();
    private static final DebugPropertyConfig SERVER = new DebugPropertyConfig.Builder().onDedicatedServer().build();
    private static final DebugPropertyConfig PER_WORLD = new DebugPropertyConfig.Builder().perWorld().onDedicatedServer().build();
    private static final DebugPropertyConfig SERVER_REQUIRES_RELOAD = new DebugPropertyConfig.Builder().onDedicatedServer().requiresReload().build();

    public static void bootstrap(){
        createBoolean("OPEN_INCOMPATIBLE_WORLDS", CLIENT,  v -> SharedConstants.DEBUG_OPEN_INCOMPATIBLE_WORLDS = v);
        createBoolean("ALLOW_LOW_SIM_DISTANCE", CLIENT, v -> SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE = v);
        createBoolean("HOTKEYS", CLIENT, v -> SharedConstants.DEBUG_HOTKEYS = v);
        createBoolean("UI_NARRATION", CLIENT, v -> SharedConstants.DEBUG_UI_NARRATION = v);
        createBoolean("SHUFFLE_UI_RENDERING_ORDER", CLIENT, v -> SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER = v);
        createBoolean("SHUFFLE_MODELS", CLIENT, v -> SharedConstants.DEBUG_SHUFFLE_MODELS = v);
        createBoolean("RENDER_UI_LAYERING_RECTANGLES", CLIENT, v -> SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES = v);
        createBoolean("PATHFINDING", RENDERER,  v -> SharedConstants.DEBUG_PATHFINDING = v);
        createBoolean("SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES", SINGLEPLAYER,  v -> SharedConstants.DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES = v);
        createBoolean("SHAPES", CLIENT, v -> SharedConstants.DEBUG_SHAPES = v);
        createBoolean("NEIGHBORSUPDATE", RENDERER,  v -> SharedConstants.DEBUG_NEIGHBORSUPDATE = v);
        createBoolean("EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER", RENDERER,  v -> SharedConstants.DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER = v);
        createBoolean("STRUCTURES", RENDERER,  v -> SharedConstants.DEBUG_STRUCTURES = v);
        createBoolean("GAME_EVENT_LISTENERS", RENDERER,  v -> SharedConstants.DEBUG_GAME_EVENT_LISTENERS = v);
        createBoolean("DUMP_TEXTURE_ATLAS", CLIENT, v -> SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS = v);
        createBoolean("DUMP_INTERPOLATED_TEXTURE_FRAMES", CLIENT, v -> SharedConstants.DEBUG_DUMP_INTERPOLATED_TEXTURE_FRAMES = v);
        createBoolean("STRUCTURE_EDIT_MODE", SERVER,  v -> SharedConstants.DEBUG_STRUCTURE_EDIT_MODE = v);
        createBoolean("SAVE_STRUCTURES_AS_SNBT", SERVER,  v -> SharedConstants.DEBUG_SAVE_STRUCTURES_AS_SNBT = v);
        createBoolean("SYNCHRONOUS_GL_LOGS", CLIENT, v -> SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS = v);
        createBoolean("VERBOSE_SERVER_EVENTS", SERVER,  v -> SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS = v);
        createBoolean("NAMED_RUNNABLES", SERVER,  v -> SharedConstants.DEBUG_NAMED_RUNNABLES = v);
        createBoolean("GOAL_SELECTOR", RENDERER,  v -> SharedConstants.DEBUG_GOAL_SELECTOR = v);
        createBoolean("VILLAGE_SECTIONS", RENDERER,  v -> SharedConstants.DEBUG_VILLAGE_SECTIONS = v);
        createBoolean("BRAIN", RENDERER,  v -> SharedConstants.DEBUG_BRAIN = v);
        createBoolean("POI", RENDERER,  v -> SharedConstants.DEBUG_POI = v);
        createBoolean("BEES", RENDERER,  v -> SharedConstants.DEBUG_BEES = v);
        createBoolean("RAIDS", RENDERER,  v -> SharedConstants.DEBUG_RAIDS = v);
        createBoolean("BLOCK_BREAK", SERVER,  v -> SharedConstants.DEBUG_BLOCK_BREAK = v);
        createBoolean("MONITOR_TICK_TIMES", SERVER,  v -> SharedConstants.DEBUG_MONITOR_TICK_TIMES = v);
        createBoolean("KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN", PER_WORLD,  v -> SharedConstants.DEBUG_KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN = v);
        createBoolean("DONT_SAVE_WORLD", PER_WORLD,  v -> SharedConstants.DEBUG_DONT_SAVE_WORLD = v);
        createBoolean("LARGE_DRIPSTONE", PER_WORLD,  v -> SharedConstants.DEBUG_LARGE_DRIPSTONE = v);
        createBoolean("CARVERS", PER_WORLD,  v -> SharedConstants.DEBUG_CARVERS = v);
        createBoolean("ORE_VEINS", PER_WORLD,  v -> SharedConstants.DEBUG_ORE_VEINS = v);
        createBoolean("SCULK_CATALYST", PER_WORLD,  v -> SharedConstants.DEBUG_SCULK_CATALYST = v);
        createBoolean("BYPASS_REALMS_VERSION_CHECK", CLIENT, v -> SharedConstants.DEBUG_BYPASS_REALMS_VERSION_CHECK = v);
        createBoolean("SOCIAL_INTERACTIONS", CLIENT, v -> SharedConstants.DEBUG_SOCIAL_INTERACTIONS = v);
        createBoolean("VALIDATE_RESOURCE_PATH_CASE", SERVER,  v -> SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE = v);
        createBoolean("UNLOCK_ALL_TRADES", PER_WORLD,  v -> SharedConstants.DEBUG_UNLOCK_ALL_TRADES = v);
        createBoolean("BREEZE_MOB", RENDERER,  v -> SharedConstants.DEBUG_BREEZE_MOB = v);
        createBoolean("TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS", PER_WORLD,  v -> SharedConstants.DEBUG_TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS = v);
        createBoolean("VAULT_DETECTS_SHEEP_AS_PLAYERS", PER_WORLD,  v -> SharedConstants.DEBUG_VAULT_DETECTS_SHEEP_AS_PLAYERS = v);
        createBoolean("FORCE_ONBOARDING_SCREEN", CLIENT, v -> SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN = v);
        createBoolean("CURSOR_POS", CLIENT, v -> SharedConstants.DEBUG_CURSOR_POS = v);
        createBoolean("DEFAULT_SKIN_OVERRIDE", CLIENT, v -> SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE = v);
        createBoolean("PANORAMA_SCREENSHOT", CLIENT, v -> SharedConstants.DEBUG_PANORAMA_SCREENSHOT = v);
        createBoolean("CHASE_COMMAND", SERVER_REQUIRES_RELOAD,  v -> SharedConstants.DEBUG_CHASE_COMMAND = v);
        createBoolean("VERBOSE_COMMAND_ERRORS", SERVER,  v -> SharedConstants.DEBUG_VERBOSE_COMMAND_ERRORS = v);
        createBoolean("DEV_COMMANDS", SERVER_REQUIRES_RELOAD,  v -> SharedConstants.DEBUG_DEV_COMMANDS = v);
        createBoolean("ACTIVE_TEXT_AREAS", CLIENT, v -> SharedConstants.DEBUG_ACTIVE_TEXT_AREAS = v);
        createBoolean("IGNORE_LOCAL_MOB_CAP", PER_WORLD,  v -> SharedConstants.DEBUG_IGNORE_LOCAL_MOB_CAP = v);
        createBoolean("DISABLE_LIQUID_SPREADING", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_LIQUID_SPREADING = v);
        createBoolean("AQUIFERS", PER_WORLD,  v -> SharedConstants.DEBUG_AQUIFERS = v);
        createBoolean("JFR_PROFILING_ENABLE_LEVEL_LOADING", SERVER,  v -> SharedConstants.DEBUG_JFR_PROFILING_ENABLE_LEVEL_LOADING = v);
        createBoolean("ENTITY_BLOCK_INTERSECTION", RENDERER,  v -> SharedConstants.DEBUG_ENTITY_BLOCK_INTERSECTION = v);
        createBoolean("GENERATE_SQUARE_TERRAIN_WITHOUT_NOISE", PER_WORLD,  v -> SharedConstants.debugGenerateSquareTerrainWithoutNoise = v);
        createBoolean("ONLY_GENERATE_HALF_THE_WORLD", PER_WORLD,  v -> SharedConstants.DEBUG_ONLY_GENERATE_HALF_THE_WORLD = v);
        createBoolean("DISABLE_FLUID_GENERATION", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_FLUID_GENERATION = v);
        createBoolean("DISABLE_AQUIFERS", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_AQUIFERS = v);
        createBoolean("DISABLE_SURFACE", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_SURFACE = v);
        createBoolean("DISABLE_CARVERS", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_CARVERS = v);
        createBoolean("DISABLE_STRUCTURES", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_STRUCTURES = v);
        createBoolean("DISABLE_FEATURES", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_FEATURES = v);
        createBoolean("DISABLE_ORE_VEINS", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_ORE_VEINS = v);
        createBoolean("DISABLE_BLENDING", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_BLENDING = v);
        createBoolean("DISABLE_BELOW_ZERO_RETROGENERATION", PER_WORLD,  v -> SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION = v);
        createBoolean("SUBTITLES", CLIENT, v -> SharedConstants.DEBUG_SUBTITLES = v);
        createInteger("FAKE_LATENCY_MS", SERVER,  v -> SharedConstants.DEBUG_FAKE_LATENCY_MS = v);
        createInteger("FAKE_JITTER_MS", SERVER,  v -> SharedConstants.DEBUG_FAKE_JITTER_MS = v);
        createBoolean("COMMAND_STACK_TRACES", SERVER,  v -> CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = v);
        createBoolean("WORLD_RECREATE", CLIENT, v -> SharedConstants.DEBUG_WORLD_RECREATE = v);
        createBoolean("SHOW_SERVER_DEBUG_VALUES", SINGLEPLAYER,  v -> SharedConstants.DEBUG_SHOW_SERVER_DEBUG_VALUES = v);
        createBoolean("FEATURE_COUNT", SERVER,  v -> SharedConstants.DEBUG_FEATURE_COUNT = v);
    }
}
