package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BuildingController extends FDCDataBaseController
{
    public BuildingInfo getBuildingInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BuildingInfo getBuildingInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BuildingInfo getBuildingInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BuildingCollection getBuildingCollection(Context ctx) throws BOSException, RemoteException;
    public BuildingCollection getBuildingCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BuildingCollection getBuildingCollection(Context ctx, String oql) throws BOSException, RemoteException;
}