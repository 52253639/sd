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
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleRptFacadeController extends BizController
{
    public RetValue getTimeFilerTasks(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
    public RetValue getFilterStatusTasks(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
    public RetValue getFilterStatusAllInfos(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
}