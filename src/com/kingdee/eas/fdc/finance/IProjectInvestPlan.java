package com.kingdee.eas.fdc.finance;

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

public interface IProjectInvestPlan extends IFDCBill
{
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(String oql) throws BOSException, EASBizException;
    public ProjectInvestPlanCollection getProjectInvestPlanCollection() throws BOSException;
    public ProjectInvestPlanCollection getProjectInvestPlanCollection(EntityViewInfo view) throws BOSException;
    public ProjectInvestPlanCollection getProjectInvestPlanCollection(String oql) throws BOSException;
    public void setPublish(String id) throws BOSException;
}