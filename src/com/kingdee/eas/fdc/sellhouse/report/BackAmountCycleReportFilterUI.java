/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class BackAmountCycleReportFilterUI extends AbstractBackAmountCycleReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(BackAmountCycleReportFilterUI.class);
    public BackAmountCycleReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromActDate.setValue(null);
		this.pkFromSignDate.setValue(null);
		this.pkFromAppDate.setValue(null);
		this.pkToActDate.setValue(null);
		this.pkToSignDate.setValue(null);
		this.pkToAppDate.setValue(null);
	}
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.pkFromActDate.getValue()!=null){
    		 pp.setObject("fromActDate", this.pkFromActDate.getValue());
         }else{
        	 pp.setObject("fromActDate", null);
         }
         if(this.pkFromSignDate.getValue()!=null){
    		 pp.setObject("fromSignDate", this.pkFromSignDate.getValue());
         }else{
        	 pp.setObject("fromSignDate", null);
         }
         if(this.pkFromAppDate.getValue()!=null){
    		 pp.setObject("fromAppDate", this.pkFromAppDate.getValue());
         }else{
        	 pp.setObject("fromAppDate", null);
         }
         if(this.pkToActDate.getValue()!=null){
    		 pp.setObject("toActDate", this.pkToActDate.getValue());
         }else{
        	 pp.setObject("toActDate", null);
         }
         if(this.pkToSignDate.getValue()!=null){
    		 pp.setObject("toSignDate", this.pkToSignDate.getValue());
         }else{
        	 pp.setObject("toSignDate", null);
         }
         if(this.pkToAppDate.getValue()!=null){
    		 pp.setObject("toAppDate", this.pkToAppDate.getValue());
         }else{
        	 pp.setObject("toAppDate", null);
         }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromActDate.setValue(params.getObject("fromActDate"));
		this.pkFromSignDate.setValue(params.getObject("fromSignDate"));
		this.pkFromAppDate.setValue(params.getObject("fromAppDate"));
		this.pkToActDate.setValue(params.getObject("toActDate"));
		this.pkToSignDate.setValue(params.getObject("toSignDate"));
		this.pkToAppDate.setValue(params.getObject("toAppDate"));
	}

}