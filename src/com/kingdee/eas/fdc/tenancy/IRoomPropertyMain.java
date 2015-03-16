package com.kingdee.eas.fdc.tenancy;

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

public interface IRoomPropertyMain extends IDataBase
{
    public RoomPropertyMainInfo getRoomPropertyMainInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomPropertyMainInfo getRoomPropertyMainInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPropertyMainInfo getRoomPropertyMainInfo(String oql) throws BOSException, EASBizException;
    public RoomPropertyMainCollection getRoomPropertyMainCollection(EntityViewInfo view) throws BOSException;
    public RoomPropertyMainCollection getRoomPropertyMainCollection(String oql) throws BOSException;
    public RoomPropertyMainCollection getRoomPropertyMainCollection() throws BOSException;
}