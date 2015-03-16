/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
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
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class BatchRoomManageUI extends AbstractBatchRoomManageUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchRoomManageUI.class);
	
	private String source = "";
	
	private final String RoomPropertySource = "roomProperty";
	
	private final String RoomJoinSource = "roomJoin";

	public BatchRoomManageUI() throws Exception {
		super();
	}
	
	protected IObjectValue createNewData() {
		BatchManageInfo batchInfo = new BatchManageInfo();
		SellProjectInfo sellProject = (SellProjectInfo)getUIContext().get("sellProject");
		batchInfo.setSellProject(sellProject);
		String src = (String)getUIContext().get("source");
		if(src.equals(this.RoomPropertySource)){
			batchInfo.setSource(BatchManageSourceEnum.Property);
		}
		else{
			batchInfo.setSource(BatchManageSourceEnum.Join);
		}
		batchInfo.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		return batchInfo;
	}
	
	public void onLoad() throws Exception {
		this.source = (String)getUIContext().get("source");
		
		super.onLoad();
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew, actionAuditResult,
				actionCopy, actionPre, actionNext, actionFirst, actionLast, actionAudit,
				actionCancel, actionCancelCancel, actionRemove }, false);
		
		if(getOprtState().equals(STATUS_VIEW)){
			this.actionEdit.setEnabled(false);
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
		}
		
		this.prmtTransactor.setEditable(false);
		
		this.initTable();
		
		this.initF7AddRoom();
	}
	
	public void loadFields() {
		super.loadFields();
		
		this.initBatchRoomEntry();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddRoom_actionPerformed(e);
		
		this.f7AddRoom.setDataBySelector();
	}

	protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnDeleteRoom_actionPerformed(e);
		
		if (this.kdtBatchRoomEntry.getRowCount() == 0 || this.kdtBatchRoomEntry.getSelectManager().size() == 0) {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		if(MsgBox.showConfirm2(this,"确认要删除该房间吗？") == MsgBox.CANCEL){
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(this.kdtBatchRoomEntry);
		this.kdtBatchRoomEntry.removeRow(row.getRowIndex());
	}
	
	/**
	 * 判断是否启用编码规则
	 * @author liang_ren969
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	private boolean isUseRule() throws BOSException, CodingRuleException,
			EASBizException {
		boolean isUse = false;
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();

		IObjectValue objValue = null;

		if (this.source.equals(this.RoomPropertySource)) {
			objValue = new RoomPropertyBookInfo();
		} else {
			objValue = new RoomJoinInfo();
		}
		currentOrgId = FDCClientHelper.getCurrentOrgId();
		if (iCodingRuleManager.isExist(objValue, currentOrgId)) {
			isUse = true;
		}

		return isUse;

	}
	

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.checkInput();
		
		IObjectPK pk = null;
		if(this.editData.getId() != null){
			pk = new ObjectUuidPK(this.editData.getId().toString());
		}
		
		this.editData.setSellProject((SellProjectInfo)this.prmtSellProject.getValue());
		this.editData.setName(this.txtBatchNumber.getText());
		this.editData.setNumber(this.txtBatchNumber.getText());
		this.editData.setTransactor((UserInfo)this.prmtTransactor.getValue());
		this.editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		//设置批次房间分录
		BatchRoomEntryCollection batchRoomCol = new BatchRoomEntryCollection();
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			BatchRoomEntryInfo batchRoomInfo = new BatchRoomEntryInfo();
			
			if(this.source.equals(this.RoomPropertySource)){
				RoomPropertyBookInfo roomBook = (RoomPropertyBookInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
				batchRoomInfo.setRoom(roomBook.getRoom());
			}
			else{
				RoomJoinInfo roomJoin = (RoomJoinInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
				batchRoomInfo.setRoom(roomJoin.getRoom());
			}
			batchRoomInfo.setIsValid(true);
			batchRoomCol.add(batchRoomInfo);
		}
		this.editData.getBatchRoomEntry().clear();
		this.editData.getBatchRoomEntry().addCollection(batchRoomCol);
		
		if(pk == null){
			pk = this.getBizInterface().save(this.editData);
		}
		else{
			this.getBizInterface().update(pk, this.editData);
		}
		
		if(pk != null){
			BatchManageInfo batchInfo = BatchManageFactory.getRemoteInstance().getBatchManageInfo(pk);
			this.editData.setId(batchInfo.getId());
			
			//保存产权/入伙单据
			for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
				IRow row = this.kdtBatchRoomEntry.getRow(i);
				if(this.source.equals(this.RoomPropertySource)){  //产权
					RoomPropertyBookInfo roomBook = (RoomPropertyBookInfo)row.getUserObject();
					roomBook.setPropState(AFMortgagedStateEnum.UNTRANSACT);  //未办理
					
					IObjectPK bookPk = null;
					if(roomBook.getId() != null){
						bookPk = new ObjectUuidPK(roomBook.getId().toString());
					}
					if(row.getCell("number").getValue()==null){
						String number = this.createNumber(false);
						if(number!=null && !"".equals(number)){
							roomBook.setNumber(number);
						}
					}else{
						roomBook.setNumber((String)row.getCell("number").getValue());
					}
					
					if(row.getCell("propertyCardNo").getValue() != null){
						roomBook.setPropertyNumber((String)row.getCell("propertyCardNo").getValue());
					}
					roomBook.setBatchManage(batchInfo);
					//保存
					try {
						if(bookPk == null){
							bookPk = RoomPropertyBookFactory.getRemoteInstance().save(roomBook);
							roomBook.setId(BOSUuid.read(bookPk.toString()));
						}
						else{
							RoomPropertyBookFactory.getRemoteInstance().update(bookPk, roomBook);
						}

						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, roomBook.getRoom(), SHEManageHelper.INTEREST,
								roomBook.getPromiseFinishDate(), roomBook.getActualFinishDate(), false);
						
					} catch (EASBizException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间生成产权单据失败，编码重复");
						SysUtil.abort();
					} catch (BOSException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间生成产权单据失败，编码重复");
						SysUtil.abort();
					}
				}
				else{  //入伙
					RoomJoinInfo roomJoin = (RoomJoinInfo)row.getUserObject();
					roomJoin.setJoinState(AFMortgagedStateEnum.UNTRANSACT);
					
					IObjectPK joinPK = null;
					if(roomJoin.getId() != null){
						joinPK = new ObjectUuidPK(roomJoin.getId().toString());
					}
					
					if(row.getCell("number").getValue()==null){
						String number = this.createNumber(false);
						if(number!=null && !"".equals(number)){
							roomJoin.setNumber(number);
						}
					}else{
						roomJoin.setNumber((String)row.getCell("number").getValue());
					}

					roomJoin.setBatchManage(batchInfo);
					//保存
					try {
						if(joinPK == null){
							joinPK = RoomJoinFactory.getRemoteInstance().save(roomJoin);
							roomJoin.setId(BOSUuid.read(joinPK.toString()));
						}
						else{
							RoomJoinFactory.getRemoteInstance().update(joinPK, roomJoin);
						}
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, roomJoin.getRoom(), SHEManageHelper.JOIN,
								roomJoin.getPromiseFinishDate(), roomJoin.getActualFinishDate(), false);
						
					} catch (EASBizException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间生成单据失败，编码重复");
						SysUtil.abort();
					} catch (BOSException ex) {
						logger.error(ex);
						FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间生成单据失败，编码重复");
						SysUtil.abort();
					}
				}
			}
		}
		
		FDCMsgBox.showInfo("批量办理 " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		this.actionEdit.setEnabled(false);
		this.btnAddRoom.setEnabled(false);
		this.btnDeleteRoom.setEnabled(false);
		this.btnAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BatchManageFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtBatchRoomEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtBatchNumber;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector =  super.getSelectors();
		selector.add(new SelectorItemInfo("batchRoomEntry.*"));
		return selector;
	}
	
	/**
	 * 检查录入数据，以及产权单据编码是否重复
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void checkInput() throws EASBizException, BOSException {
		if(this.txtBatchNumber.getText() == null || this.txtBatchNumber.getText().trim().length() <= 0){
			FDCMsgBox.showInfo("请输入办理批次");
			SysUtil.abort();
		}
		//检查同一项目下编码是否重复
		boolean isBatchNumDup = false;
		SellProjectInfo sellProject = (SellProjectInfo) getUIContext().get("sellProject");
		
		FilterInfo batchFilter = new FilterInfo();
		batchFilter.getFilterItems().add(new FilterItemInfo("number", this.txtBatchNumber.getText()));
		if(this.source.equals(this.RoomPropertySource)){  //产权批次
			batchFilter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.PROPERTY_VALUE));
		}
		else{  //入伙批次
			batchFilter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.JOIN_VALUE));
		}
		batchFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		
		if (this.editData.getId() != null) {
			batchFilter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(),
					CompareType.NOTEQUALS));
		}
		isBatchNumDup = BatchManageFactory.getRemoteInstance().exists(batchFilter);
		
		if(isBatchNumDup){
			FDCMsgBox.showInfo("办理批次名称不能重复！");
			SysUtil.abort();
		}
		
		if(this.prmtTransactor.getValue() == null){
			FDCMsgBox.showInfo("请选择经办人");
			SysUtil.abort();
		}
		
		if(this.kdtBatchRoomEntry.getRowCount() <= 0){
			FDCMsgBox.showInfo("请选择批次房间");
			SysUtil.abort();
		}
		
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			IRow row = this.kdtBatchRoomEntry.getRow(i);
			if(row.getCell("number").getValue() == null || row.getCell("number").getValue().toString().trim().length() == 0){
				FDCMsgBox.showInfo("请输入第 "+ (i+1) + "行的业务编码");
				SysUtil.abort();
			}
			if(row.getCell("number").getValue().toString().length() > 80){
				FDCMsgBox.showInfo("第 "+ (i+1) + "行的业务编码长度不能超过80个字符");
				SysUtil.abort();
			}
			if(row.getCell("propertyCardNo").getValue()!=null
				&& row.getCell("propertyCardNo").getValue().toString().length() > 255){
				FDCMsgBox.showInfo("第 "+ (i+1) + "行的房产证号长度不能超过255个字符");
				SysUtil.abort();
			}
			//检查同一项目下编码是否重复
			
			if(!this.isUseRule()){
				boolean result = false;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number", row.getCell("number").getValue().toString()));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellproject.id", sellProject.getId().toString()));
				
				if(this.source.equals(this.RoomPropertySource)){  //产权
					RoomPropertyBookInfo bookInfo = (RoomPropertyBookInfo)row.getUserObject();
					if (bookInfo.getId() != null) {
						filter.getFilterItems().add(new FilterItemInfo("id", bookInfo.getId().toString(),
								CompareType.NOTEQUALS));
					}
					result = RoomPropertyBookFactory.getRemoteInstance().exists(filter);
				}
				else{  //入伙
					RoomJoinInfo joinInfo = (RoomJoinInfo)row.getUserObject();
					if (joinInfo.getId() != null) {
						filter.getFilterItems().add(new FilterItemInfo("id", joinInfo.getId().toString(),
								CompareType.NOTEQUALS));
					}
					result = RoomJoinFactory.getRemoteInstance().exists(filter);
				}
				
				if(result){
					FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间单据编码重复！");
					SysUtil.abort();
				}
			}
			}
			
		
		//检查当前房间分录的产权编码是否重复
		for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
			IRow row = this.kdtBatchRoomEntry.getRow(i);
			for(int j=i+1; j<this.kdtBatchRoomEntry.getRowCount(); j++){
				IRow compareRow = this.kdtBatchRoomEntry.getRow(j);
				if(row.getCell("number").getValue().toString()
					.equals(compareRow.getCell("number").getValue().toString())){
					FDCMsgBox.showInfo("第" + ( i + 1 ) + "行的房间单据编码重复！");
					SysUtil.abort();
				}
			}
		}
	}
	
	/**
	 * 初始化表格编辑状态
	 */
	private void initTable(){
		//初始化房间表格
		this.kdtBatchRoomEntry.getStyleAttributes().setLocked(true);
		
		KDTextField txtNumber = new KDTextField();
    	txtNumber.setHorizontalAlignment(KDTextField.LEFT);
    	txtNumber.setMaxLength(80);
    	this.kdtBatchRoomEntry.getColumn("number").setEditor(new KDTDefaultCellEditor(txtNumber));
    	this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(false);
    	
    	try{
    		if(this.isUseRule()){
    			this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(true);
        	}else{
        		this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setLocked(false);
        		this.kdtBatchRoomEntry.getColumn("number").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
        	}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	
    	KDTextField txtPropertyNumber = new KDTextField();
    	txtPropertyNumber.setMaxLength(255);
    	this.kdtBatchRoomEntry.getColumn("propertyCardNo").setEditor(new KDTDefaultCellEditor(txtPropertyNumber));
    	
    	if(this.source.equals(this.RoomPropertySource)){
    		this.kdtBatchRoomEntry.getColumn("propertyCardNo").getStyleAttributes().setLocked(false);
		}
    	else{
    		this.kdtBatchRoomEntry.getColumn("propertyCardNo").getStyleAttributes().setHided(true);
    	}
	}
	
	/**
	 * 初始化表格数据
	 */
	private void initBatchRoomEntry(){
		if(this.editData.getId() != null){
			String batchId = this.getEditData().getId().toString();
			try {
				if(this.source.equals(this.RoomPropertySource)){  //产权
					RoomPropertyBookCollection roomBookCol = this.getRoomBookCollection(batchId);
					if(roomBookCol!=null && roomBookCol.size()>0){
						for(int i=0; i<roomBookCol.size(); i++){
							IRow row = this.kdtBatchRoomEntry.addRow();
							
							RoomPropertyBookInfo bookInfo = roomBookCol.get(i);
							SignManageInfo signInfo = bookInfo.getSign();
							row.setUserObject(bookInfo);
							
							row.getCell("room").setValue(bookInfo.getRoom().getNumber());
							row.getCell("roomId").setValue(bookInfo.getRoom().getId());
							row.getCell("number").setValue(bookInfo.getNumber());
							if(signInfo != null){
								if(signInfo.getCustomerNames() != null){
									row.getCell("customer").setValue(signInfo.getCustomerNames());
								}
								if(signInfo.getCustomerPhone() != null){
									row.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
								}
								if(signInfo.getNumber() != null){
									row.getCell("contractNumber").setValue(signInfo.getNumber());
								}
								if(signInfo.getBizNumber() != null){
									row.getCell("contractNo").setValue(signInfo.getBizNumber());
								}
								if(signInfo.getBizDate() != null){
									row.getCell("signDate").setValue(signInfo.getBizDate());
								}
							}
							row.getCell("propertyCardNo").setValue(bookInfo.getPropertyNumber());
						}
					}
				}
				else{
					RoomJoinCollection roomJoinCol = this.getRoomJoinCollection(batchId);
					if(roomJoinCol!=null && roomJoinCol.size()>0){
						for(int i=0; i<roomJoinCol.size(); i++){
							IRow row = this.kdtBatchRoomEntry.addRow();
							
							RoomJoinInfo joinInfo = roomJoinCol.get(i);
							SignManageInfo signInfo = joinInfo.getSign();
							row.setUserObject(joinInfo);
							
							row.getCell("room").setValue(joinInfo.getRoom().getNumber());
							row.getCell("roomId").setValue(joinInfo.getRoom().getId());
							row.getCell("number").setValue(joinInfo.getNumber());
							if(signInfo != null){
								if(signInfo.getCustomerNames() != null){
									row.getCell("customer").setValue(signInfo.getCustomerNames());
								}
								if(signInfo.getCustomerPhone() != null){
									row.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
								}
								if(signInfo.getNumber() != null){
									row.getCell("contractNumber").setValue(signInfo.getNumber());
								}
								if(signInfo.getBizNumber() != null){
									row.getCell("contractNo").setValue(signInfo.getBizNumber());
								}
								if(signInfo.getBizDate() != null){
									row.getCell("signDate").setValue(signInfo.getBizDate());
								}
							}
						}
					}
				}
			} catch (BOSException e) {
				logger.error(e);
			}
		}
	}
	
	protected void f7AddRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.f7AddRoom_dataChanged(e);
		
		Object[] rooms = (Object[])e.getNewValue();
		this.f7AddRoom.setValue(null);
		
		if (rooms == null || rooms.length == 0 || rooms[0] == null) {
			return;
		}
		RoomCollection roomCol = new RoomCollection();
		for (int r = 0; r < rooms.length; r++) {
			RoomInfo selRoom = (RoomInfo)rooms[r];
			//跳过已选择的房间
			boolean isExist = false;
			for(int i=0; i<this.kdtBatchRoomEntry.getRowCount(); i++){
				String roomId = null;
				if(this.source.equals(this.RoomPropertySource)){
					RoomPropertyBookInfo rowRoomProBook = (RoomPropertyBookInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
					roomId = rowRoomProBook.getRoom().getId().toString();
				}
				else{
					RoomJoinInfo rowRoomJoinInfo = (RoomJoinInfo)this.kdtBatchRoomEntry.getRow(i).getUserObject();
					roomId = rowRoomJoinInfo.getRoom().getId().toString();
				}
				if(roomId.equals(selRoom.getId().toString())){
					isExist = true;
				}
			}
			if(isExist){
				continue;
			}
			
			//检查房间是否已经办理产权/入伙
			if(this.isRoomBooked(selRoom)){
				FDCMsgBox.showWarning("房间" + selRoom.getNumber() + "已办理了批次，自动忽略");
	            continue;
			}
			
			roomCol.add(selRoom);
		}
		
		this.addRoomToBatch(roomCol);
		
	}
	
	private void addRoomToBatch(RoomCollection roomCol) throws Exception{
		for(int i=0; i<roomCol.size(); i++){
			RoomInfo selRoom = roomCol.get(i);
			
			//得到房间的签约信息
			SignManageInfo signInfo = this.getSignInfo(selRoom);
			String number = "";
			IRow newRow = this.kdtBatchRoomEntry.addRow();
			if(this.source.equals(this.RoomPropertySource)){
				//为房间新增一条产权单据
				RoomPropertyBookInfo roomPropertyBook = new RoomPropertyBookInfo();
				roomPropertyBook.setRoom(selRoom);
				roomPropertyBook.setSign(signInfo);
				
				//设置承诺完成日期
				if(signInfo != null){
					roomPropertyBook.setPromiseFinishDate(this.getPromiseDate(signInfo));
				}
				
				number= this.createNumber(true);
				if(number!=null && !"".equals(number)){
					roomPropertyBook.setNumber(number);
					this.setNumberTextEnabled(newRow);
				}
				newRow.setUserObject(roomPropertyBook);
			}
			else{
				//为房间新增一条入伙单据
				RoomJoinInfo roomJoinInfo = new RoomJoinInfo();
				roomJoinInfo.setRoom(selRoom);
				roomJoinInfo.setSign(signInfo);
				
				//设置承诺完成日期
				if(signInfo != null){
					roomJoinInfo.setPromiseFinishDate(this.getPromiseDate(signInfo));
					roomJoinInfo.setApptJoinDate(signInfo.getJoinInDate());
				}
				
				number = this.createNumber(true);
				if(number!=null && !"".equals(number)){
					roomJoinInfo.setNumber(number);
					this.setNumberTextEnabled(newRow);
				}
				newRow.setUserObject(roomJoinInfo);
			}
			if(!"".equals(number)){
				newRow.getCell("number").setValue(number);
			}
			
			newRow.getCell("room").setValue(selRoom.getNumber());
			if(signInfo != null){
				if(signInfo.getCustomerNames() != null){
					newRow.getCell("customer").setValue(signInfo.getCustomerNames());
				}
				if(signInfo.getCustomerPhone() != null){
					newRow.getCell("customerPhone").setValue(signInfo.getCustomerPhone());
				}
				if(signInfo.getNumber() != null){
					newRow.getCell("contractNumber").setValue(signInfo.getNumber());
				}
				if(signInfo.getBizNumber() != null){
					newRow.getCell("contractNo").setValue(signInfo.getBizNumber());
				}
				if(signInfo.getBizDate() != null){
					newRow.getCell("signDate").setValue(signInfo.getBizDate());
				}
			}
		}
	}
	
	/**
	 * 根据签约单中付款方案的业务明细，计算承诺完成日期
	 * @param sign
	 * @return
	 */
	private Date getPromiseDate(SignManageInfo sign){
		if(sign.getPayType() == null){
			return null;
		}
		Date promiseDate = null;
		//获取付款方案的业务明细
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("bizLists.*"));
		
		try {
			SHEPayTypeInfo payType = SHEPayTypeFactory.getRemoteInstance()
				.getSHEPayTypeInfo(new ObjectUuidPK(sign.getPayType().getId().toString()), selector);
			if(payType != null && payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
				for(int i=0; i<payType.getBizLists().size(); i++){
					BizListEntryInfo bizEntry = payType.getBizLists().get(i);
					if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.PROPERTY)){
						promiseDate = this.getBizPromiseDate(payType.getBizLists(), bizEntry);
						break;
					}
				}
			}
			
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return promiseDate;
	}
	
	/**
	 * 遍历明细，计算应完成日期
	 * @param bizEntryCol
	 * @param currentBizInfo
	 * @return
	 */
	private Date getBizPromiseDate(BizListEntryCollection bizEntryCol, 
			BizListEntryInfo currentBizInfo){
		if(currentBizInfo.getPayTypeBizTime() == null){
			return null;
		}
		else if(currentBizInfo.getPayTypeBizTime().equals("指定日期")){  //参照日期为指定日期
			return currentBizInfo.getAppDate();
		}
		else{
			for(int i=0; i<bizEntryCol.size(); i++){
				BizListEntryInfo bizInfo = bizEntryCol.get(i);
				if(bizInfo.getPayTypeBizFlow().getAlias().equals(currentBizInfo.getPayTypeBizTime())){
					Date tempDate = getBizPromiseDate(bizEntryCol, bizInfo);
					if(tempDate != null){
						//根据间隔月和天计算日期
						int mon = currentBizInfo.getMonthLimit();
						int day = currentBizInfo.getDayLimit();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * 初始化房间选择f7控件
	 */
	private void initF7AddRoom() {
		SellProjectInfo sellProject = (SellProjectInfo) getUIContext().get("sellProject");
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.isForSHE", "true"));  // 售楼属性
		filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Sign.getValue()));  //签约状态
		view.setFilter(filter);
		
		this.f7AddRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
		this.f7AddRoom.setEnabledMultiSelection(true);
		this.f7AddRoom.setEntityViewInfo(view);
	}
	
	/**
	 * 检查房间是否已在办理批次中
	 * @param room
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isRoomBooked(RoomInfo room) throws EASBizException, BOSException{
		boolean isBooked = false;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		if(this.source.equals(this.RoomPropertySource)){
			filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.PROPERTY_VALUE));
		}
		else{
			filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.JOIN_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", Boolean.TRUE));

		view.setFilter(filter);
		
		BatchRoomEntryCollection batchManageCol = BatchRoomEntryFactory.getRemoteInstance().getBatchRoomEntryCollection(view);
		if(batchManageCol!=null && batchManageCol.size()>0){
			isBooked = true;
		}
		
		return isBooked;
	}
	
	private RoomPropertyBookCollection getRoomBookCollection(String batchId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("sign.id");
		view.getSelector().add("sign.customerNames");
		view.getSelector().add("sign.customerPhone");
		view.getSelector().add("sign.number");
		view.getSelector().add("sign.bizNumber");
		view.getSelector().add("sign.bizDate");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomPropertyBookCollection roomBookCol = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		
		return roomBookCol;
	}
	
	private RoomJoinCollection getRoomJoinCollection(String batchId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("sign.id");
		view.getSelector().add("sign.customerNames");
		view.getSelector().add("sign.customerPhone");
		view.getSelector().add("sign.number");
		view.getSelector().add("sign.bizNumber");
		view.getSelector().add("sign.bizDate");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("batchManage.id", batchId));

		view.setFilter(filter);
		
		RoomJoinCollection roomBookCol = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		
		return roomBookCol;
	}
	
	/**
	 * 根据房间获取签约单信息
	 * @param room
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private SignManageInfo getSignInfo(RoomInfo room) throws EASBizException, BOSException{
		SignManageInfo signInfo = null;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("billId");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		
		TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		//从交易主线中的签约单Id获取签约单
		if(transactionCol!=null && !transactionCol.isEmpty()){
			SelectorItemCollection selCol = new SelectorItemCollection();
			selCol.add("*");
			
			signInfo = SignManageFactory.getRemoteInstance()
				.getSignManageInfo(new ObjectUuidPK(transactionCol.get(0).getBillId().toString()), selCol);
			
			return signInfo;
		}
		return signInfo;
	}

	private String createNumber(boolean isCornette) throws BOSException, CodingRuleException,
			EASBizException {
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		// if (!"ADDNEW".equals(this.oprtState)) {
		// return number;
		// }
		boolean isExist = true;
		
		IObjectValue objValue = null;
		
		if(this.source.equals(this.RoomPropertySource)){
			objValue = new RoomPropertyBookInfo();
			((RoomPropertyBookInfo)objValue).setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}else{
			objValue = new RoomJoinInfo();
			((RoomJoinInfo)objValue).setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue,
					currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				// String number = null;
				if(isCornette){
					return "";
				}
				if (iCodingRuleManager.isUseIntermitNumber(objValue,
						currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					} else {
						// 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = iCodingRuleManager.getNumber(objValue,
								currentOrgId);
					}
				} else {
					number = iCodingRuleManager.getNumber(objValue,
							currentOrgId);
				}
			}
		}
		return number;
	}
	
	private String getRuleNumber(IObjectValue caller, String orgId){
		
		 String number = "";
		 
		 try {
	            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	            if (orgId == null || orgId.trim().length() == 0)
	            {
	            	//当前用户所属组织不存在时，缺省实现是用集团的
	                 orgId = OrgConstants.DEF_CU_ID;
	            }
	            if (iCodingRuleManager.isExist(caller, orgId))
	            {
	               
	                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
	                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
	                    if (iCodingRuleManager.isUserSelect(caller, orgId))
	                    {
	                        // 启用了断号支持功能,同时启用了用户选择断号功能
	                        // KDBizPromptBox pb = new KDBizPromptBox();
	                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
	                                caller, orgId, null, null);
	                        // pb.setSelector(intermilNOF7);
	                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
	                        Object object = null;
	                        if (iCodingRuleManager.isDHExist(caller, orgId))
	                        {
	                            intermilNOF7.show();
	                            object = intermilNOF7.getData();
	                        }
	                        if (object != null)
	                        {
	                            number = object.toString();
	                        }
	                        else
	                        {
	                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
	                            number = iCodingRuleManager
	                                    .getNumber(caller, orgId);
	                        }
	                    }
	                    else
	                    {
	                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
	                        number = iCodingRuleManager.getNumber(caller, orgId);
	                    }
	                }
	                else
	                {
	                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
	                    number = iCodingRuleManager.getNumber(caller, orgId);
	                }
	            }
     
	        } catch (Exception err) {
	            //获取编码规则出错，现可以手工输入编码！
	           //handleCodingRuleError(err);
	        }
	        
	      return number;
	}
	
	protected void setNumberTextEnabled(IRow row) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {

				boolean isAllowModify = true;
				if (this.source.equals(this.RoomPropertySource)) {
					isAllowModify = FDCClientUtils.isAllowModifyNumber(
							new RoomPropertyBookInfo(), org.getId().toString());
				} else {
					isAllowModify = FDCClientUtils.isAllowModifyNumber(
							new RoomJoinInfo(), org.getId().toString());
				}
				if (isAllowModify) {
					row.getCell("number").getStyleAttributes().setLocked(false);
				} else {
					row.getCell("number").getStyleAttributes().setLocked(true);
				}
			}
	}
}