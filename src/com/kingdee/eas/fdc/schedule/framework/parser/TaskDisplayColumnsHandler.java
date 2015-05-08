/*
 * Created on Mar 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.kingdee.eas.fdc.schedule.framework.parser;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.ganttproject.gui.TableHeaderUIFacade;
import net.sourceforge.ganttproject.parser.ParsingListener;

import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskdisplaycolumnsCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskdisplaycolumnsInfo;

public class TaskDisplayColumnsHandler  extends AbstractKDHandler implements IKDHandler,ParsingListener {
	private final TableHeaderUIFacade myBuffer = new VisibleFieldsImpl();
	private final TableHeaderUIFacade myVisibleFields;
    public TaskDisplayColumnsHandler(TableHeaderUIFacade myVisibleFields) {
//    	this(visibleFields, "displaycolumn", "property-id", "order", "width");
		// myBuffer.add("tpd10", 0, 32);
    	this.myVisibleFields = myVisibleFields;
    }

    private void loadTaskDisplay(ScheduleTaskdisplaycolumnsInfo displayColumnsInfo) {
    	if(displayColumnsInfo==null||displayColumnsInfo.getProperty()==null||displayColumnsInfo.getProperty().getPropertyId()==null){
    		return;
    	}
        String id = displayColumnsInfo.getProperty().getPropertyId();
        int order = displayColumnsInfo.getOrder();
        int width = displayColumnsInfo.getWidth(); 
        myBuffer.add(id, order, width);
    }    
    
    private static class TaskFieldImpl implements TableHeaderUIFacade.Column {
    	private final String myID;
		private final int myOrder;
		private final int myWidth;

		TaskFieldImpl(String id, int order, int width) {
    		myID = id;
    		myOrder = order;
    		myWidth = width;
    	}
		public String getID() {
			return myID;
		}

		public int getOrder() {
			return myOrder;
		}

		public int getWidth() {
			return myWidth;
		}
		public boolean isVisible() {
			return true;
		}
		public String getName() {
			return null;
		}
    	
    }
    private static class VisibleFieldsImpl implements TableHeaderUIFacade {
		private List myFields = new ArrayList();
		public void add(String name, int order, int width) {
			myFields.add(new TaskFieldImpl(name, order, width));
		}
		public void clear() {
			myFields.clear();
		}
		public Column getField(int index) {
			return (Column) myFields.get(index);
		}
		public int getSize() {
			return myFields.size();
		}
		public void importData(TableHeaderUIFacade source) {
			throw new UnsupportedOperationException();
		}
	}
	public void handle() {
    	ScheduleTaskdisplaycolumnsCollection scheduleDispColumns = getScheduleBaseInfo().getScheduleDispColumns();
    	for(int i=0;i<scheduleDispColumns.size();i++){
    		loadTaskDisplay(scheduleDispColumns.get(i));
    	}
	}
	
    public void parsingStarted() {
    	myVisibleFields.clear();
    }

    public void parsingFinished() {
    	myVisibleFields.importData(myBuffer);
    }
    
}
