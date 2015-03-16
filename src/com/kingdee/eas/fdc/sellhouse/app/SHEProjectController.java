package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SHEProjectInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.SHEProjectCollection;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHEProjectController extends TreeBaseController
{
    public SHEProjectInfo getSHEProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHEProjectInfo getSHEProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHEProjectInfo getSHEProjectInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SHEProjectCollection getSHEProjectCollection(Context ctx) throws BOSException, RemoteException;
    public SHEProjectCollection getSHEProjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHEProjectCollection getSHEProjectCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateRoomModel(Context ctx, List idList, String id) throws BOSException, EASBizException, RemoteException;
    public void deleteRoomModel(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void updateEnable(Context ctx, BOSUuid id, boolean isEnabled) throws BOSException, EASBizException, RemoteException;
    public void updateSHEProjectToSellProject(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void updateSellProjectToSHEProject(Context ctx, String orgId) throws BOSException, EASBizException, RemoteException;
}