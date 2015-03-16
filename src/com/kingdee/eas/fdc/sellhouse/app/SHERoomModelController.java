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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SHERoomModelCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.sellhouse.SHERoomModelInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHERoomModelController extends CoreBaseController
{
    public SHERoomModelInfo getSHERoomModelInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHERoomModelInfo getSHERoomModelInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHERoomModelInfo getSHERoomModelInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SHERoomModelCollection getSHERoomModelCollection(Context ctx) throws BOSException, RemoteException;
    public SHERoomModelCollection getSHERoomModelCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHERoomModelCollection getSHERoomModelCollection(Context ctx, String oql) throws BOSException, RemoteException;
}