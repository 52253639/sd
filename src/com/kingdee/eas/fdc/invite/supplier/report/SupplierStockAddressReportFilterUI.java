/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

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
public class SupplierStockAddressReportFilterUI extends AbstractSupplierStockAddressReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierStockAddressReportFilterUI.class);
    public SupplierStockAddressReportFilterUI() throws Exception
    {
        super();
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
         if(this.txtContractor.getText()!=null&&!"".equals(this.txtContractor.getText().trim())){
    		 pp.setObject("contractor", this.txtContractor.getText().trim());
         }else{
        	 pp.setObject("contractor", null);
         }
         if(this.txtContractorPhone.getText()!=null&&!"".equals(this.txtContractorPhone.getText().trim())){
    		 pp.setObject("contractorPhone", this.txtContractorPhone.getText().trim());
         }else{
        	 pp.setObject("contractorPhone", null);
         }
         if(this.txtManager.getText()!=null&&!"".equals(this.txtManager.getText().trim())){
    		 pp.setObject("manager", this.txtManager.getText().trim());
         }else{
        	 pp.setObject("manager", null);
         }
         if(this.txtManagerPhone.getText()!=null&&!"".equals(this.txtManagerPhone.getText().trim())){
    		 pp.setObject("managerPhone", this.txtManagerPhone.getText().trim());
         }else{
        	 pp.setObject("managerPhone", null);
         }
         if(this.txtPersonName.getText()!=null&&!"".equals(this.txtPersonName.getText().trim())){
    		 pp.setObject("personName", this.txtPersonName.getText().trim());
         }else{
        	 pp.setObject("personName", null);
         }
         if(this.txtPersonPhone.getText()!=null&&!"".equals(this.txtPersonPhone.getText().trim())){
    		 pp.setObject("personPhone", this.txtPersonPhone.getText().trim());
         }else{
        	 pp.setObject("personPhone", null);
         }
    	 pp.setObject("isPass", this.combIsPass.getSelectedItem());
    	 pp.setObject("isAll", this.combIsAll.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
	}
	public void clear() {
		this.txtName.setText(null);
		this.txtContractor.setText(null);
		this.txtContractorPhone.setText(null);
		this.txtManager.setText(null);
		this.txtManagerPhone.setText(null);
		this.txtPersonName.setText(null);
		this.txtPersonPhone.setText(null);
		
		this.combIsAll.setSelected(true);
	}
	public void setCustomCondition(RptParams params) {
		if(params.getObject("name")!=null){
			this.txtName.setText(params.getObject("name").toString());
		}
		if(params.getObject("contractor")!=null){
			this.txtContractor.setText(params.getObject("contractor").toString());
		}
		if(params.getObject("contractorPhone")!=null){
			this.txtContractorPhone.setText(params.getObject("contractorPhone").toString());
		}
		if(params.getObject("manager")!=null){
			this.txtManager.setText(params.getObject("manager").toString());
		}
		if(params.getObject("managerPhone")!=null){
			this.txtManagerPhone.setText(params.getObject("managerPhone").toString());
		}
		if(params.getObject("personName")!=null){
			this.txtPersonName.setText(params.getObject("personName").toString());
		}
		if(params.getObject("personPhone")!=null){
			this.txtPersonPhone.setText(params.getObject("personPhone").toString());
		}
		this.combIsPass.setSelectedItem(params.getObject("isPass"));
		this.combIsAll.setSelected(params.getBoolean("isAll"));
	}

}