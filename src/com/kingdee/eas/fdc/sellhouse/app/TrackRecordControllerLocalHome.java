package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TrackRecordControllerLocalHome extends EJBLocalHome
{
    TrackRecordControllerLocal create() throws CreateException;
}