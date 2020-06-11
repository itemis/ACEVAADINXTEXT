package com.itemis.vaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

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
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private TextField textField;
	private XtextAceEditor xtextAceEditor;
	/**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {    	
    	xtextAceEditor = new XtextAceEditor();
		xtextAceEditor.setTheme("eclipse");
//		xtextAceEditor.setContentAssist(true);  // default already is true
//		xtextAceEditor.setValidation(true);     // default already is true
//		xtextAceEditor.setHighlighting(true);	// default already is true
//		xtextAceEditor.setHover(true);			// default already is true
		xtextAceEditor.setSyntaxDefinition("mydsl");
		xtextAceEditor.setXtextLang("mydsl");
		xtextAceEditor.setServiceUrl(XtextServiceConfig.SERVICE_URL);

		xtextAceEditor.setSoftTabs(false);
		xtextAceEditor.setTabSize(4);
//		xtextAceEditor.setWrap(false);          // default already is false
//		xtextAceEditor.setReadOnly(false);      // default already is false
		xtextAceEditor.setMinLines(2);
		xtextAceEditor.setMaxLines(20);
		xtextAceEditor.setPlaceholder("Please enter Code");
		xtextAceEditor.setValue("Hello Sancho !\n");

		xtextAceEditor.setSizeFull();
		xtextAceEditor.setMaxWidth("600px");
		xtextAceEditor.setMaxHeight("400px");
		xtextAceEditor.setMinWidth("500px");
		xtextAceEditor.getElement().getStyle().set("overflow", "auto");
		xtextAceEditor.getElement().getStyle().set("align-self", "start");

        add(xtextAceEditor);
        
        Button button = new Button("Get editor contents");
        button.addClickListener(this::showButtonClickedMessage);
        add(button);
        
        textField = new TextField("Editor contents");
        textField.setWidth("400px");
        add(textField);
        
        xtextAceEditor.focus();
    }
    
    private void showButtonClickedMessage(ClickEvent<?> event) {
    	String editorValue = xtextAceEditor.getValue();
    	textField.setValue(editorValue);
    	xtextAceEditor.focus();
    }
   

}
