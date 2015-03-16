package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.RetValue;

public interface IProjectCostRptFacade extends IBizCtrl
{
    public RetValue getData(ParamValue param) throws BOSException, EASBizException;
    public void updateData(String prjId) throws BOSException, EASBizException;
    public void updateAllData() throws BOSException;
    public void updateProjectsCost(Set prjSet) throws BOSException, EASBizException;
    public RetValue getProductSellArea(Set leafPrjIDs) throws BOSException, EASBizException;
    public RetValue getCollectionProductAimCost(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getCollectionProductDynAimCost(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getCollectionProductDynCost(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getCollectionFullDynCost(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getCollectionContractAcctCost(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getCollectionContractAcctCostDetails(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getProjectCostAnalysis(ParamValue paramValue) throws BOSException, EASBizException;
    public RetValue getDynCostInfo(ParamValue param) throws BOSException, EASBizException;
    public RetValue getAimCostDynInfo(ParamValue param) throws BOSException, EASBizException;
}