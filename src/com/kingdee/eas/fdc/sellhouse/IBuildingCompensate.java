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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBuildingCompensate extends IFDCBill
{
    public BuildingCompensateInfo getBuildingCompensateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildingCompensateInfo getBuildingCompensateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildingCompensateInfo getBuildingCompensateInfo(String oql) throws BOSException, EASBizException;
    public BuildingCompensateCollection getBuildingCompensateCollection() throws BOSException;
    public BuildingCompensateCollection getBuildingCompensateCollection(EntityViewInfo view) throws BOSException;
    public BuildingCompensateCollection getBuildingCompensateCollection(String oql) throws BOSException;
}