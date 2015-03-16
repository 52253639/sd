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
import com.kingdee.eas.fdc.invite.InviteClarifyCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyFactory;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;

/**
 * output class name
 */
public class InviteClarifyFullInfoUI extends AbstractInviteClarifyFullInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteClarifyFullInfoUI.class);
    private final String COL_ID = "id";
    private final String COL_STATE = "state";
    
    private final String COL_RESPDEPT = "respDept";
    private final String COL_CREATOR = "creator";
    private final String COL_CREATETIME ="createTime";
    
    private final String COL_AUDITOR = "auditor";
    private final String COL_ADUITTIME = "aduitTime";
    private final String COL_NUMBER = "number";
    /**
     * output class constructor
     */
    public InviteClarifyFullInfoUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		tblMain.removeRows();
		
		FDCHelper.formatTableDate(tblMain, COL_CREATETIME);
		FDCHelper.formatTableDate(tblMain, COL_ADUITTIME);
		
		String projectId = (String)this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("title"));
		
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("createTime"));
		
		view.getSelector().add(new SelectorItemInfo("auditTime"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("inviteProject", projectId);
		
		view.setFilter(filter);
		InviteClarifyCollection cols = InviteClarifyFactory.getRemoteInstance().getInviteClarifyCollection(view);
		Iterator iter = cols.iterator();
		while(iter.hasNext())
		{
			InviteClarifyInfo tmpInfo = (InviteClarifyInfo)iter.next();
			IRow row = tblMain.addRow();
			row.getCell(COL_ID).setValue(tmpInfo.getId());
			row.getCell(COL_STATE).setValue(tmpInfo.getState().getAlias());
			row.getCell(COL_NUMBER).setValue(tmpInfo.getNumber());
			
			if(tmpInfo.getRespDept() != null)
			{
				row.getCell(COL_RESPDEPT).setValue(tmpInfo.getRespDept().getName());
			}
			
			if(tmpInfo.getCreator() != null)
			{
				row.getCell(COL_CREATOR).setValue(tmpInfo.getCreator().getName());
			}
			row.getCell(COL_CREATETIME).setValue(tmpInfo.getCreateTime());
			
			row.getCell(COL_ADUITTIME).setValue(tmpInfo.getAuditTime());
			
			if(tmpInfo.getAuditor() != null)
			{
				row.getCell(COL_AUDITOR).setValue(tmpInfo.getAuditor().getName());
			}
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
					.create(InviteClarifyEditUI.class.getName(), uiContext,
							null, "FINDVIEW");
			uiWindow.show();
		}
    }
}