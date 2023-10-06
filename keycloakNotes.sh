#Container into Alpine
docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin  -p 8080:8080 jboss/keycloak

sudo groupadd keycloak

sudo useradd -r -g keycloak -d /opt/keycloak -s /sbin/nologin keycloak

sudo chown -R keycloak:keycloak
sudo chmod o+x /opt/keycloak/bin/

http://localhost:8080/auth/

---
#OK
KEYCLOAK_ADMIN=admin KEYCLOAK_ADMIN_PASSWORD=admin ./bin/kc.sh start-d
./bin/kc.sh start-dev

#Docker
export KC_VERSION=21.0.2
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:${KC_VERSION} start-dev

#token_endpoint
http://localhost:8080/realms/spmia-realm/protocol/openid-connect/token


#Log container started
JAVA_OPTS:  -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true   --add-exports=java.desktop/sun.awt=ALL-UNNAMED --add-exports=java.naming/com.sun.jndi.ldap=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.invoke=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.security=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.naming/javax.naming=ALL-UNNAMED

