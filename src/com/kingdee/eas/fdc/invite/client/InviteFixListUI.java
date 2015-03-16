/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.invite.InviteFixHeadCollection;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InviteFixListUI extends AbstractInviteFixListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteFixListUI.class);
    
    /**
     * output class constructor
     */
    public InviteFixListUI() throws Exception
    {
        super();
    }

	@Override
	protected String getTitle() {
		return "修正系数记录";
	}

	@Override
	protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		InviteFixHeadCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				InviteFixHeadInfo info = (InviteFixHeadInfo) iter.next();

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

	private InviteFixHeadCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));

		view.getSelector().add(new SelectorItemInfo("respDept.id"));
		view.getSelector().add(new SelectorItemInfo("respDept.number"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));

		view.getSelector().add(new SelectorItemInfo("creator.id"));
		view.getSelector().add(new SelectorItemInfo("creator.number"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));

		view.getSelector().add(new SelectorItemInfo("createTime"));

		view.getSelector().add(new SelectorItemInfo("auditor.id"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.number"));

		view.getSelector().add(new SelectorItemInfo("auditTime"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.id"));
		view.getSelector().add(new SelectorItemInfo("state"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		InviteFixHeadCollection clarifyCol = InviteFixHeadFactory.getRemoteInstance().getInviteFixHeadCollection(view);

		return clarifyCol;
	}
	
	protected FilterInfo getInvitePorjectFilter() throws Exception {
		FilterInfo filter = super.getInvitePorjectFilter();
		filter.getFilterItems().add(new FilterItemInfo("scalingRule.needCorrection","1"));
		return filter;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return InviteFixHeadFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return InviteFixEditUI.class.getName();
	}

}