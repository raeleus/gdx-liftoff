package gdx.liftoff.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ray3k.stripe.PopTable;

import static gdx.liftoff.Main.*;

/**
 * The dialog shown when the user clicks the template button in the add-ons panel
 */
public class TemplatesDialog extends PopTable  {
    private static GlyphLayout layout = new GlyphLayout();

    public TemplatesDialog() {
        setStyle(skin.get("dialog", WindowStyle.class));
        setKeepCenteredInWindow(true);
        setHideOnUnfocus(true);
        pad(SPACE_LARGE).padTop(SPACE_HUGE).padBottom(SPACE_HUGE);

        //title
        Label label = new Label(prop.getProperty("templates"), skin, "header");
        add(label);

        //scrollable area includes basic templates, third-party templates, and links
        row();
        Table scrollTable = new Table();
        scrollTable.pad(SPACE_SMALL);
        ScrollPane scrollPane = new ScrollPane(scrollTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFlickScroll(false);
        add(scrollPane).grow().spaceTop(SPACE_LARGE);
        addScrollFocusListener(scrollPane);
        stage.setScrollFocus(scrollPane);

        scrollTable.defaults().left();
        Table table = new Table();
        table.left();
        scrollTable.add(table).spaceTop(SPACE_MEDIUM).growX();

        table.defaults().left().space(SPACE_SMALL);
        ButtonGroup buttonGroup = new ButtonGroup();

        //basic templates title
        table.row();
        label = new Label(prop.getProperty("officialTemplates"), skin, "field");
        label.setTouchable(Touchable.enabled);
        table.add(label).minWidth(0).spaceBottom(SPACE_MEDIUM).colspan(2).growX();
        addTooltip(label, Align.top, prop.getProperty("officialTemplatesTip"));

        //basic templates
        addTemplate(table, buttonGroup, prop.getProperty("classic"), prop.getProperty("classicTip"));
        addTemplate(table, buttonGroup, prop.getProperty("applicationAdapter"), prop.getProperty("applicationAdapterTip"));
        addTemplate(table, buttonGroup, prop.getProperty("applicationListener"), prop.getProperty("applicationListenerTip"));
        addTemplate(table, buttonGroup, prop.getProperty("emptyTemplate"), prop.getProperty("emptyTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("gameTemplate"), prop.getProperty("gameTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("inputProcessor"), prop.getProperty("inputProcessorTip"));
        addTemplate(table, buttonGroup, prop.getProperty("kotlinClassicTemplate"), prop.getProperty("kotlinClassicTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("kotlinTemplate"), prop.getProperty("kotlinTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("scene2dTemplate"), prop.getProperty("scene2dTemplateTip"), true);
        addTemplate(table, buttonGroup, prop.getProperty("superKoalio"), prop.getProperty("superKoalioTip"));

        //third-party templates title
        table.row();
        label = new Label(prop.getProperty("thirdPartyTemplates"), skin, "field");
        label.setTouchable(Touchable.enabled);
        label.setEllipsis("...");
        table.add(label).minWidth(0).spaceTop(SPACE_LARGE).spaceBottom(SPACE_MEDIUM).colspan(2).growX();
        addTooltip(label, Align.top, prop.getProperty("officialTemplatesTip"));

        //third-party templates
        addTemplate(table, buttonGroup, prop.getProperty("ktxTemplate"), prop.getProperty("ktxTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("lmlKiwiInputTemplate"), prop.getProperty("lmlKiwiInputTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("lmlKiwiTemplate"), prop.getProperty("lmlKiwiTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("lmlMvcBasicTemplate"), prop.getProperty("lmlMvcBasicTemplateTip"), true);
        addTemplate(table, buttonGroup, prop.getProperty("lmlMvcBox2dTemplate"), prop.getProperty("lmlMvcBox2dTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("lmlMvcVisTemplate"), prop.getProperty("lmlMvcVisTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("lmlTemplate"), prop.getProperty("lmlTemplateTip"), true);
        addTemplate(table, buttonGroup, prop.getProperty("noise4jTemplate"), prop.getProperty("noise4jTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("visUiBasicTemplate"), prop.getProperty("visUiBasicTemplateTip"));
        addTemplate(table, buttonGroup, prop.getProperty("visUiShowcaseTemplate"), prop.getProperty("visUiShowcaseTemplateTip"));

        //links
        scrollTable.row();
        table = new Table();
        scrollTable.add(table).spaceTop(SPACE_HUGE).growX();

        table.defaults().space(SPACE_SMALL).expandX();
        label = new Label(prop.getProperty("links"), skin, "field");
        table.add(label).left();

        //propose a template
        table.defaults().left().padLeft(SPACE_MEDIUM);
        table.row();

        TextButton textButton = new TextButton(prop.getProperty("templatesLink"), skin, "link");
        textButton.getLabel().setAlignment(Align.left);
        table.add(textButton);
        addHandListener(textButton);
        onChange(textButton, () -> Gdx.net.openURI(prop.getProperty("issues")));

        //ok button
        row();
        textButton = new TextButton("OK", skin);
        add(textButton).prefWidth(140).spaceTop(SPACE_LARGE);
        addHandListener(textButton);
        onChange(textButton, () -> {
            hide();
        });
    }

    private void addTemplate(Table table, ButtonGroup buttonGroup, String labelText, String description) {
        addTemplate(table, buttonGroup, labelText, description, false);
    }

    /**
     * A convenience method to add a template to the table
     * @param table
     * @param buttonGroup
     * @param labelText
     * @param description
     * @param showGuiTip
     */
    private void addTemplate(Table table, ButtonGroup buttonGroup, String labelText, String description, boolean showGuiTip) {
        table.row();
        CheckBox checkBox = new CheckBox(labelText, skin, "radio");
        checkBox.left();
        table.add(checkBox).spaceRight(SPACE_MEDIUM).growX();
        buttonGroup.add(checkBox);
        addHandListener(checkBox);
        if (showGuiTip) addTooltip(checkBox, Align.top, prop.getProperty("templatesStar"));

        Label label = new Label(description, skin, "description");
        label.setEllipsis("...");
        label.setTouchable(Touchable.enabled);
        layout.setText(label.getStyle().font, description);
        table.add(label).prefWidth(layout.width).minWidth(0).growX();
        label.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    label.setColor(skin.getColor("red"));
                    checkBox.fire(event);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    label.setColor(Color.WHITE);
                    checkBox.fire(event);
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

        checkBox.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) label.setColor(skin.getColor("red"));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) label.setColor(skin.getColor("white"));
            }
        });
    }

    public static void show() {
        TemplatesDialog dialog = new TemplatesDialog();
        dialog.show(stage);
    }
}