package eu.jacobsjo.debugPropertiesPlus.property;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.SharedConstants;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record DebugProperty<T>(
        Class<T> type,
        String name,
        DebugPropertyConfig config,
        Supplier<T> getter,
        Consumer<T> setter
) {

    public static DebugProperty<Boolean> createBoolean(
            String name,
            DebugPropertyConfig config,
            Supplier<Boolean> getter,
            Consumer<Boolean> setter
    ){
        return new DebugProperty<>(Boolean.class, name, config, getter, setter);
    }

    public static DebugProperty<Integer> createInteger(
            String name,
            DebugPropertyConfig config,
            Supplier<Integer> getter,
            Consumer<Integer> setter
    ){
        return new DebugProperty<>(Integer.class, name, config, getter, setter);
    }

    public static final List<DebugProperty<?>> PROPERTIES = Arrays.<DebugProperty<?>>asList(
        createBoolean("OPEN_INCOMPATIBLE_WORLDS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_OPEN_INCOMPATIBLE_WORLDS,v -> SharedConstants.DEBUG_OPEN_INCOMPATIBLE_WORLDS = v),
        createBoolean("ALLOW_LOW_SIM_DISTANCE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE,v -> SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE = v),
        createBoolean("HOTKEYS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_HOTKEYS,v -> SharedConstants.DEBUG_HOTKEYS = v),
        createBoolean("UI_NARRATION", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_UI_NARRATION,v -> SharedConstants.DEBUG_UI_NARRATION = v),
        createBoolean("SHUFFLE_UI_RENDERING_ORDER", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER,v -> SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER = v),
        createBoolean("SHUFFLE_MODELS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHUFFLE_MODELS,v -> SharedConstants.DEBUG_SHUFFLE_MODELS = v),
        createBoolean("RENDER_UI_LAYERING_RECTANGLES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES,v -> SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES = v),
        createBoolean("PATHFINDING", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_PATHFINDING,v -> SharedConstants.DEBUG_PATHFINDING = v),
        createBoolean("SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES", DebugPropertyConfig.SINGLEPLAYER, () -> SharedConstants.DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES,v -> SharedConstants.DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES = v),
        createBoolean("SHAPES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHAPES,v -> SharedConstants.DEBUG_SHAPES = v),
        createBoolean("NEIGHBORSUPDATE", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_NEIGHBORSUPDATE,v -> SharedConstants.DEBUG_NEIGHBORSUPDATE = v),
        createBoolean("EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER,v -> SharedConstants.DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER = v),
        createBoolean("STRUCTURES", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_STRUCTURES,v -> SharedConstants.DEBUG_STRUCTURES = v),
        createBoolean("GAME_EVENT_LISTENERS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_GAME_EVENT_LISTENERS,v -> SharedConstants.DEBUG_GAME_EVENT_LISTENERS = v),
        createBoolean("DUMP_TEXTURE_ATLAS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS,v -> SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS = v),
        createBoolean("DUMP_INTERPOLATED_TEXTURE_FRAMES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DUMP_INTERPOLATED_TEXTURE_FRAMES,v -> SharedConstants.DEBUG_DUMP_INTERPOLATED_TEXTURE_FRAMES = v),
        createBoolean("STRUCTURE_EDIT_MODE", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_STRUCTURE_EDIT_MODE,v -> SharedConstants.DEBUG_STRUCTURE_EDIT_MODE = v),
        createBoolean("SAVE_STRUCTURES_AS_SNBT", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_SAVE_STRUCTURES_AS_SNBT,v -> SharedConstants.DEBUG_SAVE_STRUCTURES_AS_SNBT = v),
        createBoolean("SYNCHRONOUS_GL_LOGS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS,v -> SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS = v),
        createBoolean("VERBOSE_SERVER_EVENTS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS,v -> SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS = v),
        createBoolean("NAMED_RUNNABLES", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_NAMED_RUNNABLES,v -> SharedConstants.DEBUG_NAMED_RUNNABLES = v),
        createBoolean("GOAL_SELECTOR", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_GOAL_SELECTOR,v -> SharedConstants.DEBUG_GOAL_SELECTOR = v),
        createBoolean("VILLAGE_SECTIONS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_VILLAGE_SECTIONS,v -> SharedConstants.DEBUG_VILLAGE_SECTIONS = v),
        createBoolean("BRAIN", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BRAIN,v -> SharedConstants.DEBUG_BRAIN = v),
        createBoolean("POI", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_POI,v -> SharedConstants.DEBUG_POI = v),
        createBoolean("BEES", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BEES,v -> SharedConstants.DEBUG_BEES = v),
        createBoolean("RAIDS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_RAIDS,v -> SharedConstants.DEBUG_RAIDS = v),
        createBoolean("BLOCK_BREAK", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_BLOCK_BREAK,v -> SharedConstants.DEBUG_BLOCK_BREAK = v),
        createBoolean("MONITOR_TICK_TIMES", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_MONITOR_TICK_TIMES,v -> SharedConstants.DEBUG_MONITOR_TICK_TIMES = v),
        createBoolean("KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN,v -> SharedConstants.DEBUG_KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN = v),
        createBoolean("DONT_SAVE_WORLD", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DONT_SAVE_WORLD,v -> SharedConstants.DEBUG_DONT_SAVE_WORLD = v),
        createBoolean("LARGE_DRIPSTONE", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_LARGE_DRIPSTONE,v -> SharedConstants.DEBUG_LARGE_DRIPSTONE = v),
        createBoolean("CARVERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_CARVERS,v -> SharedConstants.DEBUG_CARVERS = v),
        createBoolean("ORE_VEINS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_ORE_VEINS,v -> SharedConstants.DEBUG_ORE_VEINS = v),
        createBoolean("SCULK_CATALYST", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_SCULK_CATALYST,v -> SharedConstants.DEBUG_SCULK_CATALYST = v),
        createBoolean("BYPASS_REALMS_VERSION_CHECK", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_BYPASS_REALMS_VERSION_CHECK,v -> SharedConstants.DEBUG_BYPASS_REALMS_VERSION_CHECK = v),
        createBoolean("SOCIAL_INTERACTIONS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SOCIAL_INTERACTIONS,v -> SharedConstants.DEBUG_SOCIAL_INTERACTIONS = v),
        createBoolean("VALIDATE_RESOURCE_PATH_CASE", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE,v -> SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE = v),
        createBoolean("UNLOCK_ALL_TRADES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_UNLOCK_ALL_TRADES,v -> SharedConstants.DEBUG_UNLOCK_ALL_TRADES = v),
        createBoolean("BREEZE_MOB", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BREEZE_MOB,v -> SharedConstants.DEBUG_BREEZE_MOB = v),
        createBoolean("TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS,v -> SharedConstants.DEBUG_TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS = v),
        createBoolean("VAULT_DETECTS_SHEEP_AS_PLAYERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_VAULT_DETECTS_SHEEP_AS_PLAYERS,v -> SharedConstants.DEBUG_VAULT_DETECTS_SHEEP_AS_PLAYERS = v),
        createBoolean("FORCE_ONBOARDING_SCREEN", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN,v -> SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN = v),
        createBoolean("CURSOR_POS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_CURSOR_POS,v -> SharedConstants.DEBUG_CURSOR_POS = v),
        createBoolean("DEFAULT_SKIN_OVERRIDE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE,v -> SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE = v),
        createBoolean("PANORAMA_SCREENSHOT", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_PANORAMA_SCREENSHOT,v -> SharedConstants.DEBUG_PANORAMA_SCREENSHOT = v),
        createBoolean("CHASE_COMMAND", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_CHASE_COMMAND,v -> SharedConstants.DEBUG_CHASE_COMMAND = v),
        createBoolean("VERBOSE_COMMAND_ERRORS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VERBOSE_COMMAND_ERRORS,v -> SharedConstants.DEBUG_VERBOSE_COMMAND_ERRORS = v),
        createBoolean("DEV_COMMANDS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_DEV_COMMANDS,v -> SharedConstants.DEBUG_DEV_COMMANDS = v),
        createBoolean("ACTIVE_TEXT_AREAS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_ACTIVE_TEXT_AREAS,v -> SharedConstants.DEBUG_ACTIVE_TEXT_AREAS = v),
        createBoolean("IGNORE_LOCAL_MOB_CAP", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_IGNORE_LOCAL_MOB_CAP,v -> SharedConstants.DEBUG_IGNORE_LOCAL_MOB_CAP = v),
        createBoolean("DISABLE_LIQUID_SPREADING", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_LIQUID_SPREADING,v -> SharedConstants.DEBUG_DISABLE_LIQUID_SPREADING = v),
        createBoolean("AQUIFERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_AQUIFERS,v -> SharedConstants.DEBUG_AQUIFERS = v),
        createBoolean("JFR_PROFILING_ENABLE_LEVEL_LOADING", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_JFR_PROFILING_ENABLE_LEVEL_LOADING,v -> SharedConstants.DEBUG_JFR_PROFILING_ENABLE_LEVEL_LOADING = v),
        createBoolean("ENTITY_BLOCK_INTERSECTION", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_ENTITY_BLOCK_INTERSECTION,v -> SharedConstants.DEBUG_ENTITY_BLOCK_INTERSECTION = v),
        createBoolean("GENERATE_SQUARE_TERRAIN_WITHOUT_NOISE", DebugPropertyConfig.SERVER, () -> SharedConstants.debugGenerateSquareTerrainWithoutNoise,v -> SharedConstants.debugGenerateSquareTerrainWithoutNoise = v),
        createBoolean("ONLY_GENERATE_HALF_THE_WORLD", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_ONLY_GENERATE_HALF_THE_WORLD,v -> SharedConstants.DEBUG_ONLY_GENERATE_HALF_THE_WORLD = v),
        createBoolean("DISABLE_FLUID_GENERATION", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_FLUID_GENERATION,v -> SharedConstants.DEBUG_DISABLE_FLUID_GENERATION = v),
        createBoolean("DISABLE_AQUIFERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_AQUIFERS,v -> SharedConstants.DEBUG_DISABLE_AQUIFERS = v),
        createBoolean("DISABLE_SURFACE", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_SURFACE,v -> SharedConstants.DEBUG_DISABLE_SURFACE = v),
        createBoolean("DISABLE_CARVERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_CARVERS,v -> SharedConstants.DEBUG_DISABLE_CARVERS = v),
        createBoolean("DISABLE_STRUCTURES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_STRUCTURES,v -> SharedConstants.DEBUG_DISABLE_STRUCTURES = v),
        createBoolean("DISABLE_FEATURES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_FEATURES,v -> SharedConstants.DEBUG_DISABLE_FEATURES = v),
        createBoolean("DISABLE_ORE_VEINS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_ORE_VEINS,v -> SharedConstants.DEBUG_DISABLE_ORE_VEINS = v),
        createBoolean("DISABLE_BLENDING", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_BLENDING,v -> SharedConstants.DEBUG_DISABLE_BLENDING = v),
        createBoolean("DISABLE_BELOW_ZERO_RETROGENERATION", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION,v -> SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION = v),
        createBoolean("SUBTITLES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SUBTITLES,v -> SharedConstants.DEBUG_SUBTITLES = v),
        createInteger("FAKE_LATENCY_MS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FAKE_LATENCY_MS,v -> SharedConstants.DEBUG_FAKE_LATENCY_MS = v),
        createInteger("FAKE_JITTER_MS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FAKE_JITTER_MS,v -> SharedConstants.DEBUG_FAKE_JITTER_MS = v),
        createBoolean("COMMAND_STACK_TRACES", DebugPropertyConfig.SERVER_GLOBAL, () -> CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES,v -> CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = v),
        createBoolean("WORLD_RECREATE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_WORLD_RECREATE,v -> SharedConstants.DEBUG_WORLD_RECREATE = v),
        createBoolean("SHOW_SERVER_DEBUG_VALUES", DebugPropertyConfig.SINGLEPLAYER, () -> SharedConstants.DEBUG_SHOW_SERVER_DEBUG_VALUES,v -> SharedConstants.DEBUG_SHOW_SERVER_DEBUG_VALUES = v),
        createBoolean("FEATURE_COUNT", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FEATURE_COUNT,v -> SharedConstants.DEBUG_FEATURE_COUNT = v)
    );

}
