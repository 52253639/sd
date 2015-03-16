/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class AppraiseGuideLineTypeEditUI extends AbstractAppraiseGuideLineTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseGuideLineTypeEditUI.class);
    
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    
    private AppraiseGuideLineTypeInfo parentInfo = null;
	private String strTemp = null;
	
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		boolean canAdd = false;
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
		if(!canAdd)
		{
			this.actionAddNew.setEnabled(canAdd);
			this.actionRemove.setEnabled(canAdd);
			this.actionEdit.setEnabled(canAdd);
		}
		
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		
		this.kDDescription.setMaxLength(200);
		
		txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				AppraiseGuideLineTypeInfo parent = (AppraiseGuideLineTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
				if (parent == null)
					return;
				if(getOprtState().equals(STATUS_ADDNEW))
				{
					String number = parent.getNumber().replace('!', '.')+".";
					if (!txtNumber.getText().startsWith(number)) {
						txtNumber.setText(number);
						txtNumber.setSelectionStart(number.length());
					}
				}
				else if(getOprtState().equals(STATUS_EDIT))
				{
					String number = parent.getNumber();
					number = number.replace('!', '.');
					if(number.lastIndexOf(".") > 0)
					{
						String parentNumber = number.substring(0,number.lastIndexOf("."))+".";
						if (!txtNumber.getText().startsWith(parentNumber)) {
							txtNumber.setText(parentNumber);
							txtNumber.setSelectionStart(parentNumber.length());
						}
					}
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		if(editData.getId()!=null&&!editData.isIsLeaf()){
			this.txtLongNumber.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
		
	}
    /**
     * output class constructor
     */
    public AppraiseGuideLineTypeEditUI() throws Exception
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
    	Map map = getUIContext();
		AppraiseGuideLineTypeInfo parentInfo = (AppraiseGuideLineTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("guideLineType.id",parentInfo.getId().toString()));
			if(AppraiseGuideLineFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类别已经有数据，不能在新增子类别");
				abort();
			}
		}
    	
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        if(editData.getId()!=null&&!editData.isIsLeaf()){
			this.txtLongNumber.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
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
		// TODO 自动生成方法存根
		AppraiseGuideLineTypeInfo object = new AppraiseGuideLineTypeInfo();
		Map map = getUIContext();
		AppraiseGuideLineTypeInfo parentInfo = (AppraiseGuideLineTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null)
			object.setParent(parentInfo);
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return AppraiseGuideLineTypeFactory.getRemoteInstance();
	}
	public void loadFields() {
		super.loadFields();
		
		parentInfo = (AppraiseGuideLineTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);
		if(getOprtState().equals(STATUS_ADDNEW))
		{
			if (getUIContext().get(UIContext.PARENTNODE) != null) {
				strTemp = parentInfo.getNumber();
				strTemp = strTemp.replace('!', '.');
				this.txtNumber.setText(strTemp + ".");
			}
			else
			{
				;
			}
		}
		else if(getOprtState().equals(STATUS_EDIT))
		{
			parentInfo = ((AppraiseGuideLineTypeInfo)(editData)).getParent();
			if(parentInfo == null)
			{
				this.txtNumber.setText(((AppraiseGuideLineTypeInfo)(editData)).getNumber());
			}
			else
			{
				strTemp = parentInfo.getNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".") > 0)
				{
					if (!txtNumber.getText().startsWith(strTemp)) {
						String parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
						txtNumber.setText(strTemp);
						txtNumber.setSelectionStart(parentNumber.length());
					}
				}
			}
		}
		else if(getOprtState().equals(STATUS_VIEW))
		{
			strTemp = this.editData.getNumber();
			strTemp = strTemp.replace('!', '.');
			this.txtNumber.setText(strTemp);
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.verifyInput(e);
		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"编码不能为空");
			abort();
		}
		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"名称不能为空");
			abort();
		}
		if(editData.getDescription()!=null&&editData.getDescription().length()>1000){
			FDCMsgBox.showWarning(this,"描述不能超过1000个字");
			abort();
			
		}
	}
	
	 /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic =  super.getSelectors();
    	sic.add(new SelectorItemInfo("longNumber"));
    	sic.add(new SelectorItemInfo("isLeaf"));
    	
    	return sic ;
    }     
    
    public void onShow() throws Exception
	{
		super.onShow();
		this.txtNumber.requestFocus();
	}
    
    protected void initOldData(IObjectValue dataObject) {
//		parentInfo = (AppraiseGuideLineTypeInfo) getUIContext().get(UIContext.PARENTNODE);
//		if (getUIContext().get(UIContext.PARENTNODE) != null) {
//			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
//				String strTemp = parentInfo.getLongNumber();
//				strTemp = strTemp.replace('!', '.');
//				((AppraiseGuideLineTypeInfo) dataObject).setLongNumber(strTemp + ".");
//			} else if (STATUS_EDIT.equals(getOprtState())) {
//				if(dataObject != null)
//				{
//					String strTemp = ((AppraiseGuideLineTypeInfo) dataObject).getLongNumber();
//					strTemp = strTemp.replace('!', '.');
//					((AppraiseGuideLineTypeInfo) dataObject).setLongNumber(strTemp);
//				}
//			}
//		}
		super.initOldData(dataObject);
	}

}