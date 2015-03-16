/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class MarketAccountViewReportFilterUI extends AbstractMarketAccountViewReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketAccountViewReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public MarketAccountViewReportFilterUI() throws Exception
    {
        super();
		this.spinBeginYear.setValue(new Integer(DateTimeUtils.getYear(new Date())));
		this.spinEndYear.setValue(new Integer(DateTimeUtils.getYear(new Date())));
		this.yearRadioButton.setSelected(true);
		if(this.yearRadioButton.isSelected()){
			this.spinBeginMonth.setEnabled(false);
			this.spinEndMonth.setEnabled(false);
		}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
		//开始年
		if(this.spinBeginYear.getValue()!=null){
			pp.setObject("beginYear", this.spinBeginYear.getValue());
		}else{
			pp.setObject("beginYear", null);
		}
		//开始月
		if(this.spinBeginMonth.getValue()!=null){
			pp.setObject("beginMonth", this.spinBeginMonth.getValue());
		}else{
			pp.setObject("beginMonth", null);
		}
		//结束年
		if(this.spinEndYear.getValue()!=null){
			pp.setObject("endYear", this.spinEndYear.getValue());
		}else{
			pp.setObject("endYear", null);
		}
		//结束月
		if(this.spinEndMonth.getValue()!=null){
			pp.setObject("endMonth", this.spinEndMonth.getValue());
		}else{
			pp.setObject("endMonth", null);
		}
		//按年
		if(this.yearRadioButton.isSelected()){
			pp.setObject("yearOrMonth", "year");
		}
		//按月
		if(this.monthRadioButton.isSelected()){
			pp.setObject("yearOrMonth", "month");
		}
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams rptparams) {
		if(rptparams.getObject("beginYear")!=null){
			this.spinBeginYear.setValue(rptparams.getObject("beginYear"));
		}
		if(rptparams.getObject("beginMonth")!=null){
			this.spinBeginMonth.setValue(rptparams.getObject("beginMonth"));
		}
		if(rptparams.getObject("endYear")!=null){
			this.spinEndYear.setValue(rptparams.getObject("endYear"));
		}
		if(rptparams.getObject("endMonth")!=null){
			this.spinEndMonth.setValue(rptparams.getObject("endMonth"));
		}
		if(rptparams.getString("yearOrMonth")!=null){
			if(rptparams.getString("yearOrMonth").equals("year")) {
				this.yearRadioButton.setSelected(true);
			}else{
				this.monthRadioButton.setSelected(true);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.market.report.AbstractMarketAccountViewReportFilterUI#yearRadioButton_actionPerformed(java.awt.event.ActionEvent)
	 */
	protected void yearRadioButton_actionPerformed(ActionEvent e) throws Exception {
		super.yearRadioButton_actionPerformed(e);
		if(this.yearRadioButton.isSelected()){
			this.spinBeginMonth.setEnabled(false);
			this.spinEndMonth.setEnabled(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.market.report.AbstractMarketAccountViewReportFilterUI#monthRadioButton_actionPerformed(java.awt.event.ActionEvent)
	 */
	protected void monthRadioButton_actionPerformed(ActionEvent e) throws Exception {
		super.monthRadioButton_actionPerformed(e);
		if(this.monthRadioButton.isSelected()){
			this.spinBeginMonth.setEnabled(true);
			this.spinBeginMonth.setValue(new Integer(DateTimeUtils.getMonth(new Date())));
			this.spinEndMonth.setEnabled(true);
			this.spinEndMonth.setValue(new Integer(DateTimeUtils.getMonth(new Date())));
		}
	}

}