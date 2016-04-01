
# Nocket

### Vorbereitung

zu installierende Eclipse Plug-Ins:

 - Eclipse WTP (Help -> "Install new Software..")
 - für Tomcat: Eclipse JST Server Adapters (Help -> "Install new Software..")
 - für Jetty: Update-Site - <http://download.Eclipse.org/jetty/updates/jetty-wtp>
 - Gradle Plug-In (Help -> "Eclipse Marketplace...")

### Projekt in Eclipse einrichten und konfigurieren:

 - In Eclipse eine Tomcat 7 Runtime konfigurieren (Einstellungen -> Server -> Runtime Environments). 
   Name sollte `Apache Tomcat 7.0` sein, wird in der Regel von Eclipse so vorgegeben   
 - Projekt in Eclipse importieren (File -> Import... -> Gradle Project). Alternativ kann man auch über die Shell das Eclipse 
   Projekt generieren (`gradlew cleanEclipse eclipse`) und dann als reguläres Eclipse Projekt importieren.

Bei Bedarf kann der Gradle-Build benutzerspezifisch konfiguriert werden. Hierzu geht man wie folgt vor:

 - Kopieren der Datei `./gradle/default.config` zu `./gradle/<username>.config`
 - Anpassen der Werte in `./gradle/<username>.config`
 - Bei Ã„nderungen an den Eclipse Werten ggfs. Projekt neu generieren: `gradlew cleanEclipse eclipse`
 
Falls es Probleme mit einem Proxy gibt, folgende Datei im `.gradle`-Verzeichnis des Benutzers anlegen:

    ~/.gradle/gradle.properties:
    
    systemProp.http.proxyHost=<proxy ip>
    systemProp.http.proxyPort=8080
    systemProp.http.nonProxyHosts=localhost

### Eigene Features entwickeln / Arbeiten mit Branches

https://github.com/Nocket/nocket/wiki

### Beispielanwendung starten

    ./gradlew tomcatRunWar

### Releasefähige Dateien erzeugen

Die generierten Dateien findet man unter `build/libs`, für das Testen in Eclipse ist das generieren der Artefakte nicht notwendig.

- für die lokale Entwickler Maschine:

    `./gradlew build`
    
- Aktueller Entwicklungs-Snapshot für Test-Maschinen

    `./gradlew buildSnapshot` // generiert das JAR und ein WAR mit den Beispielen
    
- Release

    `./gradlew buildRelease`  // generiert das JAR und ein WAR mit den Beispielen

### Release in Gradle einbinden

    dependencies {
        compile "org.nocket:nocket:0.4.4"
    }
    
    repositories { 
        mavenCentral()
        maven { url "https://raw.githubusercontent.com/Nocket/mvn-repository/master/releases" }
    }
