package com.kingdee.eas.fdc.invite.supplier;

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

public interface IAptitudeFile extends ICoreBillEntryBase
{
    public AptitudeFileInfo getAptitudeFileInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AptitudeFileInfo getAptitudeFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AptitudeFileInfo getAptitudeFileInfo(String oql) throws BOSException, EASBizException;
    public AptitudeFileCollection getAptitudeFileCollection() throws BOSException;
    public AptitudeFileCollection getAptitudeFileCollection(EntityViewInfo view) throws BOSException;
    public AptitudeFileCollection getAptitudeFileCollection(String oql) throws BOSException;
}