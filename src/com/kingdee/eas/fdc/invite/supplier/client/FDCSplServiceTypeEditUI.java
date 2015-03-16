/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCSplServiceTypeEditUI extends AbstractFDCSplServiceTypeEditUI
{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -445691357731119234L;
	public static Logger getLogger() {
		return logger;
	}
	private static final Logger logger = CoreUIObject.getLogger(FDCSplServiceTypeEditUI.class);
	private FDCSplServiceTypeInfo parent = null;
	private String parentnumber = null;
	private int state = 0;
    
    /**
     * 定义字符串，以初始化本级编码
     */
    private String tempNumber="";
	
    protected FDCTreeBaseDataInfo getEditData() {
		
		return new FDCSplServiceTypeInfo();
	}

	protected KDBizMultiLangBox getNameCtrl() {
		
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		
		FDCSplServiceTypeInfo splstInfo=new FDCSplServiceTypeInfo();
		Map map=getUIContext();
		FDCSplServiceTypeInfo info=(FDCSplServiceTypeInfo)map.get(UIContext.PARENTNODE);
		
		if(info !=null)
		{
			splstInfo.setParent(info);
		}
		return splstInfo;
		
	}
	
	public void onLoad() throws Exception {
		
		super.onLoad();
		this.initState();
		this.setBtnVisable();
		this.initNumber();
		this.monitorNumber();
	}
	
	/**
	 * @description		初始化编码格式
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void monitorNumber()
	{
		/**
		 * 添加监听 
		 */
		this.txtNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				initNumber();
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}
	
	/**
	 * @description		初始化状态设置
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void initState()
	{
		if(getOprtState().equals(STATUS_ADDNEW)){
    		state = 1;
		}else{
			
		}
		
		if(editData.getNumber() != null && editData.getParent()!= null && editData.getParent().getNumber() != null && editData.getNumber().toString().equals(editData.getParent().getNumber().toString())){
			this.txtLongNumber.setText(null);
		}
	}
	
	/**
	 * @description		本级编码固定前缀组成
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void initNumber()
	{
		if(parentnumber == null ){
	    	 parent = (FDCSplServiceTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
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
			
			if(number.length()>0 ){
				number+=".";
			}
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
	 * @description		重写：(编码格式)
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void loadFields() {
		
		parent = (FDCSplServiceTypeInfo) getUIContext().get(UIContext.PARENTNODE);

    	super.loadFields();
    	
    	/**
    	 * 新增时初始化本级编码
    	 */
		if(getOprtState().equals(STATUS_ADDNEW))
		{
			if (getUIContext().get(UIContext.PARENTNODE) != null) {
				tempNumber = parent.getNumber();
				tempNumber = tempNumber.replace('!', '.');
				this.txtNumber.setText(tempNumber + ".");
			}
			else
			{
				;
			}
		}
		/**
		 * 修改时，初始化本级编码
		 */
		else if(getOprtState().equals(STATUS_EDIT))
		{
			parent = ((FDCSplServiceTypeInfo)(editData)).getParent();
			if(parent == null)
			{
				this.txtNumber.setText(((FDCSplServiceTypeInfo)(editData)).getNumber());
			}
			else
			{
				tempNumber = parent.getNumber();
				tempNumber = tempNumber.replace('!', '.');
				if(tempNumber.lastIndexOf(".") > 0)
				{
					if (!txtNumber.getText().startsWith(tempNumber)) {
						String parentNumber = tempNumber.substring(0,tempNumber.lastIndexOf("."));
						txtNumber.setText(tempNumber);
						txtNumber.setSelectionStart(parentNumber.length());
					}
				}
			}
		}
		/**
		 * 查看时初始化本级编码
		 */
		else if(getOprtState().equals(STATUS_VIEW))
		{
			tempNumber = this.editData.getNumber();
			tempNumber = tempNumber.replace('!', '.');
			this.txtNumber.setText(tempNumber);
		}
    }
	
	
	/**
	 * @description		设置启用按钮隐藏
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void setBtnVisable()
	{
		this.btnCancelCancel.setVisible(false);
	}
	
	
	/**
	 * @description		远程接口调用
	 * @author			GeneZhou
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected ICoreBase getBizInterface() throws Exception {
		
		return FDCSplServiceTypeFactory.getRemoteInstance();
	} 
	
	
	/**
	 * @description		获取资源文件
	 * @author			GeneZhou
	 * @createDate		2010-11-19
	 * @param			msg
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private String getResourceFile(String msg)
	{
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
	
    
	/**
	 * @description		验证：过滤是否存在
	 * @author			GeneZhou
	 * @createDate		2010-11-19
	 * @param			msg
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private boolean isExist(String fkey,String fvalue) throws Exception
	{
		FilterInfo info=new FilterInfo();
		FilterItemInfo itenInfo=new FilterItemInfo(fkey,fvalue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
		info.getFilterItems().add(itenInfo);
		info.setMaskString(" #0");
		/**
		 * 编辑状态
		 */
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			FilterItemInfo items=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
			info.getFilterItems().add(items);
			info.setMaskString("#0 and #1");
		}
		boolean flag=FDCSplServiceTypeFactory.getRemoteInstance().exists(info);
		return flag;
	}
	
	/**
	 * @description		基础数据录入验证
	 * @author			GeneZhou
	 * @createDate		2010-11-19
	 * @param			msg
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void verifyInput(ActionEvent e) throws Exception {

		
		if(editData.getNumber() == null  || editData.getNumber().trim().length()<=0)
		{
			MsgBox.showWarning(this, getResourceFile("level")+getResourceFile("notNull"));
			SysUtil.abort();
			this.txtNumber.setFocusable(true);
		}
		
		if(editData.getName() == null || editData.getName().trim().length()<=0)
		{
			MsgBox.showWarning(this,getResourceFile("serviceType")+getResourceFile("name")+getResourceFile("notNull"));
			SysUtil.abort();
			this.txtName.setFocusable(true);
		}
		if(isExist("number",editData.getNumber().trim())==true)
		{
    		MsgBox.showInfo(this,getResourceFile("level")+editData.getNumber()+getResourceFile("again"));
    		SysUtil.abort();
    		this.txtNumber.setFocusable(true);
    	}
		if (null != editData.getParent() &&editData.getParent().getNumber()!=null&& (editData.getParent().getNumber().length()+editData.getNumber().length())>getPrama()) {
			MsgBox.showWarning(this, "本级编码超长!");
			SysUtil.abort();
		}
    	if(isExist("name",editData.getName().trim())==true)
    	{
    		MsgBox.showInfo(this,getResourceFile("serviceType")+getResourceFile("name")+editData.getName()+getResourceFile("again"));
    		SysUtil.abort();
    		this.txtName.setFocusable(true);
    	}  
    	/**
    	 * 取得输入服务类型名称去除空格
    	 */
    	if(editData.getName() != null && editData.getName().trim().length()>0)
    	{
    		editData.setName(editData.getName().toString().trim());
    	}
		
	}
	private int getPrama(){
		ParamItemCollection sc  = new ParamItemCollection();
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo info = new FilterInfo();
	    info.getFilterItems().add(new FilterItemInfo("keyID.number","TreeLongNumberlen"));
	    view.setFilter(info);
	    try {
			sc = ParamItemFactory.getRemoteInstance().getParamItemCollection(view);
			if (sc.size()>0) {
				ParamItemInfo paramItemInfo = sc.get(0);
				return Integer.parseInt(paramItemInfo.getValue());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return 80;
	}
	
    /**
     * output class constructor
     */
    public FDCSplServiceTypeEditUI() throws Exception
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
	 * @description		提交
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        if(state == 1){
    		this.txtName.setDefaultLangItemData(null);
    		this.txtDescription.setText(null);
    		this.txtNumber.setText(null);
    		parent = null;
    		initNumber();
       }
        
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
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("parent.*"));
    	return sic;
    }

}