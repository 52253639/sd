/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ExpertTypeInfo;
import com.kingdee.eas.fdc.invite.TemplateFileFactory;
import com.kingdee.eas.fdc.invite.TemplateTypeFactory;
import com.kingdee.eas.fdc.invite.TemplateTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class TemplateTypeEditUI extends AbstractTemplateTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TemplateTypeEditUI.class);
    
    private TemplateTypeInfo parentInfo = null;
	private String strTemp = null;
	
    /**
     * output class constructor
     */
    public TemplateTypeEditUI() throws Exception
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
 
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic =  super.getSelectors();
    	sic.add("isLeaf");
    	return sic ;
    } 
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.txtName.setRequired(true);
    	this.txtNumber.setRequired(true);
    	
    	initHeadStyle();
    	
    	txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				TemplateTypeInfo parent = (TemplateTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
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
			
			this.txtNumber.setEnabled(false);
		}
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(editData.getId()!=null&&!editData.isIsLeaf()){
			
			this.txtNumber.setEnabled(false);
		}
	}

	protected void initHeadStyle()
	{
		this.contParent.setEnabled(false);
		this.prmtParent.setEnabled(false);
		
		this.prmtParent.setDisplayFormat("$number$");
		
		prmtParent.setQueryInfo("com.kingdee.eas.fdc.invite.app.TemplateTypeQuery");
		
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);

		if(getUIContext().get("CAN_ADD") != null)
		{
			Boolean canAdd = (Boolean)(getUIContext().get("CAN_ADD") );
			if(!canAdd.booleanValue())
			{
				this.actionEdit.setEnabled(false);
				this.actionAddNew.setEnabled(false);
				this.actionRemove.setEnabled(false);
				this.actionSubmit.setEnabled(false);
			}
			else
			{
				if(getOprtState().equals(OprtState.VIEW))
				{
					this.contNumber.setEnabled(false);
					this.txtNumber.setEnabled(false);
					
					this.contName.setEnabled(false);
					this.txtName.setEnabled(false);
					
					this.txtDescription.setEnabled(false);
					
					this.actionAddNew.setEnabled(true);
					this.actionRemove.setEnabled(false);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
				}
				else if(getOprtState().equals(OprtState.ADDNEW))
				{
					this.actionAddNew.setEnabled(true);
					this.actionRemove.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(true);
				}
				else if(getOprtState().equals(OprtState.EDIT))
				{
					this.actionAddNew.setEnabled(true);
					this.actionRemove.setEnabled(true);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(true);
				}
			}
		}
		else
		{
			if(getOprtState().equals(OprtState.VIEW))
			{
				this.contNumber.setEnabled(false);
				this.txtNumber.setEnabled(false);
				
				this.contName.setEnabled(false);
				this.txtName.setEnabled(false);
				
				this.txtDescription.setEnabled(false);
				
				this.actionAddNew.setEnabled(true);
				this.actionRemove.setEnabled(false);
				this.actionEdit.setEnabled(true);
				this.actionSubmit.setEnabled(false);
			}
			else if(getOprtState().equals(OprtState.ADDNEW))
			{
				this.actionAddNew.setEnabled(true);
				this.actionRemove.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionSubmit.setEnabled(true);
			}
			else if(getOprtState().equals(OprtState.EDIT))
			{
				this.actionAddNew.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionEdit.setEnabled(false);
				this.actionSubmit.setEnabled(true);
			}
		}
	}
	
	protected IObjectValue createNewData() {
		TemplateTypeInfo typeInfo = new TemplateTypeInfo();
		
		typeInfo.setFullOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
		typeInfo.setParent((TemplateTypeInfo)getUIContext().get(UIContext.PARENTNODE));
		
		return typeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TemplateTypeFactory.getRemoteInstance();
	}

	protected void initWorkButton() 
	{
		super.initWorkButton();
		
		this.actionPrintPreview.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPrint.setEnabled(false);
		
		this.actionCancel.setVisible(false);
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCancelCancel.setEnabled(false);
		
		this.actionFirst.setVisible(false);
		this.actionFirst.setEnabled(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);
		
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		this.actionLast.setEnabled(false);
		this.actionLast.setVisible(false);
		
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
	
		this.actionAttachment.setEnabled(false);
		this.actionAttachment.setVisible(false);

	}
	
	protected void verifyInput(ActionEvent e) throws Exception 
	{
		if(StringUtils.isEmpty(editData.getNumber()))
		{
			FDCMsgBox.showWarning(this,"本级编码不能为空");
			abort();
		}
		if(StringUtils.isEmpty(editData.getName()))
		{
			FDCMsgBox.showWarning(this,"名称不能为空");
			abort();
		}
	
		if(editData.getDescription() != null)
		{
			if(editData.getDescription().trim().length() > 200)
			{
				FDCMsgBox.showWarning(this,"描述长度不能超过200字符");
				abort();
			}
		}
	}
	
	/**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	Map map = getUIContext();
		TemplateTypeInfo parentInfo = (TemplateTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",parentInfo.getId().toString()));
			if(TemplateFileFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类型已经有数据，不能进行此操作");
				abort();
			}
		}
		
        super.actionAddNew_actionPerformed(e);
        
        this.contParent.setEnabled(false);
        this.prmtParent.setEnabled(false);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {		
		if(editData.getId()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",editData.getId().toString()));
			if(TemplateFileFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类型已经有数据，不能进行此操作");
				abort();
			}
		}
		
		TemplateTypeInfo parent = editData.getParent();
		super.actionRemove_actionPerformed(e);
		
		prmtParent.setValue(parent);
	}
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(prmtParent.getValue() != null){
    		TemplateTypeInfo typeInfo = (TemplateTypeInfo)(prmtParent.getValue());
    		FilterInfo filter = new FilterInfo();
    		if(typeInfo.getId() != null)
    		{
    			filter.getFilterItems().add(new FilterItemInfo("templateType.id",typeInfo.getId().toString()));
    			if(TemplateFileFactory.getRemoteInstance().exists(filter)){
    				FDCMsgBox.showWarning(this,"明细类型已经有数据，不能进行此操作");
    				abort();
    			}
    		}
    	}
    	
        super.actionSubmit_actionPerformed(e);
    }
    protected void initDataStatus() 
	{
		super.initDataStatus();
	}
	
	protected boolean isShowAttachmentAction() {
		return false;
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
		this.txtNumber.requestFocus();
	}
	
	public void loadFields() {
		super.loadFields();
		
		parentInfo = (TemplateTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		
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
			parentInfo = ((TemplateTypeInfo)(editData)).getParent();
			if(parentInfo == null)
			{
				this.txtNumber.setText(((TemplateTypeInfo)(editData)).getNumber());
			}
			else
			{
				strTemp = parentInfo.getNumber();
				strTemp = strTemp.replace('!', '.');
//				this.txtNumber.setText(strTemp);
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
	
	protected void initOldData(IObjectValue dataObject) {
//		parentInfo = (TemplateTypeInfo) getUIContext().get(UIContext.PARENTNODE);
//		if (getUIContext().get(UIContext.PARENTNODE) != null) {
//			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
//				String strTemp = parentInfo.getNumber();
//				strTemp = strTemp.replace('!', '.');
//				((TemplateTypeInfo) dataObject).setNumber(strTemp + ".");
//			} else if (STATUS_EDIT.equals(getOprtState())) {
//				if(dataObject != null)
//				{
//					String strTemp = ((TemplateTypeInfo) dataObject).getNumber();
//					strTemp = strTemp.replace('!', '.');
//					((TemplateTypeInfo) dataObject).setNumber(strTemp);
//				}
//			}
//		}
		super.initOldData(dataObject);
	}
}