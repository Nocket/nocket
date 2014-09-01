package forscher.nocket.page.gen.layout;

import gengui.annotations.Assisted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

public class AllComponents implements Serializable {

    String textfield;
    String textarea;
    String label;
    Integer numberfield;
    Date datefield;
    Date assisted;
    Boolean checkbox;
    TimeUnit combobox;
    List<TimeUnit> listbox;
    AllComponentsNested nested = new AllComponentsNested();
    List<AllComponentsNested> table;

    protected AllComponents() {
        table = new ArrayList<AllComponentsNested>();
        table.add(new AllComponentsNested());
        table.add(new AllComponentsNested());
    }

    @NotNull
    public Date getAssisted() {
        return assisted;
    }

    @Assisted
    public void setAssisted(Date assisted) {
        this.assisted = assisted;
    }

    public String getLabel() {
        return label;
    }

    @NotNull
    public String getTextfield() {
        return textfield;
    }

    public void setTextfield(String textfield) {
        this.textfield = textfield;
    }

    @NotNull
    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    @NotNull
    public Integer getNumberfield() {
        return numberfield;
    }

    public void setNumberfield(Integer numberfield) {
        this.numberfield = numberfield;
    }

    @NotNull
    public Date getDatefield() {
        return datefield;
    }

    public void setDatefield(Date datefield) {
        this.datefield = datefield;
    }

    @NotNull
    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String validateCheckbox(Boolean checkbox) {
        if (checkbox == null || !checkbox)
            return "The check box must be checked";
        return null;
    }

    @NotNull
    public TimeUnit getCombobox() {
        return combobox;
    }

    public void setCombobox(TimeUnit combobox) {
        this.combobox = combobox;
    }

    @NotNull
    public List<TimeUnit> getListbox() {
        return listbox;
    }

    public void setListbox(List<TimeUnit> listbox) {
        this.listbox = listbox;
    }

    public String validateListbox(List<TimeUnit> listbox) {
        if (listbox.size() < 1)
            return "At least one entry must be selected!";
        return null;
    }

    public List<TimeUnit> choiceListbox() {
        List<TimeUnit> choices = new ArrayList<TimeUnit>();
        choices.add(TimeUnit.DAYS);
        choices.add(TimeUnit.HOURS);
        return choices;
    }

    public AllComponentsNested getNested() {
        return nested;
    }

    public void setNested(AllComponentsNested nested) {
        this.nested = nested;
    }

    public List<AllComponentsNested> getTable() {
        return table;
    }

    public void setTable(List<AllComponentsNested> table) {
        this.table = table;
    }

    public void button() {
        System.out.println("Done");
    }
}
