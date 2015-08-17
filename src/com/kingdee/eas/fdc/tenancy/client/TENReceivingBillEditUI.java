/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.DefaultRevAction;
import com.kingdee.eas.fdc.basecrm.client.IRevAction;
import com.kingdee.eas.fdc.basecrm.client.SelectRevListUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateCollection;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class TENReceivingBillEditUI extends AbstractTENReceivingBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(TENReceivingBillEditUI.class);

	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	protected BuildingInfo buildingInfo = null;
	protected BuildingUnitInfo buildUnit = null;
	 TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
	public TENReceivingBillEditUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		this.f7FdcCustomers.setEnabledMultiSelection(false);
		this.contTransContract.setVisible(false);
		super.onLoad();
		initControl();	
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionAudit.setEnabled(false);
			this.actionReceive.setEnabled(false);
		}

//		if(this.isByCustomer.isSelected()&&!OprtState.EDIT.equals(this.oprtState)){
//
//			this.contTenancyBill.setVisible(false);
//			this.f7TenancyBill.setVisible(false);
//			
//			this.tennacyStr.setVisible(true);
//			this.contTenancyStr.setVisible(true);
//
//		}
		this.txtNumber.setMaxLength(44);
		
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");
		this.prmtRevAccount.setDialog(opseelect);
		
        EntityViewInfo entityViewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        entityViewInfo.setFilter(filterInfo);
        filterInfo.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
        this.prmtAccountBank.setEntityViewInfo(entityViewInfo);
        
        this.prmtRevAccount.setRequired(true);
        
		this.actionReceive.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		
		this.tblEntry.getColumn("stleCount").getStyleAttributes().setHided(true);
		
		this.f7FdcCustomers.setEnabled(true);
		
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		contRevBillType.setVisible(false);
	}
	
	protected void comboRevBillType_itemStateChanged(ItemEvent e)
			throws Exception {				
		RevBillTypeEnum revBillType = (RevBillTypeEnum)this.comboRevBillType.getSelectedItem();
		setTableEntry(revBillType);
	}
	
	protected void initEntryTableByRevBillType(RevBillTypeEnum revBillType){
		this.comboRevBillType.setSelectedItem(revBillType);		
	}
	
	protected void initEntryTableOfGathering(){
		this.tblEntry.removeRows();
		this.contTransContract.setVisible(false);
		super.initEntryTableOfGathering();
	}
	
	protected void initEntryTableOfRefundment() {
		this.tblEntry.removeRows();
		this.contTransContract.setVisible(false);
		super.initEntryTableOfRefundment();
	}
	
	protected void initEntryTableOfAdjust() {
		this.tblEntry.removeRows();
		this.contTransContract.setVisible(false);
		super.initEntryTableOfAdjust();
	}
	
	protected void initEntryTableOfTransfer(){		
		this.tblEntry.removeRows();
		this.contTransContract.setVisible(true);
		this.contSincerObligate.setVisible(false);
		super.initEntryTableOfTransfer();
	}
	
	private void initControl()
	{
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionDelVoucher.setVisible(true);
		this.actionAuditResult.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.f7TenancyUser.setEnabled(false);
		this.actionVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
		this.contRoomLongNumber.setVisible(false);	
		this.comboRevBillType.setEnabled(true);	
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionDelVoucher.setVisible(false);
	}
	
	public void setTableEntry(RevBillTypeEnum revBillType)
	{
		setObliFilter((SellProjectInfo)this.f7SellProject.getValue(),(RevBillTypeEnum)this.comboRevBillType.getSelectedItem());
		if(RevBillTypeEnum.gathering.equals(revBillType)){			
			initEntryTableOfGathering();
			this.contRecAmount.setBoundLabelText("收款金额");
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			initEntryTableOfRefundment();
			this.contRecAmount.setBoundLabelText("退款金额");
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			initEntryTableOfTransfer();
			this.contRecAmount.setBoundLabelText("转款金额");
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			initEntryTableOfAdjust();
		}
		
		this.tblEntry.getColumn("stleCount").getStyleAttributes().setHided(true);
		this.tblEntry.getColumn("stleType").getStyleAttributes().setHided(true);
		this.tblEntry.getColumn("stleNumber").getStyleAttributes().setHided(true);
		this.tblEntry.getColumn("revAccount").getStyleAttributes().setHided(true);
		this.tblEntry.getColumn("revBankAccount").getStyleAttributes().setHided(true);
		this.tblEntry.getColumn("custAccount").getStyleAttributes().setHided(true);
	}

	public void loadFields() {
		
		isLoadFields = true;
		boolean boo = false;
		if(this.getUIContext().get("isWorkflowRec")!=null)
		{
			boo = ((Boolean)this.getUIContext().get("isWorkflowRec")).booleanValue();
		}
		//当是从退租审批工作流中进行财务处理的时候设置收款单上的控件
		if(boo)
		{
			this.f7TenancyBill.setEnabled(false);
			this.comboRevBillType.setEnabled(false);
			this.f7SellProject.setEnabled(false);
			this.contBizDate.setEnabled(false);
			this.f7Room.setEnabled(false);
			this.f7FdcCustomers.setEnabled(false);
			this.btnAddNew.setEnabled(false);
		}
		RevBillTypeEnum revBillType = this.editData.getRevBillType();
		if(revBillType!=null)setTableEntry(revBillType);		
		super.loadFields();
		try {		
			setTeniFilter((SellProjectInfo)this.f7SellProject.getValue());
			setObliFilter((SellProjectInfo)this.f7SellProject.getValue(),(RevBillTypeEnum)this.comboRevBillType.getSelectedItem());
			loadFields_forChildren();
			
			if(this.editData.getTenancyObj() != null){
				setCustomerFilter(this.editData.getTenancyObj());
			}else if(this.editData.getObligateObj()!= null){
				setCustomerFilter(this.editData.getObligateObj());
			}
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		this.comboRevBizType.setSelectedItem(this.editData.getRevBizType());
		
		//因为转出合同没有保存。所以在查看的时候从转款来源中把转出合同找出来显示
		if(this.getOprtState().equals("VIEW") || this.getOprtState().equals("EDIT"))
		{
			if(RevBizTypeEnum.tenancy.equals(this.editData.getRevBizType()) && RevBillTypeEnum.transfer.equals(this.editData.getRevBillType()))
			{
				String receivingID = this.editData.getId().toString();				
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.getSelector().add("sourceEntries.headEntry.id");
				viewInfo.getSelector().add("sourceEntries.fromRevListId");
				viewInfo.getSelector().add("sourceEntries.fromRevListType");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("head.id",receivingID));
				viewInfo.setFilter(filter);
				try {
					FDCReceivingBillEntryCollection recEntryColl = FDCReceivingBillEntryFactory.getRemoteInstance().getFDCReceivingBillEntryCollection(viewInfo);
					if(recEntryColl.size()>0)
					{
						FDCReceivingBillEntryInfo recEntryInfo = recEntryColl.get(0);
						TransferSourceEntryCollection transEntryColl = recEntryInfo.getSourceEntries();
						if(transEntryColl.size()>0)
						{
							TransferSourceEntryInfo transEntryInfo = transEntryColl.get(0);
							RevListTypeEnum fromType = transEntryInfo.getFromRevListType();
							if(RevListTypeEnum.tenRoomRev.equals(fromType))
							{
								SelectorItemCollection sels = new SelectorItemCollection();
								sels.add("tenRoom.tenancy.id");
								sels.add("tenRoom.tenancy.name");
								sels.add("tenRoom.tenancy.number");
								TenancyRoomPayListEntryInfo tenancyRoom = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(transEntryInfo.getFromRevListId()),sels);
								this.contTransContract.setVisible(true);
								this.f7TransContract.setValue(tenancyRoom.getTenRoom().getTenancy());
							}
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}
		}		
		isLoadFields = false;
		FDCReceivingBillInfo rev = (FDCReceivingBillInfo)this.editData;
//		this.tennacyStr.setText(rev.getTennacyStr());
	}

	public void storeFields() {
		super.storeFields();
		storeFields_forChildren();
		FDCReceivingBillInfo rev = (FDCReceivingBillInfo)this.editData;
//		rev.setTennacyStr(this.tennacyStr.getText());
	}

	protected List getAppRefundmentList() throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map getAppRevList() throws BOSException, EASBizException {
		// 诚意预留和租赁类型的分别对待
		RevBizTypeEnum revBizType = (RevBizTypeEnum) comboRevBizType
				.getSelectedItem();
		if (RevBizTypeEnum.tenancy.equals(revBizType)) {
			return getTenancyPayLists();
		}else if (RevBizTypeEnum.customer.equals(revBizType)) {

		}

		return null;
	}

	private Map getTenancyPayLists() throws EASBizException, BOSException {
		
		
		if (this.f7TenancyBill
				.getData() == null) {
			MsgBox.showInfo(this, "请选择租赁合同！");
			this.abort();
		}
		Map map = new HashMap();
		List paylist = new ArrayList();
		List otherPayList = new ArrayList();
		TenancyBillInfo tenancy =null;
		if(this.f7TenancyBill
				.getData() instanceof TenancyBillInfo){
		tenancy = (TenancyBillInfo) this.f7TenancyBill
				.getData();
		inPutListByTenancyBillandList( tenancy, paylist, otherPayList);
		}else{
			Object[] obj = (Object[])this.f7TenancyBill
			.getData();
			for(int i = 0 ; i <obj.length;i++){
				tenancy = (TenancyBillInfo)obj[i];
				if(tenancy!=null)
				inPutListByTenancyBillandList(tenancy, paylist, otherPayList);
			}
		}
		
		map.put(KEY_TENPAYLIST, paylist);
		map.put(KEY_TENOTHERPAYLIST, otherPayList);
		return map;
	}
	/**
	 * @author Tim_gao 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @description 根据多合同或者单合同添加分录
	 */
	protected void inPutListByTenancyBillandList(TenancyBillInfo tenancy,List paylist,List otherPayList) throws EASBizException, BOSException{
		tenancy = TenancyHelper.getTenancyBillInfo(tenancy.getId()
				.toString());
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			TenancyRoomPayListEntryCollection tenRoomPays = tenRoom
					.getRoomPayList();
			for (int j = 0; j < tenRoomPays.size(); j++) {
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPays.get(j);
				tenRoomPay.setTenRoom(tenRoom);
				// by tim_gao 加入合同
				tenRoomPay.setTenBill(tenancy);
				paylist.add(tenRoomPay);
			}
		}

	
		
		TenBillOtherPayCollection tenOtherColl = tenancy.getOtherPayList();
		CRMHelper.sortCollection(tenOtherColl, "leaseSeq", true);
		for (int i = 0; i < tenOtherColl.size(); i++) {
			TenBillOtherPayInfo tenOtherInfo = tenOtherColl.get(i);
			// by tim_gao 加入合同
			tenOtherInfo.setHead(tenancy);
			otherPayList.add(tenOtherInfo);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
//		if(this.isByCustomer.isSelected()){
//			Object[] obj = (Object[])this.f7TenancyBill.getValue();
//			StringBuffer str=new StringBuffer();
//			for(int i = 0 ; i<obj.length;i++)
//			{	
//				if(obj[i]!=null){
//				TenancyBillInfo tenancy = (TenancyBillInfo)obj[i];
//			
//				str.append(tenancy.getNumber().toString()+";");
//				}
//			}
////			this.tennacyStr.setText(str.toString());
//			FDCReceivingBillInfo fdcR = (FDCReceivingBillInfo)this.editData;
//			fdcR.setTenancyObj(null);
//			this.f7TenancyBill.setValue(null);
//		}
		
		
		super.actionSubmit_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (!RevBillStatusEnum.SAVE.equals(this.editData.getBillStatus())&& !RevBillStatusEnum.SUBMIT.equals(this.editData.getBillStatus()))
		{
			MsgBox.showInfo(this, "只有已提交或保存状态的收款单才可以修改！");
			return;
		}
		if(RevBillTypeEnum.transfer.equals(this.editData.getRevBillType()))
		{
			MsgBox.showInfo("转款单不允许修改");
			this.abort();
		}
		if(this.editData.getSrcRevBill() != null){
			MsgBox.showInfo("属于代收的收款单,不允许修改或删除！请去修改或删除源单据！");
			this.abort();
		}
		checkCreateBillStatus(this.editData.getId().toString(),"所选收款单中已生成出纳收款单，不允许修改操作！");
		super.actionEdit_actionPerformed(e);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(false);
//		this.btnSelectRevList.setEnabled(true);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(RevBillTypeEnum.transfer.equals(this.editData.getRevBillType()))
		{
			MsgBox.showInfo("转款单不允许删除");
			this.abort();
		}
		if(this.editData.getSrcRevBill() != null){
			MsgBox.showInfo("属于代收的收款单,不允许修改或删除！请去修改或删除源单据！");
			this.abort();
		}
		checkCreateBillStatus(this.editData.getId().toString(),"所选收款单中已生成出纳收款单，不允许删除操作！");
		super.actionRemove_actionPerformed(e);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);

		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("收款单编码不能为空!");
			this.abort();
		}
		if(this.f7FdcCustomers.getValue() == null)
		{
			MsgBox.showInfo("客户信息不能为空!");
			this.abort();
		}		
		
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType == null) {
			throw new BOSException("逻辑错误");
		}
		if(tblEntry.getRowCount()==0)
		{
			MsgBox.showInfo("收款类型不能为空,请选择收款类型!");
			this.abort();
		}
		if (RevBizTypeEnum.tenancy.equals(revBizType)) {
			if(this.f7TenancyBill.getValue() == null)
			{
				MsgBox.showWarning("租赁合同为空，无法进行当前操作！"	);
				this.abort();
			}
		}else if(RevBizTypeEnum.sincerity.equals(revBizType))
		{
			if(this.f7SincerObligate.getValue() == null)
			{
				MsgBox.showWarning("诚意预留单为空，无法进行当前操作！"	);
				this.abort();
			}
		}
	}

	protected IRevListInfo getRevListInfo(RevListTypeEnum revListType,
			String revListId) throws EASBizException, BOSException {
		return TENRevHelper.getRevListInfo(revListType, revListId);
	}

	protected List getAppRevRefundmentList() throws BOSException,EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) comboRevBizType.getSelectedItem();
		List res = null;
		
		if (RevBizTypeEnum.obligate.equals(revBizType)) {
			res = getSincerPayLists();
		} else if (RevBizTypeEnum.tenancy.equals(revBizType)) {
			Map map = this.getTenancyPayLists();
			if(map == null  ||  map.isEmpty()){
				return null;
			}
			List list = new ArrayList();
			for (Iterator itor = map.keySet().iterator(); itor.hasNext();) {
				List tlist = (List) map.get(itor.next());
				list.addAll(tlist);
			}
			res = list;
		}
		
		if(res == null  ||  res.isEmpty()){
			return res;
		}
		
		//把那些可退金额为0的排除
		List tolRes = new ArrayList();
		if(res != null  &&  !res.isEmpty()){
			for(int i=0; i<res.size(); i++){
				IRevListInfo revListInfo = (IRevListInfo) res.get(i);
				if(CRMHelper.getBigDecimal(revListInfo.getRemainLimitAmount()).compareTo(FDCHelper.ZERO) <= 0){
					continue;
				}
				tolRes.add(revListInfo);
			}
		}
		
		return tolRes;
	}

	protected List getDirRevList() throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}

	protected MoneySysTypeEnum getMoneySysType() {
		return MoneySysTypeEnum.TenancySys;
	}

	protected List getPreRevList() throws BOSException, EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) comboRevBizType
				.getSelectedItem();
		if (RevBizTypeEnum.obligate.equals(revBizType)) {
			return getSincerPayLists();
		}

		return null;
	}

	private List getSincerPayLists() throws EASBizException, BOSException {
		SincerObligateInfo sinObInfo = (SincerObligateInfo) this.f7SincerObligate
				.getData();
		if (sinObInfo == null) {
			MsgBox.showInfo(this, "请选择诚意预留单!");
			this.abort();
		}

		sinObInfo = TenancyHelper.getSincerObligateInfo(sinObInfo.getId()
				.toString());
		List list = new ArrayList();
		SincerPaylistEntrysCollection sinPayList = sinObInfo
				.getPayListEntrys();
		for (int j = 0; j < sinPayList.size(); j++) {
			SincerPaylistEntrysInfo sinPaylistInfo = sinPayList.get(j);
			list.add(sinPaylistInfo);
		}
		return list;
	}

	protected List getToTransferRevList() throws BOSException, EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) comboRevBizType.getSelectedItem();
		if (RevBizTypeEnum.tenancy.equals(revBizType)) {
			List list = new ArrayList();
			TenancyBillInfo tenancy = null;
			if(this.f7TenancyBill.getValue() instanceof TenancyBillInfo){
				if(this.f7TenancyBill.getValue()!=null)
				tenancy = (TenancyBillInfo)this.f7TenancyBill.getValue();
				inPutToTransListByTenancyBillandList(tenancy,list);
			}else{
				Object[] obj = (Object[])this.f7TenancyBill
				.getData();
				for(int i= 0 ;i<obj.length;i++){
					if(obj[i]!=null)
				inPutToTransListByTenancyBillandList((TenancyBillInfo)obj[i],list);
				}
			}
			
			
			
//			if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancy.getTenancyType())){
//				TenancyBillInfo oldTen = tenancy.getOldTenancyBill();
//				if(oldTen != null){
//					TenancyRoomPayListEntryCollection tenPays = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection("select *,moneyDefine.*,tenRoom.* where tenRoom.tenancy='"+oldTen.getId().toString()+"' and moneyDefine.moneyType='" + MoneyTypeEnum.DEPOSITAMOUNT_VALUE + "' and moneyDefine.sysType='" + MoneySysTypeEnum.TENANCYSYS_VALUE + "'");
//					for(int i=0; i<tenPays.size(); i++){
//						TenancyRoomPayListEntryInfo tenPay = tenPays.get(i);
//						if(CRMHelper.getBigDecimal(tenPay.getRemainLimitAmount()).compareTo(FDCHelper.ZERO) > 0){
//							list.add(tenPay);
//						}
//					}
//				}
//			}
			
			return list;
			
		}
		return null;
	}
	
	/**
	 * @author Tim_gao 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @description 得到专款明细
	 */
	protected void inPutToTransListByTenancyBillandList(TenancyBillInfo tenancy,List list) throws EASBizException, BOSException{
		tenancy = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(tenancy.getId()));
		SincerObligateInfo sinObInfo = tenancy.getSincerObligate();
		if (sinObInfo != null) {
			SincerObligateInfo sinObliInfo = TenancyHelper.getSincerObligateInfo(sinObInfo.getId().toString());
			list.addAll(Arrays.asList(sinObliInfo.getPayListEntrys().toArray()));
		}
		
		if(this.f7TransContract.getValue()==null){
			TenancyRoomPayListEntryCollection tenPays2 = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection("select *,moneyDefine.*,tenRoom.* where tenRoom.tenancy='"+tenancy.getId().toString()+"' and moneyDefine.moneyType='" + MoneyTypeEnum.DEPOSITAMOUNT_VALUE + "' and moneyDefine.sysType='" + MoneySysTypeEnum.TENANCYSYS_VALUE + "'");
			for(int i=0; i<tenPays2.size(); i++){
				TenancyRoomPayListEntryInfo tenPay = tenPays2.get(i);
				if(CRMHelper.getBigDecimal(tenPay.getRemainLimitAmount()).compareTo(FDCHelper.ZERO) > 0){
					list.add(tenPay);
				}
			}
		}else
		{
			TenancyBillInfo tenancyInfo = (TenancyBillInfo)this.f7TransContract.getValue();
			TenancyRoomPayListEntryCollection tenPays2 = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection("select *,moneyDefine.*,tenRoom.* where tenRoom.tenancy='"+tenancyInfo.getId().toString()+"' and moneyDefine.moneyType='" + MoneyTypeEnum.DEPOSITAMOUNT_VALUE + "' and moneyDefine.sysType='" + MoneySysTypeEnum.TENANCYSYS_VALUE + "'");
			for(int i=0; i<tenPays2.size(); i++){
				TenancyRoomPayListEntryInfo tenPay = tenPays2.get(i);
				if(CRMHelper.getBigDecimal(tenPay.getRemainLimitAmount()).compareTo(FDCHelper.ZERO) > 0){
					list.add(tenPay);
				}
			}
		}
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		TDprint(false);
	}
	
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		TDprint(true);
	}
	
	/**
	 * 
	 * @param ofNot true为套打预览 false为套打
	 * @throws IOException 
	 */
	public void TDprint(boolean ofNot) throws IOException{
		checkSelected();
		String purchaseID = this.editData.getId().toString();
		List idList = new ArrayList();
		idList.add(purchaseID);
		if (idList == null || idList.size() == 0)
			return;
		if(setting.getIsMoneyDeifine()){
			KDNoteHelper appHlp = new KDNoteHelper();			
			Map moneyMap = setting.getMoneyMap();
			Set moneySet = moneyMap.keySet();
			ICrossPrintDataProvider[] datas =new ICrossPrintDataProvider[moneySet.size()] ;
			String[] templatePaths=new String[moneySet.size()];
			//查找收款单对应款项用来和租赁设置里的匹配来寻找对应的模版
			FDCReceivingBillCollection fdcc=null;
			try {
				Set set=new HashSet();
				for(int i =0;i<idList.size();i++){
				set.add(idList.get(i));
				}
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
		        filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		        view.setFilter(filter);     
		        SelectorItemCollection sic=new SelectorItemCollection();
		        sic.add("*");
		        sic.add("entries.*");
		        sic.add("entries.moneyDefine.id");
		        sic.add("entries.moneyDefine.name");
		        sic.add("entries.moneyDefine.number");
		        view.setSelector(sic);
		        IFDCReceivingBill ifdc=FDCReceivingBillFactory.getRemoteInstance();
				fdcc=ifdc.getFDCReceivingBillCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			List moneyNameList=new ArrayList();
			for(int j=0;j<fdcc.size();j++){
				FDCReceivingBillEntryCollection fdce=fdcc.get(j).getEntries();
				for(int k=0;k<fdce.size();k++){
					FDCReceivingBillEntryInfo fdceInfo=fdce.get(k);
					if(fdceInfo.getMoneyDefine()!=null){
						moneyNameList.add(fdceInfo.getMoneyDefine().getId().toString());
					}
				}
			}
			
			//这里需要修改，收款单套打元数据必须是多元数据。因为如果分录有多条的话头就会打印重复
			List moneySettingList = new ArrayList();
			Iterator iter = moneySet.iterator();
			int k=0;
			while(iter.hasNext())
			{				
				MoneyDefineInfo moneyInfo = (MoneyDefineInfo)iter.next();
				moneySettingList.add(moneyInfo.getId().toString());
				if(moneyNameList.contains(moneyInfo.getId().toString()))
				{
					k++;
					datas [k]=new FDCCrossDuoCurrency(idList,getTDQueryPK(),moneyInfo.getId().toString(),"moneyDefine");
					templatePaths[k]=(String)moneyMap.get(moneyInfo);
				}
			}
			
			String moneySetID = equlasList(moneyNameList, moneySettingList);
			if(moneySetID.length()>0)
			{
				MsgBox.showInfo("款项 "+moneySetID+"没有找到对应的套打模版，请到租赁设置里去设置款项对应模版");
				this.abort();
			}
			int endLength=0;
			for(int i =0;i<datas.length;i++){
				if(datas[i]!=null){
					endLength++;
				}
			}
			List newDatasList=new ArrayList();
			List newTemplatePathsList=new ArrayList();
			for(int i =0;i<datas.length;i++){
				if(datas[i]!=null){
					newDatasList.add(datas[i]);
					newTemplatePathsList.add(templatePaths[i]);
				}
			}
				
			ICrossPrintDataProvider[] newDatas =new ICrossPrintDataProvider[endLength] ;
			String[] newTemplatePaths=new String[endLength];
			for(int i =0;i<newDatasList.size();i++){
				newDatas[i]=(ICrossPrintDataProvider) newDatasList.get(i);
				newTemplatePaths[i]=(String) newTemplatePathsList.get(i);
			}
			if(newTemplatePaths.length>0 && newTemplatePaths[0]!=null){
				appHlp.crossPrint(newTemplatePaths, newDatas, ofNot, SwingUtilities.getWindowAncestor(this));
			}else{
				MsgBox.showWarning("无此款项类型的模板，无法进行套打,请到租赁设置里去设置款项对应模版！");
				SysUtil.abort();
			}
		}else
		{
			FDCCrossDuoCurrency data = new FDCCrossDuoCurrency(idList, new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TENReceivingBillTDQuery"),"receivePrint");
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/tenancy/receivingBill/receiving", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		}
	}
	
	public String equlasList(List moneyList,List moneySettingList)
	{
		StringBuffer moneyNameID = new StringBuffer();
		Set set = new HashSet();
		for(int i=0;i<moneyList.size();i++)
		{
			boolean boo = false;
			String moneyID = (String)moneyList.get(i);
			for(int j=0;j<moneySettingList.size();j++)
			{
				String moneySettingId = (String)moneySettingList.get(j);
				if(moneyID.equals(moneySettingId))
				{
					boo = true;
				}
			}
			if(!boo)
			{
				set.add(moneyID);				
			}			
		}
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String ID = (String) iter.next();
			try {
				if(moneyNameID.length()==0)
				{
					
						String moneyName = ((MoneyDefineInfo)MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(ID))).getName();
						moneyNameID.append(moneyName);								
				}else
				{
					moneyNameID.append(",");
					String moneyName = ((MoneyDefineInfo)MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(ID))).getName();
					moneyNameID.append(moneyName);
				}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}	
		}
		return moneyNameID.toString();
	}
	
	 protected IMetaDataPK getTDQueryPK() {
	    	return new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TENReceivingBillTDQuery");
		}

	protected void f7SellProject_dataChanged(DataChangeEvent e)
			throws Exception {
		Object oldObj = e.getOldValue();
		Object newObj = e.getNewValue();
		if (oldObj == newObj) {
			return;
		}
		if (oldObj != null && newObj != null && oldObj.equals(newObj)) {
			return;
		} else {
			SellProjectInfo projectInfo = (SellProjectInfo)newObj;		
			setTeniFilter(projectInfo);
			setObliFilter(projectInfo,(RevBillTypeEnum)this.comboRevBillType.getSelectedItem());
			f7TenancyBill.setValue(null);
			f7SincerObligate.setValue(null);
			setCustomerFilter(this.f7TenancyBill.getValue());
			setCustomerFilter(this.f7SincerObligate.getValue());
		}
	}
	
	protected void setTeniFilter(SellProjectInfo projectInfo)
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (projectInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectInfo.getId()
							.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITING_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.BLANKOUT_VALUE,CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1 and #2 and #3 and #4");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null));
		}
		f7TenancyBill.setDisplayFormat("$name");
		viewInfo.setFilter(filter);
		f7TenancyBill.setEntityViewInfo(viewInfo);
		f7TransContract.setEntityViewInfo(viewInfo);
	}
	
	protected void setObliFilter(SellProjectInfo projectInfo,RevBillTypeEnum revBillType)
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (projectInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectInfo.getId()
							.toString()));
			if(RevBillTypeEnum.refundment.equals(revBillType))
			{
				filter.getFilterItems().add(
						new FilterItemInfo("bizState",BizStateEnum.CANCEL_VALUE));
				filter.getFilterItems().add(
						new FilterItemInfo("bizState",BizStateEnum.LEASE_VALUE));
			}else
			{
				filter.getFilterItems().add(
						new FilterItemInfo("bizState",BizStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(
						new FilterItemInfo("bizState",BizStateEnum.SINCEROBLIGATED_VALUE));
			}			
			filter.setMaskString("#0 and (#1 or #2)");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null));
		}
		viewInfo.setFilter(filter);
		f7SincerObligate.setEntityViewInfo(viewInfo);
	}
	
	protected void setCustomerFilter(Object obj) throws EASBizException, BOSException
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(obj!=null)
		{	
			if(obj instanceof TenancyBillInfo)
			{
				Set customerIDSet = new HashSet();
				TenancyBillInfo tenBill = (TenancyBillInfo)obj;
				tenBill = TenancyHelper.getTenancyBillInfo(tenBill.getId()
						.toString());
				TenancyCustomerEntryCollection tenCusColl = tenBill.getTenCustomerList();
				for(int i=0;i<tenCusColl.size();i++)
				{
					TenancyCustomerEntryInfo entryInfo = tenCusColl.get(i);					
					FDCCustomerInfo cusInfo = entryInfo.getFdcCustomer();
					customerIDSet.add(cusInfo.getId().toString());
				}
				
				RoomCollection roomColl = new RoomCollection();
				TenancyRoomEntryCollection tenRoomColl = tenBill.getTenancyRoomList();
				for(int i=0;i<tenRoomColl.size();i++)
				{
					TenancyRoomEntryInfo tenRoomInfo = tenRoomColl.get(i);
					roomColl.add(tenRoomInfo.getRoom());
					
				}
				if(roomColl.size() > 0)
				{
					f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,getMoneySysType(), roomColl,(SellProjectInfo)this.f7SellProject.getValue()));
				}
				removeDataChangeListener(f7Room);
				this.f7Room.setValue(tenRoomColl.get(0).getRoom());
				addDataChangeListener(f7Room);
				this.f7TenancyUser.setValue(tenBill.getTenancyAdviser());
				filter.getFilterItems().add(
						new FilterItemInfo("id",customerIDSet,CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				this.f7FdcCustomers.setEntityViewInfo(viewInfo);
				this.f7FdcCustomers.setValue(tenCusColl.get(0).getFdcCustomer());
			}else if(obj instanceof SincerObligateInfo)
			{
				Set customerIDSet = new HashSet();
				SincerObligateInfo sinInfo = (SincerObligateInfo)obj;
				sinInfo = TenancyHelper.getSincerObligateInfo(sinInfo.getId()
						.toString());
				SinCustomerEntrysCollection sinCusColl =  sinInfo.getSinCustomerList();
				for(int i=0;i<sinCusColl.size();i++)
				{
					SinCustomerEntrysInfo entryInfo = sinCusColl.get(i);
					FDCCustomerInfo cusInfo = entryInfo.getFdcCustomer();
					customerIDSet.add(cusInfo.getId().toString());
				}
				
				RoomCollection roomColl = new RoomCollection();
				SinObligateRoomsEntryCollection sinRoomColl = sinInfo.getSinRoomList();
				for(int i=0;i<sinRoomColl.size();i++)
				{
					SinObligateRoomsEntryInfo sinRoomInfo = sinRoomColl.get(i);
					roomColl.add(sinRoomInfo.getRoom());
					
				}
				if(roomColl.size() > 0)
				{
					f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,getMoneySysType(), roomColl,(SellProjectInfo)this.f7SellProject.getValue()));
				}
				this.f7Room.setValue(sinRoomColl.get(0).getRoom());
				
				filter.getFilterItems().add(
						new FilterItemInfo("id",customerIDSet,CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				this.f7FdcCustomers.setEntityViewInfo(viewInfo);
				this.f7FdcCustomers.setValue(sinCusColl.get(0).getFdcCustomer());
			}
	  }else
	    {
		    filter.getFilterItems().add(
				new FilterItemInfo("id",null));
		    viewInfo.setFilter(filter);
		    this.f7FdcCustomers.setEntityViewInfo(viewInfo);
		    f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,getMoneySysType(), null,(SellProjectInfo)this.f7SellProject.getValue()));
		    this.f7FdcCustomers.setValue(null);
		    this.f7Room.setValue(null);
	}
	}
	
	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		if(isLoadFields)
		{
			return;
		}
		RoomInfo room = null;
		if(e.getNewValue() instanceof RoomInfo)
		{
			room = (RoomInfo) e.getNewValue();
		}
		if (room == null)
		{
			return;
		}
		RevBizTypeEnum bizType = (RevBizTypeEnum)this.comboRevBizType.getSelectedItem();
		Set billStateSet = TenancyHelper.getQueryBillStates(bizType,(RevBillTypeEnum)this.comboRevBillType.getSelectedItem());
		if(RevBizTypeEnum.tenancy.equals(bizType))
		{
			TenancyBillCollection tenancyBillColl = TenancyHelper.getValidTenancyContractByRoomAndState(room, billStateSet);
//			if(tenancyBillColl != null && tenancyBillColl.size() < 1)
//			{
//				logger.warn("未取得房间号为 "+ room.getNumber() +" 的有效合同！");
//				this.f7TenancyBill.setValue(null);
//				if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
//				{
//					MsgBox.showInfo("该房间没有进行收款操作的合同！");
//				}
//				this.abort();
//			}
			this.f7Room.setUserObject(room);
			this.f7Room.setValue(room);
			if (tenancyBillColl !=null && tenancyBillColl.size() > 0)
			{
				TenancyBillInfo tenInfo = tenancyBillColl.get(0);
				this.f7TenancyBill.setValue(tenancyBillColl.get(0));
				this.f7TenancyUser.setValue(tenInfo.getTenancyAdviser());
			}
		}else 
			if(RevBizTypeEnum.obligate.equals(bizType))
		{
			SincerObligateCollection sincerColl = TenancyHelper.getValidObligateByRoomAndState(room, billStateSet);
//			if(sincerColl != null && sincerColl.size() < 1)
//			{
//				logger.warn("未取得房间号为 "+ room.getNumber() +" 的有效预留单！");
//				this.f7SincerObligate.setValue(null);
//				if(this.getOprtState() != OprtState.VIEW)
//				{
//					MsgBox.showInfo("该房间没有进行收款操作的预留单！");
//				}
//				this.abort();
//			}
			this.f7Room.setUserObject(room);
			this.f7Room.setValue(room);
			if (sincerColl != null && sincerColl.size() == 1)
			{
				this.f7SincerObligate.setValue(sincerColl.get(0));
			}
		}		
	}
	
	protected void f7TenancyBill_dataChanged(DataChangeEvent e)
			throws Exception {
		Object oldObj = e.getOldValue();
		Object newObj = e.getNewValue();
		if (oldObj == newObj) {
			return;
		}
		if (oldObj != null && newObj != null && oldObj.equals(newObj)) {
			return;
		}
//		if (this.isByCustomer.isSelected()) {
//			if(this.f7FdcCustomers.getValue()==null)
//			{/*	MsgBox.showWarning("请先填入交费客户信息！");*/
//				SysUtil.abort();
//			}
//			if(this.f7FdcCustomers.getValue()instanceof FDCCustomerInfo){
//				
//				setTenBillFilterByFDCCustomer((FDCCustomerInfo)this.f7FdcCustomers.getValue());
//			}else
//			{
//				Object[] obj = (Object[])this.f7FdcCustomers.getValue();
//				if(obj!=null)
//				setTenBillFilterByFDCCustomer((FDCCustomerInfo)obj[0]);
//			}
//			
//		} else {
			setCustomerFilter(this.f7TenancyBill.getValue());
			this.tblEntry.removeRows(false);
//		}
	}

	protected void f7SincerObligate_dataChanged(DataChangeEvent e)
			throws Exception {
		Object oldObj = e.getOldValue();
		Object newObj = e.getNewValue();
		if (oldObj == newObj) {
			return;
		}
		if (oldObj != null && newObj != null && oldObj.equals(newObj)) {
			return;
		}
		setCustomerFilter(this.f7SincerObligate.getValue());
		this.tblEntry.removeRows(false);
	}

	protected void tblEntry_editStopped(KDTEditEvent e) throws Exception {
		super.tblEntry_editStopped(e);
	}

	protected void initCompentByBizType(RevBizTypeEnum revBizType) {	
		setObliFilter((SellProjectInfo)this.f7SellProject.getValue(),(RevBillTypeEnum)this.comboRevBillType.getSelectedItem());
		if (RevBizTypeEnum.tenancy.equals(revBizType)) {
			this.contTenancyBill.setVisible(true);
			this.f7TenancyBill.setRequired(true);
			this.contSincerObligate.setVisible(false);
			this.f7SincerObligate.setRequired(false);
			if(!"VIEW".equals(this.getOprtState()))
			{
				this.f7SincerObligate.setValue(null);
				this.f7FdcCustomers.setValue(null);
				this.f7Room.setValue(null);
			}				
		} else if (RevBizTypeEnum.obligate.equals(revBizType)) {
			this.contTenancyBill.setVisible(false);
			this.f7TenancyBill.setRequired(false);
			this.contSincerObligate.setVisible(true);
			this.f7SincerObligate.setRequired(true);
			this.contTransContract.setVisible(false);
			if(!"VIEW".equals(this.getOprtState()))
			{
				this.f7TenancyBill.setValue(null);
				this.f7Room.setValue(null);
				this.f7FdcCustomers.setValue(null);
				this.f7TenancyUser.setValue(null);
			}			
		}
	}

	protected IObjectValue createNewData() {
		FDCReceivingBillInfo fdcRev = (FDCReceivingBillInfo) super.createNewData();

		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get(KEY_TENANCY_BILL);
		fdcRev.setTenancyObj(ten);

		SincerObligateInfo sinOb = (SincerObligateInfo) this.getUIContext().get(KEY_SINCER_OBLIGATE);
		fdcRev.setObligateObj(sinOb);
		this.btnSelectRevList.setEnabled(true);
//		fdcRev.setTenancyUser(userInfo);
		return fdcRev;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.addObjectCollection(this.getSelectors_forChildren());
		
		sels.add("srcRevBill.id");
		return sels;
	}

	protected String getHandleClazzName() {
		return "com.kingdee.eas.fdc.tenancy.app.TenRevHandle";
	}

	protected RevBizTypeEnum[] getBizTypes() {
		return new RevBizTypeEnum[] { RevBizTypeEnum.tenancy,
				RevBizTypeEnum.obligate};
	}

	protected IRevAction getAction() {
		return new DefaultRevAction(){
			public IRevListInfo createRevListInfo() {
				TenBillOtherPayInfo rev = new TenBillOtherPayInfo();
				rev.setId(BOSUuid.create(rev.getBOSType()));
				rev.setHead((TenancyBillInfo) TENReceivingBillEditUI.this.f7TenancyBill.getValue());
				return rev;
			}

			public List getExpandCols(RevListTypeEnum revListType) {
				List list = new ArrayList();
				if(RevListTypeEnum.tenRoomRev.equals(revListType))//租赁合同收款明细
				{				
//					list.add(new Object[]{"roomDesCol", "房间", new Integer(1), "tenRoom.roomLongNum"});
//					list.add(new Object[]{"leaseSeq", "租期", new Integer(2), "leaseSeq"});
//					list.add(new Object[]{"startDate", "起始日期", new Integer(5), "startDate"});
//					list.add(new Object[]{"endDate", "结束日期", new Integer(6), "endDate"});
//					
//					list.add(new Object[]{"tenBillNumber", "合同编码", new Integer(3), "tenBill.number"});
//					list.add(new Object[]{"tenBillName", "合同名称", new Integer(4), "tenBill.tenancyName"});
					
				}else if(RevListTypeEnum.tenOtherRev.equals(revListType))//其他应收收款明细
				{				
//					list.add(new Object[]{"roomDesCol2", "房间2", new Integer(5), "tenRoom.roomLongNum2"});	
//					list.add(new Object[]{"tenBillNumber", "合同编码", new Integer(3), "head.number"});
//					list.add(new Object[]{"tenBillName", "合同名称", new Integer(4), "head.tenancyName"});
				}
				else if(RevListTypeEnum.sincerobligate.equals(revListType))
				{
				}
				return list;
			}

			public RoomInfo getRoomInfoByRevList(IRevListInfo revListInfo) {
				if(revListInfo instanceof TenancyRoomPayListEntryInfo)
				{
					TenancyRoomPayListEntryInfo tenRoomEntryInfo = (TenancyRoomPayListEntryInfo)revListInfo;
					SelectorItemCollection selc = new SelectorItemCollection();
					selc.add("tenRoom.room.id");
					selc.add("tenRoom.room.name");
					selc.add("tenRoom.room.number");
					try {
						tenRoomEntryInfo = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(tenRoomEntryInfo.getId()), selc);
					} catch (Exception e) {
						e.printStackTrace();
						handleException(e);
					}
					if(tenRoomEntryInfo.getTenRoom()!=null && tenRoomEntryInfo.getTenRoom().getRoom()!=null)					
					{
						return tenRoomEntryInfo.getTenRoom().getRoom();
					}				
				}else if(revListInfo instanceof TenBillOtherPayInfo)
				{
					return (RoomInfo) TENReceivingBillEditUI.this.f7Room.getValue();
				}
				return null;
			}
			
			public BigDecimal getRemissionAmountRevList(IRevListInfo revListInfo)
			{
				if(revListInfo instanceof TenancyRoomPayListEntryInfo)
				{
					TenancyRoomPayListEntryInfo tenRoomEntryInfo = (TenancyRoomPayListEntryInfo)revListInfo;
					return tenRoomEntryInfo.getRemissionAmount();
				}
				return null;
			}
		};
	}
	
	protected void btnSelectRevList_actionPerformed(ActionEvent e) throws Exception {
		//super.btnSelectRevList_actionPerformed(e);
		
		
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		UIContext uiContext = prepareSelectRevListContext();
		
		RevBillTypeEnum type = (RevBillTypeEnum)this.comboRevBillType.getSelectedItem();
		
		if(type.getName().toString().equals(RevBillTypeEnum.GATHERING_VALUE)){
			uiContext.put("isGathering", "true");
		}
//		if(this.isByCustomer.isSelected()){
//			uiContext.put("ISBYCUSTOMER", "true");
//		}else{
			uiContext.put("ISBYCUSTOMER", "false");
//		}
		uiContext.put("tenancyBill", this.editData.getTenancyObj());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectRevListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		Map resContext = uiWindow.getUIObject().getUIContext();
		
		if (resContext.get(SelectRevListUI.KEY_RES_OPT) == null ||
				!((Boolean)resContext.get(SelectRevListUI.KEY_RES_OPT)).booleanValue()){
			return;
		}

		addEntryTableData(resContext);
	}
	
//	public void isByCustomer_actionPerformed(ActionEvent e) throws Exception{
//	      //write your code here
//		super.isByCustomer_actionPerformed(e);
//		if(this.isByCustomer.isSelected()){
//			this.f7TenancyBill.setValue(null);
//			this.f7Room.setValue(null);
//			this.f7Room.setEnabled(false);
//			this.f7TenancyBill.setEnabledMultiSelection(true);
//			if(this.f7FdcCustomers.getValue()!=null){
//				if(this.f7FdcCustomers.getValue() instanceof FDCCustomerInfo){
//					if(this.f7FdcCustomers.getValue() !=null)
//					setTenBillFilterByFDCCustomer((FDCCustomerInfo)this.f7FdcCustomers.getValue() );
//				}
//				else{
//				Object[] obj = (Object[])this.f7FdcCustomers.getValue();
//				if(obj!=null)
//				setTenBillFilterByFDCCustomer((FDCCustomerInfo)obj[0]);
//				}
//			}
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id",null,CompareType.ISNOT));
//			view.setFilter(filter);
//			this.f7FdcCustomers.setEntityViewInfo(view);
//		}else{
//			setTeniFilter((SellProjectInfo)this.f7SellProject.getValue());
//			this.f7TenancyBill.setEnabledMultiSelection(false);
//			this.f7Room.setEnabled(true);
//		}
//	}
	/**
	 * @author Tim_gao
	 * @description 根据交费客户信息过滤合同
	 */
	protected void setTenBillFilterByFDCCustomer(FDCCustomerInfo customer)
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (customer != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", customer.getId()));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITING_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",TenancyBillStateEnum.BLANKOUT_VALUE,CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1 and #2 and #3 and #4");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", null));
		}
		f7TenancyBill.setDisplayFormat("$number");
		viewInfo.setFilter(filter);
		f7TenancyBill.setEntityViewInfo(viewInfo);
		f7TransContract.setEntityViewInfo(viewInfo);
	}
	
//	public void f7FdcCustomers_dataChanged(DataChangeEvent e) throws Exception{
//	      //write your code here
//		super.f7FdcCustomers_dataChanged(e);
//		if(this.isByCustomer.isSelected()){
//			if(this.f7FdcCustomers.getValue()!=null){
//				Object[] obj = (Object[])this.f7FdcCustomers.getValue();
//				if(obj!=null)
//					setTenBillFilterByFDCCustomer((FDCCustomerInfo)obj[0]);
//			}
//		}
//	}
	
	private void checkCreateBillStatus(String id,String msg){
		try {
			IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", id, CompareType.EQUALS));
			evi.setFilter(filterInfo);
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("sourceBillId"));
			coll.add(new SelectorItemInfo("billStatus"));
			evi.setSelector(coll);
			ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
			if(collection!=null && collection.size()>0){
				//result = true;
				MsgBox.showWarning(this, msg);
				SysUtil.abort();
			}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"获取是否已生成出纳收款单状态失败!");
			}
			
	}
	protected void prmtAccountBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.prmtAccountBank.getValue()==null) return;
    	AccountBankInfo revAccBankInfo = (AccountBankInfo)this.prmtAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
		revAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(revAccBankInfo.getId()), sels);
    	this.prmtBank.setValue(revAccBankInfo.getBank());
    	this.prmtRevAccount.setValue(revAccBankInfo.getAccount());
	}
    protected void prmtBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(CommerceHelper.isF7DataChanged(e))
    	{
    		initrevAccountBankFilter();
    	}
	}
	/*
     * 银行账户根据银行来进行过滤
     */
    private void initrevAccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit(); 
    	if(this.prmtBank.getValue()!=null)
		{
			BankInfo revBank = (BankInfo)this.prmtBank.getValue();
			filter.getFilterItems().add(new FilterItemInfo("bank.id", revBank.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}
    }
    private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

}