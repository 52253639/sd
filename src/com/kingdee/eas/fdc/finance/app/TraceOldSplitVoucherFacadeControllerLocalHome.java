package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TraceOldSplitVoucherFacadeControllerLocalHome extends EJBLocalHome
{
    TraceOldSplitVoucherFacadeControllerLocal create() throws CreateException;
}