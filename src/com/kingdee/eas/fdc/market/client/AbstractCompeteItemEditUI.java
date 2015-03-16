/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractCompeteItemEditUI extends com.kingdee.eas.fdc.propertymgmt.client.PPMProjectBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompeteItemEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contselectProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvestor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contagent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcoverArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contarchitectArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcubageRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbruntRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbruntArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdonateArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contparkingLot;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsaleStute;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfinishTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpropertyCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjoinTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfromTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttoTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotleYears;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblyear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmanageCharge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsaleRoomTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewestAvrPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewestAvgAllAm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewestInitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewestHighPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contadress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contvillageSupFacil;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contenvMunicipalInf;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl39;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeveloper;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellproject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinvestor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtagent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcoverArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtarchitectArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcubageRate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtproductType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbruntRoomType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbruntArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdonateArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtparkingLot;
    protected com.kingdee.bos.ctrl.swing.KDComboBox saleStute;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfinishTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpropertyCompany;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkjoinTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfromTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktoTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttotleYears;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmanageCharge;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsaleRoomTel;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtnewestAvrPrice;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtnewestAvgAllAm;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtnewestInitPrice;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtnewestHighPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtadress;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanevillageSupFacil;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtvillageSupFacil;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneenvMunicipalInf;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtenvMunicipalInf;
    protected com.kingdee.bos.ctrl.swing.KDPanel priceInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel saleInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel marketInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel useMedia;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPriceInfoEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPriceInfoEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSaleInfoEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtSaleInfoEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtMarketingEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtMarketingEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtUseMediaEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtUseMediaEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDeveloper;
    protected com.kingdee.eas.fdc.market.CompeteItemInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCompeteItemEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCompeteItemEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAttachment
        String _tempStr = null;
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        actionAttachment.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift H"));
        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contselectProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvestor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contagent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcoverArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contarchitectArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcubageRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contproductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbruntRoomType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbruntArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdonateArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contparkingLot = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsaleStute = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfinishTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpropertyCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjoinTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfromTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttoTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotleYears = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblyear = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contmanageCharge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsaleRoomTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewestAvrPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewestAvgAllAm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewestInitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewestHighPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contadress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contvillageSupFacil = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contenvMunicipalInf = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.paneBIZLayerControl39 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contDeveloper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmtsellproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtinvestor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtagent = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcoverArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtarchitectArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcubageRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtproductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbruntRoomType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbruntArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtdonateArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtparkingLot = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.saleStute = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkstartTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkfinishTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtpropertyCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkjoinTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkfromTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pktoTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txttotleYears = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtmanageCharge = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtsaleRoomTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtnewestAvrPrice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtnewestAvgAllAm = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtnewestInitPrice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtnewestHighPrice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtadress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.scrollPanevillageSupFacil = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtvillageSupFacil = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneenvMunicipalInf = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtenvMunicipalInf = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.priceInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.saleInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.marketInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.useMedia = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtPriceInfoEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSaleInfoEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtMarketingEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtUseMediaEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtDeveloper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contselectProject.setName("contselectProject");
        this.continvestor.setName("continvestor");
        this.contagent.setName("contagent");
        this.contcoverArea.setName("contcoverArea");
        this.contarchitectArea.setName("contarchitectArea");
        this.contcubageRate.setName("contcubageRate");
        this.contproductType.setName("contproductType");
        this.contbruntRoomType.setName("contbruntRoomType");
        this.contbruntArea.setName("contbruntArea");
        this.contdonateArea.setName("contdonateArea");
        this.contparkingLot.setName("contparkingLot");
        this.contsaleStute.setName("contsaleStute");
        this.contstartTime.setName("contstartTime");
        this.contfinishTime.setName("contfinishTime");
        this.contpropertyCompany.setName("contpropertyCompany");
        this.contjoinTime.setName("contjoinTime");
        this.contfromTime.setName("contfromTime");
        this.conttoTime.setName("conttoTime");
        this.conttotleYears.setName("conttotleYears");
        this.lblyear.setName("lblyear");
        this.contmanageCharge.setName("contmanageCharge");
        this.contsaleRoomTel.setName("contsaleRoomTel");
        this.contnewestAvrPrice.setName("contnewestAvrPrice");
        this.contnewestAvgAllAm.setName("contnewestAvgAllAm");
        this.contnewestInitPrice.setName("contnewestInitPrice");
        this.contnewestHighPrice.setName("contnewestHighPrice");
        this.contadress.setName("contadress");
        this.contvillageSupFacil.setName("contvillageSupFacil");
        this.contenvMunicipalInf.setName("contenvMunicipalInf");
        this.paneBIZLayerControl39.setName("paneBIZLayerControl39");
        this.contDeveloper.setName("contDeveloper");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtDescription.setName("txtDescription");
        this.prmtsellproject.setName("prmtsellproject");
        this.txtinvestor.setName("txtinvestor");
        this.txtagent.setName("txtagent");
        this.txtcoverArea.setName("txtcoverArea");
        this.txtarchitectArea.setName("txtarchitectArea");
        this.txtcubageRate.setName("txtcubageRate");
        this.prmtproductType.setName("prmtproductType");
        this.txtbruntRoomType.setName("txtbruntRoomType");
        this.txtbruntArea.setName("txtbruntArea");
        this.txtdonateArea.setName("txtdonateArea");
        this.txtparkingLot.setName("txtparkingLot");
        this.saleStute.setName("saleStute");
        this.pkstartTime.setName("pkstartTime");
        this.pkfinishTime.setName("pkfinishTime");
        this.txtpropertyCompany.setName("txtpropertyCompany");
        this.pkjoinTime.setName("pkjoinTime");
        this.pkfromTime.setName("pkfromTime");
        this.pktoTime.setName("pktoTime");
        this.txttotleYears.setName("txttotleYears");
        this.txtmanageCharge.setName("txtmanageCharge");
        this.txtsaleRoomTel.setName("txtsaleRoomTel");
        this.txtnewestAvrPrice.setName("txtnewestAvrPrice");
        this.txtnewestAvgAllAm.setName("txtnewestAvgAllAm");
        this.txtnewestInitPrice.setName("txtnewestInitPrice");
        this.txtnewestHighPrice.setName("txtnewestHighPrice");
        this.txtadress.setName("txtadress");
        this.scrollPanevillageSupFacil.setName("scrollPanevillageSupFacil");
        this.txtvillageSupFacil.setName("txtvillageSupFacil");
        this.scrollPaneenvMunicipalInf.setName("scrollPaneenvMunicipalInf");
        this.txtenvMunicipalInf.setName("txtenvMunicipalInf");
        this.priceInfo.setName("priceInfo");
        this.saleInfo.setName("saleInfo");
        this.marketInfo.setName("marketInfo");
        this.useMedia.setName("useMedia");
        this.kdtPriceInfoEntry.setName("kdtPriceInfoEntry");
        this.kdtSaleInfoEntry.setName("kdtSaleInfoEntry");
        this.kdtMarketingEntry.setName("kdtMarketingEntry");
        this.kdtUseMediaEntry.setName("kdtUseMediaEntry");
        this.prmtDeveloper.setName("prmtDeveloper");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setForeground(new java.awt.Color(0,0,0));
        // contselectProject		
        this.contselectProject.setBoundLabelText(resHelper.getString("contselectProject.boundLabelText"));		
        this.contselectProject.setBoundLabelLength(100);		
        this.contselectProject.setBoundLabelUnderline(true);		
        this.contselectProject.setVisible(true);		
        this.contselectProject.setBoundLabelAlignment(7);		
        this.contselectProject.setForeground(new java.awt.Color(0,0,0));
        // continvestor		
        this.continvestor.setBoundLabelText(resHelper.getString("continvestor.boundLabelText"));		
        this.continvestor.setBoundLabelLength(100);		
        this.continvestor.setBoundLabelUnderline(true);		
        this.continvestor.setVisible(true);		
        this.continvestor.setBoundLabelAlignment(7);		
        this.continvestor.setForeground(new java.awt.Color(0,0,0));
        // contagent		
        this.contagent.setBoundLabelText(resHelper.getString("contagent.boundLabelText"));		
        this.contagent.setBoundLabelLength(100);		
        this.contagent.setBoundLabelUnderline(true);		
        this.contagent.setVisible(true);		
        this.contagent.setBoundLabelAlignment(7);		
        this.contagent.setForeground(new java.awt.Color(0,0,0));
        // contcoverArea		
        this.contcoverArea.setBoundLabelText(resHelper.getString("contcoverArea.boundLabelText"));		
        this.contcoverArea.setBoundLabelLength(100);		
        this.contcoverArea.setBoundLabelUnderline(true);		
        this.contcoverArea.setVisible(true);		
        this.contcoverArea.setForeground(new java.awt.Color(0,0,0));		
        this.contcoverArea.setBoundLabelAlignment(7);
        // contarchitectArea		
        this.contarchitectArea.setBoundLabelText(resHelper.getString("contarchitectArea.boundLabelText"));		
        this.contarchitectArea.setBoundLabelLength(100);		
        this.contarchitectArea.setBoundLabelUnderline(true);		
        this.contarchitectArea.setVisible(true);		
        this.contarchitectArea.setForeground(new java.awt.Color(0,0,0));		
        this.contarchitectArea.setBoundLabelAlignment(7);
        // contcubageRate		
        this.contcubageRate.setBoundLabelText(resHelper.getString("contcubageRate.boundLabelText"));		
        this.contcubageRate.setBoundLabelLength(100);		
        this.contcubageRate.setBoundLabelUnderline(true);		
        this.contcubageRate.setVisible(true);		
        this.contcubageRate.setBoundLabelAlignment(7);		
        this.contcubageRate.setForeground(new java.awt.Color(0,0,0));
        // contproductType		
        this.contproductType.setBoundLabelText(resHelper.getString("contproductType.boundLabelText"));		
        this.contproductType.setBoundLabelLength(100);		
        this.contproductType.setBoundLabelUnderline(true);		
        this.contproductType.setVisible(true);		
        this.contproductType.setBoundLabelAlignment(7);		
        this.contproductType.setForeground(new java.awt.Color(0,0,0));
        // contbruntRoomType		
        this.contbruntRoomType.setBoundLabelText(resHelper.getString("contbruntRoomType.boundLabelText"));		
        this.contbruntRoomType.setBoundLabelLength(100);		
        this.contbruntRoomType.setBoundLabelUnderline(true);		
        this.contbruntRoomType.setVisible(true);		
        this.contbruntRoomType.setBoundLabelAlignment(7);		
        this.contbruntRoomType.setForeground(new java.awt.Color(0,0,0));
        // contbruntArea		
        this.contbruntArea.setBoundLabelText(resHelper.getString("contbruntArea.boundLabelText"));		
        this.contbruntArea.setBoundLabelLength(100);		
        this.contbruntArea.setBoundLabelUnderline(true);		
        this.contbruntArea.setVisible(true);		
        this.contbruntArea.setBoundLabelAlignment(7);		
        this.contbruntArea.setForeground(new java.awt.Color(0,0,0));
        // contdonateArea		
        this.contdonateArea.setBoundLabelText(resHelper.getString("contdonateArea.boundLabelText"));		
        this.contdonateArea.setBoundLabelLength(100);		
        this.contdonateArea.setBoundLabelUnderline(true);		
        this.contdonateArea.setVisible(true);		
        this.contdonateArea.setBoundLabelAlignment(7);		
        this.contdonateArea.setForeground(new java.awt.Color(0,0,0));
        // contparkingLot		
        this.contparkingLot.setBoundLabelText(resHelper.getString("contparkingLot.boundLabelText"));		
        this.contparkingLot.setBoundLabelLength(100);		
        this.contparkingLot.setBoundLabelUnderline(true);		
        this.contparkingLot.setVisible(true);		
        this.contparkingLot.setBoundLabelAlignment(7);		
        this.contparkingLot.setForeground(new java.awt.Color(0,0,0));
        // contsaleStute		
        this.contsaleStute.setBoundLabelText(resHelper.getString("contsaleStute.boundLabelText"));		
        this.contsaleStute.setBoundLabelLength(100);		
        this.contsaleStute.setBoundLabelUnderline(true);		
        this.contsaleStute.setVisible(true);		
        this.contsaleStute.setBoundLabelAlignment(7);		
        this.contsaleStute.setForeground(new java.awt.Color(0,0,0));
        // contstartTime		
        this.contstartTime.setBoundLabelText(resHelper.getString("contstartTime.boundLabelText"));		
        this.contstartTime.setBoundLabelLength(100);		
        this.contstartTime.setBoundLabelUnderline(true);		
        this.contstartTime.setVisible(true);		
        this.contstartTime.setBoundLabelAlignment(7);		
        this.contstartTime.setForeground(new java.awt.Color(0,0,0));
        // contfinishTime		
        this.contfinishTime.setBoundLabelText(resHelper.getString("contfinishTime.boundLabelText"));		
        this.contfinishTime.setBoundLabelLength(100);		
        this.contfinishTime.setBoundLabelUnderline(true);		
        this.contfinishTime.setVisible(true);		
        this.contfinishTime.setBoundLabelAlignment(7);		
        this.contfinishTime.setForeground(new java.awt.Color(0,0,0));
        // contpropertyCompany		
        this.contpropertyCompany.setBoundLabelText(resHelper.getString("contpropertyCompany.boundLabelText"));		
        this.contpropertyCompany.setBoundLabelLength(100);		
        this.contpropertyCompany.setBoundLabelUnderline(true);		
        this.contpropertyCompany.setVisible(true);		
        this.contpropertyCompany.setBoundLabelAlignment(7);		
        this.contpropertyCompany.setForeground(new java.awt.Color(0,0,0));
        // contjoinTime		
        this.contjoinTime.setBoundLabelText(resHelper.getString("contjoinTime.boundLabelText"));		
        this.contjoinTime.setBoundLabelLength(100);		
        this.contjoinTime.setBoundLabelUnderline(true);		
        this.contjoinTime.setVisible(true);		
        this.contjoinTime.setBoundLabelAlignment(7);		
        this.contjoinTime.setForeground(new java.awt.Color(0,0,0));
        // contfromTime		
        this.contfromTime.setBoundLabelText(resHelper.getString("contfromTime.boundLabelText"));		
        this.contfromTime.setBoundLabelLength(100);		
        this.contfromTime.setBoundLabelUnderline(true);		
        this.contfromTime.setVisible(true);		
        this.contfromTime.setForeground(new java.awt.Color(0,0,0));		
        this.contfromTime.setBoundLabelAlignment(7);
        // conttoTime		
        this.conttoTime.setBoundLabelText(resHelper.getString("conttoTime.boundLabelText"));		
        this.conttoTime.setBoundLabelLength(100);		
        this.conttoTime.setBoundLabelUnderline(true);		
        this.conttoTime.setVisible(true);		
        this.conttoTime.setForeground(new java.awt.Color(0,0,0));		
        this.conttoTime.setBoundLabelAlignment(7);
        // conttotleYears		
        this.conttotleYears.setBoundLabelText(resHelper.getString("conttotleYears.boundLabelText"));		
        this.conttotleYears.setBoundLabelLength(100);		
        this.conttotleYears.setBoundLabelUnderline(true);		
        this.conttotleYears.setVisible(true);		
        this.conttotleYears.setBoundLabelAlignment(7);		
        this.conttotleYears.setForeground(new java.awt.Color(0,0,0));
        // lblyear		
        this.lblyear.setText(resHelper.getString("lblyear.text"));		
        this.lblyear.setVisible(true);		
        this.lblyear.setHorizontalAlignment(0);		
        this.lblyear.setForeground(new java.awt.Color(0,0,0));		
        this.lblyear.setBackground(new java.awt.Color(192,192,192));
        // contmanageCharge		
        this.contmanageCharge.setBoundLabelText(resHelper.getString("contmanageCharge.boundLabelText"));		
        this.contmanageCharge.setBoundLabelLength(100);		
        this.contmanageCharge.setBoundLabelUnderline(true);		
        this.contmanageCharge.setVisible(true);		
        this.contmanageCharge.setBoundLabelAlignment(7);		
        this.contmanageCharge.setForeground(new java.awt.Color(0,0,0));
        // contsaleRoomTel		
        this.contsaleRoomTel.setBoundLabelText(resHelper.getString("contsaleRoomTel.boundLabelText"));		
        this.contsaleRoomTel.setBoundLabelLength(100);		
        this.contsaleRoomTel.setBoundLabelUnderline(true);		
        this.contsaleRoomTel.setVisible(true);		
        this.contsaleRoomTel.setBoundLabelAlignment(7);		
        this.contsaleRoomTel.setForeground(new java.awt.Color(0,0,0));
        // contnewestAvrPrice		
        this.contnewestAvrPrice.setBoundLabelText(resHelper.getString("contnewestAvrPrice.boundLabelText"));		
        this.contnewestAvrPrice.setBoundLabelLength(100);		
        this.contnewestAvrPrice.setBoundLabelUnderline(true);		
        this.contnewestAvrPrice.setVisible(true);		
        this.contnewestAvrPrice.setBoundLabelAlignment(7);		
        this.contnewestAvrPrice.setForeground(new java.awt.Color(0,0,0));
        // contnewestAvgAllAm		
        this.contnewestAvgAllAm.setBoundLabelText(resHelper.getString("contnewestAvgAllAm.boundLabelText"));		
        this.contnewestAvgAllAm.setBoundLabelLength(100);		
        this.contnewestAvgAllAm.setBoundLabelUnderline(true);		
        this.contnewestAvgAllAm.setVisible(true);		
        this.contnewestAvgAllAm.setForeground(new java.awt.Color(0,0,0));		
        this.contnewestAvgAllAm.setBoundLabelAlignment(7);
        // contnewestInitPrice		
        this.contnewestInitPrice.setBoundLabelText(resHelper.getString("contnewestInitPrice.boundLabelText"));		
        this.contnewestInitPrice.setBoundLabelLength(100);		
        this.contnewestInitPrice.setBoundLabelUnderline(true);		
        this.contnewestInitPrice.setVisible(true);		
        this.contnewestInitPrice.setBoundLabelAlignment(7);		
        this.contnewestInitPrice.setForeground(new java.awt.Color(0,0,0));
        // contnewestHighPrice		
        this.contnewestHighPrice.setBoundLabelText(resHelper.getString("contnewestHighPrice.boundLabelText"));		
        this.contnewestHighPrice.setBoundLabelLength(100);		
        this.contnewestHighPrice.setBoundLabelUnderline(true);		
        this.contnewestHighPrice.setVisible(true);		
        this.contnewestHighPrice.setBoundLabelAlignment(7);		
        this.contnewestHighPrice.setForeground(new java.awt.Color(0,0,0));
        // contadress		
        this.contadress.setBoundLabelText(resHelper.getString("contadress.boundLabelText"));		
        this.contadress.setBoundLabelLength(100);		
        this.contadress.setBoundLabelUnderline(true);		
        this.contadress.setVisible(true);		
        this.contadress.setBoundLabelAlignment(7);		
        this.contadress.setForeground(new java.awt.Color(0,0,0));
        // contvillageSupFacil		
        this.contvillageSupFacil.setBoundLabelText(resHelper.getString("contvillageSupFacil.boundLabelText"));		
        this.contvillageSupFacil.setBoundLabelLength(100);		
        this.contvillageSupFacil.setBoundLabelUnderline(true);		
        this.contvillageSupFacil.setVisible(true);		
        this.contvillageSupFacil.setForeground(new java.awt.Color(0,0,0));		
        this.contvillageSupFacil.setBoundLabelAlignment(7);
        // contenvMunicipalInf		
        this.contenvMunicipalInf.setBoundLabelText(resHelper.getString("contenvMunicipalInf.boundLabelText"));		
        this.contenvMunicipalInf.setBoundLabelLength(120);		
        this.contenvMunicipalInf.setBoundLabelUnderline(true);		
        this.contenvMunicipalInf.setVisible(true);		
        this.contenvMunicipalInf.setBoundLabelAlignment(7);		
        this.contenvMunicipalInf.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl39		
        this.paneBIZLayerControl39.setVisible(true);
        // contDeveloper		
        this.contDeveloper.setBoundLabelText(resHelper.getString("contDeveloper.boundLabelText"));		
        this.contDeveloper.setBoundLabelLength(100);		
        this.contDeveloper.setBoundLabelUnderline(true);		
        this.contDeveloper.setVisible(true);		
        this.contDeveloper.setBoundLabelAlignment(7);		
        this.contDeveloper.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(255);
        // txtDescription		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);
        // prmtsellproject		
        this.prmtsellproject.setDisplayFormat("$name$");		
        this.prmtsellproject.setEditFormat("$number$");		
        this.prmtsellproject.setCommitFormat("$number$");		
        this.prmtsellproject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        // txtinvestor		
        this.txtinvestor.setVisible(true);		
        this.txtinvestor.setHorizontalAlignment(2);		
        this.txtinvestor.setMaxLength(100);		
        this.txtinvestor.setRequired(false);		
        this.txtinvestor.setEnabled(true);		
        this.txtinvestor.setForeground(new java.awt.Color(0,0,0));
        // txtagent		
        this.txtagent.setVisible(true);		
        this.txtagent.setHorizontalAlignment(2);		
        this.txtagent.setMaxLength(100);		
        this.txtagent.setRequired(false);		
        this.txtagent.setEnabled(true);		
        this.txtagent.setForeground(new java.awt.Color(0,0,0));
        // txtcoverArea		
        this.txtcoverArea.setVisible(true);		
        this.txtcoverArea.setHorizontalAlignment(2);		
        this.txtcoverArea.setDataType(1);		
        this.txtcoverArea.setForeground(new java.awt.Color(0,0,0));		
        this.txtcoverArea.setSupportedEmpty(true);		
        this.txtcoverArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtcoverArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtcoverArea.setPrecision(2);		
        this.txtcoverArea.setRequired(false);		
        this.txtcoverArea.setEnabled(true);
        // txtarchitectArea		
        this.txtarchitectArea.setVisible(true);		
        this.txtarchitectArea.setHorizontalAlignment(2);		
        this.txtarchitectArea.setDataType(1);		
        this.txtarchitectArea.setForeground(new java.awt.Color(0,0,0));		
        this.txtarchitectArea.setSupportedEmpty(true);		
        this.txtarchitectArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtarchitectArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtarchitectArea.setPrecision(2);		
        this.txtarchitectArea.setRequired(false);		
        this.txtarchitectArea.setEnabled(true);
        // txtcubageRate		
        this.txtcubageRate.setVisible(true);		
        this.txtcubageRate.setHorizontalAlignment(2);		
        this.txtcubageRate.setDataType(1);		
        this.txtcubageRate.setSupportedEmpty(true);		
        this.txtcubageRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtcubageRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtcubageRate.setPrecision(2);		
        this.txtcubageRate.setRequired(false);		
        this.txtcubageRate.setEnabled(true);		
        this.txtcubageRate.setForeground(new java.awt.Color(0,0,0));
        // prmtproductType		
        this.prmtproductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.prmtproductType.setVisible(true);		
        this.prmtproductType.setEditable(true);		
        this.prmtproductType.setDisplayFormat("$name$");		
        this.prmtproductType.setEditFormat("$number$");		
        this.prmtproductType.setCommitFormat("$number$");		
        this.prmtproductType.setRequired(false);		
        this.prmtproductType.setEnabled(true);		
        this.prmtproductType.setForeground(new java.awt.Color(0,0,0));
        // txtbruntRoomType		
        this.txtbruntRoomType.setVisible(true);		
        this.txtbruntRoomType.setHorizontalAlignment(2);		
        this.txtbruntRoomType.setMaxLength(100);		
        this.txtbruntRoomType.setRequired(false);		
        this.txtbruntRoomType.setEnabled(true);		
        this.txtbruntRoomType.setForeground(new java.awt.Color(0,0,0));
        // txtbruntArea		
        this.txtbruntArea.setVisible(true);		
        this.txtbruntArea.setHorizontalAlignment(2);		
        this.txtbruntArea.setDataType(1);		
        this.txtbruntArea.setSupportedEmpty(true);		
        this.txtbruntArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtbruntArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtbruntArea.setPrecision(2);		
        this.txtbruntArea.setRequired(false);		
        this.txtbruntArea.setEnabled(true);		
        this.txtbruntArea.setForeground(new java.awt.Color(0,0,0));
        // txtdonateArea		
        this.txtdonateArea.setVisible(true);		
        this.txtdonateArea.setHorizontalAlignment(2);		
        this.txtdonateArea.setDataType(1);		
        this.txtdonateArea.setSupportedEmpty(true);		
        this.txtdonateArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtdonateArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtdonateArea.setPrecision(2);		
        this.txtdonateArea.setRequired(false);		
        this.txtdonateArea.setEnabled(true);		
        this.txtdonateArea.setForeground(new java.awt.Color(0,0,0));
        // txtparkingLot		
        this.txtparkingLot.setVisible(true);		
        this.txtparkingLot.setHorizontalAlignment(2);		
        this.txtparkingLot.setMaxLength(100);		
        this.txtparkingLot.setRequired(false);		
        this.txtparkingLot.setEnabled(true);		
        this.txtparkingLot.setForeground(new java.awt.Color(0,0,0));
        // saleStute		
        this.saleStute.setVisible(true);		
        this.saleStute.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.SaleState").toArray());		
        this.saleStute.setRequired(false);		
        this.saleStute.setEnabled(true);		
        this.saleStute.setForeground(new java.awt.Color(0,0,0));
        // pkstartTime		
        this.pkstartTime.setVisible(true);		
        this.pkstartTime.setRequired(false);		
        this.pkstartTime.setEnabled(true);		
        this.pkstartTime.setForeground(new java.awt.Color(0,0,0));
        // pkfinishTime		
        this.pkfinishTime.setVisible(true);		
        this.pkfinishTime.setRequired(false);		
        this.pkfinishTime.setEnabled(true);		
        this.pkfinishTime.setForeground(new java.awt.Color(0,0,0));
        // txtpropertyCompany		
        this.txtpropertyCompany.setVisible(true);		
        this.txtpropertyCompany.setHorizontalAlignment(2);		
        this.txtpropertyCompany.setMaxLength(100);		
        this.txtpropertyCompany.setRequired(false);		
        this.txtpropertyCompany.setEnabled(true);		
        this.txtpropertyCompany.setForeground(new java.awt.Color(0,0,0));
        // pkjoinTime		
        this.pkjoinTime.setVisible(true);		
        this.pkjoinTime.setRequired(false);		
        this.pkjoinTime.setEnabled(true);		
        this.pkjoinTime.setForeground(new java.awt.Color(0,0,0));
        // pkfromTime		
        this.pkfromTime.setVisible(true);		
        this.pkfromTime.setForeground(new java.awt.Color(0,0,0));		
        this.pkfromTime.setRequired(true);		
        this.pkfromTime.setEnabled(true);
        this.pkfromTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkfromTime_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.pkfromTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    pkfromTime_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pktoTime		
        this.pktoTime.setVisible(true);		
        this.pktoTime.setForeground(new java.awt.Color(0,0,0));		
        this.pktoTime.setRequired(true);		
        this.pktoTime.setEnabled(true);
        this.pktoTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pktoTime_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.pktoTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    pktoTime_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txttotleYears		
        this.txttotleYears.setVisible(true);		
        this.txttotleYears.setHorizontalAlignment(2);		
        this.txttotleYears.setDataType(1);		
        this.txttotleYears.setSupportedEmpty(true);		
        this.txttotleYears.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txttotleYears.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txttotleYears.setPrecision(3);		
        this.txttotleYears.setRequired(false);		
        this.txttotleYears.setEnabled(true);		
        this.txttotleYears.setForeground(new java.awt.Color(0,0,0));
        // txtmanageCharge		
        this.txtmanageCharge.setVisible(true);		
        this.txtmanageCharge.setHorizontalAlignment(2);		
        this.txtmanageCharge.setDataType(1);		
        this.txtmanageCharge.setSupportedEmpty(true);		
        this.txtmanageCharge.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtmanageCharge.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtmanageCharge.setPrecision(2);		
        this.txtmanageCharge.setRequired(false);		
        this.txtmanageCharge.setEnabled(true);		
        this.txtmanageCharge.setForeground(new java.awt.Color(0,0,0));
        // txtsaleRoomTel		
        this.txtsaleRoomTel.setVisible(true);		
        this.txtsaleRoomTel.setHorizontalAlignment(2);		
        this.txtsaleRoomTel.setMaxLength(100);		
        this.txtsaleRoomTel.setRequired(false);		
        this.txtsaleRoomTel.setEnabled(true);		
        this.txtsaleRoomTel.setForeground(new java.awt.Color(0,0,0));
        // txtnewestAvrPrice		
        this.txtnewestAvrPrice.setPrecision(2);		
        this.txtnewestAvrPrice.setEnabled(false);		
        this.txtnewestAvrPrice.setDataType(6);
        // txtnewestAvgAllAm		
        this.txtnewestAvgAllAm.setPrecision(2);		
        this.txtnewestAvgAllAm.setEnabled(false);
        // txtnewestInitPrice		
        this.txtnewestInitPrice.setPrecision(2);		
        this.txtnewestInitPrice.setEnabled(false);
        // txtnewestHighPrice		
        this.txtnewestHighPrice.setPrecision(2);		
        this.txtnewestHighPrice.setEnabled(false);		
        this.txtnewestHighPrice.setDataType(6);
        // txtadress		
        this.txtadress.setVisible(true);		
        this.txtadress.setHorizontalAlignment(2);		
        this.txtadress.setMaxLength(255);		
        this.txtadress.setRequired(false);		
        this.txtadress.setEnabled(true);		
        this.txtadress.setForeground(new java.awt.Color(0,0,0));
        // scrollPanevillageSupFacil
        // txtvillageSupFacil		
        this.txtvillageSupFacil.setVisible(true);		
        this.txtvillageSupFacil.setForeground(new java.awt.Color(0,0,0));		
        this.txtvillageSupFacil.setRequired(false);		
        this.txtvillageSupFacil.setMaxLength(255);		
        this.txtvillageSupFacil.setEnabled(true);
        // scrollPaneenvMunicipalInf
        // txtenvMunicipalInf		
        this.txtenvMunicipalInf.setVisible(true);		
        this.txtenvMunicipalInf.setRequired(false);		
        this.txtenvMunicipalInf.setMaxLength(255);		
        this.txtenvMunicipalInf.setEnabled(true);		
        this.txtenvMunicipalInf.setForeground(new java.awt.Color(0,0,0));
        // priceInfo		
        this.priceInfo.setVisible(true);
        // saleInfo		
        this.saleInfo.setVisible(true);
        // marketInfo		
        this.marketInfo.setVisible(true);
        // useMedia		
        this.useMedia.setVisible(true);
        // kdtPriceInfoEntry
		String kdtPriceInfoEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pushDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"pushSaleSets\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"pusharchArea\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"avgPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"avgAllAmonut\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"stPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"highestPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"remarkE1\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{pushDate}</t:Cell><t:Cell>$Resource{pushSaleSets}</t:Cell><t:Cell>$Resource{pusharchArea}</t:Cell><t:Cell>$Resource{avgPrice}</t:Cell><t:Cell>$Resource{avgAllAmonut}</t:Cell><t:Cell>$Resource{stPrice}</t:Cell><t:Cell>$Resource{highestPrice}</t:Cell><t:Cell>$Resource{remarkE1}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPriceInfoEntry.setFormatXml(resHelper.translateString("kdtPriceInfoEntry",kdtPriceInfoEntryStrXML));
        this.kdtPriceInfoEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPriceInfoEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPriceInfoEntry.putBindContents("editData",new String[] {"number","pushDate","pushSaleSets","pusharchArea","avgPrice","avgAllAmonut","stPrice","highestPrice","remarkE1"});


        this.kdtPriceInfoEntry.checkParsed();
        KDTextField kdtPriceInfoEntry_number_TextField = new KDTextField();
        kdtPriceInfoEntry_number_TextField.setName("kdtPriceInfoEntry_number_TextField");
        kdtPriceInfoEntry_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtPriceInfoEntry_number_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_number_TextField);
        this.kdtPriceInfoEntry.getColumn("number").setEditor(kdtPriceInfoEntry_number_CellEditor);
        KDDatePicker kdtPriceInfoEntry_pushDate_DatePicker = new KDDatePicker();
        kdtPriceInfoEntry_pushDate_DatePicker.setName("kdtPriceInfoEntry_pushDate_DatePicker");
        kdtPriceInfoEntry_pushDate_DatePicker.setVisible(true);
        kdtPriceInfoEntry_pushDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtPriceInfoEntry_pushDate_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_pushDate_DatePicker);
        this.kdtPriceInfoEntry.getColumn("pushDate").setEditor(kdtPriceInfoEntry_pushDate_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_pushSaleSets_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_pushSaleSets_TextField.setName("kdtPriceInfoEntry_pushSaleSets_TextField");
        kdtPriceInfoEntry_pushSaleSets_TextField.setVisible(true);
        kdtPriceInfoEntry_pushSaleSets_TextField.setEditable(true);
        kdtPriceInfoEntry_pushSaleSets_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_pushSaleSets_TextField.setDataType(0);
        KDTDefaultCellEditor kdtPriceInfoEntry_pushSaleSets_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_pushSaleSets_TextField);
        this.kdtPriceInfoEntry.getColumn("pushSaleSets").setEditor(kdtPriceInfoEntry_pushSaleSets_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_pusharchArea_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_pusharchArea_TextField.setName("kdtPriceInfoEntry_pusharchArea_TextField");
        kdtPriceInfoEntry_pusharchArea_TextField.setVisible(true);
        kdtPriceInfoEntry_pusharchArea_TextField.setEditable(true);
        kdtPriceInfoEntry_pusharchArea_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_pusharchArea_TextField.setDataType(1);
        	kdtPriceInfoEntry_pusharchArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtPriceInfoEntry_pusharchArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtPriceInfoEntry_pusharchArea_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtPriceInfoEntry_pusharchArea_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_pusharchArea_TextField);
        this.kdtPriceInfoEntry.getColumn("pusharchArea").setEditor(kdtPriceInfoEntry_pusharchArea_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_avgPrice_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_avgPrice_TextField.setName("kdtPriceInfoEntry_avgPrice_TextField");
        kdtPriceInfoEntry_avgPrice_TextField.setVisible(true);
        kdtPriceInfoEntry_avgPrice_TextField.setEditable(true);
        kdtPriceInfoEntry_avgPrice_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_avgPrice_TextField.setDataType(1);
        	kdtPriceInfoEntry_avgPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtPriceInfoEntry_avgPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtPriceInfoEntry_avgPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtPriceInfoEntry_avgPrice_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_avgPrice_TextField);
        this.kdtPriceInfoEntry.getColumn("avgPrice").setEditor(kdtPriceInfoEntry_avgPrice_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_avgAllAmonut_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_avgAllAmonut_TextField.setName("kdtPriceInfoEntry_avgAllAmonut_TextField");
        kdtPriceInfoEntry_avgAllAmonut_TextField.setVisible(true);
        kdtPriceInfoEntry_avgAllAmonut_TextField.setEditable(true);
        kdtPriceInfoEntry_avgAllAmonut_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_avgAllAmonut_TextField.setDataType(1);
        	kdtPriceInfoEntry_avgAllAmonut_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtPriceInfoEntry_avgAllAmonut_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtPriceInfoEntry_avgAllAmonut_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtPriceInfoEntry_avgAllAmonut_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_avgAllAmonut_TextField);
        this.kdtPriceInfoEntry.getColumn("avgAllAmonut").setEditor(kdtPriceInfoEntry_avgAllAmonut_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_stPrice_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_stPrice_TextField.setName("kdtPriceInfoEntry_stPrice_TextField");
        kdtPriceInfoEntry_stPrice_TextField.setVisible(true);
        kdtPriceInfoEntry_stPrice_TextField.setEditable(true);
        kdtPriceInfoEntry_stPrice_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_stPrice_TextField.setDataType(1);
        	kdtPriceInfoEntry_stPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtPriceInfoEntry_stPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtPriceInfoEntry_stPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtPriceInfoEntry_stPrice_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_stPrice_TextField);
        this.kdtPriceInfoEntry.getColumn("stPrice").setEditor(kdtPriceInfoEntry_stPrice_CellEditor);
        KDFormattedTextField kdtPriceInfoEntry_highestPrice_TextField = new KDFormattedTextField();
        kdtPriceInfoEntry_highestPrice_TextField.setName("kdtPriceInfoEntry_highestPrice_TextField");
        kdtPriceInfoEntry_highestPrice_TextField.setVisible(true);
        kdtPriceInfoEntry_highestPrice_TextField.setEditable(true);
        kdtPriceInfoEntry_highestPrice_TextField.setHorizontalAlignment(2);
        kdtPriceInfoEntry_highestPrice_TextField.setDataType(1);
        	kdtPriceInfoEntry_highestPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtPriceInfoEntry_highestPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtPriceInfoEntry_highestPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtPriceInfoEntry_highestPrice_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_highestPrice_TextField);
        this.kdtPriceInfoEntry.getColumn("highestPrice").setEditor(kdtPriceInfoEntry_highestPrice_CellEditor);
        KDTextField kdtPriceInfoEntry_remarkE1_TextField = new KDTextField();
        kdtPriceInfoEntry_remarkE1_TextField.setName("kdtPriceInfoEntry_remarkE1_TextField");
        kdtPriceInfoEntry_remarkE1_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtPriceInfoEntry_remarkE1_CellEditor = new KDTDefaultCellEditor(kdtPriceInfoEntry_remarkE1_TextField);
        this.kdtPriceInfoEntry.getColumn("remarkE1").setEditor(kdtPriceInfoEntry_remarkE1_CellEditor);
        // kdtSaleInfoEntry
		String kdtSaleInfoEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"endDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"saleSets\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"saleArea\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{stDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{saleSets}</t:Cell><t:Cell>$Resource{saleArea}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSaleInfoEntry.setFormatXml(resHelper.translateString("kdtSaleInfoEntry",kdtSaleInfoEntryStrXML));

                this.kdtSaleInfoEntry.putBindContents("editData",new String[] {"number","stDate","endDate","saleSets","saleArea","remark"});


        this.kdtSaleInfoEntry.checkParsed();
        KDTextField kdtSaleInfoEntry_number_TextField = new KDTextField();
        kdtSaleInfoEntry_number_TextField.setName("kdtSaleInfoEntry_number_TextField");
        kdtSaleInfoEntry_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSaleInfoEntry_number_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_number_TextField);
        this.kdtSaleInfoEntry.getColumn("number").setEditor(kdtSaleInfoEntry_number_CellEditor);
        KDDatePicker kdtSaleInfoEntry_stDate_DatePicker = new KDDatePicker();
        kdtSaleInfoEntry_stDate_DatePicker.setName("kdtSaleInfoEntry_stDate_DatePicker");
        kdtSaleInfoEntry_stDate_DatePicker.setVisible(true);
        kdtSaleInfoEntry_stDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtSaleInfoEntry_stDate_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_stDate_DatePicker);
        this.kdtSaleInfoEntry.getColumn("stDate").setEditor(kdtSaleInfoEntry_stDate_CellEditor);
        KDDatePicker kdtSaleInfoEntry_endDate_DatePicker = new KDDatePicker();
        kdtSaleInfoEntry_endDate_DatePicker.setName("kdtSaleInfoEntry_endDate_DatePicker");
        kdtSaleInfoEntry_endDate_DatePicker.setVisible(true);
        kdtSaleInfoEntry_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtSaleInfoEntry_endDate_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_endDate_DatePicker);
        this.kdtSaleInfoEntry.getColumn("endDate").setEditor(kdtSaleInfoEntry_endDate_CellEditor);
        KDFormattedTextField kdtSaleInfoEntry_saleSets_TextField = new KDFormattedTextField();
        kdtSaleInfoEntry_saleSets_TextField.setName("kdtSaleInfoEntry_saleSets_TextField");
        kdtSaleInfoEntry_saleSets_TextField.setVisible(true);
        kdtSaleInfoEntry_saleSets_TextField.setEditable(true);
        kdtSaleInfoEntry_saleSets_TextField.setHorizontalAlignment(2);
        kdtSaleInfoEntry_saleSets_TextField.setDataType(1);
        	kdtSaleInfoEntry_saleSets_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSaleInfoEntry_saleSets_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSaleInfoEntry_saleSets_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSaleInfoEntry_saleSets_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_saleSets_TextField);
        this.kdtSaleInfoEntry.getColumn("saleSets").setEditor(kdtSaleInfoEntry_saleSets_CellEditor);
        KDFormattedTextField kdtSaleInfoEntry_saleArea_TextField = new KDFormattedTextField();
        kdtSaleInfoEntry_saleArea_TextField.setName("kdtSaleInfoEntry_saleArea_TextField");
        kdtSaleInfoEntry_saleArea_TextField.setVisible(true);
        kdtSaleInfoEntry_saleArea_TextField.setEditable(true);
        kdtSaleInfoEntry_saleArea_TextField.setHorizontalAlignment(2);
        kdtSaleInfoEntry_saleArea_TextField.setDataType(1);
        	kdtSaleInfoEntry_saleArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSaleInfoEntry_saleArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSaleInfoEntry_saleArea_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSaleInfoEntry_saleArea_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_saleArea_TextField);
        this.kdtSaleInfoEntry.getColumn("saleArea").setEditor(kdtSaleInfoEntry_saleArea_CellEditor);
        KDTextField kdtSaleInfoEntry_remark_TextField = new KDTextField();
        kdtSaleInfoEntry_remark_TextField.setName("kdtSaleInfoEntry_remark_TextField");
        kdtSaleInfoEntry_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtSaleInfoEntry_remark_CellEditor = new KDTDefaultCellEditor(kdtSaleInfoEntry_remark_TextField);
        this.kdtSaleInfoEntry.getColumn("remark").setEditor(kdtSaleInfoEntry_remark_CellEditor);
        // kdtMarketingEntry
		String kdtMarketingEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtMarketingEntry.setFormatXml(resHelper.translateString("kdtMarketingEntry",kdtMarketingEntryStrXML));

                this.kdtMarketingEntry.putBindContents("editData",new String[] {"remark"});


        this.kdtMarketingEntry.checkParsed();
        KDTextField kdtMarketingEntry_remark_TextField = new KDTextField();
        kdtMarketingEntry_remark_TextField.setName("kdtMarketingEntry_remark_TextField");
        kdtMarketingEntry_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtMarketingEntry_remark_CellEditor = new KDTDefaultCellEditor(kdtMarketingEntry_remark_TextField);
        this.kdtMarketingEntry.getColumn("remark").setEditor(kdtMarketingEntry_remark_CellEditor);
        // kdtUseMediaEntry
		String kdtUseMediaEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtUseMediaEntry.setFormatXml(resHelper.translateString("kdtUseMediaEntry",kdtUseMediaEntryStrXML));

                this.kdtUseMediaEntry.putBindContents("editData",new String[] {"remark"});


        this.kdtUseMediaEntry.checkParsed();
        KDTextField kdtUseMediaEntry_remark_TextField = new KDTextField();
        kdtUseMediaEntry_remark_TextField.setName("kdtUseMediaEntry_remark_TextField");
        kdtUseMediaEntry_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtUseMediaEntry_remark_CellEditor = new KDTDefaultCellEditor(kdtUseMediaEntry_remark_TextField);
        this.kdtUseMediaEntry.getColumn("remark").setEditor(kdtUseMediaEntry_remark_CellEditor);
        // prmtDeveloper		
        this.prmtDeveloper.setQueryInfo("com.kingdee.eas.fdc.market.app.DeveloperManageQuery");		
        this.prmtDeveloper.setVisible(true);		
        this.prmtDeveloper.setEditable(true);		
        this.prmtDeveloper.setDisplayFormat("$name$");		
        this.prmtDeveloper.setEditFormat("$number$");		
        this.prmtDeveloper.setCommitFormat("$number$");		
        this.prmtDeveloper.setRequired(false);		
        this.prmtDeveloper.setEnabled(true);		
        this.prmtDeveloper.setForeground(new java.awt.Color(0,0,0));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtinvestor,txtagent,txtcoverArea,txtarchitectArea,txtcubageRate,prmtproductType,txtbruntRoomType,txtbruntArea,txtdonateArea,txtparkingLot,saleStute,pkstartTime,pkfinishTime,txtpropertyCompany,pkjoinTime,pktoTime,txttotleYears,txtmanageCharge,txtsaleRoomTel,txtadress,txtvillageSupFacil,txtenvMunicipalInf,kdtUseMediaEntry,kdtMarketingEntry,kdtSaleInfoEntry,kdtPriceInfoEntry,pkfromTime,txtDescription,txtNumber,lblyear,prmtDeveloper}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 985, 627));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer3.setBounds(new Rectangle(672, 10, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(9, 371, 938, 19));
        this.add(kDLabelContainer4, null);
        contselectProject.setBounds(new Rectangle(341, 10, 270, 19));
        this.add(contselectProject, null);
        continvestor.setBounds(new Rectangle(341, 34, 270, 19));
        this.add(continvestor, null);
        contagent.setBounds(new Rectangle(672, 34, 270, 19));
        this.add(contagent, null);
        contcoverArea.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(contcoverArea, null);
        contarchitectArea.setBounds(new Rectangle(341, 58, 270, 19));
        this.add(contarchitectArea, null);
        contcubageRate.setBounds(new Rectangle(672, 58, 270, 19));
        this.add(contcubageRate, null);
        contproductType.setBounds(new Rectangle(10, 82, 270, 19));
        this.add(contproductType, null);
        contbruntRoomType.setBounds(new Rectangle(341, 82, 270, 19));
        this.add(contbruntRoomType, null);
        contbruntArea.setBounds(new Rectangle(672, 82, 270, 19));
        this.add(contbruntArea, null);
        contdonateArea.setBounds(new Rectangle(10, 106, 270, 19));
        this.add(contdonateArea, null);
        contparkingLot.setBounds(new Rectangle(341, 106, 270, 19));
        this.add(contparkingLot, null);
        contsaleStute.setBounds(new Rectangle(672, 106, 270, 19));
        this.add(contsaleStute, null);
        contstartTime.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contstartTime, null);
        contfinishTime.setBounds(new Rectangle(341, 130, 270, 19));
        this.add(contfinishTime, null);
        contpropertyCompany.setBounds(new Rectangle(10, 178, 270, 19));
        this.add(contpropertyCompany, null);
        contjoinTime.setBounds(new Rectangle(672, 130, 270, 19));
        this.add(contjoinTime, null);
        contfromTime.setBounds(new Rectangle(10, 155, 270, 19));
        this.add(contfromTime, null);
        conttoTime.setBounds(new Rectangle(341, 155, 270, 19));
        this.add(conttoTime, null);
        conttotleYears.setBounds(new Rectangle(672, 154, 236, 19));
        this.add(conttotleYears, null);
        lblyear.setBounds(new Rectangle(906, 154, 36, 19));
        this.add(lblyear, null);
        contmanageCharge.setBounds(new Rectangle(341, 178, 270, 19));
        this.add(contmanageCharge, null);
        contsaleRoomTel.setBounds(new Rectangle(672, 178, 270, 19));
        this.add(contsaleRoomTel, null);
        contnewestAvrPrice.setBounds(new Rectangle(10, 202, 270, 19));
        this.add(contnewestAvrPrice, null);
        contnewestAvgAllAm.setBounds(new Rectangle(341, 202, 270, 19));
        this.add(contnewestAvgAllAm, null);
        contnewestInitPrice.setBounds(new Rectangle(672, 202, 270, 19));
        this.add(contnewestInitPrice, null);
        contnewestHighPrice.setBounds(new Rectangle(10, 225, 270, 19));
        this.add(contnewestHighPrice, null);
        contadress.setBounds(new Rectangle(10, 250, 935, 19));
        this.add(contadress, null);
        contvillageSupFacil.setBounds(new Rectangle(10, 274, 443, 89));
        this.add(contvillageSupFacil, null);
        contenvMunicipalInf.setBounds(new Rectangle(463, 274, 484, 91));
        this.add(contenvMunicipalInf, null);
        paneBIZLayerControl39.setBounds(new Rectangle(7, 397, 945, 216));
        this.add(paneBIZLayerControl39, null);
        contDeveloper.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contDeveloper, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contselectProject
        contselectProject.setBoundEditor(prmtsellproject);
        //continvestor
        continvestor.setBoundEditor(txtinvestor);
        //contagent
        contagent.setBoundEditor(txtagent);
        //contcoverArea
        contcoverArea.setBoundEditor(txtcoverArea);
        //contarchitectArea
        contarchitectArea.setBoundEditor(txtarchitectArea);
        //contcubageRate
        contcubageRate.setBoundEditor(txtcubageRate);
        //contproductType
        contproductType.setBoundEditor(prmtproductType);
        //contbruntRoomType
        contbruntRoomType.setBoundEditor(txtbruntRoomType);
        //contbruntArea
        contbruntArea.setBoundEditor(txtbruntArea);
        //contdonateArea
        contdonateArea.setBoundEditor(txtdonateArea);
        //contparkingLot
        contparkingLot.setBoundEditor(txtparkingLot);
        //contsaleStute
        contsaleStute.setBoundEditor(saleStute);
        //contstartTime
        contstartTime.setBoundEditor(pkstartTime);
        //contfinishTime
        contfinishTime.setBoundEditor(pkfinishTime);
        //contpropertyCompany
        contpropertyCompany.setBoundEditor(txtpropertyCompany);
        //contjoinTime
        contjoinTime.setBoundEditor(pkjoinTime);
        //contfromTime
        contfromTime.setBoundEditor(pkfromTime);
        //conttoTime
        conttoTime.setBoundEditor(pktoTime);
        //conttotleYears
        conttotleYears.setBoundEditor(txttotleYears);
        //contmanageCharge
        contmanageCharge.setBoundEditor(txtmanageCharge);
        //contsaleRoomTel
        contsaleRoomTel.setBoundEditor(txtsaleRoomTel);
        //contnewestAvrPrice
        contnewestAvrPrice.setBoundEditor(txtnewestAvrPrice);
        //contnewestAvgAllAm
        contnewestAvgAllAm.setBoundEditor(txtnewestAvgAllAm);
        //contnewestInitPrice
        contnewestInitPrice.setBoundEditor(txtnewestInitPrice);
        //contnewestHighPrice
        contnewestHighPrice.setBoundEditor(txtnewestHighPrice);
        //contadress
        contadress.setBoundEditor(txtadress);
        //contvillageSupFacil
        contvillageSupFacil.setBoundEditor(scrollPanevillageSupFacil);
        //scrollPanevillageSupFacil
        scrollPanevillageSupFacil.getViewport().add(txtvillageSupFacil, null);
        //contenvMunicipalInf
        contenvMunicipalInf.setBoundEditor(scrollPaneenvMunicipalInf);
        //scrollPaneenvMunicipalInf
        scrollPaneenvMunicipalInf.getViewport().add(txtenvMunicipalInf, null);
        //paneBIZLayerControl39
        paneBIZLayerControl39.add(priceInfo, resHelper.getString("priceInfo.constraints"));
        paneBIZLayerControl39.add(saleInfo, resHelper.getString("saleInfo.constraints"));
        paneBIZLayerControl39.add(marketInfo, resHelper.getString("marketInfo.constraints"));
        paneBIZLayerControl39.add(useMedia, resHelper.getString("useMedia.constraints"));
        //priceInfo
        priceInfo.setLayout(null);        kdtPriceInfoEntry.setBounds(new Rectangle(1, 1, 936, 169));
        kdtPriceInfoEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPriceInfoEntry,new com.kingdee.eas.fdc.market.CompeteItemPriceInfoEntryInfo(),null,false);
        priceInfo.add(kdtPriceInfoEntry_detailPanel, null);
        //saleInfo
        saleInfo.setLayout(null);        kdtSaleInfoEntry.setBounds(new Rectangle(1, 1, 936, 171));
        kdtSaleInfoEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtSaleInfoEntry,new com.kingdee.eas.fdc.market.CompeteItemSaleInfoEntryInfo(),null,false);
        saleInfo.add(kdtSaleInfoEntry_detailPanel, null);
        //marketInfo
        marketInfo.setLayout(null);        kdtMarketingEntry.setBounds(new Rectangle(2, -1, 936, 166));
        kdtMarketingEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtMarketingEntry,new com.kingdee.eas.fdc.market.CompeteItemMarketingEntryInfo(),null,false);
        marketInfo.add(kdtMarketingEntry_detailPanel, null);
        //useMedia
        useMedia.setLayout(null);        kdtUseMediaEntry.setBounds(new Rectangle(3, 1, 935, 169));
        kdtUseMediaEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtUseMediaEntry,new com.kingdee.eas.fdc.market.CompeteItemUseMediaEntryInfo(),null,false);
        useMedia.add(kdtUseMediaEntry_detailPanel, null);
        //contDeveloper
        contDeveloper.setBoundEditor(prmtDeveloper);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "userObject");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("sellproject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtsellproject, "data");
		dataBinder.registerBinding("investor", String.class, this.txtinvestor, "text");
		dataBinder.registerBinding("agent", String.class, this.txtagent, "text");
		dataBinder.registerBinding("coverArea", java.math.BigDecimal.class, this.txtcoverArea, "value");
		dataBinder.registerBinding("architectArea", java.math.BigDecimal.class, this.txtarchitectArea, "value");
		dataBinder.registerBinding("cubageRate", java.math.BigDecimal.class, this.txtcubageRate, "value");
		dataBinder.registerBinding("productType", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.prmtproductType, "data");
		dataBinder.registerBinding("bruntRoomType", String.class, this.txtbruntRoomType, "text");
		dataBinder.registerBinding("bruntArea", java.math.BigDecimal.class, this.txtbruntArea, "value");
		dataBinder.registerBinding("donateArea", java.math.BigDecimal.class, this.txtdonateArea, "value");
		dataBinder.registerBinding("parkingLot", String.class, this.txtparkingLot, "text");
		dataBinder.registerBinding("saleStute", com.kingdee.eas.fdc.market.SaleState.class, this.saleStute, "selectedItem");
		dataBinder.registerBinding("startTime", java.util.Date.class, this.pkstartTime, "value");
		dataBinder.registerBinding("finishTime", java.util.Date.class, this.pkfinishTime, "value");
		dataBinder.registerBinding("propertyCompany", String.class, this.txtpropertyCompany, "text");
		dataBinder.registerBinding("joinTime", java.util.Date.class, this.pkjoinTime, "value");
		dataBinder.registerBinding("fromTime", java.util.Date.class, this.pkfromTime, "value");
		dataBinder.registerBinding("toTime", java.util.Date.class, this.pktoTime, "value");
		dataBinder.registerBinding("totleYears", java.math.BigDecimal.class, this.txttotleYears, "value");
		dataBinder.registerBinding("manageCharge", java.math.BigDecimal.class, this.txtmanageCharge, "value");
		dataBinder.registerBinding("saleRoomTel", String.class, this.txtsaleRoomTel, "text");
		dataBinder.registerBinding("newestAvrPrice", java.math.BigDecimal.class, this.txtnewestAvrPrice, "text");
		dataBinder.registerBinding("newestAvgAllAm", java.math.BigDecimal.class, this.txtnewestAvgAllAm, "text");
		dataBinder.registerBinding("newestInitPrice", java.math.BigDecimal.class, this.txtnewestInitPrice, "text");
		dataBinder.registerBinding("newestHighPrice", java.math.BigDecimal.class, this.txtnewestHighPrice, "text");
		dataBinder.registerBinding("adress", String.class, this.txtadress, "text");
		dataBinder.registerBinding("villageSupFacil", String.class, this.txtvillageSupFacil, "text");
		dataBinder.registerBinding("envMunicipalInf", String.class, this.txtenvMunicipalInf, "text");
		dataBinder.registerBinding("PriceInfoEntry.number", String.class, this.kdtPriceInfoEntry, "number.text");
		dataBinder.registerBinding("PriceInfoEntry", com.kingdee.eas.fdc.market.CompeteItemPriceInfoEntryInfo.class, this.kdtPriceInfoEntry, "userObject");
		dataBinder.registerBinding("PriceInfoEntry.pushDate", java.util.Date.class, this.kdtPriceInfoEntry, "pushDate.text");
		dataBinder.registerBinding("PriceInfoEntry.pushSaleSets", int.class, this.kdtPriceInfoEntry, "pushSaleSets.text");
		dataBinder.registerBinding("PriceInfoEntry.pusharchArea", java.math.BigDecimal.class, this.kdtPriceInfoEntry, "pusharchArea.text");
		dataBinder.registerBinding("PriceInfoEntry.avgPrice", java.math.BigDecimal.class, this.kdtPriceInfoEntry, "avgPrice.text");
		dataBinder.registerBinding("PriceInfoEntry.avgAllAmonut", java.math.BigDecimal.class, this.kdtPriceInfoEntry, "avgAllAmonut.text");
		dataBinder.registerBinding("PriceInfoEntry.stPrice", java.math.BigDecimal.class, this.kdtPriceInfoEntry, "stPrice.text");
		dataBinder.registerBinding("PriceInfoEntry.highestPrice", java.math.BigDecimal.class, this.kdtPriceInfoEntry, "highestPrice.text");
		dataBinder.registerBinding("PriceInfoEntry.remarkE1", String.class, this.kdtPriceInfoEntry, "remarkE1.text");
		dataBinder.registerBinding("SaleInfoEntry.number", String.class, this.kdtSaleInfoEntry, "number.text");
		dataBinder.registerBinding("SaleInfoEntry", com.kingdee.eas.fdc.market.CompeteItemSaleInfoEntryInfo.class, this.kdtSaleInfoEntry, "userObject");
		dataBinder.registerBinding("SaleInfoEntry.stDate", java.util.Date.class, this.kdtSaleInfoEntry, "stDate.text");
		dataBinder.registerBinding("SaleInfoEntry.endDate", java.util.Date.class, this.kdtSaleInfoEntry, "endDate.text");
		dataBinder.registerBinding("SaleInfoEntry.saleSets", java.math.BigDecimal.class, this.kdtSaleInfoEntry, "saleSets.text");
		dataBinder.registerBinding("SaleInfoEntry.saleArea", java.math.BigDecimal.class, this.kdtSaleInfoEntry, "saleArea.text");
		dataBinder.registerBinding("SaleInfoEntry.remark", String.class, this.kdtSaleInfoEntry, "remark.text");
		dataBinder.registerBinding("MarketingEntry.remark", String.class, this.kdtMarketingEntry, "remark.text");
		dataBinder.registerBinding("MarketingEntry", com.kingdee.eas.fdc.market.CompeteItemMarketingEntryInfo.class, this.kdtMarketingEntry, "userObject");
		dataBinder.registerBinding("UseMediaEntry.remark", String.class, this.kdtUseMediaEntry, "remark.text");
		dataBinder.registerBinding("UseMediaEntry", com.kingdee.eas.fdc.market.CompeteItemUseMediaEntryInfo.class, this.kdtUseMediaEntry, "userObject");
		dataBinder.registerBinding("Developer", com.kingdee.eas.fdc.market.DeveloperManageInfo.class, this.prmtDeveloper, "data");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.CompeteItemEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtinvestor.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.CompeteItemInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellproject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("investor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coverArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("architectArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cubageRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bruntRoomType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bruntArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("donateArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parkingLot", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleStute", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finishTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("joinTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fromTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totleYears", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("manageCharge", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleRoomTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newestAvrPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newestAvgAllAm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newestInitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newestHighPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("villageSupFacil", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("envMunicipalInf", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.pushDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.pushSaleSets", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.pusharchArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.avgPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.avgAllAmonut", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.stPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.highestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PriceInfoEntry.remarkE1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.stDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.saleSets", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.saleArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SaleInfoEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("MarketingEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("MarketingEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("UseMediaEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("UseMediaEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Developer", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
        }
    }

    /**
     * output pkfromTime_dataChanged method
     */
    protected void pkfromTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkfromTime_focusLost method
     */
    protected void pkfromTime_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output pktoTime_dataChanged method
     */
    protected void pktoTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pktoTime_focusLost method
     */
    protected void pktoTime_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kdtPriceInfoEntry_editStopped method
     */
    protected void kdtPriceInfoEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("sellproject.*"));
        sic.add(new SelectorItemInfo("investor"));
        sic.add(new SelectorItemInfo("agent"));
        sic.add(new SelectorItemInfo("coverArea"));
        sic.add(new SelectorItemInfo("architectArea"));
        sic.add(new SelectorItemInfo("cubageRate"));
        sic.add(new SelectorItemInfo("productType.*"));
        sic.add(new SelectorItemInfo("bruntRoomType"));
        sic.add(new SelectorItemInfo("bruntArea"));
        sic.add(new SelectorItemInfo("donateArea"));
        sic.add(new SelectorItemInfo("parkingLot"));
        sic.add(new SelectorItemInfo("saleStute"));
        sic.add(new SelectorItemInfo("startTime"));
        sic.add(new SelectorItemInfo("finishTime"));
        sic.add(new SelectorItemInfo("propertyCompany"));
        sic.add(new SelectorItemInfo("joinTime"));
        sic.add(new SelectorItemInfo("fromTime"));
        sic.add(new SelectorItemInfo("toTime"));
        sic.add(new SelectorItemInfo("totleYears"));
        sic.add(new SelectorItemInfo("manageCharge"));
        sic.add(new SelectorItemInfo("saleRoomTel"));
        sic.add(new SelectorItemInfo("newestAvrPrice"));
        sic.add(new SelectorItemInfo("newestAvgAllAm"));
        sic.add(new SelectorItemInfo("newestInitPrice"));
        sic.add(new SelectorItemInfo("newestHighPrice"));
        sic.add(new SelectorItemInfo("adress"));
        sic.add(new SelectorItemInfo("villageSupFacil"));
        sic.add(new SelectorItemInfo("envMunicipalInf"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.number"));
        sic.add(new SelectorItemInfo("PriceInfoEntry.*"));
//        sic.add(new SelectorItemInfo("PriceInfoEntry.number"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.pushDate"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.pushSaleSets"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.pusharchArea"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.avgPrice"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.avgAllAmonut"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.stPrice"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.highestPrice"));
    sic.add(new SelectorItemInfo("PriceInfoEntry.remarkE1"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.number"));
        sic.add(new SelectorItemInfo("SaleInfoEntry.*"));
//        sic.add(new SelectorItemInfo("SaleInfoEntry.number"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.stDate"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.endDate"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.saleSets"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.saleArea"));
    sic.add(new SelectorItemInfo("SaleInfoEntry.remark"));
    sic.add(new SelectorItemInfo("MarketingEntry.remark"));
        sic.add(new SelectorItemInfo("MarketingEntry.*"));
//        sic.add(new SelectorItemInfo("MarketingEntry.number"));
    sic.add(new SelectorItemInfo("UseMediaEntry.remark"));
        sic.add(new SelectorItemInfo("UseMediaEntry.*"));
//        sic.add(new SelectorItemInfo("UseMediaEntry.number"));
        sic.add(new SelectorItemInfo("Developer.*"));
        return sic;
    }        
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "CompeteItemEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.CompeteItemEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.CompeteItemFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.CompeteItemInfo objectValue = new com.kingdee.eas.fdc.market.CompeteItemInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtPriceInfoEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("saleStute","0");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}