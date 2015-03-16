package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCCustomerControllerLocalHome extends EJBLocalHome
{
    FDCCustomerControllerLocal create() throws CreateException;
}