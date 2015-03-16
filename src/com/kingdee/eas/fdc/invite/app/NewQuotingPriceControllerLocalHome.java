package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewQuotingPriceControllerLocalHome extends EJBLocalHome
{
    NewQuotingPriceControllerLocal create() throws CreateException;
}