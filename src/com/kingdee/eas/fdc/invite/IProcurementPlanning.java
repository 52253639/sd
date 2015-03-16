package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.FilterInfo;
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

public interface IProcurementPlanning extends IFDCBill
{
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public ProcurementPlanningInfo getProcurementPlanningInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProcurementPlanningInfo getProcurementPlanningInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProcurementPlanningCollection getProcurementPlanningCollection() throws BOSException;
    public ProcurementPlanningCollection getProcurementPlanningCollection(EntityViewInfo view) throws BOSException;
}