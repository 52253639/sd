package com.kingdee.eas.fdc.tenancy;

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

public interface INewDealAmountEntry extends ICoreBillEntryBase
{
    public NewDealAmountEntryInfo getNewDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewDealAmountEntryInfo getNewDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewDealAmountEntryInfo getNewDealAmountEntryInfo(String oql) throws BOSException, EASBizException;
    public NewDealAmountEntryCollection getNewDealAmountEntryCollection() throws BOSException;
    public NewDealAmountEntryCollection getNewDealAmountEntryCollection(EntityViewInfo view) throws BOSException;
    public NewDealAmountEntryCollection getNewDealAmountEntryCollection(String oql) throws BOSException;
}