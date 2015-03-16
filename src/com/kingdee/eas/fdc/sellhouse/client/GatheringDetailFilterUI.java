/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class GatheringDetailFilterUI extends AbstractGatheringDetailFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(GatheringDetailFilterUI.class);
    
    public GatheringDetailFilterUI() throws Exception
    {
        super();
    }


	private static final String MONTH_TO = "monthTo";
	private static final String MONTH_FROM = "monthFrom";
	private static final String YEAR_TO = "yearTo";
	private static final String YEAR_FROM = "yearFrom";
	private static final String DATE_TO = "dateTo";
	private static final String DATE_FROM = "dateFrom";
	private static final String DATE_TYPE = "dateType";
	
	private static final String IS_SHOW_ALL = "isShowAll";  //unUsed
	private static final String IS_INCLUDE_HISTORY = "includeHistory";
	
	
	private static final String IS_accFund="accFund";
	private static final String IS_commissionCharge="commissionCharge";
	private static final String IS_compensateAmount="compensateAmount";
	private static final String IS_elseAmount="elseAmount";
	private static final String IS_fisrtAmount="fisrtAmount";
	private static final String IS_fitmentAmount="fitmentAmount";
	private static final String IS_houseAmount="houseAmount";
	private static final String IS_loanAmount="loanAmount";
	private static final String IS_preconcertMoney="preconcertMoney";
	private static final String IS_preMoney="preMoney";
	private static final String IS_lateFee="lateFee";
	private static final String IS_replaceFee="replaceFee";
	private static final String IS_purchaseAmount="purchaseAmount";
	

	public void clear() {
		this.pkDateFrom.setValue(new Date());
		this.pkDateTo.setValue(new Date());
		this.radioByDay.setSelected(true);
		initControlByDateType();
		this.chkIsShowAll.setSelected(false);
		this.kdhistory.setSelected(false);
		this.kdaccFund.setSelected(false);
		this.kdcommissionCharge.setSelected(false);
		this.kdcompensateAmount.setSelected(false);
		this.kdelseAmount.setSelected(false);
		this.kdfisrtAmount.setSelected(false);
		this.kdfitmentAmount.setSelected(false);
		this.kdhouseAmount.setSelected(false);
		this.kdlateFee.setSelected(false);
		this.kdloanAmount.setSelected(false);
		this.kdpreconcertMoney.setSelected(false);
		this.kdpreMoney.setSelected(false);
		this.kdpurchaseAmount.setSelected(false);
		this.kdreplaceFee.setSelected(false);
		
	}
	
	public void onLoad() throws Exception {
		
		this.kDCheckBox2.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				selectAll();
				
			}});
		super.onLoad();
	}

	private void selectAll(){
		boolean isSelect = this.kDCheckBox2.isSelected();
		this.kdaccFund.setSelected(isSelect);
		this.kdcommissionCharge.setSelected(isSelect);
		this.kdcompensateAmount.setSelected(isSelect);
		this.kdelseAmount.setSelected(isSelect);
		this.kdfisrtAmount.setSelected(isSelect);
		this.kdfitmentAmount.setSelected(isSelect);
		this.kdhouseAmount.setSelected(isSelect);
		this.kdloanAmount.setSelected(isSelect);
		this.kdpreconcertMoney.setSelected(isSelect);
		this.kdpreMoney.setSelected(isSelect);
		this.kdlateFee.setSelected(isSelect);
		this.kdreplaceFee.setSelected(isSelect);
		this.kdpurchaseAmount.setSelected(isSelect);
	}
	
	
	
	
	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	
		if(!para.getBoolean(IS_SHOW_ALL)) {
			Date beginQueryDate = this.getBeginQueryDate(para);
			Date endQueryDate = this.getEndQueryDate(para);
				if(endQueryDate!=null) endQueryDate = FDCDateHelper.getNextDay(endQueryDate);    
			
				if(beginQueryDate!=null)				
					filter.getFilterItems().add(new FilterItemInfo("BeginQueryDate",new Timestamp(beginQueryDate.getTime()),CompareType.GREATER_EQUALS));
				if(endQueryDate!=null)
					filter.getFilterItems().add(new FilterItemInfo("EndQueryDate",new Timestamp(endQueryDate.getTime()), CompareType.LESS));
		}
		
		filter.getFilterItems().add(new FilterItemInfo(IS_accFund, new Boolean(this.kdaccFund.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_commissionCharge, new Boolean(this.kdcommissionCharge.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_compensateAmount, new Boolean(this.kdcompensateAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_elseAmount, new Boolean(this.kdelseAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_fisrtAmount, new Boolean(this.kdfisrtAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_fitmentAmount, new Boolean(this.kdfitmentAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_houseAmount, new Boolean(this.kdhouseAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_lateFee, new Boolean(this.kdlateFee.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_loanAmount, new Boolean(this.kdloanAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_preconcertMoney, new Boolean(this.kdpreconcertMoney.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_preMoney, new Boolean(this.kdpreMoney.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_purchaseAmount, new Boolean(this.kdpurchaseAmount.isSelected())));
		filter.getFilterItems().add(new FilterItemInfo(IS_replaceFee, new Boolean(this.kdreplaceFee.isSelected())));
			
		return filter;
	}

	public Date getBeginQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public Date getEndQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_TO);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}	

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if (this.radioByDay.isSelected()) {
			param.add(DATE_FROM, (Date) this.pkDateFrom.getValue());
			param.add(DATE_TO, (Date) this.pkDateTo.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radioByMonth.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radioByQuarter.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radioByYear.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(DATE_TYPE, 3);
		}
		param.add(IS_SHOW_ALL, this.chkIsShowAll.isSelected());		
		
		param.add(IS_INCLUDE_HISTORY, this.kdhistory.isSelected());				
	
		param.add(IS_accFund, this.kdaccFund.isSelected());
		param.add(IS_commissionCharge, this.kdcommissionCharge.isSelected());
		param.add(IS_compensateAmount, this.kdcompensateAmount.isSelected());
		param.add(IS_elseAmount, this.kdelseAmount.isSelected());
		param.add(IS_fisrtAmount, this.kdfisrtAmount.isSelected());
		param.add(IS_fitmentAmount, this.kdfitmentAmount.isSelected());
		param.add(IS_houseAmount, this.kdhouseAmount.isSelected());
		param.add(IS_lateFee, this.kdlateFee.isSelected());
		param.add(IS_loanAmount, this.kdloanAmount.isSelected());
		param.add(IS_preconcertMoney, this.kdpreconcertMoney.isSelected());
		param.add(IS_preMoney, this.kdpreMoney.isSelected());
		param.add(IS_purchaseAmount, this.kdpurchaseAmount.isSelected());
		param.add(IS_replaceFee, this.kdreplaceFee.isSelected());
		
		return param.getCustomerParams();
	}

	private void initControlByDateType() {
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

		if (this.chkIsShowAll.isSelected()) {
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


	protected void radioByDay_actionPerformed(ActionEvent e) throws Exception {
		super.radioByDay_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByMonth_actionPerformed(ActionEvent e) throws Exception {
		super.radioByMonth_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByQuarter_actionPerformed(ActionEvent e)
			throws Exception {
		super.radioByQuarter_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByYear_actionPerformed(ActionEvent e) throws Exception {
		super.radioByYear_actionPerformed(e);
		this.initControlByDateType();
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if (para.getInt(DATE_TYPE) == 0) {
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.radioByMonth.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.radioByQuarter.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.radioByYear.setSelected(true);
		}
		this.chkIsShowAll.setSelected(para.getBoolean(IS_SHOW_ALL));
		this.kdhistory.setSelected(para.getBoolean(IS_INCLUDE_HISTORY));
	
		if (para.getInt(DATE_TYPE) == 0) {
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.radioByMonth.setSelected(true);
		}else if (para.getInt(DATE_TYPE) == 2) {
			this.radioByQuarter.setSelected(true);
		}else if (para.getInt(DATE_TYPE) == 3) {
			this.radioByYear.setSelected(true);
		}
		this.initControlByDateType();
		
		if (para.getInt(DATE_TYPE) == 0) {
			this.pkDateFrom.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}
		
		this.kdaccFund.setSelected(para.getBoolean(IS_accFund));
		this.kdcommissionCharge.setSelected(para.getBoolean(IS_commissionCharge));
		this.kdcompensateAmount.setSelected(para.getBoolean(IS_compensateAmount));
		this.kdelseAmount.setSelected(para.getBoolean(IS_elseAmount));
		this.kdfisrtAmount.setSelected(para.getBoolean(IS_fisrtAmount));
		this.kdfitmentAmount.setSelected(para.getBoolean(IS_fitmentAmount));
		this.kdhouseAmount.setSelected(para.getBoolean(IS_houseAmount));
		this.kdlateFee.setSelected(para.getBoolean(IS_lateFee));
		this.kdloanAmount.setSelected(para.getBoolean(IS_loanAmount));
		this.kdpreconcertMoney.setSelected(para.getBoolean(IS_preconcertMoney));
		this.kdpreMoney.setSelected(para.getBoolean(IS_preMoney));
		this.kdpurchaseAmount.setSelected(para.getBoolean(IS_purchaseAmount));
		this.kdreplaceFee.setSelected(para.getBoolean(IS_replaceFee));
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginQueryDate = this.getBeginQueryDate(para);
		Date endQueryDate = this.getEndQueryDate(para);
		boolean isShowAll = para.getBoolean(IS_SHOW_ALL);
		if (beginQueryDate != null && endQueryDate != null) {
			if (endQueryDate.before(beginQueryDate)) {
				MsgBox.showWarning(this, "开始日期不能大于结束日期!");
				return false;
			}
		}else if(!isShowAll){
			MsgBox.showWarning(this, "日期不能为空!");
			return false;			
		}
		
			if(!isAccFundAmount(para) && !isCommissionCharge(para) && !isCompensateAmount(para)
					&& !isElseAmount(para) && !isFisrtAmount(para) && !isFitmentAmount(para)
					&& !isHouseAmount(para) && !isLateFee(para) && !isLoanAmount(para)
					&& !isPreMoney(para) && !ispurchaseAmount(para) && !isReplaceFee(para)
					&& !isPreconcertMoney(para)) {
					MsgBox.showWarning(this, "请至少选择一个款项类别!");
					return false;
			}
			
		
		return true;
	}

	protected void chkIsShowAll_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsShowAll_actionPerformed(e);
		this.initControlByDateType();
	}


    
	public boolean isAccFundAmount(FDCCustomerParams para){
		return para.getBoolean(IS_accFund);
	}
	public boolean isCommissionCharge(FDCCustomerParams para){
		return para.getBoolean(IS_commissionCharge);
	}
	public boolean isCompensateAmount(FDCCustomerParams para){
		return para.getBoolean(IS_compensateAmount);
	}
	public boolean isElseAmount(FDCCustomerParams para){
		return para.getBoolean(IS_elseAmount);
	}
	public boolean isFisrtAmount(FDCCustomerParams para){
		return para.getBoolean(IS_fisrtAmount);
	}
	public boolean isFitmentAmount(FDCCustomerParams para){
		return para.getBoolean(IS_fitmentAmount);
	}
	public boolean isHouseAmount(FDCCustomerParams para){
		return para.getBoolean(IS_houseAmount);
	}
	public boolean isLateFee(FDCCustomerParams para){
		return para.getBoolean(IS_lateFee);
	}
	public boolean isLoanAmount(FDCCustomerParams para){
		return para.getBoolean(IS_loanAmount);
	}
	public boolean isPreMoney(FDCCustomerParams para){
		return para.getBoolean(IS_preMoney);
	}
	
	public boolean ispurchaseAmount(FDCCustomerParams para){
		return para.getBoolean(IS_purchaseAmount);
	}
	public boolean isReplaceFee(FDCCustomerParams para){
		return para.getBoolean(IS_replaceFee);
	}
	public boolean isPreconcertMoney(FDCCustomerParams para){
		return para.getBoolean(IS_preconcertMoney);
	}
	
    
    
    
    
    
}