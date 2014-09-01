package forscher.nocket.page.gen;

import gengui.annotations.Disabled;
import gengui.annotations.Eager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Merge implements Serializable {
    private String ichBinEinFeld;

    private SpezialTabellenZeile[] tabelle;

    private MergeInner inner;

    public Merge() {
        inner = new MergeInner();

        tabelle = new SpezialTabellenZeile[3];
        tabelle[0] = new SpezialTabellenZeile(true, null);
        tabelle[0].setSpalte1("1,1");
        tabelle[0].setSpalte2("2,1");
        tabelle[0].setSpalte3(TimeUnit.DAYS);
        tabelle[0].setFile(new File("un/be.kannt"));
        tabelle[1] = new SpezialTabellenZeile(false, new SerializableCallback() {
            @Override
            public void run() {
                setIchBinEinFeld("tabellenzeile 2 wurde angeklickt!");
            }
        });
        tabelle[1].setSpalte1("1,2");
        tabelle[1].setSpalte2("2,2");
        tabelle[1].setSpalte3(TimeUnit.HOURS);
        tabelle[1].setFile(new File("resources/webapp/img/wicket.png"));
        tabelle[2] = new SpezialTabellenZeile(false, new SerializableCallback() {
            @Override
            public void run() {
                setIchBinEinFeld("tabellenzeile 3 wurde angeklickt!");
            }
        });
        tabelle[2].setSpalte1("1,3");
        tabelle[2].setSpalte2("2,3");
        tabelle[2].setSpalte3(null);
    }

    private abstract class SerializableCallback implements Runnable, Serializable {
    }

    public String getIchBinEinFeld() {
        return ichBinEinFeld;
    }

    @Eager
    public void setIchBinEinFeld(String value) {
        ichBinEinFeld = value;
    }

    public String getIchBinDasSelbeFeldReadOnly() {
        return ichBinEinFeld;
    }

    public String getIchBinDasSelbeFeldDasZweiteMalReadOnly() {
        return ichBinEinFeld;
    }

    @Disabled
    public void setIchBinDasSelbeFeldDasZweiteMalReadOnly(String value) {
        ichBinEinFeld = value;
    }

    public String getIchBinDasSelbeFeldDasDritteMalReadOnly() {
        return ichBinEinFeld;
    }

    public void setIchBinDasSelbeFeldDasDritteMalReadOnly(String value) {
        ichBinEinFeld = value;
    }

    public String disableIchBinDasSelbeFeldDasDritteMalReadOnly() {
        return "weils so is...";
    }

    public SpezialTabellenZeile[] getTabelle() {
        return tabelle;
    }

    public MergeInner getInner() {
        return inner;
    }

    public SpezialTabellenZeile[] getTabelle2() {
        return tabelle;
    }

    public void removeFromTabelle(SpezialTabellenZeile... zeilen) {
        List<SpezialTabellenZeile> retained = new ArrayList<SpezialTabellenZeile>(Arrays.asList(getTabelle()));
        for (SpezialTabellenZeile zeile : zeilen) {
            retained.remove(zeile);
        }
        tabelle = retained.toArray(new SpezialTabellenZeile[0]);
    }

    //    public void removeFromTabelle(SpezialTabellenZeile zeile) {
    //        List<SpezialTabellenZeile> retained = new ArrayList<SpezialTabellenZeile>(Arrays.asList(getTabelle()));
    //        retained.remove(zeile);
    //        tabelle = retained.toArray(new SpezialTabellenZeile[0]);
    //    }
}
