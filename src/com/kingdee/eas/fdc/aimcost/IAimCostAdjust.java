package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAimCostAdjust extends IFDCBill
{
    public AimCostAdjustInfo getAimCostAdjustInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AimCostAdjustInfo getAimCostAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AimCostAdjustInfo getAimCostAdjustInfo(String oql) throws BOSException, EASBizException;
    public AimCostAdjustCollection getAimCostAdjustCollection() throws BOSException;
    public AimCostAdjustCollection getAimCostAdjustCollection(EntityViewInfo view) throws BOSException;
    public AimCostAdjustCollection getAimCostAdjustCollection(String oql) throws BOSException;
}