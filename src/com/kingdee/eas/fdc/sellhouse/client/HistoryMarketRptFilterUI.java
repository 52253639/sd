/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class HistoryMarketRptFilterUI extends AbstractHistoryMarketRptFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(HistoryMarketRptFilterUI.class);
    protected ListUI listUI;
	protected ItemAction actionListOnLoad;
	
	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

    public HistoryMarketRptFilterUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	this.f7SaleBalance.setRequired(true);
    	initF7SaleBalance();
    	super.onLoad();
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
		f7SaleBalance.setEntityViewInfo(view);
    }
    
    public HistoryMarketRptFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
    }

    public void storeFields()
    {
        super.storeFields();
    }

    public boolean verify() {
    	if(this.f7SaleBalance.getValue()==null)
    	{
    		MsgBox.showInfo("月结期间不能为空!");
    		return false;
    	}else
    	{
    		PeriodInfo periodInfo = (PeriodInfo)this.f7SaleBalance.getValue();
        	Date startDate = (Date)this.pkDateFrom.getValue();
        	Date endDate = (Date)this.pkDateTo.getValue();
        	
        	if(startDate.after(periodInfo.getEndDate()) && startDate.equals(periodInfo.getEndDate())==false)
        	{
        		MsgBox.showInfo("结算开始时间不能大于月结期间结束时间");
        		return false;
        	}
        	if(startDate.after(endDate) && startDate.equals(endDate)==false)
        	{
        		MsgBox.showInfo("结算开始时间不能大于结算结束时间");
        		return false;
        	}
        	if(startDate.before(periodInfo.getBeginDate()))
        	{
        		MsgBox.showInfo("结算开始时间不能小于月结期间开始时间");
        		return false;
        	}
        	if(endDate.before(startDate) && endDate.equals(startDate)==false)
        	{
        		MsgBox.showInfo("结算结束时间不能小于月结期间开始时间");
        		return false;
        	}
        	if(endDate.after(periodInfo.getEndDate()))
        	{
        		MsgBox.showInfo("结算结束时间不能大于月结期间结束时间");
        		return false;
        	}
    	} 	
		return true;
	}
    
    protected void f7SaleBalance_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	PeriodInfo periodInfo = (PeriodInfo)this.f7SaleBalance.getValue();
    	if(periodInfo!=null) {
	    	this.pkDateFrom.setValue(periodInfo.getBeginDate());
	    	this.pkDateTo.setValue(periodInfo.getEndDate());
    	}
        super.f7SaleBalance_dataChanged(e);
    }
    
    public Date getDateFrom()
    {
    	 Date startDate = (Date)this.pkDateFrom.getValue();
    	 return startDate;
    }
    
    public Date getDateTo()
    {
    	Date endDate = (Date)this.pkDateTo.getValue();
    	return endDate;
    }
    
    public PeriodInfo getPeriod() {
    	PeriodInfo periodInfo = (PeriodInfo)this.f7SaleBalance.getValue();
    	return periodInfo;
    }
    
    protected void pkDateFrom_dataChanged(DataChangeEvent e) throws Exception {
    	super.pkDateFrom_dataChanged(e);  	
    }
    
    protected void pkDateTo_dataChanged(DataChangeEvent e) throws Exception {
    	super.pkDateTo_dataChanged(e);
    }

}