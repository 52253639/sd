package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.finance.SubjectLevelCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.SubjectLevelInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SubjectLevelController extends DataBaseController
{
    public SubjectLevelInfo getSubjectLevelInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SubjectLevelInfo getSubjectLevelInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SubjectLevelInfo getSubjectLevelInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SubjectLevelCollection getSubjectLevelCollection(Context ctx) throws BOSException, RemoteException;
    public SubjectLevelCollection getSubjectLevelCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SubjectLevelCollection getSubjectLevelCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map getSelectedIDS(Context ctx, String pk) throws BOSException, RemoteException;
}