package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InvoiceControllerRemoteHome extends EJBHome
{
    InvoiceControllerRemote create() throws CreateException, RemoteException;
}