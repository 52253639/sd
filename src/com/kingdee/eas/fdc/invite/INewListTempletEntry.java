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

public interface INewListTempletEntry extends ITreeBase
{
    public NewListTempletEntryInfo getNewListTempletEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewListTempletEntryInfo getNewListTempletEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewListTempletEntryInfo getNewListTempletEntryInfo(String oql) throws BOSException, EASBizException;
    public NewListTempletEntryCollection getNewListTempletEntryCollection() throws BOSException;
    public NewListTempletEntryCollection getNewListTempletEntryCollection(String oql) throws BOSException;
    public NewListTempletEntryCollection getNewListTempletEntryCollection(EntityViewInfo view) throws BOSException;
}