@ECHO OFF
SETLOCAL
SET "WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar"
SET "MAVEN_PROJECTBASEDIR=%~dp0"
"java" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%." -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
ENDLOCAL
