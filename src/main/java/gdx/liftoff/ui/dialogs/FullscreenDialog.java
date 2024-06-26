package gdx.liftoff.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.ray3k.stripe.CollapsibleGroup;
import com.ray3k.stripe.CollapsibleGroup.CollapseType;
import com.ray3k.stripe.PopTable;
import com.ray3k.stripe.ScaleContainer;
import gdx.liftoff.Main;
import gdx.liftoff.ui.LogoWidget;
import gdx.liftoff.ui.panels.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static gdx.liftoff.Main.*;

/**
 * Dialog shown when in fullscreen layout mode. This includes all the panels at once. The layout scales up if the
 * available space is larger than 1920x1080.
 */
public class FullscreenDialog extends PopTable {
    public static FullscreenDialog fullscreenDialog;
    private TextButton generateButton;
    private Table versionTable;

    public FullscreenDialog() {
        super(skin.get("fullscreen", WindowStyle.class));
        fullscreenDialog = this;
        setFillParent(true);
        pad(SPACE_LARGE);

        populate();
    }

    public void populate() {
        clearChildren();

        //collapsible group that alternates between screen scaled scrollpane and a fit scaled container based on available space
        CollapsibleGroup dualCollapsibleGroup = new CollapsibleGroup(CollapseType.BOTH);
        add(dualCollapsibleGroup).grow();

        Table contentTable = new Table();
        ScaleContainer scaleContainer = new ScaleContainer(Scaling.fit, contentTable);
        scaleContainer.setPrefSize(1920, 1080);
        scaleContainer.setMinSize(1920, 1080);
        dualCollapsibleGroup.addActor(scaleContainer);
        createPanels(contentTable);

        contentTable = new Table();
        createPanels(contentTable);

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        dualCollapsibleGroup.addActor(scrollPane);
        addScrollFocusListener(scrollPane);
    }

    public void updateGenerateButton() {
        generateButton.setDisabled(!validateUserData());
    }

    private void createPanels(Table contentTable) {
        contentTable.defaults().space(SPACE_SMALL);
        Table table = new Table();
        contentTable.add(table).growX();
        table.setTransform(true);

        //empty cell to ensure logo is centered
        table.add().uniformX();

        //logo
        LogoWidget logoWidget = new LogoWidget(false);
        table.add(logoWidget).minHeight(Value.prefHeight).spaceBottom(0).expandX();

        //reset button
        Button button = new Button(skin, "reload");
        table.add(button).top().right().uniformX();
        addTooltip(button, Align.left, prop.getProperty("reset"));
        addHandListener(button);
        onChange(button, ConfirmResetUserData::showDialog);

        contentTable.row();
        table = new Table();
        contentTable.add(table).growX().spaceTop(0);

        //new project title
        table.defaults().space(SPACE_MEDIUM);
        Label label = new Label(prop.getProperty("options"), skin, "header");
        table.add(label);

        //project panel
        table.row();
        ProjectPanel projectPanel = new ProjectPanel(true);
        table.add(projectPanel).growX().maxWidth(400);
        projectPanel.captureKeyboardFocus();

        contentTable.row();
        table = new Table();
        contentTable.add(table).growY();

        //add-ons panel
        table.defaults().space(SPACE_HUGE).uniformX().growY().maxHeight(500);
        AddOnsPanel addOnsPanel = new AddOnsPanel(true);
        table.add(addOnsPanel);

        //third-party panel
        ThirdPartyPanel thirdPartyPanel = new ThirdPartyPanel(true);
        table.add(thirdPartyPanel);

        Table subTable = new Table();
        subTable.top();
        table.add(subTable);

        //settings panel
        subTable.defaults().space(SPACE_LARGE);
        SettingsPanel settingsPanel = new SettingsPanel(true);
        subTable.add(settingsPanel);

        //paths panel
        subTable.row();
        PathsPanel pathsPanel = new PathsPanel(true);
        subTable.add(pathsPanel).minWidth(450);

        contentTable.row();
        table = new Table();
        contentTable.add(table).growX();

        //empty cell for equal spacing
        table.add().expandX().uniformX();

        //generate button
        generateButton = new TextButton(prop.getProperty("generate"), skin, "big");
        updateGenerateButton();
        table.add(generateButton);
        addHandListener(generateButton);
        addTooltip(generateButton, Align.top, prop.getProperty("generateTip"));
        onChange(generateButton, () -> hide(sequence(
            fadeOut(.3f),
            run(() -> {
                Main.generateProject();
                FullscreenCompleteDialog.show();
            })
        )));

        //version
        versionTable = new Table();
        table.add(versionTable).expandX().right().bottom().uniformX();
        updateVersion();
    }

    @Override
    public void hide(Action action) {
        super.hide(action);
        fullscreenDialog = null;
    }

    public void updateVersion() {
        versionTable.clearChildren();

        Label label = new Label("v" + prop.getProperty("liftoffVersion"), skin);
        versionTable.add(label);

        if (latestStableVersion != null && !prop.getProperty("liftoffVersion").equals(latestStableVersion)) {
            versionTable.row();
            TextButton updateButton = new TextButton(prop.getProperty("updateAvailable"), skin, "link");
            versionTable.add(updateButton);
            addHandListener(updateButton);
            addTooltip(updateButton, Align.top, prop.getProperty("updateTip"));
            onChange(updateButton, () -> Gdx.net.openURI(prop.getProperty("updateUrl")));
        }
    }

    public static void show() {
        FullscreenDialog fullscreenDialog = new FullscreenDialog();
        fullscreenDialog.show(stage);
    }
}
