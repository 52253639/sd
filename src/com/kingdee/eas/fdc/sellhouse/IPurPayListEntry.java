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

public interface IPurPayListEntry extends ITranPayListEntry
{
    public PurPayListEntryInfo getPurPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurPayListEntryInfo getPurPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurPayListEntryInfo getPurPayListEntryInfo(String oql) throws BOSException, EASBizException;
    public PurPayListEntryCollection getPurPayListEntryCollection() throws BOSException;
    public PurPayListEntryCollection getPurPayListEntryCollection(EntityViewInfo view) throws BOSException;
    public PurPayListEntryCollection getPurPayListEntryCollection(String oql) throws BOSException;
}