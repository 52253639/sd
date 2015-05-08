/*
 * Created on 06.03.2005
 */
package com.kingdee.eas.fdc.schedule.framework.parser;

import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;

/**
 * @author bard
 */
public class ViewHandler extends AbstractKDHandler implements IKDHandler {
    public ViewHandler() {

    }

/*    public void startElement(String namespaceURI, String sName, String qName,
            Attributes attrs) throws FileFormatException {
        if ("view".equals(qName)) {
            loadViewState(attrs);
        }
    }

    private void loadViewState(Attributes attrs) {
        myUIFacade.getZoomManager().setZoomState(
                attrs.getValue("zooming-state"));
    }*/

	public void handle() {
		loadViewState();
	}
	
	private void loadViewState(){
		ScheduleBaseInfo info = getScheduleBaseInfo();
		//TODO view-state 值的设定
		getKDParser().getUiFacade().getZoomManager().setZoomState("default:6");
	}

}
