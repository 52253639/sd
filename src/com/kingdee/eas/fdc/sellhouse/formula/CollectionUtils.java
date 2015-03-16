package com.kingdee.eas.fdc.sellhouse.formula;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.kscript.ParserException;
import com.kingdee.bos.kscript.runtime.Interpreter;
import com.kingdee.bos.kscript.runtime.InterpreterException;
import com.kingdee.bos.metadata.bo.BusinessObjectInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.service.formula.api.FormulaVarInfoParser;
import com.kingdee.bos.service.formula.api.IVarInfo;
import com.kingdee.bos.service.formula.engine.RunFormulaException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.fm.common.EJBAccessFactory;

public class CollectionUtils {
	private static String METER_TYPE_ARBOSTYPE = "arBosTyps";
	private static String METER_TYPE_PMBOSTYPE = "pmBosTyps";
	private	static String roomBosType = "903E0236";
//	private	static String fdccustomerBosType = "682588A8";
	private static String[] arBosTyps = new String[]{roomBosType };
    private static String[] pmBosTyps = new String[]{};
    /**
	 * 生成代收费用传入公式编辑器的业务对象
	 * @return
	 */
	public static BusinessObjectInfo[] createArBizObjects(){
		return createMeterObjects(METER_TYPE_ARBOSTYPE);
	}
	
	/**
	 * 生成传入公式编辑器的业务对象
	 * @param meterType
	 * @return
	 */
	public static BusinessObjectInfo[] createMeterObjects(String meterType){
		BusinessObjectInfo[] results = null;
		EntityObjectInfo entityInfo = null;
		try {
			if(METER_TYPE_ARBOSTYPE.equals(meterType)){
				results = new EntityObjectInfo[arBosTyps.length];
				for (int i = 0; i < results.length; i++) {
					entityInfo = EJBAccessFactory.createRemoteInstance().getEntityInfo(BOSObjectType.create(arBosTyps[i]));
					results[i] = entityInfo;
				} 
			}else if(METER_TYPE_PMBOSTYPE.equals(meterType)) {
				results = new EntityObjectInfo[pmBosTyps.length];
				for (int i = 0; i < results.length; i++) {
					entityInfo = EJBAccessFactory.createRemoteInstance().getEntityInfo(BOSObjectType.create(pmBosTyps[i]));
					results[i] = entityInfo;
				} 
			}
		}catch (BOSException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static String[][] createArVars(IVarInfo[] myVarInfos){
		String[][] result = new String[][]{
				{"DealTotalAmount","成交总价","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"StandardTotalAmount","标准总价","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"BuildingArea","建筑面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"RoomArea","套内面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"buildingProperty","建筑性质","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"certificateName","证件名称","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"PropertyFeatures","置业特征","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"actualBuildingArea","实测建筑面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"actualRoomArea","实测套内面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"compensateAmount","补差款","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""}
		};
		if(myVarInfos == null) return result;
		String[][] result1 = new String[myVarInfos.length][5];
		for (int i = 0; i < myVarInfos.length; i++) {
			result1[i]= new String[]{myVarInfos[i].getVarName(), myVarInfos[i].getVarAlias(), myVarInfos[i].getVarType(), myVarInfos[i].getVarScope(), myVarInfos[i].getVarDesc()};
		}
//		for (int i = 0; i < result.length; i++){
//			result1[i+myVarInfos.length]=result[i];
//		}
		return result1;
	}
	
	public static String[][] createArVars(){
		String[][] result = new String[][]{
//				{"RoomInfo","房间","com.kingdee.eas.fdc.sellhouse.RoomInfo",IVarInfo.VAR_SCOPE_LOCAL,""},
//				{"FdccustomerInfo","认购单","com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo",IVarInfo.VAR_SCOPE_LOCAL,""},
				{"DealTotalAmount","成交总价","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"StandardTotalAmount","标准总价","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"BuildingArea","建筑面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"RoomArea","套内面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"buildingProperty","建筑性质","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"certificateName","证件名称","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"PropertyFeatures","置业特征","STRING",IVarInfo.VAR_SCOPE_IN,""},
				{"actualBuildingArea","实测建筑面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"actualRoomArea","实测套内面积","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""},
				{"compensateAmount","补差款","BIGDECIMAL",IVarInfo.VAR_SCOPE_IN,""}
		};
		return result;
	}
	
	/**
	 * 根据公式求值 从FormulaEngine类copy过来
	 * @param formulaStr
	 * @param params  
	 * @param envCtx
	 * @return
	 * example:
	 * if(formula != null){
    		RoomChargeInfo roomCharge = new RoomChargeInfo();
    		roomCharge.setUnitPrice(new BigDecimal("11.5"));
    		Object[] arObjs = ChargeFormulaUtils.getArObjs4Formula(roomCharge);
    		Object obj = ChargeFormulaUtils.runFormula(formula, arObjs, null);
    		System.out.println(obj);
    	}
	 * @throws RunFormulaException
	 */
	public static Object runFormula(String formulaStr, Object[] params, Context envCtx)
	throws RunFormulaException {
		if (formulaStr == null)
			throw new RunFormulaException("Formula string couldn't be null.");

		HashMap paramMap = new HashMap();
		IVarInfo[] varInfos = new FormulaVarInfoParser().getFormulaVars(formulaStr);
		int paramIdx = 0;
		if (varInfos != null && params != null)
			for (int i=0; i<varInfos.length; i++) {
//				FormulaEngine 中是IVarInfo.VAR_SCOPE_IN, 改成 IVarInfo.VAR_SCOPE_LOCAL
				if (IVarInfo.VAR_SCOPE_LOCAL.equals(varInfos[i].getVarScope())
			      || IVarInfo.VAR_SCOPE_IN.equals(varInfos[i].getVarScope())
				) {
					if (params.length > paramIdx)
						paramMap.put(varInfos[i].getVarName(), params[i]);
					else
						break;
					paramIdx++;
				}
			}
		return runFormula(formulaStr, paramMap, envCtx);
	}

	public static Object runFormula(String formulaStr, Map paramCtx, Context envCtx)
	throws RunFormulaException {
		//    if (formulaStr == null)
		//        throw new RunFormulaException("Formula string couldn't be null.");
		//    Interpreter interpreter = new Interpreter(envCtx);         
		//    Object rtnValue = null;
		//    try {
		//        rtnValue = interpreter.evalForMultiPass(formulaStr, paramCtx);
		//    } catch (InterpreterException e) {
		//        throw new RunFormulaException(e);
		//    } catch (ParserException e) {
		//        throw new RunFormulaException(e);
		//    }
		//    return rtnValue;

		Interpreter interpreter = new Interpreter(envCtx);         
		return runFormula(interpreter, formulaStr, paramCtx, envCtx);
	}

	public static Object runFormula(Interpreter interpreter, String formulaStr, Map paramCtx, Context envCtx)
	throws RunFormulaException{
		if (formulaStr == null)
			throw new RunFormulaException("Formula string couldn't be null.");
		//    Interpreter interpreter = new Interpreter(envCtx);         
		Object rtnValue = null;
		try {
			rtnValue = interpreter.evalForMultiPass(formulaStr, paramCtx);
		} catch (InterpreterException e) {
			throw new RunFormulaException(e);
		} catch (ParserException e) {
			throw new RunFormulaException(e);
		}
		return rtnValue;
	}

}
