package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IYearSale extends ICoreBillEntryBase
{
    public YearSaleInfo getYearSaleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public YearSaleInfo getYearSaleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public YearSaleInfo getYearSaleInfo(String oql) throws BOSException, EASBizException;
    public YearSaleCollection getYearSaleCollection() throws BOSException;
    public YearSaleCollection getYearSaleCollection(EntityViewInfo view) throws BOSException;
    public YearSaleCollection getYearSaleCollection(String oql) throws BOSException;
}