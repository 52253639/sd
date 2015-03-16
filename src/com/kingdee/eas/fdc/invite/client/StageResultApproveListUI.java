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
import com.kingdee.eas.fdc.invite.StageResultApproveCollection;
import com.kingdee.eas.fdc.invite.StageResultApproveFactory;
import com.kingdee.eas.fdc.invite.StageResultApproveInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class StageResultApproveListUI extends AbstractStageResultApproveListUI
{
    private static final Logger logger = CoreUIObject.getLogger(StageResultApproveListUI.class);
    public StageResultApproveListUI() throws Exception
    {
        super();
    }
    protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		StageResultApproveCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				StageResultApproveInfo info = (StageResultApproveInfo) iter.next();

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
	private StageResultApproveCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		StageResultApproveCollection clarifyCol = StageResultApproveFactory.getRemoteInstance().getStageResultApproveCollection(view);

		return clarifyCol;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return StageResultApproveFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return StageResultApproveEditUI.class.getName();
	}
	protected String getTitle() {
		return "阶段性成果审批记录";
	}

}