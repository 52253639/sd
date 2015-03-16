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

public interface ICommerceChance extends IFDCBill
{
    public CommerceChanceInfo getCommerceChanceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommerceChanceInfo getCommerceChanceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommerceChanceInfo getCommerceChanceInfo(String oql) throws BOSException, EASBizException;
    public CommerceChanceCollection getCommerceChanceCollection() throws BOSException;
    public CommerceChanceCollection getCommerceChanceCollection(EntityViewInfo view) throws BOSException;
    public CommerceChanceCollection getCommerceChanceCollection(String oql) throws BOSException;
    public void updateToTransaction(String type, String id) throws BOSException, EASBizException;
}