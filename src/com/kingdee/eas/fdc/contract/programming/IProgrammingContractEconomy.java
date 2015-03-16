package com.kingdee.eas.fdc.contract.programming;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IProgrammingContractEconomy extends ICoreBase
{
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection() throws BOSException;
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection(String oql) throws BOSException;
}