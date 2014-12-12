package org.nocket.test.widgets.choices;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.util.io.IClusterable;

@SuppressWarnings("serial")
public class ChoicesTestsPageModel implements IClusterable {

    private List<DayListItem> allDays;

    public ChoicesTestsPageModel() {
        allDays = Arrays.asList(new DayListItem("Mondays"), new DayListItem("Tuesdays"), new DayListItem("Wednesdays"),
                new DayListItem("Thursdays"), new DayListItem("Fridays"), new DayListItem("Saturdays"),
                new DayListItem("Sundays"));
    }

    public List<DayListItem> getAllDays() {
        return allDays;
    }

    private List<DayListItem> multiDays, multiDaysClassic;

    private DayListItem oneDay;

    public List<DayListItem> getMultiDays() {
        return multiDays;
    }

    public void setMultiDays(List<DayListItem> multiDays) {
        this.multiDays = multiDays;
    }

    public List<DayListItem> getMultiDaysClassic() {
        return multiDaysClassic;
    }

    public void setMultiDaysClassic(List<DayListItem> multiDaysClassic) {
        this.multiDaysClassic = multiDaysClassic;
    }

    public DayListItem getOneDay() {
        return oneDay;
    }

    public void setOneDay(DayListItem oneDay) {
        this.oneDay = oneDay;
    }

}
