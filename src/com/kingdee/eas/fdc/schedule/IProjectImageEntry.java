package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IProjectImageEntry extends ICoreBillEntryBase
{
    public ProjectImageEntryCollection getProjectImageEntryCollection(String oql) throws BOSException;
    public ProjectImageEntryCollection getProjectImageEntryCollection(EntityViewInfo view) throws BOSException;
    public ProjectImageEntryCollection getProjectImageEntryCollection() throws BOSException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(String oql) throws BOSException, EASBizException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectImageEntryInfo getProjectImageEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public byte[] getOriginalImageById(BOSUuid ID) throws BOSException, EASBizException;
}