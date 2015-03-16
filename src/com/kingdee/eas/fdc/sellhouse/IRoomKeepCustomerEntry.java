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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IRoomKeepCustomerEntry extends IBillEntryBase
{
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(String oql) throws BOSException, EASBizException;
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection() throws BOSException;
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection(EntityViewInfo view) throws BOSException;
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection(String oql) throws BOSException;
}