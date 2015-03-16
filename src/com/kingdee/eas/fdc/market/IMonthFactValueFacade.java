package com.kingdee.eas.fdc.market;

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
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.HashMap;

public interface IMonthFactValueFacade extends IBizCtrl
{
    public IRowSet getFactValue(HashMap map) throws BOSException, EASBizException;
    public IRowSet getYearValue(String ProjectId, String year) throws BOSException, EASBizException;
    public IRowSet getLastValue(String projectId) throws BOSException, EASBizException;
}