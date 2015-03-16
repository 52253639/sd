/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeCollection;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeFactory;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class AppraiseTemplateTypeEditUI extends AbstractAppraiseTemplateTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseTemplateTypeEditUI.class);
    
    private AppraiseTempletTypeInfo parentInfo = null;
	private String strTemp = null;
	
    /**
     * output class constructor
     */
    public AppraiseTemplateTypeEditUI() throws Exception
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
    	if(prmtParent.getValue() != null){
    		AppraiseTempletTypeInfo typeInfo = (AppraiseTempletTypeInfo)(prmtParent.getValue());
    		FilterInfo filter = new FilterInfo();
    		if(typeInfo.getId() != null)
    		{
    			filter.getFilterItems().add(new FilterItemInfo("templateType.id",typeInfo.getId().toString()));
    			if(AppraiseTemplateFactory.getRemoteInstance().exists(filter)){
    				FDCMsgBox.showWarning(this,"明细类别已经有数据，不能执行此操作");
    				abort();
    			}
    		}
    	}
    	
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	Map map = getUIContext();
		AppraiseTempletTypeInfo parentInfo = (AppraiseTempletTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",parentInfo.getId().toString()));
			if(AppraiseTemplateFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类别已经有数据，不能再新增子类别");
				abort();
			}
		}
		
        super.actionAddNew_actionPerformed(e);
        
        this.contParent.setEnabled(false);
        this.prmtParent.setEnabled(false);
    }
	protected IObjectValue createNewData() {
    	AppraiseTempletTypeInfo info = new AppraiseTempletTypeInfo();
    	
    	Map map = getUIContext();

    	AppraiseTempletTypeInfo parent = (AppraiseTempletTypeInfo)( map.get(UIContext.PARENTNODE));
    	info.setParent(parent);

    	info.setIsEnabled(true);
    	
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return AppraiseTempletTypeFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception {
		
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		
		super.onLoad();
		initHeadStyle();
		
		txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				AppraiseTempletTypeInfo parent = (AppraiseTempletTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
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

	/**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic =  super.getSelectors();
    	sic.add("isLeaf");
    	return sic ;
    }     
	
	protected void initHeadStyle()
	{
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);

		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		
		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);
		
		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);
		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		
		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionAttachment.setEnabled(false);
		this.actionAttachment.setVisible(false);
		
		this.contParent.setEnabled(false);
		this.prmtParent.setEnabled(false);
		
		this.prmtParent.setDisplayFormat("$number$");
		
		prmtParent.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7AppraiseGuideLineTypeQuery");
		
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
	}
	
	protected void initDataStatus() 
	{
		// TODO Auto-generated method stub
		super.initDataStatus();
		if(OprtState.EDIT.equals(getOprtState()))
		{
			this.contParent.setEnabled(false);
			this.prmtParent.setEnabled(false);
			
			this.contNumber.setEnabled(true);
			this.txtNumber.setEnabled(true);
			
			this.contName.setEnabled(true);
			this.txtName.setEnabled(true);
			
			this.txtDescription.setEnabled(true);
			
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			
			this.actionEdit.setEnabled(false);
		}
		else if(OprtState.VIEW.equals(getOprtState()))
		{
//			this.contParent.setEnabled(false);
//			this.prmtParent.setEnabled(false);
			this.prmtParent.setEditable(false);
			
			this.contNumber.setEnabled(false);
			this.txtNumber.setEnabled(false);
			
			this.contName.setEnabled(false);
			this.txtName.setEnabled(false);
			
			this.txtDescription.setEnabled(false);
			
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(true);
			
			if(getUIContext().get("CAN_ADD") != null)
			{
				Boolean canAdd = (Boolean)(getUIContext().get("CAN_ADD") );
				if(!canAdd.booleanValue())
				{
					this.actionEdit.setEnabled(false);
					this.actionAddNew.setEnabled(false);
				}
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
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
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {		
		if(editData.getId()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",editData.getId().toString()));
			if(AppraiseTemplateFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类别已经有数据，不能执行此操作");
				abort();
			}
		}

		AppraiseTempletTypeInfo parent = editData.getParent();
		super.actionRemove_actionPerformed(e);
		
		prmtParent.setValue(parent);
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
		this.txtNumber.requestFocus();
	}
	
	public void loadFields() {
		super.loadFields();
		if (getUIContext().get(UIContext.PARENTNODE) instanceof AppraiseTempletTypeInfo) {
			parentInfo = (AppraiseTempletTypeInfo) getUIContext().get(
					UIContext.PARENTNODE);
			logger.warn("TYPEINFO");
		} else if (getUIContext().get(UIContext.PARENTNODE) instanceof String) {
			
			FDCMsgBox.showWarning("String +"
					+ getUIContext().get(UIContext.PARENTNODE).toString()
					);
			logger.warn("String"+getUIContext().get(UIContext.PARENTNODE));
			EntityViewInfo view = new EntityViewInfo();
			view.getFilter().getFilterItems().add(new FilterItemInfo("name",getUIContext().get(UIContext.PARENTNODE).toString()));
			
			AppraiseTempletTypeCollection col = null;
			try {
				col = AppraiseTempletTypeFactory
						.getRemoteInstance().getAppraiseTempletTypeCollection(view);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("loadFields" + e.getMessage());
				FDCMsgBox.showWarning("加载不到类型节点");
			}
			parentInfo = col.get(0);
		}
		
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
			parentInfo = ((AppraiseTempletTypeInfo)(editData)).getParent();
			if(parentInfo == null)
			{
				this.txtNumber.setText(((AppraiseTempletTypeInfo)(editData)).getNumber());
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
//		parentInfo = (AppraiseTempletTypeInfo) getUIContext().get(UIContext.PARENTNODE);
//		if (getUIContext().get(UIContext.PARENTNODE) != null) {
//			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
//				String strTemp = parentInfo.getNumber();
//				strTemp = strTemp.replace('!', '.');
//				((AppraiseTempletTypeInfo) dataObject).setNumber(strTemp + ".");
//			} else if (STATUS_EDIT.equals(getOprtState())) {
//				if(dataObject != null)
//				{
//					String strTemp = ((AppraiseTempletTypeInfo) dataObject).getNumber();
//					strTemp = strTemp.replace('!', '.');
//					((AppraiseTempletTypeInfo) dataObject).setNumber(strTemp);
//				}
//			}
//		}
		super.initOldData(dataObject);
	}
}