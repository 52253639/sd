/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.CompeterFactory;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CompeterListUI extends AbstractCompeterListUI {
	private static final Logger logger = CoreUIObject.getLogger(CompeterListUI.class);

	/**
	 * output class constructor
	 */
	public CompeterListUI() throws Exception {
		super();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return CompeterEditUI.class.getName();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return CompeterFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// 按扭和菜单显示控制
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}

		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("lastAveragePrice").getStyleAttributes().setNumberFormat("#,###.00");
	}

}