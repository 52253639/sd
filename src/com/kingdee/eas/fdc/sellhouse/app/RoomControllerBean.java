package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BillAdjustCollection;
import com.kingdee.eas.fdc.sellhouse.BillAdjustFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TranLinkEnum;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.util.app.DbUtil;

public class RoomControllerBean extends AbstractRoomControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomControllerBean");

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomInfo info = (RoomInfo) model;
		if(info.getSaleArea() == null){
			info.setSaleArea(info.getBuildingArea());
		}
		if (info.getRoomJoinState() == null)
			info.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
		if (info.getRoomLoanState() == null)
			info.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		if (info.getRoomACCFundState() == null)
			info.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		if (info.getRoomBookState() == null)
			info.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		if (info.getRoomCompensateState() == null)
			info.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		return super._save(ctx, info);
	}
	//界面控制
	protected void _checkNameDup(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomInfo info = (RoomInfo) model;
		if(info.getSaleArea() == null){
			info.setSaleArea(info.getBuildingArea());
		}
		if (info.getRoomJoinState() == null)
			info.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
		if (info.getRoomLoanState() == null)
			info.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		if (info.getRoomACCFundState() == null)
			info.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		if (info.getRoomBookState() == null)
			info.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		if (info.getRoomCompensateState() == null)
			info.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		return super._submit(ctx, info);
	}

	
	
	protected Result _submit(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		return super._submit(ctx, colls);
	}
	
	
	
//	protected void _checkNameDup(Context ctx, IObjectValue model)
//			throws BOSException, EASBizException {
//
//	}
	
	
	
	  protected void _checkNumberBlank(Context ctx , IObjectValue model) throws
      BOSException , EASBizException
      {
		  
      }
//	protected void _checkNameBlank(Context ctx, IObjectValue model)
//			throws BOSException, EASBizException {
//	}

//	protected void _checkNumberDup(Context ctx, IObjectValue model)
//			throws BOSException, EASBizException {
//		RoomInfo room = (RoomInfo) model;
//		FilterInfo filter = new FilterInfo();
//		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, room.getNumber(),CompareType.EQUALS);
//		filter.getFilterItems().add(filterItem);
//		if (room.getId() != null) {
//			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, room.getId(), CompareType.NOTEQUALS);
//			filter.getFilterItems().add(filterItem);
//		}
//		filterItem = new FilterItemInfo("building.id", room.getBuilding().getId().toString());
//		filter.getFilterItems().add(filterItem);
//		if(room.getBuildUnit()!=null){
//			filterItem = new FilterItemInfo("buildUnit.id", room.getBuildUnit().getId().toString());
//			filter.getFilterItems().add(filterItem);
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		SorterItemCollection sorter = new SorterItemCollection();
//		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
//
//		if (super._exists(ctx, filter)) {
//			String name = this._getPropertyAlias(ctx, room,	IFWEntityStruct.dataBase_Number)	+ room.getNumber();
//			throw new EASBizException(EASBizException.CHECKDUPLICATED,		new Object[] { name });
//		}
//	}
	
	/**
	 * 售前复核
	 * */
	protected void _doAreaAudit(Context ctx, List idList) throws BOSException {
		if (idList == null || idList.isEmpty()) {
			logger.warn("选中项为空，客户端逻辑判断有误，请检查。");
			return;
		}
		StringBuffer sb = new StringBuffer(
				"update T_SHE_ROOM set FIsAreaAudited=?,FSaleArea=FBuildingArea where FID in (");
		for (int i = 0; i < idList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(idList.get(i));
			sb.append("'");
		}
		sb.append(")");
		logger.debug("sql:" + sb.toString());
		DbUtil.execute(ctx, sb.toString(), new Object[] {new Integer(1)});
	}
	
	/**
	 * 实测复核
	 * */
	protected void _doActualAreaAudit(Context ctx, List idList) throws BOSException {
		if (idList == null || idList.isEmpty()) {
			logger.warn("选中项为空，客户端逻辑判断有误，请检查。");
			return;
		}
		//房间如果已经认购了，房间状态变化时会根据预售或者现售更新销售面积，这里不能再更新了
		StringBuffer sb = new StringBuffer("update T_SHE_ROOM set FIsActualAreaAudited=? where FSellState in ('PrePurchase','Purchase','Sign') and FID in (");
		for (int i = 0; i < idList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(idList.get(i));
			sb.append("'");
		}
		sb.append(")");
		logger.debug("sql:" + sb.toString());
		DbUtil.execute(ctx, sb.toString(), new Object[] { new Integer(1) });
		
		sb = new StringBuffer("update T_SHE_ROOM set FIsActualAreaAudited=?,FSaleArea=FActualBuildingArea where FSellState not in ('PrePurchase','Purchase','Sign') and FID in (");
		for (int i = 0; i < idList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(idList.get(i));
			sb.append("'");
		}
		sb.append(")");
		logger.debug("sql:" + sb.toString());
		DbUtil.execute(ctx, sb.toString(), new Object[] { new Integer(1) });

	}

	protected void _doBasePriceAudit(Context ctx, List idList) throws BOSException {
		if (idList == null || idList.isEmpty()) {
			logger.warn("选中项为空，客户端逻辑判断有误，请检查。");
			return;
		}
		StringBuffer sb = new StringBuffer(
				"update T_SHE_ROOM set FIsBasePriceAudited=? where FID in (");
		for (int i = 0; i < idList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(idList.get(i));
			sb.append("'");
		}
		sb.append(")");
		logger.debug("sql:" + sb.toString());
		DbUtil.execute(ctx, sb.toString(), new Object[] {new Integer(1)});
	}

	/**
	 * 回收房间,清除该房间当前执行的认购合同及相关信息 
	 * 需要清除的数据: 
	 * n 当前认购单 
	 * n 认购单对应的变更单 
	 * n 认购单对应的更名单 
	 * n认购单对应的收款单 
	 * n 当前签约单 
	 * 退房单
	 * n 入伙单 
	 * n 按揭办理 
	 * n 产权办理 
	 * n 面积补差 
	 * n 跟进记录
	 * 附属房产的相关纪录
	 */
	protected void _reclaimRoom(Context ctx, String id) throws BOSException, EASBizException {
		if(id == null){
			logger.error("回收的房间ID不能为空！");
			return;
		}
		
		//先校验
		RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(id));
		
		if(room == null){
			throw new BOSException("房间ID找不到房间!");
		}
		
		RoomSellStateEnum sellState = room.getSellState();
		if(!RoomSellStateEnum.PrePurchase.equals(sellState)  && !RoomSellStateEnum.Purchase.equals(sellState)  &&  !RoomSellStateEnum.Sign.equals(sellState)){
			throw new BOSException("房间不是预定,认购或者签约状态!");
		}
		
		PurchaseInfo purchase = room.getLastPurchase();
		
		if(purchase == null  || purchase.getId() == null){
			throw new BOSException("房间没有当前认购单！");//TODO 定义业务异常
		}
		
		//待清除的跟进记录
		Set toDelBillId = new HashSet();
		String purchaseId = purchase.getId().toString();
		toDelBillId.add(purchaseId);
		
		//如果有附属房产，需要把附属房间的销售状态改为待售状态
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo attachFilter = new FilterInfo();
		attachFilter.getFilterItems().add(new FilterItemInfo("head.id", purchaseId));
		view.setFilter(attachFilter);
		
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.*");
		view.getSelector().add("attachmentEntry.room.*");
		
		PurchaseRoomAttachmentEntryCollection atts = PurchaseRoomAttachmentEntryFactory.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection(view);
		for(int i=0; i<atts.size(); i++){
			PurchaseRoomAttachmentEntryInfo att = atts.get(i);
			if(att == null){
				logger.error("PurchaseRoomAttachmentEntryInfo 为空.");
				continue;
			}
			if(att.getAttachmentEntry() == null){
				logger.error("att.getAttachmentEntry() 为空.");
				continue;
			}
			RoomInfo attRoom = att.getAttachmentEntry().getRoom();
			if(attRoom == null){
				logger.error("attRoom 为空.");
				continue;
			}
			setRoomOnShow(ctx, attRoom);
		}
		
		//认购变更单
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("purchase.id", purchaseId));
		PurchaseChangeFactory.getLocalInstance(ctx).delete(filter);
		
		//更名单
		PurchaseChangeCustomerFactory.getLocalInstance(ctx).delete(filter);
		
		//签约单
		IObjectPK[] deltedSignPKs = RoomSignContractFactory.getLocalInstance(ctx).delete(filter);
		for(int i=0; i<deltedSignPKs.length; i++){
			toDelBillId.add(deltedSignPKs[i].toString());
		}
		
		//退房单
		IObjectPK[] deltedQuitPKs = QuitRoomFactory.getLocalInstance(ctx).delete(filter);
		for(int i=0; i<deltedQuitPKs.length; i++){
			toDelBillId.add(deltedQuitPKs[i].toString());
		}
		
		//房地产收款单
		FDCReceivingBillFactory.getLocalInstance(ctx).delete("where purchaseObj.id='"+purchaseId+"'");;
		
		//更新房间信息
		setRoomOnShow(ctx, room);
		
		//认购单
		PurchaseFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(purchaseId));
		
		//清除跟进记录
		if(!toDelBillId.isEmpty()){
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("relationContract", toDelBillId, CompareType.INCLUDE));
			TrackRecordFactory.getLocalInstance(ctx).delete(filter);
		}
	}

	private void setRoomOnShow(Context ctx, RoomInfo room) throws BOSException, EASBizException {
		room.setSellState(RoomSellStateEnum.OnShow);
		room.setDealPrice(null);
		room.setDealTotalAmount(null);
		room.setToPrePurchaseDate(null);
		room.setToPurchaseDate(null);
		
		room.setToSignDate(null);
		room.setLastPurchase(null);
		room.setLastSignContract(null);
		room.setLastAreaCompensate(null);//TODO 确认该字段
		room.setAreaCompensateAmount(null);
		
		room.setSellAmount(null);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("toPrePurchaseDate");
		sels.add("toPurchaseDate");
		
		sels.add("toSignDate");
		sels.add("lastPurchase");
		sels.add("lastSignContract");
		sels.add("lastAreaCompensate");
		sels.add("areaCompensateAmount");
		
		sels.add("sellAmount");
		
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
	}

	/**
	 * 一次性放回房间的多个信息集合
	 * collInfoNames 可包含的字符串内容包括：  
	 * RoomJoinInfo RoomAreaCompensateInfo  RoomLoanInfo RoomAccFundInfo RoomPropertyBookInfo  QuitRoomCollection
	 * BizListEntryCollection PurchasePayListEntryCollection PurchaseCollection PurchaseChangeCollection 
	 * PurchaseChangeCustomerCollection RoomSignContractCollection RoomKeepDownBillCollection ChangeRoomCollection
	 * BillAdjustCollection ReceivingBillCollectionR ReceivingBillCollectionP
	 * 
	 */
	protected Map _getRoomInfoCollectionMap(Context ctx, RoomInfo roomInfo, String collInfoNames) throws BOSException, EASBizException, EASBizException {
		Map infoMap = new HashMap();
		if(roomInfo==null || collInfoNames==null)	return infoMap;
		
		logger.info("getRoomInfoCollectionMap:"+collInfoNames + new Timestamp(new Date().getTime()));
		
		String roomId = roomInfo.getId().toString();
		PurchaseInfo mainline = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo("select id,currentLink,billId where room.id='"+roomId+"'");
		TranLinkEnum type = null;
		String purID = null;
		if(mainline ==null){
			return infoMap;
		}else{
//			type = mainline.getCurrentLink();
//			purID = mainline.getBillId().toString();
		}
		BaseTransactionInfo newTransaction = null;
		if(type != null && TranLinkEnum.SIGN.equals(type)){
			newTransaction = SignManageFactory.getLocalInstance(ctx).getSignManageInfo("select id,payType.* where id='"+purID+ "'");
		} else if(type != null && TranLinkEnum.PURCHASE.equals(type)){
			newTransaction = PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo("select id,payType.* where id='"+purID+ "'");
		} else if(type != null && TranLinkEnum.PREDETERMINE.equals(type)){
			newTransaction = PrePurchaseManageFactory.getLocalInstance(ctx).getPrePurchaseManageInfo("select id,payType.* where id='"+purID+ "'");
		}
		if(newTransaction == null){
			return infoMap;
		}
		
		
		if(collInfoNames.indexOf("RoomJoinInfo")>=0) {
			RoomJoinCollection joins = RoomJoinFactory.getLocalInstance(ctx).getRoomJoinCollection(
							"select id,joinDate,joinEndDate where room.id = '"+roomId+"'");
			if (joins.size() > 1) 
				throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { "存在多个入伙,系统错误!" });
			else if(joins.size() == 1) 
				infoMap.put("RoomJoinInfo", joins.get(0));
			else
				infoMap.put("RoomJoinInfo", null);
			
			logger.info("getRoomInfoCollectionMap: RoomJoinInfo" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("RoomAreaCompensateInfo")>=0) {
			RoomAreaCompensateCollection roomAreaCompens = RoomAreaCompensateFactory.getLocalInstance(ctx)
				.getRoomAreaCompensateCollection("select compensateDate,compensateState,compensateAmount where room.id='"+roomId+"'");
			if(roomAreaCompens.size()>1)
				throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { "存在多个补差,系统错误!" });
			else if(roomAreaCompens.size()==1)
				infoMap.put("RoomAreaCompensateInfo", roomAreaCompens.get(0));
			else
				infoMap.put("RoomAreaCompensateInfo", null);
			
			logger.info("getRoomInfoCollectionMap: RoomAreaCompensateInfo" + new Timestamp(new Date().getTime()));
		}
		//by tim—gao DB2中总览页签不显示，去掉数字查询的''
		if(collInfoNames.indexOf("RoomLoanInfo")>=0) {
			RoomLoanCollection roomLoans = RoomLoanFactory.getLocalInstance(ctx)
				.getRoomLoanCollection("select fundProcessDate,processLoanDate where room.id='"+roomId+"' and mmType.moneyType = '"+MoneyTypeEnum.LOANAMOUNT_VALUE+"' and aFMortgagedState in ("+0+","+1+","+3+")");

			if(roomLoans.size()>1)
				throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { "存在多个按揭,系统错误!" });
			else if(roomLoans.size()==1)
				infoMap.put("RoomLoanInfo", roomLoans.get(0));
			else
				infoMap.put("RoomLoanInfo", null);
			
			logger.info("getRoomInfoCollectionMap: RoomLoanInfo" + new Timestamp(new Date().getTime()));
		}
		//by tim—gao DB2中总览页签不显示，去掉数字查询的''
		if(collInfoNames.indexOf("RoomAccFundInfo")>=0) {
			RoomLoanCollection roomLoans = RoomLoanFactory.getLocalInstance(ctx)
				.getRoomLoanCollection("select fundProcessDate,processLoanDate where room.id='"+roomId+"' and mmType.moneyType = '"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"' and aFMortgagedState in ("+0+","+1+","+3+")");
			if(roomLoans.size()>1)
				throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { "存在多个公积金,系统错误!" });
			else if(roomLoans.size()==1)
				infoMap.put("RoomAccFundInfo", roomLoans.get(0));
			else
				infoMap.put("RoomAccFundInfo", null);
			
			logger.info("getRoomInfoCollectionMap: RoomAccFundInfo" + new Timestamp(new Date().getTime()));
		}
		
		
		if(collInfoNames.indexOf("RoomPropertyBookInfo")>=0) {
			RoomPropertyBookCollection roomBooks = RoomPropertyBookFactory.getLocalInstance(ctx)
				.getRoomPropertyBookCollection("select id,transactDate where room.id='"+roomId+"'");
			if(roomBooks.size()>1)
				throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { "存在产权登记,系统错误!" });
			else if(roomBooks.size()==1)
				infoMap.put("RoomPropertyBookInfo", roomBooks.get(0));
			else
				infoMap.put("RoomPropertyBookInfo", null);
			
			logger.info("getRoomInfoCollectionMap: RoomPropertyBookInfo" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("QuitRoomCollection")>=0) {
			QuitRoomCollection quitRooms = QuitRoomFactory.getLocalInstance(ctx)
				.getQuitRoomCollection("select number,state,quitDate,description,purchase.id,creator.name where room.id='"+roomId+"'");
			infoMap.put("QuitRoomCollection", quitRooms);
			
			logger.info("getRoomInfoCollectionMap: QuitRoomCollection" + new Timestamp(new Date().getTime()));
		}
		
		//最新单据付款方案的业务流程分录
//		BaseTransactionInfo newTransaction = null;
//		if (signInfo != null && signInfo.getPayType() != null ) {
//			newTransaction = signInfo ;
//		} else
//			if(purchaseInfo != null && purchaseInfo.getPayType() != null) {
//			newTransaction =purchaseInfo;
//		} else if(prePurchaseInfo != null && prePurchaseInfo.getPayType() != null){
//			newTransaction =prePurchaseInfo;
//		}
		if(collInfoNames.indexOf("BizListEntryCollection")>=0 && newTransaction.getPayType() != null) {
			BizListEntryCollection bizLists = BizListEntryFactory.getLocalInstance(ctx)
				.getBizListEntryCollection("select id,bizFlow,payTypeBizFlow,bizTime,payTypeBizTime,appDate,monthLimit,dayLimit where head.id='"+newTransaction.getPayType().getId().toString()+"' order by seq");
			infoMap.put("BizListEntryCollection", bizLists);
			
			logger.info("getRoomInfoCollectionMap: BizListEntryCollection" + new Timestamp(new Date().getTime()));
		}
		//签约单对应的付款明细
		if(collInfoNames.indexOf("SignPayListEntryCollection")>=0 && TranLinkEnum.SIGN.equals(type)) {
			SignPayListEntryCollection listEntrys = SignPayListEntryFactory.getLocalInstance(ctx)
				.getSignPayListEntryCollection("select id,appAmount,actRevAmount,actRevDate " +
						",seq,appDate,hasRefundmentAmount,moneyDefine.id,moneyDefine.name,moneyDefine.moneyType,currency.id,currency.name  where head.id='"+newTransaction.getId().toString()+"' order by seq");
			infoMap.put("SignPayListEntryCollection", listEntrys);
			
			logger.info("getRoomInfoCollectionMap: SignPayListEntryCollection" + new Timestamp(new Date().getTime()));
		}
		
		//认购单对应的付款明细
		if(collInfoNames.indexOf("PurPayListEntryCollection")>=0 && TranLinkEnum.PURCHASE.equals(type)) {
			PurPayListEntryCollection listEntrys = PurPayListEntryFactory.getLocalInstance(ctx)
				.getPurPayListEntryCollection("select id,appAmount,actRevAmount,actRevDate " +
						",seq,appDate,hasRefundmentAmount,moneyDefine.id,moneyDefine.name,moneyDefine.moneyType,currency.id,currency.name  where head.id='"+newTransaction.getId().toString()+"' order by seq");
			infoMap.put("PurPayListEntryCollection", listEntrys);
			
			logger.info("getRoomInfoCollectionMap: PurPayListEntryCollection" + new Timestamp(new Date().getTime()));
		}
		
		//预订单对应的付款明细
		if(collInfoNames.indexOf("PrePurchasePayListEntryCollection")>=0 && TranLinkEnum.PREDETERMINE.equals(type)) {
			PrePurchasePayListEntryCollection listEntrys = PrePurchasePayListEntryFactory.getLocalInstance(ctx)
				.getPrePurchasePayListEntryCollection("select id,appAmount,actRevAmount,actRevDate " +
						",seq,appDate,hasRefundmentAmount,moneyDefine.id,moneyDefine.name,moneyDefine.moneyType,currency.id,currency.name  where head.id='"+newTransaction.getId().toString()+"' order by seq");
			infoMap.put("PrePurchasePayListEntryCollection", listEntrys);
			
			logger.info("getRoomInfoCollectionMap: PrePurchasePayListEntryCollection" + new Timestamp(new Date().getTime()));
		}
		
		//其他应收 xin_wang 2010.09.19
		if(collInfoNames.indexOf("elsePayListEntry")>=0 && roomInfo.getLastPurchase()!=null) {
			PurchaseElsePayListEntryCollection elsePayListEntrys = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
				.getPurchaseElsePayListEntryCollection("select id,appAmount,actRevAmount,actRevDate " +
						",seq,appDate,hasRefundmentAmount,moneyDefine.id,moneyDefine.name,moneyDefine.moneyType,currency.id,currency.name  where head.id='"+roomInfo.getLastPurchase().getId().toString()+"' order by seq");
			infoMap.put("elsePayListEntry", elsePayListEntrys);
			
			logger.info("getRoomInfoCollectionMap: elsePayListEntry" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("PurchaseCollection")>=0) {
			PurchaseCollection purchases = PurchaseFactory.getLocalInstance(ctx)
				.getPurchaseCollection("select number,purchaseState,bookedDate,customerNames,salesman.name,dealAmount,description,prePurchaseDate," +
						" purchaseDate,creator.name where room.id='"+roomId+"'");
			infoMap.put("PurchaseCollection", purchases);
			
			logger.info("getRoomInfoCollectionMap: PurchaseCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("PurchaseChangeCollection")>=0) {
			PurchaseChangeCollection purchanges = PurchaseChangeFactory.getLocalInstance(ctx)
				.getPurchaseChangeCollection("select number,changeDate,state,bookedDate,purchase.id,description ,creator.name where purchase.room.id='"+roomId+"'");
			infoMap.put("PurchaseChangeCollection", purchanges);
			
			logger.info("getRoomInfoCollectionMap: PurchaseChangeCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("PurchaseChangeCustomerCollection")>=0) {
			PurchaseChangeCustomerCollection purchangeCusts = PurchaseChangeCustomerFactory.getLocalInstance(ctx)
				.getPurchaseChangeCustomerCollection("select number,bizDate,purchase.id,state,bookedDate,bizDate,lastUpdateTime,description ,creator.name where purchase.room.id='"+roomId+"'");
			infoMap.put("PurchaseChangeCustomerCollection", purchangeCusts);
			
			logger.info("getRoomInfoCollectionMap: PurchaseChangeCustomerCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("RoomSignContractCollection")>=0) {
			RoomSignContractCollection roomSignContracts = RoomSignContractFactory.getLocalInstance(ctx)
				.getRoomSignContractCollection("select number,signDate,purchase.id,isBlankOut,description ,creator.name where room.id='"+roomId+"'");
			infoMap.put("RoomSignContractCollection", roomSignContracts);
			
			logger.info("getRoomInfoCollectionMap: getRoomInfoCollectionMap" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("RoomKeepDownBillCollection")>=0) {
			RoomKeepDownBillCollection roomKeepDowns = RoomKeepDownBillFactory.getLocalInstance(ctx)
				.getRoomKeepDownBillCollection("select number,cancelDate,bizDate,purchase.id,description ,creator.name,handler.name where room.id='"+roomId+"'");
			infoMap.put("RoomKeepDownBillCollection", roomKeepDowns);
			
			logger.info("getRoomInfoCollectionMap: RoomKeepDownBillCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("ChangeRoomCollection")>=0) {
			ChangeRoomCollection changRooms = ChangeRoomFactory.getLocalInstance(ctx)
				.getChangeRoomCollection("select number,changeDate,oldPurchase.id,state,description ,creator.name where oldRoom.id='"+roomId+"'");
			infoMap.put("ChangeRoomCollection", changRooms);
			
			logger.info("getRoomInfoCollectionMap: ChangeRoomCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("BillAdjustCollection")>=0) {
			BillAdjustCollection billAdjusts = BillAdjustFactory.getLocalInstance(ctx)
				.getBillAdjustCollection("select number,state,bizDate,purchase.id,description ,creator.name where purchase.room.id='"+roomId+"'");
			infoMap.put("BillAdjustCollection", billAdjusts);
			
			logger.info("getRoomInfoCollectionMap: BillAdjustCollection" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("ReceivingBillCollectionR")>=0) {
			FDCReceivingBillCollection fdcRevs = FDCReceivingBillFactory.getLocalInstance(ctx)
				.getFDCReceivingBillCollection("select amount,bizDate,currency.name,customer.name,creator.name,entries.revAmount,entries.moneyDefine.name " +
						",revBillType,entries.sourceEntries.*,entries.sourceEntries.fromMoneyDefine.name " + 
						" where room.id = '"+roomId+"' and billStatus <> "+RevBillStatusEnum.SAVE_VALUE +" " +   
						" and (revBizType ='"+RevBizTypeEnum.CUSTOMER_VALUE +"' or revBizType ='"+RevBizTypeEnum.PURCHASE_VALUE +"' or revBizType ='"+RevBizTypeEnum.SINCERITY_VALUE +"' or revBizType ='"+RevBizTypeEnum.AREACOMPENSATE_VALUE +"') " +
						" order by createTime desc "	);
			
			//,entries.sourceEntries.fromMoneyDefine.name 这个字段为冗余字段，所以要单独查询
			for(int i=0;i<fdcRevs.size();i++) {
				for(int j=0;j<fdcRevs.get(i).getEntries().size();j++) {
					FDCReceivingBillEntryInfo fdcEntryInfo = fdcRevs.get(i).getEntries().get(j);
					for(int k=0;k<fdcEntryInfo.getSourceEntries().size();k++)  {
						TransferSourceEntryInfo trasfInfo = fdcEntryInfo.getSourceEntries().get(k);
						SheRevHandle sheRevHandle = new SheRevHandle();
						if(trasfInfo.getFromMoneyDefine()==null && trasfInfo.getFromRevListId()!=null){
							IRevList interfaceFactroy = (IRevList)sheRevHandle.getRevListBizInterface(ctx, trasfInfo.getFromRevListType());
							if(interfaceFactroy!=null) {							
									RevListInfo revListInfo = interfaceFactroy.getRevListInfo("select moneyDefine.name where id = '"+trasfInfo.getFromRevListId()+"'");
									trasfInfo.setFromMoneyDefine(revListInfo.getMoneyDefine());
							}
						}
					}
				}			
			}
			
			infoMap.put("ReceivingBillCollectionR", fdcRevs);
			
			logger.info("getRoomInfoCollectionMap: ReceivingBillCollectionR" + new Timestamp(new Date().getTime()));
		}
		
		if(collInfoNames.indexOf("ReceivingBillCollectionP")>=0 && roomInfo.getLastPurchase()!=null) {
			FDCReceivingBillCollection fdcRevs = FDCReceivingBillFactory.getLocalInstance(ctx)
				.getFDCReceivingBillCollection("select amount,bizDate,currency.name,customer.name,creator.name,entries.revAmount,entries.moneyDefine.name " +
					",revBillType,entries.sourceEntries.*,entries.sourceEntries.fromMoneyDefine.name  " + 
					"where purchaseObj.id = '"+roomInfo.getLastPurchase().getId()+ "' and billStatus<>"+RevBillStatusEnum.SAVE_VALUE +" "+
					" and (revBizType ='"+RevBizTypeEnum.CUSTOMER_VALUE +"' or revBizType ='"+RevBizTypeEnum.PURCHASE_VALUE +"' or revBizType ='"+RevBizTypeEnum.SINCERITY_VALUE +"' or revBizType ='"+RevBizTypeEnum.AREACOMPENSATE_VALUE +"') " +
					"  order by createTime desc"	); 
			//,entries.sourceEntries.fromMoneyDefine.name 这个字段为冗余字段，所以要单独查询
			for(int i=0;i<fdcRevs.size();i++) {
				for(int j=0;j<fdcRevs.get(i).getEntries().size();j++) {
					FDCReceivingBillEntryInfo fdcEntryInfo = fdcRevs.get(i).getEntries().get(j);
					for(int k=0;k<fdcEntryInfo.getSourceEntries().size();k++)  {
						TransferSourceEntryInfo trasfInfo = fdcEntryInfo.getSourceEntries().get(k);
						SheRevHandle sheRevHandle = new SheRevHandle();
						if(trasfInfo.getFromMoneyDefine()==null && trasfInfo.getFromRevListId()!=null){
							IRevList interfaceFactroy = (IRevList)sheRevHandle.getRevListBizInterface(ctx, trasfInfo.getFromRevListType());
							if(interfaceFactroy!=null) {
								  RevListInfo revListInfo = interfaceFactroy.getRevListInfo("select moneyDefine.name where id = '"+trasfInfo.getFromRevListId()+"'");
								  trasfInfo.setFromMoneyDefine(revListInfo.getMoneyDefine());
							}
						}
					}
				}			
			}
			infoMap.put("ReceivingBillCollectionP", fdcRevs);
			
			logger.info("getRoomInfoCollectionMap: ReceivingBillCollectionP" + new Timestamp(new Date().getTime()));
		}
		//
		
		//诚意认购单
		if(collInfoNames.indexOf("SincerityPurchaseCollection")>=0) {
			SincerityPurchaseCollection sinColl = SincerityPurchaseFactory.getLocalInstance(ctx) 
				.getSincerityPurchaseCollection("select sincerityAmount,number,state,sincerityState,bookDate,description,customer.name ,salesman.name,creator.name" +
						",sellProject.name,sellProject.number where room.id='"+roomId+"'"); 
			infoMap.put("SincerityPurchaseCollection", sinColl);
			logger.info("getRoomInfoCollectionMap: SincerityPurchaseCollection" + new Timestamp(new Date().getTime()));
		}
		
		
		logger.info("getRoomInfoCollectionMap: End......." + new Timestamp(new Date().getTime()));
		return infoMap;
	}
	
	protected void _roomIpdateBatch(Context ctx, List idList, Map map)
			throws BOSException {
		StringBuffer sb=new StringBuffer();
		sb.append("update T_SHE_Room set FDescription_l1=FDescription_l1");
		if(map.get("floorHeight")!=null){
			sb.append(",FFloorHeight ="+(BigDecimal)map.get("floorHeight"));
		}
		if(map.get("direction")!=null){
			sb.append(",FDirectionID ='"+map.get("direction")+"'");
		}
		if(map.get("roomForm")!=null){
			sb.append(",FRoomFormID ='"+map.get("roomForm")+"'");
		}
//		if(map.get("sight")!=null){
//			sb.append(",FSightID ='"+map.get("sight")+"'");
//		}
		if(map.get("sight")!=null){
			sb.append(",FNewSightID ='"+map.get("sight")+"'");
		}
		if(map.get("fitmentStandard")!=null){
			sb.append(",FFitmentStandard ='"+map.get("fitmentStandard")+"'");
		}
		if(map.get("buildingProperty")!=null){
			sb.append(",FBuildingPropertyID ='"+map.get("buildingProperty")+"'");
		}
		if(map.get("deliverHouseStandard")!=null){
			sb.append(",FDeliverHouseStandard ='"+map.get("deliverHouseStandard")+"'");
		}
		if(map.get("productType")!=null){
			sb.append(",FProductTypeID ='"+map.get("productType")+"'");
		}
//		if(map.get("productDetail")!=null){
//			sb.append(",FProductDetailID ='"+map.get("productDetail")+"'");
//		}
		if(map.get("productDetail")!=null){
			sb.append(",FNewProduceRemarkID ='"+map.get("productDetail")+"'");
		}
		if(map.get("houseProperty")!=null){
			sb.append(",FHouseProperty ='"+map.get("houseProperty")+"'");
		}
//		if(map.get("eyeSignht")!=null){
//			sb.append(",FEyeSignhtID ='"+map.get("eyeSignht")+"'");
//		}
		if(map.get("eyeSignht")!=null){
			sb.append(",FNewEyeSightID ='"+map.get("eyeSignht")+"'");
		}
//		if(map.get("noise")!=null){
//			sb.append(",FNoiseID ='"+map.get("noise")+"'");
//		}
		
		//by tim_gao 增加户型，建筑结构
		if(map.get("roomModel")!=null){
			sb.append(",FRoomModelID ='"+map.get("roomModel")+"'");
		}
		if(map.get("builStruct")!=null){
			sb.append(",FBuilStructID ='"+map.get("builStruct")+"'");
		}
		
		
		if(map.get("noise")!=null){
			sb.append(",FNewNoiseID ='"+map.get("noise")+"'");
		}
//		if(map.get("roomUsage")!=null){
//			sb.append(",FRoomUsageID ='"+map.get("roomUsage")+"'");
//		}
		if(map.get("roomUsage")!=null){
			sb.append(",FNewRoomUsageID ='"+map.get("roomUsage")+"'");
		}
//		if(map.get("posseStd")!=null){
//			sb.append(",FPosseStdID ='"+map.get("posseStd")+"'");
//		}
		if(map.get("posseStd")!=null){
			sb.append(",FNewPossStdID ='"+map.get("posseStd")+"'");
		}
//		if(map.get("decoraStd")!=null){
//			sb.append(",FDecoraStdID ='"+map.get("decoraStd")+"'");
//		}
		if(map.get("decoraStd")!=null){
			sb.append(",FNewDecorastdID ='"+map.get("decoraStd")+"'");
		}
		if(map.get("window")!=null){
			sb.append(",Fwindow ='"+map.get("window")+"'");
		}
		Set set=new HashSet();
    	for(int i =0;i<idList.size();i++){
    		set.add(idList.get(i));
    	}
		sb.append(" where Fid in "+FMHelper.setTran2String(set)+"");
		DbUtil.execute(ctx, sb.toString());
	}

	/**
	 * 批量增加房屋面积变更历史
	 * new add by renliang 2011-1-18
	 */
	protected void _addRoomAreaChange(Context ctx, List idList,RoomAreaChangeTypeEnum type)
			throws BOSException, EASBizException {
		
		if(idList!=null && idList.size()>0){
			IORMappingDAO roomAreaChange = ORMappingDAO.getInstance(new RoomAreaChangeHisInfo().getBOSType(), ctx, getConnection(ctx));
			for (int i = 0; i < idList.size(); i++) {
				RoomInfo room = (RoomInfo)idList.get(i);
				if(room==null){
					continue;
				}
				RoomAreaChangeHisInfo hisInfo = new RoomAreaChangeHisInfo();
				hisInfo.setHead(room.getId());
				hisInfo.setBuildingArea(room.getBuildingArea());
				hisInfo.setRoomArea(room.getRoomArea());
				hisInfo.setActualBuildingArea(room.getActualBuildingArea());
				hisInfo.setActualRoomArea(room.getActualRoomArea());
				if(type.equals(RoomAreaChangeTypeEnum.ACTUAL)){
					hisInfo.setType(RoomAreaChangeTypeEnum.ACTUAL);
				}else if(type.equals(RoomAreaChangeTypeEnum.PRESALES)){
					hisInfo.setType(RoomAreaChangeTypeEnum.PRESALES);
				}else{
					hisInfo.setType(RoomAreaChangeTypeEnum.ACTUAL);
				}
				hisInfo.setOperationTime(new Date());
				roomAreaChange.addNewBatch(hisInfo);
			}
			
			roomAreaChange.executeBatch();
			
		}
	}

	/**
	 * 更新房间的面积信息
	 */
	protected void _updateAreaInfo(Context ctx, List roomList)
			throws BOSException, EASBizException {
		
		if(roomList!=null && roomList.size()>0){
			try {
				String sql = "update t_she_room set FPlanBuildingArea=?,FPlanRoomArea=?,FBuildingArea=?,FRoomArea=? ,FActualBuildingArea = ?,FActualRoomArea=?,FTenancyArea=?,FSellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomList.size(); i++) {
					RoomInfo info = (RoomInfo) roomList.get(i);
					sqlBuilder.addParam(info.getPlanBuildingArea());
					sqlBuilder.addParam(info.getPlanRoomArea());
					sqlBuilder.addParam(info.getBuildingArea());
					sqlBuilder.addParam(info.getRoomArea());
					sqlBuilder.addParam(info.getActualBuildingArea());
					sqlBuilder.addParam(info.getActualRoomArea());
					sqlBuilder.addParam(info.getTenancyArea());
					if(info.getSellAreaType().equals(SellAreaTypeEnum.PLANNING)){
						sqlBuilder.addParam(SellAreaTypeEnum.PLANNING_VALUE.toString());
					}
					if(info.getSellAreaType().equals(SellAreaTypeEnum.PRESELL)){
						sqlBuilder.addParam(SellAreaTypeEnum.PRESELL_VALUE.toString());			
					}
					if(info.getSellAreaType().equals(SellAreaTypeEnum.ACTUAL)){
						sqlBuilder.addParam(SellAreaTypeEnum.ACTUAL_VALUE.toString());
					}
					
					sqlBuilder.addParam(info.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _actAudit(Context ctx, List roomIdList) throws BOSException,
			EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set FIsActualAreaAudited=?,FIsActAudited=?,FActChangeState=?,FIsChangeSellArea=?,FsellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo  room=  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("1"));
					sqlBuilder.addParam(new Integer("1"));
					if(room.isIsActAudited()){
						sqlBuilder.addParam(RoomActChangeStateEnum.CHANGE_VALUE);
					}else{
						sqlBuilder.addParam(RoomActChangeStateEnum.NOCHANGE_VALUE);
					}
					if(room.isIsChangeSellArea()){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					sqlBuilder.addParam(SellAreaTypeEnum.ACTUAL_VALUE); 
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _actUnAudit(Context ctx, List roomIdList)
			throws BOSException, EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set FIsActualAreaAudited=?,FIsActAudited=?,FsellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo  room=  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("0"));
					sqlBuilder.addParam(new Integer("0"));
					if(room.isIsPreAudited()){
						sqlBuilder.addParam(SellAreaTypeEnum.PRESELL_VALUE);
					}else{
						sqlBuilder.addParam(SellAreaTypeEnum.PLANNING_VALUE);
					}
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _planAudit(Context ctx, List roomIdList)
			throws BOSException, EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set fisplan=?,FIsPlanAudited=?,FPlanChangeState=?,FIsChangeSellArea=?,FsellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo  room=  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("1"));
					sqlBuilder.addParam(new Integer("1"));
					if(room.isIsPlanAudited()){
						sqlBuilder.addParam(RoomPlanChangeStateEnum.CHANGE_VALUE);
					}else{
						sqlBuilder.addParam(RoomPlanChangeStateEnum.NOCHANCE_VALUE);
					}
					if(room.isIsChangeSellArea()){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					if (room.getSellAreaType() != null) {
						if (!room.getSellAreaType().equals(SellAreaTypeEnum.PRESELL) && !room.getSellAreaType().equals(SellAreaTypeEnum.ACTUAL)) {
							sqlBuilder.addParam(SellAreaTypeEnum.PLANNING_VALUE);
						} else if (room.getSellAreaType().equals(SellAreaTypeEnum.PRESELL)) {
							sqlBuilder.addParam(SellAreaTypeEnum.PRESELL_VALUE);
						} else {
							sqlBuilder.addParam(SellAreaTypeEnum.ACTUAL_VALUE);
						}
					}
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _planUnAudit(Context ctx, List roomIdList)
			throws BOSException, EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set fisplan=?,FIsPlanAudited=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo  room=  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("0"));
					sqlBuilder.addParam(new Integer("0"));
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _preAudit(Context ctx, List roomIdList) throws BOSException,
			EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set fispre=?,FIsPreAudited=?,FPreChangeState=?,FIsChangeSellArea=?,FsellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo  room=  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("1"));
					sqlBuilder.addParam(new Integer("1"));
					if(room.isIsPreAudited()){
						sqlBuilder.addParam(RoomPreChangeStateEnum.CHANGE_VALUE);
					}else{
						sqlBuilder.addParam(RoomPreChangeStateEnum.NOCHANGE_VALUE);
					}
					if(room.isIsChangeSellArea()){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					if (room.getSellAreaType() != null) {
						if (!room.getSellAreaType().equals(SellAreaTypeEnum.ACTUAL)) {
							sqlBuilder.addParam(SellAreaTypeEnum.PRESELL_VALUE);
						} else if (room.getSellAreaType().equals(SellAreaTypeEnum.ACTUAL)) {
							sqlBuilder.addParam(SellAreaTypeEnum.ACTUAL_VALUE);
						}
					}
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
	protected void _preUnAudit(Context ctx, List roomIdList)
			throws BOSException, EASBizException {
		if(roomIdList!=null && roomIdList.size()>0){
			try {
				String sql = "update t_she_room set fispre=?,FIsPreAudited=?,FsellAreaType=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomIdList.size(); i++) {
					RoomInfo room  =  (RoomInfo)roomIdList.get(i);
					sqlBuilder.addParam(new Integer("0"));
					sqlBuilder.addParam(new Integer("0"));
					if(!room.isIsActAudited()){
						sqlBuilder.addParam(SellAreaTypeEnum.PLANNING_VALUE); 
					}else{
						sqlBuilder.addParam(SellAreaTypeEnum.ACTUAL_VALUE); 
					}
					sqlBuilder.addParam(room.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
	}
}