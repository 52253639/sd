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
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISinObligateAttachEntry extends ICoreBillBase
{
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(String oql) throws BOSException, EASBizException;
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection() throws BOSException;
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection(EntityViewInfo view) throws BOSException;
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection(String oql) throws BOSException;
}