@echo off
setlocal

set SCRIPT_DIR=%~dp0%
cd %SCRIPT_DIR%
set APP_HOME=%cd%

set APP_CLASSPATH=etc;lib/*;lib/ext/*

set MAIN_CLASS=com.anthavio.disquo.browser.DisqusBrowserJettyMain
set APP_OPTS=

set JMX_PORT=9013

set JAVA_OPTS=-Xmx128m -XX:+HeapDumpOnOutOfMemoryError
rem set JAVA_OPTS=%JAVA_OPTS% -Dcom.sun.management.jmxremote.port=%JMX_PORT% -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
rem set JAVA_OPTS=%JAVA_OPTS% -Dwebconsole.type=properties -Dwebconsole.jms.url=tcp://localhost:61616 -Dwebconsole.jmx.url=service:jmx:rmi:///jndi/rmi://localhost:%JMX_PORT%/jmxrmi -Dwebconsole.jmx.role=sibsim -Dwebconsole.jmx.password=kokosak
set JAVA_OPTS=%JAVA_OPTS% -Dlogback.configurationFile=etc/logback.xml


rem echo java %JAVA_OPTS% -cp %APP_CLASSPATH% %MAIN_CLASS% %APP_OPTS%
%JAVA_HOME%/bin/java %JAVA_OPTS% -cp %APP_CLASSPATH% %MAIN_CLASS% %APP_OPTS%

endlocal
