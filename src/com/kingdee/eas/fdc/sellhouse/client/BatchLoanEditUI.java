/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.IAFMortgaged;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.IRoomLoan;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BatchLoanEditUI extends AbstractBatchLoanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BatchLoanEditUI.class);
    
    /**
     * output class constructor
     */
    public BatchLoanEditUI() throws Exception
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
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
//        super.kDTable1_editStopped(e);
    }

    String filterStr = "";
    String state = "";
    String filterAFID = "";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	Map roomIDmap = new HashMap();
    	
    	kDTable1.checkParsed();
		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		String rooIDList = null;
		for (int i = 0, size = roomIds.size(); i < size; i++) {
			String id = roomIds.get(i).toString();
			if (rooIDList == null)
				rooIDList = "" + "'" + id + "'";
			else
				rooIDList = rooIDList + ",'" + id + "'";
		}
		state = getUIContext().get("state").toString();
		String mmType ="AccFundAmount";
		if(getUIContext().get("mmType")!=null){
			if("按揭款".equals(getUIContext().get("mmType").toString())){
				mmType = "LoanAmount";
			}
			filterStr = " and (MonDefine.fmoneyType ='"+mmType+"') ";
		}
		String filterStr1 = "";
		if("done".equals(state)){
			List roomLoanIds = (List)getUIContext().get("roomLoanIds");
			String roomLoanList = null;
			for (int i = 0, size = roomLoanIds.size(); i < size; i++) {
				String id = roomLoanIds.get(i).toString();
				if (roomLoanList == null)
					roomLoanList = "" + "'" + id + "'";
				else
					roomLoanList = roomLoanList + ",'" + id + "'";
			}
			filterStr1 = "and RoomLoan.FID IN (" + roomLoanList + " ) ";
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select distinct Room.FID as roomID,Room.FNumber as roomNumber, Pur.FID as purchaseId, MonDefine.fid as mmTypeId, " +
				"Pur.FToSignDate as toSignDate, SC.FContractNumber as contractNumber, SC.FOnRecordDate as onRecordsDate, Pur.FDealAmount as dealAmount, " +
				"roomloan.FNumber as loanNumber, roomloan.FLoanFixedYear as loanFixedYear, roomloan.FActualLoanAmt as actualLoanAmt, " +
				"roomloan.FLoanLawyer as loanLawyer, roomloan.FLoanCompactNumber as loanCompactNumber, u.FID as transactorId, u.FName_l2 as transactorName,u.FNumber as transactorNumber, roomloan.FID as roomLoanId "+
				",Pur.faccumulationFundAmount as accumulationFundAmount,Pur.floanAmount as loanAmount,MonDefine.fmoneyType as moneyType "+
				" from T_SHE_PurchasePayListEntry PayList "+
				"left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid "+
				"left outer join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID "+
				"left outer join T_SHE_Room Room on Room.FID = Pur.FRoomID "+
				"left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid " +
				"left outer join T_PM_User as u on roomloan.FTransactorID = u.fid "+
//				" left outer join t_pm_user as u on pur.fsalesmanid = u.fid " +
//				"left join T_SHE_Building Build on Build.FID = Room.FBuildingID "+
//				"left join T_SHE_SellProject Sell on Sell.FID = Build.FSellProjectID "+
//				"left join T_SHE_Subarea Sb on Sb.FID = Build.FSubareaID "+
//				"left join T_SHE_BuildingUnit BuilUnit on BuilUnit.FID=Room.FBuildUnitID "+
//				"left join T_SHE_PurchaseCustomerInfo PC on PC.FHeadID =Pur.FID "+
//				"left join T_SHE_FDCCustomer FC on FC.FID=PC.FCustomerID "+
//				"left join T_ORG_BaseUnit Bu on Bu.FID=Pur.FOrgUnitID "+
				"left outer join T_SHE_RoomSignContract SC on SC.FPurchaseID =Pur.FID "+
				"where (Pur.FPurchaseState ='PurchaseAudit' or Pur.FPurchaseState = 'PurchaseChange') " +
				filterStr + filterStr1 +
				" and Room.FID IN (" + rooIDList + ")");
		IRowSet rowSet = builder.executeQuery();
		kDTable1.checkParsed();
		while(rowSet.next()){
			
			/**
			 * 因为关联查找出来的数据是多条的，所以必须去掉重复的数据 
			 * by renliang at 2010-11-19
			 */
			if(roomIDmap.containsKey(rowSet.getString("roomID"))){
				continue;
			}
			roomIDmap.put(rowSet.getString("roomID"), rowSet.getString("roomID"));
			
			IRow row = kDTable1.addRow();
			row.getCell("id").setValue(rowSet.getString("roomID"));
			row.getCell("roomNumber").setValue(rowSet.getString("roomNumber"));
//			RoomInfo room = new RoomInfo();
//			room.setId(BOSUuid.read(rowSet.getString("roomID")));
//			room.setNumber(rowSet.getString("roomNumber"));
//			row.getCell("roomNumber").setValue(room);
//			row.getCell("roomNumber").setUserObject(room);
			
//			row.getCell("toSignDate").setValue(rowSet.getString("toSignDate"));
			if(rowSet.getDate("toSignDate")!=null){
				row.getCell("toSignDate").setValue(format.format(rowSet.getDate("toSignDate")));
			}else{
				row.getCell("toSignDate").setValue(rowSet.getString("toSignDate"));
			}
			row.getCell("contractNumber").setValue(rowSet.getString("contractNumber"));
//			row.getCell("onRecordsDate").setValue(rowSet.getString("onRecordsDate"));
			if(rowSet.getDate("onRecordsDate")!=null){
				row.getCell("onRecordsDate").setValue(format.format(rowSet.getDate("onRecordsDate")));
			}else{
				row.getCell("onRecordsDate").setValue(rowSet.getString("onRecordsDate"));
			}
			row.getCell("dealAmount").setValue(rowSet.getString("dealAmount"));
			row.getCell("purchaseId").setValue(rowSet.getString("purchaseId"));
			row.getCell("mmTypeId").setValue(rowSet.getString("mmTypeId"));
			if("done".equals(state)){
				row.getCell("number").setValue(rowSet.getString("loanNumber"));
				row.getCell("loanFixedYear").setValue(rowSet.getString("loanFixedYear"));
				row.getCell("actualLoanAmt").setValue(rowSet.getString("actualLoanAmt"));
				row.getCell("loanLawyer").setValue(rowSet.getString("loanLawyer"));
				row.getCell("loanCompactNumber").setValue(rowSet.getString("loanCompactNumber"));
				UserInfo userInfo = new UserInfo();
				if(rowSet.getString("transactorId") != null){
					userInfo.setId(BOSUuid.read(rowSet.getString("transactorId")));
					userInfo.setName(rowSet.getString("transactorName"));
					userInfo.setNumber(rowSet.getString("transactorNumber"));
				}			
				row.getCell("transactor").setValue(userInfo);
				row.getCell("transactor").setUserObject(userInfo);
				row.getCell("roomLoanId").setValue(rowSet.getString("roomLoanId"));
				row.getCell("mmTypeId").setValue(rowSet.getString("mmTypeId"));
			}else{
				row.getCell("number").setValue(null);
				row.getCell("loanFixedYear").setValue(null);
				row.getCell("actualLoanAmt").setValue(null);
				row.getCell("loanLawyer").setValue(null);
				row.getCell("loanCompactNumber").setValue(null);		
				row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("transactor").setUserObject(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("roomLoanId").setValue(null);
			}
			
			if(rowSet.getString("moneyType").equals("LoanAmount")){
				if(rowSet.getString("loanAmount")!=null){
					row.getCell("actualLoanAmt").setValue(rowSet.getString("loanAmount"));
				}
			}else{
				if(rowSet.getString("accumulationFundAmount")!=null){
					row.getCell("actualLoanAmt").setValue(rowSet.getString("accumulationFundAmount"));
				}
			}
		}
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionPrint,
				actionPrintPreview,actionEdit,actionRemove }, false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);
		
//    	kDTable1.getColumn("roomNumber").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("toSignDate").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("contractNumber").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("onRecordsDate").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("dealAmount").getStyleAttributes().setLocked(true);
    	kDTable1.getColumn("dealAmount").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
//    	kDTable1.getColumn("dealAmount")
    	kDTable1.getColumn("loanFixedYear").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
    	kDTable1.getColumn("actualLoanAmt").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
    	
    	KDTextField txtNumber = new KDTextField(KDFormattedTextField.BIGINTEGER_TYPE);
    	txtNumber.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtNumber.setMaxLength(80);
    	kDTable1.getColumn("number").setEditor(new KDTDefaultCellEditor(txtNumber));
    	
    	KDTextField txtLoanFixedYear = new KDTextField(KDFormattedTextField.BIGINTEGER_TYPE);
    	txtLoanFixedYear.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtLoanFixedYear.setMaxLength(4);
    	kDTable1.getColumn("loanFixedYear").setEditor(new KDTDefaultCellEditor(txtLoanFixedYear));
    	
    	KDTextField txtActualLoanAmt = new KDTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    	txtActualLoanAmt.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtActualLoanAmt.setMaxLength(13);
    	kDTable1.getColumn("actualLoanAmt").setEditor(new KDTDefaultCellEditor(txtActualLoanAmt));
    	
    	KDTextField txtLoanLawyer = new KDTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    	txtLoanLawyer.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtLoanLawyer.setMaxLength(80);
    	kDTable1.getColumn("loanLawyer").setEditor(new KDTDefaultCellEditor(txtLoanLawyer));
    	
    	KDTextField txtLoanCompactNumber = new KDTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    	txtLoanCompactNumber.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtLoanCompactNumber.setMaxLength(80);
    	kDTable1.getColumn("loanCompactNumber").setEditor(new KDTDefaultCellEditor(txtLoanCompactNumber));
    	
    	kDTable1.getColumn("number").setRequired(true);
    	kDTable1.getColumn("transactor").setRequired(true);
    	
    	
//    	Set allRoomIds = new HashSet();
//    	if("toDo".equals(state)){
//    		allRoomIds = (Set)getUIContext().get("roomLoanData");
//    	}else{
//	    	RoomLoanCollection rlc = (RoomLoanCollection)getUIContext().get("roomLoanData");
//	    	for(int i=0; i<rlc.size(); i++){
//	    		RoomLoanInfo loan = rlc.get(i);
//	    		if(loan.getRoom()!=null){
//	    			allRoomIds.add(loan.getRoom().getId().toString());
//	    		}
//	    	}
//    	}
    	
    	String sellProject = getUIContext().get("sellProject").toString();
    	final KDBizPromptBox bizRoomBox = new KDBizPromptBox();
    	bizRoomBox.setDisplayFormat("$name$");
    	bizRoomBox.setEditFormat("$number$");
    	bizRoomBox.setCommitFormat("$name$");
    	bizRoomBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("id",allRoomIds,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("building.sellProject.name",sellProject));
    	if("done".equals(state)){
    		Set allRoomIds = new HashSet();
    		String AFMortgagedId = getUIContext().get("AFMortgagedId").toString();
    		filterAFID = " and AF.FID = ('" + AFMortgagedId.toString() + "') ";
    		builder.clear();
    		builder.appendSql("select distinct Room.FID as roomID "+
					"from T_SHE_PurchasePayListEntry PayList "+
					"left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid "+
					"left outer join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID "+
					"left outer join T_SHE_Room Room on Room.FID = Pur.FRoomID "+
					"left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid " +
					" left outer join t_she_AFMortgaged AF on roomloan.FORSOMortgagedID = AF.FID "+
					"where (roomloan.FAFMortgagedState =1 or roomloan.FAFMortgagedState = 3) " +
					filterStr + 
//					" and (roomloan.FAFMortgagedState = 1 or roomloan.FAFMortgagedState = 3)  " +
					"and AF.FID = ('" + AFMortgagedId.toString() + "')" );
    		IRowSet rows = builder.executeQuery();
    		while(rows.next()){
    			String roomid = rows.getString("roomID");
    			allRoomIds.add(roomid);
    		}
    		filter.getFilterItems().add(new FilterItemInfo("id",allRoomIds,CompareType.INCLUDE));
    	}else{
//    		Set allRoomIds = new HashSet();
//    		String roomIDList = null;
//    		List roomsId = (List)getUIContext().get("roomLoanData");
//    		for (int i = 0; i <roomsId.size() ; i++) {
//    			String id = roomsId.get(i).toString();
//    			if (roomIDList == null)
//    				roomIDList = "" + "'" + id + "'";
//    			else
//    				roomIDList = roomIDList + ",'" + id + "'";
//    		}
//    		builder.clear();
//    		builder.appendSql("select Room.FID as roomID "+
//					"from T_SHE_PurchasePayListEntry PayList "+
//					"left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid "+
//					"left join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID "+
//					"left join T_SHE_Room Room on Room.FID = Pur.FRoomID "+
//					"left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid "+
//					"where (Pur.FPurchaseState ='PurchaseAudit' or Pur.FPurchaseState = 'PurchaseChange') " +
////					" and (roomloan.FAFMortgagedState = '0') "+
////					" and (roomloan.FAFMortgagedState not in ('1','3') or roomloan.FAFMortgagedState is null )"+
//					filterStr +
//					"and Room.FID IN (" + roomIDList + ")");
//    		IRowSet rows = builder.executeQuery();
//    		while(rows.next()){
//    			String roomid = rows.getString("roomID");
//    			allRoomIds.add(roomid);
//    		}
    		Set allRoomIds = (Set)getUIContext().get("roomsID");
    		filter.getFilterItems().add(new FilterItemInfo("id",allRoomIds,CompareType.INCLUDE));
    	}
    	view.setFilter(filter);
    	bizRoomBox.setEntityViewInfo(view);
    	this.kDTable1.getColumn("roomNumber").setEditor(new KDTDefaultCellEditor(bizRoomBox));
    	bizRoomBox.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj!=null && eventObj.getNewValue()!=null && eventObj.getNewValue() instanceof RoomInfo){
					if(eventObj.getNewValue()!=null && eventObj.getOldValue()!=null && eventObj.getNewValue().toString().equals(eventObj.getOldValue().toString())){
						return;
					}
					RoomInfo room = (RoomInfo)eventObj.getNewValue();
//					PurchaseInfo purInfo = room.getLastPurchase();
					if(room.getLastPurchase()!=null){
					String purchaseID=room.getLastPurchase().getId().toString();
					PurchaseInfo purInfo=null;
					try {
						purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purchaseID));
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
					
					if(purInfo!=null){
						if(!purInfo.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit)){
							MsgBox.showWarning("认购单未审批，无法新增按揭！");
//							abort();
							if(eventObj.getOldValue()!=null){
//								RoomInfo oldRoom;
								try {
//									oldRoom = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(kDTable1.getRow(selectedRow).getCell("id").getValue().toString()));
									bizRoomBox.setValue(eventObj.getOldValue().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								bizRoomBox.setValue(null);
							}
							return;
						}
					}
					}
//					int selectedRow = kDTable1.getSelectManager().getActiveRowIndex();
					for(int i=0; i<kDTable1.getRowCount(); i++){
						if(kDTable1.getRow(i).getCell("roomNumber").getValue() == null) continue;
						if( room.getNumber().toString().equals(kDTable1.getRow(i).getCell("roomNumber").getValue().toString())){
							MsgBox.showInfo("请不要重复添加房间!");
							if(eventObj.getOldValue()!=null){
//								RoomInfo oldRoom;
								try {
//									oldRoom = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(kDTable1.getRow(selectedRow).getCell("id").getValue().toString()));
									bizRoomBox.setValue(eventObj.getOldValue().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								bizRoomBox.setValue(null);
							}
							return;
						}
					}
					int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.clear();
					builder.appendSql("select distinct Room.FID as roomID,Room.FNumber as roomNumber, Pur.FID as purchaseId, MonDefine.fid as mmTypeId, " +
							"Pur.FToSignDate as toSignDate, SC.FContractNumber as contractNumber, SC.FOnRecordDate as onRecordsDate, Pur.FDealAmount as dealAmount, " +
							"roomloan.FNumber as loanNumber, roomloan.FLoanFixedYear as loanFixedYear, roomloan.FActualLoanAmt as actualLoanAmt, " +
							"roomloan.FLoanLawyer as loanLawyer, roomloan.FLoanCompactNumber as loanCompactNumber, u.FID as transactorId, u.FName_l2 as transactorName,u.FNumber as transactorNumber, roomloan.FID as roomLoanId "+
							"from T_SHE_PurchasePayListEntry PayList "+
							"left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid "+
							"left outer join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID "+
							"left outer join T_SHE_Room Room on Room.FID = Pur.FRoomID "+
							"left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid " +
							"left outer join T_PM_User as u on roomloan.FTransactorID = u.fid "+
							" left outer join t_she_AFMortgaged AF on roomloan.FORSOMortgagedID = AF.FID "+
//							" left outer join t_pm_user as u on pur.fsalesmanid = u.fid " +
//							"left join T_SHE_Building Build on Build.FID = Room.FBuildingID "+
//							"left join T_SHE_SellProject Sell on Sell.FID = Build.FSellProjectID "+
//							"left join T_SHE_Subarea Sb on Sb.FID = Build.FSubareaID "+
//							"left join T_SHE_BuildingUnit BuilUnit on BuilUnit.FID=Room.FBuildUnitID "+
//							"left join T_SHE_PurchaseCustomerInfo PC on PC.FHeadID =Pur.FID "+
//							"left join T_SHE_FDCCustomer FC on FC.FID=PC.FCustomerID "+
//							"left join T_ORG_BaseUnit Bu on Bu.FID=Pur.FOrgUnitID "+
							"left outer join T_SHE_RoomSignContract SC on SC.FPurchaseID =Pur.FID "+
							"where (Pur.FPurchaseState ='PurchaseAudit' or Pur.FPurchaseState = 'PurchaseChange') " +
//							"and (MonDefine.fmoneyType ='LoanAmount' or MonDefine.fmoneyType='AccFundAmount') " +
							filterStr + filterAFID +
							"and Room.FID = ('" + room.getId().toString() + "')");
					try {
						IRowSet rowSet = builder.executeQuery();
						kDTable1.checkParsed();
						while(rowSet.next()){
							kDTable1.getRow(rowIndex).getCell("id").setValue(rowSet.getString("roomID"));
							kDTable1.getRow(rowIndex).getCell("roomNumber").setValue(rowSet.getString("roomNumber"));
//							kDTable1.getRow(rowIndex).getCell("toSignDate").setValue(rowSet.getString("toSignDate"));
							if(rowSet.getDate("toSignDate")!=null){
								kDTable1.getRow(rowIndex).getCell("toSignDate").setValue(format.format(rowSet.getDate("toSignDate")));
							}else{
								kDTable1.getRow(rowIndex).getCell("toSignDate").setValue(rowSet.getString("toSignDate"));
							}
							kDTable1.getRow(rowIndex).getCell("contractNumber").setValue(rowSet.getString("contractNumber"));
//							kDTable1.getRow(rowIndex).getCell("onRecordsDate").setValue(rowSet.getString("onRecordsDate"));
							if(rowSet.getDate("onRecordsDate")!=null){
							kDTable1.getRow(rowIndex).getCell("onRecordsDate").setValue(format.format(rowSet.getDate("onRecordsDate")));
							}else{
								kDTable1.getRow(rowIndex).getCell("onRecordsDate").setValue(rowSet.getString("onRecordsDate"));
							}
							kDTable1.getRow(rowIndex).getCell("dealAmount").setValue(rowSet.getString("dealAmount"));
							kDTable1.getRow(rowIndex).getCell("purchaseId").setValue(rowSet.getString("purchaseId"));
							kDTable1.getRow(rowIndex).getCell("mmTypeId").setValue(rowSet.getString("mmTypeId"));
							if("done".equals(state)){
								kDTable1.getRow(rowIndex).getCell("number").setValue(rowSet.getString("loanNumber"));
								kDTable1.getRow(rowIndex).getCell("loanFixedYear").setValue(rowSet.getString("loanFixedYear"));
								kDTable1.getRow(rowIndex).getCell("actualLoanAmt").setValue(rowSet.getString("actualLoanAmt"));
								kDTable1.getRow(rowIndex).getCell("loanLawyer").setValue(rowSet.getString("loanLawyer"));
								kDTable1.getRow(rowIndex).getCell("loanCompactNumber").setValue(rowSet.getString("loanCompactNumber"));
								UserInfo userInfo = new UserInfo();
								if(rowSet.getString("transactorId") != null){
									userInfo.setId(BOSUuid.read(rowSet.getString("transactorId")));
									userInfo.setName(rowSet.getString("transactorName"));
									userInfo.setNumber(rowSet.getString("transactorNumber"));
								}
								kDTable1.getRow(rowIndex).getCell("transactor").setValue(userInfo);
								kDTable1.getRow(rowIndex).getCell("transactor").setUserObject(userInfo);
								kDTable1.getRow(rowIndex).getCell("roomLoanId").setValue(rowSet.getString("roomLoanId"));
							}else{
								kDTable1.getRow(rowIndex).getCell("number").setValue(null);
								kDTable1.getRow(rowIndex).getCell("loanFixedYear").setValue(null);
								kDTable1.getRow(rowIndex).getCell("actualLoanAmt").setValue(null);
								kDTable1.getRow(rowIndex).getCell("loanLawyer").setValue(null);
								kDTable1.getRow(rowIndex).getCell("loanCompactNumber").setValue(null);
								kDTable1.getRow(rowIndex).getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
								kDTable1.getRow(rowIndex).getCell("transactor").setUserObject(SysContext.getSysContext().getCurrentUserInfo());
								kDTable1.getRow(rowIndex).getCell("roomLoanId").setValue(null);
							}
						}
						String roomLoanId = kDTable1.getRow(rowIndex).getCell("roomLoanId").getValue().toString();
						IRoomLoan roomLoan = (IRoomLoan)getBizInterface();
						RoomLoanInfo roomLoanInfo = roomLoan.getRoomLoanInfo(new ObjectUuidPK(roomLoanId));
						List list = getBatchRoomLoan();
						Boolean finish = Boolean.TRUE;
						for(int i = 0 ; i<roomLoanInfo.getAFMortgaged().size();i++){
							RoomLoanAFMEntrysInfo rleInfo=roomLoanInfo.getAFMortgaged().get(i);
							if(rleInfo.isIsAOrB()){
								kDTable2.getRow(i).getCell("id").setValue(rleInfo.getId());
								kDTable2.getRow(i).getCell("aDate").setValue(new Date());
//								row.getCell("aDate").getStyleAttributes().setLocked(true);
								for(int j=0; j<list.size(); j++){
									RoomLoanInfo loanInfo = (RoomLoanInfo)list.get(j);
									if(!loanInfo.getAFMortgaged().get(i).isIsFinish()){ 
										finish = Boolean.FALSE;
									}
//									else{
//										finish = Boolean.TRUE;
//									}
								}
								kDTable2.getRow(i).getCell("aFinish").setValue(finish);
								if(finish.booleanValue()){
									kDTable2.getRow(i).getStyleAttributes().setLocked(finish.booleanValue());
									kDTable2.getRow(i).getStyleAttributes().setBackground(Color.gray);
									kDTable2.getRow(i).getCell("aDate").setValue(null);
								}else{
									kDTable2.getRow(i).getStyleAttributes().setLocked(finish.booleanValue());
									kDTable2.getRow(i).getStyleAttributes().setBackground(Color.white);
								}
								kDTable2.getRow(i).getCell("aApproach").setValue(rleInfo.getApproach());
								kDTable2.getRow(i).getCell("aRemark").setValue(rleInfo.getRemark());
								kDTable2.getRow(i).getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
							}else{
								if(!getUIContext().containsKey("isEditData")){
								finish = Boolean.TRUE;
								kDTable3.getRow(i).getCell("id").setValue(rleInfo.getId());
								kDTable3.getRow(i).getCell("bDate").setValue(new Date());
//								row.getCell("bDate").getStyleAttributes().setLocked(true);
								for(int j=0; j<list.size(); j++){
									RoomLoanInfo loanInfo = (RoomLoanInfo)list.get(j);
									if(loanInfo.getAFMortgaged()!=null && loanInfo.getAFMortgaged().get(i)!=null && !loanInfo.getAFMortgaged().get(i).isIsFinish()){
										finish = Boolean.FALSE;
									}
//									else{
//										finish = Boolean.TRUE;
//									}
								}
								kDTable3.getRow(i).getCell("bFinish").setValue(finish);
								if(finish.booleanValue()){
									kDTable3.getRow(i).getStyleAttributes().setLocked(finish.booleanValue());
									kDTable3.getRow(i).getStyleAttributes().setBackground(Color.GRAY);
									kDTable3.getRow(i).getCell("bDate").setValue(null);
								}else{
									kDTable3.getRow(i).getStyleAttributes().setBackground(Color.white);
								}
								kDTable3.getRow(i).getCell("bApproach").setValue(rleInfo.getApproach());
								kDTable3.getRow(i).getCell("bRemark").setValue(rleInfo.getRemark());
								kDTable3.getRow(i).getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
								
								}
							}
						}
						if(getUIContext().containsKey("isEditData") && ((Boolean)getUIContext().get("isEditData")).booleanValue()){
//							for(int j = 0;j<afmInfo.getDataEntrys().size();j++){
//								AFMortgagedDataEntryInfo ade=afmInfo.getDataEntrys().get(j);
//								IRow row= kDTable3.addRow();
//								row.getCell("bDate").setValue(new Date());
//								row.getCell("bFinish").setValue(Boolean.FALSE);
//								row.getCell("bApproach").setValue(ade.getName());
//								row.getCell("bRemark").setValue(ade.getRemark());
//								row.getCell("aOrb").setValue(Boolean.FALSE);
//							}
//							kDTable3.getStyleAttributes().setLocked(true);
						}

					} catch (BOSException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}
    	});
    	
//    	FDCHelper.formatTableDate(kDTable1, "toSignDate");
//    	FDCHelper.formatTableDate(kDTable1, "onRecordsDate");
		
    	KDBizPromptBox bizTransactorBox = new KDBizPromptBox();
    	bizTransactorBox.setDisplayFormat("$name$");
    	bizTransactorBox.setEditFormat("$number$");
    	bizTransactorBox.setCommitFormat("$name$");
    	bizTransactorBox.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
    	this.kDTable1.getColumn("transactor").setEditor(new KDTDefaultCellEditor(bizTransactorBox));
    	
    	ObjectValueRender ovr = new ObjectValueRender();
    	ovr.setFormat(new BizDataFormat("$name$"));
    	this.kDTable1.getColumn("transactor").setRenderer(ovr);
    	
    	
		kDTable1.getColumn("loanFixedYear").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		
		getAFMortgaged();
		initEntry();
		
		if("done".equals(getUIContext().get("state").toString())){
			setCheckBox();
		}
		
		if(getUIContext().containsKey("isEditData") && ((Boolean)getUIContext().get("isEditData")).booleanValue()){
			this.chbFinish.setVisible(false);
		}
	}
    
    public void setCheckBox(){
    	boolean finish = true;
    	if(getUIContext().containsKey("steps")){
    		List steps = (List)getUIContext().get("steps");
    		for(int i=0; i<steps.size(); i++){
    			String approach = "";
    			if(steps.get(i)!=null){
    				approach = steps.get(i).toString();
    			}
    			if(!"全部完成".equals(approach)){
    				finish = false;
    			}
    		}
    	}
    	if(finish){
    		chbFinish.setSelected(finish);
    	}
    }
//    public void setCheckBox(){
//    	boolean finish = true;
//    	for(int i=0; i<kDTable2.getRowCount(); i++){
//    		IRow row = kDTable2.getRow(i);
//    		if(!((Boolean)row.getCell("aFinish").getValue()).booleanValue()){
//    			finish = false;
//    		}
//    	}
//    	for(int i=0; i<kDTable3.getRowCount(); i++){
//    		IRow row = kDTable3.getRow(i);
//    		if(!((Boolean)row.getCell("bFinish").getValue()).booleanValue()){
//    			finish = false;
//    		}
//    	}
//    	if(getUIContext().containsKey("mStates")){
//    		List mStates = (List)getUIContext().get("mStates");
//    		for(int i=0; i<mStates.size(); i++){
//    			String mState = mStates.get(i).toString();
//    			if(!"办理完成".equals(mState)){
//    				finish = false;
//    			}
//    		}
//    	}
//    	if(finish){
//    		chbFinish.setSelected(finish);
//    	}
//    }
    
    public void onShow() throws Exception {
		super.onShow();

    }
    
//    protected RoomLoanCollection getAllLoanData() throws BOSException{
//    	EntityViewInfo view = new EntityViewInfo();
//    	view.setFilter(new FilterInfo());
//    	SelectorItemCollection selector = new SelectorItemCollection();
//    	selector.add("room.id");
//    	view.setSelector(selector);
//    	RoomLoanCollection loanConllection = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
//    	return loanConllection;
//    }
    
    private AFMortgagedInfo afmInfo = null;
    public AFMortgagedInfo getAFMortgaged() throws BOSException, EASBizException {
//		List roomIds= (List)getUIContext().get("roomIds");
//		String roomID = roomIds.get(0).toString();
//		IRoom ir=RoomFactory.getRemoteInstance();
//		SelectorItemCollection roomSic=new SelectorItemCollection();
//		roomSic.add("id");
//		roomSic.add("name");
//		roomSic.add("number");
//		roomSic.add("building.id");
//		roomSic.add("building.name");
//		roomSic.add("building.number");
//		roomSic.add("building.sellProject.id");
//		roomSic.add("building.sellProject.name");
//		roomSic.add("building.sellProject.number");
//		RoomInfo room=ir.getRoomInfo(new ObjectUuidPK(roomID),roomSic);
//		SellProjectInfo spInfo= room.getBuilding().getSellProject();
//		EntityViewInfo view=new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("project",spInfo.getId()));
//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
//		
//		if(getUIContext().containsKey("roomLoan.id")){
//			String biz= getUIContext().get("roomLoan.id").toString();
//			MoneyDefineInfo moneyInfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
//			if(moneyInfo.getMoneyType().getValue().equals(AFMmmTypeEnum.LOANAMOUNT_VALUE)){
//				filter.getFilterItems().add(new FilterItemInfo("mmType",AFMmmTypeEnum.LOANAMOUNT_VALUE));
//			}else{
//				filter.getFilterItems().add(new FilterItemInfo("mmType",AFMmmTypeEnum.ACCFUNDAMOUNT_VALUE));
//			}
//		}
//		
//		SelectorItemCollection sic=new SelectorItemCollection();
//		sic.add("id");
//		sic.add("name");
//		sic.add("number");
//		sic.add("isEnabled");
//		sic.add("ApproachEntrys.id");
//		sic.add("ApproachEntrys.name");
//		sic.add("ApproachEntrys.number");
//		sic.add("ApproachEntrys.remark");
//		sic.add("DataEntrys.id");
//		sic.add("DataEntrys.name");
//		sic.add("DataEntrys.number");
//		sic.add("DataEntrys.remark");
//		view.setFilter(filter);
//		view.setSelector(sic);
//		IAFMortgaged iafm=AFMortgagedFactory.getRemoteInstance();
//		afmInfo=iafm.getAFMortgagedCollection(view).get(0);
    	if(getUIContext().containsKey("AFMortgagedId")){
	    	String AFMortgagedId = getUIContext().get("AFMortgagedId").toString();
	    	IAFMortgaged iafm=AFMortgagedFactory.getRemoteInstance();
			afmInfo=iafm.getAFMortgagedInfo(new ObjectUuidPK(AFMortgagedId));
	    	
    	}
    	return afmInfo;
    }
    
    public void initEntry() throws Exception{
		kDTable2.checkParsed();
		kDTable2.removeRows(false);
		kDTable3.checkParsed();
		kDTable3.removeRows(false);
		kDTable2.getColumn("aApproach").getStyleAttributes().setLocked(true);
		kDTable3.getColumn("bApproach").getStyleAttributes().setLocked(true);
		String state = getUIContext().get("state").toString();
		if("toDo".equals(state)){
			List roomIds= (List)getUIContext().get("roomIds");
			String roomID = roomIds.get(0).toString();
			IRoom ir=RoomFactory.getRemoteInstance();
			SelectorItemCollection roomSic=new SelectorItemCollection();
			roomSic.add("id");
			roomSic.add("name");
			roomSic.add("number");
			roomSic.add("building.id");
			roomSic.add("building.name");
			roomSic.add("building.number");
			roomSic.add("building.sellProject.id");
			roomSic.add("building.sellProject.name");
			roomSic.add("building.sellProject.number");
			RoomInfo room=ir.getRoomInfo(new ObjectUuidPK(roomID),roomSic);
			SellProjectInfo spInfo= room.getBuilding().getSellProject();
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project",spInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			
			if(getUIContext().containsKey("roomLoan.id")){
				String biz= getUIContext().get("roomLoan.id").toString();
				MoneyDefineInfo moneyInfo=MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
				if(moneyInfo.getMoneyType().getValue().equals(AFMmmTypeEnum.LOANAMOUNT_VALUE)){
					filter.getFilterItems().add(new FilterItemInfo("mmType",AFMmmTypeEnum.LOANAMOUNT_VALUE));
				}else{
					filter.getFilterItems().add(new FilterItemInfo("mmType",AFMmmTypeEnum.ACCFUNDAMOUNT_VALUE));
				}
			}
			
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			sic.add("isEnabled");
			sic.add("ApproachEntrys.id");
			sic.add("ApproachEntrys.name");
			sic.add("ApproachEntrys.number");
			sic.add("ApproachEntrys.remark");
			sic.add("DataEntrys.id");
			sic.add("DataEntrys.name");
			sic.add("DataEntrys.number");
			sic.add("DataEntrys.remark");
			view.setFilter(filter);
			view.setSelector(sic);
			try
			{
				IAFMortgaged iafm=AFMortgagedFactory.getRemoteInstance();
				afmInfo=iafm.getAFMortgagedCollection(view).get(0);

				if(afmInfo!=null){
					for(int i=0;i<afmInfo.getApproachEntrys().size();i++){
						AFMortgagedApproachEntryInfo aae=afmInfo.getApproachEntrys().get(i);
						IRow row= kDTable2.addRow();
						row.getCell("aDate").setValue(new Date());
						row.getCell("aFinish").setValue(Boolean.FALSE);
						row.getCell("aApproach").setValue(aae.getName());
						row.getCell("aRemark").setValue(aae.getRemark());
						row.getCell("aOrb").setValue(Boolean.TRUE);
					}
					for(int i = 0;i<afmInfo.getDataEntrys().size();i++){
						AFMortgagedDataEntryInfo ade=afmInfo.getDataEntrys().get(i);
						IRow row= kDTable3.addRow();
						row.getCell("bDate").setValue(new Date());
						row.getCell("bFinish").setValue(Boolean.FALSE);
						row.getCell("bApproach").setValue(ade.getName());
						row.getCell("bRemark").setValue(ade.getRemark());
						row.getCell("aOrb").setValue(Boolean.FALSE);
					}
				}
			} catch (BOSException e)
			{
				e.printStackTrace();
			} 
		}else{
			String roomLoanId = getUIContext().get("roomLoan.id").toString();
			IRoomLoan roomLoan = (IRoomLoan)getBizInterface();
			RoomLoanInfo roomLoanInfo = roomLoan.getRoomLoanInfo(new ObjectUuidPK(roomLoanId));
			List list = getBatchRoomLoan();
			Boolean finish = Boolean.TRUE;
			for(int i = 0 ; i<roomLoanInfo.getAFMortgaged().size();i++){
				RoomLoanAFMEntrysInfo rleInfo=roomLoanInfo.getAFMortgaged().get(i);
				if(rleInfo.isIsAOrB()){
					IRow row= kDTable2.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("aDate").setValue(new Date());
//					row.getCell("aDate").getStyleAttributes().setLocked(true);
					for(int j=0; j<list.size(); j++){
						RoomLoanInfo loanInfo = (RoomLoanInfo)list.get(j);
						if(!loanInfo.getAFMortgaged().get(i).isIsFinish()){ 
							finish = Boolean.FALSE;
						}
//						else{
//							finish = Boolean.TRUE;
//						}
					}
					row.getCell("aFinish").setValue(finish);
					if(finish.booleanValue()){
						row.getStyleAttributes().setLocked(finish.booleanValue());
						row.getStyleAttributes().setBackground(Color.gray);
						row.getCell("aDate").setValue(null);
					}else{
						row.getStyleAttributes().setBackground(Color.white);
					}
					row.getCell("aApproach").setValue(rleInfo.getApproach());
					row.getCell("aRemark").setValue(rleInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
				}else{
					if(!getUIContext().containsKey("isEditData")){
					finish = Boolean.TRUE;
					IRow row= kDTable3.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("bDate").setValue(new Date());
//					row.getCell("bDate").getStyleAttributes().setLocked(true);
					for(int j=0; j<list.size(); j++){
						RoomLoanInfo loanInfo = (RoomLoanInfo)list.get(j);
						if(loanInfo.getAFMortgaged()!=null && loanInfo.getAFMortgaged().get(i)!=null && !loanInfo.getAFMortgaged().get(i).isIsFinish()){
							finish = Boolean.FALSE;
						}
//						else{
//							finish = Boolean.TRUE;
//						}
					}
					row.getCell("bFinish").setValue(finish);
					if(finish.booleanValue()){
						row.getStyleAttributes().setLocked(finish.booleanValue());
						row.getStyleAttributes().setBackground(Color.GRAY);
						row.getCell("bDate").setValue(null);
					}else{
						row.getStyleAttributes().setBackground(Color.white);
					}
					row.getCell("bApproach").setValue(rleInfo.getApproach());
					row.getCell("bRemark").setValue(rleInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
					
					}
				}
			}
			if(getUIContext().containsKey("isEditData") && ((Boolean)getUIContext().get("isEditData")).booleanValue()){
//				for(int j = 0;j<afmInfo.getDataEntrys().size();j++){
//					AFMortgagedDataEntryInfo ade=afmInfo.getDataEntrys().get(j);
//					IRow row= kDTable3.addRow();
//					row.getCell("bDate").setValue(new Date());
//					row.getCell("bFinish").setValue(Boolean.FALSE);
//					row.getCell("bApproach").setValue(ade.getName());
//					row.getCell("bRemark").setValue(ade.getRemark());
//					row.getCell("aOrb").setValue(Boolean.FALSE);
//				}
//				kDTable3.getStyleAttributes().setLocked(true);
			}
		}
    }
   	
    protected List getTableRoomLoan(){
    	List list = new ArrayList();
    	for(int i=0; i<kDTable1.getRowCount(); i++){
    		RoomLoanInfo roomloaninfo = new RoomLoanInfo();
    		list.add(roomloaninfo);
    	}
    	return list;
    }
    
	protected List getBatchRoomLoan() throws Exception{
		List list = new ArrayList();
		for(int i=0; i<kDTable1.getRowCount(); i++){
			if(kDTable1.getRow(i).getCell("roomLoanId").getValue()==null) continue;
			String roomLoanId = kDTable1.getRow(i).getCell("roomLoanId").getValue().toString();
			IRoomLoan roomLoan = (IRoomLoan)getBizInterface();
			RoomLoanInfo roomLoanInfo = roomLoan.getRoomLoanInfo(new ObjectUuidPK(roomLoanId));  
			list.add(roomLoanInfo);
		}
		return list;
	}
	
	protected void kDTable2_editStopped(KDTEditEvent e) throws Exception {
		IRow row=KDTableUtil.getSelectedRow(kDTable2);
		if(row!=null){
			Object af=row.getCell("aFinish").getValue();
			int rowIndex=row.getRowIndex();
			if(af.equals(Boolean.TRUE)){
				for(int i = 0 ; i<rowIndex;i++){
					kDTable2.getRow(i).getCell("aFinish").setValue(Boolean.TRUE);
				}
			}else{
				int rowCount=kDTable2.getRowCount();
				if(rowCount>rowIndex){
					for(int i = (rowIndex+1) ; i<rowCount;i++){
						kDTable2.getRow(i).getCell("aFinish").setValue(Boolean.FALSE);
					}
				}
				chbFinish.setSelected(false);
			}
		}
	}
	
	protected void kDTable3_editStopped(KDTEditEvent e) throws Exception {
		IRow row=KDTableUtil.getSelectedRow(kDTable3);
		if(row!=null){
			Object af=row.getCell("bFinish").getValue();
//			int rowIndex=row.getRowIndex();
			if(af.equals(Boolean.FALSE)){
				chbFinish.setSelected(false);
			}
//			if(af.equals(Boolean.TRUE)){
//				for(int i = 0 ; i<rowIndex;i++){
//					kDTable3.getRow(i).getCell("bFinish").setValue(Boolean.TRUE);
//				}
//			}else{
//				int rowCount=kDTable3.getRowCount();
//				if(rowCount>rowIndex){
//					for(int i = (rowIndex+1) ; i<rowCount;i++){
//						kDTable3.getRow(i).getCell("bFinish").setValue(Boolean.FALSE);
//					}
//				}
//				chbFinish.setSelected(false);
//			}
		}
	}

	protected void chbFinish_stateChanged(ChangeEvent e) throws Exception {
		if(chbFinish.isSelected()){
			for(int i = 0 ; i<kDTable2.getRowCount();i++){
				if(kDTable2.getRow(i).getCell("aFinish").getValue()!=null){
					String isFinish=kDTable2.getRow(i).getCell("aFinish").getValue().toString();
					if(isFinish.equals("0") || isFinish.equals("false")){
						chbFinish.setSelected(false);
						MsgBox.showWarning(this,"步骤未全部完成，无法选择办理完成！");
						return ;
					}
				}
				
			}
			for(int i = 0 ; i<kDTable3.getRowCount();i++){
				if(kDTable3.getRow(i).getCell("bFinish").getValue()!=null){
					String isFinish=kDTable3.getRow(i).getCell("bFinish").getValue().toString();
					if(isFinish.equals("0") || isFinish.equals("false")){
						chbFinish.setSelected(false);
						MsgBox.showWarning(this,"资料未全部完成，无法选择办理完成！");
						return ;
					}
				}
			}
		}
	}
	
	public void checkSelected()
    {
			if (kDTable1.getRowCount() == 0 || kDTable1.getSelectManager().size() == 0)
	        {
	            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
	            SysUtil.abort();
	        }
	}
	
	protected void btnRemoveRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRemoveRoom_actionPerformed(e);
		checkSelected();
		if(MsgBox.showConfirm2(this,"你确认要去掉该房间吗？") == MsgBox.CANCEL){
			return;
		}
		if(kDTable1.getRowCount() == 1){
			MsgBox.showInfo("房间信息只剩一条不能删除！");
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(kDTable1);
		kDTable1.removeRow(row.getRowIndex());
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		List roomLoanList = new ArrayList();
		if("toDo".equals(getUIContext().get("state").toString())){
			roomLoanList = getTableRoomLoan();
		}else{
			roomLoanList = getBatchRoomLoan();
		}
		updateRoomLoans(roomLoanList);
		intercalateApproach(roomLoanList);
		saveEntrysToEdit(roomLoanList);
		RoomLoanFactory.getRemoteInstance().batchSave(roomLoanList, null);
		if(getOprtState().equals(this.STATUS_ADDNEW)){
			MsgBox.showWarning(this,"保存成功！");
			this.getUIWindow().close();
			
		}
	}
	
//	boolean isSaved = false;
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		IRow firstRow = kDTable1.getRow(0);
		for(int i=0; i<kDTable1.getRowCount(); i++){
			IRow row = kDTable1.getRow(i);
			if(row.getCell("roomNumber").getValue() == null || "".equals(row.getCell("roomNumber").getValue().toString())){
				MsgBox.showInfo("请选择房间编码");
				abort();
			}
			if(row.getCell("number").getValue() == null || "".equals(row.getCell("number").getValue().toString())){
				MsgBox.showInfo("请填写公积金/按揭编号！");
				abort();
			}
			if(i>0){
				if(firstRow.getCell("number").getValue().toString().equals(row.getCell("number").getValue().toString())){
					MsgBox.showInfo("公积金/按揭编号不能重复！");
					abort();
				}
			}
			if(row.getCell("transactor").getValue() == null || row.getCell("transactor").getValue().toString() == null || "".equals(row.getCell("transactor").getValue().toString())){
				MsgBox.showInfo("请填写经办人！");
				abort();
			}
		}
		
		RoomLoanCollection roomLoanList = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection();
		if("toDo".equals(getUIContext().get("state"))){
			for(int i=0; i<roomLoanList.size(); i++){
				RoomLoanInfo roomLoan = (RoomLoanInfo)roomLoanList.get(i);
				for(int j=0; j<kDTable1.getRowCount(); j++){
					String number = kDTable1.getRow(j).getCell("number").getValue().toString();
					if(number.equals(roomLoan.getNumber())){
						MsgBox.showInfo("编号为  “"+number+"” 的公积金/按揭编号已存在！");
						abort();
					}
				}
			}
		}
	}
	
	protected void updateRoomLoans(List roomLoanList) throws Exception{
		for(int i=0; i<kDTable1.getRowCount(); i++){
			IRow row = kDTable1.getRow(i);
			
			String roomId = row.getCell("id").getValue().toString();
			RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
			String purchaseId = row.getCell("purchaseId").getValue().toString();
			PurchaseManageInfo purchaseInfo = PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(purchaseId));
			String moneyId = row.getCell("mmTypeId").getValue().toString();
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(moneyId));
			
			String number = row.getCell("number").getValue().toString();
			Integer loanFixedYear = null;
			if(row.getCell("loanFixedYear").getValue() != null){
				loanFixedYear = Integer.valueOf( row.getCell("loanFixedYear").getValue().toString());
			}
			BigDecimal actualLoanAmt = null;
			if(row.getCell("actualLoanAmt").getValue() != null){
				actualLoanAmt = new BigDecimal(row.getCell("actualLoanAmt").getValue().toString());
			}
			String loanLawyer = null;
			if(row.getCell("loanLawyer").getValue() != null){
				loanLawyer = row.getCell("loanLawyer").getValue().toString();
			}
			String loanCompactNumber = null;
			if(row.getCell("loanCompactNumber").getValue() != null){
				loanCompactNumber = row.getCell("loanCompactNumber").getValue().toString();
			}
			UserInfo transactor = (UserInfo)row.getCell("transactor").getValue(); 
			RoomLoanInfo roomloaninfo = (RoomLoanInfo)roomLoanList.get(i);
			
			if(roomloaninfo.getRoom() == null){
				roomloaninfo.setRoom(roomInfo);
			}
			if(roomloaninfo.getPurchase() == null){
				roomloaninfo.setPurchase(purchaseInfo);
			}
			if(roomloaninfo.getMmType() == null){
				roomloaninfo.setMmType(moneyInfo);
			}
			
			if("toDo".equals(getUIContext().get("state").toString())){
				roomloaninfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTING);
			}
			roomloaninfo.setNumber(number);
			if(loanFixedYear!=null) roomloaninfo.setLoanFixedYear(loanFixedYear.intValue());
			if(actualLoanAmt!=null) roomloaninfo.setActualLoanAmt(actualLoanAmt);
			if(loanLawyer!=null) roomloaninfo.setLoanLawyer(loanLawyer);
			if(loanFixedYear!=null) roomloaninfo.setLoanCompactNumber(loanCompactNumber);
			roomloaninfo.setTransactor(transactor);			
		}
	}
	
	public void updateAFMortgagedState(String property, String state, List roomLoanList) throws Exception{
//		int m = 0; 
//		for(int i=0; i<kDTable2.getRowCount(); i++){
//			if(((Boolean)kDTable2.getRow(i).getCell("aFinish").getValue()).booleanValue()){
//				m++;
//			}
//		}
		for(int i=0; i<roomLoanList.size(); i++){
//			int n=0;
			RoomLoanInfo roomLoanInfo = (RoomLoanInfo)roomLoanList.get(i);
//			for(int j=0; j<roomLoanInfo.getAFMortgaged().size();j++){
//				if(roomLoanInfo.getAFMortgaged().get(j).isIsAOrB() && roomLoanInfo.getAFMortgaged().get(j).isIsFinish()){
//					n++;
//				}
//			}
//			if(roomLoanInfo.getAFMortgagedState()==null
//					|| roomLoanInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTING)){
//				this.roomLoanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
				if(afmInfo!=null && roomLoanInfo.getORSOMortgaged()==null){
					roomLoanInfo.setORSOMortgaged(afmInfo);
				}
				if(property.equals("AFMortgagedState")){
					if(state.equals("done") && !getUIContext().containsKey("isEditData") && !"全部完成".equals(roomLoanInfo.getApproach())){
						roomLoanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
					}else if(state.equals("doing") && !getUIContext().containsKey("isEditData") && !"全部完成".equals(roomLoanInfo.getApproach())){
						roomLoanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTING);
					}
				}
//				else{
//					if("done".equals(getUIContext().get("state")) && m>n){
//						for(int j=0; j<roomLoanInfo.getAFMortgaged().size();j++){
//							if(roomLoanInfo.getAFMortgaged().get(j).isIsAOrB() && !roomLoanInfo.getAFMortgaged().get(j).isIsFinish()){
//								roomLoanInfo.setApproach(property);
//							}
//						}
//					}else if("toDo".equals(getUIContext().get("state"))){
//						roomLoanInfo.setApproach(property);
//					}
//				}
//			}
		}
	}
	
	public void updateApproach(String approach, List roomLoanList){
		int m = 0; 
		for(int i=0; i<kDTable2.getRowCount(); i++){
			if(((Boolean)kDTable2.getRow(i).getCell("aFinish").getValue()).booleanValue()){
				m++;
			}
		}
		for(int i=0; i<roomLoanList.size(); i++){
			int n = 0;
			RoomLoanInfo roomLoanInfo = (RoomLoanInfo)roomLoanList.get(i);
			for(int j=0; j<roomLoanInfo.getAFMortgaged().size();j++){
				if(roomLoanInfo.getAFMortgaged().get(j).isIsAOrB() && roomLoanInfo.getAFMortgaged().get(j).isIsFinish()){
					n++;
				}
			}
			if(roomLoanInfo.getAFMortgagedState()==null
					|| roomLoanInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTING)){
				if("done".equals(getUIContext().get("state")) && m>=n  && !"全部完成".equals(roomLoanInfo.getApproach())){
					roomLoanInfo.setApproach(approach);
				}else if("toDo".equals(getUIContext().get("state"))){
					roomLoanInfo.setApproach(approach);
				}	
			}else{
				if(!"全部完成".equals(roomLoanInfo.getApproach())){
					roomLoanInfo.setApproach(approach);
				}
			}
		}
	}
	
	public void intercalateApproach(List roomLoanList) throws Exception{
		String ApproachStr="";
		if(chbFinish.isSelected()){
			ApproachStr="全部完成";
			updateAFMortgagedState("AFMortgagedState", "done", roomLoanList);
		}else{
			for(int i= 0 ; i<kDTable2.getRowCount();i++){
				if(kDTable2.getRow(i).getCell("aFinish").getValue().equals(Boolean.TRUE)){
					ApproachStr=kDTable2.getRow(i).getCell("aApproach").getValue().toString();
				}
			}
//			if(!getUIContext().containsKey("isEditData")){
				updateAFMortgagedState("AFMortgagedState","doing", roomLoanList);
//			}
		}
//		updateAFMortgagedState(ApproachStr, "", roomLoanList);
//		if(!getUIContext().containsKey("isEditData")){
			updateApproach(ApproachStr, roomLoanList);
//		}
	}
	
	public void saveEntrysToEdit(List roomLoanList) throws Exception{
//		if("toDo".equals(getUIContext().get("state").toString())){
			int i=0;
			for(i=0; i<kDTable2.getRowCount(); i++){
				IRow row = kDTable2.getRow(i);
				RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
				rleInfo.setDate((Date)row.getCell("aDate").getValue());
				rleInfo.setIsFinish(((Boolean)row.getCell("aFinish").getValue()).booleanValue());
				rleInfo.setApproach(row.getCell("aApproach").getValue().toString());
				if(row.getCell("aRemark").getValue()!=null){
					rleInfo.setRemark(row.getCell("aRemark").getValue().toString());
				}
				rleInfo.setIsAOrB(((Boolean)row.getCell("aOrb").getValue()).booleanValue());
//				
//				RoomLoanInfo roomLoanInfo = new RoomLoanInfo();
//				roomLoanInfo.getAFMortgaged().add(rleInfo);
				for(int j=0; j<roomLoanList.size(); j++){
					RoomLoanInfo loanInfo = (RoomLoanInfo)roomLoanList.get(j);
					if(loanInfo.getAFMortgaged()==null || loanInfo.getAFMortgaged().get(i)==null){
						loanInfo.getAFMortgaged().add(rleInfo);
					}else if(loanInfo.getAFMortgaged().get(i)!=null && !loanInfo.getAFMortgaged().get(i).isIsFinish()){
						loanInfo.getAFMortgaged().set(i, rleInfo);
					}
				}
			}
			if(!getUIContext().containsKey("isEditData")){
				for(int k =0;k<kDTable3.getRowCount();k++){
					IRow row=kDTable3.getRow(k);
					RoomLoanAFMEntrysInfo rleInfo=new RoomLoanAFMEntrysInfo();
					rleInfo.setDate((Date)row.getCell("bDate").getValue());
					rleInfo.setIsFinish(((Boolean)row.getCell("bFinish").getValue()).booleanValue());
					rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
					if(row.getCell("bRemark").getValue()!=null){
						rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
					}
					rleInfo.setIsAOrB(((Boolean)row.getCell("aOrb").getValue()).booleanValue());
					for(int j=0; j<roomLoanList.size(); j++){
						RoomLoanInfo loanInfo = (RoomLoanInfo)roomLoanList.get(j);
						if("toDo".equals(getUIContext().get("state"))){
							loanInfo.getAFMortgaged().add(rleInfo);					
						}
						else if(loanInfo.getAFMortgaged().get(i+k)!=null && !loanInfo.getAFMortgaged().get(i+k).isIsFinish()){
							loanInfo.getAFMortgaged().set(i+k, rleInfo);
						}
					}
				}	
			}
//		}else{
//			List batchLoanList = getBatchRoomLoan();
//			for(int i=0; i<batchLoanList.size(); i++){
//				RoomLoanInfo roomloan = (RoomLoanInfo)batchLoanList.get(i);
//				if(roomloan.getAFMortgaged().get)
//			}
	}
	
	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		RoomLoanListUI listUI = (RoomLoanListUI) getUIContext().get("Owner");
		try {
			listUI.refresh(null);
		} catch (Exception e) {
			this.handleException(e);
		}
		return b;
	}
	
	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
		IRow row = kDTable1.addRow(0);
	}
	
    protected IObjectValue createNewData() {
		RoomLoanInfo objectValue = new RoomLoanInfo();
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomLoanFactory.getRemoteInstance();
	}

}