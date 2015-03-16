package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogCollection;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogInfo;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectCostChangeLogController extends CoreBaseController
{
    public boolean insertLog(Context ctx, Set prjSet) throws BOSException, RemoteException;
    public boolean updateLog(Context ctx, Set prjSet) throws BOSException, RemoteException;
    public boolean deleteLog(Context ctx) throws BOSException, RemoteException;
    public Set getChangePrjs(Context ctx, Set prjSet) throws BOSException, RemoteException;
    public Set getAllChangePrjs(Context ctx) throws BOSException, RemoteException;
}