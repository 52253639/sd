/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MoneyDesListUI extends AbstractMoneyDesListUI {
	private static final Logger logger = CoreUIObject.getLogger(MoneyDesListUI.class);

	/**
	 * output class constructor
	 */
	public MoneyDesListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
//		 return UIFactoryName.MODEL;
		return UIFactoryName.NEWTAB;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return MoneyDesEditUI.class.getName();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MoneyDefineFactory.getRemoteInstance();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			FilterInfo groupFilter = new FilterInfo();
			groupFilter.getFilterItems().add(new FilterItemInfo("isGroup", Boolean.FALSE));
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)		{
				viewInfo.getFilter().mergeFilter(groupFilter, "and");
			}else{
				viewInfo.setFilter(groupFilter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		String revBillTypeId = null;
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", id));
			if (PayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被付款方案使用,不能删除!");
				return;
			}
			if (SincerReceiveEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被预约排号付款明细使用,不能删除!");
				return;
			}
			if (PrePurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被预定单付款明细使用,不能删除!");
				return;
			}
			if (PurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被认购单付款明细使用,不能删除!");
				return;
			}
			if (SignPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被签约单付款明细使用,不能删除!");
				return;
			}
			if (SHERevBillEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被收款单使用,不能删除!");
				return;
			}
			if (TenBillOtherPayFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被租赁合同其他应收使用,不能删除!");
				return;
			}
			if (TenancyRoomPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被租赁合同收款明细使用,不能删除!");
				return;
			}
			MoneyDefineInfo des = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if(des.getRevBillType()!=null){
				revBillTypeId = des.getRevBillType().getId().toString();
			}
		}
		super.actionRemove_actionPerformed(e);
		if (revBillTypeId != null) {
			ReceivingBillTypeFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(revBillTypeId)));
		}
	}
    	

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", id));
			if (PayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被付款方案使用,不能修改!");
				return;
			}
			if (SincerReceiveEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被预约排号付款明细使用,不能修改!");
				return;
			}
			if (PrePurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被预定单付款明细使用,不能修改!");
				return;
			}
			if (PurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被认购单付款明细使用,不能修改!");
				return;
			}
			if (SignPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被签约单付款明细使用,不能修改!");
				return;
			}
			if (SHERevBillEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被收款单使用,不能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
	}
}