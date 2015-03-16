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

public interface IPurCustomerEntry extends ITranCustomerEntry
{
    public PurCustomerEntryInfo getPurCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurCustomerEntryInfo getPurCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurCustomerEntryInfo getPurCustomerEntryInfo(String oql) throws BOSException, EASBizException;
    public PurCustomerEntryCollection getPurCustomerEntryCollection() throws BOSException;
    public PurCustomerEntryCollection getPurCustomerEntryCollection(EntityViewInfo view) throws BOSException;
    public PurCustomerEntryCollection getPurCustomerEntryCollection(String oql) throws BOSException;
}