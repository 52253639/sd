package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataController;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RESchTemplateController extends FDCTreeBaseDataController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RESchTemplateInfo getRESchTemplateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RESchTemplateInfo getRESchTemplateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RESchTemplateInfo getRESchTemplateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, RESchTemplateInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, RESchTemplateInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, RESchTemplateInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, RESchTemplateInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, RESchTemplateInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public RESchTemplateCollection getRESchTemplateCollection(Context ctx) throws BOSException, RemoteException;
    public RESchTemplateCollection getRESchTemplateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RESchTemplateCollection getRESchTemplateCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public void importTasks(Context ctx, String templateID, RESchTemplateTaskCollection tasks) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
}