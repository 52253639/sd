package com.kingdee.eas.fdc.schedule.app;

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
import com.kingdee.eas.fdc.schedule.ProjectImageCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectImageController extends ScheduleBaseController
{
    public ProjectImageCollection getProjectImageCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProjectImageCollection getProjectImageCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectImageCollection getProjectImageCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectImageInfo getProjectImageInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectImageInfo getProjectImageInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectImageInfo getProjectImageInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public Map audit(Context ctx, Set ids, UserInfo auditor) throws BOSException, EASBizException, RemoteException;
    public Map unAudit(Context ctx, Set ids, UserInfo unAuditor) throws BOSException, EASBizException, RemoteException;
}