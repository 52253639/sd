/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSplServiceTypeEditUI extends AbstractMarketSplServiceTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSplServiceTypeEditUI.class);
    private MarketSplServiceTypeInfo parent = null;
	private String parentnumber = null;
	private int state = 0;
    
    /**
     * 定义字符串，以初始化本级编码
     */
    private String tempNumber="";
    /**
     * output class constructor
     */
    public MarketSplServiceTypeEditUI() throws Exception
    {
        super();
    }
    protected FDCTreeBaseDataInfo getEditData() {
		
		return new MarketSplServiceTypeInfo();
	}

	protected KDBizMultiLangBox getNameCtrl() {
		
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		
		return this.txtNumber;
	}
	
	
	protected IObjectValue createNewData() {
		
		MarketSplServiceTypeInfo splstInfo=new MarketSplServiceTypeInfo();
		Map map=getUIContext();
		MarketSplServiceTypeInfo info=(MarketSplServiceTypeInfo)map.get(UIContext.PARENTNODE);
		
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
	    	 parent = (MarketSplServiceTypeInfo)( getUIContext().get(UIContext.PARENTNODE));
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
		
		parent = (MarketSplServiceTypeInfo) getUIContext().get(UIContext.PARENTNODE);

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
			parent = ((MarketSplServiceTypeInfo)(editData)).getParent();
			if(parent == null)
			{
				this.txtNumber.setText(((MarketSplServiceTypeInfo)(editData)).getNumber());
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
		boolean flag=MarketSplServiceTypeFactory.getRemoteInstance().exists(info);
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
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeFactory.getRemoteInstance();
    }


}