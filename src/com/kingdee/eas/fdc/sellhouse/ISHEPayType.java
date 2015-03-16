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

public interface ISHEPayType extends IFDCDataBase
{
    public SHEPayTypeInfo getSHEPayTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SHEPayTypeInfo getSHEPayTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SHEPayTypeInfo getSHEPayTypeInfo(String oql) throws BOSException, EASBizException;
    public SHEPayTypeCollection getSHEPayTypeCollection() throws BOSException;
    public SHEPayTypeCollection getSHEPayTypeCollection(EntityViewInfo view) throws BOSException;
    public SHEPayTypeCollection getSHEPayTypeCollection(String oql) throws BOSException;
    public void enable(IObjectPK pk) throws BOSException, EASBizException;
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException;
    public boolean deleteById(String billId) throws BOSException, EASBizException;
}