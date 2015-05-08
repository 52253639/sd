/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ProjectCalendarListUI extends AbstractProjectCalendarListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectCalendarListUI.class);

	/**
	 * output class constructor
	 */
	public ProjectCalendarListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longnumber",SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("isSys",Boolean.TRUE));
		filter.setMaskString("#0 or #1");
		if(viewInfo != null && viewInfo.getFilter() != null){
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else{
			viewInfo.setFilter(filter);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleCalendarFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectCalendarEditUI.class.getName();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.windowTitle = "日历设置";
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	// update by libing at 2011-8-3
	protected String[] getLocateNames() {
		// TODO Auto-generated method stub
		String locateNames[] = new String[3];
		locateNames[0] = "name";
		locateNames[1] = "number";
		locateNames[2] = "projectName";
		return locateNames;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String str[] = getSelectedListId();
		IScheduleCalendar info = (IScheduleCalendar) getBizInterface();
		// 标准日历不准删除
		// for (int i = 0; i < str.length; i++) {
		// if ("L5WgOM5+TDCr/oCKWmYL0IclCXs=".equals(str[i])) {
		// FDCMsgBox.showInfo("系统标准日历不允许删除！");
		// abort();
		// }
		// }
//		boolean b = info.isquote(str);
//		if (b) {
//			FDCMsgBox.showInfo("存在被引用的日历，不允许删除！");
//			SysUtil.abort();
//		}
		super.actionRemove_actionPerformed(e);
	}

	// 得到当前选中行的ID
	public String[] getSelectedListId() {
		checkSelected();
		// SelectManager 是kdtable中行管理类
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		ArrayList idList = new ArrayList();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				ICell cell = tblMain.getRow(rowIndex).getCell(getKeyFieldName());
				if (!idList.contains(cell.getValue())) {
					idList.add(cell.getValue());
				}
			}
		}
		String[] listId = null;
		if (idList != null && idList.size() > 0) {
			Iterator iterat = idList.iterator();
			listId = new String[idList.size()];
			int index = 0;
			while (iterat.hasNext()) {
				listId[index] = (String) iterat.next();
				index++;
			}
		}
		return listId;
	}
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		return super.getSelectors();
	}
	
	protected void execQuery() {
		// TODO Auto-generated method stub
		super.execQuery();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		String str[] = getSelectedListId();
//		if (str.length > 0 && "L5WgOM5+TDCr/oCKWmYL0IclCXs=".equals(str[0].toString())) {
//			FDCMsgBox.showInfo("系统预设数据，不允许修改");
//			SysUtil.abort();
//		}
		super.actionEdit_actionPerformed(e);
	}
	
}