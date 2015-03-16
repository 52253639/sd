/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class MonthSellReportFilterUI extends AbstractMonthSellReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthSellReportFilterUI.class);

	private static final String IS_PRE_INTO_SALE = "isPreIntoSale";
	private static final String IS_INCLUDE_ATTACH = "isIncludeAttach";
	private static final String IS_SHOW_SPECIAL = "chkIsShowSpecial";
	private static final String MONTH_FROM = "monthFrom";
	private static final String YEAR_FROM = "yearFrom";
	private static final String SHOWBY_PRODUCTTYPE = "showByProductType";
	private static final String SHOWBY_BUILDING = "showByBuilding";
    
    
    public MonthSellReportFilterUI() throws Exception
    {
        super();
    }

	public void clear() {
		this.chkIsIncludeAttach.setSelected(false);
		this.chkIsPrePurchaseIntoSaleStat.setSelected(false);
		this.radioProductType.setSelected(true);
		this.radioBuilding.setSelected(false);
		initControlDate();
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginMonthQueryDate = this.getBeginMonthQueryDate(para);
		Date endMonthQueryDate = this.getEndMonthQueryDate(para);
		endMonthQueryDate = FDCDateHelper.getNextDay(endMonthQueryDate);    
		Date beginYearQueryDate = this.getBeginYearQueryDate(para);
		Date endYearQueryDate = this.getEndYearQueryDate(para);
		endYearQueryDate = FDCDateHelper.getNextDay(endYearQueryDate);   
		
		//只做传递参数用
		filter.getFilterItems().add(new FilterItemInfo("BeginMonthDate",beginMonthQueryDate==null?null:new Timestamp(beginMonthQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("EndMonthDate",endMonthQueryDate==null?null:new Timestamp(endMonthQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("BeginYearDate",beginYearQueryDate==null?null:new Timestamp(beginYearQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("EndYearDate",endYearQueryDate==null?null:new Timestamp(endYearQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo(IS_PRE_INTO_SALE,new Integer(para.getBoolean(IS_PRE_INTO_SALE)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(IS_INCLUDE_ATTACH,new Integer(para.getBoolean(IS_INCLUDE_ATTACH)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(IS_SHOW_SPECIAL,new Integer(para.getBoolean(IS_SHOW_SPECIAL)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(SHOWBY_PRODUCTTYPE,new Integer(para.getBoolean(SHOWBY_PRODUCTTYPE)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(SHOWBY_BUILDING,new Integer(para.getBoolean(SHOWBY_BUILDING)?1:0)));
		
		return filter;
	}

	public Date getBeginMonthQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
		cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
		cal.set(Calendar.DATE, 1);
		date = cal.getTime();

		return DateTimeUtils.truncateDate(date);
	}

	public Date getEndMonthQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
		cal.set(Calendar.MONTH, para.getInt(MONTH_FROM));
		cal.set(Calendar.DATE, 0);
		date = cal.getTime();

		return DateTimeUtils.truncateDate(date);
	}	
	
	public Date getBeginYearQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		date = cal.getTime();

		return DateTimeUtils.truncateDate(date);
	}	
	
	public Date getEndYearQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM) + 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 0);
		date = cal.getTime();

		return DateTimeUtils.truncateDate(date);
	}	
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue()).intValue());
		param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue()).intValue());
		
		param.add(IS_INCLUDE_ATTACH, this.chkIsIncludeAttach.isSelected());
		param.add(IS_PRE_INTO_SALE, this.chkIsPrePurchaseIntoSaleStat.isSelected());
		param.add(IS_SHOW_SPECIAL, this.chkIsShowSpecial.isSelected());
		
		param.add(SHOWBY_PRODUCTTYPE, this.radioProductType.isSelected());
		param.add(SHOWBY_BUILDING, this.radioBuilding.isSelected());
		
		return param.getCustomerParams();
	}

	private void initControlDate() {
		SpinnerNumberModel yearMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMoFrom);
		SpinnerNumberModel monthMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
		spiMonthFrom.setModel(monthMoFrom);
	}


	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		this.initControlDate();

		this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
		this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));

		this.chkIsIncludeAttach.setSelected(para.getBoolean(IS_INCLUDE_ATTACH));
		this.chkIsPrePurchaseIntoSaleStat.setSelected(para.getBoolean(IS_PRE_INTO_SALE));
		this.chkIsShowSpecial.setSelected(para.getBoolean(IS_SHOW_SPECIAL));
		
		this.radioProductType.setSelected(para.getBoolean(SHOWBY_PRODUCTTYPE));
		this.radioBuilding.setSelected(para.getBoolean(SHOWBY_BUILDING));
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	

    

}