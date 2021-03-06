# Ace-Editor integration into vaadin - spring boot project

This project was setup using https://vaadin.com/start/latest/project-base

The project provides a basic vaadin spring xtext-ace-editor integration.

When run, navigate to http://localhost:8080 to see an xtext-ace-editor,
integrated with some other vaadin ui components.


## build

Initial setup:
- Make sure that you created the xtext mydsl example.
- Add the following section to the `pom.xml` of the `org.xtext.example.mydsl.web` project
``` xml
<!-- ... (maven-war-plugin) -->
<configuration>
  <!-- other configurations -->
  <attachClasses>true</attachClasses>
  <classesClassifier>classes</classesClassifier>
</configuration
<!-- ... -->
```
  This will add all the classes within this war to the maven repository.
- Executed `mvn install` (in the root project) to get the all artifacts (org.xtext.example.mydsl...) into the local maven repository, which are referenced by this project.

Now you are ready to compile this project:

`mvn compile`

## run

`mvn spring-boot:run`

## Exchange DSL

- Make sure to add the appropriate maven artefacts for that (other) language.
  Add (or exchange) all `${projectGroupId}` entries with their respective counterpart (or change the `projectGroupId` variable).
  ```xml
		<dependency>
			<groupId>${projectGroupId}</groupId>
			<artifactId>${projectGroupId}</artifactId>
			<version>${projectVersion}</version>
		</dependency>
		<dependency>
			<groupId>${projectGroupId}</groupId>
			<artifactId>${projectGroupId}.ide</artifactId>
			<version>${projectVersion}</version>
		</dependency>
		<dependency>
			<groupId>${projectGroupId}</groupId>
			<artifactId>${projectGroupId}.web</artifactId>
			<version>${projectVersion}</version>
			<classifier>classes</classifier>
		</dependency>
  ```
- Copy the generated syntax definition from the web project `src/main/webapp/xtext-resources/generated/mode-<your-language>.js` 
  to src/main/resources/META-INF/resources/frontend/mode-<your language>.js
  and add '<your-language>' as first parameter to the topmost define function call:
  ```javascript
    define('<your-language>', ["ace/lib/oop", ...
  ```    
  and add the following header to the js file itself:
  ```javascript
	import { define } from "@vaadin/flow-frontend/xtext/require.js"
	
	import ace from 'ace-builds'
	define('ace/ace',[],function() { return ace; });
	define('ace/lib/oop',[], function() { return ace.require('ace/lib/oop'); });
	define('ace/mode/text',[], function() { return ace.require('ace/mode/text'); });
	define('ace/mode/text_highlight_rules',[], function() { return ace.require('ace/mode/text_highlight_rules'); });
	
	// from this point down follows the original generated syntax definition file of the xtext project (except for the name 'mydsl' passed to the definition)
  ```
  
- Change the xtext servlet to your class in XtextServiceConfig.java
- Change `syntaxDefinition` and `xtextLang` in `MainView.java` to your language. 

## Import into eclipse

(see below)

# Project Base for Vaadin and Spring Boot (original README.md from hereon)

This is an example project that can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.


## Running the Application
There are two ways to run the application :  using `mvn spring-boot:run` or by running the `Application` class directly from your IDE.

You can use any IDE of your preference,but we suggest Eclipse or Intellij IDEA.
Below are the configuration details to start the project using a `spring-boot:run` command. Both Eclipse and Intellij IDEA are covered.

#### Eclipse
- Right click on a project folder and select `Run As` --> `Maven build..` . After that a configuration window is opened.
- In the window set the value of the **Goals** field to `spring-boot:run` 
- You can optionally select `Skip tests` checkbox
- All the other settings can be left to default

Once configurations are set clicking `Run` will start the application

#### Intellij IDEA
- On the right side of the window, select Maven --> Plugins--> `spring-boot` --> `spring-boot:run` goal
- Optionally, you can disable tests by clicking on a `Skip Tests mode` blue button.

Clicking on the green run button will start the application.

After the application has started, you can view your it at http://localhost:8080/ in your browser.


If you want to run the application locally in the production mode, use `spring-boot:run -Pproduction` command instead.
### Running Integration Tests

Integration tests are implemented using [Vaadin TestBench](https://vaadin.com/testbench). The tests take a few minutes to run and are therefore included in a separate Maven profile. To run the tests using Google Chrome, execute

`mvn verify -Pit`

and make sure you have a valid TestBench license installed. If the tests fail because of an old Chrome Driver or you want to use a different browser, you'll need to update the `webdrivers.xml` file in the project root.

Profile `it` adds the following parameters to run integration tests:
```sh
-Dwebdriver.chrome.driver=path_to_driver
-Dcom.vaadin.testbench.Parameters.runLocally=chrome
```

If you would like to run a separate test make sure you have added these parameters to VM Options of JUnit run configuration

## Project overview

Project follow the Maven's [standard directory layout structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html):
- Under the `srs/main/java` are located Application sources
   - `Application.java` is a runnable Java application class and a starting point
   - `GreetService.java` is a  Spring service class
   - `MainView.java` is a default view and entry point of the application
- Under the `srs/test` are located test files
- `src/main/resources` contains configuration files and static resources
- The `frontend` directory in the root folder contains client-side dependencies and resource files
   - All CSS styles used by the application are located under the root directory `frontend/styles`    
   - Templates would be stored under the `frontend/src`


## More Information and Next Steps

- Vaadin Basics [https://vaadin.com/docs](https://vaadin.com/docs)
- More components at [https://vaadin.com/components](https://vaadin.com/components) and [https://vaadin.com/directory](https://vaadin.com/directory)
- Download this and other examples at [https://vaadin.com/start](https://vaadin.com/start)
- Using Vaadin and Spring [https://vaadin.com/docs/v14/flow/spring/tutorial-spring-basic.html](https://vaadin.com/docs/v14/flow/spring/tutorial-spring-basic.html) article
- Join discussion and ask a question at [https://vaadin.com/forum](https://vaadin.com/forum)


## Notes

If you run application from a command line, remember to prepend a `mvn` to the command.
