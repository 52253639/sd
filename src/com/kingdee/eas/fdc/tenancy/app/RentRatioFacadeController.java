package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RentRatioFacadeController extends BizController
{
    public Map getRentRatioInfo(Context ctx, Map param) throws BOSException, RemoteException;
    public Map getBatchFilterInfo(Context ctx, Map customerParams) throws BOSException, RemoteException;
}