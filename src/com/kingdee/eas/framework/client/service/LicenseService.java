/*jadclipse*/package com.kingdee.eas.framework.client.service;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.base.license.LicenseException;
import com.kingdee.eas.base.license.LicenseUserInfo;
import com.kingdee.eas.base.license.client.LicenseController;
import com.kingdee.eas.base.license.client.monitor.LicenseClientUtil;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import java.util.*;
import org.apache.log4j.Logger;
public class LicenseService
{
            public LicenseService(IUIObject ui)
            {




/*  25*/        logger = Logger.getLogger(com.kingdee.eas.framework.client.service.LicenseService.class);
/*  26*/        hasLicence = false;









/*  36*/        this.ui = ui;
            }
            public void checkLicence()
                throws Exception
            {
//            	
//
//
//
//
//
//
//
//
//
//
//
//
///*  53*/        LicenseUserInfo user = (LicenseUserInfo)SysContext.getSysContext().getProperty("License.UserInfo");
///*  54*/        LicenseController lc = FrameWorkClientUtils.getLicenseController();
//
//
//
//
//
//
//
//
///*  63*/        String className = getUIClassName(ui);
//
//
//
//
//
//
//
//
//
//
///*  74*/        if(!needCheck(lc, className))
///*  75*/            return;
///*  76*/        int licenselcFlag = lc.requestLicense(user, className);
//
///*  78*/        switch(licenselcFlag)
//                {
///* <-MISALIGNED-> */ /*  53*/        case 3: 
///* <-MISALIGNED-> */ /*  53*/        default:
//                    break;
///* <-MISALIGNED-> */ /*  81*/        case 1: 
///* <-MISALIGNED-> */ /*  81*/            hasLicence = true;
///* <-MISALIGNED-> */ /*  82*/            if(!licenseRecordList.contains(className))
///* <-MISALIGNED-> */ /*  83*/                licenseRecordList.add(className);
//                    break;
//
//
//
///*  91*/        case 4: /*  91*/            MsgBox.showDetailAndOK((CoreUI)ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_Invalid"), constructMessage(lc, className), 0);
//
///*  93*/            abort();
//                    break;
//
//
//
//
///*  99*/        case 2: /*  99*/            MsgBox.showDetailAndOK((CoreUI)ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_NO_SURPLUS"), constructMessage(lc, className), 0);
//
///* 101*/            abort();
//                    break;
//                }
            }
            public void releaseLicense()
                throws Exception
            {
//                String sessionId;
//                LicenseController lc;
//
//
//
//
//
//
//
//
//
//
/////* 120*/        if(!hasLicence)
/////* 121*/            break MISSING_BLOCK_LABEL_62;/* 121*/        
//				sessionId = SysContext.getSysContext().getSessionID();
//
///* 123*/        lc = FrameWorkClientUtils.getLicenseController();
//                String className;
//
///* 126*/        className = getUIClassName(ui);
//
//
///* 129*/        if(!needCheck(lc, className))
///* 130*/            return;
///* 131*/        try
//                {
///* <-MISALIGNED-> */ /* 131*/            lc.releaseLicense(sessionId, className);
///* <-MISALIGNED-> */ /* 132*/            licenseRecordList.remove(className);
//                }
///* <-MISALIGNED-> */ /* 134*/        catch(LicenseException lex) { }
//
///* 138*/        hasLicence = false;
            }
            private String getUIClassName(IUIObject uiObject)
            {

/* 143*/        String className = null;
/* 144*/        if(uiObject.getMetaDataPK() != null)
/* 145*/            className = uiObject.getMetaDataPK().getFullName();

/* 147*/        else/* 147*/            className = uiObject.getClass().getName();

/* 149*/        return className;
            }
            private boolean needCheck(LicenseController lc, String fullClassName)
                throws LicenseException
            {





/* 159*/        String check = (String)ui.getUIContext().get("checkLicense");
/* 160*/        if(!StringUtils.isEmpty(check) && check.trim().equalsIgnoreCase("true"))
                {/* 161*/            logger.info((new StringBuilder()).append("license main menu,need check\uFF1A").append(fullClassName).toString());
/* 162*/            return true;
                } else
                {




/* 169*/            logger.info((new StringBuilder()).append("coreui license unlimited \uFF1A").append(fullClassName).toString());
/* 170*/            return false;
                }
            }
            private String constructMessage(LicenseController lc, String className)
                throws LicenseException
            {








/* 184*/        if(lc == null || className == null)
                {/* 185*/            return "";
                } else
                {/* 187*/            StringBuffer buffer = new StringBuffer();
/* 188*/            buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ClassName"));
/* 189*/            buffer.append((new StringBuilder()).append(ui.getUITitle()).append("[").append(getUIClassName(ui)).append("]").toString());
/* 190*/            buffer.append("\n");
/* 191*/            buffer.append(constructLicenseMessage(lc, className));
/* 192*/            return buffer.toString();
                }
            }
            private final String getPackageNameForClass(Class cls)
            {







/* 204*/        String packageName = getUIClassName(ui);
/* 205*/        return packageName.substring(0, packageName.lastIndexOf(".") + 1);
            }
            private void abort()
            {

/* 210*/        ((CoreUI)ui).setCursorOfDefault();
/* 211*/        SysUtil.abort();
            }
            public static final List getLicenseRecordList()
            {







/* 222*/        return licenseRecordList;
            }
            public static final String constructLicenseMessage(LicenseController lc, String className)
                throws LicenseException
            {








/* 235*/        if(lc == null || className == null)
/* 236*/            return "";

/* 238*/        StringBuffer buffer = new StringBuffer();
/* 239*/        buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ModuleName"));
/* 240*/        String moduleName = lc.getModuleByPackage(className);
/* 241*/        if(moduleName == null || moduleName.trim().length() == 0)
/* 242*/            buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));

/* 244*/        else/* 244*/            buffer.append((new StringBuilder()).append(LicenseClientUtil.getModularAliasNameByModularName(moduleName)).append("[").append(moduleName).append("]").toString());

/* 246*/        buffer.append("\n");
/* 247*/        buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_SubSystem"));
/* 248*/        String subSysName = LicenseClientUtil.getSubSystemNameByModularName(moduleName);
/* 249*/        if(subSysName == null || subSysName.trim().length() == 0)
/* 250*/            buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));

/* 252*/        else/* 252*/            buffer.append((new StringBuilder()).append(LicenseClientUtil.getModularAliasNameByModularName(subSysName)).append("[").append(subSysName).append("]").toString());

/* 254*/        buffer.append("\n");
/* 255*/        return buffer.toString();
            }
            private Logger logger;
            private boolean hasLicence;
            private IUIObject ui;
            private static List licenseRecordList = new ArrayList();
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\client\bos\eas_framework-client.jar
	Total time: 80 ms
	Jad reported messages/errors:
Couldn't resolve all exception handlers in method releaseLicense
	Exit status: 0
	Caught exceptions:
*/