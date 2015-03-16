package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public interface ISubjectLevel extends IDataBase
{
    public SubjectLevelInfo getSubjectLevelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SubjectLevelInfo getSubjectLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SubjectLevelInfo getSubjectLevelInfo(String oql) throws BOSException, EASBizException;
    public SubjectLevelCollection getSubjectLevelCollection() throws BOSException;
    public SubjectLevelCollection getSubjectLevelCollection(EntityViewInfo view) throws BOSException;
    public SubjectLevelCollection getSubjectLevelCollection(String oql) throws BOSException;
    public Map getSelectedIDS(String pk) throws BOSException;
}