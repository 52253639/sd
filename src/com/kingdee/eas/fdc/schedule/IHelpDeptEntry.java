package com.kingdee.eas.fdc.schedule;

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

public interface IHelpDeptEntry extends ICoreBillEntryBase
{
    public HelpDeptEntryCollection getHelpDeptEntryCollection(String oql) throws BOSException;
    public HelpDeptEntryCollection getHelpDeptEntryCollection(EntityViewInfo view) throws BOSException;
    public HelpDeptEntryCollection getHelpDeptEntryCollection() throws BOSException;
    public HelpDeptEntryInfo getHelpDeptEntryInfo(String oql) throws BOSException, EASBizException;
    public HelpDeptEntryInfo getHelpDeptEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HelpDeptEntryInfo getHelpDeptEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
}