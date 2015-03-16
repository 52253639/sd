package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface VoucherForDynamicControllerLocalHome extends EJBLocalHome
{
    VoucherForDynamicControllerLocal create() throws CreateException;
}