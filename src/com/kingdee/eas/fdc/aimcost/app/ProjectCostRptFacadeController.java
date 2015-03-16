package com.kingdee.eas.fdc.aimcost.app;

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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectCostRptFacadeController extends BizController
{
    public RetValue getData(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
    public void updateData(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public void updateAllData(Context ctx) throws BOSException, RemoteException;
    public void updateProjectsCost(Context ctx, Set prjSet) throws BOSException, EASBizException, RemoteException;
    public RetValue getProductSellArea(Context ctx, Set leafPrjIDs) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionProductAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionProductDynAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionProductDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionFullDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionContractAcctCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getCollectionContractAcctCostDetails(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getProjectCostAnalysis(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
    public RetValue getDynCostInfo(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
    public RetValue getAimCostDynInfo(Context ctx, ParamValue param) throws BOSException, EASBizException, RemoteException;
}