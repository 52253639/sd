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

public interface IBanBasedataEntryList extends ICoreBillEntryBase
{
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(String oql) throws BOSException, EASBizException;
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection() throws BOSException;
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection(EntityViewInfo view) throws BOSException;
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection(String oql) throws BOSException;
}