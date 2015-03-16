package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PaymentSplitFacadeController extends BizController
{
    public RetValue getPaymentSplit(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getWorkLoadConfirmSplitDatas(Context ctx, Map paramValue) throws BOSException, EASBizException, RemoteException;
}