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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ExpertTypeFactory;
import com.kingdee.eas.fdc.invite.ExpertTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ExpertTypeEditUI extends AbstractExpertTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpertTypeEditUI.class);
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    
    private ExpertTypeInfo parentInfo = null;
	private String strTemp = null;
	
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("parent.number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isLeaf"));
        return sic;
    }       
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		boolean canAdd = false;
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
		this.actionAddNew.setEnabled(canAdd);
		this.actionRemove.setEnabled(canAdd);
		this.actionEdit.setEnabled(canAdd);
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionSave.setVisible(false);
		
		txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				ExpertTypeInfo parent = (ExpertTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
				if (parent == null)
					return ;
				
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
    public ExpertTypeEditUI() throws Exception
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
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("name",this.txtName.getText().trim()));
    	if(editData.getId()!=null)
    	filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
    	if(getUIContext().get(UIContext.PARENTNODE) != null && editData.getParent() != null){
    		
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",this.editData.getParent().getId()));
    		
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",null));
    	}
    	    	
    	if(getBizInterface().exists(filter)){
    		FDCMsgBox.showWarning(this,"已有相同的专家类型！");
			return;
    	}
    	
    	super.actionSubmit_actionPerformed(e);
    }
    
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        
    	
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
  
	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		ExpertTypeInfo object = new ExpertTypeInfo();
		Map map = getUIContext();
		ExpertTypeInfo parentInfo = (ExpertTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null)
			object.setParent(parentInfo);
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return ExpertTypeFactory.getRemoteInstance();
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
		
		parentInfo = (ExpertTypeInfo) getUIContext().get(UIContext.PARENTNODE);

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
			parentInfo = ((ExpertTypeInfo)(editData)).getParent();
			if(parentInfo == null)
			{
				this.txtNumber.setText(((ExpertTypeInfo)(editData)).getNumber());
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
//		parentInfo = (ExpertTypeInfo) getUIContext().get(UIContext.PARENTNODE);
//		if (getUIContext().get(UIContext.PARENTNODE) != null) {
//			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
//				String strTemp = parentInfo.getNumber();
//				strTemp = strTemp.replace('!', '.');
//				((ExpertTypeInfo) dataObject).setNumber(strTemp + ".");
//			} else if (STATUS_EDIT.equals(getOprtState())) {
//				if(dataObject != null)
//				{
//					String strTemp = ((ExpertTypeInfo) dataObject).getNumber();
//					strTemp = strTemp.replace('!', '.');
//					if(strTemp.lastIndexOf(".") > 0)
//					{
//						String parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
//						((ExpertTypeInfo) dataObject).setNumber(parentNumber);
//					}
//				}
//			}
//		}
		super.initOldData(dataObject);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
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
				FDCMsgBox.showWarning(this,"描述长度不能超过200");
				abort();
			}
		}
	}
}