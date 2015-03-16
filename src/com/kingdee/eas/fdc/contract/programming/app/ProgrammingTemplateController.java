package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateCollection;
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

public interface ProgrammingTemplateController extends FDCDataBaseController
{
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(Context ctx) throws BOSException, RemoteException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void copy(Context ctx) throws BOSException, RemoteException;
}