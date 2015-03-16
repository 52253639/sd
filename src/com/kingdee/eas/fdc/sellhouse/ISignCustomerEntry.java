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

public interface ISignCustomerEntry extends ITranCustomerEntry
{
    public SignCustomerEntryCollection getSignCustomerEntryCollection(EntityViewInfo view) throws BOSException;
    public SignCustomerEntryCollection getSignCustomerEntryCollection() throws BOSException;
    public SignCustomerEntryCollection getSignCustomerEntryCollection(String oql) throws BOSException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(String oql) throws BOSException, EASBizException;
}