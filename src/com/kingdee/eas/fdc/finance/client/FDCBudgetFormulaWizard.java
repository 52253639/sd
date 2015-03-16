package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

public class FDCBudgetFormulaWizard extends FormulaWizardUI {

	private static final long serialVersionUID = 9218636146710659277L;

	private static final Logger logger = Logger
			.getLogger(FDCBudgetFormulaWizard.class);

	// 成本中心
	private KDBizPromptBox costCenter;

	public FDCBudgetFormulaWizard() throws Exception {
		super();
		// TODO 自动生成构造函数存根
	}

	protected void onOpen() {
		costCenter = (KDBizPromptBox) this.getParamComponent("orgUnit");

		//改变显示格式
		final IFormatter formCostCenter = costCenter.getDisplayFormatter();
		IFormatter formatCostCenter = new IFormatter() {
			public void applyPattern(String pattern) {
				formCostCenter.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formCostCenter.valueToString(o) != null) {
					return formCostCenter.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};
		costCenter.setDisplayFormatter(formatCostCenter);
		costCenter.setEnabledMultiSelection(false);
//		 只能选择明细的，增加监听交验

		if (costCenter.getClientProperty("OrgDataChangeLisenter") == null) {
			OrgDataChangeLisenter ls = new OrgDataChangeLisenter(null);
			costCenter.addDataChangeListener(ls);
			costCenter.putClientProperty("OrgDataChangeLisenter", ls);
		}
		if(paramComponents.containsKey("periodStep")){
			KDFormattedTextField txtPeriodStep = (KDFormattedTextField)this.getParamComponent("periodStep");
			txtPeriodStep.setNegatived(true);
			
		}
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
						info = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(new ObjectUuidPK(info.getId()), selectors);
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
