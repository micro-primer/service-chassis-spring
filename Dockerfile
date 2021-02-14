FROM jetty:10.0.0-jdk11-slim

ENV TZ UTC

RUN java -jar "$JETTY_HOME/start.jar" --add-module=deploy,jmx,stats,rewrite

WORKDIR ${JETTY_BASE}
#RUN printf '<?xml version="1.0"?>\n\
#<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure_9_3.dtd">\n\
#<Configure id="Server" class="org.eclipse.jetty.server.Server">\n\
#  <Get id="oldhandler" name="handler"/>\n\
#  <Set name="handler">\n\
#    <New id="Rewrite" class="org.eclipse.jetty.rewrite.handler.RewriteHandler">\n\
#      <Set name="handler"><Ref refid="oldhandler"/></Set>\n\
#      <Call name="addRule">\n\
#        <Arg>\n\
#          <New class="org.eclipse.jetty.rewrite.handler.RedirectPatternRule">\n\
#            <Set name="pattern">/</Set>\n\
#            <Set name="location">/index.html</Set>\n\
#          </New>\n\
#        </Arg>\n\
#      </Call>\n\
#    </New>\n\
#  </Set>\n\
#</Configure>' > ${JETTY_BASE}/etc/jetty-rewrite.xml

COPY app/target/service.war ${JETTY_BASE}/webapps/ROOT.war

ENTRYPOINT exec java \
#           -Djetty.logs=${JETTY_HOME}/log \
#           -Djetty.state=${JETTY_STATE} \
           -Djetty.home=${JETTY_HOME} \
           -Djetty.base=${JETTY_BASE} \
           -Djetty.http.port=8080 \
           -Djava.rmi.server.hostname=127.0.0.1 \
           -Dlogback.configurationFile=${LOGBACK_CONFIGURATION:-logback-json.xml} \
           -Dcom.sun.management.jmxremote.ssl=false \
           -Dcom.sun.management.jmxremote.authenticate=false \
           -Dcom.sun.management.jmxremote.port=1099 \
           -Dcom.sun.management.jmxremote.rmi.port=1099 \
           -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${JETTY_HOME} \
           -XX:+UseStringDeduplication \
           -Xms128M -Xmx128M \
           ${JAVA_OPTS} \
           -jar ${JETTY_HOME}/start.jar