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

public interface ISaleBlanceSet extends IDataBase
{
    public SaleBlanceSetInfo getSaleBlanceSetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleBlanceSetInfo getSaleBlanceSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleBlanceSetInfo getSaleBlanceSetInfo(String oql) throws BOSException, EASBizException;
    public SaleBlanceSetCollection getSaleBlanceSetCollection() throws BOSException;
    public SaleBlanceSetCollection getSaleBlanceSetCollection(EntityViewInfo view) throws BOSException;
    public SaleBlanceSetCollection getSaleBlanceSetCollection(String oql) throws BOSException;
}