package com.kingdee.eas.fdc.tenancy;

import java.util.List;

import com.kingdee.bos.Context;
import com.kingdee.bos.kscript.KScriptException;

public abstract class AbstractFuncInfo {
	 public static final String CATEGORY_MATH = "租赁取数函数";

	    String funcName, funcCategory, funcDesc;

	    /**
	     * @return 返回 funcCategory。
	     */
	    public String getFuncCategory() {
	        return funcCategory;
	    }

	    /**
	     * @param funcCategory
	     *            要设置的 funcCategory。
	     */
	    public void setFuncCategory(String funcCategory) {
	        this.funcCategory = funcCategory;
	    }

	    /**
	     * @return 返回 funcDesc。
	     */
	    public String getFuncDesc() {
	        return funcDesc;
	    }

	    /**
	     * @param funcDesc
	     *            要设置的 funcDesc。
	     */
	    public void setFuncDesc(String funcDesc) {
	        this.funcDesc = funcDesc;
	    }

	    /**
	     * @return 返回 funcName。
	     */
	    public String getFuncName() {
	        return funcName;
	    }

	    /**
	     * @param funcName
	     *            要设置的 funcName。
	     */
	    public void setFuncName(String funcName) {
	        this.funcName = funcName;
	    }

	    public AbstractFuncInfo(String name, String category, String desc) {
	        funcName = name;
	        funcCategory = category;
	        funcDesc = desc;
	    }

	    public AbstractFuncInfo() {
	    }

	    abstract public Object evalFunction(List args) throws KScriptException;

	    /**
	     * @param args
	     * @return
	     */
	    protected Context getContext(List args) {
	        Context ctx = (Context) args.get(0);
	        return ctx;
	    }
}
