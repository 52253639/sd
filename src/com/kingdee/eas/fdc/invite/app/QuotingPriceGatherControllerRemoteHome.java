package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface QuotingPriceGatherControllerRemoteHome extends EJBHome
{
    QuotingPriceGatherControllerRemote create() throws CreateException, RemoteException;
}