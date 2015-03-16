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

public interface IPriceSetSchemeEntry extends ICoreBase
{
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(String oql) throws BOSException, EASBizException;
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection(EntityViewInfo view) throws BOSException;
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection() throws BOSException;
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection(String oql) throws BOSException;
}