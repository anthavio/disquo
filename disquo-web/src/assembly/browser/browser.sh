#!/bin/sh

PRG="$0"
PRGDIR=`dirname "$PRG"` 
cd "$PRGDIR"
APP_HOME=`pwd`

APP_CLASSPATH="etc:lib/*:lib/ext/*"

cygwin=false
case "`uname`" in
  CYGWIN*) cygwin=true;;
esac

if [ "$cygwin" = "true" ]; then
	JAVA_HOME=`cygpath --absolute --windows "$JAVA_HOME"`
	APP_CLASSPATH=`cygpath --path --windows "$APP_CLASSPATH"` 
fi

MAIN_CLASS=com.anthavio.disquo.browser.DisqusBrowserJettyMain

APP_OPTS=

JMX_PORT=9013

JAVA_OPTS="-Xmx128m -XX:+HeapDumpOnOutOfMemoryError"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
#JAVA_OPTS="$JAVA_OPTS -Dwebconsole.type=properties -Dwebconsole.jms.url=tcp://localhost:61616 -Dwebconsole.jmx.url=service:jmx:rmi:///jndi/rmi://localhost:$JMX_PORT/jmxrmi -Dwebconsole.jmx.role=sim -Dwebconsole.jmx.password=kokosak"
JAVA_OPTS="$JAVA_OPTS -Dlogback.configurationFile=etc/logback.xml"

#echo java $JAVA_OPTS -cp $APP_CLASSPATH $MAIN_CLASS $APP_OPTS
$JAVA_HOME/bin/java $JAVA_OPTS -cp $APP_CLASSPATH $MAIN_CLASS $APP_OPTS
