/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * 工程量确认单序时薄界面
 */
public class WorkLoadConfirmBillListUI extends AbstractWorkLoadConfirmBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkLoadConfirmBillListUI.class);
    //状态
    private static final String COL_STATE = "state";
    //业务日期
    private static final String COL_BIZDATE = "bizDate";
    //确认工程量
    private static final String COL_WORKLOAD = "workLoad";
    //确认日期
    private static final String COL_CONFIRMDATE = "confirmDate";
    //制单日期
    private static final String COL_CREATETIME  = "createTime";
    //确认期间
    private static final String COL_PERIOD = "period";
    //是否生成凭证
    private static final String COL_ISVOUCHERED = "isVouchered";
    //编码
    private static final String COL_NUMBER = "number";
    //乙方
    private static final String COL_PARTB = "partB";
    //制单人
    private static final String COL_CREATOR = "creator";
    //ID
    private static final String COL_ID = "id";
    //审批人
    private static final String COL_AUDITOR = "auditor";
    //审批时间
    private static final String COL_AUDITTIME = "audtiTime";
    //备注/参考信息
    private static final String COL_DESCRIPTION = "description";
    //拆分状态
    private static final String COL_SPLITSTATE = "splitState";
    /**
     * 启用暂缓
     */
    public static final String COL_ISRESPITE = "isRespite"; 
    
    /**
     * output class constructor
     */
    public WorkLoadConfirmBillListUI() throws Exception
    {
        super();
    }
    
    protected void tblWorkLoad_tableSelectChanged(KDTSelectEvent e) throws Exception {
		setActionVoucher();
		super.tblWorkLoad_tableSelectChanged(e);
		// 增加启用暂缓、取消暂缓
		IRow row;
		if (this.tblWorkLoad.getSelectManager().getActiveRowIndex() != -1) {
			if ((tblWorkLoad.getSelectManager().getBlocks().size() > 1)
					|| (e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)) {
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			} else {
				row = this.tblWorkLoad.getRow(this.tblWorkLoad.getSelectManager().getActiveRowIndex());
				String state = row.getCell(COL_STATE).getValue().toString();
				if (FDCBillStateEnum.SAVED.toString().equals(state)
						|| FDCBillStateEnum.SUBMITTED.toString().equals(state)
						|| FDCBillStateEnum.AUDITTING.toString().equals(state)) {
					if (Boolean.TRUE.equals(row.getCell("isRespite").getValue())) {
						actionRespite.setEnabled(false);
						actionCancelRespite.setEnabled(true);
					} else {
						actionRespite.setEnabled(true);
						actionCancelRespite.setEnabled(false);
					}
					if (tblMain.getSelectManager().getBlocks().size() == 1
							&& FDCBillStateEnum.AUDITTED.equals(row.getCell("state").getValue())) {
						actionRespite.setEnabled(false);
						actionCancelRespite.setEnabled(true);
					}
				} else {
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(false);
				}
			}
		}
	}
    
    /**
     * 设置生成凭证/删除凭证action
     * 如果fiVouchered为是，则禁用生成凭证，否则禁用删除凭证
     * @throws EASBizException
     * @throws BOSException
     */
    
    protected void setActionVoucher() throws EASBizException, BOSException{
    	String state = new String();
    	Boolean isVouchered = Boolean.FALSE;
    	String splitState = new String();
        int activedRowIndex = tblWorkLoad.getSelectManager().getActiveRowIndex();
        if(activedRowIndex < 0) {
        	actionVoucher.setEnabled(false);
        	actionDelVoucher.setEnabled(false);
        	actionSplit.setEnabled(false);
        	return;
        }
        state = tblWorkLoad.getRow(activedRowIndex).
        		getCell(COL_STATE).getValue().toString();
        isVouchered = (Boolean) tblWorkLoad.getRow(activedRowIndex).
        		getCell(COL_ISVOUCHERED).getValue();
        splitState = tblWorkLoad.getRow(activedRowIndex).
        		getCell(COL_SPLITSTATE).getValue().toString();
        if(state.equals(FDCBillStateEnum.AUDITTED.toString())&&
        		isVouchered.equals(Boolean.FALSE)){
        	actionVoucher.setEnabled(true);
  		    actionVoucher.setVisible(true);
  		    actionDelVoucher.setEnabled(false);
        }else if(isVouchered.equals(Boolean.TRUE)){
        	actionDelVoucher.setEnabled(true);
        	actionDelVoucher.setVisible(true);
        	actionVoucher.setEnabled(false);
        }else{
		    actionVoucher.setEnabled(false);
		    actionDelVoucher.setEnabled(false);  
        }
        if(splitState.equals(CostSplitStateEnum.NOSPLIT.toString()) &&
        		state.equals(FDCBillStateEnum.AUDITTED.toString()))
        	actionSplit.setEnabled(true);
        else actionSplit.setEnabled(false);
    }
    
   /**
     * 拆分按钮--
     * 若对应合同在待处理合同中，提示：对应合同还在待处理合同流程，未进行处理，不能进行本操作！
     * 确认单未审批，提示单据状态不适合。
     * 若对应的工程量确认单已审批单据未拆分，则链接到对应的工程量确认单拆分编辑界面。
     * 若对应的工程量确认单已审批单据拆分但拆分未被引用且为保存状态，则链接到对应的工程量确认单拆分编辑界面。
     * 否则，则链接到对应拆分的查看界面。
     * 
     * 	
	 * 工程量拆分，新需求拆分前要检查合同拆分和变更拆分是否已经审批。
	 * @Modified owen_wen 2010-12-01
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected(tblWorkLoad);      
               
		// Added By Owen_wen 2010-12-03          
        FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), this, true);//工程量拆分只有成本类，所以第3个参数传入true
        
//        选中的行号
        int actRowIdx = tblWorkLoad.getSelectManager().getActiveRowIndex();
        
//        若对应合同在待处理合同中，提示：对应合同还在待处理合同流程，未进行处理，不能进行本操作！
        WorkLoadConfirmBillInfo wlInfo = (WorkLoadConfirmBillInfo) tblWorkLoad.getRow(actRowIdx).getUserObject();
        if(ConSplitExecStateEnum.INVALID.equals(
        		wlInfo.getContractBill().getConSplitExecState())){
        	FDCMsgBox.showWarning("对应合同还在待处理合同流程，未进行处理，不能进行本操作！");
        	return;
        }
        
//      确认单未审批，提示单据状态不适合。
        if(!FDCBillStateEnum.AUDITTED.
        		equals(wlInfo.getState())){
        	FDCMsgBox.showWarning("该单据状态不适合拆分");
        }else{

        	IUIWindow splitUIwindow = null;
			UIContext uiContext = new UIContext(this);
			String uiName = "com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI";
	        EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.add("state");
	        sic.add("splitState");
	        sic.add("voucherRefer");
	        FilterInfo filter = new FilterInfo();
	        filter.getFilterItems().add(
	        		new FilterItemInfo("workLoadConfirmBill.id",wlInfo.getId()));
	        filter.getFilterItems().add(
	        		new FilterItemInfo("isWorkLoadBill",Boolean.TRUE));
	        filter.getFilterItems().add(
	        		new FilterItemInfo("isInvalid",Boolean.FALSE));
	        view.setSelector(sic);
	        view.setFilter(filter);
	        PaymentSplitCollection paymentColect = PaymentSplitFactory.getRemoteInstance().
	        		getPaymentSplitCollection(view);
	        PaymentSplitInfo paySplitInfo = new PaymentSplitInfo();
	        for(Iterator it = paymentColect.iterator();it.hasNext();){
	        	paySplitInfo = (PaymentSplitInfo)it.next();
	        }
	        if(paySplitInfo.getId()==null ){
	        	uiContext.put("costBillID",wlInfo.getId().toString());
				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
						create(uiName, uiContext, null,OprtState.ADDNEW);
				splitUIwindow.show();
	        }else if((paySplitInfo.getVoucherRefer() == null || 
	        		PaySplitVoucherRefersEnum.NOTREFER.equals(paySplitInfo.getVoucherRefer()))&& 
        			(FDCBillStateEnum.SAVED.equals(paySplitInfo.getState()))){
	        	uiContext.put(UIContext.ID,paySplitInfo.getId().toString());
				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
						create(uiName, uiContext, null,OprtState.EDIT);
				splitUIwindow.show();
	        }else{
	          	uiContext.put(UIContext.ID, paySplitInfo.getId().toString());
				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
						create(uiName, uiContext, null,OprtState.VIEW);
				splitUIwindow.show();
	        }
        }
	        
	        /**
	         * 第二次修改
	         */
	        
	        
//	        if(paySplitInfo.getId() ==null||					//已审批且拆分单中没有
//	        	((!CostSplitStateEnum.NOSPLIT.			    	//        若对应的工程量确认单已审批单据拆分但拆分未被引用且为保存状态，则链接到对应的工程量确认单拆分编辑界面。
//	        			equals(paySplitInfo.getSplitState())) &&((
//	        		PaySplitVoucherRefersEnum.NOTREFER.
//	        			equals(paySplitInfo.getVoucherRefer())) ||(
//	        		paySplitInfo.getVoucherRefer() == null))&&(
//	        		FDCBillStateEnum.SAVED.
//	        			equals(paySplitInfo.getState())))||(
//	        	CostSplitStateEnum.NOSPLIT.			//        若对应的工程量确认单已审批单据未拆分，则链接到对应的工程量确认单拆分编辑界面。
//	    	        	equals(paySplitInfo.getSplitState())
//	    	   )) {
//	        	uiContext.put("costBillID",wlInfo.getId().toString());
//				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//						create(uiName, uiContext, null,OprtState.EDIT);
//				splitUIwindow.show();
//	        }else{
//	        	uiContext.put(UIContext.ID, paySplitInfo.getId().toString());
//				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//						create(uiName, uiContext, null,OprtState.VIEW);
//				splitUIwindow.show();
//	        }
//        }
        
	        /**
	         * 最早修改
	         */
	        
//        
//        
//        
//        filter = new FilterInfo();
//        
//        ICell cell_Id = tblWorkLoad.getRow(	actRowIdx).
//        		getCell(COL_ID);
//        
//        
//        filter.getFilterItems().add(
//        		new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
//        if(WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter)){
//        	filter = new FilterInfo();
//        	filter.getFilterItems().add(
//        			new FilterItemInfo("workLoadConfirmBill",cell.getValue().toString(),CompareType.EQUALS));
//        	filter.getFilterItems().add(
//        			new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
//    		
//			
//        	if(PaymentSplitFactory.getRemoteInstance().exists(filter)){
//    			try {
//    				EntityViewInfo view = new EntityViewInfo();
//    				view.getSelector().add("id");
//    				filter = new FilterInfo();
//    				filter.getFilterItems().add(
//    						new FilterItemInfo("workLoadConfirmBill.id",cell.getValue().toString(),CompareType.EQUALS));
//    				view.setFilter(filter);
//    				PaymentSplitCollection paymentSpllitInfos = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(view);
//    				uiContext.put(UIContext.ID, paymentSpllitInfos.get(0).getId().toString());
//    				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//    						create(uiName, uiContext, null,OprtState.VIEW);
//    				splitUIwindow.show();
//    			} catch (Exception e1) {
//    				handUIException(e1);
//    				return;
//    			}
//        	}else{
//        		try {
////        			int tblMainActRow = tblMain.getSelectManager().getActiveRowIndex();
////        			String contractId = tblMain.getRow(tblMainActRow).getCell(COL_ID).getValue().toString();
////        			uiContext.put("contractId", contractId);
//        			
//    			} catch (Exception e1) {
//    				handUIException(e1);
//    				return;
//    			}
//        	}
//        }else{
//        	FDCMsgBox.showWarning("该单据状态不适合拆分");
//        }
    }

	protected void audit(List ids) throws Exception {
		WorkLoadConfirmBillFactory.getRemoteInstance().audit(ids);		
	}

	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("curProject.name");
		selectors.add("contractBill.name");
		selectors.add("contractBill.partB.name");
		selectors.add("orgUnit.displayName");
		selectors.add("curProject.displayName");
		selectors.add("period.number");
		selectors.add("period.periodYear");
		selectors.add("period.periodNumber");
		selectors.add("confirmDate");
		selectors.add("number");
		selectors.add("state");
		selectors.add("bizDate");
		selectors.add("workLoad");
		selectors.add("creator.name");
		selectors.add("createTime");
		selectors.add("fiVouchered");
		selectors.add("voucher.id");
		selectors.add("voucher.number");
		selectors.add("description");
		selectors.add("id");
		selectors.add("auditor.name");
		selectors.add("auditTime");
		selectors.add("contractBill.id");
		selectors.add("splitState");
		selectors.add("contractBill.conSplitExecState");
		selectors.add("costSplit.voucherRefer");
		selectors.add("isRespite");
		return selectors;
	}

	protected KDTable getBillListTable() {
		return tblWorkLoad;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WorkLoadConfirmBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillEditUI.class.getName();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return WorkLoadConfirmBillFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return true;
	}

	
	protected void unAudit(List ids) throws Exception {
		WorkLoadConfirmBillFactory.getRemoteInstance().unAudit(ids);		
	}
	
    protected String getBillStatePropertyName() {
    	return "state";
    }
    
	public void initUIContentLayout() {
		super.initUIContentLayout();
				
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}
    
	protected void initTable() {
		super.initTable();

		FDCHelper.formatTableNumber(tblWorkLoad, COL_WORKLOAD);
		FDCHelper.formatTableDate(tblWorkLoad,COL_BIZDATE);
		FDCHelper.formatTableDate(tblWorkLoad,COL_CONFIRMDATE);
		FDCHelper.formatTableDate(tblWorkLoad,COL_CREATETIME);
		tblWorkLoad.getColumn(COL_BIZDATE).setWidth(80);
		tblWorkLoad.getColumn(COL_PERIOD).setWidth(80);
		tblWorkLoad.getColumn(COL_CONFIRMDATE).setWidth(80);
		tblWorkLoad.getColumn(COL_STATE).setWidth(80);
		tblWorkLoad.getColumn(COL_ISVOUCHERED).setWidth(80);
		tblWorkLoad.getColumn(COL_NUMBER).setWidth(120);
		tblWorkLoad.getColumn(COL_WORKLOAD).setWidth(80);
		tblWorkLoad.getViewManager().setFreezeView(-1,tblWorkLoad.getColumnIndex(COL_WORKLOAD) +1 );
		
	}
	
	//重载合同过滤条件 增加对进入动态成本的判断
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		//只显示进入动态成本合同
		filterItems.add(new FilterItemInfo("isCoseSplit",Boolean.TRUE));
		return filter;
	}
	
	//根据合同显示工程量清单
	protected boolean displayBillByContract(KDTSelectEvent e, EntityViewInfo view) throws BOSException {
	    BigDecimal workLoadAll = FDCHelper.ZERO;
		if(view==null){
			return false ;
		}
		int pre = getPre(e);
		//设置精度
		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		getBillListTable().getColumn(COL_WORKLOAD).getStyleAttributes().setNumberFormat(oriFormat);
		WorkLoadConfirmBillCollection  coll = WorkLoadConfirmBillFactory.getRemoteInstance()
			.getWorkLoadConfirmBillCollection(view);
		
		WorkLoadConfirmBillInfo info;
		IRow row;
		for(Iterator it = coll.iterator();it.hasNext();){
			info = (WorkLoadConfirmBillInfo)it.next();
			row = getBillListTable().addRow();
			if(info.getContractBill()!=null ){
				if(info.getContractBill().getPartB() != null)
					row.getCell(COL_PARTB).setValue(info.getContractBill().getName());
			}
			if(info.getPeriod()!=null){
					row.getCell(COL_PERIOD).setValue(info.getPeriod());
			}
			if(info.getCreator() != null){
					row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			}
			if(info.getAuditor()!=null){
				row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
			}
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_STATE).setValue(info.getState());
			row.getCell(COL_BIZDATE).setValue(info.getBizDate());
			row.getCell(COL_CONFIRMDATE).setValue(info.getConfirmDate());
			row.getCell(COL_CREATETIME).setValue(info.getCreateTime());
			row.getCell(COL_WORKLOAD).setValue(info.getWorkLoad());
			workLoadAll =  FDCHelper.add(workLoadAll, info.getWorkLoad());
			row.getCell(COL_ISVOUCHERED).setValue(new Boolean(info.isFiVouchered()));
			row.getCell(COL_ID).setValue(info.getId().toString());
			row.getCell(COL_AUDITTIME).setValue(info.getAuditTime());
			row.getCell(COL_DESCRIPTION).setValue(info.getDescription());
			row.getCell(COL_SPLITSTATE).setValue(info.getSplitState());
			row.getCell(COL_ISRESPITE).setValue(new Boolean(info.isIsRespite()));
			row.setUserObject(info);
		}
//		KDTFootManager footManager = tblWorkLoad.getFootManager();
//		footManager = new KDTFootManager(tblWorkLoad);
//		footManager.addFootView();
//		IRow footRow = footManager.addFootRow(tblWorkLoad.getRowCount());
//		footRow.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
//		footRow.getCell(COL_WORKLOAD).setValue(workLoadAll);
//		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
//		footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		KDTFootManager footRowManager= tblWorkLoad.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			footRowManager = new KDTFootManager(tblWorkLoad);
			footRowManager.addFootView();
			tblWorkLoad.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			tblWorkLoad.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			tblWorkLoad.getIndexColumn().setWidth(60);
			footRow.getCell(COL_WORKLOAD).setValue(workLoadAll);
			footRow.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//设置到第一个可视行
			footRowManager.addIndexText(0, "合计");
		}else{
			footRow=tblWorkLoad.getFootRow(0);
			footRow.getCell(COL_WORKLOAD).setValue(workLoadAll);
//			if(footRow.getUserObject()==null){
//				footRow=tblWorkLoad.addFootRow(1);
//			};
		}
		
		
		return true;
	}
	
	public void onLoad() throws Exception {
		//检查是否启用了参数，待做了参数后放开。
//		checkWorkLoadParam();
		String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		if(!FDCUtils.getDefaultFDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
			FDCMsgBox.showWarning(this, "未启用“工程量确认流程与付款流程分离”参数，不能使用本功能！");
			SysUtil.abort();
		}
		super.onLoad();
		this.actionSplit.setEnabled(true);
		this.actionSplit.setVisible(true);
		this.actionTraceDown.setEnabled(true);
		this.actionTraceDown.setVisible(true);
		setActionVoucher();
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		//增加启用暂缓，取消暂缓按钮
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}
	/**
	 * 检查是否启用了参数“工程量确认流程与付款流程分离”，否则提示无法打开。(参数还未确认，待后期添加上)
	 */	
	protected void checkWorkLoadParam() {
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		boolean isWorkLoadParam = false;
		try {
			isWorkLoadParam = FDCUtils.getDefaultFDCParamByKey(null, companyID, "");
		} catch (EASBizException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			this.handUIException(e);
		} catch (BOSException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			this.handUIException(e);
		}
		if(!isWorkLoadParam){
			MsgBox.showError(this,"没有启用参数\"工程量确认流程与付款流程分离\",无法打开！");
			SysUtil.abort();
		}
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}
	
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(this.tblMain);
    	String contractID = (String)getSelectedFieldValues(COL_ID).get(0);
    	if(hasSettledWorkLoad(contractID)) return;
    	if(hasUnauditBill(contractID))	return;
		super.actionAddNew_actionPerformed(e);
	}

    /**
     * 判断是否有结算后生成的工程量清单
     * @param contractID
     * @return
     */
	protected boolean hasSettledWorkLoad(String contractID) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("hasSettled",Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractID));
		try {
			if(WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter)){
				com.kingdee.eas.util.client.MsgBox.showWarning(this,"已根据合同结算确认工程量，不允许新增！");
				SysUtil.abort();
				return true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected IObjectValue createNewData() {
		WorkLoadConfirmBillInfo info = new WorkLoadConfirmBillInfo();
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		info.setCreator(user);
		info.setCreateTime(new Timestamp(new Date().getTime()));
		info.setOrgUnit(orgUnit);
		return info;
	}

	protected void fetchInitData() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");

		Map initparam = new HashMap();
		if(contractBillId!=null){
			initparam.put("contractBillId",contractBillId);
		}
		super.fetchInitData();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		
		checkSelected(tblWorkLoad);
	}
	
	/**
	 * 该合同是否有结算后未审批的工程量清单
	 * @param contractID
	 * @return
	 */
	protected boolean hasUnauditBill(String contractID) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.hasSettled",Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractID));
		try {
			if(WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter)){
				com.kingdee.eas.util.client.MsgBox.showWarning(this,"存在未审批的工程量清单，请先审批之后再新增合同结算后的工程量确认单！");
				SysUtil.abort();
				return true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return false;
	}
    
	/**
	 * 返回定位字段的集合
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate"};
	}
	
	/**
	 * 获取选中行的合同id
	 * @return
	 * @author owen_wen 2010-11-30
	 */
	private String getContractBillId(){
		this.checkSelected();
		int selectedRowIndex = this.getMainTable().getSelectManager().getActiveRowIndex();
		String contractId = this.getMainTable().getRow(selectedRowIndex).getCell("id").getValue().toString();
		return contractId;
	}	
	
	protected FilterInfo getOtherFilter4Query() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.TRUE));
		return filter;
	}
	
	protected boolean isOnlyQueryAudited() {
		return true;
	}
}