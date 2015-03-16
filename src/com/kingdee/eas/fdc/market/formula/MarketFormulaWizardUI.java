package com.kingdee.eas.fdc.market.formula;

import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;

public class MarketFormulaWizardUI extends FormulaWizardUI
{
	private KDBizPromptBox fdcProject;
	
	private KDDatePicker beginDate;

	private KDDatePicker endDate;

	public MarketFormulaWizardUI() throws Exception
	{
		super();
	}

	protected void onOpen()
	{

		fdcProject = (KDBizPromptBox) this.getParamComponent("fdcProject");
//		fdcProject.setEnabledMultiSelection(true);
		fdcProject.setRequired(true);

		

		//改变显示格式
		

		/*beginDate = (KDDatePicker) this.getParamComponent("begingDate");
		 beginDate.setSupportedEmpty(true);
		 beginDate.setVisible(false);*/
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 4134892076882436686L;

}
