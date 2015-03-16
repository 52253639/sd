package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class QuestionPaperReportFacade extends BireportBaseFacade implements IQuestionPaperReportFacade
{
    public QuestionPaperReportFacade()
    {
        super();
        registerInterface(IQuestionPaperReportFacade.class, this);
    }
    public QuestionPaperReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0811DE88");
    }
    private QuestionPaperReportFacadeController getController() throws BOSException
    {
        return (QuestionPaperReportFacadeController)getBizController();
    }
}