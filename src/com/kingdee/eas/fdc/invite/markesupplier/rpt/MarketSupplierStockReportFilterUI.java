/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class MarketSupplierStockReportFilterUI extends AbstractMarketSupplierStockReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierStockReportFilterUI() throws Exception
    {
        super();
        prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketGradeSetUpQuery");
        
    }
    protected void combIsAll_itemStateChanged(ItemEvent e) throws Exception {
		if(this.combIsAll.isSelected()){
			this.combIsPass.setEnabled(false);
			this.combIsPass.setSelectedItem(null);
		}else{
			this.combIsPass.setEnabled(true);
		}
	}
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.txtName.getText()!=null&&!"".equals(this.txtName.getText().trim())){
    		 pp.setObject("name", this.txtName.getText().trim());
         }else{
        	 pp.setObject("name", null);
         }
         if(this.Visibility.getValue()!=null){
    		 pp.setObject("Visibility", this.Visibility.getValue());
         }else{
        	 pp.setObject("Visibility", null);
         }
         if(this.prmtGrade.getValue()!=null){
    		 pp.setObject("grade", this.prmtGrade.getValue());
         }else{
        	 pp.setObject("grade", null);
         }
         if(this.prmtCurProject.getValue()!=null){
    		 pp.setObject("curProject", this.prmtCurProject.getValue());
         }else{
        	 pp.setObject("curProject", null);
         }
    	 pp.setObject("wqht", this.combWQHT.isSelected());
    	 pp.setObject("wjs", this.combWJS.isSelected());
    	 pp.setObject("yjs", this.combYJS.isSelected());
    	 pp.setObject("isPass", this.combIsPass.getSelectedItem());
    	 pp.setObject("isAll", this.combIsAll.isSelected());
    	 pp.setObject("fromDate", this.pkFromDate.getValue());
    	 pp.setObject("toDate", this.pkToDate.getValue());
    	 pp.setObject("isAllContract", this.combIsAllContract.isSelected());
		 return pp;
	}

	public void clear() {
		this.txtName.setText(null);
		this.prmtCurProject.setValue(null);
		this.prmtGrade.setValue(null);
		this.Visibility.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", SysContext.getSysContext().getCurrentFIUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);
		this.prmtCurProject.setEntityViewInfo(view);
		
		this.combWQHT.setSelected(true);
		this.combWJS.setSelected(true);
		this.combYJS.setSelected(true);
		
		this.combIsAll.setSelected(true);
	}
	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams params) {
		if(params.getObject("name")!=null){
			this.txtName.setText(params.getObject("name").toString());
		}
		this.prmtGrade.setValue(params.getObject("grade"));
		this.Visibility.setValue(params.getObject("Visibility"));
		this.prmtCurProject.setValue(params.getObject("curProject"));
		this.combIsPass.setSelectedItem(params.getObject("isPass"));
		this.combIsAll.setSelected(params.getBoolean("isAll"));
		this.combWQHT.setSelected(params.getBoolean("wqht"));
		this.combWJS.setSelected(params.getBoolean("wjs"));
		this.combYJS.setSelected(params.getBoolean("yjs"));
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.combIsAllContract.setSelected(params.getBoolean("isAllContract"));
		
	}

}