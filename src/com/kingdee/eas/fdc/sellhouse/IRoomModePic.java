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
import com.kingdee.eas.framework.ICoreBase;

public interface IRoomModePic extends ICoreBase
{
    public RoomModePicInfo getRoomModePicInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomModePicInfo getRoomModePicInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomModePicInfo getRoomModePicInfo(String oql) throws BOSException, EASBizException;
    public RoomModePicCollection getRoomModePicCollection() throws BOSException;
    public RoomModePicCollection getRoomModePicCollection(EntityViewInfo view) throws BOSException;
    public RoomModePicCollection getRoomModePicCollection(String oql) throws BOSException;
}