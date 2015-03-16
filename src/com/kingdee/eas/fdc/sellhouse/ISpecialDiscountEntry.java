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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ISpecialDiscountEntry extends ICoreBillEntryBase
{
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(String oql) throws BOSException, EASBizException;
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection() throws BOSException;
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection(EntityViewInfo view) throws BOSException;
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection(String oql) throws BOSException;
}