/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.ITenBillBase;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class RoomKeepDownBillEditUI extends AbstractRoomKeepDownBillEditUI {

//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	private SellProjectInfo sellProject = null;
	
	public RoomKeepDownBillEditUI() throws Exception {
		super();
		this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.achievementmgmt.app.RoomF7Query");
		this.prmtKeepdownCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");
	
	}

	private static final Logger logger = CoreUIObject.getLogger(RoomKeepDownBillEditUI.class);

	public void onLoad() throws Exception {
		
		super.onLoad();
		this.actionPrint.setVisible(true);
    	this.actionPrintPreview.setVisible(true);
    	this.actionPrint.setEnabled(true);
    	this.actionPrintPreview.setEnabled(true);
		initAllF7();
		if(OprtState.EDIT.equals(this.getOprtState())){
			this.txtNumber.setEnabled(false);
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		if(!OprtState.ADDNEW.equals(this.getOprtState())){
			if(RoomKeepDownBizEnum.Auditted.equals(((IRoomKeepDownBill)this.getBizInterface()).
					getRoomKeepDownBillInfo(new ObjectUuidPK(this.editData.getId()), selector).getBizState())){
				this.btnEdit.setEnabled(false);
			}
			
			this.prmtRoom.setEnabled(false);
		}
		
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			SpinnerNumberModel model = new SpinnerNumberModel(1, 0,999, 1);
			this.ksKeepDownDay.setModel(model);
		}
		
		//审核按钮
		this.btnAudit.setVisible(false);
		this.btnAudit.setEnabled(false);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
		if(OprtState.EDIT.equals(this.getOprtState())){
			
			FilterInfo filter = new FilterInfo();
			if(null!=this.prmtRoom.getValue())
			{
				filter.getFilterItems().add(new FilterItemInfo("bizState", RoomKeepDownBizEnum.AUDITTED_VALUE,CompareType.EQUALS));
			}
				
				filter.getFilterItems().add(new FilterItemInfo("number", this.txtNumber.getText(),CompareType.EQUALS));
			
			if(this.getBizInterface().exists(filter)){
				this.btnAudit.setEnabled(false);
				this.actionAudit.setEnabled(false);
			}
			
		}
		
		this.btnAuditResult.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
	
		this.prmtKeepdownCustomer.setDisplayFormat("$number$");
		this.prmtKeepdownCustomer.setEditFormat("$name$");
		this.prmtKeepdownCustomer.setCommitFormat("$number$");
		this.btnUnAudit.setVisible(false);
		this.btnUnAudit.setEnabled(false);
		this.actionAudit.setEnabled(false);
	}
   
	public void initAllF7() throws EASBizException, BOSException{
	//--房屋f7
		this.prmtRoom.setSelector(new NewFDCRoomPromptDialog(Boolean.FALSE, null, null,
				MoneySysTypeEnum.SalehouseSys, null,sellProject));
	//客户
		if(sellProject!=null){
			this.prmtKeepdownCustomer.setEntityViewInfo(NewCommerceHelper.
					getPermitCustomerView(SHEManageHelper.getParentSellProject(null,sellProject),SysContext.getSysContext().getCurrentUserInfo()));
		}
		
	}
	/**
	 * @description 黏贴自tenbillbaseEditUI 处理当前项目取不到
	 * @author tim_gao
	 */
	//子类可以重载
	protected  void fetchInitData() throws Exception{
		super.fetchInitData();
		if(this.getUIContext().get(FDCConstants.FDC_INIT_PROJECT)!=null){
			this.sellProject = (SellProjectInfo)this.getUIContext().get(FDCConstants.FDC_INIT_PROJECT);
		}
	}

	public void actionCancelKeepDown_actionPerformed(ActionEvent e) throws Exception
    {	
		if(null==this.editData.getId()&&OprtState.ADDNEW.equals(this.getOprtState())){
			  FDCMsgBox.showWarning("单据未提交，不适合此操作！");
			  SysUtil.abort();
		  }
		if(null==this.prmtRoom.getValue()){
			
			FDCMsgBox.showWarning("请选择房间！");
			SysUtil.abort();
		}
		if(null==this.editData.getId())
		{
			FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
			}
		RoomInfo room = (RoomInfo)this.prmtRoom.getValue();
		if(null!=room)
		{
			((IRoomKeepDownBill) this.getBizInterface()).cancelKeepDown(room.getId().toString(),this.editData.getId().toString());
		}
		else{
			((IRoomKeepDownBill) this.getBizInterface()).cancelKeepDown(null,this.editData.getId().toString());
		}
		super.actionCancelKeepDown_actionPerformed(e);
    }


	public void loadFields() {
		super.loadFields();
		if(OprtState.ADDNEW.equals(this.getOprtState())){
//			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
//			this.prmtKeepdownCustomer.setValue(user);
			this.kdBizTime.setValue(SHEHelper.getCurrentTime());
		}
		SHECustomerCollection sheCusCol = new SHECustomerCollection();
		if(null!=this.editData.getCustomerEntry()&&this.editData.getCustomerEntry().size()>0){
			for(int i = 0 ; i<this.editData.getCustomerEntry().size();i++){
				sheCusCol.add(this.editData.getCustomerEntry().get(i).getCustomer());
			}
			this.prmtKeepdownCustomer.setValue(sheCusCol.toArray());
		}
//		if(OprtState.VIEW.equals(this.getOprtState()) || OprtState.EDIT.equals(this.getOprtState())){
//			this.ksKeepDownDay.setValue(new Integer(this.editData.getKeepDate()));  //(new Integer(this.editData.getKeepDate()));
//		}
		
		initOldData(editData);
	}

	  public SelectorItemCollection getSelectors(){
		  SelectorItemCollection selector = super.getSelectors();
		  selector.add("customerEntry.*");
		  selector.add("customerEntry.customer");
		  selector.add("customerEntry.customer.*");
		  selector.add("*");
		  selector.add("CU.*");
		  return  selector ;
	    
	 }
	
	protected IObjectValue createNewData() {
		RoomKeepDownBillInfo billInfo = new RoomKeepDownBillInfo();
		if(null!=this.getUIContext().get("sellProject")){
			sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
			billInfo.setProject(sellProject);
		}
		if(null!=this.getUIContext().get("roomId")){
			String roomid  = this.getUIContext().get("roomId").toString();
			try {
				
				RoomInfo room =RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomid)));
				if(null!= room){
					
					billInfo.setRoom(room);
				}
			
				
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.warn("房间信息加载失败！");
				FDCMsgBox.showWarning("请选择房间信息!");
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("房间信息加载失败！");
				FDCMsgBox.showWarning("请选择房间信息!");
			}
		}
		billInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		billInfo.setReason(RoomKeepDownReasonEnum.CONTROl);
		return billInfo;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	
		String number = txtNumber.getText();
		if (StringUtils.isEmpty(number)) {
			MsgBox.showInfo("业务编号不能为空!");
			this.abort();
		}

		
		
//		if(OprtState.ADDNEW.equals(this.oprtState)){
//			if(this.prmtRoom.getData()instanceof RoomInfo){
//				RoomInfo room = (RoomInfo)this.prmtRoom.getData();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("room", room.getId().toString()));
////				Set stateSet = new HashSet();
////				stateSet.add(RoomKeepDownBizEnum.SUBMITTED_VALUE);
////				stateSet.add(RoomKeepDownBizEnum.AUDITTED_VALUE);
////				stateSet.add(RoomKeepDownBizEnum.SAVED_VALUE);
////				filter.getFilterItems().add(new FilterItemInfo("state", stateSet,CompareType.INCLUDE));
//				
//				filter.getFilterItems().add(new FilterItemInfo("cancelDate", null));
//				filter.getFilterItems().add(new FilterItemInfo("cacelStaff", null));
//				if(this.getBizInterface().exists(filter)){
//					FDCMsgBox.showInfo("房屋 已预留！");
//					this.abort();
//				}
//			}
//			//新增是校验编码
//			FilterInfo filter1 = new FilterInfo();
//			filter1.getFilterItems().add(new FilterItemInfo("number", number));
//			if (this.getBizInterface().exists(filter1)) {
//				MsgBox.showInfo("业务编号" + number + "已经存在!");
//				this.abort();
//			}
//		}
		//房间状态的校验
		RoomInfo room = (RoomInfo) this.prmtRoom.getValue();
		if(null==room){
			FDCMsgBox.showWarning("请选择房间！");
			SysUtil.abort();
		}else{
			if(OprtState.ADDNEW.equals(this.getOprtState())){
			if(!(RoomSellStateEnum.Init.equals(room.getSellState())||RoomSellStateEnum.OnShow.equals(room.getSellState()))){
				FDCMsgBox.showWarning("请选择未推盘或者待售状态的房间做预留！");
				SysUtil.abort();
			}
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//		verifyBalance();
		super.actionSave_actionPerformed(e);
		this.disposeUIWindow();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		verifyBalance();
		if(OprtState.ADDNEW.equals(this.getOprtState())){
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		
		//项目
		if(null!=this.prmtRoom.getValue()||"".equals(this.prmtRoom.getValue())){
			FilterItemInfo filterItemroom= new FilterItemInfo("room",((RoomInfo)this.prmtRoom.getValue()).getId().toString(), CompareType.EQUALS);
			filterInfo.getFilterItems().add(filterItemroom);
//			Set billState = new HashSet();
//			billState.add(RoomKeepDownBizEnum.SUBMITTED_VALUE);
//			billState.add(RoomKeepDownBizEnum.AUDITTED_VALUE);
			FilterItemInfo billStateFilter = new FilterItemInfo("bizState",RoomKeepDownBizEnum.SUBMITTED_VALUE,CompareType.EQUALS);
			FilterItemInfo billStateFilter1 = new FilterItemInfo("bizState",RoomKeepDownBizEnum.AUDITTED_VALUE,CompareType.EQUALS);
		
			filterInfo.getFilterItems().add(billStateFilter);
			filterInfo.getFilterItems().add(billStateFilter1);
			filterInfo.getFilterItems().add(new FilterItemInfo("cancelDate", null));
			filterInfo.getFilterItems().add(new FilterItemInfo("cacelStaff", null));
			
			filterInfo.setMaskString("#0 and (#1 or #2) and #3 and #4");
			if(this.getBillInterface().exists(filterInfo))
			{
				FDCMsgBox.showWarning("房间已经存在预留单！");
				SysUtil.abort();
			}
		}
		
		}
		super.actionSubmit_actionPerformed(e);
		if(!OprtState.EDIT.equals(this.getOprtState())){
			this.disposeUIWindow();
		}else{
			this.btnAudit.setVisible(true);
			this.btnAudit.setEnabled(true);
			this.actionAudit.setVisible(true);
			this.actionAudit.setEnabled(true);
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		if(RoomKeepDownBizEnum.CancelKeepDown.equals(((RoomKeepDownBillInfo)this.editData).getBizState())||
				RoomKeepDownBizEnum.Auditted.equals(((RoomKeepDownBillInfo)this.editData).getBizState())){
			FDCMsgBox.showWarning("单据状态不适合删除！");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}
	/**
	 * @descripition 审批
	 * @author tim_gao
	 */
	  public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	    {	
		  if(null==this.editData.getId()&&OprtState.ADDNEW.equals(this.getOprtState())){
			  FDCMsgBox.showWarning("单据未提交，不适合审批！");
			  SysUtil.abort();
		  }
		  //这里写的比较恶心回来改
		  SelectorItemCollection selc = new SelectorItemCollection();
		  selc.add("bizState");
		 RoomKeepDownBillInfo roomkeepDown = ((IRoomKeepDownBill)getBizInterface()).
		 getRoomKeepDownBillInfo(new ObjectUuidPK(this.editData.getId()),selc);
		  
		  
		  
		  if(RoomKeepDownBizEnum.Submitted.equals(roomkeepDown.getBizState())){
			
			  roomkeepDown.setId(this.editData.getId());
			  roomkeepDown.setBizState(RoomKeepDownBizEnum.Auditted);
		  
		  
		  	((IRoomKeepDownBill)this.getBillInterface()).audit(BOSUuid.read(this.editData.getId().toString()));
			this.getBillInterface().updatePartial(roomkeepDown, selc);
		  }
		  FDCMsgBox.showWarning("已审批！");
//	        super.actionAudit_actionPerformed(e);
	    }

	  
//	/**
//	 * 对于已结算的期间，不允许进行收款及修改
//	 * */
//	private void verifyBalance() {
//		Date bizDate = (Date) this.prmt.getValue();
//		if(bizDate==null){
//			MsgBox.showInfo("保留时间不能为空！");
//			this.abort();
//		}
//		Date balanceEndDate = null;
//		SellProjectInfo sellProject = null;
//		RoomKeepDownBillInfo info = (RoomKeepDownBillInfo) this.editData;
//		if (info.getRoom() != null && info.getRoom().getBuilding() != null && info.getRoom().getBuilding().getSellProject() != null) {
//			sellProject = info.getRoom().getBuilding().getSellProject();
//		}
//		if (sellProject != null) {
//			try {
//				balanceEndDate = getLastEndDate(sellProject.getId().toString());
//			} catch (Exception e) {
//				handleException(e);
//				e.printStackTrace();
//			}
//			SHEHelper.verifyBalance(bizDate, balanceEndDate);
//		}
//	}

//	/***
//	 * 按销售项目取上次结算的截止日期。
//	 * **/
//
//	private Date getLastEndDate(String sellProjectId) throws Exception {
//		Date lastEndDate = null;
//		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
//		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
//		detailBuilder.addParam(sellProjectId);
//		try {
//			IRowSet detailSet = detailBuilder.executeQuery();
//			if (detailSet.next()) {
//				lastEndDate = detailSet.getDate("FBalanceEndDate");
//			}
//		} catch (Exception e) {
//			handleException(e);
//			e.printStackTrace();
//		}
//		return lastEndDate;
//	}

	public void storeFields() {
		super.storeFields();
		if(this.sellProject!=null){
			this.editData.setProject(sellProject);
		}
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.editData.setBizState(RoomKeepDownBizEnum.Submitted);
		}
//		SHECustomerCollection sheCol = ((SHECustomerCollection)this.prmtKeepdownCustomer.getValue());
//		if(null!=this.prmtKeepdownCustomer.getValue()
//				&&sheCol.size()>0){
//			
//			for(int i = 0 ; i<sheCol.size();i++ ){
//				RoomKeepCustomerEntryInfo roomKeepCustomer = new RoomKeepCustomerEntryInfo();
//				SHECustomerInfo sheCustomer = (SHECustomerInfo)sheCol.get(i);
//			roomKeepCustomer.setCustomer(sheCustomer);
//	
//			this.editData.getCustomerEntry().add(roomKeepCustomer);
//			//维护客户名称字段
//			String cusStr = this.editData.getCusStr()+sheCustomer.getName()+";";
//			this.editData.setCusStr(cusStr);
//			}
//			
//		}
		//预设初始
		String cusStr = "";
		this.editData.setCusStr(cusStr);
		this.editData.getCustomerEntry().clear();
		if(this.prmtKeepdownCustomer.getValue() instanceof Object[]){
			Object[] sheCol = ((Object[])this.prmtKeepdownCustomer.getValue());
			if(null!=this.prmtKeepdownCustomer.getValue()
					&&sheCol.length>0){
				
				for(int i = 0 ; i<sheCol.length;i++ ){
					RoomKeepCustomerEntryInfo roomKeepCustomer = new RoomKeepCustomerEntryInfo();
					SHECustomerInfo sheCustomer = (SHECustomerInfo)sheCol[i];
				roomKeepCustomer.setCustomer(sheCustomer);
		
				this.editData.getCustomerEntry().add(roomKeepCustomer);
				//维护客户名称字段
				 cusStr = this.editData.getCusStr()+sheCustomer.getName()+";";
				this.editData.setCusStr(cusStr);
				}
				
			}
		}
		else if(this.prmtKeepdownCustomer.getValue() instanceof SHECustomerCollection){
			SHECustomerCollection sheCol = ((SHECustomerCollection)this.prmtKeepdownCustomer.getValue());
			if(null!=this.prmtKeepdownCustomer.getValue()
					&&sheCol.size()>0){
				
				for(int i = 0 ; i<sheCol.size();i++ ){
					RoomKeepCustomerEntryInfo roomKeepCustomer = new RoomKeepCustomerEntryInfo();
					SHECustomerInfo sheCustomer = (SHECustomerInfo)sheCol.get(i);
					roomKeepCustomer.setCustomer(sheCustomer);
		
					this.editData.getCustomerEntry().add(roomKeepCustomer);
					//维护客户名称字段
					cusStr = this.editData.getCusStr()+sheCustomer.getName()+";";
					this.editData.setCusStr(cusStr);
				}
				
			}
		}
		initOldData(editData);
	}


	protected ICoreBase getBizInterface() throws Exception {
		return RoomKeepDownBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	protected KDTable getDetailTable() {
		return null;
	}
	
	 /**
     * output prmtRoom_stateChanged method
     */
    protected void prmtRoom_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    	super.prmtRoom_stateChanged(e);
    	
    }





	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}





	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	/**
     * output ksKeepDownDay_stateChanged method
     */
    protected void ksKeepDownDay_stateChanged(ChangeEvent e) throws Exception
    {
    	
    	if(new Integer(this.ksKeepDownDay.getValue().toString()).intValue()<=0){
    	
    		this.ksKeepDownDay.setValue(new Integer(1));
    		SysUtil.abort();
    	}
    }



	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	 /**
     * output prmtKeepdownCustomer_dataChanged method
     */
    protected void prmtKeepdownCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(this.prmtKeepdownCustomer.getValue() instanceof RoomKeepCustomerEntryCollection ){
    		this.prmtKeepdownCustomer.setValue(null);
    	}
    	if(this.prmtKeepdownCustomer.getValue() instanceof Object[]){
			Object[] obj = (Object[])this.prmtKeepdownCustomer.getValue();
			if(obj.length>3){
				FDCMsgBox.showWarning("请最多选择3条客户！");
				SysUtil.abort();
				this.prmtKeepdownCustomer.setValue(null);
			}
			
		}
		else if(this.prmtKeepdownCustomer.getValue() instanceof SHECustomerCollection){
			SHECustomerCollection cusCol = (SHECustomerCollection)this.prmtKeepdownCustomer.getValue();
			if(cusCol.size()>3){
				FDCMsgBox.showWarning("请最多选择3条客户！");
				SysUtil.abort();
				this.prmtKeepdownCustomer.setValue(null);
			}
		}
    	
    }
    protected String getTDFileName() {
		return "/bim/fdc/sellhouse/RoomKeepDown";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomKeepDownBillPrintQuery");
	}
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		RoomKeepDownBillDataProvider data = new RoomKeepDownBillDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;

		}
		RoomKeepDownBillDataProvider data = new RoomKeepDownBillDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.btnAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		actionAttachment.setVisible(false);
	}
}