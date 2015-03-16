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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISinObligateRoomsEntry extends ICoreBillBase
{
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(String oql) throws BOSException, EASBizException;
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection() throws BOSException;
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection(EntityViewInfo view) throws BOSException;
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection(String oql) throws BOSException;
}