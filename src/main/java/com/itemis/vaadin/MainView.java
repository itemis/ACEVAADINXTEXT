package com.itemis.vaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
// import com.vaadin.flow.server.VaadinSession;

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
// @CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
// @PreserveOnRefresh
// @Push
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private TextField textField;
	private XtextAceEditor aceEditor;
	/**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {    	
    	aceEditor = new XtextAceEditor();
		aceEditor.setTheme("eclipse");

		aceEditor.setSoftTabs(false);
		aceEditor.setTabSize(4);
		aceEditor.setWrap(false);
		aceEditor.setReadOnly(false);
		aceEditor.setMinLines(2);
		aceEditor.setMaxLines(20);
		aceEditor.setPlaceholder("Please enter Code");
		aceEditor.setValue("Hello Sancho !\n");

		aceEditor.setSizeFull();
		aceEditor.setMaxWidth("600px");
		aceEditor.setMaxHeight("400px");
		aceEditor.setMinWidth("500px");
		aceEditor.getElement().getStyle().set("overflow", "auto");
		aceEditor.getElement().getStyle().set("align-self", "start");

        add(aceEditor);
        
        Button button = new Button("Get editor contents");
        button.addClickListener(this::showButtonClickedMessage);
        add(button);
        
        textField = new TextField("Editor contents");
        textField.setWidth("400px");
        add(textField);
        
        aceEditor.focus();
    }
    
    private void showButtonClickedMessage(ClickEvent<?> event) {
    	String editorValue = aceEditor.getValue();
    	textField.setValue(editorValue);
    	aceEditor.focus();
    }
   

}
