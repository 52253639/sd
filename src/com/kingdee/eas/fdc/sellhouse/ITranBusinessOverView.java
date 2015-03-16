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

public interface ITranBusinessOverView extends ITranPayListEntry
{
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(String oql) throws BOSException, EASBizException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection() throws BOSException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(EntityViewInfo view) throws BOSException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(String oql) throws BOSException;
}