/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.UseTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCCusBaseDataEditUI extends AbstractFDCCusBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCusBaseDataEditUI.class);
    
    /**
     * output class constructor
     */
    public FDCCusBaseDataEditUI() throws Exception
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
		this.contGroup.setVisible(false);
		
//		if(this.editData.getGroup() != null  &&  UseTypeEnum.DanXuan.equals(this.editData.getGroup().getUseType())){
//			this.chkIsDefault.setEnabled(true);
//		}else{
//			this.chkIsDefault.setEnabled(false);
//		}
		if(this.editData.getGroup() != null && CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID.equals(this.editData.getGroup().getId().toString())){
			this.chkIsDefault.setEnabled(false);
		}else{
			this.chkIsDefault.setEnabled(true);
		}
		
	}

	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().equals("")) {
			MsgBox.showInfo(this, "客户基础资料编码不能为空!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().equals("")) {
			MsgBox.showInfo(this, "客户基础资料名称不能为空!");
			SysUtil.abort();
		}
	}

	protected IObjectValue createNewData() {
		FDCCusBaseDataGroupInfo groupInfo = (FDCCusBaseDataGroupInfo) this.getUIContext().get("GroupInfo");
		FDCCusBaseDataInfo info = new FDCCusBaseDataInfo();
		if(groupInfo!=null){
			info.setGroup(groupInfo);	
		}
		info.setIsEnabled(true);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCusBaseDataFactory.getRemoteInstance();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(this.editData.isIsEnabled()){
				MsgBox.showInfo(this, "已启用，不允许删除!");
				this.abort();
			}else if("IZ0jdIxGTO2yLHZ+AfoGFsRvOTM=".equals(editData.getId().toString())){
				MsgBox.showWarning(this, "默认数据，不允许删除!");
				this.abort();
			}else if(isUse(this.editData)){
				MsgBox.showWarning(this, "已被引用，不允许删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	private boolean isUse(FDCCusBaseDataInfo info) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateType.id", info.getId().toString()));
		view.setFilter(filter);
		SHECustomerCollection coll = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		FDCOrgCustomerCollection orgColl = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
        if((coll != null && coll.size()>0) || (orgColl != null && orgColl.size()>0)){
			return true;
		}
        return false;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(this.editData.isIsEnabled()){
				MsgBox.showInfo(this, "已启用，不允许修改!");
				this.abort();
			}else if("IZ0jdIxGTO2yLHZ+AfoGFsRvOTM=".equals(editData.getId().toString())){
				MsgBox.showWarning(this, "默认数据，不允许修改!");
				this.abort();
			}
			
		}
			/*
			String id = editData.getString("id");
			StringBuffer sb = new StringBuffer();
			sb.append("where ");
			sb.append("agePositioning.id='").append(id).append("' or ");
			sb.append("brandLevel.id='").append(id).append("' or ");
			sb.append("brandStyle.id='").append(id).append("' or ");
			sb.append("gradeOrientation.id='").append(id).append("' or ");
			sb.append("customerRevenuePositoin.id='").append(id).append("'");
			BrandInfoRegistrationCollection brs = BrandInfoRegistrationFactory.getRemoteInstance().getBrandInfoRegistrationCollection(sb.toString());
			
			sb=new StringBuffer();
			sb.append("where ownership.id='").append(id).append("'"); 
			BrandinfoRegistrationEntryCollection bres=BrandinfoRegistrationEntryFactory.getRemoteInstance().getBrandinfoRegistrationEntryCollection(sb.toString());
			if((brs!=null && brs.size()>0 && brs.get(0) instanceof BrandInfoRegistrationInfo) 
					|| (bres!=null && bres.size()>0 && bres.get(0) instanceof BrandinfoRegistrationEntryInfo)){
				MsgBox.showInfo("此数据已被引用,不能操作!");
				SysUtil.abort();
			} else {
				super.actionEdit_actionPerformed(e);
			}
			*/
		super.actionEdit_actionPerformed(e);
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) || actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand())) {
			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();

			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
				MsgBox.showInfo("当前系统组织不是集团,不能操作!");
				SysUtil.abort();
			}
		}
		super.beforeActionPerformed(e);
	}
	
	protected String getViewPermItemName() {
//		return "RetailAssistantData_view";
		return null;
	}
	
	/**
	 * 
	 * 描述：新增权限
	 */
	protected String getAddNewPermItemName() {
//		return "RetailAssistantData_addnew";
		return null;
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
    	s.add("*");
    	return s;
    }
    
}