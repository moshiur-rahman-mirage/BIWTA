package com.zaberp.zab.biwtabackend.zbusiness;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "zbusiness")
public class Zbusiness {

    @Id
    private Integer zid;

    private LocalDateTime ztime;
    private LocalDateTime zutime;

    private String xshort;
    private String xtaxnum;
    private String zorg;
    private String xcity;
    private String xstate;
    private String xzip;
    private String xcountry;
    private String xphone;
    private String xfax;
    private String xcontact;
    private String xemail;
    private String xurl;
    private String xdformat;
    private String xdsep;
    private String xtimage;
    private String xbimage;
    private String xcustom;
    private String zactive;
    private String zemail;
    private String xmadd;
    private String zauserid;
    private String zuuserid;
    private String zaip;
    private String zuip;
    private String xsignatory;
    private String xdesignation;
    private String xpadd;

    @Lob
    private byte[] ximage;

    private String xvatregno;
    private String xtin;
    private String xcur;
    private String xcat;
    private String xnatureofbusiness;
    private String xnatureoffinoper;
    private String xetin;
    private String xaccname;
    private String xbank;
    private String xbranch;
    private String xbacc;
    private String xacctype;
    private String xrouting;

    @Lob
    private byte[] xauthorsign;

    private String xbinadd;
    private String xvatdepstacc;
    private String xcomcode;
    private String xshortname;
    private String xcode;
    private String xurlupload;
    private String xbtype;
    private String xtype;

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public LocalDateTime getZtime() {
        return ztime;
    }

    public void setZtime(LocalDateTime ztime) {
        this.ztime = ztime;
    }

    public LocalDateTime getZutime() {
        return zutime;
    }

    public void setZutime(LocalDateTime zutime) {
        this.zutime = zutime;
    }

    public String getXshort() {
        return xshort;
    }

    public void setXshort(String xshort) {
        this.xshort = xshort;
    }

    public String getXtaxnum() {
        return xtaxnum;
    }

    public void setXtaxnum(String xtaxnum) {
        this.xtaxnum = xtaxnum;
    }

    public String getZorg() {
        return zorg;
    }

    public void setZorg(String zorg) {
        this.zorg = zorg;
    }

    public String getXcity() {
        return xcity;
    }

    public void setXcity(String xcity) {
        this.xcity = xcity;
    }

    public String getXstate() {
        return xstate;
    }

    public void setXstate(String xstate) {
        this.xstate = xstate;
    }

    public String getXzip() {
        return xzip;
    }

    public void setXzip(String xzip) {
        this.xzip = xzip;
    }

    public String getXcountry() {
        return xcountry;
    }

    public void setXcountry(String xcountry) {
        this.xcountry = xcountry;
    }

    public String getXphone() {
        return xphone;
    }

    public void setXphone(String xphone) {
        this.xphone = xphone;
    }

    public String getXfax() {
        return xfax;
    }

    public void setXfax(String xfax) {
        this.xfax = xfax;
    }

    public String getXcontact() {
        return xcontact;
    }

    public void setXcontact(String xcontact) {
        this.xcontact = xcontact;
    }

    public String getXemail() {
        return xemail;
    }

    public void setXemail(String xemail) {
        this.xemail = xemail;
    }

    public String getXurl() {
        return xurl;
    }

    public void setXurl(String xurl) {
        this.xurl = xurl;
    }

    public String getXdformat() {
        return xdformat;
    }

    public void setXdformat(String xdformat) {
        this.xdformat = xdformat;
    }

    public String getXdsep() {
        return xdsep;
    }

    public void setXdsep(String xdsep) {
        this.xdsep = xdsep;
    }

    public String getXtimage() {
        return xtimage;
    }

    public void setXtimage(String xtimage) {
        this.xtimage = xtimage;
    }

    public String getXbimage() {
        return xbimage;
    }

    public void setXbimage(String xbimage) {
        this.xbimage = xbimage;
    }

    public String getXcustom() {
        return xcustom;
    }

    public void setXcustom(String xcustom) {
        this.xcustom = xcustom;
    }

    public String getZactive() {
        return zactive;
    }

    public void setZactive(String zactive) {
        this.zactive = zactive;
    }

    public String getZemail() {
        return zemail;
    }

    public void setZemail(String zemail) {
        this.zemail = zemail;
    }

    public String getXmadd() {
        return xmadd;
    }

    public void setXmadd(String xmadd) {
        this.xmadd = xmadd;
    }

    public String getZauserid() {
        return zauserid;
    }

    public void setZauserid(String zauserid) {
        this.zauserid = zauserid;
    }

    public String getZuuserid() {
        return zuuserid;
    }

    public void setZuuserid(String zuuserid) {
        this.zuuserid = zuuserid;
    }

    public String getZaip() {
        return zaip;
    }

    public void setZaip(String zaip) {
        this.zaip = zaip;
    }

    public String getZuip() {
        return zuip;
    }

    public void setZuip(String zuip) {
        this.zuip = zuip;
    }

    public String getXsignatory() {
        return xsignatory;
    }

    public void setXsignatory(String xsignatory) {
        this.xsignatory = xsignatory;
    }

    public String getXdesignation() {
        return xdesignation;
    }

    public void setXdesignation(String xdesignation) {
        this.xdesignation = xdesignation;
    }

    public String getXpadd() {
        return xpadd;
    }

    public void setXpadd(String xpadd) {
        this.xpadd = xpadd;
    }

    public byte[] getXimage() {
        return ximage;
    }

    public void setXimage(byte[] ximage) {
        this.ximage = ximage;
    }

    public String getXvatregno() {
        return xvatregno;
    }

    public void setXvatregno(String xvatregno) {
        this.xvatregno = xvatregno;
    }

    public String getXtin() {
        return xtin;
    }

    public void setXtin(String xtin) {
        this.xtin = xtin;
    }

    public String getXcur() {
        return xcur;
    }

    public void setXcur(String xcur) {
        this.xcur = xcur;
    }

    public String getXcat() {
        return xcat;
    }

    public void setXcat(String xcat) {
        this.xcat = xcat;
    }

    public String getXnatureofbusiness() {
        return xnatureofbusiness;
    }

    public void setXnatureofbusiness(String xnatureofbusiness) {
        this.xnatureofbusiness = xnatureofbusiness;
    }

    public String getXnatureoffinoper() {
        return xnatureoffinoper;
    }

    public void setXnatureoffinoper(String xnatureoffinoper) {
        this.xnatureoffinoper = xnatureoffinoper;
    }

    public String getXetin() {
        return xetin;
    }

    public void setXetin(String xetin) {
        this.xetin = xetin;
    }

    public String getXaccname() {
        return xaccname;
    }

    public void setXaccname(String xaccname) {
        this.xaccname = xaccname;
    }

    public String getXbank() {
        return xbank;
    }

    public void setXbank(String xbank) {
        this.xbank = xbank;
    }

    public String getXbranch() {
        return xbranch;
    }

    public void setXbranch(String xbranch) {
        this.xbranch = xbranch;
    }

    public String getXbacc() {
        return xbacc;
    }

    public void setXbacc(String xbacc) {
        this.xbacc = xbacc;
    }

    public String getXacctype() {
        return xacctype;
    }

    public void setXacctype(String xacctype) {
        this.xacctype = xacctype;
    }

    public String getXrouting() {
        return xrouting;
    }

    public void setXrouting(String xrouting) {
        this.xrouting = xrouting;
    }

    public byte[] getXauthorsign() {
        return xauthorsign;
    }

    public void setXauthorsign(byte[] xauthorsign) {
        this.xauthorsign = xauthorsign;
    }

    public String getXbinadd() {
        return xbinadd;
    }

    public void setXbinadd(String xbinadd) {
        this.xbinadd = xbinadd;
    }

    public String getXvatdepstacc() {
        return xvatdepstacc;
    }

    public void setXvatdepstacc(String xvatdepstacc) {
        this.xvatdepstacc = xvatdepstacc;
    }

    public String getXcomcode() {
        return xcomcode;
    }

    public void setXcomcode(String xcomcode) {
        this.xcomcode = xcomcode;
    }

    public String getXshortname() {
        return xshortname;
    }

    public void setXshortname(String xshortname) {
        this.xshortname = xshortname;
    }

    public String getXcode() {
        return xcode;
    }

    public void setXcode(String xcode) {
        this.xcode = xcode;
    }

    public String getXurlupload() {
        return xurlupload;
    }

    public void setXurlupload(String xurlupload) {
        this.xurlupload = xurlupload;
    }

    public String getXbtype() {
        return xbtype;
    }

    public void setXbtype(String xbtype) {
        this.xbtype = xbtype;
    }

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }
}
