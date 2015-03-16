package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IHandleTenancy extends ITenBillBase
{
    public HandleTenancyInfo getHandleTenancyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HandleTenancyInfo getHandleTenancyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HandleTenancyInfo getHandleTenancyInfo(String oql) throws BOSException, EASBizException;
    public HandleTenancyCollection getHandleTenancyCollection() throws BOSException;
    public HandleTenancyCollection getHandleTenancyCollection(EntityViewInfo view) throws BOSException;
    public HandleTenancyCollection getHandleTenancyCollection(String oql) throws BOSException;
    public void handleTenancyRoom(IObjectCollection handleRoomEntryColl, IObjectValue tenancyBillInfo) throws BOSException;
}