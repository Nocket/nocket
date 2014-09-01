package org.nocket.component.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.io.IClusterable;

/**
 * Provides a MenuItem for the MenuPanel component. Needs to get a label and a
 * target page.
 * 
 * @author vocke03
 * 
 */
@SuppressWarnings("serial")
public class MenuItem implements IClusterable {

    private String label;
    private Class<? extends Page> targetPage;
    private final List<MenuItem> subItems;
    private PageParameters pageParameters;

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public Class<? extends Page> getTargetPage() {
	return targetPage;
    }

    public void setTargetPage(Class<? extends Page> targetPage) {
	this.targetPage = targetPage;
    }

    public MenuItem(String label, Class<? extends Page> targetPage) {
	super();
	this.label = label;
	this.targetPage = targetPage;
	this.subItems = new ArrayList<MenuItem>();
    }

    public void addSubItem(MenuItem subItem) {
	subItems.add(subItem);
    }

    public void setPageParameters(PageParameters pageParamters) {
	this.pageParameters = pageParamters;
    }

    public PageParameters getPageParameters() {
	return pageParameters;
    }

    public List<MenuItem> getSubItems() {
	return subItems;
    }

    /**
     * Returns true if the selection process was successful so that the menu
     * item can be registered in the session as the last selected one. This
     * causes the appropriate menu to be displayed unfolded until another main
     * menu item is clicked.
     */
    public boolean onClick(Component source, AjaxRequestTarget target) {
	source.setResponsePage(getTargetPage(), pageParameters);
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((label == null) ? 0 : label.hashCode());
	return result;
    }

    // Equals method is required for comparing a menu item with the last selected one from the NocketSession
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MenuItem other = (MenuItem) obj;
	if (!label.equals(other.label))
	    return false;
	if (targetPage == null) {
	    if (other.targetPage != null)
		return false;
	} else if (targetPage != other.targetPage)
	    return false;
	return true;
    }

}
