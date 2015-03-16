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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IRoomRecoverHistory extends IDataBase
{
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(String oql) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, RoomRecoverHistoryInfo model) throws BOSException, EASBizException;
    public IObjectPK addnew(RoomRecoverHistoryInfo model) throws BOSException, EASBizException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(String oql) throws BOSException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(EntityViewInfo view) throws BOSException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection() throws BOSException;
}