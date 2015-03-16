/*jadclipse*/package com.kingdee.eas.fi.gl;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.eas.basedata.framework.IDataBaseD;
import com.kingdee.eas.basedata.master.auxacct.*;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import java.util.Iterator;
import org.apache.log4j.Logger;
public class InitAssistBalanceImportFDC extends InitAssistBalanceImport
{
            public InitAssistBalanceImportFDC()
            {
            }
            private static void printLog(String msg)
            {














/*  42*/        logger.error((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
/*  43*/        System.err.println((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
/*  44*/        System.out.println((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
            }
            protected DataBaseInfo findAsstActObject(Context ctx, String asstHGAttribute, AssistantHGInfo assistantHGInfo, String strAsstActTypeName, String number, String strAsstName, AsstActTypeCollection arrayAsstActTypeInfo)
                throws BOSException
            {/*  48*/        printLog((new StringBuilder()).append("findAsstActObject.").append(asstHGAttribute).toString());
/*  49*/        if(isFDCAsstAct(asstHGAttribute))
                {/*  50*/            printLog((new StringBuilder()).append("findAsstActObject in FDC.").append(asstHGAttribute).toString());
/*  51*/            return findAsstActObject4FDC(ctx, asstHGAttribute, assistantHGInfo, strAsstActTypeName, number, strAsstName, arrayAsstActTypeInfo);
                } else
                {
/*  54*/            return super.findAsstActObject(ctx, asstHGAttribute, assistantHGInfo, strAsstActTypeName, number, strAsstName, arrayAsstActTypeInfo);
                }
            }
            private boolean isFDCAsstAct(String asstHGAttribute)
            {




/*  63*/        return "curProject".equalsIgnoreCase(asstHGAttribute) || "ContractBaseData".equalsIgnoreCase(asstHGAttribute) || "ProductType".equalsIgnoreCase(asstHGAttribute) || "sellProject".equalsIgnoreCase(asstHGAttribute) || "building".equalsIgnoreCase(asstHGAttribute) || "room".equalsIgnoreCase(asstHGAttribute);
            }
            private DataBaseInfo findAsstActObject4FDC(Context ctx, String asstHGAttribute, AssistantHGInfo assistantHGInfo, String strAsstActTypeName, String number, String strAsstName, AsstActTypeCollection arrayAsstActTypeInfo)
                throws BOSException
            {









/*  77*/        IAssistantHG iAssistantHG = AssistantHGFactory.getLocalInstance(ctx);
/*  78*/        com.kingdee.bos.util.BOSObjectType type = iAssistantHG.getType();
/*  79*/        IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
/*  80*/        String cuid = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();


/*  83*/        CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
/*  84*/        FullOrgUnitInfo org = company.castToFullOrgUnitInfo();

/*  86*/        com.kingdee.eas.basedata.org.SaleOrgUnitInfo sellUnit = ContextUtil.getCurrentSaleUnit(ctx);
/*  87*/        String companyId = company.getId().toString();
/*  88*/        String orgId = org.getId().toString();



/*  92*/        EntityObjectInfo eo = loader.getEntity(type);
/*  93*/        PropertyCollection props = eo.getInheritedNoDuplicatedProperties();
/*  94*/        Iterator itr = props.iterator();
/*  95*/        LinkPropertyInfo pi = null;

/*  97*/        DataBaseInfo data = null;

/*  99*/        try
                {
/* <-MISALIGNED-> */ /*  99*/            do
                    {
/* <-MISALIGNED-> */ /*  99*/                if(!itr.hasNext())
/* <-MISALIGNED-> */ /* 100*/                    break;
/* <-MISALIGNED-> */ /* 100*/                PropertyInfo p = (PropertyInfo)itr.next();
/* <-MISALIGNED-> */ /* 101*/                if(!asstHGAttribute.equalsIgnoreCase(p.getName()) || !(p instanceof LinkPropertyInfo))
/* <-MISALIGNED-> */ /* 102*/                    continue;
/* <-MISALIGNED-> */ /* 102*/                pi = (LinkPropertyInfo)p;
/* <-MISALIGNED-> */ /* 103*/                break;
                    } while(true);
/* <-MISALIGNED-> */ /* 106*/            if(pi != null)
                    {
/* <-MISALIGNED-> */ /* 107*/                EntityObjectInfo eobj = pi.getRelationship().getSupplierObject();
/* <-MISALIGNED-> */ /* 109*/                String strFacotry = eobj.getBusinessImplFactory();
/* <-MISALIGNED-> */ /* 110*/                String strLocal = eobj.getBusinessImplName();
/* <-MISALIGNED-> */ /* 111*/                EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 112*/                SelectorItemCollection sic = view.getSelector();
/* <-MISALIGNED-> */ /* 113*/                sic.add(new SelectorItemInfo("number"));
/* <-MISALIGNED-> */ /* 114*/                sic.add(new SelectorItemInfo("name"));
/* <-MISALIGNED-> */ /* 116*/                FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 117*/                view.setFilter(filter);
/* <-MISALIGNED-> */ /* 118*/                FilterItemCollection fic = filter.getFilterItems();
/* <-MISALIGNED-> */ /* 120*/                AsstActTypeInfo typeInfo = GlWebServiceUtil.findAsstActtypeNumberByAttribute(ctx, asstHGAttribute);
/* <-MISALIGNED-> */ /* 122*/                if(typeInfo.isUseLongNumber() && number != null)/* 124*/                    fic.add(new FilterItemInfo("longNumber", number.replace('.', '!')));

/* 126*/                else/* 126*/                if("room".equalsIgnoreCase(asstHGAttribute))
/* 127*/                    fic.add(new FilterItemInfo("name", strAsstName));


/* 130*/                else/* 130*/                    fic.add(new FilterItemInfo("number", number));


/* 133*/                if("curProject".equalsIgnoreCase(asstHGAttribute))
/* 134*/                    fic.add(new FilterItemInfo("fullOrgUnit.id", companyId));

/* 136*/                else/* 136*/                if("ContractBaseData".equalsIgnoreCase(asstHGAttribute))
/* 137*/                    fic.add(new FilterItemInfo("company.id", companyId));

/* 139*/                else/* 139*/                if(!"sellProject".equalsIgnoreCase(asstHGAttribute))







/* 147*/                    if("building".equalsIgnoreCase(asstHGAttribute))
                            {


/* 151*/                        if(assistantHGInfo.getSellProject() != null && assistantHGInfo.getSellProject().getId() != null)
/* 152*/                            fic.add(new FilterItemInfo("sellProject.id", assistantHGInfo.getSellProject().getId().toString()));
                            } else


/* 156*/                    if("room".equalsIgnoreCase(asstHGAttribute))
                            {


/* 160*/                        if(assistantHGInfo.getSellProject() != null && assistantHGInfo.getSellProject().getId() != null)
/* 161*/                            fic.add(new FilterItemInfo("building.sellProject.id", assistantHGInfo.getSellProject().getId()));


/* 164*/                        if(assistantHGInfo.getBuilding() != null && assistantHGInfo.getBuilding().getId() != null)
                                {/* 165*/                            fic.add(new FilterItemInfo("building.id", assistantHGInfo.getBuilding().getId()));


/* 168*/                            if(assistantHGInfo.getSellProject() != null)/* 168*/                                if(assistantHGInfo.getSellProject().getId() == null);
                                }
                            }






/* 177*/                String strMethod = "getDataBaseCollection";
/* 178*/                Class argTypes[] = {/* 178*/                    view.getClass()
                        };/* 179*/                Object args[] = {/* 179*/                    view
                        };/* 180*/                printLog((new StringBuilder()).append("filter hpw: ").append(filter).append(";").append(view).toString());
/* 181*/                Object result = RunningBusinessMethod.runLocalMethod(ctx, strFacotry, strLocal, strMethod, argTypes, args);

/* 183*/                if(result != null)
                        {/* 184*/                    DataBaseCollection collection = (DataBaseCollection)result;
/* 185*/                    if(collection.size() != 0)
                            {/* 186*/                        printLog((new StringBuilder()).append("InitAcctCussentImportFDC hpw: ").append(collection.size()).toString());
/* 187*/                        if(collection.size() > 1)
                                {/* 188*/                            throw new Exception((new StringBuilder()).append(strAsstActTypeName).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "1009_InitImportBase", ctx.getLocale())).toString());
                                } else
                                {/* 190*/                            data = collection.get(0);
/* 191*/                            return data;
                                }
                            }
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 195*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 196*/            printLog((new StringBuilder()).append("findAsstActObject in exception.").append(e.getMessage()).append(";").append(e).toString());
/* <-MISALIGNED-> */ /* 197*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /* 199*/        printLog("over hpw: ");
/* <-MISALIGNED-> */ /* 200*/        return null;
            }
            public static AsstActTypeInfo findAsstActtypeNumberByAttribute(Context ctx, String asstHGAttribute)
                throws BOSException
            {



/* 211*/        if(asstHGAttribute == null || asstHGAttribute.trim().equals(""))
/* 212*/            return null;

/* 214*/        IAsstActType asstActType = null;
/* 215*/        if(ctx != null)
/* 216*/            asstActType = AsstActTypeFactory.getLocalInstance(ctx);

/* 218*/        else/* 218*/            asstActType = AsstActTypeFactory.getRemoteInstance();

/* 220*/        EntityViewInfo view = new EntityViewInfo();
/* 221*/        SelectorItemCollection sic = view.getSelector();
/* 222*/        sic.add(new SelectorItemInfo("id"));
/* 223*/        sic.add(new SelectorItemInfo("number"));
/* 224*/        sic.add(new SelectorItemInfo("name"));
/* 225*/        sic.add(new SelectorItemInfo("asstHGAttribute"));
/* 226*/        sic.add(new SelectorItemInfo("useLongNumber"));
/* 227*/        sic.add(new SelectorItemInfo("mappingFieldName"));
/* 228*/        sic.add(new SelectorItemInfo("isMultilevel"));
/* 229*/        sic.add(new SelectorItemInfo("groupTableName"));
/* 230*/        sic.add(new SelectorItemInfo("useLongNumber"));
/* 231*/        sic.add(new SelectorItemInfo("glAsstActTypeGrp.id"));
/* 232*/        sic.add(new SelectorItemInfo("glAsstActTypeGrp.shareTactic"));
/* 233*/        FilterInfo filter = new FilterInfo();
/* 234*/        view.setFilter(filter);
/* 235*/        FilterItemCollection fic = filter.getFilterItems();
/* 236*/        fic.add(new FilterItemInfo("asstHGAttribute", asstHGAttribute.trim()));
/* 237*/        AsstActTypeInfo asstActTypeInfo = null;
/* 238*/        AsstActTypeCollection asstActTypeCollection = asstActType.getAsstActTypeCollection(view);
/* 239*/        if(asstActTypeCollection != null && asstActTypeCollection.size() != 0)
/* 240*/            asstActTypeInfo = asstActTypeCollection.get(0);


/* 243*/        return asstActTypeInfo;
            }
            public static FilterInfo getDFilterInfo(IDataBaseD iDaba, String cuid)
                throws Exception
            {


/* 250*/        ObjectUuidPK pk = new ObjectUuidPK(cuid);

/* 252*/        return iDaba.getDatabaseDFilter(pk, "id", "adminCU.id");
            }
            private static Logger logger = Logger.getLogger(com.kingdee.eas.fi.gl.InitAssistBalanceImportFDC.class);
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\zd\lib\industry\fdc_merge-server.jar
	Total time: 39 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/