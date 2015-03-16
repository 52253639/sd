package com.kingdee.eas.fdc.sellhouse;

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

public interface IBalanceAdjustFacade extends IBizCtrl
{
    public void blankOutRevBill(String id, boolean isInv, boolean isVerify) throws BOSException, EASBizException;
    public void blankOutPurchase(String id) throws BOSException, EASBizException;
}