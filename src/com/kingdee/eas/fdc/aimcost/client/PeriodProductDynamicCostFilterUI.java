/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import sun.util.calendar.CalendarDate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PeriodProductDynamicCostFilterUI extends AbstractPeriodProductDynamicCostFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(PeriodProductDynamicCostFilterUI.class);
    public final static String YEAR="year";
    public final static String MONTH="month";
    public final static String ISCHECK="isCheck";
    
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	
    public PeriodProductDynamicCostFilterUI() throws Exception
    {
        super();
        pkdate = FDCClientHelper.getCompanyCurrentDate();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	clear();
    }
    
	public void clear()
	{
//    	Calendar calc=Calendar.getInstance();
//    	int year=calc.get(Calendar.YEAR);
//    	int month=calc.get(Calendar.MONTH)+1;
    	
		Date curDate = pkdate[0];
		int year = curDate.getYear()+1900;
		int month = curDate.getMonth()+1;
		
    	SpinnerNumberModel model=new SpinnerNumberModel(year,1900,9999,1);
    	spYear.setModel(model);
    	model=new SpinnerNumberModel(month,1,12,1);
    	spMonth.setModel(model);
    	chkPeriod.setSelected(false);
    	try {
			chkPeriod_actionPerformed(null);
		} catch (Exception e) {
			handUIException(e);
		}
	}
    
    public FilterInfo getFilterInfo() {
    	FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
    	int year=para.getInt(YEAR);
    	int month=para.getInt(MONTH);
    	boolean isCheck=para.getBoolean(ISCHECK);
    	FilterInfo filter=new  FilterInfo();
    	filter.appendFilterItem("year", new Integer(year));
    	filter.appendFilterItem("month", new Integer(month));
    	filter.appendFilterItem("isCheck", Boolean.valueOf(isCheck));
    	return filter;
    }
    
    public void setCustomerParams(CustomerParams cp) {
    	super.setCustomerParams(cp);
    	FDCCustomerParams para = new FDCCustomerParams(cp);
    	spYear.setValue(new Integer(para.getInt(YEAR)));
    	spMonth.setValue(new Integer(para.getInt(MONTH)));
    	chkPeriod.setSelected(para.getBoolean(ISCHECK));
    	try {
			chkPeriod_actionPerformed(null);
		} catch (Exception e) {
			handUIException(e);
		}
    }
    public CustomerParams getCustomerParams() {
    	FDCCustomerParams para = new FDCCustomerParams();
    	para.add(YEAR, ((Integer)spYear.getValue()).intValue());
    	para.add(MONTH, ((Integer)spMonth.getValue()).intValue());
    	para.add(ISCHECK, chkPeriod.isSelected());
    	return para.getCustomerParams();
    }
    
    /**
     * output chkPeriod_actionPerformed method
     */
    protected void chkPeriod_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.chkPeriod_actionPerformed(e);
        if(chkPeriod.isSelected()){
        	spMonth.setEnabled(true);
        	spYear.setEnabled(true);
        }else{
        	spMonth.setEnabled(false);
        	spYear.setEnabled(false);
        }
    }
    
    public PeriodInfo getPeriod() throws EASBizException, BOSException{
    	PeriodInfo periodInfo=null;
    	FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
    	
    	if(para.getBoolean(ISCHECK)){
    		int year=para.getInt(YEAR);
    		int month=para.getInt(MONTH)-1;
    		Calendar calc=Calendar.getInstance();
    		calc.set(year, month, 1);
    		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
    		if(currentFIUnit==null||currentFIUnit.getId()==null){
    			return null;
    		}
    		SelectorItemCollection selector=new SelectorItemCollection();
    		selector.add("accountPeriodType.id");
			CompanyOrgUnitInfo companyInfo = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(currentFIUnit.getId()), selector);
			
    		periodInfo = FDCUtils.fetchPeriod(null, companyInfo.getAccountPeriodType().getId().toString(), calc.getTime());
    		if(periodInfo==null){
    			MsgBox.showWarning(this, "所选期间不存在");
    			SysUtil.abort();
    		}
    	}
    	//先判断所选期间是否有月结数据

    	return periodInfo;
    }
    
    public static PeriodInfo getPeriod(FilterInfo filter) throws EASBizException, BOSException{
    	PeriodInfo periodInfo=null;
    	int year=0;
    	int month=0;
    	boolean isCheck=false;
    	for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
    		FilterItemInfo item=(FilterItemInfo)iter.next();
    		if(item.getPropertyName().equals("year")&&(item.getCompareValue() instanceof Integer)){
    			year=((Integer)item.getCompareValue()).intValue();
    		}
    		if(item.getPropertyName().equals("month")&&(item.getCompareValue() instanceof Integer)){
    			month=((Integer)item.getCompareValue()).intValue();
    		}
    		if(item.getPropertyName().equals("isCheck")&&(item.getCompareValue() instanceof Integer)){
    			isCheck=((Integer)item.getCompareValue()).intValue()==1;
    		}
    	}
    	
    	if(!isCheck||year==0){
    		return null;
    	}
    	

		Calendar calc=Calendar.getInstance();
		calc.set(year, month-1, 1);
		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit==null||currentFIUnit.getId()==null){
			return null;
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("accountPeriodType.id");
		CompanyOrgUnitInfo companyInfo = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(currentFIUnit.getId()), selector);
		
		periodInfo = FDCUtils.fetchPeriod(null, companyInfo.getAccountPeriodType().getId().toString(), calc.getTime());
		if(periodInfo==null){
			MsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "所选期间不存在");
			SysUtil.abort();
		}
	
    	return periodInfo;
    }
}