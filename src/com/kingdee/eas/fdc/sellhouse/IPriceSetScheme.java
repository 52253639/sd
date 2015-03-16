package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IPriceSetScheme extends IFDCDataBase
{
    public PriceSetSchemeInfo getPriceSetSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PriceSetSchemeInfo getPriceSetSchemeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PriceSetSchemeInfo getPriceSetSchemeInfo(String oql) throws BOSException, EASBizException;
    public PriceSetSchemeCollection getPriceSetSchemeCollection() throws BOSException;
    public PriceSetSchemeCollection getPriceSetSchemeCollection(EntityViewInfo view) throws BOSException;
    public PriceSetSchemeCollection getPriceSetSchemeCollection(String oql) throws BOSException;
    public void setEnabled(List idList, boolean isEnabled) throws BOSException;
}