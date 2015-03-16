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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAgioBill extends IFDCBill
{
    public AgioBillInfo getAgioBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AgioBillInfo getAgioBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AgioBillInfo getAgioBillInfo(String oql) throws BOSException, EASBizException;
    public AgioBillCollection getAgioBillCollection() throws BOSException;
    public AgioBillCollection getAgioBillCollection(EntityViewInfo view) throws BOSException;
    public AgioBillCollection getAgioBillCollection(String oql) throws BOSException;
    public void enable(IObjectPK pk) throws BOSException, EASBizException;
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException;
}