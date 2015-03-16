package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomDealAmountError implements IError {
	public String getDes() {
		return "房间的成交数据不等于合同上的数据!";
	}

	//暂不实现
	public List getHandles() {
		List list = new ArrayList();
		list.add(new HandleRoomDealAmountErrorData1());
		list.add(new HandleRoomDealAmountErrorData2());
		
		return list;
	}

	public List matchErrorDatas() throws BOSException, SQLException {
		String sql = "select a.fid roomId,b.fid purchaseId from t_she_room a inner join t_she_purchase b on a.flastpurchaseid=b.fid where a.fdealtotalamount is null or b.fdealamount is null or a.fDealTotalAmount!=b.fdealamount";
		
		IRowSet rowSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		List list = new ArrayList();
		while(rowSet.next()){
			String roomId = rowSet.getString("roomId");
			String purchaseId = rowSet.getString("purchaseId");
			
			list.add(new RoomDealAmountErrorData());
		}
		return list;
	}
}

class RoomDealAmountErrorData implements IErrorData{
	private RoomInfo room;
	private PurchaseInfo purchase;
	
	public RoomDealAmountErrorData(RoomInfo room, PurchaseInfo purchase){
		this.room = room;
		this.purchase = purchase;
	}
	public RoomDealAmountErrorData(){
	}
	
	public Map getParams() {
		Map map = new HashMap();
		map.put("room", room);
		map.put("purchase", purchase);
		
		return map;
	}

	public RoomInfo getRoom() {
		return room;
	}
	public void setRoom(RoomInfo room) {
		this.room = room;
	}
	public PurchaseInfo getPurchase() {
		return purchase;
	}
	public void setPurchase(PurchaseInfo purchase) {
		this.purchase = purchase;
	}
}

//以下不实现
class HandleRoomDealAmountErrorData1 implements IHandleErrorData{
	public IHandleReport handleErrorData(IErrorData errorData) throws EASBizException, BOSException {
		Map params = errorData.getParams();
		RoomInfo room = (RoomInfo) params.get("room");
		PurchaseInfo purchase = (PurchaseInfo) params.get("purchase");
		
		BigDecimal purDealAmount = purchase.getDealAmount();
		room.setDealTotalAmount(purDealAmount);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("room");
		RoomFactory.getRemoteInstance().updatePartial(room, sels);
		
		return null;
	}

	public String getDes() {
		return "将合同上的成交金额更新到房间上!";
	}
}

class HandleRoomDealAmountErrorData2 implements IHandleErrorData{
	public IHandleReport handleErrorData(IErrorData errorData) throws EASBizException, BOSException {
		Map params = errorData.getParams();
		RoomInfo room = (RoomInfo) params.get("room");
		RoomFactory.getRemoteInstance().reclaimRoom(room.getId().toString());
		return null;
	}

	public String getDes() {
		return "将该房间回收!";
	}
}

