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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPriceAdjustEntry extends IDataBase
{
    public PriceAdjustEntryInfo getPriceAdjustEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PriceAdjustEntryInfo getPriceAdjustEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PriceAdjustEntryInfo model) throws BOSException, EASBizException;
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection() throws BOSException;
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection(EntityViewInfo view) throws BOSException;
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection(String oql) throws BOSException;
}