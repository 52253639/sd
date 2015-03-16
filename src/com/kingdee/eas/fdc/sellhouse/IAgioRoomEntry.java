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

public interface IAgioRoomEntry extends IDataBase
{
    public AgioRoomEntryInfo getAgioRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AgioRoomEntryInfo getAgioRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AgioRoomEntryInfo getAgioRoomEntryInfo(String oql) throws BOSException, EASBizException;
    public AgioRoomEntryCollection getAgioRoomEntryCollection() throws BOSException;
    public AgioRoomEntryCollection getAgioRoomEntryCollection(EntityViewInfo view) throws BOSException;
    public AgioRoomEntryCollection getAgioRoomEntryCollection(String oql) throws BOSException;
}