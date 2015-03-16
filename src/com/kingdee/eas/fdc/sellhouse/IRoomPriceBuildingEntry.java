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

public interface IRoomPriceBuildingEntry extends IDataBase
{
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException;
    public void updatePartial(RoomPriceBuildingEntryInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection() throws BOSException;
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection(EntityViewInfo view) throws BOSException;
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection(String oql) throws BOSException;
}