# TODOs:

- Muss die jetty-all-server-7.5.0.v20110901.jar und servlet-api-2.5.jar mitgeliefert werden?
- Wird der Generator ein eigenes Projekt?
- Sind Logger eigebtlich Serialisierbar??? H���h.

### TODO meis026 

- Die DMDWebApplication ist entstanden aus der NocketWebApplication, weil es im init-Teile gibt, 
  die SWJ-spezifisch sind. Aber von der NocketWebApplication gibt es eine Ableitung DMDGenericWebApplication. 
  Das funzt nicht.

### �nderungen

- Err.handler().process durch log.debug ersetzt
- ArgReader aus Cuba �bernommen
  - Package gelassen
  - cuba.util.ArgumentException durch IllegalArgumentException ersetzt