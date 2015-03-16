/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProjectPlanFilterUI extends AbstractProjectPlanFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectPlanFilterUI.class);
    
    
    public ProjectPlanFilterUI() throws Exception
    {
        super();
    }
	public void clear() {
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		if(month+1>12){
			year=year+1;
			month=1;
		}else{
			month=month+1;
		}
		this.spYear.setValue(year);
		this.spMonth.setValue(month);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.cbIsAll.setSelected(false);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		if(month+1>12){
			year=year+1;
			month=1;
		}else{
			month=month+1;
		}
		this.spYear.setValue(year);
		this.spMonth.setValue(month);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.cbIsAll.setSelected(false);
	}
	public void setCustomerParams(CustomerParams cp)
	{
		FDCCustomerParams para=new FDCCustomerParams(cp);
		if(para.get("year")!=null){
			this.spYear.setValue(para.getInt("year"));
		}
		if(para.get("month")!=null){
			this.spMonth.setValue(para.getInt("month"));
		}
		this.cbIsAll.setSelected(para.getBoolean("all"));
		super.setCustomerParams(cp);
	}
	public CustomerParams getCustomerParams()
	{
		FDCCustomerParams param=new FDCCustomerParams();
		param.add("year", this.spYear.getIntegerVlaue());
		param.add("month", this.spMonth.getIntegerVlaue());
		param.add("all", this.cbIsAll.isSelected());
		return param.getCustomerParams();
	}
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		if(para.get("year")!=null&&para.get("month")!=null&&!para.getBoolean("all")){
			int year=Integer.parseInt(para.get("year").toString());
			int month=Integer.parseInt(para.get("month").toString());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month-1);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			filter.getFilterItems().add(new FilterItemInfo("bizDate",cal.getTime()));
		}
		return filter;
	}
	protected void cbIsAll_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbIsAll.isSelected()){
			this.spYear.setEnabled(false);
			this.spMonth.setEnabled(false);
		}else{
			this.spYear.setEnabled(true);
			this.spMonth.setEnabled(true);
		}
	}
	
}