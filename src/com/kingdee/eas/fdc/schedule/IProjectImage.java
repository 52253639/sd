package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;

public interface IProjectImage extends IScheduleBase
{
    public ProjectImageCollection getProjectImageCollection(String oql) throws BOSException;
    public ProjectImageCollection getProjectImageCollection(EntityViewInfo view) throws BOSException;
    public ProjectImageCollection getProjectImageCollection() throws BOSException;
    public ProjectImageInfo getProjectImageInfo(String oql) throws BOSException, EASBizException;
    public ProjectImageInfo getProjectImageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectImageInfo getProjectImageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public Map audit(Set ids, UserInfo auditor) throws BOSException, EASBizException;
    public Map unAudit(Set ids, UserInfo unAuditor) throws BOSException, EASBizException;
}