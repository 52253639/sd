package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface ITranStateHis extends ICoreBase
{
    public TranStateHisInfo getTranStateHisInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TranStateHisInfo getTranStateHisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TranStateHisInfo getTranStateHisInfo(String oql) throws BOSException, EASBizException;
    public TranStateHisCollection getTranStateHisCollection() throws BOSException;
    public TranStateHisCollection getTranStateHisCollection(EntityViewInfo view) throws BOSException;
    public TranStateHisCollection getTranStateHisCollection(String oql) throws BOSException;
}