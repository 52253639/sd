package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class RoomSignContractControllerBean extends
		AbstractRoomSignContractControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomSignContractControllerBean");

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomSignContractInfo sign = (RoomSignContractInfo) model;
		PurchaseInfo purchase = sign.getPurchase();
		if (purchase.getId() == null) {
			throw new BOSException("can't Sign directly!");
			// purchase.setId(BOSUuid.create(purchase.getBOSType()));
			// PurchaseFactory.getLocalInstance(ctx).addnew(purchase);
			// PurchaseFactory.getLocalInstance(ctx).audit(purchase.getId());
		} else {
//			PurchaseFactory.getLocalInstance(ctx).update(
//					new ObjectUuidPK(purchase.getId()), purchase);
		}
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomSignContractInfo sign = (RoomSignContractInfo) model;
		PurchaseInfo purchase = sign.getPurchase();
		
		
		if (purchase.getId() != null) {
			/** 由于调用了多前台的调用storeField的时候可能会改写认购单的付款明细，由于多处调用，业务复杂，先做如下处理 */
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("currency.*");
			sic.add("moneyDefine.*");

			view.setSelector(sic);

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", purchase.getId().toString()));

			view.setFilter(filter);

			PurchasePayListEntryCollection paylistEntryColls = PurchasePayListEntryFactory
					.getLocalInstance(ctx).getPurchasePayListEntryCollection(
							view);

			purchase.getPayListEntry().clear();
			purchase.getPayListEntry().addCollection(paylistEntryColls);
		}
		
			
		if (purchase.getId() == null) {
			throw new BOSException("can't Sign directly!");
			// purchase.setId(BOSUuid.create(purchase.getBOSType()));
			// PurchaseFactory.getLocalInstance(ctx).addnew(purchase);
			// PurchaseFactory.getLocalInstance(ctx).audit(purchase.getId());
		} else {

			PurchaseFactory.getLocalInstance(ctx).update(new ObjectUuidPK(purchase.getId()), purchase);
		}
		IObjectPK pk = super._submit(ctx, model);
		
		//更改房间的状态,如果认购包含附属房产，还得修改附属房产的房间状态 -zhicheng_jin  090614
		RoomInfo room = sign.getRoom();
		room.setLastSignContract(sign);
		room.setSellState(RoomSellStateEnum.Sign);
		room.setToSignDate(sign.getSignDate());
		
		SelectorItemCollection stColl = new SelectorItemCollection();
		stColl.add("lastSignContract");
		stColl.add("sellState");
		stColl.add("toSignDate");
		
		RoomFactory.getLocalInstance(ctx).updatePartial(room,stColl);
		
		//处理附属房产   
		//TODO 附属房产的成交单价，成交总价是否需要更新。
		PurchaseRoomAttachmentEntryCollection purAttaches = purchase.getAttachmentEntries();
		for(int i=0; i<purAttaches.size(); i++){
			PurchaseRoomAttachmentEntryInfo purAttach = purAttaches.get(i);
			RoomAttachmentEntryInfo roomAttach = purAttach.getAttachmentEntry();
			if(roomAttach == null){
				logger.error("逻辑错误。roomAttach=null,purAttachID:" + purAttach.getId().toString());
				continue;
			}
			RoomInfo attRoom = roomAttach.getRoom();
			if(attRoom == null){
				logger.error("逻辑错误。attRoom=null,roomAttachID:" + roomAttach.getId().toString());
				continue;
			}
			
			attRoom.setSellState(RoomSellStateEnum.Sign);
			attRoom.setToSignDate(sign.getSignDate());
			
			SelectorItemCollection stCol = new SelectorItemCollection();
			stCol.add("sellState");
			stCol.add("toSignDate");
			
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom,stColl);
		}
		
		purchase.setToSignDate(sign.getSignDate());
		SelectorItemCollection purSels = new SelectorItemCollection();
		purSels.add("toSignDate");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, purSels);
		
		return pk;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
	{
		/*RoomSignContractInfo roomSign = (RoomSignContractInfo) this.getValue(ctx,new ObjectUuidPK(billId));
		PurchaseInfo purchase = roomSign.getPurchase();
		RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.Sign,purchase.getId().toString());*/
		super._audit(ctx, billId);
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomSignContractInfo billInfo = (RoomSignContractInfo) model;
		if (billInfo.isIsBlankOut()) {
			return;
		}
		if (billInfo.getNumber() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", billInfo.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("isBlankOut", Boolean.FALSE));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
							.getId()));
			if (billInfo.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", billInfo.getId().toString(),
								CompareType.NOTEQUALS));
			}
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.NUMBER_DUP);
			}
		}
		// filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("name", billInfo.getName()));
		// filter.getFilterItems().add(
		// new FilterItemInfo("isBlankOut", Boolean.FALSE));
		// filter.getFilterItems()
		// .add(
		// new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
		// .getId()));
		// if (billInfo.getId() != null) {
		// filter.getFilterItems().add(
		// new FilterItemInfo("id", billInfo.getId().toString(),
		// CompareType.NOTEQUALS));
		// }
		// if (_exists(ctx, filter)) {
		// throw new ContractException(ContractException.NAME_DUP);
		// }
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
	
	/**
	 * 备案
	 * */
	protected void _onRecord(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo) getValue(ctx, pks[i]);
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsOnRecord(true);
			contract.setOnRecordDate(new Date());
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isOnRecord");
			sels.add("onRecordDate");
			_updatePartial(ctx, contract, sels);
		}
	}

	/**
	 * 领取
	 */
	protected void _pullDown(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsPullDown(true);
			contract.setPullDownDate(new Date());
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isPullDown");
			selector.add("pullDownDate");
			_updatePartial(ctx, contract, selector);
		}
		
	}
	
	/**
	 * 签章
	 */
	protected void _stamp(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsStamp(true);
			contract.setStampDate(new Date());
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isStamp");
			selector.add("stampDate");
			_updatePartial(ctx, contract, selector);
		}
	}
	
	/**
	 * 反审批
	 */
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		RoomSignContractInfo contract = (RoomSignContractInfo)this.getValue(ctx, new ObjectUuidPK(billId));
		contract.setIsOnRecord(false);
		contract.setOnRecordDate(null);
		contract.setIsStamp(false);
		contract.setStampDate(null);
		contract.setIsPullDown(false);
		contract.setPullDownDate(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isOnRecord");
		selector.add("onRecordDate");
		selector.add("isStamp");
		selector.add("stampDate");
		selector.add("isPullDown");
		selector.add("pullDownDate");
		RoomSignContractFactory.getLocalInstance(ctx).updatePartial(contract, selector);
		super._unAudit(ctx, billId);
	}
	/**
	 * 备案
	 * */
	protected void _onRecord(Context ctx, IObjectPK[] pks, Date RecordDate) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo) getValue(ctx, pks[i]);
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsOnRecord(true);
			contract.setOnRecordDate(RecordDate);
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isOnRecord");
			sels.add("onRecordDate");
			_updatePartial(ctx, contract, sels);
		}
		
	}

	/**
	 * 领取
	 */
	protected void _pullDown(Context ctx, IObjectPK[] pks, Date pullDownDate) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsPullDown(true);
			contract.setPullDownDate(pullDownDate);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isPullDown");
			selector.add("pullDownDate");
			_updatePartial(ctx, contract, selector);
		}
	}
	/**
	 * 签章
	 */
	protected void _stamp(Context ctx, IObjectPK[] pks, Date stampDate) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsStamp(true);
			contract.setStampDate(stampDate);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isStamp");
			selector.add("stampDate");
			_updatePartial(ctx, contract, selector);
		}
		
	}
	/**
	 * 取消备案
	 * @throws EASBizException 
	 * */
	protected void _unOnRecord(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo) getValue(ctx, pks[i]);
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsOnRecord(false);
			contract.setOnRecordDate(null);	
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isOnRecord");
			sels.add("onRecordDate");
			_updatePartial(ctx, contract, sels);
		}
		
		
	}

	/**
	 * 取消领取
	 * @throws EASBizException 
	 */
	protected void _unPullDown(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsPullDown(false);
			contract.setPullDownDate(null);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isPullDown");
			selector.add("pullDownDate");
			_updatePartial(ctx, contract, selector);
		}
	}
	/**
	 * 取消签章
	 * @throws EASBizException 
	 */
	protected void _unStamp(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo)getValue(ctx, pks[i]);
			//判断是否已审批
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsStamp(false);
			contract.setStampDate(null);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isStamp");
			selector.add("stampDate");
			_updatePartial(ctx, contract, selector);
		}
		
	}
//再次重做备案。
	protected void _onRecord(Context ctx, IObjectPK[] pks, Date RecordDate, String contractNumber) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		for(int i=0; i<pks.length; i++){
			RoomSignContractInfo contract = (RoomSignContractInfo) getValue(ctx, pks[i]);
			if(!FDCBillStateEnum.AUDITTED.equals(contract.getState())){
				continue;
			}
			contract.setIsOnRecord(true);
			contract.setOnRecordDate(RecordDate);
			if(!"".equals(contractNumber) && contractNumber!=null){
			contract.setContractNumber(contractNumber);
			}
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isOnRecord");
			sels.add("onRecordDate");
			sels.add("contractNumber");
			_updatePartial(ctx, contract, sels);
		}
	}

	protected void _receiveBill(Context ctx, BOSUuid billID)
			throws BOSException, EASBizException {		
	}
}