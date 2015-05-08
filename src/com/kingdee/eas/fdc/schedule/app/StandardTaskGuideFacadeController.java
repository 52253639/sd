package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface StandardTaskGuideFacadeController extends BizController
{
    public void standardTaskGuideDel(Context ctx, List list) throws BOSException, RemoteException;
    public Set getAllId(Context ctx, List list) throws BOSException, RemoteException;
}