package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PropertyDoSchemeController extends FDCDataBaseController
{
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection(Context ctx) throws BOSException, RemoteException;
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void disEnable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void setEnabled(Context ctx, List idList, boolean isEnabled) throws BOSException, EASBizException, RemoteException;
}