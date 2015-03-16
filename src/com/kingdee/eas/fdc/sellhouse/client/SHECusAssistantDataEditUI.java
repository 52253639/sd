/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHECusAssistantDataEditUI extends AbstractSHECusAssistantDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHECusAssistantDataEditUI.class);
    private FullOrgUnitInfo orgUnit = SysContext.getSysContext()
	.getCurrentOrgUnit().castToFullOrgUnitInfo();
    
    /**
     * output class constructor
     */
    public SHECusAssistantDataEditUI() throws Exception
    {
        super();
		// 初始化设置按钮隐藏，显示
		actionSave.setVisible(false);
		actionCopy.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		menuBiz.setVisible(false);
		prmtGroup.setEnabled(false);
		txtNumber.setRequired(true);
		txtName.setRequired(true);
		txtNumber.setMaxLength(50);
		txtName.setMaxLength(50);
		txtDescription.setMaxLength(255);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		this.setSize(290, 177);
    }

    public void onLoad() throws Exception {
		super.onLoad();
		txtNumber.setMaxLength(50);
		txtName.setMaxLength(50);
		txtDescription.setMaxLength(255);
		
		btnCancelCancel.setVisible(false);
		actionCancel.setVisible(false);
		btnCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		
		this.f7Project.setEnabled(false);
		
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().equals("")) {
			MsgBox.showInfo(this, "辅助资料编码不能为空!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().equals("")) {
			MsgBox.showInfo(this, "辅助资料名称不能为空!");
			SysUtil.abort();
		}
	}

	protected IObjectValue createNewData() {
		SHECusAssistantDataGroupInfo groupInfo = (SHECusAssistantDataGroupInfo) this.getUIContext().get("GroupInfo");
		SHECusAssistantDataInfo info = new SHECusAssistantDataInfo();
		if(groupInfo!=null){
			info.setGroup(groupInfo);
		}
		
		SellProjectInfo pro = (SellProjectInfo) this.getUIContext().get("sellProject");
		if(pro != null){
			info.setProject(pro);
		}
		/**
		 * 添加创建组织字段
		 * add by renliang 2011-9-2
		 */
		if(this.orgUnit!=null){
			info.setOrgUnit(this.orgUnit);
		}
		
		info.setIsEnabled(true);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECusAssistantDataFactory.getRemoteInstance();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(editData.isIsEnabled()){
				MsgBox.showWarning(this, "已启用，不允许删除!");
				this.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		this.f7Project.setEnabled(false);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(editData.isIsEnabled()){
				MsgBox.showWarning(this, "已启用，不允许删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) 
				|| actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand()) 
				|| actionCancel.getClass().getName().equals(e.getActionCommand())
				|| actionCancelCancel.getClass().getName().equals(e.getActionCommand())) {
			
			FDCSysContext.getInstance().checkIsSHEOrg(this);
		}
		super.beforeActionPerformed(e);
	}
	
	protected String getViewPermItemName() {
//		return "RetailAssistantData_view";
		return "";
	}
	/**
	 * 
	 * 描述：新增权限
	 */
	protected String getAddNewPermItemName() {
//		return "RetailAssistantData_addnew";
		return "";
	}
	
	protected final String getOnloadPermItemName() {
		String state = getOprtState();
		if (state.equals(OprtState.ADDNEW)) {
			return getAddNewPermItemName();
		}
		return getViewPermItemName();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection s = super.getSelectors();
		s.add("isEnabled");
		
		return s;
	}
    
}