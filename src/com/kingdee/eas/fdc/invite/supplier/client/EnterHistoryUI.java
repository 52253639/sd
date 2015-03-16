/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.AcceptanceLetterCollection;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillCollection;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillFactory;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo;
import com.kingdee.eas.fdc.invite.client.InviteEntSuppChkBillEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			EnterHistoryUI.java			
 * 版权：		金蝶国际软件集团有限公司版权所有<P>		 	
 * 描述：		供应库.入围历史<P>
 *
 * @author		田宝平
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class EnterHistoryUI extends AbstractEnterHistoryUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterHistoryUI.class);
    
    /*
     * 定义供应商number
     */
    private String supplierNumber = "";
    
    /*
     * 定义供应商ID
     */
    private String supplierID = "";
    
    /*
     * 定义选择项目的ID
     */
    private String entInviteProjectID = "";
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	/*
    	 * 初始化样式
    	 */
    	initStyle();
    	
    	/*
    	 * 页面加载时查询，并填充文本框和表格
    	 */
    	setValueToTable();
    }
    
    /*
     * 获取资源文件
     */
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    
    /**
     * 
    * @description		初始化样式
    * @author			田宝平	
    * @createDate		2010-12-1
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
    public void initStyle(){
    	
    	/*
    	 * 设置查看按钮的图标显示类型
    	 */
    	this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
    	
    	/*
    	 * 设置服务菜单的功能选项为启用
    	 */
    	this.MenuItemKnowStore.setVisible(true);
    	this.MenuItemKnowStore.setEnabled(true);
    	this.MenuItemAnwser.setVisible(true);
    	this.MenuItemAnwser.setEnabled(true);
    	this.MenuItemRemoteAssist.setVisible(true);
    	this.MenuItemRemoteAssist.setEnabled(true);
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {

    	/*
    	 * 判断是否选择行
    	 */
    	 checkSelected();
    	 
 		/*
 		 * 获取选择记录的索引
 		 */
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		
		/*
		 * 获取选择记录的ID
		 */
		Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
		
		UIContext uiContext = new UIContext(this);
		
		/*
		 * 判断选择记录的ID是否为空
		 */
 		if(null != infoId){
        	/*
        	 * 将选择记录的ID放置
        	 */
    		uiContext.put("ID", infoId);
    	}
 		
 		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
 		IUIWindow uiWindow = null;
 		
 		/*
 		 * 根据选择跳转到查看页面
 		 */
 		uiWindow = getIUIWindow(uiContext,uiFactory,uiWindow);
  		uiWindow.show();
    }
    
    /**
     * 
    * @description		根据选择跳转到查看页面
    * @author			田宝平	
    * @createDate		2010-11-15
    * @param			UIContext,IUIFactory,IUIWindow
    * @return			IUIWindow		
    *	
    * @version			EAS7.0
    * @see
     */
    protected IUIWindow getIUIWindow(UIContext uiContext,IUIFactory uiFactory,IUIWindow uiWindow) throws Exception{

    	uiWindow = uiFactory.create(InviteEntSuppChkBillEditUI.class.getName(), uiContext, null,"FINDVIEW");
 		
    	return uiWindow;
    }
    
    /**
     * 					双击选择跳转到查看页面
    * @description		
    * @author			田宝平	
    * @createDate		2010-11-15
    *	
    * @version			EAS7.0
    * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractAppraiseHistoryUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
     */
	final protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
		/*
		 * 未选中表行时
		 */
		if (e.getType() == 0 || e.getType() == 3) {
			return;
		} else {
			this.tblMain.getStyleAttributes().setLocked(true);
    		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    		
    		/*
    		 * 获取选择行的索引
    		 */
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			
			if (activeRowIndex != -1 && e.getClickCount() == 2) {
				if (this.tblMain.getRow(activeRowIndex).getCell("id") != null) {
					
					/*
					 * 判断是否选择
					 */
					checkSelected();
					
					/*
					 * 获取选择行的索引
					 */
					int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
					
					/*
					 * 获取选择记录的id
					 */
					Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();

					UIContext uiContext = new UIContext(this);
					
					/*
					 * 判断选择记录的ID是否为空
					 */
					if (null != infoId) {
						/*
						 * 将选择记录的ID放置
						 */
						uiContext.put("ID", infoId);
					}

					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					IUIWindow uiWindow = null;
					
					/*
					 * 根据选择的跳转到查看页面
					 */
					uiWindow = getIUIWindow(uiContext, uiFactory, uiWindow);
					uiWindow.show();
				}
			}
		}
	}
    
	/**
	 * 
	* @description		判断是否选择行
	* @author			田宝平	
	* @createDate		2010-12-1
	* @param	
	* @return			void		
	*	
	* @version			EAS7.0
	* @see
	 */
	public void checkSelected()
	{
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
		{
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	* @description		先填充文本框，再查询并将查询的数据填充表格
	* @author			田宝平	
	* @createDate		2010-11-15
	* @param	
	* @return			void		
	*	
	* @version			EAS7.0
	 * @throws EASBizException 
	* @see
	 */   
    private void setValueToTable() throws EASBizException{
    	
    	if(null != getUIContext().get("SupplierInfo")){
    		SupplierStockInfo infoTMP = (SupplierStockInfo) getUIContext().get("SupplierInfo");
    		
    		/*
    		 * 将传递的供应商名称放置到文本框中
    		 */
    		this.txtSupplierName.setText(infoTMP.getName());
    		
        	/*
        	 * 获取选择记录的Number，ID
        	 */
    		supplierNumber = infoTMP.getNumber().toString();
    		supplierID = infoTMP.getId().toString();
    	}
    	
    	/*
    	 * 设置文本框的属性为只读
    	 */
    	this.txtSupplierName.setEnabled(false);
    	
		/*
		 * 锁定表格
		 */
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
    	try {    		
    		IRow row = null;
    		InviteEntSuppChkBillCollection stc = getSelectedCollection();
			Iterator it = (Iterator) stc.iterator();

			/*
			 * 迭代将查询的结果放置到表格中
			 */
			while(it.hasNext()){
				InviteEntSuppChkBillInfo info = (InviteEntSuppChkBillInfo)it.next();
				
				/*
				 * 新增行
				 */
				row = this.tblMain.addRow();
				
				/*
				 * 根据单元格的名称,依次将值放置到单元格中，并排除为NULL的数据
				 */
				
				row.getCell("SupplierID").setValue(supplierID);
				
				if(info !=null && info.getId().toString() != null){
					row.getCell("id").setValue(info.getId().toString());
				}else{
					row.getCell("id").setValue(null);
				}

			/*	if(info != null && info.getInviteProject() != null && info.getInviteProject().getProject()!=null && info.getInviteProject().getProject().getName() != null && info.getInviteProject().getProject().getName().trim().length() > 0){
					row.getCell("prjName").setValue(info.getInviteProject().getProject().getName());
				}else{
					row.getCell("prjName").setValue(null);
				}*/
				
				if(info != null && info.getInviteProject() != null && info.getInviteProject().getName() != null && info.getInviteProject().getName().trim().length() > 0){
					row.getCell("traTask").setValue(info.getInviteProject().getName());
				}else{
					row.getCell("traTask").setValue(null);
				}
				
				if(info !=null && info.getHandlerDate() != null){
					row.getCell("entDate").setValue(info.getHandlerDate());
				}else{
					row.getCell("entDate").setValue(null);
				}

				if(info !=null && info.getInviteProject() != null && info.getInviteProject().getId().toString() != null){
					
					/*
					 * 取得选择行的供应商所属项目的ID
					 */
					entInviteProjectID = info.getInviteProject().getId().toString();
					
					/*
					 * 判断是否在中标通知书中
					 */
					AcceptanceLetterCollection stc2 = getSelectedCollection2();

					/*
					 * 定义在中标通知书中的个数
					 */
					int billNum = 0;
					if(stc2 !=null){
						billNum = stc2.size();
					}

					/*
					 * 若在中标通知书中则为中标，否则为未中标
					 */
					if (billNum > 0) {
						row.getCell("isTarget").setValue(getResource("zhongBiao"));
					} else {
						row.getCell("isTarget").setValue(getResource("notZhongBiao"));
					}
				}else{
					row.getCell("isTarget").setValue(null);
				}
			}
			
			/*
			 * 对输出的格式进行页面转换
			 */
			// 测试提Bug了，说这种日期显示格式不统一 Added by Owen_wen 2011-12-02
			// this.tblMain.getColumn("entDate").getStyleAttributes().setNumberFormat("yyyy.mm.dd");
			
			/*
			 * 将列的显示设置为居中
			 */
			this.tblMain.getColumn("prjName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("traTask").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("entDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("isTarget").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			tblMain.getHeadStyleAttributes().setLocked(true);
			
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
	/**
	 * 
	* @description		过滤入围审批单及其分录的相关信息
	* @author			田宝平	
	* @createDate		2010-11-27
	* @param	
	* @return			ContractBillCollection		
	*	
	* @version			EAS7.0
	* @see
	 */
	protected InviteEntSuppChkBillCollection getSelectedCollection() throws EASBizException, BOSException
	{
		/*
		 * 定义查询实体
		 */
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("handlerDate"));	 
		view.getSelector().add(new SelectorItemInfo("entry.*"));	 
        view.getSelector().add(new SelectorItemInfo("entry.supplier.*")); 
		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));	 
		view.getSelector().add(new SelectorItemInfo("inviteProject.project.*"));	
        
		/*
		 * 将选择记录的Number放置到list中
		 */
		List list = new ArrayList();
		list.add(supplierNumber);

		/*
		 * 根据传递的list,调用远程方法,查询入围审批单分录中的ParentID(即入围审批单的ID),并将结果放置到set中返回
		 */
		Set setId = SupplierStockFactory.getRemoteInstance().getEHDBValue(list);
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		//filter.getFilterItems().add(new FilterItemInfo("entry.supplier.number",supplierNumber));
		filter.getFilterItems().add(new FilterItemInfo("id",setId,CompareType.INCLUDE));

        /*
         * 将查询条件注入
         */
	    view.setFilter(filter);
  		try {
			view.decode("ORDER BY handlerDate desc");
		} catch (ParserException e) {
			handleException(e);
		}
	    
	    /*
	     * 返回查询的结果
	     */
        return InviteEntSuppChkBillFactory.getRemoteInstance().getInviteEntSuppChkBillCollection(view);
	}
	
	/**
	 * 
	* @description		过滤中标通知书的相关信息
	* @author			田宝平	
	* @createDate		2010-11-28
	* @param	
	* @return			AcceptanceLetterCollection		
	*	
	* @version			EAS7.0
	* @see
	 */
	protected AcceptanceLetterCollection getSelectedCollection2() throws EASBizException, BOSException
	{
		/*
		 * 定义查询实体
		 */
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		view.getSelector().add(new SelectorItemInfo("id"));	 
		view.getSelector().add(new SelectorItemInfo("supplier.*"));	  
		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));	 
        
		/*
		 * 定义一个过滤条件，以添加主查询的查询条件
		 */
		FilterInfo filter = new FilterInfo();
		
		/*
		 * 状态为已审批
		 */
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",entInviteProjectID));

        /*
         * 将查询条件注入
         */
	    view.setFilter(filter);
	    
	    /*
	     * 返回查询的结果
	     */
        return AcceptanceLetterFactory.getRemoteInstance().getAcceptanceLetterCollection(view);
	}
	
	/**
	 * 
	* @description		关闭窗口刷新之前调用的页面
	* @author			田宝平	
	* @createDate		2010-11-21
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.CoreUI#destroyWindow()
	 */
    public boolean destroyWindow() {
   	 boolean b = super.destroyWindow();
   	 Object ui = null ;
   	 ui = getUIContext().get("Owner");
   	 if( b && null !=ui && ui instanceof SupplierStockListUI){
   		 SupplierStockListUI newUI = (SupplierStockListUI)ui;
       	 try {
				newUI.getTblMain();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return b;
   }
    
    /**
     * output class constructor
     */
    public EnterHistoryUI() throws Exception
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

}