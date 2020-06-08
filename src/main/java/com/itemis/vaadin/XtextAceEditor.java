package com.itemis.vaadin;

import com.vaadin.flow.shared.ui.LoadMode;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
// import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

@Tag("ace-widget")
// @NpmPackage(value = "requirejs", version = "2.1.22" /*"2.3.6"*/)
@NpmPackage(value = "ace-builds", version = "1.4.8")
@NpmPackage(value = "jquery", version = "3.5.1")
// @JavaScript("https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
// @JsModule("requirejs/require.js")
@JavaScript(value = "./xtext/xtext-ace.js", loadMode = LoadMode.LAZY)
@JsModule("./ace/ace-widget.js")

// @JavaScript("./xtext/require-test.js")
// @JavaScript("./xtext/require-test-import.js")
// @JavaScript("./xtext/jquery-test.js")
public class XtextAceEditor extends AbstractSinglePropertyField<XtextAceEditor, String>
		implements HasSize, HasStyle, Focusable<XtextAceEditor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private String oldValue;

	public XtextAceEditor() {
		super("value", "", false);
	}
//	
//    /**
//     * Sets the theme of the editor.
//     */
//    public void setTheme(String theme) {
//        getElement().setAttribute("theme", "ace/theme/" + theme);
//    }
//
//    /**
//     * Sets the mode of the editor.
//     */
//    public void setMode(String mode) {
//        getElement().setAttribute("mode", "ace/mode/" + mode);
//    }
//
//    /**
//     * Sets font-size for the editor in pixels.
//     */
//    public void setFontSize(int fontSize) {
//        getElement().setAttribute("font-size", fontSize + "px");
//    }
//
//    /**
//     * Sets softtabs for the editor.
//     */
//    public void setSoftTabs(boolean softTabs) {
//        getElement().setAttribute("softtabs", softTabs);
//    }
//
//    /**
//     * Sets tab-size for the editor.
//     */
//    public void setTabSize(int tabSize) {
//        getElement().setAttribute("tab-size", String.valueOf(tabSize));
//    }
//
//    /**
//     * Sets readonly for the editor.
//     */
//    public void setReadOnly(boolean readOnly) {
//        getElement().setAttribute("readonly", readOnly);
//    }
//
//    /**
//     * Sets wrap for the editor.
//     */
//    public void setWrap(Boolean wrap) {
//        getElement().setAttribute("wrap", wrap);
//    }
//
//    //TODO: autoComplete
//
//    /**
//     * Sets minlines for the editor.
//     */
//    public void setMinLines(int minLines) {
//        getElement().setAttribute("minlines", String.valueOf(minLines));
//    }
//
//    /**
//     * Sets maxlines for the editor.
//     */
//    public void setMaxLines(int maxLines) {
//        getElement().setAttribute("maxlines", String.valueOf(maxLines));
//    }
//
//    /**
//     * Sets initialFocus for the editor.
//     */
//    public void setInitialFocus(Boolean initialFocus) {
//        getElement().setAttribute("initialFocus", initialFocus);
//    }
//
//    /**
//     * Sets placeholder for the editor.
//     */
//    public void setPlaceholder(String placeholder) {
//        getElement().setAttribute("placeholder", placeholder);
//    }
//
//    /**
//     * Returns the current value of the editor.
//     */
//    @Synchronize(value = "editor-content", property = "editorValue")
//    public String getValue() {
//        return getElement().getProperty("editorValue");
//    }
//
//    /**
//     * Sets value for the editor.
//     */
//    public void setValue(String value) {
//        getElement().setProperty("value", value);
//    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ComponentValueChangeEvent<XtextAceEditor, String>> listener) {
        oldValue = getValue();

        getElement().addEventListener("editor-content", e -> {
            listener.valueChanged(new ComponentValueChangeEvent<>(this, this, oldValue, true));
            oldValue = getValue();
        });

        return super.addValueChangeListener(listener);
    }

	

}
