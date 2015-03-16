package com.kingdee.eas.fdc.sellhouse;

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
import java.util.Date;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBatchSettlementFacade extends IBizCtrl
{
    public void generateRecBil(CoreBaseCollection recBillList, CoreBaseCollection payList, Date recDate) throws BOSException, EASBizException;
}