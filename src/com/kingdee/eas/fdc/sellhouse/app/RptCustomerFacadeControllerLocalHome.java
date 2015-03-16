package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RptCustomerFacadeControllerLocalHome extends EJBLocalHome
{
    RptCustomerFacadeControllerLocal create() throws CreateException;
}