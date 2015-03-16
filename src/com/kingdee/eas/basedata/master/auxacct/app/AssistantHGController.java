package com.kingdee.eas.basedata.master.auxacct.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.ObjectBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGCollection;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AssistantHGController extends ObjectBaseController
{
    public AssistantHGCollection getAssistantHGCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AssistantHGCollection getAssistantHGCollection(Context ctx) throws BOSException, RemoteException;
    public AssistantHGCollection getAssistantHGCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public AssistantHGInfo getAssistantHG(Context ctx, AssistantHGInfo assGrp, String bookId, AsstActTypeCollection items) throws BOSException, EASBizException, RemoteException;
    public AssistantHGInfo getAssistantHGInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AssistantHGInfo getAssistantHGInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AssistantHGInfo getAssistantHGInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectValue getAssistantHG(Context ctx, IObjectPK asstHGPK, String bookId, IObjectPK[] items) throws BOSException, EASBizException, RemoteException;
}