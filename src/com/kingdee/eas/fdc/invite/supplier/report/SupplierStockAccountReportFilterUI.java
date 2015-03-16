/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class SupplierStockAccountReportFilterUI extends AbstractSupplierStockAccountReportFilterUI
{
	public boolean verify() {
		if (this.pkFromDate.getValue() == null) {
			FDCMsgBox.showWarning(this,"开始日期不能为空");
			this.pkFromDate.requestFocus();
			return false;
		}
		if (this.pkToDate.getValue() == null) {
			FDCMsgBox.showWarning(this,"结束日期不能为空");
			this.pkToDate.requestFocus();
			return false;
		}
		Date dateFrom = (Date) this.pkFromDate.getValue();
		Date dateTo = (Date) this.pkToDate.getValue();
		if(dateFrom.after(dateTo)){
			FDCMsgBox.showWarning(this,"结束日期必须大于开始日期！");
			return false;
		}
		return true;
	}
	private static final Logger logger = CoreUIObject.getLogger(SupplierStockAccountReportFilterUI.class);
    public SupplierStockAccountReportFilterUI() throws Exception
    {
        super();
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