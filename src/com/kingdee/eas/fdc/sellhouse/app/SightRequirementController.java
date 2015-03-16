package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.SightRequirementCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SightRequirementController extends FDCDataBaseController
{
    public SightRequirementInfo getSightRequirementInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SightRequirementInfo getSightRequirementInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SightRequirementInfo getSightRequirementInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SightRequirementCollection getSightRequirementCollection(Context ctx) throws BOSException, RemoteException;
    public SightRequirementCollection getSightRequirementCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SightRequirementCollection getSightRequirementCollection(Context ctx, String oql) throws BOSException, RemoteException;
}