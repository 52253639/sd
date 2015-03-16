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
import java.util.Map;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IPayPlan extends IBillBase
{
    public PayPlanInfo getPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PayPlanInfo getPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PayPlanInfo getPayPlanInfo(String oql) throws BOSException, EASBizException;
    public PayPlanCollection getPayPlanCollection() throws BOSException;
    public PayPlanCollection getPayPlanCollection(EntityViewInfo view) throws BOSException;
    public PayPlanCollection getPayPlanCollection(String oql) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public Map getData(String orgUnitId, Map param) throws BOSException, EASBizException;
    public Map getData(Set orgIds, Map param) throws BOSException, EASBizException;
    public Map getData(Set orgIds, Set conTypeIdS, Map param) throws BOSException, EASBizException;
    public Map getData(String orgUnitId, Set conTypeIds, Map param) throws BOSException, EASBizException;
    public void audit(Map param) throws BOSException, EASBizException;
    public void unAudit(Map param) throws BOSException, EASBizException;
}