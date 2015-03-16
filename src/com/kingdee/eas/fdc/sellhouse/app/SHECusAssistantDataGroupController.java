package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHECusAssistantDataGroupController extends TreeBaseController
{
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(Context ctx) throws BOSException, RemoteException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(Context ctx, String oql) throws BOSException, RemoteException;
}