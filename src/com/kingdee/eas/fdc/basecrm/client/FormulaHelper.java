package com.kingdee.eas.fdc.basecrm.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.facade.FacadeInfo;
import com.kingdee.bos.service.formula.api.FormulaVarInfoParser;
import com.kingdee.bos.service.formula.api.FormulaVarUtil;
import com.kingdee.bos.service.formula.api.IVarInfo;
import com.kingdee.bos.service.formula.api.VarInfo;
import com.kingdee.eas.base.forewarn.ForewarnConstant;
import com.kingdee.eas.base.forewarn.ForewarnObjectInfo;
import com.kingdee.eas.base.forewarn.ForewarnObjectType;
import com.kingdee.eas.base.forewarn.MetadataType;

public class FormulaHelper {

	
	/**
	 * 处理对象脚本
	 * @param objects
	 * @param script
	 * @return
	 */
	public static String processObjectScript(FormulaVarInfo[] objects,String script)		{
			String formula ;
			if(script == null){
				formula = "";			
			}else{
				formula = script;
			}
			
			Set varAliases = getVarName(script);
			Map varInfos = getVarInfos(objects);
			int size = objects.length;
			for(int i = 0; i < size; i++)	{
				FormulaVarInfo object = objects[i];
				if(!varAliases.contains(object.getAlias()))		{
					VarInfo varInfo = (VarInfo)varInfos.get(object.getAlias());
					formula =  FormulaVarUtil.genVarStr(varInfo.getVarName(), varInfo.getVarAlias(),
									varInfo.getVarType(),	null, varInfo.getVarScope(),	varInfo.getVarDesc()) 
									+ "\n" + formula;
				}
			}
			return formula;
	}
	
	private static Map getVarInfos(FormulaVarInfo[] objects)	{
		Map vars = new HashMap();
		int size = objects.length;
		for(int i = 0; i < size; i++)	{	
			FormulaVarInfo object = objects[i];
			if(object.getMetadataType()!=null) {
				if(object.getMetadataType().equals(MetadataType.ENTITY))		{
						EntityObjectInfo entity = MetaDataLoaderFactory.getRemoteMetaDataLoader()
											.getEntity(new MetaDataPK(object.getType()));
						String name = getDifferentName(vars, getName(object.getName()));
						String alias = object.getAlias();
						String type = entity.getObjectValueClass();
						String initValue = null;
						String scope = ForewarnConstant.LOCAL;
						String description = object.getDescription() == null ? "" : object.getDescription();
						VarInfo varInfo = new VarInfo(name, alias, type, initValue, scope, description);
						vars.put(alias,varInfo);
				}else if(object.getMetadataType().equals(MetadataType.FACADE))		{
					FacadeInfo facadeInfo = MetaDataLoaderFactory.getRemoteMetaDataLoader()
										.getFacade(new MetaDataPK(object.getType()));
					String name = getDifferentName(vars, getName(object.getName()));
					String alias = object.getAlias();
					String type = facadeInfo.getBusinessInterface();
					String initValue = null;
					String scope = ForewarnConstant.LOCAL;
					String description = object.getDescription() == null ? "" : object.getDescription();
					VarInfo varInfo = new VarInfo(name, alias, type, initValue, scope, description);
					vars.put(alias,varInfo);
				}
			}else{				
				VarInfo varInfo = new VarInfo(object.getName(), object.getAlias(), object.getType()
										, null,	ForewarnConstant.LOCAL, "");
				vars.put(object.getAlias(),varInfo);
			}
		}
		return vars;
	}
	
	private static Set getVarName(String script) {
		IVarInfo[] infos = null;
		if(script == null)	{
			script = "";
		}
		if(!script.trim().equalsIgnoreCase(""))		{
			infos = getVarInfos(script);
		}
		if(infos == null)		{
			return new HashSet();
		}else{
			Set set = new HashSet();
			int length = infos.length;
			for(int j = 0; j < length; j++)			{
				set.add(infos[j].getVarAlias());
			}
			return set;
		}
	}
	
	/**
	 * 得到公式脚本的参数列表
	 * @param formula
	 * @return
	 */
    public static IVarInfo[] getVarInfos(String formula)    {
        IVarInfo[] infos=null;
        FormulaVarInfoParser formulaVarInfoParser=new FormulaVarInfoParser();
        infos = formulaVarInfoParser.getFormulaVars(formula);
        return infos;
    }
	
	private static String getName(String name)
	{
		int index = name.lastIndexOf(".");
		if(index != -1)
		{
			return name.substring(index + 1);
		}
		else
		{
			return name;
		}
	}
	
	private static String getDifferentName(Map vars, String name)
	{
		while(hasSameVarName(vars, name))
		{
			name = name + "1"; 		
		}
		return name;
	}
	
	private static boolean hasSameVarName(Map vars, String name)
	{
		Collection collection  = vars.values();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext())
		{
			VarInfo info = (VarInfo)iterator.next();
			if(info.getVarName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
	
	
}
