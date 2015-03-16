package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface IRefPriceEntry extends ICoreBase
{
    public RefPriceEntryInfo getRefPriceEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RefPriceEntryInfo getRefPriceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RefPriceEntryInfo getRefPriceEntryInfo(String oql) throws BOSException, EASBizException;
    public RefPriceEntryCollection getRefPriceEntryCollection() throws BOSException;
    public RefPriceEntryCollection getRefPriceEntryCollection(EntityViewInfo view) throws BOSException;
    public RefPriceEntryCollection getRefPriceEntryCollection(String oql) throws BOSException;
    public RefPriceEntryCollection getCollection(EntityViewInfo view, HashMap cols) throws BOSException;
}