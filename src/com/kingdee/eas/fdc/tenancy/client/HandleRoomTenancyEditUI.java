package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.HandleTenancyFactory;
import com.kingdee.eas.fdc.tenancy.HandleTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TransactStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

public class HandleRoomTenancyEditUI extends AbstractHandleRoomTenancyEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(HandleRoomTenancyEditUI.class);

	private ItemAction[] hiddenAction = {this.actionEdit,this.actionAddNew,this.actionSave,this.actionCopy
			,this.actionPrint,this.actionPrintPreview,this.actionFirst,this.actionPre,this.actionNext,
			this.actionLast,this.actionSubmit,this.actionRemove,this.actionCancel,this.actionCancelCancel,this.actionAttachment};

	//接收传过来的合同
	TenancyBillInfo tenancyBillInfo = null;

	public HandleRoomTenancyEditUI() throws Exception
	{
		super();
	}

	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();		
		//this.btnSelectRoom.setVisible(false);
		//this.f7tenancyBill.setEnabled(false);
		this.customerTable.checkParsed();
		this.actionHandleTenancy.setEnabled(true);
		this.roomTable.checkParsed();
		this.initUI();
		if(tenancyRoom.getTabCount() == 2){
			this.tenancyRoom.remove(1);
		}
		TenancyClientHelper.hideButton(hiddenAction);
		this.btnAddLine.setVisible(false);
		if(this.getOprtState().equals("VIEW"))
		{
			this.btnSelectRoom.setVisible(false);
			this.btnHandleTenancy.setEnabled(false);
			String tenancyBillInfoID = (String)this.getUIContext().get("ID");
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("tenancyBill.*");
			sels.add("handleRoomEntrys.*");
			HandleTenancyInfo handleInfo = HandleTenancyFactory.getRemoteInstance().getHandleTenancyInfo(new ObjectUuidPK(tenancyBillInfoID),sels);
			tenancyBillInfo = TenancyHelper.getTenancyBillInfo(handleInfo.getTenancyBill().getId().toString());
			this.f7tenancyBill.setValue(tenancyBillInfo);
			if(handleInfo.getHandleRoomEntrys()!=null)
			{
				HandleRoomEntrysCollection handleColl = handleInfo.getHandleRoomEntrys();
				for(int i=0;i<handleColl.size();i++)
				{
					this.roomTable.checkParsed();
					IRow row = this.roomTable.addRow();
					HandleRoomEntrysInfo handleEntrysInfo = handleColl.get(i);
					showViewHandleRoomEntrysInfo(handleEntrysInfo,row);
				}
			}
			
		}else
		{
			this.btnSelectRoom.setVisible(true);
			this.btnSelectRoom.setEnabled(true);
			this.btnRemoveLine.setEnabled(true);
			String tenancyBillId = (String) this.getUIContext().get("tenancyBillId");

			if(tenancyBillId == null)
			{
				this.f7tenancyBill.setEnabled(true);
				SellProjectInfo projectInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id",projectInfo.getId().toString()));
				//保存提交审批中终止和作废的合同不能进行房间交接
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITING_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXPIRATION_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.BLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE,CompareType.NOTEQUALS));
				view.setFilter(filter);
				this.f7tenancyBill.setEntityViewInfo(view);
			}else
			{

				tenancyBillInfo = TenancyHelper.getTenancyBillInfo(tenancyBillId);
				this.f7tenancyBill.setValue(tenancyBillInfo);
			}
		}
		if(this.getUIContext().get("tenancyRoomEntryColl")!=null)
		{
			Set tenBillIDset = new HashSet();
			TenancyRoomEntryCollection tenancyRoomEntryColl = (TenancyRoomEntryCollection)this.getUIContext().get("tenancyRoomEntryColl");
			for(int i=0;i<tenancyRoomEntryColl.size();i++)
			{
				TenancyRoomEntryInfo tenEntryInfo = tenancyRoomEntryColl.get(i);
				TenancyBillInfo tenBillInfo = tenEntryInfo.getTenancy();
				tenBillIDset.add(tenBillInfo.getId().toString());
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id",tenBillIDset,CompareType.INCLUDE));
			view.setFilter(filter);
			this.f7tenancyBill.setEntityViewInfo(view);
		}
		
		tenancyRoom.remove(attachPanle);
		tenancyRoom.remove(joinVoucherPanel1);
		tenancyRoom.remove(customerMoveVoucherPanel1);
		tenancyRoom.remove(payListPanel1);
		roomTable.getColumn("tenancyType").getStyleAttributes().setHided(true);
	}
	
	private void showViewHandleRoomEntrysInfo(HandleRoomEntrysInfo handleEntrysInfo,IRow row)
	{
		row.getCell("handleType").setValue(handleEntrysInfo.getHandleType());
		row.getCell("objectNumber").setValue(handleEntrysInfo.getTenancyRoomNumber());
		row.getCell("transactState").setValue(handleEntrysInfo.getTransactState());
		row.getCell("finishDate").setValue(handleEntrysInfo.getFinishDate());
		row.getCell("description").setValue(handleEntrysInfo.getDescription());
	}

	protected void f7tenancyBill_dataChanged(DataChangeEvent e)
	throws Exception {
		super.f7tenancyBill_dataChanged(e);
		TenancyBillInfo tenBillInfo = (TenancyBillInfo)this.f7tenancyBill.getValue();
		TenancyBillInfo tenancyBillInfo = TenancyHelper.getTenancyBillInfo(tenBillInfo.getId().toString());
		//this.verityTenancyBill(tenancyBillInfo);
		this.fillTenancyBillInfo(tenancyBillInfo);
		//this.fillRoomTableByTenancyBill(tenancyBillInfo);
		this.customerTable.removeRows();
		this.fillCustomerTableByTenancyBill(tenancyBillInfo);
	}
	/**
	 * 填充合同的相关信息
	 * @param tenancyBillInfo
	 */
	private void fillTenancyBillInfo(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return;
		//this.f7tenancyBill.setValue(tenancyBillInfo);
		if(tenancyBillInfo.getTenancyAdviser() != null)
		{
			this.f7SaleMan.setText(tenancyBillInfo.getTenancyAdviser().getName());
		}
		if(tenancyBillInfo.getCreator() != null){
			this.f7Creator.setText(tenancyBillInfo.getCreator().getName());
		}
		this.txtTenancyTerm.setText(new Integer(tenancyBillInfo.getLeaseTime()).toString());
		if(tenancyBillInfo.getTenancyType() != null)
		{
			this.txtTenancyBillType.setText(tenancyBillInfo.getTenancyType().getAlias());
		}
		this.txtTenancyBillState.setText(tenancyBillInfo.getTenancyState().toString());
		this.pkStartDate.setValue(tenancyBillInfo.getStartDate());
		this.pkEndDate.setValue(tenancyBillInfo.getEndDate());
	}

	/**
	 * 初始化单元
	 *
	 */
	private void initUI()
	{
		KDCheckBox roomItem = new KDCheckBox();
		roomItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tenancyBillCustomer.requestFocus();
			}
		});
		this.roomTable.getColumn("finishDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		for(int i = 0; i < this.customerTable.getColumnCount(); i ++)
		{
			this.customerTable.getColumn(i).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 根据合同信息来填充客户表格
	 * @param tenancyBillInfo
	 */
	private void fillCustomerTableByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
		{
			logger.warn("传过来的合同为空呀，咋搞的哦...");
			return;
		}

		TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();

		for(int i = 0; i< tenancyCustomerEntryColl.size(); i ++)
		{
			//一个合同他所对应的客户没有了，这种情况会存在么？
			if(tenancyCustomerEntryColl.get(i).getFdcCustomer() != null)
			{
				FDCCustomerInfo fdcCustomer =tenancyCustomerEntryColl.get(i).getFdcCustomer();

				IRow row = this.customerTable.addRow();

				row.setUserObject(fdcCustomer);
				row.getCell("proporation").getFormattedStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				row.getCell("proporation").getFormattedStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				row.getCell("proporation").setValue(tenancyCustomerEntryColl.get(i).getPropertyPercent());
				row.getCell("customerName").setValue(fdcCustomer.getName());
				row.getCell("postalCode").setValue(fdcCustomer.getPostalcode());
				row.getCell("communicateTel").setValue(fdcCustomer.getPhone());
				row.getCell("certificateName").setValue(fdcCustomer.getCertificateName());
				row.getCell("certificateId").setValue(fdcCustomer.getCertificateNumber());
				row.getCell("communicateAddress").setValue(fdcCustomer.getAddress());
				row.getCell("bookedDate").setValue(fdcCustomer.getCreateTime());
				row.getCell("remark").setValue(fdcCustomer.getDescription());
			}
			else
			{
				logger.warn("这个合同所对应的客户没了哦...");
			}
		}

	}

	protected void btnSelectRoom_actionPerformed(ActionEvent e)
	throws Exception {
		if(this.f7tenancyBill.getValue()==null)
		{
			MsgBox.showInfo("请先选择要交接的合同");
			this.abort();
		}
		TenancyBillInfo tenBillInfo = (TenancyBillInfo)this.f7tenancyBill.getValue();
		TenancyBillInfo tenancyBillInfo = TenancyHelper.getTenancyBillInfo(tenBillInfo.getId().toString());
		TenancyBillStateEnum tenState = tenancyBillInfo.getTenancyState();
		if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) || TenancyBillStateEnum.Auditing.equals(tenState))
    	{
    		MsgBox.showInfo("合同还未经过审批,请先审批");
    		this.abort();
    	}else if(TenancyBillStateEnum.Expiration.equals(tenState))
    	{
    		MsgBox.showInfo("合同已经终止不能进行交房操作!");
    		this.abort();
    	}else if(TenancyBillStateEnum.Executing.equals(tenState))
    	{
    		MsgBox.showInfo("执行中的合同不能进行交房操作!");
    		this.abort();
    	}else if(TenancyBillStateEnum.BlankOut.equals(tenState))
    	{
    		MsgBox.showInfo("合同已作废!");
    		this.abort();
    	}  
		UIContext uiContext = new UIContext(this);
		uiContext.put("tenancyBillInfo", tenancyBillInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(SelectHandleRoomUI.class.getName(), uiContext, null, STATUS_VIEW);
		uiWindow.show();

		Map uiMap = uiWindow.getUIObject().getUIContext();	
		if(uiMap.get("list")!=null)
		{
			List tabList = new ArrayList();
			for(int i=0;i<roomTable.getRowCount();i++)
			{
				IRow row2 = roomTable.getRow(i);
				//ITenancyEntryInfo tenInfo = (ITenancyEntryInfo)row2.getCell("objectNumber").getUserObject();
				TenancyRoomEntryInfo tenInfo = (TenancyRoomEntryInfo)row2.getCell("objectNumber").getUserObject();
				if(tenInfo!=null)
				{
					tabList.add(tenInfo.getStrId());
				}
			}
			List list = (List)uiMap.get("list");
			for(int i=0;i<list.size();i++)
			{
				HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)list.get(i);
				if(handleRoomInfo.getTenancyRoom()!=null)			
				{
					//TenancyRoomEntryInfo tenRoomInfo = (TenancyRoomEntryInfo)list.get(i);
					if(TenancyClientHelper.isInclude(handleRoomInfo.getTenancyRoom().getId().toString(),tabList))
					{
						MsgBox.showInfo("该房间已经在列表中不能重复交房！");
						return;
					}else{
						this.roomTable.checkParsed();
						IRow row = this.roomTable.addRow();
						this.showHandleRoom(handleRoomInfo,row);
					}
				}
								
			}
		}
		if(uiMap.get("attachList")!=null)
		{
			List tabAttachList = new ArrayList();
			for(int i=0;i<attachTable.getRowCount();i++)
			{
				IRow row2 = attachTable.getRow(i);
				//ITenancyEntryInfo tenInfo = (ITenancyEntryInfo)row2.getCell("objectNumber").getUserObject();
				TenAttachResourceEntryInfo tenInfo = (TenAttachResourceEntryInfo)row2.getCell("attachNumber").getUserObject();
				if(tenInfo!=null)
				{
					tabAttachList.add(tenInfo.getStrId());
				}
			}
			List attachList = (List)uiMap.get("attachList");
			if(attachList.size()>0)
			{
				for(int i=0;i<attachList.size();i++)
				{
					HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)attachList.get(i);
					if(handleRoomInfo.getAttach()!=null)
					{
						if(TenancyClientHelper.isInclude(handleRoomInfo.getAttach().getId().toString(),attachList))
						{
							MsgBox.showInfo("该配套资源已经在列表中不能重复交房！");
							return;
						}else
						{
							this.attachTable.checkParsed();
							IRow row = this.attachTable.addRow();
							this.showHandleAttach(handleRoomInfo,row);
						}
					}					
				}
			}
		}
		super.btnSelectRoom_actionPerformed(e);
	}
	
	private void showHandleAttach(HandleRoomEntrysInfo handleRoomInfo,IRow row)
	{
		TenAttachResourceEntryInfo tenAttachInfo = handleRoomInfo.getAttach();
		String number = tenAttachInfo.getTenancyBill().getNumber();
		String name = tenAttachInfo.getAttachLongNum();
		StringBuffer handleName = new StringBuffer();
		handleName.append(number).append("_").append(name);
		handleRoomInfo.setName(handleName.toString());
		handleRoomInfo.setNumber(handleName.toString());
		row.getCell("attachNumber").setValue(handleRoomInfo.getAttach().getAttachLongNum());
		row.getCell("attachNumber").setUserObject(handleRoomInfo.getAttach());
		
		handleRoomInfo.setTenancyRoomNumber(handleRoomInfo.getAttach().getAttachLongNum());
		if(HandleStateEnum.NoHandleRoom.equals(handleRoomInfo.getOldHandleState()))
		{

				row.getCell("transactState").setValue(TransactStateEnum.TransactOver);
				handleRoomInfo.setTransactState(TransactStateEnum.TransactOver);
				handleRoomInfo.setNewHandleState(HandleStateEnum.livingHouse);
				try {
					row.getCell("finishDate").setValue(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
					handleRoomInfo.setFinishDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					e.printStackTrace();
				}
		}else if(HandleStateEnum.livingHouse.equals(handleRoomInfo.getOldHandleState()))
		{

				row.getCell("transactState").setValue(TransactStateEnum.TransactOver);
				handleRoomInfo.setTransactState(TransactStateEnum.TransactOver);
				handleRoomInfo.setNewHandleState(HandleStateEnum.AlreadyCallBack);
				try {
					row.getCell("finishDate").setValue(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
					handleRoomInfo.setFinishDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					e.printStackTrace();
				}
		}
		handleRoomInfo.setDescription((String)row.getCell("description").getValue());
		handleRoomInfo.setFinishDate((java.util.Date)row.getCell("finishDate").getValue());
		try {
			handleRoomInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		row.getCell("handleType").setValue(handleRoomInfo.getHandleType());
		row.getCell("handleType").getStyleAttributes().setLocked(true);
		row.getCell("attachNumber").getStyleAttributes().setLocked(true);
		row.getCell("transactState").getStyleAttributes().setLocked(true);
		//row.getCell("finishDate").getStyleAttributes().setLocked(true);
		row.setUserObject(handleRoomInfo);
	}

	private void showHandleRoom(HandleRoomEntrysInfo handleRoomInfo,IRow row)
	{
		TenancyRoomEntryInfo tenRoomInfo = handleRoomInfo.getTenancyRoom();
		String number = tenRoomInfo.getTenancy().getNumber();
		String name = tenRoomInfo.getRoomLongNum();
		StringBuffer handleName = new StringBuffer();
		handleName.append(number).append("_").append(name);
		handleRoomInfo.setName(handleName.toString());
		handleRoomInfo.setNumber(handleName.toString());
		row.getCell("objectNumber").setValue(handleRoomInfo.getTenancyRoom().getRoomLongNum());
		row.getCell("objectNumber").setUserObject(handleRoomInfo.getTenancyRoom());

		handleRoomInfo.setTenancyRoomNumber(handleRoomInfo.getTenancyRoom().getRoomLongNum());
		if(HandleStateEnum.NoHandleRoom.equals(handleRoomInfo.getOldHandleState()))
		{
			if(handleRoomInfo.getTenancyRoom().getRoom().isIsForPPM())
			{
				handleRoomInfo.setNewHandleState(HandleStateEnum.HandlingRoom);
				row.getCell("finishDate").getStyleAttributes().setLocked(true);
			}else
			{
				row.getCell("transactState").setValue(TransactStateEnum.TransactOver);
				handleRoomInfo.setTransactState(TransactStateEnum.TransactOver);
				handleRoomInfo.setNewHandleState(HandleStateEnum.livingHouse);
				try {
					row.getCell("finishDate").setValue(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
					handleRoomInfo.setFinishDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}else if(HandleStateEnum.livingHouse.equals(handleRoomInfo.getOldHandleState()))
		{
			if(handleRoomInfo.getTenancyRoom().getRoom().isIsForPPM())
			{
				handleRoomInfo.setNewHandleState(HandleStateEnum.CallBacking);
				row.getCell("finishDate").getStyleAttributes().setLocked(true);
			}else
			{
				row.getCell("transactState").setValue(TransactStateEnum.TransactOver);
				handleRoomInfo.setTransactState(TransactStateEnum.TransactOver);
				handleRoomInfo.setNewHandleState(HandleStateEnum.AlreadyCallBack);
				try {
					row.getCell("finishDate").setValue(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
					handleRoomInfo.setFinishDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		handleRoomInfo.setDescription((String)row.getCell("description").getValue());
		handleRoomInfo.setFinishDate((java.util.Date)row.getCell("finishDate").getValue());
		try {
			handleRoomInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		row.getCell("handleType").setValue(handleRoomInfo.getHandleType());
		row.getCell("handleType").getStyleAttributes().setLocked(true);
		row.getCell("objectNumber").getStyleAttributes().setLocked(true);
		row.getCell("transactState").getStyleAttributes().setLocked(true);
		//row.getCell("finishDate").getStyleAttributes().setLocked(true);
		row.setUserObject(handleRoomInfo);
	}

	public void actionHandleTenancy_actionPerformed(ActionEvent e)
	throws Exception {
		if(this.roomTable.getRowCount() < 1 && this.attachTable.getRowCount()<1)
		{
			MsgBox.showInfo("没有房间可进行交接操作！");
			this.abort();
		}
		HandleTenancyInfo handleInfo = new HandleTenancyInfo();
		HandleRoomEntrysCollection handleRoomEntryColl = new HandleRoomEntrysCollection();
		TenancyRoomEntryCollection tenancyRoomEntryColl = new TenancyRoomEntryCollection();
		TenAttachResourceEntryCollection tenAttachEntryColl = new TenAttachResourceEntryCollection();
		String name = "";
		for(int i = 0; i < this.roomTable.getRowCount(); i ++)
		{
			IRow row = this.roomTable.getRow(i);
			//被锁住的表示之前已经交接过房间,或者不能进行交接
			if(row.getStyleAttributes().isLocked()){
				continue;
			}
			HandleRoomEntrysInfo handleRoominfo = (HandleRoomEntrysInfo) row.getUserObject();
			name = handleRoominfo.getName();
			handleRoomEntryColl.add(handleRoominfo);
			TenancyRoomEntryInfo tenancyRoomEntryInfo = handleRoominfo.getTenancyRoom();
			tenancyRoomEntryColl.add(tenancyRoomEntryInfo);
		}
		for(int i = 0; i < this.attachTable.getRowCount(); i ++)
		{
			IRow row = this.attachTable.getRow(i);
			//被锁住的表示之前已经交接过配套资源,或者不能进行交接
			if(row.getStyleAttributes().isLocked()){
				continue;
			}
			HandleRoomEntrysInfo handleRoominfo = (HandleRoomEntrysInfo) row.getUserObject();
			name = handleRoominfo.getName();
			handleRoomEntryColl.add(handleRoominfo);
			TenAttachResourceEntryInfo tenAttachEntryInfo = handleRoominfo.getAttach();
			tenAttachEntryColl.add(tenAttachEntryInfo);
		}
		if(handleRoomEntryColl.isEmpty()){
			MsgBox.showInfo(this, "没有需要交接的房间!");
			return;
		}
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo)this.f7tenancyBill.getValue();
		handleInfo.setNumber(name);
		handleInfo.setName(name);
		handleInfo.setTenancyBill(tenancyBillInfo);
		handleInfo.getHandleRoomEntrys().addCollection(handleRoomEntryColl);
		HandleTenancyFactory.getRemoteInstance().submit(handleInfo);
		//TODO 这里有风险，需要在事物下完成
		//服务端完成了新合同，老合同上面交接时间，房源上的状态的改变
		TenancyBillFactory.getRemoteInstance().handleTenancyRoom(tenAttachEntryColl,tenancyRoomEntryColl,tenancyBillInfo,handleRoomEntryColl);	
		//更改合同状态
		this.updateTenancyBillState(tenancyBillInfo);
		MsgBox.showInfo("交接成功！");
		this.disposeUIWindow();
		super.actionHandleTenancy_actionPerformed(e);
	}
	
	/**
	 * 根据合同去找到它下面的所有配套资源
	 * @param tenancyBillInfo
	 * @return
	 */
	private TenAttachResourceEntryCollection getTenAttachEntryCollByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",tenancyBillInfo.getId().toString()));

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);

		TenAttachResourceEntryCollection tenAttachEntryColl = null;

		try
		{
			tenAttachEntryColl = TenAttachResourceEntryFactory.getRemoteInstance().getTenAttachResourceEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return tenAttachEntryColl;
	}

	/**
	 * 根据合同去找到它下面的所有房间
	 * @param tenancyBillInfo
	 * @return
	 */
	private TenancyRoomEntryCollection getTenancyRoomEntryCollByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillInfo.getId().toString()));

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);

		TenancyRoomEntryCollection tenancyRoomEntryColl = null;

		try
		{
			tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return tenancyRoomEntryColl;
	}
	
	//根据房间是否交接来确定改租中的合同的状态
	private boolean rejiggerIsCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;
		int noHandleCount = 0;
		TenancyBillInfo targetTen = new TenancyBillInfo();
		TenancyBillInfo targetTen2 = new TenancyBillInfo();
		try {
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenancyBillInfo.getId().toString());
			targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
			targetTen2 = TenancyHelper.getTenancyBillInfo(tenancyBillInfo.getId().toString());
			if(targetTen==null)
			{
				isCompleteDeal(dealName,tenancyBillInfo);
			}else
			{
				if(targetTen.getTenAttachResourceList().size()==0)
				{
					debug = isCompleteDeal(dealName,tenancyBillInfo);
				}else
				{
					List roomIDlist = new ArrayList();
					TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
					if(tenEntryColl.size()==0) debug = true;
					for(int i=0;i<tenEntryColl.size();i++)
					{
						TenancyRoomEntryInfo tenInfo = tenEntryColl.get(i);
						if(HandleStateEnum.NoHandleRoom.equals(tenInfo.getHandleState()))
						{
							noHandleCount++;
							roomIDlist.add(tenInfo.getRoom().getId().toString());
						}
					}
					TenancyRoomEntryCollection tenColl = targetTen2.getTenancyRoomList();
					for(int i=0;i<tenColl.size();i++)
					{
						TenancyRoomEntryInfo tenancyRoomEntryInfo = tenColl.get(i);
	                    if("shoufang".equalsIgnoreCase(dealName))
						{
							if((TenancyClientHelper.isInclude(tenancyRoomEntryInfo.getRoom().getId().toString(), roomIDlist) 
									&& tenancyRoomEntryInfo.getActQuitTenDate() != null) || !tenancyRoomEntryInfo.getHandleState().equals(HandleStateEnum.AlreadyCallBack))
							{
								temp ++;
							}
						}
						// 是否是没有完全交房
						if ("banjiaofang".equalsIgnoreCase(dealName))
						{
							if(temp == 0)
							{
								debug = false;
							}
							else if(temp<noHandleCount&&temp>0)
							{
								debug =true;
							}

						} else
						{
							if (temp == noHandleCount)
							{
								debug = true;
							} else
							{
								debug = false;
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return debug;
	}
	
	
	//根据配套资源是否交接来确定改租中的合同的状态
	private boolean rejiggerAttachIsCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = true;
		int temp = 0;
		int noHandleCount = 0;
		TenancyBillInfo targetTen = new TenancyBillInfo();
		TenancyBillInfo targetTen2 = new TenancyBillInfo();
		try {
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenancyBillInfo.getId().toString());
			targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
			targetTen2 = TenancyHelper.getTenancyBillInfo(tenancyBillInfo.getId().toString());
			if(targetTen==null)
			{
				isCompleteDealAttach(dealName,tenancyBillInfo);
			}else
			{
				if(targetTen.getTenancyRoomList().size()==0)
				{
					debug = isCompleteDealAttach(dealName,tenancyBillInfo);
				}else
				{
					List attachIDlist = new ArrayList();
					TenAttachResourceEntryCollection tenAttachEntryColl = targetTen.getTenAttachResourceList();
					for(int i=0;i<tenAttachEntryColl.size();i++)
					{
						TenAttachResourceEntryInfo tenInfo = tenAttachEntryColl.get(i);
						if(HandleStateEnum.NoHandleRoom.equals(tenInfo.getHandleState()))
						{
							noHandleCount++;
							attachIDlist.add(tenInfo.getAttachResource().getId().toString());
						}
					}
					TenAttachResourceEntryCollection tenColl = targetTen2.getTenAttachResourceList();
					for(int i=0;i<tenColl.size();i++)
					{
						TenAttachResourceEntryInfo tenAttachResEntryInfo = tenColl.get(i);
	                    if("shoufang".equalsIgnoreCase(dealName))
						{
	                    	//在新合同中
							if((TenancyClientHelper.isInclude(tenAttachResEntryInfo.getAttachResource().getId().toString(), attachIDlist) 
									&& tenAttachResEntryInfo.getActQuitTenDate() != null) || !tenAttachResEntryInfo.getHandleState().equals(HandleStateEnum.AlreadyCallBack))
							{
								temp ++;
							} 
						}
						// 是否是没有完全交房
						if ("banjiaofang".equalsIgnoreCase(dealName))
						{
							if(temp == 0)
							{
								debug = false;
							}
							else if(temp<noHandleCount&&temp>0)
							{
								debug =true;
							}

						} else
						{
							if (temp == noHandleCount)
							{
								debug = true;
							} else
							{
								debug = false;
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return debug;
	}
	
	/**
	 * 判断退租的合同下配套资源有没有完成交接，退租时不一定要把所有的配套资源全收回来才是完成交接。因为有可能在退租的原合同中有房间根本就没有
	 * 交房，所以退租时只有把所有已交的配套资源收回来就可以算是完成交房了
	 * @param dealName jiaofang shoufang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean quitIsCompleteDealAttach(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;
		int livingCount = 0;
		TenAttachResourceEntryCollection tenAttachEntryColl = this.getTenAttachEntryCollByTenancyBill(tenancyBillInfo);
		if(tenAttachEntryColl.size()==0) return true;
		for(int i=0;i<tenAttachEntryColl.size();i++)
		{
			TenAttachResourceEntryInfo tenAttachInfo = tenAttachEntryColl.get(i);
			if(HandleStateEnum.livingHouse.equals(tenAttachInfo.getHandleState())||HandleStateEnum.AlreadyCallBack.equals(tenAttachInfo.getHandleState()))
			{
				livingCount++;
			}
		}
		for(int i=0;i<tenAttachEntryColl.size();i++)
		{
			TenAttachResourceEntryInfo tenAttachEntryInfo = tenAttachEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenAttachEntryInfo.getActDeliveryAttachResDate()!= null)
				{
					temp ++;
				}
			}else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenAttachEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
			// 是否是没有完全交房
			if ("banjiaofang".equalsIgnoreCase(dealName))
			{
				if(temp == 0)
				{
					debug = false;
				}
				else if(temp<tenAttachEntryColl.size()&&temp>0)
				{
					debug =true;
				}

			} else
			{
				if (temp == livingCount)
				{
					debug = true;
				} else
				{
					debug = false;
				}
			}
		}
		return debug;
	}
	
	/**
	 * 判断退租的合同下房间有没有完成交接，退租时不一定要把所有的房间全收回来才是完成交接。因为有可能在退租的原合同中有房间根本就没有
	 * 交房，所以退租时只有把所有已交的房间收回来就可以算是完成交房了
	 * @param dealName jiaofang shoufang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean quitIsCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;
		int livingCount = 0;
		TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenancyRoomEntryCollByTenancyBill(tenancyBillInfo);
		if(tenancyRoomEntryColl.size()==0) debug = true;
		for(int i=0;i<tenancyRoomEntryColl.size();i++)
		{
			TenancyRoomEntryInfo tenancyRoomInfo = tenancyRoomEntryColl.get(i);
			if(HandleStateEnum.livingHouse.equals(tenancyRoomInfo.getHandleState())||HandleStateEnum.AlreadyCallBack.equals(tenancyRoomInfo.getHandleState()))
			{
				livingCount++;
			}
		}
		for(int i=0;i<tenancyRoomEntryColl.size();i++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null)
				{
					temp ++;
				}
			}else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
			// 是否是没有完全交房
			if ("banjiaofang".equalsIgnoreCase(dealName))
			{
				if(temp == 0)
				{
					debug = false;
				}
				else if(temp<tenancyRoomEntryColl.size()&&temp>0)
				{
					debug =true;
				}

			} else
			{
				if (temp == livingCount)
				{
					debug = true;
				} else
				{
					debug = false;
				}
			}
		}
		return debug;
	}

	/**
	 * 通过传入的时间字段名称去判断该合同下的房间有没有完成交接
	 * @param deanName  ex:  jiaofang   shoufang banjiaofang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;

		TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenancyRoomEntryCollByTenancyBill(tenancyBillInfo);
		if(tenancyRoomEntryColl.size() == 0)
			debug =  true;
		for(int i = 0;i < tenancyRoomEntryColl.size(); i ++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null)
				{
					temp ++;
				}
			}
			else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
		}
		// 是否是没有完全交房
		if ("banjiaofang".equalsIgnoreCase(dealName))
		{
			if(temp == 0)
			{
				debug = false;
			}
			else if(temp<tenancyRoomEntryColl.size()&&temp>0)
			{
				debug =true;
			}

		} else
		{
			if (temp == tenancyRoomEntryColl.size())
			{
				debug = true;
			} else
			{
				debug = false;
			}
		}
		return debug;
	}
	
	/**
	 * 通过传入的时间字段名称去判断该合同下的配套资源有没有完成交接
	 * @param deanName  ex:  jiaofang   shoufang banjiaofang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isCompleteDealAttach(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;

 		TenAttachResourceEntryCollection tenAttachEntryColl = this.getTenAttachEntryCollByTenancyBill(tenancyBillInfo);
		if(tenAttachEntryColl.size() ==0)
			debug = true;
		for(int i = 0;i < tenAttachEntryColl.size(); i ++)
		{
			TenAttachResourceEntryInfo tenAttachEntryInfo = tenAttachEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenAttachEntryInfo.getActDeliveryAttachResDate() != null)
				{
					temp ++;
				}
			}
			else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenAttachEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
		}
		// 是否是没有完全交房
		if ("banjiaofang".equalsIgnoreCase(dealName))
		{
			if(temp == 0)
			{
				debug = false;
			}
			else if(temp<tenAttachEntryColl.size()&&temp>0)
			{
				debug =true;
			}

		} else
		{
			if (temp == tenAttachEntryColl.size())
			{
				debug = true;
			} else
			{
				debug = false;
			}
		}
		return debug;
	}

	/**
	 * 更改合同的状态，这里更改的不仅是传入的合同的状态，还要判断她的后续合同或者乱七八糟什么的合同一起更改
	 * @param tenancyBillInfo
	 */
	private void updateTenancyBillState(TenancyBillInfo billInfo)
	{
		if(billInfo == null)
			return;
		//需要在数据库中重新取值一次
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billInfo.getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("oldTenancyBill.*");
		view.getSelector().add("*");
		view.setFilter(filter);

		TenancyBillInfo tenancyBillInfo = null;
		try
		{
			tenancyBillInfo = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view).get(0);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		if(tenancyBillInfo == null)
		{
			logger.warn("出错啦...");
			MsgBox.showInfo("系统错误，已记录日志！");
			this.abort();
		}
		//如果是半执行状态 或已交押金首租，则说明当前是交房操作，它有可能有旧合同，也
		if(TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState())
				|| TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()))
		{		
			//旧合同
			TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
			if(oldTenancyBillInfo == null)
			{
				//完成交房
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					//如果房间已经交接完并且配套资源也交接完了那么合同状态为执行中状态
					if(isCompleteDealAttach("jiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
					}else
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
					
				}
				else
				{
					//是否是半交房
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
			}//有旧合同的情况下
			else
			{
				//				完成交房
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					//如果房间已经交接完并且配套资源也交接完了那么合同状态为执行中状态
					if(isCompleteDealAttach("jiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
					}else
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				else
				{
					//是否是半交房
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				//处理旧合同
				if(isCompleteDeal("shoufang",oldTenancyBillInfo))
				{
					if(isCompleteDealAttach("shoufang",oldTenancyBillInfo))
					{
						oldTenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
					}				
				}
				SelectorItemCollection selectorColl = new SelectorItemCollection();
				selectorColl.add("tenancyState");
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(oldTenancyBillInfo,selectorColl);
				} catch (Exception e)
				{
					super.handUIException(e);
				}
			}

		}else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenancyBillInfo.getTenancyState()))
		{
			// 完成收房
			if (rejiggerIsCompleteDeal("shoufang",tenancyBillInfo))
			{
				if(rejiggerAttachIsCompleteDeal("shoufang",tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.Expiration);
				}else
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.RejiggerTenancying);
				}
				
			} else
			{
				// 是否是半交房
				if (rejiggerIsCompleteDeal("banjiaofang", tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.RejiggerTenancying);
				}
			}				
			SelectorItemCollection selectorColl = new SelectorItemCollection();
			selectorColl.add("tenancyState");
			try
			{
				TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
			} catch (Exception e)
			{
				super.handUIException(e);
			}
		}else if(TenancyBillStateEnum.ContinueTenancying.equals(tenancyBillInfo.getTenancyState()))
		{
			// 完成收房
			if (isCompleteDeal("shoufang",tenancyBillInfo))
			{
				if(isCompleteDealAttach("shoufang",tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.Expiration);
				}else
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ContinueTenancying);
				}
				
			} else
			{
				// 是否是半交房
				if (isCompleteDeal("banjiaofang", tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ContinueTenancying);
				}
			}				
			SelectorItemCollection selectorColl = new SelectorItemCollection();
			selectorColl.add("tenancyState");
			try
			{
				TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
			} catch (Exception e)
			{
				super.handUIException(e);
			}
		}else if(TenancyBillStateEnum.ChangeNaming.equals(tenancyBillInfo.getTenancyState()))
		{
			// 完成收房
			if (isCompleteDeal("shoufang",tenancyBillInfo))
			{
				if(isCompleteDealAttach("shoufang",tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.Expiration);
				}else
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ChangeNaming);
				}
			} else
			{
				// 是否是半交房
				if (isCompleteDeal("banjiaofang", tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ChangeNaming);
				}
			}				
			SelectorItemCollection selectorColl = new SelectorItemCollection();
			selectorColl.add("tenancyState");
			try
			{
				TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
			} catch (Exception e)
			{
				super.handUIException(e);
			}
		}else if(TenancyBillStateEnum.PriceChanging.equals(tenancyBillInfo.getTenancyState()))
		{   //添加价格变更 xin_wang 2010.12.22
			// 完成收房
			if (isCompleteDeal("shoufang",tenancyBillInfo))
			{
				if(isCompleteDealAttach("shoufang",tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.Expiration);
				}else
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ChangeNaming);
				}
			} else
			{
				// 是否是半交房
				if (isCompleteDeal("banjiaofang", tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.ChangeNaming);
				}
			}				
			SelectorItemCollection selectorColl = new SelectorItemCollection();
			selectorColl.add("tenancyState");
			try
			{
				TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
			} catch (Exception e)
			{
				super.handUIException(e);
			}
		}else if(TenancyBillStateEnum.QuitTenancying.equals(tenancyBillInfo.getTenancyState()))
		{
			// 完成收房
			if (quitIsCompleteDeal("shoufang",tenancyBillInfo))
			{
				if(quitIsCompleteDealAttach("shoufang",tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.Expiration);
				}else
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.QuitTenancying);
				}
				
			} else
			{
				// 是否是半交房
				if (quitIsCompleteDeal("banjiaofang", tenancyBillInfo))
				{
					tenancyBillInfo
					.setTenancyState(TenancyBillStateEnum.QuitTenancying);
				}
			}				
			SelectorItemCollection selectorColl = new SelectorItemCollection();
			selectorColl.add("tenancyState");
			try
			{
				TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
			} catch (Exception e)
			{
				super.handUIException(e);
			}
		}
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add("tenancyState");
		try
		{
			TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
		} catch (Exception e)
		{
			super.handUIException(e);
		}
	}

	public void onShow() throws Exception
	{
		super.onShow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return HandleTenancyFactory.getRemoteInstance();
	}

}