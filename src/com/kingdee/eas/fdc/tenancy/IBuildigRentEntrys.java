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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBuildigRentEntrys extends IDataBase
{
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(String oql) throws BOSException, EASBizException;
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection() throws BOSException;
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection(EntityViewInfo view) throws BOSException;
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection(String oql) throws BOSException;
}