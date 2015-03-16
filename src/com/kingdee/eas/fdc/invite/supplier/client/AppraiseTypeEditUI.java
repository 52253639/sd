/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			AppraiseTypeEditUI.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		评审模板类型编辑界面
 *		
 * @author		
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class AppraiseTypeEditUI extends AbstractAppraiseTypeEditUI
{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 2992914843271238606L;

	private static final Logger logger = CoreUIObject.getLogger(AppraiseTypeEditUI.class);
    
    private SupplierAppraiseTypeInfo parentInfo = null;
    /*
     * 标识
     */
    private String strTemp="";
    /*
     * 状态序列号
     */
    private int state = 0;
  
    /**
     * output class constructor
     */
    public AppraiseTypeEditUI() throws Exception
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
         * @description		获取资源文件
         * @author			
         * @createDate		2010-12-8
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if(getOprtState().equals(STATUS_ADDNEW)){
    		state = 1;
		}
    	this.txtLongNumber.setEnabled(false);
    	this.txtName.setRequired(true);
    	this.txtDesi.setMaxLength(500);
    	txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				initTxtNum();
			}
			public void keyTyped(KeyEvent e) {
			}
		});
    }
    
     /**
         * @description		初始化编码框
         * @author			
         * @createDate		2010-12-8
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    protected void initTxtNum() {
    	if(parentnumber == null ){
	    	 parent = (SupplierAppraiseTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
		     if (parent == null)
		    	 return ;
		     parentnumber = parent.getNumber().replace('!', '.')+".";
		}
		if(getOprtState().equals(STATUS_ADDNEW) || state == 1)
		{
			if (!txtNumber.getText().startsWith(parentnumber)) {
				txtNumber.setText(parentnumber);
				txtNumber.setSelectionStart(parentnumber.length());
			}
		}
		if(getOprtState().equals(STATUS_EDIT)  && state != 1)
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
    /**
     * output actionMsgFormat_actionPerformed
     */
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("parent.number"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    } 
	 /**
     * @description		提交事件
     * @author			
     * @createDate		2010-12-7
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name",this.txtName.getDefaultLangItemData().toString().trim()));
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
		}
		if(getUIContext().get(UIContext.PARENTNODE) != null && editData.getParent() != null){
			filter.getFilterItems().add(new FilterItemInfo("parent.id",this.editData.getParent().getId()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("parent.id",null));
		} 	
		if(getBizInterface().exists(filter)){
			FDCMsgBox.showWarning(this,getResource("name")+getResource("repeat"));
			return;
		}
		super.actionSubmit_actionPerformed(e);
		if(state == 1){
			editData = (SupplierAppraiseTypeInfo) createNewData();
			loadFields();
			this.txtName.setDefaultLangItemData(null);
			this.txtDesi.setText(null);
			parentInfo = null;
			this.txtNumber.setText(null);
			initTxtNum();
		}
		logger.info(e.getSource());
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
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    
     /**
         * @description		
         * @author			胥凯	
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public boolean destroyWindow() {
    	return super.destroyWindow();
    }
    
    /**
     * @description		
     * @author			陈伟		
     * @createDate		2010-12-2
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    protected void doAfterSubmit(IObjectPK arg0) throws Exception {

    	super.doAfterSubmit(arg0);
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
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
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
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-30
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    SupplierAppraiseTypeInfo parent = null;
    String parentnumber = null;
    
     /**
     * @description		
     * @author			陈伟		
     * @createDate		2010-11-23
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    public void loadFields() {
    	super.loadFields();
		parentInfo = (SupplierAppraiseTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);//getOprtState().equals(STATUS_ADDNEW)
		if(getOprtState().equals(STATUS_ADDNEW))
		{
			if (getUIContext().get(UIContext.PARENTNODE) != null) {
				strTemp = parentInfo.getNumber();
				strTemp = strTemp.replace('!', '.');
				this.txtNumber.setText(strTemp + ".");
			}
			else
			{
			}
		}
		else if(getOprtState().equals(STATUS_EDIT))
		{
			parentInfo = ((SupplierAppraiseTypeInfo)(editData)).getParent();
			if(parentInfo == null)
			{
				this.txtNumber.setText(((SupplierAppraiseTypeInfo)(editData)).getNumber());
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
    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }
    /**
     * output actionMsgFormat_actionPerformed
     */
	protected IObjectValue createNewData() {
		SupplierAppraiseTypeInfo object = new SupplierAppraiseTypeInfo();
		Map map = getUIContext();
		SupplierAppraiseTypeInfo parentInfo = (SupplierAppraiseTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null)
			object.setParent(parentInfo);
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SupplierAppraiseTypeFactory.getRemoteInstance();
	}
	 /**
	     * @description		验证用户输入方法
	     * @author				
	     * @createDate		2010-12-7
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void verifyInput(ActionEvent actionevent) throws Exception {
		
	 	if(editData.getNumber() == null || editData.getNumber().toString().trim().length() == 0){
			FDCMsgBox.showWarning(this,getResource("level")+getResource("notNull"));
			abort();
		}
	 	
		if(editData.getName() == null || editData.getName().toString().trim().length() == 0){
			FDCMsgBox.showWarning(this,getResource("name")+getResource("notNull"));
			abort();
		}
		
		if(isExist("number",editData.getNumber().toString().trim()) == true){
    		MsgBox.showInfo(this,getResource("level")+editData.getNumber()+getResource("again"));
    		abort();
    	}
		
    	if(isExist("name",editData.getName().toString().trim()) == true){
    		MsgBox.showInfo(this,getResource("name")+editData.getName()+getResource("again"));
    		abort();
    	}  
		/*
		 * 保存前清除数据前后的空格
		 */
		editData.setName(editData.getName().toString().trim());
		editData.setNumber(editData.getNumber().toString().trim());
		
		if(editData.getDescription() != null && editData.getDescription().trim().length() > 0){
			editData.setDescription(editData.getDescription().toString().trim());
		}else{
			editData.setDescription("");
		}
	}
	
	/**
     * @description		过滤条件，判断是否重复
     * @author			胥凯	
     * @createDate		2010-11-22
     * @param			filedName  对应字段
     * @param			filedValue  界面输入的数据
     * @return			存在就返回true 否则false	
     *	
     * @version			EAS7.0
     * @see						
     */
	private boolean isExist(String filedName,String filedValue)throws Exception{
		if(editData.getId() == null){
			return false;
		}
		FilterInfo filterInfo=new FilterInfo();
		FilterItemInfo itemInfo=new FilterItemInfo(filedName,filedValue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
		filterInfo.getFilterItems().add(itemInfo);
		filterInfo.setMaskString(" #0");
		if(STATUS_EDIT.equals(this.getOprtState())){
			FilterItemInfo itemInfo_2=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
			filterInfo.getFilterItems().add(itemInfo_2);
			filterInfo.setMaskString("#0 and #1");
		}
		boolean flag=SupplierAppraiseTypeFactory.getRemoteInstance().exists(filterInfo);
		return flag;
	}
	protected void initOldData(IObjectValue dataObject) {
		super.initOldData(dataObject);
	}
	public void onShow() throws Exception
	{
		super.onShow();
		this.txtNumber.requestFocus();
	}
	
}