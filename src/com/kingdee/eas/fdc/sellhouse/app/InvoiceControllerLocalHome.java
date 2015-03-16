package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InvoiceControllerLocalHome extends EJBLocalHome
{
    InvoiceControllerLocal create() throws CreateException;
}