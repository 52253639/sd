/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection;
import com.kingdee.eas.framework.ICoreBase;


/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商评审模版查询
 *		
 * @author		陈伟
 * @version		EAS7.0		
 * @createDate	2010-12-10	 
 * @see						
 */
public class F7TemplatelistUI extends AbstractF7TemplatelistUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8675512432578223722L;

	private static final Logger logger = CoreUIObject.getLogger(F7TemplatelistUI.class);
    
    /**
     * output class constructor
     */
    public F7TemplatelistUI() throws Exception
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
     * @description		鼠标事件
     * @author			陈伟		
     * @createDate		2010-11-17
     * @param	        
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(e.getType() > 0){//单击鼠标
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if(rowIndex>-1){//大于-1行激活确认事件
    			this.actionComfirm.setEnabled(true);
    		}else{
    			this.actionComfirm.setEnabled(false);
    		}
    	}
        super.tblMain_tableClicked(e);
    }

	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getBizInterface() throws Exception {
		
		return SupplierAppraiseTemplateFactory.getRemoteInstance();
	}

	private SupplierEvaluateEditUI evaluate = null;
	
	private SupplierPerformEvalulationEditUI supplierPerform = null;
	private SupplierQuaReviewEditUI qualificationReview = null;
	/*
	 * 1 为定期评审，2为履约评估，3为资格考察
	 */
	private int auditNumber = 0; 
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void onLoad() throws Exception { 
		super.onLoad();
		
		this.actionComfirm.setEnabled(false);
		this.actionReturn.setEnabled(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		iniauditUI();
		try {
			iniTblMainInfo();
		} catch (EASBizException e) {
              handleException(e);
              logger.error(e);
		} catch (BOSException e) {
			 handleException(e);
		}
	}
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void loadFields() { 
		super.loadFields();

	}
	/**
	 * @description		判断是什么评审单打开的
	 * @author			陈伟		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 * @修改人			胥凯、梁良
	 * @修改时间		2010-11-18
	 * @version			EAS1.0
	 * @see						
	 */
	private void iniauditUI() {
		Object obj = getUIContext().get("Evaluate");
		if(obj instanceof SupplierEvaluateEditUI){
			evaluate =(SupplierEvaluateEditUI) getUIContext().get("Evaluate");
			if(null != evaluate){
				auditNumber =1;
			}
		}else if(obj instanceof SupplierPerformEvalulationEditUI){
			supplierPerform = (SupplierPerformEvalulationEditUI) getUIContext().get("Evaluate");
			if(null != supplierPerform){
				auditNumber = 2;
			}
		}else if(obj instanceof SupplierQuaReviewEditUI){
			qualificationReview = (SupplierQuaReviewEditUI) getUIContext().get("Evaluate");
			if(null != qualificationReview){
				auditNumber = 3;
			}
		}
	}
	/**
	 * @description		确定事件
	 * @author			陈伟		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 * @修改人			胥凯、梁良
	 * @修改时间		2010-11-18
	 * @version			EAS1.0
	 * @see						
	 */
	public void actionComfirm_actionPerformed(ActionEvent e) throws Exception {
	    if(auditNumber == 1){
	    	evaluate.fillInTemplate(getSelectedInfo());
	    }else if(auditNumber == 2){
	    	supplierPerform.fillInTemplate(getSelectedInfo());
	    }else if(auditNumber == 3){
	    	qualificationReview.fillInTemplate(getSelectedInfo());
	    	
	    }
	    this.disposeUIWindow();
	}
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void onShow() throws Exception {
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	/**
	 * @description		获取所有模版
	 * @author			陈伟		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected SupplierAppraiseTemplateCollection getSelectedCollection() throws EASBizException, BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("state"));	 
		view.getSelector().add(new SelectorItemInfo("phase"));	 
        view.getSelector().add(new SelectorItemInfo("guideEntry.*"));     
        view.getSelector().add(new SelectorItemInfo("creator.*"));   
        view.getSelector().add(new SelectorItemInfo("guideEntry.splAuditIndex.*"));  
		FilterInfo filter = new FilterInfo();
		//filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
	    if(auditNumber == 1){
	     	filter.getFilterItems().add(new FilterItemInfo("phase", AppraisePhaseEnum.DEADLINE_VALUE));
		 }else if(auditNumber == 2){
			 filter.getFilterItems().add(new FilterItemInfo("phase", AppraisePhaseEnum.EVAL_VALUE)); 
		 }else  if(auditNumber == 3){
			 filter.getFilterItems().add(new FilterItemInfo("phase", AppraisePhaseEnum.QUAL_VALUE));
		 }	   
	    filter.getFilterItems().add(new FilterItemInfo("isStart", Boolean.TRUE));
	    view.setFilter(filter);
	  
        return SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateCollection(view);
 
	}
	/**
	 * @description		填充分录
	 * @author			陈伟		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @see						
	 */
	private void iniTblMainInfo() throws EASBizException, BOSException {
		 this.tblMain.checkParsed();
		 this.tblMain.removeRows();
		 this.tblMain.getStyleAttributes().setLocked(true);
		 IRow row = null;
		 SupplierAppraiseTemplateCollection stc =getSelectedCollection();
		 Iterator it = (Iterator)stc.iterator();
		 while(it.hasNext()){
			 SupplierAppraiseTemplateInfo info = (SupplierAppraiseTemplateInfo)it.next();
			 if(info.getGuideEntry().size() == 0  ){//没有分录的 不要加入
				 continue;
			 }
			 row = this.tblMain.addRow();
			 row.getCell("phase").setValue(info.getPhase()); 			 
			 row.getCell("isStart").setValue(Boolean.valueOf(info.isIsStart()));
			 row.getCell("state").setValue(info.getState());
			 row.getCell("name").setValue(info.getName());
			 row.getCell("createTime").setValue(info.getCreateTime());
			 row.getCell("id").setValue(info.getId().toString());
			 row.getCell("guideEntry").setValue(info.getGuideEntry()); 
			 
		 }

	}
	protected SupplierGuideEntryCollection getSelectedInfo() throws EASBizException, BOSException
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex <0){
			abort() ;
		}
		SupplierGuideEntryCollection sgec =(SupplierGuideEntryCollection) this.tblMain.getRow(rowIndex).getCell("guideEntry").getValue();
	
	
		return sgec ;
	}
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void actionReturn_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}

}