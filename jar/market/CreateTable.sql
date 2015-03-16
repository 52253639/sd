Create Table T_MAR_AreaSet ( FMinArea NUMERIC(28,10),FMaxArea NUMERIC(28,10),FMinCompare VARCHAR(100),FMaxCompare VARCHAR(100),FMinAreaVal NVARCHAR(100),FMaxAreaVal NVARCHAR(100),FCompanyIdID VARCHAR(44),FCompanyNum NVARCHAR(100),FIsEnabled INT,FName_l1 NVARCHAR(255),FName_l2 NVARCHAR(255),FName_l3 NVARCHAR(255),FNumber NVARCHAR(80),FDescription_l1 NVARCHAR(255),FDescription_l2 NVARCHAR(255),FDescription_l3 NVARCHAR(255),FSimpleName NVARCHAR(80),FCreatorID VARCHAR(44),FCreateTime DateTime,FLastUpdateUserID VARCHAR(44),FLastUpdateTime DateTime,FControlUnitID VARCHAR(44),FID VARCHAR(44) DEFAULT '' NOT NULL ,CONSTRAINT PK_AreaSet PRIMARY KEY (FID));



drop table T_MAR_MeasurePlanTarget;

Create Table T_MAR_MeasurePlanTarget ( FMeasurePhasesID VARCHAR(44) DEFAULT '',FProjectAginID VARCHAR(44) DEFAULT '',FVersions NVARCHAR(50),FVersionsName NVARCHAR(100),FMeasureType VARCHAR(100),FAdjustCause NVARCHAR(500),FTotalAmount NUMERIC(28,10),FRemarks NVARCHAR(500),FIsNew INT,FOrgUnitID VARCHAR(44),FState VARCHAR(100),FName NVARCHAR(80),FOriginalAmount NUMERIC(4,4) DEFAULT 0,FAmount NUMERIC(28,10),FAuditTime DateTime,FBookedDate DateTime,FPeriodId VARCHAR(44),FIsRespite INT,FNumber NVARCHAR(80),FBizDate DateTime,FHandlerID VARCHAR(44),FDescription NVARCHAR(80),FHasEffected INT,FAuditorID VARCHAR(44),FSourceBillID NVARCHAR(80),FSourceFunction NVARCHAR(80),FCreatorID VARCHAR(44),FCreateTime DateTime,FLastUpdateUserID VARCHAR(44),FLastUpdateTime DateTime,FControlUnitID VARCHAR(44),FID VARCHAR(44) DEFAULT '' NOT NULL ,FVersionType VARCHAR(44) DEFAULT '' NOT NULL ,CONSTRAINT PK_MeasurePlanTarg PRIMARY KEY (FID));


drop table T_MAR_MeasurePlanTargetEntry;

Create Table T_MAR_MeasurePlanTargetEntry ( FParentID VARCHAR(44),FProductTypeID VARCHAR(44),FProductConstitute VARCHAR(100),FAcreage NUMERIC(28,10),FQuantity INT,FPrice NUMERIC(28,10),FSumAmount NUMERIC(28,10),FAreaRange NVARCHAR(100),FNewAreaRange NVARCHAR(100) DEFAULT '',FSeq INT,FID VARCHAR(44) DEFAULT '' NOT NULL ,CONSTRAINT PK_MeasurePTarg PRIMARY KEY (FID));

