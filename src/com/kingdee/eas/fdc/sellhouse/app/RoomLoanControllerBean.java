package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomLoanControllerBean extends AbstractRoomLoanControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomLoanControllerBean");

//	protected void _batchSave(Context ctx, List idList, Map valueMap)
//			throws BOSException, EASBizException {
//		FilterInfo filterexist = new FilterInfo();
//		RoomLoanCollection roomLoanColl = new RoomLoanCollection();
//		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
//		Date processLoanDate = (Date) valueMap.get("processLoanDate");
//		Date hockEnrolDate = (Date) valueMap.get("hockEnrolDate");
//		String description = (String) valueMap.get("description");
//		UserInfo transactor = (UserInfo) valueMap.get("transactor");
//		UserInfo creator = ContextUtil.getCurrentUserInfo(ctx);
//		Timestamp creatTime = new Timestamp(new Date().getTime());
//		for (int i = 0, size = idList.size(); i < size; i++) {
//			filterexist = new FilterInfo();
//			filterexist.getFilterItems().add(
//					new FilterItemInfo("room.id", idList.get(i)));
//			if (super.exists(ctx, filterexist)) {
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filterexist);
//				RoomLoanInfo roomLoan = super.getRoomLoanCollection(ctx, view)
//						.get(0);
//				if (processLoanDate != null)
//					roomLoan.setProcessLoanDate(processLoanDate);
//				if (hockEnrolDate != null)
//					roomLoan.setHockEnrolDate(hockEnrolDate);
//				if (description != null && (!description.equals("")))
//					roomLoan.setDescription(description);
//				if (transactor != null)
//					roomLoan.setTransactor(transactor);
//				_save(ctx, roomLoan);
//			} else {
//				RoomLoanInfo roomLoan = new RoomLoanInfo();
//				RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(BOSUuid
//						.read(idList.get(i).toString())));
//				if (processLoanDate != null) {
//					room.setRoomLoanState(RoomLoanStateEnum.LOANED);
//					SelectorItemCollection selector = new SelectorItemCollection();
//					selector.add("roomLoanState");
//					iRoom.updatePartial(room, selector);
//				} else {
//					room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
//					SelectorItemCollection selector = new SelectorItemCollection();
//					selector.add("roomLoanState");
//					iRoom.updatePartial(room, selector);
//				}
//				roomLoan.setRoom(room);
//				roomLoan.setProcessLoanDate(processLoanDate);
//				roomLoan.setHockEnrolDate(hockEnrolDate);
//				roomLoan.setDescription(description);
//				roomLoan.setTransactor(transactor);
//				roomLoan.setCreator(creator);
//				roomLoan.setCreateTime(creatTime);
//				_save(ctx, roomLoan);
//				// roomLoanColl.add(roomLoan);
//			}
//		}
//
//		// if (roomLoanColl.size() > 0)
//		// super._addnew(ctx, roomLoanColl);
//	}

	protected void _batchSave(Context ctx, List idList, Map valueMap)
			throws BOSException, EASBizException {
		for(int i=0; i<idList.size(); i++){
			RoomLoanInfo roomLoanInfo = (RoomLoanInfo)idList.get(i);
			_save(ctx, roomLoanInfo);
		}
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomLoanInfo info = (RoomLoanInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom()
					.getId()));
			if (info.getProcessLoanDate() != null)
				room.setRoomLoanState(RoomLoanStateEnum.LOANED);
			else
				room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
			if (info.getFundProcessDate() != null)
				room.setRoomACCFundState(RoomACCFundStateEnum.FUND);
			else
				room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomLoanState");
			selector.add("roomACCFundState");
			iRoom.updatePartial(room, selector);
		}
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomLoanInfo info = (RoomLoanInfo) model;
		
		if (info.getRoom() != null && info.getRoom().getId() != null) {
			IRoom iRoom = RoomFactory.getLocalInstance(ctx);
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));

			
			RoomLoanInfo newInfo = info;
			if(info.getMmType()==null || info.getMmType().getMoneyType()==null) {
				newInfo = this.getRoomLoanInfo(ctx, "select AFMortgagedState,mmType.moneyType where id = '"+info.getId()+"' ");
			}
			
			if(newInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
				if(newInfo.getAFMortgagedState()!=null && newInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTED)) {
					room.setRoomLoanState(RoomLoanStateEnum.LOANED);
				}else{
					room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
				}					
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomLoanState");
				iRoom.updatePartial(room, selector);
			}else if(newInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {
				if(newInfo.getAFMortgagedState()!=null && newInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTED)) {
					room.setRoomACCFundState(RoomACCFundStateEnum.FUND);
				}else{
					room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
				}					
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomACCFundState");
				iRoom.updatePartial(room, selector);
			}
			
		}
		return super._submit(ctx, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		RoomLoanInfo info = super.getRoomLoanInfo(ctx, pk);
		if (info.getRoom() != null) {
			info.getRoom().setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
			info.getRoom().setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomLoanState");
			selector.add("roomACCFundState");
			RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
					selector);
		}
		if(pk!=null){
			recycleNumber(ctx, pk);	
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			RoomLoanInfo info = super.getRoomLoanInfo(ctx, arrayPK[i]);
			if (info.getRoom() != null) {
				info.getRoom().setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
				info.getRoom()
						.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomLoanState");
				selector.add("roomACCFundState");
				RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
						selector);
			}
		}
		for (int i = 0; i < arrayPK.length; i++) {
			IObjectPK pk = arrayPK[i];
			if(pk!=null){
				recycleNumber(ctx, pk);	
			}
		}
		super._delete(ctx, arrayPK);
	}

	protected boolean isUseName() {
		return false;
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		if (FDCBillInfo.getNumber() != null && FDCBillInfo.getNumber().trim().length() > 0) {
			super.checkBill(ctx, model);
		}
	}
	
	// 设置组织
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}
	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

	protected IRowSet _exeQuery(Context ctx, String filterStr) throws BOSException
	{
		if(filterStr==null)filterStr="";
		/*String sql="select distinct Sell.FName_L2 as SellProject,Sb.FName_L2 as Subarea,Build.FName_L2 as Building," +
					"BuilUnit.FName_L2 as BuildingUnit,Room.FID as RoomID,Room.FNumber as RoomNumber," +
					"Room.FRoomNo as RoomNo,Pur.FNumber as PurchaseNumber,Pur.FToSignDate as ToSignDate," +
					"FC.FName_L2 as FDCCustomer,MonDefine.FMoneyType as Moneydefine,Pur.FID as PurchaseID," +
					"MonDefine.FID as MonDefineID,Bu.FName_L2 as OrgUnit,MonDefine.FID as MoneyID , u.fname_l2 as salesmanName, roomloan.fdescription as roomLoanDescription, " +
					//添加付款类型 eirc_wang 2010.08.9
					" payType.FName_L2 payType, "+
					//添加用户名称 renliang 2010-11-6
					" Pur.FCustomerNames as CustomerNames "+
					"from T_SHE_PurchasePayListEntry PayList "+
					"left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid "+
					"left join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID "+
					"left join T_SHE_Room Room on Room.FID = Pur.FRoomID "+
					" left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid "+
					" left outer join t_she_AFMortgaged AF on roomloan.FORSOMortgagedID = AF.FID "+
//					" left outer join t_she_purchase as last on room.flastpurchaseid = last.fid "+
					" left outer join t_pm_user as u on pur.fsalesmanid = u.fid " +
					"left join T_SHE_Building Build on Build.FID = Room.FBuildingID "+
					"left join T_SHE_SellProject Sell on Sell.FID = Build.FSellProjectID "+
					"left join T_SHE_Subarea Sb on Sb.FID = Build.FSubareaID "+
					"left join T_SHE_BuildingUnit BuilUnit on BuilUnit.FID=Room.FBuildUnitID "+
					"left join T_SHE_PurchaseCustomerInfo PC on PC.FHeadID =Pur.FID "+
					"left join T_SHE_FDCCustomer FC on FC.FID=PC.FCustomerID "+
					"left join T_ORG_BaseUnit Bu on Bu.FID=Pur.FOrgUnitID "+
					"left join T_SHE_RoomSignContract SC on SC.FPurchaseID =Pur.FID "+
					//添加付款类型 eirc_wang 2010.08.9
					" left join T_SHE_SHEPayType payType on Pur.FPayTypeID = payType.FID "+
					"where (MonDefine.fmoneyType ='LoanAmount' or MonDefine.fmoneyType='AccFundAmount') " +
					"and PC.FPropertyPercent is not null and (Pur.FPurchaseState ='PurchaseAudit' or Pur.FPurchaseState = 'PurchaseChange') " +
					"and Room.FID is not null and PC.FSeq = 0 "+filterStr+" "+
					"order by SellProject,Subarea,Building,BuildingUnit,RoomNumber,RoomNo,purchasenumber";*/
		
			StringBuffer sb  = new StringBuffer();
			sb.append("select distinct Sell.FName_L2 as SellProject,Sb.FName_L2 as Subarea,Build.FName_L2 as Building,");
			sb.append(" BuilUnit.FName_L2 as BuildingUnit,Room.FID as RoomID,Room.Fdisplayname as RoomNumber,");
			sb.append(" Room.FRoomNo as RoomNo,Pur.FNumber as PurchaseNumber,Pur.FToSignDate as ToSignDate,");
			sb.append(" FC.FName_L2 as FDCCustomer,MonDefine.FMoneyType as Moneydefine,Pur.FID as PurchaseID,");
			sb.append(" MonDefine.FID as MonDefineID,Bu.FName_L2 as OrgUnit,MonDefine.FID as MoneyID , u.fname_l2 as salesmanName, roomloan.fdescription as roomLoanDescription,");
			sb.append(" payType.FName_L2 payType,");
			sb.append(" Pur.FCustomerNames as CustomerNames");
			sb.append(" from T_SHE_PurchasePayListEntry PayList ");
			sb.append(" left join t_she_moneydefine MonDefine on PayList.FMoneyDefineID = MonDefine.fid ");
			sb.append(" left join T_SHE_Purchase Pur on Pur.FID = PayList.FHeadID ");
			sb.append(" left join T_SHE_Room Room on Room.FID = Pur.FRoomID ");
			sb.append(" left outer join t_she_roomloan roomloan on  roomloan.froomid = room.fid ");
			sb.append(" left outer join t_she_AFMortgaged AF on roomloan.FORSOMortgagedID = AF.FID" );
			sb.append(" left outer join t_pm_user as u on pur.fsalesmanid = u.fid ");
			sb.append(" left join T_SHE_Building Build on Build.FID = Room.FBuildingID ");
			sb.append(" left join T_SHE_SellProject Sell on Sell.FID = Build.FSellProjectID ");
			sb.append(" left join T_SHE_Subarea Sb on Sb.FID = Build.FSubareaID ");
			sb.append(" left join T_SHE_BuildingUnit BuilUnit on BuilUnit.FID=Room.FBuildUnitID ");
			sb.append(" left join T_SHE_PurchaseCustomerInfo PC on PC.FHeadID =Pur.FID ");
			sb.append(" left join T_SHE_FDCCustomer FC on FC.FID=PC.FCustomerID ");
			sb.append(" left join T_ORG_BaseUnit Bu on Bu.FID=Pur.FOrgUnitID ");
			sb.append(" left join T_SHE_RoomSignContract SC on SC.FPurchaseID =Pur.FID ");
			sb.append(" left join T_SHE_SHEPayType payType on Pur.FPayTypeID = payType.FID ");
			sb.append(" where (MonDefine.fmoneyType ='LoanAmount' or MonDefine.fmoneyType='AccFundAmount') ");
			sb.append(" and PC.FPropertyPercent is not null and (Pur.FPurchaseState ='PurchaseAudit' or Pur.FPurchaseState = 'PurchaseChange') ");
			sb.append(" and Room.FID is not null and PC.FSeq = 0 "+filterStr);
			sb.append(" order by SellProject,Subarea,Building,BuildingUnit,RoomNumber,RoomNo,purchasenumber");
		
		//return DbUtil.executeQuery(ctx, sql);
		return DbUtil.executeQuery(ctx, sb.toString());
	}

	protected IRowSet _getRoomList(Context ctx, String projectID)
			throws BOSException, EASBizException {
		logger.error("roomloan getRoomList function start");
		logger.error("roomloan getRoomList function's projectID:"+projectID);
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append(" * ");
		sb.append(" from ( ");
		sb.append(" select ");
//		sb.append(" distinct ");
		sb.append(" room.fid as roomId, ");
		sb.append(" room.fname_l2 as roomName, ");
		sb.append(" room.fnumber as roomNumber, ");
		sb.append(" money.FMoneyType as moneyType "); 
		sb.append(" from t_she_room room ");
		sb.append(" left join T_SHE_Building building on room.fbuildingid = building.fid ");
		sb.append(" left join T_SHE_Transaction transac on room.fid = transac.froomid ");
		sb.append(" left join T_SHE_TranBusinessOverView bizView on bizView.fheadid = transac.fid ");
		sb.append(" left join T_SHE_MoneyDefine money on bizView.FMoneyDefineID  = money.fid ");
		sb.append(" left join t_she_sellproject sellproject on building.fsellprojectid = sellproject.fid ");
		sb.append(" where (money.FMoneyType = 'LoanAmount' ");
		sb.append(" or  money.FMoneyType ='AccFundAmount') ");
		sb.append(" and room.fid not in  ");
		sb.append(" ( ");
		sb.append(" select roomloan.FRoomID from T_SHE_RoomLoan roomloan ");
		sb.append(" left join T_SHE_MoneyDefine moneyType on roomloan.FMmType = moneyType.fid");
	    sb.append(" where roomloan.fafmortgagedState in ('0', '1', '3', '8')");
		sb.append(" and moneyType.FMoneyType =money.FMoneyType ");
		sb.append(" ) ");
		sb.append(" and sellproject.fid='"+projectID+"'");
		sb.append(" and transac.fcurrentlink in ('Sign') and transac.fisvalid = '0' ");
		sb.append(" ) as temp ");
		logger.error("roomloan getRoomList function sql:"+sb.toString());
		logger.error("roomloan getRoomList function return");
		return DbUtil.executeQuery(ctx, sb.toString());
		
	}

}