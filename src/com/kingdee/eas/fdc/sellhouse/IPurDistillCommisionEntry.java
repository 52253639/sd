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

public interface IPurDistillCommisionEntry extends ICoreBase
{
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(String oql) throws BOSException, EASBizException;
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection() throws BOSException;
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection(EntityViewInfo view) throws BOSException;
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection(String oql) throws BOSException;
}