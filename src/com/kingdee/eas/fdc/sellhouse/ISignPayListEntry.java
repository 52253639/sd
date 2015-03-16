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

public interface ISignPayListEntry extends ITranPayListEntry
{
    public SignPayListEntryInfo getSignPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SignPayListEntryInfo getSignPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SignPayListEntryInfo getSignPayListEntryInfo(String oql) throws BOSException, EASBizException;
    public SignPayListEntryCollection getSignPayListEntryCollection() throws BOSException;
    public SignPayListEntryCollection getSignPayListEntryCollection(EntityViewInfo view) throws BOSException;
    public SignPayListEntryCollection getSignPayListEntryCollection(String oql) throws BOSException;
}