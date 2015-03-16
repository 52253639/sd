/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.material.client.MaterialInfoEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商材料报价信息查看
 *		
 * @author		周航建
 * @version		EAS7.0		
 * @createDate	2010-11-14 
 * @see
 */
public class MaterialInfoListUI extends AbstractMaterialInfoListUI
{
    /*
	 * 标识
	 */
	private static final long serialVersionUID = 7693444392856655931L;
	private static final Logger logger = CoreUIObject.getLogger(MaterialInfoListUI.class);
	/*
	 * 全局变量：编码(number)
	 */
    private String param="";
    
    

	/**
	 * @description		窗体加载事件
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    public void onLoad() throws Exception {
    	
    	super.onLoad();
    	this.initSeting();
    	    	
    	
    }
    
    /**
	 * @description		显示
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    public void onShow() throws Exception {
    
    	super.onShow();
    	this.setStyle();
    }
    
    private void setStyle(){
    	/*
    	 * 设置kDContainer控件收缩属性
    	 */
    	this.tblMaterialInfo.setEnableActive(false);
    }
    
    /**
	 * @description		获取供应商名称参数
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param			supplierName
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initSeting() throws Exception {
		if( null != getUIContext().get("SupplierInfo")){
			SupplierStockInfo info = (SupplierStockInfo) getUIContext().get("SupplierInfo");
			/*
			 * 设置文本框Text
			 */
			this.txtSupplierName.setText(info.getName());
			/*
			 * 供应商编码
			 */
			this.param=info.getNumber();
		}
		/*
		 * 填充表数据
		 */
		this.fillDataInTable();
		
	}
	
	
	
	/**
	 * @description		判断是否选择行
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param			
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void checkSelectedRow()
	{
		if(this.tblMain.getRowCount()==0 || this.tblMain.getSelectManager().size()==0){
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * @description		初始化表单数据
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @throws  
	 * @throws EASBizException 
	 * @see						
	 */
    private void fillDataInTable() throws Exception
    {
    	
			List list = new ArrayList();
			list.add(param);
			/*
			 * 调用远程方法,数据查询
			 */
			Set set = SupplierStockFactory.getRemoteInstance().getDataBase(list);
			/*
			 * 锁表
			 */
			this.tblMain.checkParsed();

			Iterator it = set.iterator();
			while(it.hasNext())
			{
				Map map=(Map)it.next();

				IRow ir=this.tblMain.addRow();
				/*
				 * 绑定表数据
				 */
				ir.getCell("MaterialNumber").setValue(map.get("MaterialNumber"));
				ir.getCell("MaterialName").setValue(map.get("MaterialName"));				
				ir.getCell("Model").setValue(map.get("Model"));
				ir.getCell("Unit").setValue(map.get("Unit"));
				ir.getCell("Prices").setValue(map.get("Prices"));
				ir.getCell("Time").setValue(map.get("Time"));
				ir.getCell("ProjetName").setValue(map.get("ProjetName"));
				ir.getCell("DealName").setValue(map.get("DealName"));
				ir.getCell("id").setValue(map.get("materinfoId"));
				
				/*
				 * 锁定表单元格
				 */
				this.setColumnLock();
				
			}
		
		
		/*
		 * 设置价格尾数
		 */
		this.tblMain.getColumn("Prices").getStyleAttributes().setNumberFormat("#0.00");
		/*
		 * 内容文字居右
		 */
		this.tblMain.getColumn("Prices").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		/*
		 * 锁住
		 */
		this.tblMain.getHeadStyleAttributes().setLocked(true);
		/*
		 * 选择数据行
		 */
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    /**
	 * @description		双击表格事件
	 * @author			周航建	
	 * @createDate		2010-12-08
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    private void setColumnLock()
    {
    	/*
		 * 锁住单元格
		 */
    	this.tblMain.getColumn("MaterialNumber").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("MaterialName").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("Model").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("Unit").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("Prices").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("Time").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("ProjetName").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("DealName").getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn("id").getStyleAttributes().setLocked(true);
    	
    }
    
    /**
	 * @description		双击表格事件
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	
    	/*
    	 * 取得选择行ID
    	 */
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if(e.getClickCount() == 1){
			this.tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).setCollapse(true);
		}
		if (index != -1 && e.getClickCount() == 2) {
			
			Object infoId = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
			UIContext uiContext = new UIContext(this);
			if(null == infoId){
				abort();
			}
		    /*
		     * 将选择记录的ID放置
		     */
	   		uiContext.put(UIContext.ID, infoId);
	   		
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
			IUIWindow uiWindow=null;
			uiWindow = uiFactory.create(MaterialInfoEditUI.class.getName(), uiContext, null,"FINDVIEW");
			uiWindow.show();
		}
    	
    }
    
    /**
	 * @description		报价信息查看
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
       /*
        * 判断选择行
        */
    	checkSelectedRow();
    	
    	/*
    	 * 得到选择行ID
    	 */
		Object infoId = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		
		UIContext uiContext = new UIContext(this);
		/*
		 * 判断选择记录的ID是否为空
		 */
		if(null == infoId){
			abort();
		}
       	/*
       	 * 将选择记录的ID放置(放ID 必须用UIContext.ID同时UIContext.ID为必写)
       	 */
   		uiContext.put(UIContext.ID, infoId);
		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
		IUIWindow uiWindow=null;
		uiWindow = uiFactory.create(MaterialInfoEditUI.class.getName(), uiContext, null,"FINDVIEW");
		uiWindow.show();
    }
    
     
    
    
    /**
     * output class constructor
     */
    public MaterialInfoListUI() throws Exception
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
        logger.info(e.getClass());
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

    

}