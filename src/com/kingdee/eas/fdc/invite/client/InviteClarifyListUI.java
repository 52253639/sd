/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;
import java.util.Iterator;

import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.invite.InviteClarifyCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyFactory;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 询标谈判 序时薄
 */
public class InviteClarifyListUI extends AbstractInviteClarifyListUI {
	private static final Logger logger = CoreUIObject.getLogger(InviteClarifyListUI.class);
	public InviteClarifyListUI() throws Exception {
		super();
	}
	protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		InviteClarifyCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				InviteClarifyInfo info = (InviteClarifyInfo) iter.next();

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
	static InviteClarifyCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		InviteClarifyCollection clarifyCol = InviteClarifyFactory.getRemoteInstance().getInviteClarifyCollection(view);

		return clarifyCol;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteClarifyFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return InviteClarifyEditUI.class.getName();
	}
	protected String getTitle() {
		return "询标谈判记录";
	}
}