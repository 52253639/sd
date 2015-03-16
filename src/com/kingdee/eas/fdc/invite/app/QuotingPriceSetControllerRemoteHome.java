package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface QuotingPriceSetControllerRemoteHome extends EJBHome
{
    QuotingPriceSetControllerRemote create() throws CreateException, RemoteException;
}