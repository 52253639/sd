package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TrackRecordControllerRemoteHome extends EJBHome
{
    TrackRecordControllerRemote create() throws CreateException, RemoteException;
}