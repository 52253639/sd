package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface IXQEntry extends IFDCDataBase
{
    public XQEntryInfo getXQEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public XQEntryInfo getXQEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public XQEntryInfo getXQEntryInfo(String oql) throws BOSException, EASBizException;
    public XQEntryCollection getXQEntryCollection() throws BOSException;
    public XQEntryCollection getXQEntryCollection(EntityViewInfo view) throws BOSException;
    public XQEntryCollection getXQEntryCollection(String oql) throws BOSException;
}