package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewQuotingPriceControllerRemoteHome extends EJBHome
{
    NewQuotingPriceControllerRemote create() throws CreateException, RemoteException;
}