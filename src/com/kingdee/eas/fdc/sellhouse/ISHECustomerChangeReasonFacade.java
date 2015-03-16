package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface ISHECustomerChangeReasonFacade extends IBizCtrl
{
    public void addNewMessage(IObjectValue company, String reason, IObjectValue sheCustomer, IObjectValue propertyConsultant) throws BOSException, SellHouseException, EASBizException;
}