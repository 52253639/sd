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
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BuildingUnitController extends DataBaseController
{
    public BuildingUnitInfo getBuildingUnitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BuildingUnitInfo getBuildingUnitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BuildingUnitInfo getBuildingUnitInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BuildingUnitCollection getBuildingUnitCollection(Context ctx) throws BOSException, RemoteException;
    public BuildingUnitCollection getBuildingUnitCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BuildingUnitCollection getBuildingUnitCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateBuildingUnitbyBuild(Context ctx, BuildingInfo building) throws BOSException, EASBizException, RemoteException;
}