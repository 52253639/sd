package com.kingdee.eas.fdc.invite;

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

public interface IRefPrice extends ICoreBase
{
    public RefPriceInfo getRefPriceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RefPriceInfo getRefPriceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RefPriceInfo getRefPriceInfo(String oql) throws BOSException, EASBizException;
    public RefPriceCollection getRefPriceCollection() throws BOSException;
    public RefPriceCollection getRefPriceCollection(EntityViewInfo view) throws BOSException;
    public RefPriceCollection getRefPriceCollection(String oql) throws BOSException;
}