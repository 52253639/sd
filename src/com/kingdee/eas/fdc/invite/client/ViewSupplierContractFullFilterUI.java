package com.kingdee.eas.fdc.invite.client;

import java.util.Calendar;
import java.util.Map;

import javax.swing.SpinnerNumberModel;

import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.fdc.contract.client.ContractFullFilterUI;
import com.kingdee.eas.framework.client.ListUI;

public class ViewSupplierContractFullFilterUI extends ContractFullFilterUI {
	
	public Map uiContext;

	public ViewSupplierContractFullFilterUI(ListUI listUI,Map uiContext, ItemAction actionListOnLoad) throws Exception {
		super(listUI, actionListOnLoad);
		this.uiContext = uiContext;
		// TODO 自动生成构造函数存根
	}
	public void setCustomerParams(CustomerParams  cp){
		super.setCustomerParams(cp);
		
		SupplierInfo supplier = (SupplierInfo)this.getUIContext().get("supplier"); 
		if(supplier!=null)
			this.f7Provider.setValue(supplier);
	}
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		if(uiContext!=null){
			SupplierInfo supplier = (SupplierInfo)uiContext.get("supplier"); 
			
			if(supplier!=null)
			{
				this.f7Provider.setValue(supplier);
				this.f7Provider.setEnabled(false);
			}
		}
	}
	public void clear() {
		super.clear();
		if(uiContext!=null){
			SupplierInfo supplier = (SupplierInfo)uiContext.get("supplier"); 
			
			if(supplier!=null)
			{
				this.f7Provider.setValue(supplier);
				this.f7Provider.setEnabled(false);
			}
		}
		this.chkAllContract.setSelected(true);
//		super.initControlByDateType();
		//
		
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);

		if (this.radioByDay.isSelected()) {
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.radioByMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
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
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
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
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
		}

		if (this.chkAllContract.isSelected()) {
			this.radioByDay.setEnabled(false);
			this.radioByMonth.setEnabled(false);
			this.radioByQuarter.setEnabled(false);
			this.radioByYear.setEnabled(false);
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
			this.pkDateFrom.setEnabled(false);
			this.pkDateTo.setEnabled(false);
		} else {
			this.radioByDay.setEnabled(true);
			this.radioByMonth.setEnabled(true);
			this.radioByQuarter.setEnabled(true);
			this.radioByYear.setEnabled(true);
			this.plDateType.setEnabled(true);
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
			this.pkDateFrom.setEnabled(true);
			this.pkDateTo.setEnabled(true);
		}
		
	}

}
