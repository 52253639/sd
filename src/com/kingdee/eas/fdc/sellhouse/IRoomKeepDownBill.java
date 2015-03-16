package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IRoomKeepDownBill extends IFDCBill
{
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(String oql) throws BOSException, EASBizException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection() throws BOSException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(EntityViewInfo view) throws BOSException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(String oql) throws BOSException;
    public void cancelKeepDown(String roomId, String billId) throws BOSException, EASBizException;
    public boolean checkRoomKeepDown(String roomId, IObjectValue sheCustomer) throws BOSException, EASBizException;
}