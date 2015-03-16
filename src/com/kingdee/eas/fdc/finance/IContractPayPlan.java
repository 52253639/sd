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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IContractPayPlan extends IBillBase
{
    public ContractPayPlanInfo getContractPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractPayPlanInfo getContractPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractPayPlanInfo getContractPayPlanInfo(String oql) throws BOSException, EASBizException;
    public ContractPayPlanCollection getContractPayPlanCollection(EntityViewInfo view) throws BOSException;
    public ContractPayPlanCollection getContractPayPlanCollection() throws BOSException;
    public ContractPayPlanCollection getContractPayPlanCollection(String oql) throws BOSException;
    public boolean submitCol(String contractId, IObjectCollection planCol) throws BOSException, EASBizException;
}