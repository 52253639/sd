package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class RptQuestionPaperFacade extends BireportBaseFacade implements IRptQuestionPaperFacade
{
    public RptQuestionPaperFacade()
    {
        super();
        registerInterface(IRptQuestionPaperFacade.class, this);
    }
    public RptQuestionPaperFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRptQuestionPaperFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F1AEA6BB");
    }
    private RptQuestionPaperFacadeController getController() throws BOSException
    {
        return (RptQuestionPaperFacadeController)getBizController();
    }
}