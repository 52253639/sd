package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

public class OrgUnitAcctFormulaWizard extends FormulaWizardUI{

	private KDBizPromptBox companyNumber;
	private KDBizPromptBox bgNumber;
	
	public OrgUnitAcctFormulaWizard() throws Exception {
		super();
	}
	protected void onOpen() {
		companyNumber = (KDBizPromptBox) this.getParamComponent("companyNumber");
		companyNumber.setEnabledMultiSelection(false);

		if (companyNumber.getClientProperty("OrgDataChangeLisenter") == null) {
			OrgDataChangeLisenter ls = new OrgDataChangeLisenter(null);
			companyNumber.addDataChangeListener(ls);
			companyNumber.putClientProperty("OrgDataChangeLisenter", ls);
		}
		bgNumber = (KDBizPromptBox) this.getParamComponent("bgNumber");
		NewBgItemDialog bgItemDialog = (NewBgItemDialog) bgNumber.getSelector();
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
	}
	private class OrgDataChangeLisenter implements DataChangeListener {

		private CoreUIObject ui;

		public OrgDataChangeLisenter(CoreUIObject ui) {
			super();
			this.setUi(ui);
		}

		public void dataChanged(DataChangeEvent eventObj) {
			KDBizPromptBox box = (KDBizPromptBox) eventObj.getSource();
			if (box.getData() instanceof TreeBaseInfo) {

				TreeBaseInfo info = (TreeBaseInfo) box.getData();

				if (info != null) {
					SelectorItemCollection selectors = new SelectorItemCollection();
					selectors.add("id");
					selectors.add("number");
					selectors.add("longNumber");
					selectors.add("name");
					selectors.add("isLeaf");
					try {
						info = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(info.getId()), selectors);
					} catch (Exception e) {
						ExceptionHandler.handle(e);
					}
					if (!info.isIsLeaf()) {
						box.setUserObject(null);
						box.setText(null);
						MsgBox.showWarning(getUi(), FDCClientUtils.getRes("selectLeaf"));
					}
				}
			}
		}
		private void setUi(CoreUIObject ui) {
			this.ui = ui;
		}
		private CoreUIObject getUi() {
			return ui;
		}
	}
}
