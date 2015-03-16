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

public interface IPossessionStandard extends IDataBase
{
    public PossessionStandardInfo getPossessionStandardInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PossessionStandardInfo getPossessionStandardInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PossessionStandardInfo getPossessionStandardInfo(String oql) throws BOSException, EASBizException;
    public PossessionStandardCollection getPossessionStandardCollection() throws BOSException;
    public PossessionStandardCollection getPossessionStandardCollection(EntityViewInfo view) throws BOSException;
    public PossessionStandardCollection getPossessionStandardCollection(String oql) throws BOSException;
}