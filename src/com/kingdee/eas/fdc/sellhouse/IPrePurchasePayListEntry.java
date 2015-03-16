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

public interface IPrePurchasePayListEntry extends ITranPayListEntry
{
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection() throws BOSException;
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(EntityViewInfo view) throws BOSException;
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(String oql) throws BOSException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PrePurchasePayListEntryInfo model) throws BOSException, EASBizException;
}