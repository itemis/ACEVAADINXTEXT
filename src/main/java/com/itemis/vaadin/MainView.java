package com.itemis.vaadin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import io.github.ciesielskis.AceEditor;
import io.github.ciesielskis.AceMode;
import io.github.ciesielskis.AceTheme;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
// @CssImport("./styles/shared-styles.css")
// @CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {
    	
    	AceEditor aceEditor = new AceEditor();
		aceEditor.setTheme(AceTheme.github);
		aceEditor.setMode(AceMode.java);

		aceEditor.setSoftTabs(false);
		aceEditor.setTabSize(4);
		aceEditor.setWrap(false);
		aceEditor.setReadOnly(false);
		aceEditor.setMinLines(2);
		aceEditor.setMaxLines(20);
		aceEditor.setPlaceholder("Please enter Code");

		aceEditor.setSizeFull();
		aceEditor.setMinWidth("60rem");
		aceEditor.setMinHeight("20rem");
		aceEditor.setMaxHeight("270vh");
		aceEditor.getElement().getStyle().set("overflow", "auto");
		aceEditor.getElement().getStyle().set("align-self", "start");

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        // addClassName("centered-content");

        add(aceEditor);
    }

}
