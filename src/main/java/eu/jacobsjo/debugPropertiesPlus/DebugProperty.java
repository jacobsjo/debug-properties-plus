package eu.jacobsjo.debugPropertiesPlus;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.SharedConstants;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record DebugProperty<T>(
        String name,
        DebugPropertyConfig config,
        Supplier<T> getter,
        Consumer<T> setter
) {
    public static final List<DebugProperty<?>> PROPERTIES = Arrays.asList(
        new DebugProperty<>("OPEN_INCOMPATIBLE_WORLDS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_OPEN_INCOMPATIBLE_WORLDS,v -> SharedConstants.DEBUG_OPEN_INCOMPATIBLE_WORLDS = v),
        new DebugProperty<>("ALLOW_LOW_SIM_DISTANCE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE,v -> SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE = v),
        new DebugProperty<>("HOTKEYS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_HOTKEYS,v -> SharedConstants.DEBUG_HOTKEYS = v),
        new DebugProperty<>("UI_NARRATION", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_UI_NARRATION,v -> SharedConstants.DEBUG_UI_NARRATION = v),
        new DebugProperty<>("SHUFFLE_UI_RENDERING_ORDER", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER,v -> SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER = v),
        new DebugProperty<>("SHUFFLE_MODELS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHUFFLE_MODELS,v -> SharedConstants.DEBUG_SHUFFLE_MODELS = v),
        new DebugProperty<>("RENDER_UI_LAYERING_RECTANGLES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES,v -> SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES = v),
        new DebugProperty<>("PATHFINDING", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_PATHFINDING,v -> SharedConstants.DEBUG_PATHFINDING = v),
        new DebugProperty<>("SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES", DebugPropertyConfig.SINGLEPLAYER, () -> SharedConstants.DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES,v -> SharedConstants.DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES = v),
        new DebugProperty<>("SHAPES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SHAPES,v -> SharedConstants.DEBUG_SHAPES = v),
        new DebugProperty<>("NEIGHBORSUPDATE", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_NEIGHBORSUPDATE,v -> SharedConstants.DEBUG_NEIGHBORSUPDATE = v),
        new DebugProperty<>("EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER,v -> SharedConstants.DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER = v),
        new DebugProperty<>("STRUCTURES", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_STRUCTURES,v -> SharedConstants.DEBUG_STRUCTURES = v),
        new DebugProperty<>("GAME_EVENT_LISTENERS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_GAME_EVENT_LISTENERS,v -> SharedConstants.DEBUG_GAME_EVENT_LISTENERS = v),
        new DebugProperty<>("DUMP_TEXTURE_ATLAS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS,v -> SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS = v),
        new DebugProperty<>("DUMP_INTERPOLATED_TEXTURE_FRAMES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DUMP_INTERPOLATED_TEXTURE_FRAMES,v -> SharedConstants.DEBUG_DUMP_INTERPOLATED_TEXTURE_FRAMES = v),
        new DebugProperty<>("STRUCTURE_EDIT_MODE", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_STRUCTURE_EDIT_MODE,v -> SharedConstants.DEBUG_STRUCTURE_EDIT_MODE = v),
        new DebugProperty<>("SAVE_STRUCTURES_AS_SNBT", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_SAVE_STRUCTURES_AS_SNBT,v -> SharedConstants.DEBUG_SAVE_STRUCTURES_AS_SNBT = v),
        new DebugProperty<>("SYNCHRONOUS_GL_LOGS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS,v -> SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS = v),
        new DebugProperty<>("VERBOSE_SERVER_EVENTS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS,v -> SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS = v),
        new DebugProperty<>("NAMED_RUNNABLES", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_NAMED_RUNNABLES,v -> SharedConstants.DEBUG_NAMED_RUNNABLES = v),
        new DebugProperty<>("GOAL_SELECTOR", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_GOAL_SELECTOR,v -> SharedConstants.DEBUG_GOAL_SELECTOR = v),
        new DebugProperty<>("VILLAGE_SECTIONS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_VILLAGE_SECTIONS,v -> SharedConstants.DEBUG_VILLAGE_SECTIONS = v),
        new DebugProperty<>("BRAIN", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BRAIN,v -> SharedConstants.DEBUG_BRAIN = v),
        new DebugProperty<>("POI", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_POI,v -> SharedConstants.DEBUG_POI = v),
        new DebugProperty<>("BEES", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BEES,v -> SharedConstants.DEBUG_BEES = v),
        new DebugProperty<>("RAIDS", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_RAIDS,v -> SharedConstants.DEBUG_RAIDS = v),
        new DebugProperty<>("BLOCK_BREAK", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_BLOCK_BREAK,v -> SharedConstants.DEBUG_BLOCK_BREAK = v),
        new DebugProperty<>("MONITOR_TICK_TIMES", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_MONITOR_TICK_TIMES,v -> SharedConstants.DEBUG_MONITOR_TICK_TIMES = v),
        new DebugProperty<>("KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN,v -> SharedConstants.DEBUG_KEEP_JIGSAW_BLOCKS_DURING_STRUCTURE_GEN = v),
        new DebugProperty<>("DONT_SAVE_WORLD", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DONT_SAVE_WORLD,v -> SharedConstants.DEBUG_DONT_SAVE_WORLD = v),
        new DebugProperty<>("LARGE_DRIPSTONE", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_LARGE_DRIPSTONE,v -> SharedConstants.DEBUG_LARGE_DRIPSTONE = v),
        new DebugProperty<>("CARVERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_CARVERS,v -> SharedConstants.DEBUG_CARVERS = v),
        new DebugProperty<>("ORE_VEINS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_ORE_VEINS,v -> SharedConstants.DEBUG_ORE_VEINS = v),
        new DebugProperty<>("SCULK_CATALYST", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_SCULK_CATALYST,v -> SharedConstants.DEBUG_SCULK_CATALYST = v),
        new DebugProperty<>("BYPASS_REALMS_VERSION_CHECK", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_BYPASS_REALMS_VERSION_CHECK,v -> SharedConstants.DEBUG_BYPASS_REALMS_VERSION_CHECK = v),
        new DebugProperty<>("SOCIAL_INTERACTIONS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SOCIAL_INTERACTIONS,v -> SharedConstants.DEBUG_SOCIAL_INTERACTIONS = v),
        new DebugProperty<>("VALIDATE_RESOURCE_PATH_CASE", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE,v -> SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE = v),
        new DebugProperty<>("UNLOCK_ALL_TRADES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_UNLOCK_ALL_TRADES,v -> SharedConstants.DEBUG_UNLOCK_ALL_TRADES = v),
        new DebugProperty<>("BREEZE_MOB", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_BREEZE_MOB,v -> SharedConstants.DEBUG_BREEZE_MOB = v),
        new DebugProperty<>("TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS,v -> SharedConstants.DEBUG_TRIAL_SPAWNER_DETECTS_SHEEP_AS_PLAYERS = v),
        new DebugProperty<>("VAULT_DETECTS_SHEEP_AS_PLAYERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_VAULT_DETECTS_SHEEP_AS_PLAYERS,v -> SharedConstants.DEBUG_VAULT_DETECTS_SHEEP_AS_PLAYERS = v),
        new DebugProperty<>("FORCE_ONBOARDING_SCREEN", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN,v -> SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN = v),
        new DebugProperty<>("CURSOR_POS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_CURSOR_POS,v -> SharedConstants.DEBUG_CURSOR_POS = v),
        new DebugProperty<>("DEFAULT_SKIN_OVERRIDE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE,v -> SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE = v),
        new DebugProperty<>("PANORAMA_SCREENSHOT", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_PANORAMA_SCREENSHOT,v -> SharedConstants.DEBUG_PANORAMA_SCREENSHOT = v),
        new DebugProperty<>("CHASE_COMMAND", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_CHASE_COMMAND,v -> SharedConstants.DEBUG_CHASE_COMMAND = v),
        new DebugProperty<>("VERBOSE_COMMAND_ERRORS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_VERBOSE_COMMAND_ERRORS,v -> SharedConstants.DEBUG_VERBOSE_COMMAND_ERRORS = v),
        new DebugProperty<>("DEV_COMMANDS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_DEV_COMMANDS,v -> SharedConstants.DEBUG_DEV_COMMANDS = v),
        new DebugProperty<>("ACTIVE_TEXT_AREAS", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_ACTIVE_TEXT_AREAS,v -> SharedConstants.DEBUG_ACTIVE_TEXT_AREAS = v),
        new DebugProperty<>("IGNORE_LOCAL_MOB_CAP", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_IGNORE_LOCAL_MOB_CAP,v -> SharedConstants.DEBUG_IGNORE_LOCAL_MOB_CAP = v),
        new DebugProperty<>("DISABLE_LIQUID_SPREADING", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_LIQUID_SPREADING,v -> SharedConstants.DEBUG_DISABLE_LIQUID_SPREADING = v),
        new DebugProperty<>("AQUIFERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_AQUIFERS,v -> SharedConstants.DEBUG_AQUIFERS = v),
        new DebugProperty<>("JFR_PROFILING_ENABLE_LEVEL_LOADING", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_JFR_PROFILING_ENABLE_LEVEL_LOADING,v -> SharedConstants.DEBUG_JFR_PROFILING_ENABLE_LEVEL_LOADING = v),
        new DebugProperty<>("ENTITY_BLOCK_INTERSECTION", DebugPropertyConfig.CLIENT_OP, () -> SharedConstants.DEBUG_ENTITY_BLOCK_INTERSECTION,v -> SharedConstants.DEBUG_ENTITY_BLOCK_INTERSECTION = v),
        new DebugProperty<>("GENERATE_SQUARE_TERRAIN_WITHOUT_NOISE", DebugPropertyConfig.SERVER, () -> SharedConstants.debugGenerateSquareTerrainWithoutNoise,v -> SharedConstants.debugGenerateSquareTerrainWithoutNoise = v),
        new DebugProperty<>("ONLY_GENERATE_HALF_THE_WORLD", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_ONLY_GENERATE_HALF_THE_WORLD,v -> SharedConstants.DEBUG_ONLY_GENERATE_HALF_THE_WORLD = v),
        new DebugProperty<>("DISABLE_FLUID_GENERATION", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_FLUID_GENERATION,v -> SharedConstants.DEBUG_DISABLE_FLUID_GENERATION = v),
        new DebugProperty<>("DISABLE_AQUIFERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_AQUIFERS,v -> SharedConstants.DEBUG_DISABLE_AQUIFERS = v),
        new DebugProperty<>("DISABLE_SURFACE", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_SURFACE,v -> SharedConstants.DEBUG_DISABLE_SURFACE = v),
        new DebugProperty<>("DISABLE_CARVERS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_CARVERS,v -> SharedConstants.DEBUG_DISABLE_CARVERS = v),
        new DebugProperty<>("DISABLE_STRUCTURES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_STRUCTURES,v -> SharedConstants.DEBUG_DISABLE_STRUCTURES = v),
        new DebugProperty<>("DISABLE_FEATURES", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_FEATURES,v -> SharedConstants.DEBUG_DISABLE_FEATURES = v),
        new DebugProperty<>("DISABLE_ORE_VEINS", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_ORE_VEINS,v -> SharedConstants.DEBUG_DISABLE_ORE_VEINS = v),
        new DebugProperty<>("DISABLE_BLENDING", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_BLENDING,v -> SharedConstants.DEBUG_DISABLE_BLENDING = v),
        new DebugProperty<>("DISABLE_BELOW_ZERO_RETROGENERATION", DebugPropertyConfig.SERVER, () -> SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION,v -> SharedConstants.DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION = v),
        new DebugProperty<>("SUBTITLES", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_SUBTITLES,v -> SharedConstants.DEBUG_SUBTITLES = v),
        new DebugProperty<>("FAKE_LATENCY_MS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FAKE_LATENCY_MS,v -> SharedConstants.DEBUG_FAKE_LATENCY_MS = v),
        new DebugProperty<>("FAKE_JITTER_MS", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FAKE_JITTER_MS,v -> SharedConstants.DEBUG_FAKE_JITTER_MS = v),
        new DebugProperty<>("COMMAND_STACK_TRACES", DebugPropertyConfig.SERVER_GLOBAL, () -> CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES,v -> CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = v),
        new DebugProperty<>("WORLD_RECREATE", DebugPropertyConfig.CLIENT, () -> SharedConstants.DEBUG_WORLD_RECREATE,v -> SharedConstants.DEBUG_WORLD_RECREATE = v),
        new DebugProperty<>("SHOW_SERVER_DEBUG_VALUES", DebugPropertyConfig.SINGLEPLAYER, () -> SharedConstants.DEBUG_SHOW_SERVER_DEBUG_VALUES,v -> SharedConstants.DEBUG_SHOW_SERVER_DEBUG_VALUES = v),
        new DebugProperty<>("FEATURE_COUNT", DebugPropertyConfig.SERVER_GLOBAL, () -> SharedConstants.DEBUG_FEATURE_COUNT,v -> SharedConstants.DEBUG_FEATURE_COUNT = v)
    );

}
