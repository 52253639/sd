/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SupplierQualifyListUI extends AbstractSupplierQualifyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierQualifyListUI.class);
    
    public SupplierQualifyListUI() throws Exception
    {
        super();
    }
    protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		SupplierQualifyCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				SupplierQualifyInfo info = (SupplierQualifyInfo) iter.next();

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
	public static SupplierQualifyCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("title"));
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

		SupplierQualifyCollection clarifyCol = SupplierQualifyFactory.getRemoteInstance().getSupplierQualifyCollection(view);

		return clarifyCol;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierQualifyFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierQualifyEditUI.class.getName();
	}
	protected String getTitle() {
		return "入围供应商记录";
	}
    
	
}