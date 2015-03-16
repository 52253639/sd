/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class ContractPayPlanExeFilterUI extends AbstractContractPayPlanExeFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanExeFilterUI.class);
    
    public static final String CURRENCY_ID = "currencyId";
	public static final String DATE_TYPE = "dateType";
	public static final String MONTH_TO = "monthTo";
	public static final String MONTH_FROM = "monthFrom";
	public static final String YEAR_TO = "yearTo";
	public static final String YEAR_FROM = "yearFrom";
	
	protected ItemAction actionListOnLoad;
	
	private boolean isLoaded;

	protected ListUI listUI;
	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	
    /**
     * output class constructor
     */
    public ContractPayPlanExeFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
		
		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}
    
	public void clear() {
		
		this.radioByMonth.setSelected(true);
		initControlByDateType();
		prmtCurrency.setValue(null);
	}
	
	public FilterInfo getFilterInfo() {
		
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", null, CompareType.NOTEQUALS));

		if (para.isNotNull(DATE_TYPE)) {
					
		}
		
		if(para.isNotNull(CURRENCY_ID)) {
			filter.getFilterItems().add(new FilterItemInfo("currency.id", (String)para.get(CURRENCY_ID)));
		}				
			
		return filter;
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public Map getCustomCondition() {
    	return null;
    }
    
    /**
     * output radioByMonth_actionPerformed method
     */
    protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	initControlByDateType();
    }

    /**
     * output radioByQuarter_actionPerformed method
     */
    protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	initControlByDateType();
    }

    /**
     * output radioByYear_actionPerformed method
     */
    protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	initControlByDateType();
    }

    /**
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    
    }
    
	private void initControlByDateType() {
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);

		if (this.radioByMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);

			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} else if (this.radioByQuarter.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(true);
			this.lblQuarterTo.setVisible(true);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);

			int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
			SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthFrom.setModel(quarterMo1);
			SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthTo.setModel(quarterMo2);

		} else if (this.radioByYear.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);

		}

	}
	
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if(!isLoaded){
			initControlByDateType();
			isLoaded = true;
		}
		
		if (this.radioByMonth.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue()).intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radioByQuarter.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue()).intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radioByYear.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(DATE_TYPE, 3);
		}
		
		if(prmtCurrency.getValue() != null) {
			CurrencyInfo currency = (CurrencyInfo)prmtCurrency.getValue();
			param.add(CURRENCY_ID, currency.getId().toString());
		}
		
		return param.getCustomerParams();
	}

	public void setCustomerParams(CustomerParams  cp) {
		
		if(cp == null) return;
		isLoaded = true ;
		
		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.initControlByDateType();

		//设置方案
		if (para.getInt(DATE_TYPE) == 1) {
			radioByMonth.setSelected(true);
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2) {
			radioByQuarter.setSelected(true);
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3) {
			radioByYear.setSelected(true);
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}
		
		if(para.get(CURRENCY_ID) != null) {
			String currencyId = (String)para.get(CURRENCY_ID);
			CurrencyInfo currencyInfo = null;
			try {
				currencyInfo = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(currencyId));
			} catch (Exception e) {
				handUIException(e);
			} 
			prmtCurrency.setValue(currencyInfo);
		}
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	public QueryPanelInfo getQueryPanelInfo(QuerySolutionInfo querySolutionInfo, String panelClassName)
	{
		QueryPanelCollection queryPanelCollection = querySolutionInfo.getQueryPanelInfo();
		for (int i = 0, size = queryPanelCollection.size(); i < size; i++)
		{
			QueryPanelInfo queryPanelInfo = queryPanelCollection.get(i);
			if ( panelClassName.equals(queryPanelInfo.getPanelClassName()) )
			{
				return queryPanelInfo;
			}
		}
		return null;
	}
}