/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class SupplierStockAccountReportFilterUI extends AbstractSupplierStockAccountReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierStockAccountReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public SupplierStockAccountReportFilterUI() throws Exception
    {
        super();
        this.pkFromDate.setRequired(false);
        this.pkToDate.setRequired(false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public boolean verify() {
		if(this.pkFromDate.getValue() != null&&this.pkToDate.getValue()!=null){
			Date dateFrom = (Date) this.pkFromDate.getValue();
			Date dateTo = (Date) this.pkToDate.getValue();
			if(dateFrom.after(dateTo)){
				FDCMsgBox.showWarning(this,"结束日期必须大于开始日期！");
				return false;
			}
		}
		return true;
	}
    public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
		 if(this.pkFromDate.getValue()!=null){
   		 pp.setObject("fromDate", this.pkFromDate.getValue());
        }else{
       	 pp.setObject("fromDate", null);
        }
        if(this.pkToDate.getValue()!=null){
   		 pp.setObject("toDate", this.pkToDate.getValue());
        }else{
       	 pp.setObject("toDate", null);
        }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
	}
	public void clear() {
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
	}
}