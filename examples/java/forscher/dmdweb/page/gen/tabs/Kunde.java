package forscher.dmdweb.page.gen.tabs;

import gengui.annotations.Group;
import gengui.annotations.GroupChoice;
import gengui.annotations.Prompt;

import java.io.Serializable;

import dmdweb.gen.page.guiservice.WebGuiServiceAdapter;

public class Kunde implements Serializable {

    private Historie historie;
    private String applikationsName;
    private String vorname;
    private String nachname;
    private String kontonr;

    private Historie[] historieTabelle;
    private boolean allowTabChange = true;

    private boolean tongleGroupKarteVisible = false;

    public Kunde() {
        historieTabelle = new Historie[3];
        historieTabelle[0] = newHistorie("12.01.1204", "Vorname geändert", "genehmigt", "ausgeführt");
        historieTabelle[1] = newHistorie("01.11.2012", "Nachname geändert", "genehmigt", "ausgeführt");
        historieTabelle[2] = newHistorie("03.04.2013", "Karte neubeantragt", "abgelehnt", "ausgeführt");
    }

    private Historie newHistorie(String string1, String string2, String string3, String string4) {
        Historie result = new Historie();
        result.setFeld1(string1);
        result.setFeld2(string2);
        result.setFeld3(string3);
        result.setFeld4(string4);
        return result;
    }

    @Group(value = "testgroupname.Historie")
    public Historie getHistorie() {
        return historie;
    }

    @Group(value = "testgroupname.Historie")
    public Historie[] getHistorieTabelle() {
        return historieTabelle;
    }

    public void bestellung() {
        tongleGroupKarteVisible = !tongleGroupKarteVisible;
    }

    @GroupChoice
    public String[] groupOrder() {
        int nrOfGroupsToShow = tongleGroupKarteVisible ? 3 : 2;
        String[] groups = new String[nrOfGroupsToShow];
        groups[0] = "Stamm";
        groups[1] = "testgroupname.Historie";
        if (tongleGroupKarteVisible) {
            groups[2] = "Karte";
        }
        return groups;
    }

    @Group(value = "Karte")
    @Prompt("Prüfen")
    public void pruefen() {
        new WebGuiServiceAdapter().status("Button pruefen pressed.");
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }

    @Group(value = "Stamm")
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        System.err.println("Kunde.setVorname() " + vorname);
        this.vorname = vorname;
    }

    @Group(value = "Stamm")
    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Group(value = "Karte")
    public String getKontonr() {
        return kontonr;
    }

    public void setKontonr(String kontonr) {
        this.kontonr = kontonr;
    }

    public String getApplikationsName() {
        return applikationsName;
    }

    public void setApplikationsName(String applikationsName) {
        this.applikationsName = applikationsName;
    }

    public boolean getAllowTabChange() {
        return allowTabChange;
    }

    public void setAllowTabChange(boolean allowTabChange) {
        this.allowTabChange = allowTabChange;
    }

}
