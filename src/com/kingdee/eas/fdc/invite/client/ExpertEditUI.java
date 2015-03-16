/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.ExpertCollection;
import com.kingdee.eas.fdc.invite.ExpertFactory;
import com.kingdee.eas.fdc.invite.ExpertInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.ExpertTypeInfo;
import com.kingdee.eas.fdc.invite.InviteClarifyCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ExpertEditUI extends AbstractExpertEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpertEditUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    
    /**
     * output class constructor
     */
    public ExpertEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	ExpertTypeInfo type = editData.getExpertType();
        super.actionAddNew_actionPerformed(e);
    	this.prmtExpertType.setValue(type);
    }

    /**
     * 修改专家信息
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData!=null&&editData.getId()!=null&&editData.getOrgUnit()!=null){
	    	if(editData.getOrgUnit().getId().toString().equals(currentOrg.castToFullOrgUnitInfo().getId().toString()))
	    	{
	    		super.actionEdit_actionPerformed(e);
	    	}
	    	else
	    		FDCMsgBox.showWarning(this,"非本组织维护的专家，不能修改");
    	}
    		
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData!=null&&editData.getId()!=null&&editData.getOrgUnit()!=null){
	    	if(editData.getOrgUnit().getId().toString().equals(currentOrg.castToFullOrgUnitInfo().getId().toString()))
	    	{
	    		super.actionEdit_actionPerformed(e);
	    	}
	    	else
	    		FDCMsgBox.showWarning(this,"非本组织维护的专家，不能删除");
    	}
    	
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		
		ExpertInfo object = new ExpertInfo();
		ExpertTypeInfo typeInfo = (ExpertTypeInfo)this.getUIContext().get("type");
		object.setExpertType(typeInfo);
		object.setIsEnable(false);
		
		object.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo());
		
		return object;
	}
	public void onLoad() throws Exception {
		
		super.onLoad();
		boolean canAdd = currentOrg.isIsCompanyOrgUnit();
//		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
//			canAdd = true;
//		}
		this.actionAddNew.setEnabled(canAdd);
		this.actionRemove.setEnabled(canAdd);
		this.actionEdit.setEnabled(canAdd);
		this.actionCancel.setEnabled(canAdd);
		this.actionCancelCancel.setEnabled(canAdd);
		this.actionCancel.setVisible(canAdd);
		this.actionCancelCancel.setVisible(canAdd);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.prmtUser.setRequired(true);
		this.prmtExpertType.setEnabled(false);
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionSave.setVisible(false);
		
		
		
		if(editData.getId()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("expert.id",editData.getId().toString()));
			if(ExpertQualifyEntryFactory.getRemoteInstance().exists(filter)){
				this.prmtUser.setEnabled(false);
			}
		}
		
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.AUTHENTICATEADMIN_VALUE),CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.SYSTEM_VALUE),CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("type",UserType.OTHER_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number","user",CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("CU",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		view.setFilter(filter);
		this.prmtUser.setEntityViewInfo(view);
		this.txtName.setEnabled(false);
		
		
		//在新增时候把删除按扭变灰(Lanyuanjun 2009-09-24 17:19)
		if(getOprtState().equals(OprtState.ADDNEW)){
			this.actionRemove.setEnabled(false);
		}
		
		
		if(getOprtState().equals(OprtState.VIEW))
		{
			if(getUIContext().get("CAN_ADD") != null)
			{
				Boolean flag = (Boolean)getUIContext().get("CAN_ADD");
				this.actionAddNew.setEnabled(flag.booleanValue());
				this.actionEdit.setEnabled(flag.booleanValue());
				this.actionRemove.setEnabled(flag.booleanValue());
				this.actionEdit.setEnabled(true);
            }
			
		}
		else if(getOprtState().equals(OprtState.EDIT)){
			this.actionEdit.setEnabled(false);
			
		}
		
		this.prmtUser.setDisplayFormat("$number$");
		this.prmtUser.setEditFormat("$number$");
		this.prmtUser.setCommitFormat("$number$");
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		
		return ExpertFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"编码不能为空");
			abort();
		}
		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"请选择关联的用户");
			abort();
		}
		if(editData.getDescription()!=null&&editData.getDescription().length()>1000){
			FDCMsgBox.showWarning(this,"履历不能超过1000个字");
			abort();
		}
		
		/*if(!check())
		{
			abort();
		}*/
	}

	
	private boolean check() {
		boolean flag = true;
		
		String number = editData.getNumber();
		String typeId = "";
		if(editData.getExpertType() != null)
		{
			typeId = editData.getExpertType().getId().toString();
		}
		
		EntityViewInfo viewInfoNum = new EntityViewInfo();
		FilterInfo filterNum = new FilterInfo();
		viewInfoNum.getSelector().add(new SelectorItemInfo("number"));
		viewInfoNum.getSelector().add(new SelectorItemInfo("expertType"));
		
		filterNum.appendFilterItem("number", number);
		filterNum.appendFilterItem("expertType", typeId);
		viewInfoNum.setFilter(filterNum);
		
		try {
			ExpertCollection result = ExpertFactory.getRemoteInstance().getExpertCollection(viewInfoNum);
			
			if (this.getOprtState().equalsIgnoreCase("ADDNEW")) {
				if (result.size() > 0) {
					MsgBox.showInfo("专家编码已经存在");
					flag = false;
					return flag;
				}
			}
			if (this.getOprtState().equalsIgnoreCase("EDIT")) {
				if (this.editData.get("number").toString().equalsIgnoreCase(
						number)) {
					if (result.size() > 1) {
						MsgBox.showInfo("专家编码已经存在");
						flag = false;
						return flag;
					}
				}
				else {
					if (result.size() > 0) {
						MsgBox.showInfo("专家编码已经存在");
						flag = false;
						return flag;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		String id = editData.getUser().getId().toString();
		
		EntityViewInfo viewInfoId = new EntityViewInfo();
		FilterInfo filterId = new FilterInfo();
		viewInfoNum.getSelector().add(new SelectorItemInfo("user"));
		viewInfoNum.getSelector().add(new SelectorItemInfo("expertType"));
		
		filterNum.appendFilterItem("user", id);
		filterNum.appendFilterItem("expertType", typeId);
		viewInfoNum.setFilter(filterId);
		
		try {
			ExpertCollection result = ExpertFactory.getRemoteInstance().getExpertCollection(viewInfoId);
			
			if (this.getOprtState().equalsIgnoreCase("ADDNEW")) {
				if (result.size() > 0) {
					MsgBox.showInfo("专家用户名已经存在");
					flag = false;
					return flag;
				}
			}
			if (this.getOprtState().equalsIgnoreCase("EDIT")) {
				if (editData.getUser().getId().toString().equals(
						id)) {
					if (result.size() > 1) {
						MsgBox.showInfo("专家用户名已经存在");
						flag = false;
						return flag;
					}
				}
				else {
					if (result.size() > 0) {
						MsgBox.showInfo("专家用户名已经存在");
						flag = false;
						return flag;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public SelectorItemCollection getSelectors() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("expertType.*"));
        sic.add(new SelectorItemInfo("user.*"));
        return sic;
	}

	
	protected void prmtUser_dataChanged(DataChangeEvent e) throws Exception {
		
		/*	DMP BT361787 描述： 专家库 查看界面点删除的时候提示（2009-09-24 兰远军修改）
		 *  原因：删除专家时激活了prmtUser_dataChanged事件时引发的提示，现将提示关闭掉。
		 * 
		 * 
		 */
		if(!this.isFirstOnload()&&e.getNewValue()!=null && (!getOprtState().equals(OprtState.VIEW))){
			UserInfo oldUser = (UserInfo)e.getOldValue();
			UserInfo user = (UserInfo)e.getNewValue();
			if(oldUser!=null&&oldUser.getId().toString().equals(user.getId().toString()))
				return;
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("user",user.getId().toString()));
			if(editData.getExpertType()!=null)
				filter.getFilterItems().add(new FilterItemInfo("expertType",editData.getExpertType().getId().toString()));
			if(editData.getId()!=null)
				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString()));
			
			
			if(ExpertFactory.getRemoteInstance().exists(filter))
			{
				FDCMsgBox.showInfo(this,"此专家类别中，已经有专家关联了此用户，请重新选择！");
				this.prmtUser.setValue(e.getOldValue());
			}else{
				this.txtName.setText(user.getName());
			}
			
			/*if(getOprtState().equals(OprtState.ADDNEW))
			{
				if(ExpertFactory.getRemoteInstance().exists(filter))
				{
					FDCMsgBox.showInfo(this,"此专家类别中，已经有专家关联了此用户，请重新选择！");
					this.prmtUser.setValue(e.getOldValue());
				}else{
					this.txtName.setText(user.getName());
				}
			}
			else if(getOprtState().equals(OprtState.EDIT))
			{
				
			}*/
		}
		
	}

}