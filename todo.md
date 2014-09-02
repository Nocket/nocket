# TODOs:

- Umbennen von DMD-Klassen in Nocket-Klassen
- Wird der Generator ein eigenes Projekt?
- Basis-Webseite für Nocket 
  - mit Download-Button
  - Komponenten-Dokumation
- Jira nach Github umziehen 

### TODO meis026 

- Die DMDWebApplication ist entstanden aus der NocketWebApplication, weil es im init-Teile gibt, 
  die SWJ-spezifisch sind. Aber von der NocketWebApplication gibt es eine Ableitung DMDGenericWebApplication. 
  Das funzt nicht.

### Änderungen

- Err.handler().process durch log.debug ersetzt
- ArgReader aus Cuba übernommen
  - Package gelassen
  - cuba.util.ArgumentException durch IllegalArgumentException ersetzt
- Muss die jetty-all-server-7.5.0.v20110901.jar und servlet-api-2.5.jar mitgeliefert werden?
