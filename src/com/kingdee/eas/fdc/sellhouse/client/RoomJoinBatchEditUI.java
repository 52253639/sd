/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntry;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.JoinDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomJoinBatchEditUI extends AbstractRoomJoinBatchEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomJoinBatchEditUI.class);

	/**
	 * output class constructor
	 */
	public RoomJoinBatchEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		return new RoomJoinInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomJoinFactory.getRemoteInstance();
	}

	private ICell currentHandleCodeCell = null;
	
	public void onLoad() throws Exception {
		this.tblJoin.checkParsed();
		this.tblDataEntry.checkParsed();
		
		super.onLoad();
		
		String sellProjectId = (String) getUIContext().get("sellProjectId");
		SellProjectInfo sellproject = this.getSellProject(sellProjectId);
		this.f7SellProject.setDataNoNotify(sellproject);
						
		List roomJoinIdList = (List)getUIContext().get("roomJoinIdList");
		if(roomJoinIdList!=null && !roomJoinIdList.isEmpty()){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("room.id");
			view.getSelector().add("sign.id");
			view.getSelector().add("roomJoinDoScheme.id");
			view.getSelector().add("approachEntry.*");
			view.getSelector().add("dataEntry.*");
			
			FilterInfo filter = new FilterInfo();
			
			HashSet roomJoinIdSet = new HashSet();
			for(int i=0; i<roomJoinIdList.size(); i++){
				roomJoinIdSet.add(roomJoinIdList.get(i));
			}
			filter.getFilterItems().add(new FilterItemInfo("id", roomJoinIdSet, CompareType.INCLUDE));

			view.setFilter(filter);
			
			RoomJoinCollection roomJoinCol = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
			if(roomJoinCol != null && !roomJoinCol.isEmpty()){
				for(int r=0; r<roomJoinCol.size(); r++){
					RoomJoinInfo roomJoinInfo = roomJoinCol.get(r);
					
					this.loanRoomJoinInfo(roomJoinInfo);
				}
			}
		}
		//初始化批次控件
		String batchId = (String)getUIContext().get("batchId");
		BatchManageInfo batchInfo = null;
		if(batchId != null && !batchId.equals("null")){
			batchInfo = this.getBatchManage(batchId);
		}
		this.f7Batch.setDataNoNotify(batchInfo);
		
		//初始化办理进程下拉框选项
		JoinDoSchemeInfo schemeInfo = null;
		String schemeId = (String)getUIContext().get("schemeId");
		if(schemeId != null && !schemeId.equals("null")){
			schemeInfo = this.getScheme(schemeId);
			this.f7Scheme.setDataNoNotify(schemeInfo);
		}
		
		//填充资料表格
		if(schemeInfo!=null && schemeInfo.getDataEntries() != null && !schemeInfo.getDataEntries().isEmpty()){
			JoinDataEntryCollection dataCol = schemeInfo.getDataEntries();
			for(int i=0; i<dataCol.size(); i++){
				JoinDataEntryInfo dataInfo = dataCol.get(i);
				IRow row = this.tblDataEntry.addRow();
				row.getCell("name").setValue(dataInfo.getName());
				row.getCell("isFinish").setValue(Boolean.FALSE);
				row.getCell("remark").setValue(dataInfo.getRemark());
			}
		}
		
		if(schemeInfo != null && schemeInfo.getApprEntries() != null){
			this.comboCurApp.setRequired(true);
			
			KDComboBox combox = (KDComboBox)this.contCurApp.getBoundEditor();
			combox.removeAllItems();
			for(int i=0; i<schemeInfo.getApprEntries().size(); i++){
				combox.addItem(schemeInfo.getApprEntries().get(i).getName());
			}
		}
		this.initF7Scheme();
		
		this.initF7Batch();
		
		this.initF7AddRoom();
		
		/*ICell cell = row.getCell("number");
		currentHandleCodeCell = cell;
		handleCodingRule(cell);
		
		currentHandleCodeCell = null;*/
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionPrint,
				actionPrintPreview, actionEdit, actionRemove }, false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);
		
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		f7Transactor.setValue(user);
		pkJoinDate.setValue(null);
		
//		将入伙管理中的 约定签约日期  带入
		
	}

	public void onShow() throws Exception {
		super.onShow();
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();

			if (saleUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(new RoomJoinInfo(), saleUnit.getId().toString());
				if(isAllowModify){
					this.tblJoin.getColumn("number").getStyleAttributes().setLocked(false);
					this.tblJoin.getColumn("number").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);	
				}
			}
		}
	}
	
	protected void handleCodingRule(ICell cell) throws BOSException, CodingRuleException, EASBizException {
		if (!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		
		if(cell != null && cell.getValue() != null && !cell.getValue().toString().equals("")){
			return;
		}		
		
		IObjectValue obj = new RoomJoinInfo();
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (iCodingRuleManager.isExist(obj, currentOrgId)) {
			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(obj, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(obj, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(obj, currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(obj, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(obj, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(obj, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				cell.setValue(number);
			}
			setNumberTextEnabled();
		}
	}
	
	
	protected void prepareNumber(IObjectValue caller, String number) {
		if(currentHandleCodeCell != null){
			currentHandleCodeCell.setValue(number);
		}
	}
	
    protected void setNumberTextEnabled() {
    }
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.checkInput();
		
		Date joinDate = (Date) pkJoinDate.getValue();
		UserInfo transactor = (UserInfo) f7Transactor.getValue();

		Map valueMap = new HashMap();
		valueMap.put("transactDate", joinDate);
		valueMap.put("transactor", transactor);

		List roomJoinList = new ArrayList();
		for(int i=0; i<this.tblJoin.getRowCount(); i++){
			RoomJoinInfo joinInfo = (RoomJoinInfo)this.tblJoin.getRow(i).getUserObject();
			joinInfo.setTransactor(transactor);
			joinInfo.setJoinDate(joinDate);
			
			JoinDoSchemeInfo schemeInfo = (JoinDoSchemeInfo)this.f7Scheme.getValue();
			
			//产权方案变化，更新进程
			boolean isAppChange = false;
			if(joinInfo.getRoomJoinDoScheme()==null || (joinInfo.getRoomJoinDoScheme()!=null 
					&& !joinInfo.getRoomJoinDoScheme().getId().toString().equals(schemeInfo.getId().toString()))){
				isAppChange = true;
				joinInfo.getApproachEntry().clear();
				
				joinInfo.setRoomJoinDoScheme(schemeInfo);
			}
			//设置产权进程，状态和当前进程
			RoomJoinApproachEntryCollection appEntryCol = new RoomJoinApproachEntryCollection();
			for(int eIndex=0; eIndex<schemeInfo.getApprEntries().size(); eIndex++){
				JoinApproachEntryInfo appEntryInfo = schemeInfo.getApprEntries().get(eIndex);
				
				RoomJoinApproachEntryInfo joinAppEntryInfo = null;
				
				//判断新增进程还是修改进程
				if(isAppChange){  //新增进程
					joinAppEntryInfo = new RoomJoinApproachEntryInfo();
					joinAppEntryInfo.setName(appEntryInfo.getName());
					joinAppEntryInfo.setIsFinish(Boolean.FALSE.booleanValue());
					joinAppEntryInfo.setPromiseFinishDate(getApproachPromiseDate(schemeInfo.getApprEntries(), appEntryInfo, joinInfo));
					joinAppEntryInfo.setRemark(appEntryInfo.getRemark());
					joinAppEntryInfo.setIsFinishFlag(appEntryInfo.isIsFinish());
				}
				else{  //找出对应的进程进行修改
					for(int p=0; p<joinInfo.getApproachEntry().size(); p++){
						if(joinInfo.getApproachEntry().get(p).getName().equals(appEntryInfo.getName())){
							joinAppEntryInfo = joinInfo.getApproachEntry().get(p);
						}
					}
				}
				
				if(joinAppEntryInfo!=null && this.comboCurApp.getSelectedItem().toString().equals(appEntryInfo.getName())) {
					joinAppEntryInfo.setIsFinish(true);
					joinAppEntryInfo.setActualFinishDate(new Date());
					joinAppEntryInfo.setTransactor(transactor);
					
					if(!isAppChange && joinAppEntryInfo.isIsFinishFlag()){  //修改进程，使用进程原有的标识做判断是否办理完成
						joinInfo.setCurrentApproach("办理完成");
						joinInfo.setActualFinishDate(new Date());
						joinInfo.setJoinState(AFMortgagedStateEnum.TRANSACTED);
						
						this.ckBatchUpdate.setSelected(true);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, joinInfo.getRoom(), SHEManageHelper.JOIN,
								joinInfo.getPromiseFinishDate(), joinInfo.getActualFinishDate(), false);
					}
					else if(appEntryInfo.isIsFinish()){  //否则由方案的进程标识判断是否办理完成
						joinInfo.setCurrentApproach("办理完成");
						joinInfo.setActualFinishDate(new Date());
						this.ckBatchUpdate.setSelected(true);
						
						joinInfo.setJoinState(AFMortgagedStateEnum.TRANSACTED);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, joinInfo.getRoom(), SHEManageHelper.JOIN,
								joinInfo.getPromiseFinishDate(), joinInfo.getActualFinishDate(), false);
					}
					else{
						joinInfo.setCurrentApproach(appEntryInfo.getName());
						joinInfo.setJoinState(AFMortgagedStateEnum.TRANSACTING);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, joinInfo.getRoom(), SHEManageHelper.JOIN,
							joinInfo.getPromiseFinishDate(), null, false);
					}
				}
				
				if(isAppChange){
					appEntryCol.add(joinAppEntryInfo);
				}
			}
			//新增进程
			if(isAppChange && appEntryCol.size() > 0){
				joinInfo.getApproachEntry().addCollection(appEntryCol);
			}
			
			//更新房间的产权信息
			joinInfo.setNumber((String)this.tblJoin.getRow(i).getCell("number").getValue());
			
			//批量更新资料
			if(this.ckBatchUpdate.isSelected()){
				for(int p=0; p<joinInfo.getDataEntry().size(); p++){
					joinInfo.getDataEntry().clear();
				}
				
				RoomJoinDataEntryCollection dataEntryCol = new RoomJoinDataEntryCollection();
				for(int a=0; a<this.tblDataEntry.getRowCount(); a++ ){
					RoomJoinDataEntryInfo dataEntryInfo = new RoomJoinDataEntryInfo();
					dataEntryInfo.setName((String)this.tblDataEntry.getRow(a).getCell("name").getValue());
					dataEntryInfo.setIsFinish(((Boolean)this.tblDataEntry.getRow(a).getCell("isFinish").getValue()).booleanValue());
					dataEntryInfo.setFinishDate((Date)this.tblDataEntry.getRow(a).getCell("actFinishDate").getValue());
					dataEntryInfo.setRemark((String)this.tblDataEntry.getRow(a).getCell("remark").getValue());
					
					dataEntryCol.add(dataEntryInfo);
				}
				joinInfo.getDataEntry().addCollection(dataEntryCol);
			}
			
			roomJoinList.add(joinInfo);
		}
		for(int r=0; r<roomJoinList.size(); r++){
			RoomJoinFactory.getRemoteInstance().save((RoomJoinInfo)roomJoinList.get(r));
		}
		FDCMsgBox.showInfo("批量入伙办理 " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		/*RoomJoinFactory.getRemoteInstance().batchSave(roomJoins);
		this.setMessageText("批量入伙管理 "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		this.showMessage();*/
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		actionSave_actionPerformed(e);
	}

	protected void tblJoin_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblJoin_tableClicked(e);
		
		//非批量更新，则根据选中的按揭单据，刷新按揭资料表格数据
		if(e.getClickCount() == 1 && !this.ckBatchUpdate.isSelected()){
			RoomJoinInfo joinInfo = (RoomJoinInfo)this.tblJoin.getRow(e.getRowIndex()).getUserObject();
			RoomJoinDataEntryCollection entryCol = joinInfo.getDataEntry();
			
			this.tblDataEntry.removeRows();
			if(entryCol!=null && !entryCol.isEmpty()){
				for(int i=0; i<entryCol.size(); i++){
					RoomJoinDataEntryInfo entryInfo =  entryCol.get(i);
					
					IRow dataRow = this.tblDataEntry.addRow();
					//保存房间产权表的选中行，方便后续保存更新
					dataRow.setUserObject(new Integer(e.getRowIndex()));
					dataRow.getCell("id").setValue(entryInfo.getId());
					dataRow.getCell("name").setValue(entryInfo.getName());
					dataRow.getCell("actFinishDate").setValue(entryInfo.getFinishDate());
					dataRow.getCell("isFinish").setValue(new Boolean(entryInfo.isIsFinish()));
					dataRow.getCell("remark").setValue(entryInfo.getRemark());
					
					if(entryInfo.isIsFinish()){  //已经备齐，允许录入完成日期
						dataRow.getCell("actFinishDate").getStyleAttributes().setLocked(false);
					}
				}
			}
		}
	}
	
	protected void tblJoin_editStopped(KDTEditEvent e) throws Exception {
		super.tblJoin_editStopped(e);
	}
	
	protected void tblDataEntry_editStopped(KDTEditEvent e) throws Exception {
		super.tblDataEntry_editStopped(e);
		
		IRow currentRow = this.tblDataEntry.getRow(e.getRowIndex());
		
		if(e.getColIndex() == this.tblDataEntry.getColumnIndex("isFinish")){
			boolean isFinish = ((Boolean)e.getValue()).booleanValue();
			if(isFinish){
				this.tblDataEntry.getRow(e.getRowIndex()).getCell("actFinishDate").setValue(new Date());
				this.tblDataEntry.getRow(e.getRowIndex()).getCell("actFinishDate").getStyleAttributes().setLocked(false);
			}
			else{
				this.tblDataEntry.getRow(e.getRowIndex()).getCell("actFinishDate").setValue(null);
				this.tblDataEntry.getRow(e.getRowIndex()).getCell("actFinishDate").getStyleAttributes().setLocked(true);
			}
		}
		//保存资料
		if(currentRow.getUserObject() != null && currentRow.getCell("id").getValue()!=null){
			int roomRowIndex = ((Integer)currentRow.getUserObject()).intValue();
			if(this.tblJoin.getRow(roomRowIndex).getUserObject() != null){
				RoomJoinInfo joinInfo = (RoomJoinInfo)this.tblJoin.getRow(e.getRowIndex()).getUserObject();
				RoomJoinDataEntryCollection entryCol = joinInfo.getDataEntry();
				for(int i=0; i<entryCol.size(); i++){
					RoomJoinDataEntryInfo entryInfo =  entryCol.get(i);
					if(entryInfo.getId().toString().equals(currentRow.getCell("id").getValue().toString())){
						entryInfo.setName((String)currentRow.getCell("name").getValue());
						entryInfo.setIsFinish(((Boolean)currentRow.getCell("isFinish").getValue()).booleanValue());
						entryInfo.setFinishDate((Date)currentRow.getCell("actFinishDate").getValue());
						entryInfo.setRemark((String)currentRow.getCell("remark").getValue());
					}
				}
			}
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		/*IRoomSignContract iRoomSign = RoomSignContractFactory
				.getRemoteInstance();

		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		for (int i = 0, size = roomIds.size(); i < size; i++) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", roomIds.get(i).toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("isBlankOut", Boolean.TRUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("signDate");
			view.getSelector().add("onRecordDate");
			view.getSelector().add("contractNumber");
			view.getSelector().add("room.number");

			RoomSignContractCollection roomColl = iRoomSign
					.getRoomSignContractCollection(view);
			if (roomColl.size() > 0) {
				RoomSignContractInfo contract = roomColl.get(0);
				if (pkJoinDate.getValue() != null
						&& (DateTimeUtils.dayBefore((Date)pkJoinDate.getValue(),contract.getSignDate()))) {
					MsgBox.showInfo(this, contJoinDate.getBoundLabelText()
							+ "不能早于房间 " + contract.getRoom().getNumber()
							+ "  的签约日期！");
					SysUtil.abort();
				}
			}
		}*/

		super.verifyInput(e);
	}
	
	protected void btnRoomSelect_actionPerformed(ActionEvent e) throws Exception {
		super.btnRoomSelect_actionPerformed(e);
		
		this.f7AddRoom.setDataBySelector();
	}
			
	protected void btnRoomDelete_actionPerformed(ActionEvent e) throws Exception {
		super.btnRoomDelete_actionPerformed(e);
		
		if (this.tblJoin.getRowCount() == 0 || this.tblJoin.getSelectManager().size() == 0) {
		    MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
		    SysUtil.abort();
		}
		if(MsgBox.showConfirm2(this,"确认要删除该房间吗？") == MsgBox.CANCEL){
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(this.tblJoin);
		this.tblJoin.removeRow(row.getRowIndex());
	}
	
	protected void f7JoinDoScheme_dataChanged(DataChangeEvent e) throws Exception {
		super.f7JoinDoScheme_dataChanged(e);
		
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();
		
		if (newObj == null && oldObj != null) {
			this.tblDataEntry.removeRows();
		} 
		else if (newObj != null && oldObj == null) {
			loadJoinDoScheme((JoinDoSchemeInfo) newObj, this.tblDataEntry);
		}
		else if (newObj != null && oldObj != null) {
			// 当选择同一个房间时不做处理
			JoinDoSchemeInfo newScheme = (JoinDoSchemeInfo) (newObj);
			JoinDoSchemeInfo oldScheme = (JoinDoSchemeInfo) (oldObj);
			if (!newScheme.getId().equals(oldScheme.getId())) {
				loadJoinDoScheme((JoinDoSchemeInfo) newObj, this.tblDataEntry);
			}
		}
	}
		
	protected void f7Batch_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Batch_dataChanged(e);
		
		if(e.getNewValue() == null && e.getOldValue() != null){
			this.tblJoin.removeRows();
		}
		else if(e.getNewValue() != null && e.getOldValue() == null){
			BatchManageInfo newBatchInfo = (BatchManageInfo)e.getNewValue();
			this.loadBatchRoom(newBatchInfo.getId().toString());
		}
		else if(e.getNewValue() != null && e.getOldValue() != null){
			BatchManageInfo newBatchInfo = (BatchManageInfo)e.getNewValue();
			BatchManageInfo oldBatchInfo = (BatchManageInfo)e.getOldValue();
			if(!newBatchInfo.getId().toString().equals(oldBatchInfo.getId().toString())){
				this.loadBatchRoom(newBatchInfo.getId().toString());
			}
		}
		
		//根据选择的批次初始化添加房间f7控件
		this.initF7AddRoom();
	}
		
	protected void f7AddRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.f7AddRoom_dataChanged(e);
		
		RoomJoinInfo joinInfo = (RoomJoinInfo)e.getNewValue();
		for(int i=0; i<this.tblJoin.getRowCount(); i++){
			RoomJoinInfo rowJoinInfo = (RoomJoinInfo)this.tblJoin.getRow(i).getUserObject();
			if(rowJoinInfo.getId().toString().equals(joinInfo.getId().toString())){
				FDCMsgBox.showWarning("房间已存在，请重新选择");
		        SysUtil.abort();
			}
		}
		//加载房间信息
		this.loanRoomJoinInfo(joinInfo);
	}
		
	/**
	 * 根据选择的批次，加载房间产权信息
	 * @param batchId
	 * @throws BOSException 
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	private void loadBatchRoom(String batchId) throws BOSException, EASBizException, UuidException{
		this.tblJoin.removeRows();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("sign.id");
		view.getSelector().add("joinDoScheme.id");
		view.getSelector().add("approachEntry.*");
		view.getSelector().add("dataEntry.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomJoinCollection roomJoinCol = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		if(roomJoinCol != null && !roomJoinCol.isEmpty()){
			for(int r=0; r<roomJoinCol.size(); r++){
				RoomJoinInfo roomJoinInfo = roomJoinCol.get(r);
				this.loanRoomJoinInfo(roomJoinInfo);
			}
		}
	}
	
	private void loanRoomJoinInfo(RoomJoinInfo roomJoinInfo) throws EASBizException, BOSException, UuidException{
		//获取房间信息
		RoomInfo room = this.getRoomInfo(roomJoinInfo.getRoom().getId().toString());
		roomJoinInfo.setRoom(room);
		
		IRow row = this.tblJoin.addRow();
		row.setUserObject(roomJoinInfo);
		row.getCell("id").setValue(roomJoinInfo.getId());
		
		row.getCell("room").setValue(roomJoinInfo.getRoom().getNumber());
		row.getCell("roomId").setValue(roomJoinInfo.getRoom().getId().toString());
		
		row.getCell("number").setValue(roomJoinInfo.getNumber());
		
		if(roomJoinInfo.getSign() != null){  //签约
			SignManageInfo signInfo = this.getSignInfo(roomJoinInfo.getSign().getId().toString());
			roomJoinInfo.setSign(signInfo);
			row.getCell("customer").setValue(roomJoinInfo.getSign().getCustomerNames());
			row.getCell("customerPhone").setValue(roomJoinInfo.getSign().getCustomerPhone());
			row.getCell("contractNumber").setValue(roomJoinInfo.getSign().getNumber());
			row.getCell("contractNo").setValue(roomJoinInfo.getSign().getBizNumber());
			row.getCell("signDate").setValue(roomJoinInfo.getSign().getBizDate());
			row.getCell("dealAmount").setValue(roomJoinInfo.getSign().getDealTotalAmount());
		}
		
		if(roomJoinInfo.getRoomJoinDoScheme() != null){
			JoinDoSchemeInfo schemeInfo = this.getScheme(roomJoinInfo.getRoomJoinDoScheme().getId().toString());
			roomJoinInfo.setRoomJoinDoScheme(schemeInfo);
		}
	}
	
	protected void loadJoinDoScheme(JoinDoSchemeInfo schemeInfo, KDTable dataTable)
		throws EASBizException, BOSException {
		
		JoinApproachEntryCollection appCols = schemeInfo.getApprEntries();
		JoinDataEntryCollection dataCols = schemeInfo.getDataEntries();
		
		if(appCols != null && !appCols.isEmpty()){
			this.comboCurApp.setRequired(true);
			KDComboBox combox = (KDComboBox)this.contCurApp.getBoundEditor();
			combox.removeAllItems();
			
			for(int i=0; i<appCols.size(); i++){
				combox.addItem(appCols.get(i).getName());
			}
		}
		
		if (dataTable.getRowCount() > 0) {
			dataTable.removeRows();
		}
		
		for (int i = 0; i < dataCols.size(); i++) {
			JoinDataEntryInfo dataInfo = dataCols.get(i);
			IRow row = dataTable.addRow(i);
			row.getCell("name").setValue(dataInfo.getName());
			row.getCell("isFinish").setValue(Boolean.valueOf(false));
			row.getCell("remark").setValue(dataInfo.getRemark());
			
			row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
		}
	}
	
	protected void ckBatchUpdateData_itemStateChanged(ItemEvent e)
			throws Exception {
		super.ckBatchUpdateData_itemStateChanged(e);
		
		if(this.ckBatchUpdate.isSelected()){
			this.tblDataEntry.removeRows();
			
			JoinDoSchemeInfo schemeInfo = (JoinDoSchemeInfo)this.f7Scheme.getValue();
			//填充资料表格
			if(schemeInfo!=null && schemeInfo.getDataEntries() != null && !schemeInfo.getDataEntries().isEmpty()){
				JoinDataEntryCollection entryCol = schemeInfo.getDataEntries();
				for(int i=0; i<entryCol.size(); i++){
					JoinDataEntryInfo dataInfo = entryCol.get(i);
					IRow row = this.tblDataEntry.addRow();
					
					row.getCell("name").setValue(dataInfo.getName());
					row.getCell("isFinish").setValue(Boolean.FALSE);
					row.getCell("remark").setValue(dataInfo.getRemark());
					
					row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
				}
			}
		}
		else{
			this.tblDataEntry.removeRows();
		}
	}
	
	private SellProjectInfo getSellProject(String sellProjectId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance()
			.getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
		
		return sellProject;
	}
	
	private RoomInfo getRoomInfo(String roomId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("number");
		sels.add("sellState");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance()
			.getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		
		return roomInfo;
	}
	
	private SignManageInfo getSignInfo(String signId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("customerNames");
		selCol.add("customerPhone");
		selCol.add("bizNumber");
		selCol.add("bizDate");
		selCol.add("dealTotalAmount");
		selCol.add("srcId");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
			.getSignManageInfo(new ObjectUuidPK(signId), selCol);
		
		return signInfo;
	}
	
	private JoinDoSchemeInfo getScheme(String schemeId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		selCol.add(new SelectorItemInfo("apprEntries.*"));
		selCol.add(new SelectorItemInfo("dataEntries.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", schemeId));

		JoinDoSchemeInfo schemeInfo = JoinDoSchemeFactory.getRemoteInstance()
			.getJoinDoSchemeInfo(new ObjectUuidPK(schemeId), selCol);
		
		return schemeInfo;
	}
	
	private BatchManageInfo getBatchManage(String batchId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", batchId));

		BatchManageInfo batchInfo = BatchManageFactory.getRemoteInstance()
			.getBatchManageInfo(new ObjectUuidPK(batchId), selCol);
		
		return batchInfo;
	}

	/**
	 * 初始化产权方案f7控件
	 */
	private void initF7Batch(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		String sellProjectId = getUIContext().get("sellProjectId").toString();

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		filter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.JOIN_VALUE));
		
		view.setFilter(filter);

		this.f7Batch.setEntityViewInfo(view);
	}
	
	/**
	 * 初始化方案f7控件
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initF7Scheme() throws EASBizException, BOSException{
		String sellProjectId = String.valueOf(getUIContext().get("sellProjectId"));
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		SellProjectInfo info = new SellProjectInfo();
		info.setId(BOSUuid.read(sellProjectId));
		id = SHEManageHelper.getAllParentSellProjectCollection(info,id);
	
		if (id != null && id.size() > 0) {
			filter.getFilterItems().add(
				new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(id),
						CompareType.INNER));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", null,
							CompareType.EQUALS));
		}
		view.setFilter(filter);
		this.f7Scheme.setEntityViewInfo(view);
	}
	
	/**
	 * 初始化房间选择f7控件，根据办理批次过滤房间
	 */
	private void initF7AddRoom() {
		// 过滤条件
		FilterInfo filter = new FilterInfo();
		
		BatchManageInfo batchManageInfo = (BatchManageInfo)this.f7Batch.getValue();
		if(batchManageInfo == null){
			filter.getFilterItems().add(new FilterItemInfo("batchManage.id", "xxx"));
		}
		else{
			filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchManageInfo.getId().toString()));
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.roomJoinState", RoomJoinStateEnum.JOINED_VALUE, CompareType.NOTINCLUDE));
		
		view.setFilter(filter);

		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.NewRoomJoinQuery";
		SHEHelper.initF7(this.f7AddRoom, queryInfo, filter);
	}
	
	/**
	 * 遍历进程，获取当前进程的应完成日期
	 * @param appEntryCol
	 * @param currentAppInfo
	 * @return
	 */
	private Date getApproachPromiseDate(JoinApproachEntryCollection appEntryCol, 
			JoinApproachEntryInfo currentAppInfo, RoomJoinInfo roomJoin){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("指定日期")){  //参照日期为指定日期
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("签约日期")){  //参照日期为签约日期
			if(roomJoin.getSign()==null){
				return null;
			}
			else{
				return roomJoin.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<appEntryCol.size(); i++){
				JoinApproachEntryInfo appInfo = appEntryCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(appEntryCol, appInfo, roomJoin);
					if(tempDate != null){
						//根据间隔月和天计算日期
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	private void checkInput(){
		if(this.f7Transactor.getValue() == null){
			FDCMsgBox.showInfo("经办人不能为空!");
			SysUtil.abort();
		}
		else if(this.f7Scheme.getValue() == null){
			FDCMsgBox.showInfo("入伙方案不能为空!");
			SysUtil.abort();
		}
		else if(this.comboCurApp.getSelectedItem() == null){
			FDCMsgBox.showInfo("当前进程不能为空!");
			SysUtil.abort();
		}
		else if(this.pkJoinDate.getValue() == null){
			FDCMsgBox.showInfo("办理日期不能为空!");
			SysUtil.abort();
		}
		
		if(this.tblJoin.getRowCount() <= 0){
			FDCMsgBox.showInfo("入伙办理房间不能为空!");
			SysUtil.abort();
		}
	}
}