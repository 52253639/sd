package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface ICostClosePeriodFacade extends IBizCtrl
{
    public void tracePayment(List idList) throws BOSException, EASBizException;
    public void checkCostSplit(List idList) throws BOSException, EASBizException;
    public String traceCostClose(List idList) throws BOSException, EASBizException;
    public void frozenProject(List idList) throws BOSException, EASBizException;
    public String antiCostClose(List idList) throws BOSException, EASBizException;
    public void delete(String contractId, String periodId) throws BOSException, EASBizException;
}