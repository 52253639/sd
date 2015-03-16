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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IRoomPropertyBatchMaterials extends ICoreBillEntryBase
{
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(String oql) throws BOSException, EASBizException;
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection() throws BOSException;
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection(EntityViewInfo view) throws BOSException;
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection(String oql) throws BOSException;
}