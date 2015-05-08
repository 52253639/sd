package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.HashMap;
import java.util.HashSet;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCScheduleTaskFacadeController extends BizController
{
    public HashMap getMuTaskTreeNodes(Context ctx, HashSet projectNodes) throws BOSException, EASBizException, RemoteException;
}