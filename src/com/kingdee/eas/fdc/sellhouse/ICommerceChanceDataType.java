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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ICommerceChanceDataType extends ITreeBase
{
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(String oql) throws BOSException, EASBizException;
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection() throws BOSException;
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection(EntityViewInfo view) throws BOSException;
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection(String oql) throws BOSException;
}