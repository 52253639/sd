/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;

import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.Uuid;
import com.kingdee.util.UuidException;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class HistoryMonthSellReportFilterUI extends AbstractHistoryMonthSellReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(HistoryMonthSellReportFilterUI.class);

	private static final String MONTH_FROM = "monthFrom";
	private static final String YEAR_FROM = "yearFrom";
	private static final String SHOWBY_PRODUCTTYPE = "showByProductType";
	private static final String SHOWBY_BUILDING = "showByBuilding";
    private static final String BALANCEID =  "balanceId"; 
    
    private static final String BEGINDATE = "beginDate";
    
    private static final String ENDDATE = "endDate";
	
    
    public HistoryMonthSellReportFilterUI() throws Exception
    {
        super();
    }

    private void initF7SaleBalance()
    {
    	Set periodIDSet = new HashSet();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(" select FPeriodID from t_she_salebalance where FOperateType = '"+ SaleBalanceTypeEnum.BALANCE_VALUE +"'");
    	IRowSet countSet = null;			
		try {
			countSet =  builder.executeQuery();
			while(countSet.next())
			{
				String periodID = countSet.getString("FPeriodID");		
				if(periodID!=null)
				{
					periodIDSet.add(periodID);
				}				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",periodIDSet,CompareType.INCLUDE));
		
		view.setFilter(filter);
		f7Balance.setEntityViewInfo(view);
    }
	public void clear() {
		
		this.radioProductType.setSelected(true);
		this.radioBuilding.setSelected(false);
		initControlDate();
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		this.initF7SaleBalance();
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
		
		Date beginDate = this.getBeginDate(para);
		Date endDate = this.getEndDate(para);
		
		//只做传递参数用
		filter.getFilterItems().add(new FilterItemInfo("BeginMonthDate",beginMonthQueryDate==null?null:new Timestamp(beginMonthQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("EndMonthDate",endMonthQueryDate==null?null:new Timestamp(endMonthQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("BeginYearDate",beginYearQueryDate==null?null:new Timestamp(beginYearQueryDate.getTime())));
		filter.getFilterItems().add(new FilterItemInfo("EndYearDate",endYearQueryDate==null?null:new Timestamp(endYearQueryDate.getTime())));
	
		filter.getFilterItems().add(new FilterItemInfo(SHOWBY_PRODUCTTYPE,new Integer(para.getBoolean(SHOWBY_PRODUCTTYPE)?1:0)));
		filter.getFilterItems().add(new FilterItemInfo(SHOWBY_BUILDING,new Integer(para.getBoolean(SHOWBY_BUILDING)?1:0)));
		
		filter.getFilterItems().add(new FilterItemInfo(BALANCEID,para.getString(BALANCEID)));
		filter.getFilterItems().add(new FilterItemInfo(BEGINDATE,beginDate));
		filter.getFilterItems().add(new FilterItemInfo(ENDDATE,endDate));
		
		return filter;
	}

	public Date getBeginDate(FDCCustomerParams para)
	{
		Date date = null;
		String begin = para.getString(BEGINDATE);
		
		try
		{
			date = DateTimeUtils.parseDate(begin, "yyyy-MM-dd");
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return date;
	}
	public Date getEndDate(FDCCustomerParams para)
	{
		Date date = null;
		String end = para.getString(ENDDATE);
		
		try
		{
			date = DateTimeUtils.parseDate(end, "yyyy-MM-dd");
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return date;
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
		
		param.add(SHOWBY_PRODUCTTYPE, this.radioProductType.isSelected());
		param.add(SHOWBY_BUILDING, this.radioBuilding.isSelected());
		
		Object obj = this.f7Balance.getValue();
		if(obj instanceof PeriodInfo)
		{
			PeriodInfo info = (PeriodInfo)obj;
			param.add(BALANCEID, info.getId().toString());
			param.add(BEGINDATE, info.getBeginDate().toString());
			param.add(ENDDATE, info.getEndDate().toString());
			
		}
		
		return param.getCustomerParams();
	}

	private void initControlDate() {
		SpinnerNumberModel yearMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
	}


	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		this.initControlDate();
		
		this.radioProductType.setSelected(para.getBoolean(SHOWBY_PRODUCTTYPE));
		this.radioBuilding.setSelected(para.getBoolean(SHOWBY_BUILDING));
		
		String id = para.getString(BALANCEID);
		
		try
		{
			PeriodInfo info = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(BOSUuid.read(id)));
			this.f7Balance.setValue(info);
		} catch (Exception e)
		{
			
		}
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	
	public boolean verify()
	{
		if(this.f7Balance.getValue() == null)
		{
			MsgBox.showWarning("请选择结算期间！");
			return false;
		}
		return true;
	}

    

}