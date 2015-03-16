package com.kingdee.eas.fdc.invite.client;


import java.awt.event.ActionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.client.SysContext;

/**
 * 
 * 描述: 招标清单导出 界面
 * @author owen_wen  date:2011-10-27
 * @version EAS6.1
 */
public class NewListingExportListUI extends QuotingPriceListUI {

	public NewListingExportListUI() throws Exception {
		super();		
	}

	public IMetaDataPK getMetaDataPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewListingExportListUI");
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.btnQuery.setVisible(true);
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		FilterInfo filter = null;
		try {
			filter = this.getMainFilter();
			if(this.getDialog()!=null){
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if(filter!=null&&commFilter!=null)
					filter.mergeFilter(commFilter, "and");
			} else {
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null && querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		viewInfo.setFilter(filter);		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 *  只有最新版本“导出FDC文件”按钮才可用
	 *  @author owen_wen 2010-11-11
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		
		int selectedRowIdx = e.getSelectBlock().getTop();
		IRow row = this.getMainTable().getRow(selectedRowIdx);
		boolean isLastVersion = ((Boolean)row.getCell("isLastVersion").getValue()).booleanValue();
		this.actionExportPrice.setEnabled(isLastVersion); // 只有最新版本“导出FDC文件”才可用
	}

	protected boolean isOrderForClickTableHead() {
		return true;
	}

	public DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}

	/**
	 * 复写父类方法，做自己的权限校验
	 */
	public void actionImportListUI_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK orgPK = getOrgPK(this.actionOnLoad);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), orgPK, "inv_listingExport_view");
	}

	public void actionInviteExecuteInfo_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK orgPK = getOrgPK(this.actionInviteExecuteInfo);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), orgPK, "inv_listingExport_viewexecute");
		super.actionInviteExecuteInfo_actionPerformed(e);
	}
}
