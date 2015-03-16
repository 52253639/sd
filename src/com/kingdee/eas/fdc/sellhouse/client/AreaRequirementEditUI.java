/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementFactory;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class AreaRequirementEditUI extends AbstractAreaRequirementEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AreaRequirementEditUI.class);
    
    /**
     * output class constructor
     */
    public AreaRequirementEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.contDescription.setBoundLabelText("备注");
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
	}
    
	protected IObjectValue createNewData() {
		AreaRequirementInfo value = new AreaRequirementInfo();
		return value;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AreaRequirementFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}
	//因需求改了 所以重写 这个方法  改为名称可重复
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId()==null?null:this.editData.getId().toString(),CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number",txtNumber.getText()));
		if(AreaRequirementFactory.getRemoteInstance().exists(filter)){
			MsgBox.showError("编码不能重复！");
			abort();
		}
		// 名称是否为空
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(new FilterItemInfo("name", editData.getName()));
		if(STATUS_EDIT.equals(this.getOprtState()) && editData.getId() != null){
			filter1.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if(this.getBizInterface().exists(filter1)){
			MsgBox.showInfo("名称"+editData.getName()+"已经存在，不能重复。");
			abort();
		}
	}
	
}