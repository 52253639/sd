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

public interface IHandleRoomEntrys extends IDataBase
{
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(String oql) throws BOSException, EASBizException;
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection() throws BOSException;
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection(EntityViewInfo view) throws BOSException;
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection(String oql) throws BOSException;
}