/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskInfo;
import com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection;
import com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryInfo;
import com.kingdee.eas.fdc.pm.ProjectFillBillCollection;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryInfo;
import com.kingdee.eas.fdc.pm.ProjectFillBillInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountBillFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 工程量确认单
 */
public class WorkLoadConfirmBillEditUI extends AbstractWorkLoadConfirmBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkLoadConfirmBillEditUI.class);
    
    private static final String COL_ACCOUNT = "account";
    
    private static final String COL_COSTACCOUNT = "costAccount";
    
    private static final String COL_AMOUNT = "amount";
    
    private static final String COL_CURPROJECT = "curProject";
    
    private Map alreadySelected;
    private Map allCompleteAmt;
    /**
     * output class constructor
     */
    public WorkLoadConfirmBillEditUI() throws Exception
    {
        super();
        alreadySelected = new HashMap();
        allCompleteAmt = new HashMap();
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtContractBill,txtNumber,txtWorkLoad}));
    }
    
    protected boolean isBaseTask() throws EASBizException, BOSException
{
    boolean isBaseTask = false;
    isBaseTask = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), "FDCSCH04_BASEONTASK");
    return isBaseTask;
}

 protected boolean isBaseContract() throws EASBizException, BOSException
{
    boolean isBaseContract = false;
    isBaseContract = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), "FDCSCH003");
    return isBaseContract;
}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        this.editData.setName(this.editData.getNumber());
        try {
			if(isBaseContract())
			{
				WorkLoadPrjBillEntryCollection prjEntryCol = editData.getPrjFillBillEntry();
			    prjEntryCol.clear();
			    for(int i=0;i<tblDetail.getRowCount();i++){
			    	WorkLoadPrjBillEntryInfo info = new WorkLoadPrjBillEntryInfo();
			    	IRow row = tblDetail.getRow(i);
			    	if(row.getCell("id") != null && row.getCell("id").getValue() != null){
			    		info.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			    	}else{
			    		info.setId(null);
			    	}
			    	if(row.getCell("fillBillId") != null && row.getCell("fillBillId").getValue() != null){
			    		ProjectFillBillInfo fillBillInfo = new ProjectFillBillInfo();
			    		fillBillInfo.setId(BOSUuid.read(row.getCell("fillBillId").getValue().toString()));
			    		info.setPrjFillBill(fillBillInfo);
			    	}else{
			    		info.setId(null);
			    	}
			    	info.setParent(editData);
			    	prjEntryCol.add(info);
			    }

			}
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (UuidException e) {
			handUIException(e);
		}

		try {
			if(isBaseTask())
			{
			    ContractBillInfo contractBill = editData.getContractBill();
			    WorkLoadConfirmBillRelTaskCollection entrys = editData.getReftaskentry();
			    entrys.clear();
			    WorkLoadConfirmBillRelTaskInfo inf = null;
			    for(int i = 0; i < tblDetail.getRowCount(); i++)
			    {
			        inf = new WorkLoadConfirmBillRelTaskInfo();
			        inf.setContractBill(contractBill);
			        inf.setWorkamountEntry((WorkAmountEntryInfo)tblDetail.getRow(i).getUserObject());
			        entrys.add(inf);
			    }

			}
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
     
    }

    
    private void initTableStyle()
    {
        KDTable t = tblDetail;
        t.checkParsed();
        t.getColumn("id").getStyleAttributes().setHided(true);
        t.getColumn("number").getStyleAttributes().setHided(true);
        String frmStr = "#,###.00";
        t.getColumn("completeAmt").getStyleAttributes().setNumberFormat(frmStr);
        t.getColumn("completePct").getStyleAttributes().setNumberFormat(frmStr);
        t.getColumn("accWorkLoadAmt").getStyleAttributes().setNumberFormat(frmStr);
        t.getColumn("accWorkLoadPct").getStyleAttributes().setNumberFormat(frmStr);
    }

    /**
	 * 基于进度中任务的填报方式
	 * 
	 * @param entryList
	 * @param accMap
	 */
    private void fillPrjTable(java.util.List entryList, Map accMap)
    {
        BigDecimal totalAmt = FDCHelper.ZERO;
        tblDetail.removeRows();
        BigDecimal completeArray[] = (BigDecimal[])null;
        BigDecimal percent = FDCHelper.ZERO;
        BigDecimal amount = FDCHelper.ZERO;
        initTableStyle();
        if(entryList != null)
        {
            for(int i = 0; i < entryList.size(); i++)
            {
                WorkAmountEntryInfo entryInfo = (WorkAmountEntryInfo)entryList.get(i);
                totalAmt = FDCHelper.add(totalAmt, entryInfo.getConfirmAmount());
                IRow row = tblDetail.addRow();
                row.setUserObject(entryInfo);
                row.getCell("id").setValue(entryInfo.getId());
                row.getCell("fillDate").setValue(entryInfo.getParent().getBizDate());
                row.getCell("task.number").setValue(entryInfo.getTask().getNumber());
                row.getCell("task.name").setValue(entryInfo.getTask().getName());
                row.getCell("completeAmt").setValue(entryInfo.getConfirmAmount());
                row.getCell("completePct").setValue(entryInfo.getConfirmPercent());
                row.getCell("task.id").setValue(entryInfo.getTask().getId().toString());
                row.getCell("description").setValue(entryInfo.getRemark());
                if(accMap != null && accMap.containsKey(entryInfo.getTask().getId().toString()))
                {
                    completeArray = (BigDecimal[])accMap.get(entryInfo.getTask().getId().toString());
                    amount = completeArray[0];
                    percent = completeArray[1];
                    row.getCell("accWorkLoadAmt").setValue(amount);
                    row.getCell("accWorkLoadPct").setValue(percent);
                }
            }

        }
        KDTFootManager footManager = tblDetail.getFootManager();
        if(footManager == null)
            addFootRow();
        IRow footRow = tblDetail.getFootRow(0);
        footRow.getCell("completeAmt").setValue(totalAmt);
        if (entryList != null && entryList.size() > 0) {
			txtWorkLoad.setValue(totalAmt);
		}
    }


    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	/**
    	 * 判断合同金额是否已经大于当前工程量确认单的工程量
    	 */
    	
        super.actionAudit_actionPerformed(e);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	if(STATUS_VIEW.equals(getOprtState())){
//    		return;
    	}
    	String contractID = editData.getContractBill().getId().toString();
    	if(hasSettledWorkLoad(contractID)) return;
    	if(hasUnauditBill())	return;
    	super.actionAddNew_actionPerformed(e);
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	if(FDCHelper.isEmpty(this.pkConfirmDate.getValue())){
    		FDCMsgBox.showWarning("确认日期不能为空！");
    		SysUtil.abort();
    		return;
    	}
    	if(FDCHelper.isEmpty(this.txtWorkLoad.getText())){
    		FDCMsgBox.showWarning("确认工程量不能为空！");
    		SysUtil.abort();
    		return;
    	}
    	if(hasSettledWorkLoad(editData.getContractBill().
    			getId().toString()))
    		return;
    	if(hasUnauditBill()) return;
    	super.actionSave_actionPerformed(e);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	if(FDCHelper.isEmpty(this.pkConfirmDate.getValue())){
    		FDCMsgBox.showWarning("确认日期不能为空！");
    		SysUtil.abort();
    		return;
    	}
    	if(FDCHelper.isEmpty(this.txtWorkLoad.getText())){
    		FDCMsgBox.showWarning("确认工程量不能为空！");
    		SysUtil.abort();
    		return;
    	}
//    	if(hasSettledWorkLoad(editData.getContractBill().
//    			getId().toString()))
//    		return;
//    	if(hasUnauditBill()) return;
    	checkBeforSubmit();
    	super.actionSubmit_actionPerformed(e);
    	if(editData.getId() == null || editData.getId().toString()==null){
    		tblDetail.removeRows();
    	}
    }
    
    private void checkBeforSubmit(){
    	Set prjFillIds = new HashSet();
//    	WorkLoadPrjBillEntryCollection prjFillEntryCol = editData.getPrjFillBillEntry();
    	for(int i=0;i<tblDetail.getRowCount();i++){
    		if(tblDetail.getCell(i, "fillBillId") != null && tblDetail.getCell(i, "fillBillId").getValue() != null){
    			prjFillIds.add(tblDetail.getCell(i, "fillBillId").getValue().toString());
    		}
    	}
    	prjFillIds.remove(null);
    	if(prjFillIds.size() < 1) return;
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select fillBill.FNumber FILLNUMBER, head.Fnumber HEADNUMBER from T_FPM_ProjectFillBill fillBill" +
    			"	inner join T_FNC_WorkLoadPrjBillEntry entry on entry.FPrjFillBillID=fillBill.FID" +
    			"	inner join T_FNC_WorkLoadConfirmBill head on entry.FParentID=head.FID" +
    			"	where ");
    	builder.appendParam("fillBill.FID",prjFillIds.toArray());
    	if(editData.getId() != null){
    		builder.appendSql(" and head.FID !=?");
    		builder.addParam(editData.getId().toString());
    	}
    	try {
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.size() > 0){
				String msg = "所选工程量填报单被其他工程量确认单关联：\n";
				while(rowSet.next()){
					msg = msg + "【"+rowSet.getString("FILLNUMBER") +"】被【"+  rowSet.getString("HEADNUMBER")+"】关联；\n";
				}
				FDCMsgBox.showWarning(msg);
				SysUtil.abort();
			}
		} catch (BOSException e) {
			this.handleException(e);
		} catch (SQLException e) {
			this.handleException(e);
		}
    }
    
    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        
    	super.actionUnAudit_actionPerformed(e);
    }

	protected void attachListeners() {
		pkBizDate.addDataChangeListener(bizDateChangeListener);
		
	}

	protected void detachListeners() {
		pkBizDate.removeDataChangeListener(bizDateChangeListener);
		
	}

	protected ICoreBase getBizInterface() throws Exception {

		return WorkLoadConfirmBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return tblEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void onLoad() throws Exception {
		txtWorkLoad.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWorkLoad.setPrecision(2);
		txtWorkLoad.setMaximumValue(FDCHelper.MAX_VALUE);
		txtWorkLoad.setMinimumValue(FDCHelper.MIN_VALUE);
		super.onLoad();
		tblDetail.setEditable(false);
		initCtrl();
		handleOldData();
		addFootRow();
	}
	
	
    /**
     * 拆分按钮--
     * 若对应合同在待处理合同中，提示：对应合同还在待处理合同流程，未进行处理，不能进行本操作！
     * 确认单未审批，提示单据状态不适合。
     * 若对应的工程量确认单已审批单据未拆分，则链接到对应的工程量确认单拆分编辑界面。
     * 若对应的工程量确认单已审批单据拆分但拆分未被引用且为保存状态，则链接到对应的工程量确认单拆分编辑界面。
     * 否则，则链接到对应拆分的查看界面。
     */
	
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
//		FilterInfo filter = new FilterInfo();
//        filter.getFilterItems().add(
//        		new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
//        if(WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter)){
//        	filter = new FilterInfo();
//        	filter.getFilterItems().add(
//        			new FilterItemInfo("workLoadConfirmBill",editData.getId(),CompareType.EQUALS));
//        	filter.getFilterItems().add(
//        			new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
//    		IUIWindow splitUIwindow = null;
//			UIContext uiContext = new UIContext(this);
//			String uiName = "com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI";
//			
//        	if(PaymentSplitFactory.getRemoteInstance().exists(filter)){
//    			try {
//    				EntityViewInfo view = new EntityViewInfo();
//    				view.getSelector().add("id");
//    				filter = new FilterInfo();
//    				filter.getFilterItems().add(
//    						new FilterItemInfo("workLoadConfirmBill.id",editData.getId(),CompareType.EQUALS));
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
//        			uiContext.put("costBillID",editData.getId().toString());
//    				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//    						create(uiName, uiContext, null,OprtState.ADDNEW);
//    				splitUIwindow.show();
//    			} catch (Exception e1) {
//    				handUIException(e1);
//    				return;
//    			}
//        	}
//        }else{
//        	FDCMsgBox.showWarning("该单据状态不适合拆分");
//        }
		if(ConSplitExecStateEnum.INVALID_VALUE.equals(
				editData.getContractBill().getConSplitExecState())){
        	FDCMsgBox.showWarning("对应合同还在待处理合同流程，未进行处理，不能进行本操作！");
        	return;
        }
        
//      确认单未审批，提示单据状态不适合。
        if(!FDCBillStateEnum.AUDITTED.
        		equals(editData.getState())){
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
	        		new FilterItemInfo("workLoadConfirmBill.id",editData.getId()));
	        filter.getFilterItems().add(
	        		new FilterItemInfo("isWorkLoadBill",Boolean.TRUE));
	        view.setSelector(sic);
	        view.setFilter(filter);
	        PaymentSplitCollection paymentColect = PaymentSplitFactory.getRemoteInstance().
	        		getPaymentSplitCollection(view);
	        PaymentSplitInfo paySplitInfo = new PaymentSplitInfo();
	        for(Iterator it = paymentColect.iterator();it.hasNext();){
	        	paySplitInfo = (PaymentSplitInfo)it.next();
	        }
	        if (paySplitInfo.getId()==null ){
	        	uiContext.put("costBillID",editData.getId().toString());
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
	        
	        
	        
//	        if(paySplitInfo.getId() ==null||					//已审批且拆分单中没有
//	        	((!CostSplitStateEnum.NOSPLIT.			    	//        若对应的工程量确认单已审批单据拆分但拆分未被引用且为保存状态，则链接到对应的工程量确认单拆分编辑界面。
//	        			equals(paySplitInfo.getSplitState())) &&((
//	        		PaySplitVoucherRefersEnum.NOTREFER.
//	        			equals(paySplitInfo.getVoucherRefer())) ||(
//	        		paySplitInfo.getVoucherRefer()==null))&&(
//	        		FDCBillStateEnum.SAVED.
//	        			equals(paySplitInfo.getState())))||(
//	        	CostSplitStateEnum.NOSPLIT.			//        若对应的工程量确认单已审批单据未拆分，则链接到对应的工程量确认单拆分编辑界面。
//	    	        	equals(paySplitInfo.getSplitState())
//	    	   )) {
//	        	uiContext.put("costBillID",editData.getId().toString());
//				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//						create(uiName, uiContext, null,OprtState.ADDNEW);
//				splitUIwindow.show();
//	        }else{
//	        	uiContext.put(UIContext.ID, paySplitInfo.getId().toString());
//				splitUIwindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).
//						create(uiName, uiContext, null,OprtState.VIEW);
//				splitUIwindow.show();
//	        }
        }
		
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);
		this.pkConfirmDate.setRequired(true);
		this.txtWorkLoad.setRequired(true);
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionConPrjBill.setEnabled(true);
//		txtWorkLoad.addDataChangeListener(new DataChangeListener(){
//			public void dataChanged(DataChangeEvent eventObj) {
//				if(txtWorkLoad.getText().length()>11){
//					BigDecimal number = FDCHelper.ZERO;
//					if(txtWorkLoad.getText().startsWith("-")) 
//						number = new BigDecimal(txtWorkLoad.getText().substring(1));
//					else number = new BigDecimal(txtWorkLoad.getText());
//					if(FDCHelper.isPositiveBigDecimal(
//							number.subtract(new BigDecimal("2147483647")))){
//						FDCMsgBox.showWarning("输入数字过大/小");
//					}
//					}
//				}
//				
//		});
	}
	
		
	private void initCtrl() throws Exception {
		this.prmtContractBill.setEnabled(false);
		this.prmtPartB.setEnabled(false);
		this.prmtPeriod.setEnabled(false);
		this.contPeriod.setEnabled(false);
		this.txtCurProject.setEnabled(false);
		this.txtNumber.setRequired(true);
		this.txtWorkLoad.setRequired(true);
		this.prmtCreator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.menuSubmitOption.setEnabled(false);
		this.menuTable1.setVisible(false);
		this.menuItemCopyFrom.setVisible(false);
		this.prmtContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");
		this.prmtPartB.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
		
		this.actionSplit.setEnabled(false);
		this.actionAudit.setEnabled(true);
		this.btnAudit.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.menuItemAudit.setEnabled(true);
		this.menuItemUnAudit.setVisible(true);
		this.menuItemUnAudit.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionVoucher.setEnabled(true);
		this.actionVoucher.setVisible(true);
		this.actionDelVoucher.setEnabled(true);
		this.actionDelVoucher.setVisible(true);
		this.pkConfirmDate.setRequired(true);
		this.txtWorkLoad.setRequired(true);
		
//		根据单据状态判断生成/删除凭证、拆分按钮的显示与否
		if(STATUS_ADDNEW.equals(getOprtState()) ||
			STATUS_EDIT.equals(getOprtState())) 
		{
			this.actionVoucher.setEnabled(false);
			this.actionDelVoucher.setEnabled(false);
			this.actionSplit.setEnabled(false);
		}else {
			if(editData.isFiVouchered()){
				this.actionVoucher.setEnabled(false);
				this.actionDelVoucher.setEnabled(true);
				this.actionDelVoucher.setVisible(true);
			}else if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
				this.actionVoucher.setEnabled(true);
				this.actionVoucher.setVisible(true);
				this.actionDelVoucher.setEnabled(false);
			}else{
				this.actionVoucher.setEnabled(false);
				this.actionDelVoucher.setEnabled(false);
			}
			if(CostSplitStateEnum.NOSPLIT.
					equals(editData.getSplitState())&&
				FDCBillStateEnum.AUDITTED.
					equals(editData.getState()))
				actionSplit.setEnabled(true);
	        else 
	        	actionSplit.setEnabled(false);
		}
		if(getUIContext().get("WorkLoadFullListUI") != null){
			actionAddNew.setEnabled(false);
		}
	}
	
	protected IObjectValue createNewData(){
		WorkLoadConfirmBillInfo info = new WorkLoadConfirmBillInfo();
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		info.setCreator(user);
		info.setCreateTime(new Timestamp(serverDate.getTime()));
		info.setBizDate(bookedDate);
		info.setOrgUnit(orgUnitInfo);
		info.setCurProject(curProject);
		info.setContractBill(contractBill);
		info.setConfirmDate(serverDate);
		info.setPeriod(curPeriod);
		prmtPartB.setValue(contractBill.getPartB());
		if(contractBill.isHasSettled()){
			try {
				BigDecimal workLoad = WorkLoadConfirmBillFactory.getRemoteInstance().getWorkLoad(contractBill.getId().toString());
				info.setWorkLoad(FDCHelper.subtract(contractBill.getSettleAmt(), workLoad));
				txtWorkLoad.setEditable(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			info.setHasSettled(true);
		}else info.setHasSettled(false);
		return info;
	}

	protected void fetchInitData() throws Exception {
		//合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		
		Map param = new HashMap();
		param.put("contractBillId",contractBillId);
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		
		param.put("isCost", Boolean.TRUE);
		
		//RPC从response获取
		Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
		if(initData==null){
			initData = ((IFDCBill)getBizInterface()).fetchInitData(param);
		}

		//本位币
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//本位币
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//合同单据
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//当前日期
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//是否已经冻结
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//可录入期间
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//工程项目
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
		//当前服务器日期
		serverDate = (Date)initData.get("serverDate");
		if(serverDate==null){
			serverDate = bookedDate;
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("costVoucherEntrys.amount");
		sic.add("costVoucherEntrys.curProject.name");
		sic.add("costVoucherEntrys.accountView.name");
		sic.add("costVoucherEntrys.costAccount.name");
		sic.add("splitState");
		sic.add("fiVouchered");
		sic.add("contractBill.id");
		sic.add("contractBill.conSplitExecState");
		sic.add("contractBill.hasSettled");
		sic.add("contractBill.codingNumber");
		sic.add("costSplit.voucherRefer");
		sic.add("prjFillBillEntry.prjFillBill");
		sic.add("curProject.longNumber");
		sic.add("curProject.codingNumber");
		sic.add("curProject.name");
		sic.add("curProject.id");
		
		return sic;
	}

	public void loadFields() {
		//注销监听器
		detachListeners();
		super.loadFields();
		if(editData != null && editData.getContractBill() != null){
			this.prmtPartB.setValue(editData.getContractBill().getPartB());
		}
		if((editData != null) && (editData.getState() == FDCBillStateEnum.AUDITTED)){
			this.actionEdit.setEnabled(false);
			this.actionEdit.setVisible(false);
			this.actionRemove.setEnabled(false);
			this.actionRemove.setVisible(false);
		}
		
		/**
		 * 工程量填报单分录开始
		 */
		 try
	        {
	            if(isBaseTask())
	            {
	                String billId = null;
	                if(editData.getId() != null)
	                    billId = editData.getId().toString();
	                EntityViewInfo viewInfo = new EntityViewInfo();
	                SelectorItemCollection sic = new SelectorItemCollection();
	                sic.add(new SelectorItemInfo("*"));
	                sic.add(new SelectorItemInfo("workamountEntry.task.number"));
	                sic.add(new SelectorItemInfo("workamountEntry.parent.bizDate"));
	                sic.add(new SelectorItemInfo("workamountEntry.id"));
	                sic.add(new SelectorItemInfo("workamountEntry.task.name"));
	                sic.add(new SelectorItemInfo("workamountEntry.completeDate"));
	                sic.add(new SelectorItemInfo("workamountEntry.confirmAmount"));
	                sic.add(new SelectorItemInfo("workamountEntry.confirmPercent"));
	                sic.add(new SelectorItemInfo("workamountEntry.remark"));
	                sic.add(new SelectorItemInfo("refTask.id"));
	                FilterInfo filter = new FilterInfo();
	                filter.getFilterItems().add(new FilterItemInfo("parent.id", billId));
	                viewInfo.setSelector(sic);
	                viewInfo.setFilter(filter);
	                java.util.List list = new ArrayList();
	                CoreBaseCollection cols = WorkLoadConfirmBillRelTaskFactory.getRemoteInstance().getCollection(viewInfo);
	                if(cols.size() > 0)
	                {
	                    WorkLoadConfirmBillRelTaskInfo info = null;
	                    for(int i = 0; i < cols.size(); i++)
	                    {
	                        info = (WorkLoadConfirmBillRelTaskInfo)cols.get(i);
	                        list.add(info.getWorkamountEntry());
	                    }

	                }
	                if(editData.getContractBill() != null && editData.getContractBill().getCurProject() != null)
	                    allCompleteAmt = WorkAmountBillFactory.getRemoteInstance().initTaskInfo(editData.getContractBill().getCurProject().getId().toString());
	                fillPrjTable(list, allCompleteAmt);
	            }
	        }
	        catch(EASBizException e1)
	        {
	            handUIException(e1);
	        }
	        catch(BOSException e1)
	        {
	            handUIException(e1);
	        }
	        try
	        {
	            if(isBaseContract())
	            {
	                Set prjFillBillIds = new HashSet();
	                WorkLoadPrjBillEntryCollection fillBillCol = editData.getPrjFillBillEntry();
	                WorkLoadPrjBillEntryInfo info = null;
	                for(int i = 0; i < fillBillCol.size(); i++)
	                	info = fillBillCol.get(i);
	                	if(info != null && info.getPrjFillBill()!= null )
	                    prjFillBillIds.add(info.getPrjFillBill().getId().toString());

	                try
	                {
	                    Map rptMap = WorkLoadConfirmBillFactory.getRemoteInstance().getConPrjFillBill(prjFillBillIds);
	                    if(rptMap.get("fillBillCol") != null)
	                    {
	                        ProjectFillBillCollection billCol = (ProjectFillBillCollection)rptMap.get("fillBillCol");
	                        Map accMap = new HashMap();
	                        accMap = (Map)rptMap.get("accValues");
	                        fillPrjTable(billCol, accMap);
	                    }
	                }
	                catch(EASBizException e)
	                {
	                    handleException(e);
	                }
	                catch(BOSException e)
	                {
	                    handleException(e);
	                }
	            }
	        }
	        catch(EASBizException e)
	        {
	            handleException(e);
	        }
	        catch(BOSException e)
	        {
	            handleException(e);
	        }
		/**
		 * 工程量填报单分录结束
		 */
		/**
		 * 如果合同结算了，则该单据的完工工程量 =  结算价 - 累计工程量
		 */
		WorkLoadCostVoucherEntryCollection infos = editData.getCostVoucherEntrys();
		WorkLoadCostVoucherEntryInfo info = null;
		Iterator ite = infos.iterator();
		if(!ite.hasNext()) {
			attachListeners();
			return;
		}
//		FDCClientHelper.setn
		tblEntrys.getColumn(this.COL_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		while(ite.hasNext()){
			info = (WorkLoadCostVoucherEntryInfo)ite.next();
			this.tblEntrys.addRow();
			
			IRow row = tblEntrys.getRow(
					tblEntrys.getRowCount()-1);
			row.getCell(this.COL_ACCOUNT).setValue(info.getAccountView());
			row.getCell(this.COL_COSTACCOUNT).setValue(info.getCostAccount());
			row.getCell(this.COL_AMOUNT).setValue(info.getAmount());
			row.getCell(this.COL_CURPROJECT).setValue(info.getCurProject());
		}
		
		
		//最后加上监听器
		attachListeners();
	}

	
	
	public void onShow() throws Exception {
		super.onShow();
		if(STATUS_VIEW.equals(getOprtState())){
			this.prmtPeriod.setEditable(false);
			this.prmtCreator.setEnabled(false);
			this.prmtAuditor.setEnabled(false);
			this.prmtContractBill.setEnabled(false);
			this.prmtPartB.setEnabled(false);
			this.contCurProject.setEnabled(false);
		}
		//已经结算的合同，“确认工程量”默认自动取值、不能修改 by cassiel_peng 2010-04-16
		ContractBillInfo contractBill = this.editData.getContractBill();
		if(contractBill!=null&&contractBill.getId()!=null&&contractBill.isHasSettled()){
			this.txtWorkLoad.setEnabled(false);
		}
		//Add by zhiyuan_tang 2010/07/24 判断父界面，如果是工程实际投入表，则屏蔽新增按钮
		String parentUI = (String) this.getUIContext().get("ParentUI");
		if ("PrjActualInputDetailQueryUI".equals(parentUI)) {
			this.actionAddNew.setEnabled(false);
		}
	}

	// 业务日期变化事件
	ControlDateChangeListener bizDateChangeListener = new ControlDateChangeListener(
			"bizDate");
	
	// 业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkBizDate, prmtPeriod, projectId, false);
	}
	// 变化事件
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bizDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} 
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(editData == null) return;
		if(FDCBillStateEnum.SUBMITTED_VALUE.equals(editData.getState())){
			actionSave.setEnabled(false);
		}
	}
	protected void updateButtonStatus() {
		// TODO Auto-generated method stub
		super.updateButtonStatus();
		if(FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionSave.setEnabled(false);
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		String contractId = null;
		if(editData != null){
			contractId = editData.getContractBill().getId().toString();
		}
		//累计确认完工工程量不能超过合同最新造价。
		if(contractId != null){
			BigDecimal workLoad = WorkLoadConfirmBillFactory.getRemoteInstance()
				.getWorkLoadWithoutId(contractId,editData.getId()==null?null:editData.getId().toString());
			workLoad = FDCHelper.add(workLoad, editData.getWorkLoad());
			BigDecimal lastPrice = FDCUtils.getContractLastAmt(null, contractId);
			if(workLoad.compareTo(lastPrice) >0){
				MsgBox.showError(this,"累计确认完工工程量超过合同最新造价，不能提交/审批！");
				SysUtil.abort();
			}
		}
		
		String billID = editData.getId()==null?null:editData.getId().toString();
		BigDecimal allPayReqAmt = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//R110215-258: 不包含预付款、关闭状态的处理  by hpw 2011.2.16
		builder.appendSql("select sum(t.famount) as famount  from  \n");
		builder.appendSql("(  \n");
		builder.appendSql(" (select (isnull(head.FProjectPriceInContract,0)-isnull(entry.flocadvance,0)) as famount from T_Con_PayRequestBill head \n"); 
		builder.appendSql("   left outer join T_CON_PayReqPrjPayEntry entry on entry.fparentid=head.fid \n");
		builder.appendSql("   where head.FContractId=? and head.FHasClosed=0)  \n");
		builder.appendSql(" union all \n");
		 
		builder.appendSql(" (select (isnull(head.FProjectPriceInContract,0)-isnull(entry.flocadvance,0)) as famount from T_CAS_PaymentBill head \n"); 
		builder.appendSql("  inner join T_Con_PayRequestbill req on head.FFdcPayReqID=req.FID and head.FContractBillId=req.FContractId \n"); 
		builder.appendSql("  and req.FContractId=? and req.FHasClosed=1 \n");
		builder.appendSql("  left outer join T_CON_PayReqPrjPayEntry entry on entry.fparentid=head.fid \n"); 
		  
		builder.appendSql("  ) \n"); 
		builder.appendSql(" ) t \n");
		builder.addParam(contractId);
		builder.addParam(contractId);
		try {
			IRowSet rowSet=builder.executeQuery();
			try {
				if(rowSet!=null&&rowSet.next()){
					allPayReqAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
				}
			} catch (SQLException e) {
				this.handUIException(e);
			}
		} catch (BOSException e) {
			this.handUIException(e);
		}
		amount = WorkLoadConfirmBillFactory.getRemoteInstance().getWorkLoadWithoutId(contractId,billID);
		amount = FDCHelper.add(amount,txtWorkLoad.getBigDecimalValue());
		if(allPayReqAmt.compareTo(FDCHelper.toBigDecimal(amount, 2))==1){
			if(!editData.isHasSettled()){
				MsgBox.showError(this,"合同下工程量累计值必须大于零且不小于付款申请单累计金额！");
				SysUtil.abort();
			}
		}
	}
	
	public void sortObjectCollection(AbstractObjectCollection c,Comparator comparator){
		if(c==null||c.size()<=1)
			return;
		Object[] objs=c.toArray();
		Arrays.sort(objs, comparator);
		c.clear();
		for(int i=0;i<objs.length;i++){
			c.addObject((IObjectValue)objs[i]);
		}
	}

	public void sortObjectCollection(AbstractObjectCollection c,String key1,String key2){
		final String k1 = key1;
		final String k2 = key2;
		Comparator comparator=new Comparator(){
			public int compare(Object o1, Object o2) {
				if(o1==null&&o2==null)	return 0;
				if(o1!=null&&o2==null)  return 1;
				if(o1==null&&o2!=null)	return -1;
				IObjectValue info1=(IObjectValue)o1;
				IObjectValue info2=(IObjectValue)o2;
				o1 = info1.getString(k1);
				o2 = info2.getString(k1);
				if(o1==null&&o2==null)	return 0;
				if(o1!=null&&o2==null)  return 1;
				if(o1==null&&o2!=null)	return -1;
				return ((String)o1).compareTo((String)o2);
			}
		};
		sortObjectCollection(c,comparator);
	}
	
	/**
	 * 判断是之前是否存在未审批的工程量清单
	 * @return
	 */
	protected boolean hasUnauditBill() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", editData.getContractBill().getId()));
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.hasSettled",Boolean.TRUE,CompareType.EQUALS));
		try {
			if(WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter)){
				com.kingdee.eas.util.client.MsgBox.showWarning(this,"存在未审批的工程量清单，请先审批之后再新增合同结算后的工程量确认单！");
				SysUtil.abort();
				return true;
			}
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		return false;
	}
	
	/**
	 * 是否存在结算后生成的工程量清单
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
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		return false;
	}
	
	private void fillPrjTable(ProjectFillBillCollection fillBillCol,Map accMap){
		BigDecimal totalAmt = FDCHelper.ZERO;
		tblDetail.removeRows();
		if(fillBillCol != null) {
			for(int i=0;i<fillBillCol.size();i++){
				ProjectFillBillInfo fillBillInfo = fillBillCol.get(i);
				for(int j=0;j<fillBillInfo.getEntries().size();j++){
					ProjectFillBillEntryInfo entryInfo = fillBillInfo.getEntries().get(j);
					totalAmt = FDCHelper.add(totalAmt, entryInfo.getQty());
					IRow row = tblDetail.addRow();
					row.getCell("id").setValue(null);
					row.getCell("fillBillId").setValue(fillBillInfo.getId().toString());
					row.getCell("number").setValue(fillBillInfo.getNumber());
					row.getCell("fillDate").setValue(fillBillInfo.getBizDate());
					row.getCell("task.number").setValue(entryInfo.getTask().getNumber());
					row.getCell("task.name").setValue(entryInfo.getTask().getName());
					row.getCell("completeAmt").setValue(entryInfo.getQty());
					row.getCell("completePct").setValue(entryInfo.getPercent());
					row.getCell("description").setValue(entryInfo.getDescription());
					row.getCell("task.id").setValue(entryInfo.getTask().getId().toString());
					row.getCell("fillBillId").setValue(fillBillInfo.getId().toString());
					if (accMap != null && accMap.get("accValue") != null) {
						HashMap accValue = (HashMap) accMap.get("accValue");
						row.getCell("accWorkLoadAmt").setValue(((ArrayList) accValue.get(entryInfo.getTask().getWbs().getId().toString())).get(0));
						row.getCell("accWorkLoadPct").setValue(((ArrayList) accValue.get(entryInfo.getTask().getWbs().getId().toString())).get(1));
					}
				}
			}
		}
		KDTFootManager footManager = tblDetail.getFootManager();
		if(footManager == null){
			addFootRow();
		}
		IRow footRow = tblDetail.getFootRow(0);
		footRow.getCell("completeAmt").setValue(totalAmt);
		
	}
	
	private void addFootRow(){
		IRow footRow=null;
        KDTFootManager footRowManager= tblDetail.getFootManager();
        if (footRowManager==null)  {
            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
            footRowManager = new KDTFootManager(this.tblDetail);
            footRowManager.addFootView();
            this.tblDetail.setFootManager(footRowManager);
            footRow= footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
            footRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
            this.tblDetail.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
            this.tblDetail.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        }else{
            footRow=footRowManager.getFootRow(0);
        }
	}
	
	private void refreshSelected()
    {
        WorkAmountEntryInfo info = null;
        alreadySelected.clear();
        for(int i = 0; i < tblDetail.getRowCount(); i++)
            if(tblDetail.getRow(i).getUserObject() != null)
            {
                info = (WorkAmountEntryInfo)tblDetail.getRow(i).getUserObject();
                alreadySelected.put(info.getId().toString(), info);
            }

    }
	
	public void actionConPrjBill_actionPerformed(ActionEvent e)	throws Exception {
		UIContext uiContext = new UIContext(this);
        if(contractBill == null)
            contractBill = (ContractBillInfo)prmtContractBill.getValue();
        if(isBaseTask())
        {
            refreshSelected();
            uiContext.put("contractId", contractBill.getId().toString());
            uiContext.put("alreadySelected", alreadySelected);
            IUIWindow newWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(com.kingdee.eas.fdc.schedule.client.F7WorkAmountConfirmUI.class.getName(), uiContext, null, OprtState.VIEW);
            newWin.show();
            if(newWin.getUIObject().getUIContext() != null)
            {
                boolean isClose = true;
                if(newWin.getUIObject().getUIContext().get("isCancled") != null)
                    isClose = Boolean.valueOf(newWin.getUIObject().getUIContext().get("isCancled").toString()).booleanValue();
                if(isClose)
                    return;
                java.util.List entryList = null;
                if(newWin.getUIObject().getUIContext().get("entry") != null)
                    entryList = (java.util.List)newWin.getUIObject().getUIContext().get("entry");
                Map completeMap = null;
                if(newWin.getUIObject().getUIContext().get("completeMap") != null)
                    completeMap = (Map)newWin.getUIObject().getUIContext().get("completeMap");
                if(!isClose)
                    fillPrjTable(entryList, completeMap);
            }
        } else
        if(isBaseContract())
        {
            uiContext.put("contractId", contractBill.getId().toString());
            IUIWindow prjFillBillF7UI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(com.kingdee.eas.fdc.pm.client.ProjectFillBillF7UI.class.getName(), uiContext, null, OprtState.VIEW);
            prjFillBillF7UI.show();
            if(prjFillBillF7UI.getUIObject().getUIContext() != null)
            {
                if("true".equals(prjFillBillF7UI.getUIObject().getUIContext().get("cancel")))
                    return;
                Set prjFillIds = (Set)prjFillBillF7UI.getUIObject().getUIContext().get("prjFillIds");
                Map rptMap = WorkLoadConfirmBillFactory.getRemoteInstance().getConPrjFillBill(prjFillIds);
                ProjectFillBillCollection billCol = (ProjectFillBillCollection)rptMap.get("fillBillCol");
                Map accMap = new HashMap();
                accMap = (Map)rptMap.get("accValues");
                fillPrjTable(billCol, accMap);
                IRow footRow = tblDetail.getFootRow(0);
                txtWorkLoad.setValue(footRow.getCell("completeAmt").getValue());
            }
        }
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		tblDetail.setEditable(false);
		//已经结算的合同，“确认工程量”默认自动取值、不能修改 by cassiel_peng 2010-04-16
		ContractBillInfo contractBill = this.editData.getContractBill();
		if(contractBill!=null&&contractBill.getId()!=null&&contractBill.isHasSettled()){
			this.txtWorkLoad.setEnabled(false);
		}
	}
	
	protected void disablePrintFunc() {
		// 重写父类方法，启用打印和打印预览按钮
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDNoteHelper appHlp = new KDNoteHelper();		
		WorkLoadConfirmBillDataProvider dataPvd = new WorkLoadConfirmBillDataProvider(editData.getId().toString(), getTDQueryPK());
		dataPvd.setIsBaseTask(isBaseTask());
		appHlp.print(getTDFileName(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}
	
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		KDNoteHelper appHlp = new KDNoteHelper();
		
		WorkLoadConfirmBillDataProvider dataPvd = new WorkLoadConfirmBillDataProvider(editData.getId().toString(), getTDQueryPK());
		dataPvd.setIsBaseTask(isBaseTask());
		appHlp.printPreview(getTDFileName(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	private String getTDFileName() {
		return "/bim/fdc/finance/workLoadConfirmBill";
	}
	
	private IMetaDataPK getTDQueryPK() throws EASBizException, BOSException {
		if (isBaseTask())
			return new MetaDataPK("com.kingdee.eas.fdc.finance.app.WorkLoadConfirmBill_baseTask_ForPrintQuery");
		else
			return new MetaDataPK("com.kingdee.eas.fdc.finance.app.WorkLoadConfirmBill_baseContract_ForPrintQuery");
	}
	
}