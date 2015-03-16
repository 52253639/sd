/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			AppraiseHistoryUI.java			
 * 版权：		金蝶国际软件集团有限公司版权所有<P>		 	
 * 描述：		供应库.评审历史<P>
 *
 * @author		田宝平
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class AppraiseHistoryUI extends AbstractAppraiseHistoryUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseHistoryUI.class);
	
    /*
     * 定义供应商number
     */
    private String supplierNumber = "";
    
    /*
     * 定义供应商ID
     */
    private String supplierID = "";
    
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
		
		/*
		 * 获取选择记录的类型
		 */
		Object cType = tblMain.getRow(rowIndex).getCell("cType").getValue();
		
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
 		 * 根据选择的不同类型，跳转到不同的查看页面
 		 */
 		uiWindow = getIUIWindow(uiContext,uiFactory,uiWindow,cType);
  		uiWindow.show();
    }
    
    /**
     * 
    * @description		根据选择的不同类型跳转到不同的查看页面
    * @author			田宝平	
    * @createDate		2010-11-15
    * @param			UIContext,IUIFactory,IUIWindow,Object
    * @return			IUIWindow		
    *	
    * @version			EAS7.0
    * @see
     */
    protected IUIWindow getIUIWindow(UIContext uiContext,IUIFactory uiFactory,IUIWindow uiWindow,Object cType) throws Exception{
    	
    	/*
    	 * 跳转到资格考察界面
    	 */
 		if(getResource("review").equals(cType)){
 			uiWindow = uiFactory.create(SupplierQuaReviewEditUI.class.getName(), uiContext, null,"FINDVIEW");
 	 	}
 		/*
 		 * 跳转到履约评估界面
 		 */
 		else if(getResource("evaluate").equals(cType)){
 	 		uiWindow = uiFactory.create(SupplierPerformEvalulationEditUI.class.getName(), uiContext, null,"FINDVIEW");	
 	 	}
 		/*
 		 * 跳转到定期评审界面
 		 */
 		else{
 	 		uiWindow = uiFactory.create(SupplierEvaluateEditUI.class.getName(), uiContext, null,"FINDVIEW");
 	 	}
 		
 		return uiWindow;
    }
    
    /**
     * 					根据双击选择的不同类型，跳转到不同的查看页面
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
     		 * 获取选择记录的索引
     		 */
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			
			if (activeRowIndex != -1 && e.getClickCount() == 2) {
				if (this.tblMain.getRow(activeRowIndex).getCell("id") != null) {
					
					/*
					 * 判断是否选择
					 */
					checkSelected();
					
					/*
					 * 获取选择记录的ID和状态
					 */
					int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
					Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
					Object cType = tblMain.getRow(rowIndex).getCell("cType").getValue();

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
					 * 根据选择的不同类型，跳转到不同的查看页面
					 */
					uiWindow = getIUIWindow(uiContext, uiFactory, uiWindow,cType);
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
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0){
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
    		/*
    		 * 资格考察的数据填充
    		 */
    		IRow row = null;
    		FDCSplQualificationAuditBillCollection stc = getSelectedCollection();
			Iterator it = (Iterator) stc.iterator();

	    	/*
	    	 * 迭代将查询的结果放置到表格中
	    	 */
			while(it.hasNext()){
				FDCSplQualificationAuditBillInfo info = (FDCSplQualificationAuditBillInfo)it.next();
				
				/*
				 * 定义分录的个数
				 */
				int billNum = 0;
				if(info !=null && info.getAuditResult() != null){
					billNum = info.getAuditResult().size();
				}
				
				/*
				 * 若存在分录则将相应的分录信息按行依次输出到表格中
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * 新增行
						 */
						row = this.tblMain.addRow();
						
						/*
						 * 根据单元格的名称,依次将值放置到单元格中,并排除为NULL的数据
						 */
						
						if(info !=null && info.getSupplier()!= null && info.getSupplier().getId() != null){
							row.getCell("SupplierID").setValue(info.getSupplier().getId());
						}else{
							row.getCell("SupplierID").setValue(null);
						}
						
						if(info !=null && info.getId().toString() != null){
							row.getCell("id").setValue(info.getId().toString());
						}else{
							row.getCell("id").setValue(null);
						}

						row.getCell("cType").setValue(getResource("review"));
						
						if(info !=null && info.getBusinessDate() != null){
							row.getCell("businessDate").setValue(info.getBusinessDate());
						}else{
							row.getCell("businessDate").setValue(null);
						}
						
						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getScore() != null){
							row.getCell("FScore").setValue(info.getAuditResult().get(k).getScore());
						}else{
							row.getCell("FScore").setValue(null);
						}
						
						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getSupplierType() != null && info.getAuditResult().get(k).getSupplierType().getName() != null){
							row.getCell("FName").setValue(info.getAuditResult().get(k).getSupplierType().getName());
						}else{
							row.getCell("FName").setValue(null);
						}

						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getGrade() != null){
							row.getCell("FState").setValue(info.getAuditResult().get(k).getGrade());
						}else{
							row.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * 若没有分录，则只输出主数据表的信息
				 */
				else{
					/*
					 * 新增行
					 */
					row = this.tblMain.addRow();
					
					if(info !=null && info.getSupplier()!= null && info.getSupplier().getId() != null){
						row.getCell("SupplierID").setValue(info.getSupplier().getId());
					}else{
						row.getCell("SupplierID").setValue(null);
					}
					
					if(info !=null && info.getId().toString() != null){
						row.getCell("id").setValue(info.getId().toString());
					}else{
						row.getCell("id").setValue(null);
					}
					
					row.getCell("cType").setValue(getResource("review"));
					
					if(info !=null && info.getBusinessDate() != null){
						row.getCell("businessDate").setValue(info.getBusinessDate());
					}else{
						row.getCell("businessDate").setValue(null);
					}
					
					row.getCell("FScore").setValue(null);
					row.getCell("FName").setValue(null);
					row.getCell("FState").setValue(null);
				}
			}
			
			/*
			 * 履约评估的数据填充
			 */
			IRow row2 = null;
			FDCSplKeepContractAuditBillCollection stc2 = getSelectedCollection2();
			Iterator it2 = (Iterator) stc2.iterator();

	    	/*
	    	 * 迭代将查询的结果放置到表格中
	    	 */
			while(it2.hasNext()){
				FDCSplKeepContractAuditBillInfo info2 = (FDCSplKeepContractAuditBillInfo)it2.next();
				
				/*
				 * 定义分录的个数
				 */
				int billNum = 0;
				if(info2 !=null && info2.getAuditBill() != null){
					billNum = info2.getAuditBill().size();
				}

				/*
				 * 若存在分录则将相应的分录信息按行依次输出到表格中
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * 新增行
						 */
						row2 = this.tblMain.addRow();
						
						if(info2 !=null && info2.getSupplier()!= null && info2.getSupplier().getId().toString() != null){
							row2.getCell("SupplierID").setValue(info2.getSupplier().getId().toString());
						}else{
							row2.getCell("SupplierID").setValue(null);
						}
						
						if(info2 !=null && info2.getId().toString() != null){
							row2.getCell("id").setValue(info2.getId().toString());
						}else{
							row2.getCell("id").setValue(null);
						}
						
						row2.getCell("cType").setValue(getResource("evaluate"));
						
						if(info2 !=null && info2.getBusinessDate() != null){
							row2.getCell("businessDate").setValue(info2.getBusinessDate());
						}else{
							row2.getCell("businessDate").setValue(null);
						}
						
						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getScore() != null){
							row2.getCell("FScore").setValue(info2.getAuditBill().get(k).getScore());
						}else{
							row2.getCell("FScore").setValue(null);
						}
						
						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getSupplierType() != null && info2.getAuditBill().get(k).getSupplierType().getName() != null){
							row2.getCell("FName").setValue(info2.getAuditBill().get(k).getSupplierType().getName());
						}else{
							row2.getCell("FName").setValue(null);
						}

						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getGrade() != null){
							row2.getCell("FState").setValue(info2.getAuditBill().get(k).getGrade());
						}else{
							row2.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * 若没有分录，则只输出主数据表的信息
				 */
				else{
					/*
					 * 新增行
					 */
					row2 = this.tblMain.addRow();

					if(info2 !=null && info2.getSupplier()!= null && info2.getSupplier().getId() != null){
						row2.getCell("SupplierID").setValue(info2.getSupplier().getId());
					}else{
						row2.getCell("SupplierID").setValue(null);
					}
					
					if(info2 !=null && info2.getId().toString() != null){
						row2.getCell("id").setValue(info2.getId().toString());
					}else{
						row2.getCell("id").setValue(null);
					}
					
					row2.getCell("cType").setValue(getResource("evaluate"));

					if(info2 !=null && info2.getBusinessDate() != null){
						row.getCell("businessDate").setValue(info2.getBusinessDate());
					}else{
						row.getCell("businessDate").setValue(null);
					}
					
					row2.getCell("FScore").setValue(null);
					row2.getCell("FName").setValue(null);
					row2.getCell("FState").setValue(null);
				}
			}
			
			/*
			 * 定期评审的数据填充
			 */
			IRow row3 = null;
			FDCSplPeriodAuditBillCollection stc3 = getSelectedCollection3();
			Iterator it3 = (Iterator) stc3.iterator();

	    	/*
	    	 * 迭代将查询的结果放置到表格中
	    	 */
			while(it3.hasNext()){
				FDCSplPeriodAuditBillInfo info3 = (FDCSplPeriodAuditBillInfo)it3.next();
				
				/*
				 * 定义分录的个数
				 */
				int billNum = 0;
				if(info3 !=null && info3.getAuditBill() != null){
					billNum = info3.getAuditBill().size();
				}
				
				/*
				 * 若存在分录则将相应的分录信息按行依次输出到表格中
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * 新增行
						 */
						row3 = this.tblMain.addRow();

						if(info3 !=null && info3.getSupplier()!= null && info3.getSupplier().getId() != null){
							row3.getCell("SupplierID").setValue(info3.getSupplier().getId());
						}else{
							row3.getCell("SupplierID").setValue(null);
						}
						
						if(info3 !=null && info3.getId().toString() != null){
							row3.getCell("id").setValue(info3.getId().toString());
						}else{
							row3.getCell("id").setValue(null);
						}
						
						row3.getCell("cType").setValue(getResource("syndic"));
						
						if(info3 !=null && info3.getBusinessDate() != null){
							row3.getCell("businessDate").setValue(info3.getBusinessDate());
						}else{
							row3.getCell("businessDate").setValue(null);
						}
						
						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getScore() != null){
							row3.getCell("FScore").setValue(info3.getAuditBill().get(k).getScore());
						}else{
							row3.getCell("FScore").setValue(null);
						}
						
						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getSupplierType() != null && info3.getAuditBill().get(k).getSupplierType().getName() != null){
							row3.getCell("FName").setValue(info3.getAuditBill().get(k).getSupplierType().getName());
						}else{
							row3.getCell("FName").setValue(null);
						}

						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getGrade() != null){
							row3.getCell("FState").setValue(info3.getAuditBill().get(k).getGrade());
						}else{
							row3.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * 若没有分录，则只输出主数据表的信息
				 */
				else{
						row3 = this.tblMain.addRow();

						if(info3 !=null && info3.getSupplier()!= null && info3.getSupplier().getId() != null){
							row3.getCell("SupplierID").setValue(info3.getSupplier().getId());
						}else{
							row3.getCell("SupplierID").setValue(null);
						}
						
						if(info3 !=null && info3.getId().toString() != null){
							row3.getCell("id").setValue(info3.getId().toString());
						}else{
							row3.getCell("id").setValue(null);
						}
						
						row3.getCell("cType").setValue(getResource("syndic"));

						if(info3 !=null && info3.getBusinessDate() != null){
							row3.getCell("businessDate").setValue(info3.getBusinessDate());
						}else{
							row3.getCell("businessDate").setValue(null);
						}
						
						row3.getCell("FScore").setValue(null);
						row3.getCell("FName").setValue(null);
						row3.getCell("FState").setValue(null);
				}
			}
			/*
			 * 对输出的格式进行页面转换
			 */
			this.tblMain.getColumn("businessDate").getStyleAttributes().setNumberFormat("yyyy-mm-dd");
			this.tblMain.getColumn("FScore").getStyleAttributes().setNumberFormat("#0.00");
			
			/*
			 * 将列的显示设置为居中
			 */
			this.tblMain.getColumn("cType").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("businessDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("FScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			this.tblMain.getColumn("FName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("FState").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			tblMain.getHeadStyleAttributes().setLocked(true);		
			
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
    /**
     * 
    * @description		过滤资格考察的相关信息
    * @author			田宝平	
    * @createDate		2010-11-27
    * @param	
    * @return			FDCSplQualificationAuditBillCollection		
    *	
    * @version			EAS7.0
    * @see
     */
	 protected FDCSplQualificationAuditBillCollection getSelectedCollection() throws EASBizException, BOSException
	 {
			/*
			 * 定义查询实体
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditResult.*"));	
			view.getSelector().add(new SelectorItemInfo("auditResult.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * 定义一个过滤条件，以添加主查询的查询条件
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * 状态为已审批
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * 将查询条件注入
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  设置按时间降序
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
		    
		    /*
		     * 返回查询的结果
		     */
		    return FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillCollection(view);
	}
	 
	 /**
	  * 
	 * @description		过滤履约评估的相关信息
	 * @author			田宝平	
	 * @createDate		2010-11-27
	 * @param	
	 * @return			FDCSplKeepContractAuditBillCollection		
	 *	
	 * @version			EAS7.0
	 * @see
	  */
	 protected FDCSplKeepContractAuditBillCollection getSelectedCollection2() throws EASBizException, BOSException
	 {
			/*
			 * 定义查询实体
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditBill.*"));
			view.getSelector().add(new SelectorItemInfo("auditBill.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * 定义一个过滤条件，以添加主查询的查询条件
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * 状态为已审批
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * 将查询条件注入
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  设置按时间降序
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
			
		    /*
		     * 返回查询的结果
		     */
		    return FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillCollection(view);
	}
	
	 /**
	  * 
	 * @description		过滤定期评审的相关信息
	 * @author			田宝平	
	 * @createDate		2010-11-27
	 * @param	
	 * @return			FDCSplPeriodAuditBillCollection		
	 *	
	 * @version			EAS7.0
	 * @see
	  */
	 protected FDCSplPeriodAuditBillCollection getSelectedCollection3() throws EASBizException, BOSException
	 {
			/*
			 * 定义查询实体
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditBill.*"));	
			view.getSelector().add(new SelectorItemInfo("auditBill.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * 定义一个过滤条件，以添加主查询的查询条件
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * 状态为已审批
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * 将查询条件注入
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  设置按时间降序
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
		    
		    /*
		     * 返回查询的结果
		     */
		    return FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillCollection(view);
	}

	public void onShow() throws Exception {
		/*
		 * 合并单元格
		 */
		unitTable();
	}
	
    /**
     * 
    * @description		合并单元格
    * @author			田宝平	
    * @createDate		2010-11-26
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
    public void unitTable() {
		KDTMergeManager mm =tblMain.getMergeManager();
		int longth = tblMain.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有行
			 */
			row = tblMain.getRow(i);
			
			/*
			 * 取得单元格id的值
			 */
			String type = (String) row.getCell("id").getValue();
			if (null == type) {
				continue;
			}
			if (map.get(type) == null) {
				map.put(type, Boolean.TRUE);
			}
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();
			int ben =getStartEnd( -1 , -1 , type, longth)[0];
			int end =getStartEnd( -1 , -1 , type, longth)[1];
            if(ben<end){
			  mm.mergeBlock(ben,0,end,0,KDTMergeManager.SPECIFY_MERGE);
            }
		}
	}
    
    /**
     * 
    * @description		获取要合并的行
    * @author			田宝平	
    * @createDate		2010-12-1
    * @param	
    * @return			int[]		
    *	
    * @version			EAS7.0
    * @see
     */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有行
			 */
			row = tblMain.getRow(i);
			
			/*
			 * 取得单元格id的值
			 */
			String Str = (String) row.getCell("id").getValue();
			if (null == Str) {
				continue;
			}
			if (Str.equals(type)) {
				if (ben < 0) {
					ben = i;
				} else {
					end = i;
				}
			}
		} 
       int obj [] = new int[2];
       obj[0]=ben;
       obj[1]=end;
       return obj;
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
    public AppraiseHistoryUI() throws Exception
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