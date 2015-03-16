/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EventTypeListUI extends AbstractEventTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EventTypeListUI.class);
    
    /**
     * output class constructor
     */
    public EventTypeListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}
    
    private void removeBizMenu() {
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new EventTypeInfo();
	}
	protected String getEditUIName() {
		return EventTypeEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return EventTypeFactory.getRemoteInstance();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			String number = (String) row.getCell("number").getValue();
			checkCanDel(number, FDCCustomerConstant.CAN_NOT_EDIT_DES);
		}
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			String number = (String) row.getCell("number").getValue();
			checkCanDel(number, FDCCustomerConstant.CAN_NOT_DEL_DES);
		}
		
		FDCCustomerHelper.checkHasRelatedByTrackRecord("eventType.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
	
	private void checkCanDel(String number, String msg) {
		for(int i=0; i<FDCCustomerConstant.CAN_NOT_DEL_NUMBERS.length; i++){
			if(FDCCustomerConstant.CAN_NOT_DEL_NUMBERS[i].equals(number)){
				MsgBox.showInfo(msg);
				this.abort();
			}
		}
	}
}