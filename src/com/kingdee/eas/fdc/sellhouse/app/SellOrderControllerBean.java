package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class SellOrderControllerBean extends AbstractSellOrderControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellOrderControllerBean");

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SellOrderInfo order = (SellOrderInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Number, order.getNumber(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (order.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, order
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", order.getProject()
				.getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, order,
					IFWEntityStruct.dataBase_Number)
					+ order.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { number });
		}
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SellOrderInfo order = (SellOrderInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Name, order.getName(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (order.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, order
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", order.getProject()
				.getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, order,
					IFWEntityStruct.dataBase_Name)
					+ order.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
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
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		return super._submit(ctx, model);
	}

	/**
	 * 撤盘,主要进行3点操作：1.这些房间从之前的盘次中删除;2.房间状态由待售更新为未推盘;3.房间的盘次字段清空
	 * */
	protected void _quitOrder(Context ctx, Set quitRoomIds) throws BOSException, EASBizException {
		if(quitRoomIds == null  ||  quitRoomIds.isEmpty()){
			return;
		}
		/* 校验这些房间是否可以撤盘,客户端校验了，简单处理，这里不再校验
		EntityViewInfo view = new EntityViewInfo();
		SellOrderRoomEntryCollection s = SellOrderRoomEntryFactory.getLocalInstance(ctx).getSellOrderRoomEntryCollection(view);
		*/
		
		StringBuffer roomIdFilter = new StringBuffer("(");		
		int counter = 0;
		for(Iterator itor = quitRoomIds.iterator(); itor.hasNext(); ){
			String roomId = (String) itor.next();
			if(counter != 0){
				roomIdFilter.append(",");
			}
			roomIdFilter.append("'");
			roomIdFilter.append(roomId);
			roomIdFilter.append("'");
			counter++;
		}
		roomIdFilter.append(")");
		
		String orderStateSql = "update T_SHE_SellOrderRoomEntry set FState=?, FQuitOrderDate=now() where froomid in " + roomIdFilter;
		DbUtil.execute(ctx, orderStateSql, new Object[] {RoomOrderStateEnum.QUITORDER_VALUE});
		
		String roomSql = "update t_she_room set fsellstate=?,FSellOrderId=? where fsellstate=? and fid in " + roomIdFilter;		
		DbUtil.execute(ctx, roomSql, new Object[] {RoomSellStateEnum.INIT_VALUE, "", RoomSellStateEnum.ONSHOW_VALUE});
	}
	
	/**
	 * 增加在删除单据的时候
	 * 回收number
	 * by renliang at 2010-12-4
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		recycleNumber(ctx, pk);
		
		super._delete(ctx, pk);
		
	}
	
	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		SellOrderInfo info = (SellOrderInfo)_getValue(ctx, pk);
		//------ 销售组织下获取成本中心为空的处理 zhicheng_jin 090319
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);		
		if(currentCostUnit == null){
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		//-------- over --------
		String curOrgId = currentCostUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
}