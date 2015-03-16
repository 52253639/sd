/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InviteTenderPlanningListUI extends AbstractInviteTenderPlanningListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteTenderPlanningListUI.class);
    public InviteTenderPlanningListUI() throws Exception
    {
        super();
    }
    protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		InviteTenderPlanningCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				InviteTenderPlanningInfo info = (InviteTenderPlanningInfo) iter.next();

				IRow row = getBillListTable().addRow();

				if (info != null) {
					row.getCell(COL_ID).setValue(info.getId());
					row.getCell(COL_NUMBER).setValue(info.getNumber());
					row.getCell(COL_STATE).setValue(info.getState());

					if (info.getRespDept() != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
					}
					if (info.getCreator() != null) {
						row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
					}
					row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
					if (info.getAuditor() != null) {
						row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
					}
					row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
				}
			}
		}
	}
	public static InviteTenderPlanningCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		InviteTenderPlanningCollection clarifyCol = InviteTenderPlanningFactory.getRemoteInstance().getInviteTenderPlanningCollection(view);

		return clarifyCol;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteTenderPlanningFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return InviteTenderPlanningEditUI.class.getName();
	}
	protected String getTitle() {
		return "招标策划记录";
	}

}