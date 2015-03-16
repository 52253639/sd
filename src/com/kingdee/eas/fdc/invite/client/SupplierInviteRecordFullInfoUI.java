/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;

/**
 * output class name
 */
public class SupplierInviteRecordFullInfoUI extends AbstractSupplierInviteRecordFullInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierInviteRecordFullInfoUI.class);
    
    private final String COL_ID = "id";
    private final String COL_SUP_NUMBER = "supplierNumber";
    private final String COL_SUP_NAME = "supplierName";
    private final String COL_DATE = "date";
    private final String COL_TIME = "time";
    private final String COL_CREATOR = "creator";
    /**
     * output class constructor
     */
    public SupplierInviteRecordFullInfoUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.tblMain.checkParsed();
		//this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		tblMain.removeRows();
		
		FDCHelper.formatTableDate(tblMain, COL_DATE);
		
		String projectId = (String)this.getUIContext().get(UIContext.ID);
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("supplier.id"));
		view.getSelector().add(new SelectorItemInfo("supplier.number"));
		view.getSelector().add(new SelectorItemInfo("supplier.name"));
		
		view.getSelector().add(new SelectorItemInfo("supplier.supplier.number"));
		view.getSelector().add(new SelectorItemInfo("supplier.supplier.name"));
		
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("createTime"));
		view.getSelector().add(new SelectorItemInfo("date"));
		view.getSelector().add(new SelectorItemInfo("times"));
		
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("inviteProject", projectId);
		view.setFilter(filter);
		
		SupplierInviteRecordCollection cols = SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
		
		Iterator iter = cols.iterator();
		while(iter.hasNext())
		{
			SupplierInviteRecordInfo tmpInfo = (SupplierInviteRecordInfo)iter.next();
			IRow row = tblMain.addRow();
			row.getCell(COL_ID).setValue(tmpInfo.getId());
			
			if(tmpInfo.getSupplier() != null)
			{
				row.getCell(COL_SUP_NUMBER).setValue(tmpInfo.getSupplier().getNumber());
				row.getCell(COL_SUP_NAME).setValue(tmpInfo.getSupplier().getName());
			}
			
//			row.getCell(COL_DATE).setValue(tmpInfo.getDate());
			row.getCell(COL_TIME).setValue(new Integer(tmpInfo.getTimes()));
			row.getCell(COL_CREATOR).setValue(tmpInfo.getCreator().getName());
		}
		
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception 
    {
    	if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) 
				return ;
			String id = row.getCell(COL_ID).getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, id);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(SupplierInviteRecordEditUI.class.getName(), uiContext,
							null, "FINDVIEW");
			uiWindow.show();
		}
    }

}