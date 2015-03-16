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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IArchivesEntry extends IDataBase
{
    public ArchivesEntryInfo getArchivesEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ArchivesEntryInfo getArchivesEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ArchivesEntryInfo getArchivesEntryInfo(String oql) throws BOSException, EASBizException;
    public ArchivesEntryCollection getArchivesEntryCollection() throws BOSException;
    public ArchivesEntryCollection getArchivesEntryCollection(EntityViewInfo view) throws BOSException;
    public ArchivesEntryCollection getArchivesEntryCollection(String oql) throws BOSException;
}