package com.kingdee.eas.fdc.invite;

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

public interface IListingPageSumEntry extends ICoreBase
{
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(String oql) throws BOSException, EASBizException;
    public ListingPageSumEntryCollection getListingPageSumEntryCollection() throws BOSException;
    public ListingPageSumEntryCollection getListingPageSumEntryCollection(EntityViewInfo view) throws BOSException;
    public ListingPageSumEntryCollection getListingPageSumEntryCollection(String oql) throws BOSException;
}