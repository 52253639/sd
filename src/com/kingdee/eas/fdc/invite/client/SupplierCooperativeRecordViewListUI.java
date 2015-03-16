/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class SupplierCooperativeRecordViewListUI extends AbstractSupplierCooperativeRecordViewListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierCooperativeRecordViewListUI.class);
    
    /**
     * output class constructor
     */
    public SupplierCooperativeRecordViewListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		btnAttachment.setVisible(false);
		btnAuditResult.setVisible(false);
		btnRefresh.setVisible(true);
		initRecordTable();
	}
    protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return SupplierCooperativeRecordViewListUI.class.getName();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		tblMain.getStyleAttributes().setLocked(true);
	}
	protected void tblRecord_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO Auto-generated method stub
		tblRecord.getStyleAttributes().setLocked(true);
	}
	private void initRecordTable() throws BOSException {
		// UIContext uiContext = new UIContext(this);
		SupplierInfo supplier = (SupplierInfo) getUIContext().get("supplier");
		SupplierInviteRecordCollection recordCols = null;
		if (supplier != null) {
			txtSupplierName.setText(supplier.getName());
			 recordCols = getSomeInviteRecordCols(supplier.getId().toString());
		}
		txtSupplierName.setEnabled(false);
		if(recordCols.size()>0){
			Iterator iter = recordCols.iterator();
			while(iter.hasNext())
			{
				SupplierInviteRecordInfo info = (SupplierInviteRecordInfo)iter.next();
				tblRecord.checkParsed();
				IRow row = tblRecord.addRow(0);

				if(info.getSupplier() != null)
				{
//					row.getCell("projectName").setValue(
//							info.getInviteProject().getProject());
					row.getCell("recordTask").setValue(info.getInviteProject().getName());
				}
//				row.getCell("Date").setValue(info.getDate());
				row.getCell("times").setValue(new Integer(info.getTimes()));
				row.getCell("operate").setValue(info.getCreator().getName());
				row.getCell("id").setValue(info.getId());
			}
		}

	

		
	}

	private SupplierInviteRecordCollection getSomeInviteRecordCols(String paramId)
			throws BOSException {
		SupplierInfo supplier = (SupplierInfo) getUIContext().get("supplier");
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));

		view.getSelector().add(new SelectorItemInfo("creator.id"));
		view.getSelector().add(new SelectorItemInfo("creator.number"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));

		view.getSelector().add(new SelectorItemInfo("times"));
		view.getSelector().add(new SelectorItemInfo("Date"));

		view.getSelector().add(new SelectorItemInfo("supplier.id"));
		view.getSelector().add(new SelectorItemInfo("supplier.number"));
		view.getSelector().add(new SelectorItemInfo("supplier.name"));

		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.name"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.number"));
		
		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.project.*"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.project.name"));
		view.getSelector().add(new SelectorItemInfo("inviteProject.project.number"));

		FilterInfo filter = new FilterInfo();
		if (supplier != null) {
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId()));
		}

		view.setFilter(filter);

		SupplierInviteRecordCollection cols = SupplierInviteRecordFactory.getRemoteInstance()
					.getSupplierInviteRecordCollection(view);
		return cols;
	}
	private void initSignTable() {

	}
	public FilterInfo getFilterInfo() {
		SupplierInfo supplier = (SupplierInfo) this.getUIContext().get("supplier");
		if (supplier != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("partB.id", supplier.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
			return filter;
		}

		return super.getFilterInfo();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		FilterInfo filter = getFilterInfo();

		if (viewInfo.getFilter() == null) {
			viewInfo.setFilter(filter);
		} else {
			FilterInfo filter1 = viewInfo.getFilter();
			try {
				filter1.mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
			viewInfo.setFilter(filter1);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

}