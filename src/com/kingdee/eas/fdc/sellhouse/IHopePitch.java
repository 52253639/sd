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

public interface IHopePitch extends IFDCDataBase
{
    public HopePitchInfo getHopePitchInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HopePitchInfo getHopePitchInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HopePitchInfo getHopePitchInfo(String oql) throws BOSException, EASBizException;
    public HopePitchCollection getHopePitchCollection() throws BOSException;
    public HopePitchCollection getHopePitchCollection(EntityViewInfo view) throws BOSException;
    public HopePitchCollection getHopePitchCollection(String oql) throws BOSException;
}