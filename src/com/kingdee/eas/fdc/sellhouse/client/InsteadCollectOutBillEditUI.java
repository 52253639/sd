///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
//import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory;
//import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
//import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
//import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
//import com.kingdee.bos.ctrl.kdf.table.KDTable;
//import com.kingdee.bos.ctrl.swing.KDTextField;
//
///**
// * 代收费用转出editUI
// * @author yinshujuan 
// */
//public class InsteadCollectOutBillEditUI extends AbstractInsteadCollectOutBillEditUI
//{
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = -6275990884786758500L;
//	private static final Logger logger = CoreUIObject.getLogger(InsteadCollectOutBillEditUI.class);
//	
//
//    /**
//     * output class constructor
//     */
//    public InsteadCollectOutBillEditUI() throws Exception
//    {
//        super();
//    }
//    /**
//     * output loadFields method
//     */
//    public void loadFields()
//    {
//    	this.prmtSellProject.setValue(this.editData.getSellproject());
//        super.loadFields();
//    }
//
//    public void onLoad() throws Exception
//	{
//		super.onLoad();
//		initAll();
//		//this.txtNumber.setRequired(false);
//		//this.txtNumber.setEnabled(false);
//		this.actionCopy.setVisible(false);
//		this.actionCopy.setEnabled(false);
//	}
//    /**
//     * 初始化所有控件的值
//     * @throws EASBizException
//     * @throws BOSException
//     */
//    private void initAll() throws EASBizException, BOSException {
//    	
//    	String entryID = this.getUIContext().get("entryID").toString().trim();
//		PurchaseElsePayListEntryInfo entryInfo = getElseEntryInfo(entryID);
//		if(entryInfo != null){
//			this.prmtMoneyType.setValue(entryInfo.getMoneyDefine());    //款项类型
//			this.txtmoneyCollect.setValue(entryInfo.getApAmount());  //应收金额
//			this.txtmoneyCollected.setValue(entryInfo.getActPayAmount());   //已收金额
//			this.txtmoneyReturned.setValue(entryInfo.getRefundmentAmount());  //已退金额
//		}
//		//已代付金额
//		BigDecimal insteadedAmount = (BigDecimal)this.getUIContext().get("insteadedAmount") ;
////		BigDecimal newInstead = null;
//		if(insteadedAmount!=null) {
//			insteadedAmount=insteadedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//		}
//		this.txtInsteaded.setText(insteadedAmount== null?"0.00":insteadedAmount.toString());
//		//楼栋，单元，房间，客户
//		Object building = this.getUIContext().get("building") ;
//		this.txtBuilding.setText(building==null?"":building.toString());
//		Object unit = this.getUIContext().get("unit");
//		this.txtUnit.setText(unit==null?"":unit.toString());
//		Object roomNum = this.getUIContext().get("roomNumber");
//		this.txtRoom.setText(roomNum==null?"":roomNum.toString());
//		Object customer = this.getUIContext().get("customer");
//		this.txtCustomer.setText(customer==null?"":customer.toString());
//		//退款金额
//		BigDecimal returnedAmount = new BigDecimal("0.00");
//		if(this.getUIContext().get("returned")!=null){
//			returnedAmount = (BigDecimal)this.getUIContext().get("returned");
//		}
//		//应收金额
//		BigDecimal apAmount = new BigDecimal("0.00");
//		if(this.getUIContext().get("apAmount")!=null){
//			returnedAmount = (BigDecimal)this.getUIContext().get("apAmount");
//		}
//		BigDecimal allInsteadedAmount = new BigDecimal(getTrasferedCount());//所有的已代付金额，包括还未审核的
//		if(this.oprtState.equals(STATUS_ADDNEW)) {//新增状态下
//			//没做过退款，且未代付过，则本次代付=应收金额；代付过，本次代付=应收金额-已代付金额
//			if(returnedAmount==null) {
//				this.txtmoneyInsteadCur.setValue(apAmount.subtract(allInsteadedAmount));
//			}else if(returnedAmount != null) {//做过退款，且未代付过，本次代付=应收金额-已退金额；代付过本次代付=应收金额-已退金额-已代付金额
//				this.txtmoneyInsteadCur.setValue(apAmount.subtract(returnedAmount).subtract(allInsteadedAmount));
//			}
//		}
//    }
//    /**
//     * 获取某个认购单其它应付分录的所有转出单
//     * @return
//     * @throws BOSException
//     */
//    private InsteadCollectOutBillCollection getAllInsteadBill() throws BOSException{
//    	InsteadCollectOutBillCollection collection = null;
//    	String entryID = this.getUIContext().get("entryID").toString().trim();
//    	EntityViewInfo evi = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("purchaseElsePayListEntry.id",entryID,CompareType.EQUALS));
//    	SelectorItemCollection sic = new SelectorItemCollection();
//    	sic.add(new SelectorItemInfo("*"));
//    	evi.setSelector(sic);
//    	evi.setFilter(filter);
//    	collection = InsteadCollectOutBillFactory.getRemoteInstance().getInsteadCollectOutBillCollection(evi);
//    	return collection;
//    }
//    /**
//     * 求所有已转出的费用
//     * @throws BOSException 
//     */
//    private float getTrasferedCount() throws BOSException{
//    	float transferedCount = 0.0f;
//    	InsteadCollectOutBillCollection bills = getAllInsteadBill();
//    	InsteadCollectOutBillInfo bill = null;
//    	float account = 0.0f;
//    	for(int i = 0;i<bills.size();i++){
//    		bill = bills.get(i);
//    		account = account + bill.getMoneyInsteadCur().floatValue();
//    	}
//    	transferedCount = account;
//    	return transferedCount;
//    }
//   
//	/**
//	 * 判断本次转出费用金额是否合理
//	 */
//	protected void txtmoneyInsteadCur_focusLost(FocusEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super.txtmoneyInsteadCur_focusLost(e);
//		if(this.txtmoneyInsteadCur.getText().trim().equals(""))return;
//		float curMoney = new Float(this.txtmoneyInsteadCur.getText()).floatValue();  //本次代付金额
//		float aptAmount = this.txtmoneyCollect.getNumberValue().floatValue();   //应收金额
//		float insteadedAmount = getTrasferedCount();
//		float returnedAmount = (this.txtmoneyReturned.getNumberValue())==null?0:this.txtmoneyReturned.getNumberValue().floatValue();//已退金额
//		float curAptAmount = aptAmount-insteadedAmount-returnedAmount; //本次应代付金额
//		if(curMoney+insteadedAmount+returnedAmount>aptAmount) {
//			MsgBox.showWarning("本次代付金额不能大于"+curAptAmount);
//			txtmoneyInsteadCur.setText(Float.toString(curAptAmount));
//			this.txtmoneyInsteadCur.grabFocus();
//			return;
//		}
//	}
//	/**
//	 * 判断代付金额是否合理
//	 */
//	private boolean isFittable() {
//		BigDecimal insteadCur = this.txtmoneyInsteadCur.getBigDecimalValue();
//		if(insteadCur==null) {
//			return false;
//		}
//		if(insteadCur.floatValue()<=0) {
//			return false;
//		}
//		return true;
//	}
//	/**
//     * output storeFields method
//     */
//    public void storeFields()
//    {
//    	this.editData.setSellproject((SellProjectInfo)this.prmtSellProject.getValue());
//        super.storeFields();
//    }
//
//    /**
//     * output actionPageSetup_actionPerformed
//     */
//    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionPageSetup_actionPerformed(e);
//    }
//
//    /**
//     * output actionExitCurrent_actionPerformed
//     */
//    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionExitCurrent_actionPerformed(e);
//    }
//
//    /**
//     * output actionHelp_actionPerformed
//     */
//    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionHelp_actionPerformed(e);
//    }
//
//    /**
//     * output actionAbout_actionPerformed
//     */
//    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionAbout_actionPerformed(e);
//    }
//
//    /**
//     * output actionOnLoad_actionPerformed
//     */
//    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionOnLoad_actionPerformed(e);
//    }
//
//    /**
//     * output actionSendMessage_actionPerformed
//     */
//    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionSendMessage_actionPerformed(e);
//    }
//
//    /**
//     * output actionCalculator_actionPerformed
//     */
//    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCalculator_actionPerformed(e);
//    }
//
//    /**
//     * output actionExport_actionPerformed
//     */
//    public void actionExport_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionExport_actionPerformed(e);
//    }
//
//    /**
//     * output actionExportSelected_actionPerformed
//     */
//    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionExportSelected_actionPerformed(e);
//    }
//
//    /**
//     * output actionRegProduct_actionPerformed
//     */
//    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionRegProduct_actionPerformed(e);
//    }
//
//    /**
//     * output actionPersonalSite_actionPerformed
//     */
//    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionPersonalSite_actionPerformed(e);
//    }
//
//    /**
//     * output actionProcductVal_actionPerformed
//     */
//    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionProcductVal_actionPerformed(e);
//    }
//
//    /**
//     * output actionSave_actionPerformed
//     */
//    public void actionSave_actionPerformed(ActionEvent e) throws Exception
//    {
//    	if(!isFittable()) {
//    		MsgBox.showWarning("本次代收费转出金额不合理，请重新输入！");
//    		txtmoneyInsteadCur.grabFocus();
//    		SysUtil.abort();
//    	}
//        super.actionSave_actionPerformed(e);
//    }
//
//    /**
//     * output actionSubmit_actionPerformed
//     */
//    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
//    {
//    	if(!isFittable()) {
//    		MsgBox.showWarning("本次代收费转出金额不合理，请重新输入！");
//    		txtmoneyInsteadCur.grabFocus();
//    		SysUtil.abort();
//    	}
//        super.actionSubmit_actionPerformed(e);
//    }
//
//    /**
//     * output actionCancel_actionPerformed
//     */
//    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCancel_actionPerformed(e);
//    }
//
//    /**
//     * output actionCancelCancel_actionPerformed
//     */
//    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCancelCancel_actionPerformed(e);
//    }
//
//    /**
//     * output actionFirst_actionPerformed
//     */
//    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionFirst_actionPerformed(e);
//    }
//
//    /**
//     * output actionPre_actionPerformed
//     */
//    public void actionPre_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionPre_actionPerformed(e);
//    }
//
//    /**
//     * output actionNext_actionPerformed
//     */
//    public void actionNext_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionNext_actionPerformed(e);
//    }
//
//    /**
//     * output actionLast_actionPerformed
//     */
//    public void actionLast_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionLast_actionPerformed(e);
//    }
//
//    /**
//     * output actionPrint_actionPerformed
//     */
//    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionPrint_actionPerformed(e);
//    }
//
//    /**
//     * output actionPrintPreview_actionPerformed
//     */
//    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionPrintPreview_actionPerformed(e);
//    }
//
//    /**
//     * output actionCopy_actionPerformed
//     */
//    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCopy_actionPerformed(e);
//    }
//
//    /**
//     * output actionAddNew_actionPerformed
//     */
//    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionAddNew_actionPerformed(e);
//    }
//
//    /**
//     * output actionEdit_actionPerformed
//     */
//    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionEdit_actionPerformed(e);
//    }
//
//    /**
//     * output actionRemove_actionPerformed
//     */
//    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionRemove_actionPerformed(e);
//    }
//
//    /**
//     * output actionAttachment_actionPerformed
//     */
//    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionAttachment_actionPerformed(e);
//    }
//
//    /**
//     * output actionSubmitOption_actionPerformed
//     */
//    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionSubmitOption_actionPerformed(e);
//    }
//
//    /**
//     * output actionReset_actionPerformed
//     */
//    public void actionReset_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionReset_actionPerformed(e);
//    }
//
//    /**
//     * output actionMsgFormat_actionPerformed
//     */
//    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionMsgFormat_actionPerformed(e);
//    }
//
//    /**
//     * output actionAddLine_actionPerformed
//     */
//    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionAddLine_actionPerformed(e);
//    }
//
//    /**
//     * output actionInsertLine_actionPerformed
//     */
//    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionInsertLine_actionPerformed(e);
//    }
//
//    /**
//     * output actionRemoveLine_actionPerformed
//     */
//    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionRemoveLine_actionPerformed(e);
//    }
//
//    /**
//     * output actionCreateFrom_actionPerformed
//     */
//    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCreateFrom_actionPerformed(e);
//    }
//
//    /**
//     * output actionCopyFrom_actionPerformed
//     */
//    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCopyFrom_actionPerformed(e);
//    }
//
//    /**
//     * output actionAuditResult_actionPerformed
//     */
//    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionAuditResult_actionPerformed(e);
//    }
//
//    /**
//     * output actionTraceUp_actionPerformed
//     */
//    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionTraceUp_actionPerformed(e);
//    }
//
//    /**
//     * output actionTraceDown_actionPerformed
//     */
//    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionTraceDown_actionPerformed(e);
//    }
//
//    /**
//     * output actionViewSubmitProccess_actionPerformed
//     */
//    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionViewSubmitProccess_actionPerformed(e);
//    }
//
//    /**
//     * output actionViewDoProccess_actionPerformed
//     */
//    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionViewDoProccess_actionPerformed(e);
//    }
//
//    /**
//     * output actionMultiapprove_actionPerformed
//     */
//    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionMultiapprove_actionPerformed(e);
//    }
//
//    /**
//     * output actionNextPerson_actionPerformed
//     */
//    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionNextPerson_actionPerformed(e);
//    }
//
//    /**
//     * output actionStartWorkFlow_actionPerformed
//     */
//    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionStartWorkFlow_actionPerformed(e);
//    }
//
//    /**
//     * output actionVoucher_actionPerformed
//     */
//    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionVoucher_actionPerformed(e);
//    }
//
//    /**
//     * output actionDelVoucher_actionPerformed
//     */
//    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionDelVoucher_actionPerformed(e);
//    }
//
//    /**
//     * output actionWorkFlowG_actionPerformed
//     */
//    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionWorkFlowG_actionPerformed(e);
//    }
//
//    /**
//     * output actionCreateTo_actionPerformed
//     */
//    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionCreateTo_actionPerformed(e);
//    }
//
//    /**
//     * output actionSendingMessage_actionPerformed
//     */
//    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionSendingMessage_actionPerformed(e);
//    }
//
//    /**
//     * output actionSignature_actionPerformed
//     */
//    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionSignature_actionPerformed(e);
//    }
//
//    /**
//     * output actionWorkflowList_actionPerformed
//     */
//    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionWorkflowList_actionPerformed(e);
//    }
//
//    /**
//     * output actionViewSignature_actionPerformed
//     */
//    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
//    {
//        super.actionViewSignature_actionPerformed(e);
//    }
//
//    /**
//     * output getBizInterface method
//     */
//    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
//    {
//        return com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory.getRemoteInstance();
//    }
//
//    /**
//     * output createNewDetailData method
//     */
//    protected IObjectValue createNewDetailData(KDTable table)
//    {
//		
//        return null;
//    }
//
//    /**
//     * output createNewData method
//     */
//    protected com.kingdee.bos.dao.IObjectValue createNewData()
//    {
//        com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo objectValue = new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo();
//        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo()));
//        objectValue.setManInsteadPay((com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo().getPerson()));
//        PurchaseElsePayListEntryInfo purchaseElsePayListEntryInfo = null;
//        String entryID = this.getUIContext().get("entryID").toString().trim();
//        try
//		{
//        	purchaseElsePayListEntryInfo = getElseEntryInfo(entryID);
//		} catch (Exception e)
//		{
//		}
//		objectValue.setPurchaseElsePayListEntry(purchaseElsePayListEntryInfo);
//		objectValue.setBizDate(new Date());
//        return objectValue;
//    }
//    
//    private PurchaseElsePayListEntryInfo getElseEntryInfo(String entryID) throws EASBizException, BOSException{
//    	SelectorItemCollection sic = new SelectorItemCollection();
//    	sic.add(new SelectorItemInfo("*"));
//    	sic.add(new SelectorItemInfo("moneyDefine.*"));
//    	sic.add(new SelectorItemInfo("apAmount"));
//    	sic.add(new SelectorItemInfo("actPayAmount"));
//    	sic.add(new SelectorItemInfo("refundmentAmount"));
//    	sic.add(new SelectorItemInfo("moneyDefine.*"));
//    	PurchaseElsePayListEntryInfo info = PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryInfo(new ObjectUuidPK(entryID),sic);
//    	return info;
//    }
//	protected KDBizPromptBox getSellProjectCtrl()
//	{
//		// TODO Auto-generated method stub
//		return prmtSellProject;
//	}
//	protected void attachListeners()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	protected void detachListeners()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	protected KDTextField getNumberCtrl()
//	{
//		// TODO Auto-generated method stub
//		return txtNumber;
//	}
//	
//	 protected void setAutoNumberByOrg(String orgType) {
//		///super.setAutoNumberByOrg(orgType);
//	}
//}