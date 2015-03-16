package com.kingdee.eas.fdc.basecrm.client;

import com.kingdee.eas.base.forewarn.MetadataType;

public class FormulaVarInfo {

	MetadataType metadataType;
	String name;
	String alias;
	String description;	
	String type;
	
	public FormulaVarInfo(String alias, String description,
			MetadataType metadataType, String name ,String type) {
		super();
		this.alias = alias;
		this.description = description;
		this.metadataType = metadataType;
		this.name = name;
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public MetadataType getMetadataType() {
		return metadataType;
	}
	public void setMetadataType(MetadataType metadataType) {
		this.metadataType = metadataType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
