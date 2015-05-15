/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;

/**
 * output class name
 */
public class WorkFlowReportFilterUI extends AbstractWorkFlowReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkFlowReportFilterUI.class);
    
    public WorkFlowReportFilterUI() throws Exception
    {
        super();
    }
	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
		Object[] person = (Object[])this.prmtPerson.getValue();
        if(person!=null ){
			pp.setObject("person",person);
		}else{
			pp.setObject("person",null);
		}
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
	public void onLoad() throws Exception {
		super.onLoad();
		EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox(this);
		person.setIsSingleSelect(false);
		person.showNoPositionPerson(false);
		person.setIsShowAllAdmin(true);
		this.prmtPerson.setSelector(person);
		this.prmtPerson.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
	}
	public boolean verify(){
        if(this.prmtPerson.getValue()==null){
            FDCMsgBox.showWarning("员工不能为空！");
            return false;
        }
        if(this.pkFromDate.getValue()==null){
            FDCMsgBox.showWarning("开始日期不能为空！");
            return false;
        }
        if(this.pkToDate.getValue()==null){
            FDCMsgBox.showWarning("结束日期不能为空！");
            return false;
        }
        return true;
    }
	public void onInit(RptParams arg0) throws Exception {
	}
	public void setCustomCondition(RptParams params) {
		this.prmtPerson.setValue(params.getObject("person"));
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
	}
}