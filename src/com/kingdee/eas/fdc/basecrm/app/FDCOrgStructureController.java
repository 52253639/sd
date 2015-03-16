package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.framework.app.CoreBaseController;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCOrgStructureController extends CoreBaseController
{
    public FDCOrgStructureInfo getFDCOrgStructureInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCOrgStructureInfo getFDCOrgStructureInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCOrgStructureInfo getFDCOrgStructureInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection(Context ctx) throws BOSException, RemoteException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateData(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void saveData(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
}