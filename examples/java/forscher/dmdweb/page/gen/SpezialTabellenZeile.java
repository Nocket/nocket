package forscher.dmdweb.page.gen;

import gengui.annotations.Disabled;
import gengui.annotations.Format;
import gengui.annotations.Prompt;
import gengui.annotations.TableFieldOrder;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@TableFieldOrder( //
GeneratedConstants.Properties.FormattedDouble + ", " + //
        GeneratedConstants.Properties.Spalte1 + ", " + //
        GeneratedConstants.Properties.Spalte2 + ", " + //
        GeneratedConstants.Properties.Spalte3 + ", " + //
        GeneratedConstants.Properties.FormattedDate + ", " + //
        GeneratedConstants.Properties.File //
)
public class SpezialTabellenZeile implements Serializable {
    private String spalte1;
    private String spalte2;
    private TimeUnit spalte3;
    private File file;
    private boolean buttonDisabled;
    private Runnable buttonCallback;

    public SpezialTabellenZeile(boolean buttonDisabled, Runnable buttonCallback) {
        this.buttonDisabled = buttonDisabled;
        this.buttonCallback = buttonCallback;
    }

    public String getSpalte1() {
        return spalte1;
    }

    public void setSpalte1(String spalte1) {
        this.spalte1 = spalte1;
    }

    public String getSpalte2() {
        return spalte2;
    }

    public void setSpalte2(String spalte2) {
        this.spalte2 = spalte2;
    }

    public TimeUnit getSpalte3() {
        return spalte3;
    }

    public void setSpalte3(TimeUnit spalte3) {
        this.spalte3 = spalte3;
    }

    @Format("dd.MM.yyyy' formatted'")
    public Date getFormattedDate() {
        return new Date();
    }

    @Format("#0.00")
    public double getFormattedDouble() {
        return 1 / 3d;
    }

    public File getFile() {
        return file;
    }

    @Disabled
    public void setFile(File file) {
        this.file = file;
    }

    @Prompt(example = "This is a helping text which should become a tooltip")
    public void buttonTabelle() {
        System.out.println("buttonTabelle: " + toString());
        if (buttonCallback != null) {
            buttonCallback.run();
        }
    }

    public String disableButtonTabelle() {
        if (buttonDisabled) {
            return "deaktiviert!";
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + spalte1 + "|" + spalte2 + "|" + spalte3 + "]";
    }
}
