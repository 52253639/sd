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
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;

public interface IStandardTaskGuide extends IFDCTreeBaseData
{
    public StandardTaskGuideCollection getStandardTaskGuideCollection() throws BOSException;
    public StandardTaskGuideCollection getStandardTaskGuideCollection(EntityViewInfo view) throws BOSException;
    public StandardTaskGuideCollection getStandardTaskGuideCollection(String oql) throws BOSException;
    public StandardTaskGuideInfo getStandardTaskGuideInfo(IObjectPK pk) throws BOSException, EASBizException;
    public StandardTaskGuideInfo getStandardTaskGuideInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public StandardTaskGuideInfo getStandardTaskGuideInfo(String oql) throws BOSException, EASBizException;
}