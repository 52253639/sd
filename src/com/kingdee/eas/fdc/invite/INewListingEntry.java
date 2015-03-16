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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface INewListingEntry extends ITreeBase
{
    public NewListingEntryInfo getNewListingEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewListingEntryInfo getNewListingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewListingEntryInfo getNewListingEntryInfo(String oql) throws BOSException, EASBizException;
    public NewListingEntryCollection getNewListingEntryCollection() throws BOSException;
    public NewListingEntryCollection getNewListingEntryCollection(String oql) throws BOSException;
    public NewListingEntryCollection getNewListingEntryCollection(EntityViewInfo view) throws BOSException;
}