package com.kingdee.eas.fdc.schedule.client;

import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;

public class WorkAmountF7ScheduleTaskUI extends F7ScheduleTaskUI {

	public WorkAmountF7ScheduleTaskUI() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480355054434308804L;

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
	}
	
   public void fillTable() {
	// TODO Auto-generated method stub
	   KDTable table=this.tbnMain;
   		table.checkParsed();
   		table.removeRows();
		getTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		getTable().getStyleAttributes().setLocked(true);
		Set filterTaskSet = getFilterTask();
		for(Iterator iter=getScheduleInfo().getScheduleTasks().iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			if(filterTaskSet==null||!filterTaskSet.contains(task.getId().toString())){
				IRow row=table.addRow();
				row.setUserObject(task);
				row.setTreeLevel(task.getLevel());
				loadRow(row);
   		}
   	}
}
	
}
