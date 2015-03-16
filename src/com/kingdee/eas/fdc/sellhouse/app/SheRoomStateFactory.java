package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;


/**
 * 售楼系统中专用的
 * 改变房间状态时调用
 * @author jeegan_wang
 */
public class SheRoomStateFactory {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomStateFactory");
	
	/**收款/退款时把房间状态改成预订状态
	 * 来源： 房间由待售->预订
	 * 		  房间由认购->预订	
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @bizDate 收款单的业务日期 （转预订、认购等时间要以此为准）,退款时可以为空
	 * 	 	
	 * */
	public static void setRoomPrePurState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,Date bizDate) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		if(bizDate==null) bizDate = new Date();
		
		//TODO 诚意认购状态变为预定，刘威说暂不考虑退款和删除收款单情况，所以没有写预定或者认购状态变成诚意认购的代码
		if(oldSellState.equals(RoomSellStateEnum.OnShow) 
				|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //待售->预订
			roomInfo.setSellState(RoomSellStateEnum.PrePurchase);
			roomInfo.setToPrePurchaseDate(bizDate);
			roomInfo.setToSaleDate(bizDate);
			roomInfo.setLastPurchase(purInfo);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPrePurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("lastPurchase");
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//同时也修改认购单上的相应时间
			purInfo.setToPrePurchaseDate(bizDate);
			purInfo.setToSaleDate(bizDate);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("toPrePurchaseDate");
			purUpdateSel.add("toSaleDate");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		}else if(oldSellState.equals(RoomSellStateEnum.Purchase)) { //认购->预订(认购之前就是预订状态的情况)
			roomInfo.setToPurchaseDate(null);
			roomInfo.setSellState(RoomSellStateEnum.PrePurchase);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");		
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//同时也修改认购单上的相应时间
			purInfo.setToPurchaseDate(null);			
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("ToPurchaseDate");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);			
		}

		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	
	/**收款时把房间状态改成认购状态
	 * 来源： 房间由待售/预订->认购
	 * @bizDate 收款单的业务日期 （转预订、认购等时间要以此为准） 
	 * */
	public static void setRoomPurState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,Date bizDate) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		if(bizDate==null) bizDate = new Date();
		
		if(oldSellState.equals(RoomSellStateEnum.OnShow) 
				|| oldSellState.equals(RoomSellStateEnum.SincerPurchase) 
				|| oldSellState.equals(RoomSellStateEnum.PrePurchase)) { //待售/预订/选房 ->认购
			roomInfo.setSellState(RoomSellStateEnum.Purchase);	//变认购状态
			roomInfo.setToPurchaseDate(bizDate);
			roomInfo.setDealPrice(purInfo.getDealPrice());
			roomInfo.setDealTotalAmount(purInfo.getDealAmount());
			roomInfo.setLastPurchase(purInfo);
			BigDecimal compensateAmount = roomInfo.getAreaCompensateAmount();
			if(compensateAmount == null)	compensateAmount = new BigDecimal(0);
			BigDecimal dealAmount = purInfo.getDealAmount();
			if(dealAmount == null)	dealAmount = new BigDecimal(0);
			roomInfo.setSellAmount(dealAmount.add(compensateAmount));
			//根据认购单的现售和预售修改销售面积
			if(purInfo.getSellType()!=null && SellTypeEnum.PreSell.equals(purInfo.getSellType())){
				roomInfo.setSaleArea(roomInfo.getBuildingArea());
			}else{
				roomInfo.setSaleArea(roomInfo.getActualBuildingArea());
			}
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");		
			roomUpdateSel.add("lastPurchase");
			if(oldSellState.equals(RoomSellStateEnum.OnShow) 
					|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //待售转认购时要跟新转销售时间
				roomInfo.setToSaleDate(bizDate);  //待售转认购时要跟新转销售时间
				roomUpdateSel.add("toSaleDate"); 
			}	
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//同时也修改认购单上的相应时间
			purInfo.setToPurchaseDate(bizDate);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("toPurchaseDate");
			if(oldSellState.equals(RoomSellStateEnum.OnShow) 
					|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //待售转认购时要跟新转销售时间
				purInfo.setToSaleDate(bizDate);
				purUpdateSel.add("toSaleDate");
			}
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		}
		
		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	
	/**收款时把房间状态改成待售状态
	 * 来源： 房间由预订/认购->待售
	 * isQuitRoom 是否是退房业务
	 * isAdjust 是否是调整业务	 
	 * */
	public static void setRoomOnShowState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,boolean isQuitRoom, boolean isAdjust) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		
		if(oldSellState.equals(RoomSellStateEnum.Purchase) || oldSellState.equals(RoomSellStateEnum.PrePurchase) 
				|| oldSellState.equals(RoomSellStateEnum.Sign) ) { //预定\认购\签约->待售
			roomInfo.setSellState(RoomSellStateEnum.OnShow);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			roomInfo.setToPurchaseDate(null);
			roomInfo.setToSaleDate(null);
			roomInfo.setToPrePurchaseDate(null);
			roomInfo.setToSignDate(null);
			roomInfo.setLastPurchase(null);
			roomInfo.setLastSignContract(null);
			roomInfo.setLastAreaCompensate(null);
			roomInfo.setAreaCompensateAmount(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("toPrePurchase");
			roomUpdateSel.add("toSignDate");
			roomUpdateSel.add("lastPurchase");
			roomUpdateSel.add("lastSignContract");
			roomUpdateSel.add("lastAreaCompensate");
			roomUpdateSel.add("areaCompensateAmount");
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
		}
		
		//同时也修改认购单上的相应时间
		SelectorItemCollection purUpdateSel = new SelectorItemCollection();
		if(isQuitRoom) {
			purInfo.setPurchaseState(PurchaseStateEnum.QuitRoomBlankOut);
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			purInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("purchaseState");
			purUpdateSel.add("state");
			purUpdateSel.add("lastUpdateUser");
			purUpdateSel.add("lastUpdateTime");
		}else if(isAdjust){
			purInfo.setPurchaseState(PurchaseStateEnum.AdjustBlankOut);
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			purInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("purchaseState");
			purUpdateSel.add("state");
			purUpdateSel.add("lastUpdateUser");
			purUpdateSel.add("lastUpdateTime");
		}else{//预订\认购->待售
			purInfo.setToPrePurchaseDate(null);
			purInfo.setToSaleDate(null);
			purInfo.setToPurchaseDate(null);
			purUpdateSel.add("toPrePurchaseDate");
			purUpdateSel.add("toSaleDate");	
			purUpdateSel.add("toPurchaseDate");
		}
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		
		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	/**
	 * 更改绑定房间属性
	 * 主要是根据主房间的属性来变化的
	 */
	public static void updateForRoomAttachs(Context ctx,PurchaseInfo purInfo,RoomInfo mainRoom) throws EASBizException, BOSException {
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
				.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection("select *,attachmentEntry.room.* " +
						"where attachmentEntry.head.id='"+purInfo.getRoom().getId()+"' and head.id ='"+purInfo.getId()+"' ");
		
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(mainRoom.getSellState());
			aRoom.setToPrePurchaseDate(mainRoom.getToPrePurchaseDate());
			aRoom.setToPurchaseDate(mainRoom.getToPurchaseDate());
			aRoom.setToSignDate(mainRoom.getToSignDate());
			aRoom.setToSaleDate(mainRoom.getToSaleDate());
			aRoom.setLastPurchase(mainRoom.getLastPurchase());
			aRoom.setLastSignContract(mainRoom.getLastSignContract());

			if(mainRoom.getSellState().equals(RoomSellStateEnum.OnShow)) {
				aRoom.setDealTotalAmount(null);
				aRoom.setSellAmount(null);
				aRoom.setDealPrice(null);
			}else{
				BigDecimal mergeAmount = info.getMergeAmount();
				if (mergeAmount == null) {
					mergeAmount = FDCHelper.ZERO;
				}
				aRoom.setDealTotalAmount(mergeAmount);
				aRoom.setSellAmount(mergeAmount);
				boolean isCalByRoomArea = mainRoom.isIsCalByRoomArea();
				BigDecimal area = null;
				if (isCalByRoomArea) { // 如果使现售,成交单价按照实测面积计算				
					if (SellTypeEnum.LocaleSell.equals(purInfo.getSellType())) {
						area = mainRoom.getActualRoomArea();
					} else {
						area = mainRoom.getRoomArea();
					}
				} else {
					if (SellTypeEnum.LocaleSell.equals(purInfo.getSellType())) {
						area = mainRoom.getActualBuildingArea();
					} else {
						area = mainRoom.getBuildingArea();
					}
				}
				if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
					aRoom.setDealPrice(mergeAmount.divide(area, 2,	BigDecimal.ROUND_HALF_UP));
				}
			}
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			sels.add("dealPrice");
			sels.add("dealTotalAmount");
			sels.add("sellAmount");
			sels.add("toSignDate");
			sels.add("toPrePurchaseDate");
			sels.add("toPurchaseDate");
			sels.add("toSaleDate");
			sels.add("lastPurchase");
			sels.add("lastSignContract");
			RoomFactory.getLocalInstance(ctx).updatePartial(aRoom, sels);
		}
	}
	
	
	

}
