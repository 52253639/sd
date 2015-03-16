/**

 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractMonthPayPlanListUI extends
		AbstractContractMonthPayPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractMonthPayPlanListUI.class);

	private CustomerQueryPanel filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;

	public ContractMonthPayPlanListUI() throws Exception {
		super();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
//		viewInfo.setSorter(null);
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void storeFields() {
		super.storeFields();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected String getSelectedKeyValue() {
		// TODO Auto-generated method stub
		return super.getSelectedKeyValue();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCDepConPayPlanBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return FDCDepConPayPlanEditUI.class.getName();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
//		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractMonthPayPlanFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return "id";
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWWIN;
	}

	public void onGetRowSet(IRowSet rowSet) {

		super.onGetRowSet(rowSet);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCBaseDataClientUtils.setupUITitle(this, "合同月度滚动计划-查询");
		setView();
		//tblMain.getColumn("parent.version").getStyleAttributes().setHided(true
		// );
	}

	private void setView() {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}

}