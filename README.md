## PRG2 - Exercise 1 Template Repository
Use this repository as a template for your PRG2 exercise 1 submission. The template serves as a guideline to complete your exercise 1 with JavaFX.
You can do any changes you want to the code.

### Start the application
Start the application by either running the `main` method in the `FhmdbApplication` class or by running `mvn clean javafx:run` in Maven.

Note: If you run the application with `mvn clean javafx:run` you may face a incompatible version error (`java.lang.module.InvalidModuleDescriptorException: Unsupported major.minor version XXX`). If so, check the version of your system Java installation (Environment Variables). 
Maven uses the default system Java installation. If you have multiple Java installations, you can set the Java version for Maven by adding the following to your 
`pom.xml`:
```xml
<build>
  ...
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.10.1</version>
            <configuration>
                <source>17</source> // change to your system version here
                <target>17</target> // and here
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```