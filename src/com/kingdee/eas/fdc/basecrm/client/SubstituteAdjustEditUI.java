/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustFactory;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.client.CompensateSeletRoomUI;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUIForNewSHE;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseChangeNameUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;

/**
 * output class name
 */
public class SubstituteAdjustEditUI extends AbstractSubstituteAdjustEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SubstituteAdjustEditUI.class);   

    public SubstituteAdjustEditUI() throws Exception
    {
        super();
    }

    public void loadFields()
    {
        super.loadFields();
    }


    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
    	
    	this.actionCopy.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	this.actinoCalculate.setEnabled(true);
    	this.actionTransfTo.setEnabled(true);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.actionSave.setVisible(false);
    	this.actionWorkFlowG.setVisible(false);
    	this.actionAuditResult.setVisible(false);
    	
    	if(this.editData.getTransfAdjustDate()!=null) {
    		this.actionEdit.setEnabled(false);
    	}
    	
    	this.kdtEntrys_detailPanel.setTitle("调整明细");
    	this.kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
    	this.kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
    	this.kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
    	
    	this.kdtEntrys.getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.kdtEntrys.getColumn("newPayAmount").getStyleAttributes().setLocked(false);
    	
    	EntityViewInfo monDefView = new EntityViewInfo();	//代收费用类的
    	FilterInfo monDefFilter = new FilterInfo();
    	monDefFilter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.REPLACEFEE_VALUE));
    	monDefView.setFilter(monDefFilter);    	
    	this.prmtmoneyDefine.setEntityViewInfo(monDefView);
    	
    	
    	SellProjectInfo spInfo = (SellProjectInfo)this.getUIContext().get("SellProjectInfo");
    	BuildingInfo buildInfo = (BuildingInfo)this.getUIContext().get("BuildingInfo");
    	if(spInfo!=null)	this.prmtsellProject.setValue(spInfo);
    	if(buildInfo!=null) this.prmtbuilding.setValue(buildInfo);
    	
    	this.prmtbuilding.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				SellProjectInfo spInfo = (SellProjectInfo)SubstituteAdjustEditUI.this.prmtsellProject.getValue();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",spInfo==null?null:spInfo.getId()));
				view.setFilter(filter);
				SubstituteAdjustEditUI.this.prmtbuilding.setEntityViewInfo(view);
				SubstituteAdjustEditUI.this.prmtbuilding.getQueryAgent().resetRuntimeEntityView();
				SubstituteAdjustEditUI.this.prmtbuilding.setRefresh(true);				
			}
		}); 
    	
    	this.prmtcollectFunction.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				SellProjectInfo spInfo = (SellProjectInfo)SubstituteAdjustEditUI.this.prmtsellProject.getValue();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				if(spInfo==null) {
					filter.getFilterItems().add(new FilterItemInfo("project.id",null));
					filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Boolean(true)));//
				}else{
					filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Boolean(true)));//
					try {
						filter.getFilterItems().add(new FilterItemInfo("project.id",SHEManageHelper.getAllParentSellProjectCollection(spInfo,new HashSet()),CompareType.INCLUDE));
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
				}
				
				view.setFilter(filter);
				SubstituteAdjustEditUI.this.prmtcollectFunction.setEntityViewInfo(view);
				SubstituteAdjustEditUI.this.prmtcollectFunction.getQueryAgent().resetRuntimeEntityView();
				SubstituteAdjustEditUI.this.prmtcollectFunction.setRefresh(true);
			}
		});
    	
    	if(this.getOprtState().equals(OprtState.ADDNEW))
    		this.actionTransfTo.setEnabled(false);
    	
    	
		//非售楼组织不能操作
		if(!FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCalculator.setEnabled(false);
			this.actionTransfTo.setEnabled(false);
			this.btnChooseRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
		}
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		this.kdtEntrys.getColumn("newPayAmount").setEditor(amountEditor);
    }
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionCalculator.setEnabled(false);
			this.actionTransfTo.setEnabled(false);
			this.btnChooseRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionCalculator.setEnabled(true);
			this.actionTransfTo.setEnabled(true);
			this.btnChooseRoom.setEnabled(true);
			this.btnDeleteRoom.setEnabled(true);
		}
	}

    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SubstituteAdjustFactory.getRemoteInstance();
    }


    protected IObjectValue createNewDetailData(KDTable table)
    {		
        return null;
    }


    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        SubstituteAdjustInfo objectValue = new SubstituteAdjustInfo();
		if (SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")) != null && SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
			objectValue.put("saleOrgUnit",SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")));
 
        objectValue.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUserInfo()));
		
        objectValue.setBizDate(new Date());
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        return objectValue;
    }

    
    public void actionCalculate_actionPerformed(ActionEvent e) throws Exception {
    	if(this.kdtEntrys.getRowCount()==0) return;
		if(this.editData.getTransfAdjustDate()!=null){
			FDCMsgBox.showInfo("已经传递，禁止重复计算！");
			return;
		}	
		
    	MoneyDefineInfo moneyDefInfo = (MoneyDefineInfo)this.prmtmoneyDefine.getValue();
    	if(moneyDefInfo==null) {
    		FDCMsgBox.showWarning("款项名称必须录入！");
    		return;
    	}
    	
    	CollectionInfo collInfo = (CollectionInfo)this.prmtcollectFunction.getValue();
    	if(collInfo==null){
    		FDCMsgBox.showWarning("代收费用设置必须录入！");
    		return;    		
    	}
    	
//    	this.kdtEntrys.removeRows();
    	
    	BuildingInfo buidInfo = (BuildingInfo)this.prmtbuilding.getValue();
    	SellProjectInfo spInfo = (SellProjectInfo)this.prmtsellProject.getValue();
    	SubstituteAdjustEntryCollection adjustColl = SubstituteAdjustFactory.getRemoteInstance()
    					.getCalculateResult(moneyDefInfo.getId(),spInfo==null?null:spInfo.getId(), buidInfo==null?null:buidInfo.getId(),collInfo); 
    	for (int i = 0; i < adjustColl.size(); i++) {
    		SubstituteAdjustEntryInfo newEntryInfo = adjustColl.get(i);
    		for(int k=0;k<this.kdtEntrys.getRowCount();k++){
    			IRow addRow=this.kdtEntrys.getRow(k);
				RoomInfo room=(RoomInfo) addRow.getCell("room").getValue();
				if(room!=null&&room.getId().toString().equals(newEntryInfo.getRoom().getId().toString())){
					newEntryInfo.setParent(this.editData);
		    		newEntryInfo.getRelateBizNumber();
		    		newEntryInfo.getContactAmount();
		    		newEntryInfo.getNewPayAmount();
		    		//计算新的代收费用金额
		    		
					addRow.setUserObject(newEntryInfo);
					addRow.getCell("relateBizType").setValue(newEntryInfo.getRelateBizType());
					addRow.getCell("room").setValue(newEntryInfo.getRoom());
					addRow.getCell("customer").setValue(newEntryInfo.getCustomer());
					addRow.getCell("payAmount").setValue(newEntryInfo.getPayAmount());
					addRow.getCell("relateBizNumber").setValue(newEntryInfo.getRelateBizNumber());
					addRow.getCell("relateBizEntryId").setValue(newEntryInfo.getRelateBizEntryId());
					addRow.getCell("building").setValue(newEntryInfo.getBuilding());
					addRow.getCell("relateBizId").setValue(newEntryInfo.getRelateBizId());
					addRow.getCell("bizDate").setValue(newEntryInfo.getBizDate());
					addRow.getCell("contactAmount").setValue(newEntryInfo.getContactAmount());
					addRow.getCell("amount").setValue(newEntryInfo.getNewPayAmount());
					addRow.getCell("newPayAmount").setValue(newEntryInfo.getNewPayAmount());
					String modifyTypeStr = "修改";
					if(newEntryInfo.getRelateBizEntryId()==null) modifyTypeStr = "新增"; 
					addRow.getCell("modifyType").setValue(modifyTypeStr);
					
					break;
				}
    		}
		}

    }
    
    public void actionTransfTo_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null && this.editData.getEntrys()!=null  && this.editData.getEntrys().size()>0) {
    		if(this.editData.getTransfAdjustDate()!=null){
    			FDCMsgBox.showInfo("已经传递，不允许重复传递！");
    			return;
    		}	
    		
    		SubstituteAdjustFactory.getRemoteInstance().transfTo(this.editData);
    		FDCMsgBox.showInfo("传递成功！");
    		this.editData.setTransfAdjustDate(new Date());
    		this.actionRemove.setEnabled(false);
    		this.actionSubmit.setEnabled(false);
    	}
    }
    
    
    protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
    	if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
    			&&!this.kdtEntrys.getColumnKey(e.getColIndex()).equals("newPayAmount")) {
    		IRow curRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
        	String idStr = (String)curRow.getCell("relateBizId").getValue();
        	if(idStr==null) return;    	
        	RelatBizType relatBizType = (RelatBizType)curRow.getCell("relateBizType").getValue();
        	if(relatBizType==null) return;
        	
        	UIContext uiContext = new UIContext(this); 		
    		uiContext.put(UIContext.ID, idStr);
    		String uiName = "";
    		if(relatBizType.equals(RelatBizType.PreOrder))
    			uiName = SincerityPurchaseChangeNameUI.class.getName();
    		else if(relatBizType.equals(RelatBizType.PrePur))
    			uiName = PrePurchaseManageEditUI.class.getName();
    		else if(relatBizType.equals(RelatBizType.Purchase))
    			uiName = PurchaseManageEditUI.class.getName();
    		else if(relatBizType.equals(RelatBizType.Sign))
    			uiName = SignManageEditUI.class.getName();
    		if(uiName.equals("")) return;
    		
    		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.VIEW);
    		uiWindow.show();
    	}
    }
    
   
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getTransfAdjustDate()!=null) {
    		FDCMsgBox.showWarning("单据已经传递，禁止修改！");
    		return;
    	}

    	super.actionEdit_actionPerformed(e);
    }
    
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtNumber.isEditable()  &&  this.txtNumber.isEnabled()  &&  StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showInfo(this, "编码不能为空！");
			return;
		}
		
		if(this.pkBizDate.getValue()==null){
			FDCMsgBox.showInfo(this, "业务日期不能为空！");
			return;
		}
		if(this.prmtmoneyDefine.getValue()==null){
			FDCMsgBox.showInfo(this, "款项名称不能为空！");
			return;
		}
		if(this.prmtcollectFunction.getValue()==null){
			FDCMsgBox.showInfo(this, "代收费用设置不能为空！");
			return;
		}		
		if(this.kdtEntrys.getRowCount()==0){
			FDCMsgBox.showInfo(this, "调整明细列表不能为空！");
			return;
		}	
		for(int k=0;k<this.kdtEntrys.getRowCount();k++){
			IRow addRow=this.kdtEntrys.getRow(k);
			BigDecimal amount=(BigDecimal) addRow.getCell("newPayAmount").getValue();
			if(amount==null||amount.compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"代收费用金额必须大于0！");
				this.kdtEntrys.getEditManager().editCellAt(k, this.kdtEntrys.getColumnIndex("newPayAmount"));
				SysUtil.abort();
			}
		}
    	
    	super.actionSubmit_actionPerformed(e);
    	this.chkMenuItemSubmitAndPrint.setSelected(false);
    	this.actionTransfTo.setEnabled(true);
    }
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selectors = super.getSelectors();
    	selectors.add("CU.*");
    	return selectors;
    }
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
    protected void btnChooseRoom_actionPerformed(ActionEvent e)throws Exception {
    	BuildingInfo build=(BuildingInfo) this.prmtbuilding.getValue();
    	SellProjectInfo sellProject=(SellProjectInfo) this.prmtsellProject.getValue();
    	RoomCollection rooms = RoomSelectUIForNewSHE.showMultiRoomSelectUI(this, build, null, MoneySysTypeEnum.SalehouseSys,null,sellProject);
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			String roomId=room.getId().toString();
			boolean isAdd=true;
			for(int k=0;k<this.kdtEntrys.getRowCount();k++){
				IRow row=this.kdtEntrys.getRow(k);
				RoomInfo rowRoom=(RoomInfo) row.getCell("room").getValue();
				if(rowRoom!=null&&rowRoom.getId().toString().equals(roomId)){
					isAdd=false;
					break;
				}
			}
			if(isAdd){
				IRow addRow = this.kdtEntrys.addRow();
				SubstituteAdjustEntryInfo entry=new SubstituteAdjustEntryInfo();
				addRow.setUserObject(entry);
				addRow.getCell("room").setValue(RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId)));
			}
		}
    }
    protected void btnDeleteRoom_actionPerformed(ActionEvent e) throws Exception {
		if ((this.kdtEntrys.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = this.kdtEntrys.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (this.kdtEntrys.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				this.kdtEntrys.removeRow(rowIndex);
			}
			if (this.kdtEntrys.getRow(0) != null){
				this.kdtEntrys.getSelectManager().select(0, 0);
			}
		}
	}

}