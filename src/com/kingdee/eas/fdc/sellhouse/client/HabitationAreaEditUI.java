/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class HabitationAreaEditUI extends AbstractHabitationAreaEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(HabitationAreaEditUI.class);
    
    /**
     * output class constructor
     */
    public HabitationAreaEditUI() throws Exception
    {
        super();
    }

    
	public void storeFields() {
		super.storeFields();
	}
	

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.prmtParent.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HabitationAreaQuery");	
		this.prmtParent.setEditFormat("$number$");
		this.prmtParent.setDisplayFormat("$name$");
		this.prmtParent.setCommitFormat("$number$");

		
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.btnSave.setVisible(false);
		
		
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtSimpleName.setMaxLength(80);
		this.txtDescription.setMaxLength(80);
		
		//this.prmtParent.setEnabled(false);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}		
		
		if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))
		{
			this.prmtParent.setEnabled(false);
		}
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		
		
		this.storeFields();
		//this.initOldData(this.editData);
		
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//检查是否已被客户资料引用
		HabitationAreaInfo thisInfo = (HabitationAreaInfo)this.editData;
		if(thisInfo!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("habitationArea.id",thisInfo.getId().toString()));
			if(FDCCustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被客户资料引用，禁止删除!");
				return;
			}
		}
		
		super.actionRemove_actionPerformed(e);
	}


	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("编码必须录入。");
			return;
		}if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("名称不能为空。");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", this.txtName.getText()));
		if(editData.getParent() == null){
			filter.getFilterItems().add(new FilterItemInfo("parent is null"));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("parent", editData.getParent().getId().toString()));
		}
		if(STATUS_EDIT.equals(this.getOprtState()) && editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if(this.getBizInterface().exists(filter)){
			MsgBox.showInfo("名称"+this.txtName.getText()+"已经存在，不能重复。");
			return;
		}
		super.actionSubmit_actionPerformed(e);
//		this.storeFields();
//		this.initOldData(this.editData);		
	}


	protected IObjectValue createNewData() {
		return new HabitationAreaInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return HabitationAreaFactory.getRemoteInstance();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionEdit_actionPerformed(e);
		
		this.prmtParent.setEnabled(false);
	}

}