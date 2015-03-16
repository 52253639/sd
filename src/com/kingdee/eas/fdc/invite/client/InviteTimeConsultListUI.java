/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.InviteTimeConsultCollection;
import com.kingdee.eas.fdc.invite.InviteTimeConsultFactory;
import com.kingdee.eas.fdc.invite.InviteTimeConsultInfo;
import com.kingdee.eas.framework.*;
/**
 * output class name
 */
public class InviteTimeConsultListUI extends AbstractInviteTimeConsultListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteTimeConsultListUI.class);
    public InviteTimeConsultListUI() throws Exception
    {
        super();
    }
	protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		
		InviteTimeConsultCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				InviteTimeConsultInfo info = (InviteTimeConsultInfo) iter.next();
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

	private InviteTimeConsultCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		InviteTimeConsultCollection col = InviteTimeConsultFactory.getRemoteInstance().getInviteTimeConsultCollection(view);

		return col;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteTimeConsultFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return InviteTimeConsultEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	protected String getTitle() {
		return "增加回标次数请示记录";
	}
}