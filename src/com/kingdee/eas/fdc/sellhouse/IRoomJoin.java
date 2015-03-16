package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IRoomJoin extends IFDCBill
{
    public RoomJoinInfo getRoomJoinInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomJoinInfo getRoomJoinInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomJoinInfo getRoomJoinInfo(String oql) throws BOSException, EASBizException;
    public RoomJoinCollection getRoomJoinCollection(EntityViewInfo view) throws BOSException;
    public RoomJoinCollection getRoomJoinCollection() throws BOSException;
    public RoomJoinCollection getRoomJoinCollection(String oql) throws BOSException;
    public void batchSave(RoomJoinCollection roomJoins) throws BOSException, EASBizException;
}