package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface ISettledMonthly extends ICoreBase
{
    public SettledMonthlyInfo getSettledMonthlyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SettledMonthlyInfo getSettledMonthlyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SettledMonthlyInfo getSettledMonthlyInfo(String oql) throws BOSException, EASBizException;
    public SettledMonthlyCollection getSettledMonthlyCollection() throws BOSException;
    public SettledMonthlyCollection getSettledMonthlyCollection(EntityViewInfo view) throws BOSException;
    public SettledMonthlyCollection getSettledMonthlyCollection(String oql) throws BOSException;
    public Result addnew(CoreBaseCollection colls) throws BOSException, EASBizException;
}