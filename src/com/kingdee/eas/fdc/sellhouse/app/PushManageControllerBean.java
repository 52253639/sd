package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.util.app.DbUtil;

public class PushManageControllerBean extends AbstractPushManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PushManageControllerBean");
    
	/**
	 * 重写校验:
	 * 1.不检查编码名称是否为空。 2.不检查编码名称是否重复。
	 */
	protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
	
	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
	}


	protected void _executed(Context ctx, Set pushRoomIds) throws BOSException {
		if(pushRoomIds == null  ||  pushRoomIds.isEmpty()){
			return;
		}
		StringBuffer roomIdFilter = new StringBuffer("(");		
		int counter = 0;
		for(Iterator itor = pushRoomIds.iterator(); itor.hasNext(); ){
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
		DbUtil.execute(ctx, orderStateSql, new Object[] {RoomOrderStateEnum.EXECUTED_VALUE});
		
		String roomSql = "update t_she_room set fsellstate=?,FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter+" and fsellstate!='KeepDown'";		
		DbUtil.execute(ctx, roomSql, new Object[] {RoomSellStateEnum.ONSHOW_VALUE,"",Boolean.TRUE});
		
		String roomkeepDownSql = "update t_she_room set FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter+" and fsellstate='KeepDown'";		
		DbUtil.execute(ctx, roomkeepDownSql, new Object[] {"",Boolean.TRUE});
	}

	protected void _quitOrder(Context ctx, Set quitRoomIds) throws BOSException {
		if(quitRoomIds == null  ||  quitRoomIds.isEmpty()){
			return;
		}
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
		
		String roomSql = "update t_she_room set fsellstate=?,FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter;		
		DbUtil.execute(ctx, roomSql, new Object[] {RoomSellStateEnum.INIT_VALUE, "",Boolean.FALSE});
		
	}
}