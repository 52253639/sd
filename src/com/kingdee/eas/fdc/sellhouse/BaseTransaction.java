package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;

public class BaseTransaction extends FDCBill implements IBaseTransaction
{
    public BaseTransaction()
    {
        super();
        registerInterface(IBaseTransaction.class, this);
    }
    public BaseTransaction(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseTransaction.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7775F12");
    }
    private BaseTransactionController getController() throws BOSException
    {
        return (BaseTransactionController)getBizController();
    }
    /**
     *×÷·Ï-User defined method
     *@param billid billId
     */
    public void invalid(BOSUuid billid) throws BOSException, EASBizException
    {
        try {
            getController().invalid(getContext(), billid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}