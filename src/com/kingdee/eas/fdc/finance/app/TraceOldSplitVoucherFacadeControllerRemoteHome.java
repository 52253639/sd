package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TraceOldSplitVoucherFacadeControllerRemoteHome extends EJBHome
{
    TraceOldSplitVoucherFacadeControllerRemote create() throws CreateException, RemoteException;
}