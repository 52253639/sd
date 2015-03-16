package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface IPaymentAdvicePrintFacade extends IBizCtrl
{
    public Map getValue(Map param) throws BOSException;
    public IRowSet getPrintData(Set idSet) throws BOSException;
    public IRowSet getComment() throws BOSException;
}