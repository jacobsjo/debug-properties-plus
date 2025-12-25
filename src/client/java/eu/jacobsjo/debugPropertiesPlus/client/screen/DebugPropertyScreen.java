package eu.jacobsjo.debugPropertiesPlus.client.screen;

import com.google.common.collect.Lists;
import eu.jacobsjo.debugPropertiesPlus.client.property.storage.DebugPropertyClientStorage;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permissions;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class DebugPropertyScreen extends Screen {
    private static final Component TITLE = Component.translatable("debug-properties-plus.screen.title");
    private static final Component SUBTITLE = Component.translatable("debug-properties-plus.screen.warning").withColor(0xFFFF0000);
    private static final Component PER_WORLD_HEADER = Component.translatable("debug-properties-plus.screen.header.perWorld").withColor(0xFFFFF060);
    private static final Component PER_WORLD_DEFAULT_HEADER = Component.translatable("debug-properties-plus.screen.header.perWorld.default").withColor(0xFFFFF060);
    private static final Component GLOBAL_HEADER = Component.translatable("debug-properties-plus.screen.header.global").withColor(0xFFFFF060);
    private static final Component WARNING_REQUIRES_OP = Component.translatable("debug-properties-plus.screen.warning.requires-op").withColor(0xFFFFF060);
    private static final Component WARNING_SINGLEPLAYER = Component.translatable("debug-properties-plus.screen.warning.singleplayer").withColor(0xFFFFF060);
    private static final Identifier WARNING_SPRITE = Identifier.fromNamespaceAndPath("debug-properties-plus","warning");

    private static final Component SEARCH = Component.translatable("debug.options.search").withStyle(EditBox.SEARCH_HINT_STYLE);

    private static final int WIDTH = 350;

    final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);
    private DebugPropertyScreen.@Nullable PropertyList propertyList;
    private @Nullable EditBox searchBox;

    private final boolean worldCreation;
    @Nullable private final Runnable exitCallback;

    public DebugPropertyScreen(final boolean perWorld, @Nullable Runnable exitCallback) {
        super(TITLE);
        this.worldCreation = perWorld;
        this.exitCallback = exitCallback;
    }

    @Override
    protected void init() {
        LinearLayout header = this.layout.addToHeader(LinearLayout.vertical().spacing(8));

        // First line: Title and search bar
        LinearLayout titleAndSearch = LinearLayout.horizontal().spacing(8);
        titleAndSearch.addChild(new SpacerElement(WIDTH / 3, 1));
        titleAndSearch.addChild(new StringWidget(TITLE, this.font), titleAndSearch.newCellSettings().alignVerticallyMiddle());
        this.searchBox = new EditBox(this.font, 0, 0, WIDTH / 3, 20, this.searchBox, SEARCH);
        this.searchBox.setResponder(string -> {
            assert this.propertyList != null;
            this.propertyList.updateSearch(string);
        });
        this.searchBox.setHint(SEARCH);
        titleAndSearch.addChild(this.searchBox);
        header.addChild(titleAndSearch, LayoutSettings::alignHorizontallyCenter);

        // Second line: Warning message
        header.addChild(new MultiLineTextWidget(SUBTITLE, this.font).setMaxWidth(WIDTH).setCentered(true), LayoutSettings::alignHorizontallyCenter);

        // Body: options list
        this.propertyList = new PropertyList();
        this.layout.addToContents(this.propertyList);

        // Footer: Done button
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(140).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void setInitialFocus() {
        assert this.searchBox != null;
        this.setInitialFocus(this.searchBox);
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.propertyList != null) {
            this.propertyList.updateSize(this.width, this.layout);
        }
    }

    @Override
    public void onClose() {
        if (this.exitCallback != null){
            this.exitCallback.run();
        } else {
            this.minecraft.setScreen(null);
        }
    }

    private class PropertyList extends ContainerObjectSelectionList<AbstractPropertyEntry> {
        public PropertyList() {
            super(
                    Minecraft.getInstance(),
                    WIDTH,
                    DebugPropertyScreen.this.layout.getContentHeight(),
                    DebugPropertyScreen.this.layout.getHeaderHeight(),
                    20
            );
            this.updateSearch("");
        }

        @Override
        public int getRowWidth() {
            return WIDTH;
        }

        public void updateSearch(String string) {
            this.clearEntries();

            List<DebugProperty<?>> properties = DebugProperty.PROPERTIES.values().stream().sorted().toList();
            Boolean perWorld = null;
            for (DebugProperty<?> property : properties)
                if (DebugPropertyScreen.this.includeProperty(property) && property.name.contains(string.toUpperCase(Locale.ROOT))) {
                    if (perWorld == null || property.config.perWorld() != perWorld){
                        this.addEntry(new PropertyHeader(property.config.perWorld()
                                ? DebugPropertyScreen.this.minecraft.getConnection() != null ? PER_WORLD_HEADER : PER_WORLD_DEFAULT_HEADER
                                : GLOBAL_HEADER));
                        perWorld = property.config.perWorld();
                    }

                    if (property.type == Boolean.class) {
                        this.addEntry(new BooleanPropertyEntry((DebugProperty<Boolean>) property));
                    } else if (property.type == Integer.class) {
                        this.addEntry(new IntegerPropertyEntry((DebugProperty<Integer>) property));
                    }
                }

            this.notifyListUpdated();
        }

        private void notifyListUpdated() {
            this.refreshScrollAmount();
            DebugPropertyScreen.this.triggerImmediateNarration(true);
        }
    }

    private boolean includeProperty(DebugProperty<?> property){
        if (this.worldCreation){
            return property.config.perWorld();
        } else {
            return true;
        }
    }

    private abstract static class AbstractPropertyEntry extends ContainerObjectSelectionList.Entry<AbstractPropertyEntry> {}

    private class PropertyHeader extends AbstractPropertyEntry {

        private final Component text;
        public PropertyHeader(Component text){
            this.text = text;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            guiGraphics.drawString(DebugPropertyScreen.this.minecraft.font, text, this.getContentX(), this.getContentY() + 5, -1);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of();
        }
    }

    private abstract class PropertyEntry<T> extends AbstractPropertyEntry {
        final DebugProperty<T> property;

        protected final List<AbstractWidget> children = Lists.<AbstractWidget>newArrayList();
        private final LinearLayout nameLayout;

        public PropertyEntry(final DebugProperty<T> property) {
            this.property = property;

            nameLayout = LinearLayout.horizontal();
            nameLayout.addChild(new SpacerElement(0, 16));
            nameLayout.addChild(new StringWidget(Component.literal(this.property.name), DebugPropertyScreen.this.minecraft.font), nameLayout.newCellSettings().alignVerticallyMiddle() );

            Component warning = null;
            if (DebugPropertyScreen.this.minecraft.player != null) {
                if (property.config.requiresOp() && !DebugPropertyScreen.this.minecraft.player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
                    warning = WARNING_REQUIRES_OP;
                } else if (property.config.notOnMultiplayer() && !DebugPropertyScreen.this.minecraft.player.isLocalPlayer()){
                    warning = WARNING_SINGLEPLAYER;
                }
            }

            if (warning != null){
                nameLayout.addChild(ImageWidget.sprite(16, 16, WARNING_SPRITE));
                nameLayout.addChild(new StringWidget(warning, DebugPropertyScreen.this.minecraft.font), nameLayout.newCellSettings().alignVerticallyMiddle() );
            }
            nameLayout.visitWidgets(this.children::add);
        }


        @Override
        public List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.children;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            int x = this.getContentX();
            int y = this.getContentY();

            this.nameLayout.setX(x);
            this.nameLayout.setY(y);
            this.nameLayout.arrangeElements();
            this.nameLayout.visitWidgets(w->w.render(guiGraphics, i, j, f));
        }
    }

    private class BooleanPropertyEntry extends PropertyEntry<Boolean> {
        private final Checkbox checkbox;

        public BooleanPropertyEntry(DebugProperty<Boolean> propertyKey) {
            super(propertyKey);

            Component message = Component.literal("");

            this.checkbox = Checkbox.builder(message, DebugPropertyScreen.this.font)
                    .maxWidth(60)
                    .selected(DebugPropertyClientStorage.get(property))
                    .onValueChange((cb, v) -> DebugPropertyClientStorage.set(property, v))
                    .build();
            this.children.add(this.checkbox);
            this.refreshEntry();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            super.renderContent(guiGraphics, i, j, bl, f);

            this.checkbox.setX(this.getContentX() + this.getContentWidth() - this.checkbox.getWidth());
            this.checkbox.setY(this.getContentY());
            this.checkbox.render(guiGraphics, i, j, f);
        }

        public void refreshEntry() {
            boolean value = DebugPropertyClientStorage.get(this.property);
            if (this.checkbox.selected() != value){
                this.checkbox.onPress(null);
            }
        }
    }

    private class IntegerPropertyEntry extends PropertyEntry<Integer> {
        private final EditBox editbox;

        public IntegerPropertyEntry(DebugProperty<Integer> property) {
            super(property);

            this.editbox = new EditBox(DebugPropertyScreen.super.font, 40, 16, Component.literal(property.name));
            this.editbox.setValue(DebugPropertyClientStorage.get(property).toString());
            this.editbox.setResponder(v -> {
                try {
                    int value = Integer.parseInt(v);
                    this.editbox.setTextColor(-2039584);
                    DebugPropertyClientStorage.set(property, value);
                } catch (NumberFormatException e) {
                    this.editbox.setTextColor(-65536);
                }

            });
            this.children.add(this.editbox);
            this.refreshEntry();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            super.renderContent(guiGraphics, i, j, bl, f);

            this.editbox.setX(this.getContentX() + this.getContentWidth() - this.editbox.getWidth());
            this.editbox.setY(this.getContentY());
            this.editbox.render(guiGraphics, i, j, f);
        }

        public void refreshEntry() {
            this.editbox.setValue(DebugPropertyClientStorage.get(this.property).toString());
        }
    }
}
