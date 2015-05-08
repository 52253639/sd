package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectImageEntryController extends CoreBillEntryBaseController
{
    public ProjectImageEntryCollection getProjectImageEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProjectImageEntryCollection getProjectImageEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectImageEntryCollection getProjectImageEntryCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public byte[] getOriginalImageById(Context ctx, BOSUuid ID) throws BOSException, EASBizException, RemoteException;
}