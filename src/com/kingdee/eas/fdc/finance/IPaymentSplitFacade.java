package com.kingdee.eas.fdc.finance;

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

public interface IPaymentSplitFacade extends IBizCtrl
{
    public RetValue getPaymentSplit(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getWorkLoadConfirmSplitDatas(Map paramValue) throws BOSException, EASBizException;
}