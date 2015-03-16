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
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface IAcctConCost extends ICoreBase
{
    public AcctConCostInfo getAcctConCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AcctConCostInfo getAcctConCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AcctConCostInfo getAcctConCostInfo(String oql) throws BOSException, EASBizException;
    public void update(IObjectPK pk, AcctConCostInfo model) throws BOSException, EASBizException;
    public void updatePartial(AcctConCostInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, AcctConCostInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public AcctConCostCollection getAcctConCostCollection() throws BOSException;
    public AcctConCostCollection getAcctConCostCollection(EntityViewInfo view) throws BOSException;
    public AcctConCostCollection getAcctConCostCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}