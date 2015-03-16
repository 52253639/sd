package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public interface IProjectPeriodStatus extends ICoreBase
{
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(String oql) throws BOSException, EASBizException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(EntityViewInfo view) throws BOSException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection() throws BOSException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(String oql) throws BOSException;
    public Map closeInit(List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException;
    public void closeProject(List projectIds) throws BOSException, EASBizException;
    public Map closeAll(String orgUnit, boolean isCompany) throws BOSException, EASBizException;
    public Map closeUnInit(List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException;
    public void closeUnProject(List projectIds) throws BOSException, EASBizException;
    public Map closeUnAll(String orgUnit, boolean isCompany) throws BOSException, EASBizException;
    public PeriodInfo fetchPeriod(String projectId, Date bookedDate, boolean isCost) throws BOSException, EASBizException;
    public Map fetchInitData(String curOrgId, boolean isCompany) throws BOSException, EASBizException;
    public void paramCheck(String comId) throws BOSException, EASBizException;
}