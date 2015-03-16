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
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPurchaseElsePayListEntry extends IRevList
{
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(String oql) throws BOSException, EASBizException;
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection() throws BOSException;
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection(EntityViewInfo view) throws BOSException;
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection(String oql) throws BOSException;
}