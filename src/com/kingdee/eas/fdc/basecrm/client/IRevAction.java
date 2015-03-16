package com.kingdee.eas.fdc.basecrm.client;

import java.math.BigDecimal;
import java.util.List;

import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

/**
 * 收款单客户端辅助处理扩展接口
 * */
public interface IRevAction {
	/**
	 * 用于新增直收收款时生成一条收款明细值对象
	 * */
	IRevListInfo createRevListInfo();
	
	/**
	 * 用于实现各业务系统在分录和选择界面的扩展列
	 * */
	List getExpandCols(RevListTypeEnum revListType);
	
	/**
	 * 根据付款明细得到房间
	 * */
	RoomInfo getRoomInfoByRevList(IRevListInfo revListInfo);
	
	/**
	 * 根据付款明细获得减免金额
	 * */
	BigDecimal getRemissionAmountRevList(IRevListInfo revListInfo);
}
