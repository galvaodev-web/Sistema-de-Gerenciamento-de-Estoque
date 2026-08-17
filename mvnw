#!/bin/sh
exec java -classpath "$(dirname "$0")/.mvn/wrapper/maven-wrapper.jar" -Dmaven.multiModuleProjectDirectory="$(dirname "$0")" org.apache.maven.wrapper.MavenWrapperMain "$@"
