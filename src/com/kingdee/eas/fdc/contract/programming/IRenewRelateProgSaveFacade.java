package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.Object;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface IRenewRelateProgSaveFacade extends IBizCtrl
{
    public void save(IObjectCollection objCol) throws BOSException, EASBizException;
    public Set getContractbillID(Object[] id) throws BOSException;
}