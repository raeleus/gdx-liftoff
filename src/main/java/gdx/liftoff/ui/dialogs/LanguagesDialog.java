package gdx.liftoff.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.Align;
import com.ray3k.stripe.PopTable;

import static gdx.liftoff.Main.*;
import static gdx.liftoff.ui.data.Data.*;

public class LanguagesDialog extends PopTable  {
    public LanguagesDialog() {
        setStyle(skin.get("dialog", WindowStyle.class));
        setKeepCenteredInWindow(true);
        setHideOnUnfocus(true);
        pad(20).padTop(30).padBottom(30);

        Label label = new Label(prop.getProperty("languages"), skin, "header");
        add(label);

        row();
        Table scrollTable = new Table();
        scrollTable.pad(5);
        ScrollPane scrollPane = new ScrollPane(scrollTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFlickScroll(false);
        add(scrollPane).grow().spaceTop(20);
        addScrollFocusListener(scrollPane);
        stage.setScrollFocus(scrollPane);

        scrollTable.defaults().left();
        Table table = new Table();
        table.left();
        scrollTable.add(table).spaceTop(10).growX();

        table.defaults().space(10);
        label = new Label(prop.getProperty("language"), skin, "field");
        table.add(label);

        label = new Label(prop.getProperty("languageVersion"), skin, "field");
        table.add(label);

        table.columnDefaults(0).left();
        table.columnDefaults(1).width(175);

        //groovy
        table.row();
        CheckBox checkBox = new CheckBox(prop.getProperty("groovy"), skin);
        table.add(checkBox);
        addHandListener(checkBox);

        TextField textField = new TextField(groovyDefaultVersion, skin);
        textField.setAlignment(Align.center);
        table.add(textField);
        addIbeamListener(textField);

        Button button = new Button(skin, "external-link");
        table.add(button);
        addHandListener(button);
        onChange(button, () -> Gdx.net.openURI(prop.getProperty("groovyUrl")));

        //kotlin
        table.row();
        checkBox = new CheckBox(prop.getProperty("kotlin"), skin);
        table.add(checkBox);
        addHandListener(checkBox);

        textField = new TextField(kotlinDefaultVersion, skin);
        textField.setAlignment(Align.center);
        table.add(textField);
        addIbeamListener(textField);

        button = new Button(skin, "external-link");
        table.add(button);
        addHandListener(button);
        onChange(button, () -> Gdx.net.openURI(prop.getProperty("kotlinUrl")));

        //scala
        table.row();
        checkBox = new CheckBox(prop.getProperty("scala"), skin);
        table.add(checkBox);
        addHandListener(checkBox);

        textField = new TextField(scalaDefaultVersion, skin);
        textField.setAlignment(Align.center);
        table.add(textField);
        addIbeamListener(textField);

        button = new Button(skin, "external-link");
        table.add(button);
        addHandListener(button);
        onChange(button, () -> Gdx.net.openURI(prop.getProperty("scalaUrl")));

        scrollTable.row();
        label = new Label(prop.getProperty("languagesPrompt"), skin, "description");
        label.setWrap(true);
        label.setAlignment(Align.left);
        scrollTable.add(label).spaceTop(30).growX();

        //links
        scrollTable.row();
        table = new Table();
        scrollTable.add(table).spaceTop(20).growX();

        table.defaults().space(5);
        label = new Label(prop.getProperty("links"), skin, "field");
        table.add(label).left();

        //clojure
        table.defaults().left().padLeft(10);
        table.row();
        TextButton textButton = new TextButton(prop.getProperty("clojureLink"), skin, "link");
        textButton.getLabel().setAlignment(Align.left);
        table.add(textButton);
        addHandListener(textButton);
        onChange(textButton, () -> Gdx.net.openURI(prop.getProperty("clojureUrl")));

        //other jvm's
        table.row();
        textButton = new TextButton(prop.getProperty("otherLanguagesPrompt"), skin, "link");
        table.add(textButton);
        addHandListener(textButton);
        onChange(textButton, () -> Gdx.net.openURI(prop.getProperty("otherJvmUrl")));

        row();
        textButton = new TextButton("OK", skin);
        add(textButton).prefWidth(140).spaceTop(20);
        addHandListener(textButton);
        onChange(textButton, () -> {
            hide();
        });
    }

    public static void show() {
        LanguagesDialog dialog = new LanguagesDialog();
        dialog.show(stage);
    }
}