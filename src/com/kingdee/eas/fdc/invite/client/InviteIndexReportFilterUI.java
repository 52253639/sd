/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class InviteIndexReportFilterUI extends AbstractInviteIndexReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteIndexReportFilterUI.class);
    public InviteIndexReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.pkFromDate.setRequired(true);
		this.pkToDate.setRequired(true);
	}
    public boolean verify()
    {
        if(this.pkFromDate.getValue()==null)
        {
            FDCMsgBox.showWarning("开始日期不能为空！");
            return false;
        }
        if(this.pkToDate.getValue()==null)
        {
            FDCMsgBox.showWarning("结束日期不能为空！");
            return false;
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
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
	}
}