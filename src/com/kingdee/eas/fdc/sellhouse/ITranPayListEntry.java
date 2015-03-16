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

public interface ITranPayListEntry extends ICoreBase
{
    public TranPayListEntryInfo getTranPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TranPayListEntryInfo getTranPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TranPayListEntryInfo getTranPayListEntryInfo(String oql) throws BOSException, EASBizException;
    public TranPayListEntryCollection getTranPayListEntryCollection() throws BOSException;
    public TranPayListEntryCollection getTranPayListEntryCollection(EntityViewInfo view) throws BOSException;
    public TranPayListEntryCollection getTranPayListEntryCollection(String oql) throws BOSException;
}