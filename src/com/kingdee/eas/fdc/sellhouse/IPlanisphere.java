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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPlanisphere extends IFDCBill
{
    public PlanisphereInfo getPlanisphereInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PlanisphereInfo getPlanisphereInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PlanisphereInfo getPlanisphereInfo(String oql) throws BOSException, EASBizException;
    public PlanisphereCollection getPlanisphereCollection() throws BOSException;
    public PlanisphereCollection getPlanisphereCollection(EntityViewInfo view) throws BOSException;
    public PlanisphereCollection getPlanisphereCollection(String oql) throws BOSException;
}