package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class ShowDeductOfPartABill extends CoreBase implements IShowDeductOfPartABill
{
    public ShowDeductOfPartABill()
    {
        super();
        registerInterface(IShowDeductOfPartABill.class, this);
    }
    public ShowDeductOfPartABill(Context ctx)
    {
        super(ctx);
        registerInterface(IShowDeductOfPartABill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("33470BCF");
    }
    private ShowDeductOfPartABillController getController() throws BOSException
    {
        return (ShowDeductOfPartABillController)getBizController();
    }
}