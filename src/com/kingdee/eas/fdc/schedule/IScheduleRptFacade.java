package com.kingdee.eas.fdc.schedule;

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

public interface IScheduleRptFacade extends IBizCtrl
{
    public RetValue getTimeFilerTasks(ParamValue param) throws BOSException, EASBizException;
    public RetValue getFilterStatusTasks(ParamValue param) throws BOSException, EASBizException;
    public RetValue getFilterStatusAllInfos(ParamValue param) throws BOSException, EASBizException;
}