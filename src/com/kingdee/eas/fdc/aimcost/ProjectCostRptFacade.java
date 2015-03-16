package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.RetValue;

public class ProjectCostRptFacade extends AbstractBizCtrl implements IProjectCostRptFacade
{
    public ProjectCostRptFacade()
    {
        super();
        registerInterface(IProjectCostRptFacade.class, this);
    }
    public ProjectCostRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectCostRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FC51A21");
    }
    private ProjectCostRptFacadeController getController() throws BOSException
    {
        return (ProjectCostRptFacadeController)getBizController();
    }
    /**
     *取得报表数据-User defined method
     *@param param 参数
     *@return
     */
    public RetValue getData(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新数据-User defined method
     *@param prjId 项目ID
     */
    public void updateData(String prjId) throws BOSException, EASBizException
    {
        try {
            getController().updateData(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新所有变化成本的项目-User defined method
     */
    public void updateAllData() throws BOSException
    {
        try {
            getController().updateAllData(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新要取数据的项目的成本-User defined method
     *@param prjSet prjSet
     */
    public void updateProjectsCost(Set prjSet) throws BOSException, EASBizException
    {
        try {
            getController().updateProjectsCost(getContext(), prjSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取下面明细工程项目的可售面积,并在产品上做汇总,返回的结果以产品做键,可售面积为值-User defined method
     *@param leafPrjIDs leafPrjIDs
     *@return
     */
    public RetValue getProductSellArea(Set leafPrjIDs) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSellArea(getContext(), leafPrjIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品类型目标成本表获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductAimCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductAimCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品类型动态目标成本表获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductDynAimCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductDynAimCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品类型动态成本表获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductDynCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductDynCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全项目动态成本表获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionFullDynCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionFullDynCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *科目合同明细表获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionContractAcctCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionContractAcctCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *科目合同明细表弹出窗口获取数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionContractAcctCostDetails(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionContractAcctCostDetails(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工程项目成本分析表-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getProjectCostAnalysis(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectCostAnalysis(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查看成本信息-User defined method
     *@param param param
     *@return
     */
    public RetValue getDynCostInfo(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *目标成本动态分析-User defined method
     *@param param param
     *@return
     */
    public RetValue getAimCostDynInfo(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostDynInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}