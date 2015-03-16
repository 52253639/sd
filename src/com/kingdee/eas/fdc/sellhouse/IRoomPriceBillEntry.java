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

public interface IRoomPriceBillEntry extends IDataBase
{
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(String oql) throws BOSException, EASBizException;
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection() throws BOSException;
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection(EntityViewInfo view) throws BOSException;
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection(String oql) throws BOSException;
}