package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.HouseAnlysisInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.HouseAnlysisCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface HouseAnlysisController extends FDCDataBaseController
{
    public HouseAnlysisInfo getHouseAnlysisInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public HouseAnlysisInfo getHouseAnlysisInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public HouseAnlysisInfo getHouseAnlysisInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public HouseAnlysisCollection getHouseAnlysisCollection(Context ctx) throws BOSException, RemoteException;
    public HouseAnlysisCollection getHouseAnlysisCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public HouseAnlysisCollection getHouseAnlysisCollection(Context ctx, String oql) throws BOSException, RemoteException;
}