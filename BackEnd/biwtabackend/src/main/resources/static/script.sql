USE [BIWTADB]
GO
/****** Object:  UserDefinedFunction [dbo].[CheckValue]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[CheckValue]
(
      @param varchar(50)
	 ,@action VARCHAR(50)

)
returns varchar(100)
as
begin
    declare @workingday varchar(100),@pdate DATE,@cdate DATE;

IF @action = 'WD'
BEGIN

  SET @workingday = (CASE WHEN SUBSTRING(@param,1,1)=1 then 'SAT,' else '' END)+(CASE WHEN SUBSTRING(@param,2,1)=1 then 'SUN,' else '' END)+
(CASE WHEN SUBSTRING(@param,3,1)=1 then 'MON,' else '' END)+(CASE WHEN SUBSTRING(@param,4,1)=1 then 'TUE,' else '' END)+
(CASE WHEN SUBSTRING(@param,5,1)=1 then 'WED,' else '' END)+(CASE WHEN SUBSTRING(@param,6,1)=1 then 'THU,' else '' END)+
(CASE WHEN SUBSTRING(@param,7,1)=1 then 'FRI,' else '' END) 
END
--------------------------------------
IF @action = 'Age'
BEGIN
SET @pdate = CAST(@param AS DATE)
SET @cdate = CAST(GETDATE() AS DATE)

DECLARE @year INT, @month INT, @day INT,
			@dayinyear INT,@fyear DATE,@yearcounter DATE,
			@dayinmonth INT,@fmonthwithoutyear DATE,@monthcounter DATE

	SET @pdate = CAST(@pdate AS DATE)
	SET @cdate = CAST(DATEADD(DAY,1,@cdate) AS DATE)
	SET @day = DATEDIFF(DAY,@pdate,@cdate)
	SET @fmonthwithoutyear = @pdate

	SET @month = 0
	SET @monthcounter = @fmonthwithoutyear
	WHILE(@monthcounter<=@cdate)
	BEGIN
		SET @dayinmonth = DAY(EOMONTH(@monthcounter))
		IF @dayinmonth<=@day
		BEGIN
			SET @day = @day - @dayinmonth
			SET @month = @month + 1
		END
		SET @monthcounter = DATEADD(MONTH,1,@monthcounter)
		SET @dayinmonth = 0
	END

	SET @year = CAST(@month / 12 AS INT)
	SET @month = @month - (@year*12)

	IF (@pdate>@cdate)
		SET @workingday = '0 Y, 0 M, 0 D'
	ELSE
		SET @workingday = CAST(ISNULL(@year,0) AS VARCHAR(5))+' Y '+CAST(ISNULL(@month,0) AS VARCHAR(5))+' M '+CAST(ISNULL(@day,0) AS VARCHAR(5))+' D'	
END

--------------------------------------
return @workingday
end
GO
/****** Object:  Table [dbo].[xcodes]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xcodes](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtype] [varchar](50) NOT NULL,
	[xcode] [varchar](100) NOT NULL,
	[xlong] [varchar](1000) NULL,
	[zactive] [varchar](20) NULL,
	[xacc] [varchar](50) NULL,
	[xaccvat] [varchar](50) NULL,
	[xaccdisc] [varchar](50) NULL,
	[xaccait] [varchar](50) NULL,
	[xaccret] [varchar](50) NULL,
	[xaccpur] [varchar](50) NULL,
	[xaccwo] [varchar](50) NULL,
	[xprops] [varchar](250) NULL,
	[xmadd] [varchar](250) NULL,
	[xphone] [varchar](150) NULL,
	[xfax] [varchar](250) NULL,
	[xemail] [varchar](250) NULL,
	[xarea] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xregion] [varchar](50) NULL,
	[xfield] [varchar](50) NULL,
	[xterritory] [varchar](50) NULL,
	[xhrc1] [varchar](50) NULL,
	[xdeptname] [varchar](50) NULL,
	[xtypeobj] [varchar](50) NULL,
	[xtrcode] [varchar](250) NULL,
	[xtargetshare] [decimal](20, 2) NULL,
	[xpercent] [decimal](20, 2) NULL,
	[xaccother] [varchar](50) NULL,
	[xtwh] [varchar](50) NULL,
	[xshiftimin] [time](7) NULL,
	[xshiftimout] [time](7) NULL,
	[xattimecon] [varchar](6) NULL,
	[xemptimeout] [varchar](6) NULL,
	[xincholiday] [varchar](50) NULL,
	[xtypestore] [varchar](50) NULL,
	[xproject] [varchar](50) NULL,
	[xaccreg] [varchar](50) NULL,
	[xaccdr] [varchar](50) NULL,
	[xacccash] [varchar](50) NULL,
	[xgtype] [varchar](50) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xmaxbal] [decimal](20, 2) NULL,
	[xapplyat] [varchar](50) NULL,
	[xappleave] [varchar](1) NULL,
	[xservicetenure] [int] NULL,
	[xmonthlymax] [int] NULL,
	[xprofitcntrcat] [varchar](50) NULL,
	[xdivision] [varchar](50) NULL,
	[xdistrict] [varchar](50) NULL,
	[xprofitcntr] [varchar](50) NULL,
 CONSTRAINT [PK__xcodes__D6B371C88D51C0E6] PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtype] ASC,
	[xcode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zbusiness]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zbusiness](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[xshort] [varchar](50) NULL,
	[xtaxnum] [varchar](150) NULL,
	[zorg] [varchar](50) NULL,
	[xcity] [varchar](50) NULL,
	[xstate] [varchar](50) NULL,
	[xzip] [varchar](150) NULL,
	[xcountry] [varchar](50) NULL,
	[xphone] [varchar](500) NULL,
	[xfax] [varchar](300) NULL,
	[xcontact] [varchar](150) NULL,
	[xemail] [varchar](50) NULL,
	[xurl] [varchar](50) NULL,
	[xdformat] [varchar](50) NULL,
	[xdsep] [varchar](50) NULL,
	[xtimage] [varchar](250) NULL,
	[xbimage] [varchar](250) NULL,
	[xcustom] [varchar](500) NULL,
	[zactive] [varchar](1) NULL,
	[zemail] [varchar](50) NULL,
	[xmadd] [varchar](500) NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
	[zuip] [varchar](50) NULL,
	[xsignatory] [varchar](50) NULL,
	[xdesignation] [varchar](50) NULL,
	[xpadd] [varchar](500) NULL,
	[ximage] [image] NULL,
	[xvatregno] [varchar](150) NULL,
	[xtin] [varchar](150) NULL,
	[xcur] [varchar](150) NULL,
	[xcat] [varchar](50) NULL,
	[xnatureofbusiness] [varchar](250) NULL,
	[xnatureoffinoper] [varchar](250) NULL,
	[xetin] [varchar](50) NULL,
	[xaccname] [varchar](50) NULL,
	[xbank] [varchar](50) NULL,
	[xbranch] [varchar](50) NULL,
	[xbacc] [varchar](50) NULL,
	[xacctype] [varchar](50) NULL,
	[xrouting] [varchar](50) NULL,
	[xauthorsign] [image] NULL,
	[xbinadd] [varchar](250) NULL,
	[xvatdepstacc] [varchar](50) NULL,
	[xcomcode] [varchar](50) NULL,
	[xshortname] [varchar](50) NULL,
	[xcode] [varchar](50) NULL,
	[xurlupload] [varchar](250) NULL,
	[xbtype] [varchar](20) NULL,
	[XTYPE] [varchar](20) NULL,
 CONSTRAINT [PK__zbusiness__03A81637] PRIMARY KEY CLUSTERED 
(
	[zid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ__zbusiness__049C3A70] UNIQUE NONCLUSTERED 
(
	[zemail] ASC,
	[zorg] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  View [dbo].[branchview]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[branchview](zid,xtype,xlong,xcode,xmadd,xphone,xfax,xemail,xtypeobj,zactive,xwh,zbid,xtypestore,xfwh,xacwh,xgtype) AS SELECT a.zid,b.xtype,b.xlong,b.xcode,b.xmadd,b.xphone,b.xfax,b.xemail,b.xtypeobj,b.zactive,b.xwh,CAST(a.zid as varchar(50)),isnull(xtypestore,''), (CASE WHEN b.xlong like '%QC%' then '' ELSE b.xwh END),(CASE when isnull(b.xwh,'')='' then '01' else isnull(b.xwh,'') END),isnull(b.xgtype,'')  FROM zbusiness a JOIN xcodes(NOLOCK) b ON a.zid=b.zid WHERE b.xtype='Branch'
GO
/****** Object:  Table [dbo].[imtorheader]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imtorheader](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtornum] [varchar](50) NOT NULL,
	[xdate] [date] NULL,
	[xgrnnum] [varchar](50) NULL,
	[xdatecom] [date] NULL,
	[xlong] [varchar](1000) NULL,
	[xref] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xfwh] [varchar](50) NULL,
	[xtwh] [varchar](50) NULL,
	[xstatustor] [varchar](50) NULL,
	[xdocrow] [int] NULL,
	[xstatusreq] [varchar](50) NULL,
	[xcus] [varchar](250) NULL,
	[xporeqnum] [varchar](50) NULL,
	[xdaterec] [datetime] NULL,
	[xstatusrec] [varchar](50) NULL,
	[xregi] [varchar](50) NULL,
	[xidsup] [varchar](8) NULL,
	[xrevision] [int] NULL,
	[xpreparer] [varchar](50) NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsigndate1] [datetime] NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsigndate2] [datetime] NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsigndate3] [datetime] NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsigndate4] [datetime] NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsigndate5] [datetime] NULL,
	[xsignatory6] [varchar](50) NULL,
	[xsigndate6] [datetime] NULL,
	[xsignatory7] [varchar](50) NULL,
	[xsigndate7] [datetime] NULL,
	[xshift] [varchar](50) NULL,
	[xdatereq] [datetime] NULL,
	[xsofficer] [varchar](50) NULL,
	[xdateso] [datetime] NULL,
	[xsrnum] [varchar](50) NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
	[xtrn] [char](4) NULL,
	[xtitem] [varchar](50) NULL,
	[xsignreject] [varchar](50) NULL,
	[xdatereject] [datetime] NULL,
	[xnote] [varchar](5000) NULL,
	[xnote1] [varchar](5000) NULL,
	[xnote2] [varchar](500) NULL,
	[xnote3] [varchar](500) NULL,
	[xnote4] [varchar](500) NULL,
	[xnote5] [varchar](500) NULL,
	[xbackground] [varchar](2500) NULL,
	[xacc] [varchar](50) NULL,
	[xreqtype] [varchar](50) NULL,
	[xstatusap] [varchar](50) NULL,
	[xstatusjv] [varchar](50) NULL,
	[xvoucher] [varchar](50) NULL,
	[xloannum] [varchar](50) NULL,
	[xprodnature] [varchar](50) NULL,
	[xstatusloan] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[xdategl] [date] NULL,
	[xtypeobj] [varchar](50) NULL,
	[xproject] [varchar](50) NULL,
	[xbuilding] [varchar](50) NULL,
	[xappnote] [varchar](500) NULL,
	[xassetcode] [varchar](50) NULL,
	[xloanature] [varchar](50) NULL,
	[xmoprcs] [varchar](50) NULL,
	[xblno] [varchar](50) NULL,
	[xlorryno] [varchar](50) NULL,
	[xvessel] [varchar](50) NULL,
	[xempunit] [varchar](50) NULL,
	[xcode] [varchar](100) NULL,
	[xhrc1] [varchar](50) NULL,
	[xhrc2] [varchar](50) NULL,
	[xhrc3] [varchar](50) NULL,
	[xhrc4] [varchar](50) NULL,
	[xhrc5] [varchar](50) NULL,
	[xstaff] [varchar](50) NULL,
	[xordernum] [varchar](50) NULL,
	[xbatch] [varchar](20) NULL,
	[xparentitem] [varchar](50) NULL,
	[xqtyinsp] [decimal](20, 3) NULL,
	[xsignatoryrec] [varchar](50) NULL,
	[xsignatorytrans] [varchar](50) NULL,
	[xstatus1] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xtypesr] [varchar](50) NULL,
	[xstatussr] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtornum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[imtorheaderrptview]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[imtorheaderrptview](zid,xtornum,xdate,xpreparer,xsignatory1,xsigndate1,xsignatory2,xsigndate2,xsignatory3,xsigndate3,xdateso,xsofficer) AS select zid,xtornum,xdate,xpreparer,xsignatory1,xsigndate1,xsignatory2,xsigndate2,xsignatory3,xsigndate3,xdateso,xsofficer from imtorheader
GO
/****** Object:  Table [dbo].[mmmedicine]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmmedicine](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xcase] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xdorrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xqtyord] [decimal](20, 3) NULL,
	[xbatch] [varchar](20) NULL,
	[xdateexp] [date] NULL,
	[xstatusint] [int] NULL,
	[xwh] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC,
	[xdorrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[mmmedicineview]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmmedicineview] (link,ztime,zutime,zid,zauserid,zuuserid,xcase,xrow,xdorrow,xitem,xqtyord,xbatch,xdateexp,xstatusint) as select '<a id=''two'' onclick="theFunction(event)" style="cursor: pointer;color: #007bff;" data-toggle=''modal'' data-target=''#mmmedicine''>'+ cast(xdorrow as varchar) +'</a>',ztime,zutime,zid,zauserid,zuuserid,xcase,xrow,xdorrow,xitem,xqtyord,xbatch,xdateexp,xstatusint from mmmedicine
GO
/****** Object:  View [dbo].[imtorheaderrptview2]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[imtorheaderrptview2](zid,xtornum,xdate,xlong,xfwh,xtwh,xstatustor,xtitem,xbuilding,xtypesr,xregi,xproject,xdatereq,xreqtype,xtypeobj,xappnote,xempunit) AS SELECT zid,xtornum,xdate,xlong,xfwh,xtwh,xstatustor,xtitem,xbuilding,xtypesr,xregi,xproject,xdatereq,xreqtype,xtypeobj,xappnote,xempunit FROM imtorheader
GO
/****** Object:  Table [dbo].[imtrn]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imtrn](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[ximtrnnum] [varchar](50) NOT NULL,
	[xitem] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xdate] [date] NULL,
	[xqty] [int] NULL,
	[xval] [decimal](20, 2) NULL,
	[xvalpost] [decimal](20, 2) NULL,
	[xdocnum] [varchar](50) NULL,
	[xdocrow] [int] NULL,
	[xnote] [varchar](5000) NULL,
	[xsign] [int] NULL,
	[xunit] [varchar](50) NULL,
	[xrate] [decimal](20, 3) NULL,
	[xref] [varchar](50) NULL,
	[xstatusjv] [varchar](50) NULL,
	[xvoucher] [varchar](50) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xvatamt] [decimal](20, 2) NULL,
	[xbatch] [varchar](20) NULL,
	[xpiref] [varchar](150) NULL,
	[xcqtyuse] [decimal](20, 3) NULL,
	[xissuetype] [varchar](150) NULL,
	[xordernum] [varchar](50) NULL,
	[xlot] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xcus] [varchar](250) NULL,
	[xregi] [varchar](50) NULL,
	[xdoctype] [varchar](50) NULL,
	[xqtystk] [decimal](20, 0) NULL,
	[xserial] [varchar](150) NULL,
	[xrateavg] [decimal](20, 5) NULL,
	[xqtybac] [decimal](20, 3) NULL,
	[xtrn] [char](4) NULL,
	[xproject] [varchar](50) NULL,
	[xassetcode] [varchar](50) NULL,
	[xbinref] [varchar](50) NULL,
	[xcode] [varchar](100) NULL,
	[xhrc1] [varchar](50) NULL,
	[xhrc2] [varchar](50) NULL,
	[xhrc3] [varchar](50) NULL,
	[xhrc4] [varchar](50) NULL,
	[xhrc5] [varchar](50) NULL,
	[xyear] [int] NULL,
	[xper] [int] NULL,
	[xcostcenter] [varchar](50) NULL,
	[xdeptname] [varchar](50) NULL,
	[xgitem] [varchar](100) NULL,
	[xtypeobj] [varchar](50) NULL,
	[xissueto] [varchar](50) NULL,
	[xcostrow] [int] NULL,
	[xacc] [varchar](50) NULL,
	[xsub] [varchar](50) NULL,
	[xfleet] [varchar](100) NULL,
	[xtwh] [varchar](50) NULL,
	[xdateexp] [datetime] NULL,
	[xcottontype] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[ximtrnnum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[imtrnrptview]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imtrnrptview](zid,ximtrnnum,xitem,xdocnum,xwh,xdate,xqty,xval,xnote,xsign,xqtybac,xregi,xdocrow,xrate,xvoucher,xunit) as select zid,ximtrnnum,xitem,xdocnum,xwh,xdate,isnull(xqty,0),isnull(xval,0),xnote,xsign,isnull(xqtybac,0),xregi,xdocrow,isnull(xrate,0),isnull(xvoucher,''),xunit from imtrn where isnull(xnote,'')<>'Archive Opening'
GO
/****** Object:  Table [dbo].[imtordetail]    Script Date: 11/25/2024 5:22:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imtordetail](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtornum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xmasteritem] [varchar](50) NULL,
	[xqtyord] [decimal](20, 3) NULL,
	[xunit] [varchar](50) NULL,
	[xrate] [decimal](20, 3) NULL,
	[xlineamt] [decimal](20, 2) NULL,
	[xqtyreq] [decimal](20, 2) NULL,
	[xqtycom] [decimal](20, 3) NULL,
	[xstype] [varchar](550) NULL,
	[xnote] [varchar](5000) NULL,
	[xdocrow] [int] NULL,
	[xprepqty] [decimal](20, 2) NULL,
	[xdphqty] [decimal](20, 2) NULL,
	[xqtypor] [decimal](20, 3) NULL,
	[xqtyalc] [decimal](20, 3) NULL,
	[xbrand] [varchar](500) NULL,
	[xserial] [varchar](150) NULL,
	[xqtycrn] [decimal](20, 2) NULL,
	[xsrnum] [varchar](50) NULL,
	[xdate] [date] NULL,
	[xbinref] [varchar](50) NULL,
	[xnote1] [varchar](5000) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xfwh] [varchar](50) NULL,
	[xtwh] [varchar](50) NULL,
	[xacc] [varchar](50) NULL,
	[xsub] [varchar](50) NULL,
	[xfleet] [varchar](100) NULL,
	[xbatch] [varchar](20) NULL,
	[xcode] [varchar](100) NULL,
	[xqty] [decimal](20, 2) NULL,
	[xdateexp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtornum] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[caitem]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[caitem](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xitem] [varchar](50) NOT NULL,
	[xdesc] [varchar](1000) NULL,
	[xgitem] [varchar](100) NULL,
	[xcitem] [varchar](50) NULL,
	[xcatitem] [varchar](100) NULL,
	[xrate] [decimal](20, 3) NULL,
	[xunitpur] [varchar](50) NULL,
	[xcost] [decimal](20, 2) NULL,
	[xvatamt] [decimal](20, 2) NULL,
	[xrateexp] [decimal](20, 2) NULL,
	[xhscode] [varchar](50) NULL,
	[xunit] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[xtitem] [varchar](50) NULL,
	[xstype] [varchar](550) NULL,
	[xmrp] [decimal](20, 5) NULL,
	[xdealerp] [decimal](20, 5) NULL,
	[xpacking] [varchar](30) NULL,
	[xdisc] [decimal](5, 2) NULL,
	[xdateeff] [date] NULL,
	[xdateexp] [date] NULL,
	[xspecification] [varchar](1500) NULL,
	[xpackqty] [decimal](20, 2) NULL,
	[zactive] [varchar](1) NULL,
	[xdiscstatus] [varchar](50) NULL,
	[xpsize] [varchar](50) NULL,
	[xpnature] [varchar](50) NULL,
	[xbrand] [varchar](500) NULL,
	[xcfpur] [decimal](20, 6) NULL,
	[xcfsel] [decimal](20, 6) NULL,
	[xcfiss] [decimal](20, 6) NULL,
	[xunitsel] [varchar](50) NULL,
	[xunitiss] [varchar](50) NULL,
	[xshelf] [varchar](50) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xminrate] [decimal](20, 3) NULL,
	[xminqty] [decimal](20, 3) NULL,
	[xunitpck] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[xcus] [varchar](250) NULL,
	[xitmedept] [varchar](50) NULL,
	[xitemsubdept] [varchar](50) NULL,
	[xpackweightnet] [decimal](20, 2) NULL,
	[xstocktype] [varchar](50) NULL,
	[xstockcat] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
	[xmaxqty] [decimal](20, 3) NULL,
	[xprodnature] [varchar](50) NULL,
	[xitemnew] [varchar](50) NULL,
	[xitemserial] [varchar](50) NULL,
	[xyesno] [varchar](50) NULL,
	[xgenericname] [varchar](150) NULL,
	[xgenericdesc] [varchar](150) NULL,
	[xtypeobj] [varchar](50) NULL,
	[xsumtype] [varchar](50) NULL,
	[xdrugtype] [varchar](50) NULL,
	[xstrength] [varchar](50) NULL,
	[xroute] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xbatmg] [varchar](50) NULL,
	[xorg] [varchar](50) NULL,
	[xreordqty] [decimal](20, 2) NULL,
	[xtypeitem] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xitem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[imtordetailview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imtordetailview] (zid,xtornum,xfwh,xdes1,xtwh,xdes2,xitem,xmasteritem, xdesc,xqtyreq,xqtyord,xqtyalc,xqtycom,xqtypor,xrow,xserial,xunit,xcfpur,xdate,xtitem,xstatustor,xrate,xlineamt,xacc,xprepqty,xdphqty,xprodnature)as select a.zid,a.xtornum,a.xfwh,b.xlong,a.xtwh,c.xlong,d.xitem,isnull(d.xmasteritem,''),e.xdesc, isnull(d.xqtyreq,0),isnull(d.xqtyord,0),isnull(d.xqtyalc,0),isnull(d.xqtycom,0),isnull(d.xqtypor,0),d.xrow,d.xserial,d.xunit,isnull(e.xcfpur,1),d.xdate, e.xtitem,isnull(a.xstatustor,0),d.xrate,d.xlineamt,d.xacc,d.xprepqty,d.xdphqty,e.xprodnature from imtorheader a join imtordetail d with(nolock) on a.zid=d.zid and a.xtornum=d.xtornum join caitem e with(nolock) on d.zid=e.zid and d.xitem=e.xitem left join branchview b on a.zid=b.zid and a.xfwh=b.xcode left join branchview c on a.zid=c.zid and a.xtwh=c.xcode
GO
/****** Object:  View [dbo].[imtordetailrptview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[imtordetailrptview](zid,xitem,xtornum,xrow,xqtyord,xunit,xlineamt,xqtyreq,xqtycom,xnote,xqtypor,xprepqty,xqtyalc) AS SELECT zid,xitem,xtornum,xrow,xqtyord,xunit,xlineamt,xqtyreq,xqtycom,xnote,xqtypor,xprepqty,xqtyalc FROM imtordetail
GO
/****** Object:  Table [dbo].[imtordetailbatch]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imtordetailbatch](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtornum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xorderrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xqtyord] [decimal](20, 3) NULL,
	[xbatch] [varchar](20) NULL,
	[xdateexp] [date] NULL,
	[xstatusint] [int] NULL,
	[xwh] [varchar](50) NULL,
	[xstatusrec] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtornum] ASC,
	[xrow] ASC,
	[xorderrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[imtordetailbatchview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imtordetailbatchview] (link,ztime,zutime,zauserid,zuuserid,zid,xtornum,xrow,xorderrow,xitem,xqtyord,xbatch,xdateexp,xstatusint) as select '<a id=''two'' onclick="theFunction(event)" style="cursor: pointer;color: #007bff;" data-toggle=''modal'' data-target=''#imtordetailbatch''>'+ cast(xorderrow as varchar) +'</a>',ztime,zutime,zauserid,zuuserid,zid,xtornum,xrow,xorderrow,xitem,xqtyord,xbatch,xdateexp,xstatusint from imtordetailbatch
GO
/****** Object:  Table [dbo].[xusers]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xusers](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xpassword] [varchar](50) NULL,
	[xaccess] [varchar](50) NULL,
	[xdformat] [varchar](50) NULL,
	[xdsep] [varchar](50) NULL,
	[xrole] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xlastlogdate] [datetime] NULL,
	[xname] [varchar](450) NULL,
	[zaip] [varchar](50) NULL,
	[zuip] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xsp] [varchar](50) NULL,
	[xoldpass] [varchar](50) NULL,
	[xposition] [varchar](50) NULL,
	[zscreen] [varchar](50) NULL,
	[xproject] [varchar](50) NULL,
	[xyesno] [varchar](50) NULL,
	[zactiveapp] [varchar](1) NULL,
	[xnote] [varchar](5000) NULL,
	[xdateexp] [date] NULL,
	[zactiveopapp] [varchar](1) NULL,
	[xdefpass] [varchar](100) NULL,
	[xlastcpdate] [datetime] NULL,
	[xyesno2] [varchar](50) NULL,
 CONSTRAINT [PK__xusers__3A840497548E438A] PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userstore]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userstore](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xwh] [varchar](50) NOT NULL,
	[xposition] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xwh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[userstoreview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[userstoreview](zid,zemail,xposition,xwh,xlong) AS SELECT a.zid,a.zemail,a.xposition,a.xwh,b.xlong from xusers a join xcodes b on a.zid=b.zid and a.xwh=b.xcode Where b.xtype='Branch' union all SELECT a.zid,a.zemail,a.xposition,a.xwh,b.xlong from userstore a join xcodes b on a.zid=b.zid and a.xwh=b.xcode Where b.xtype='Branch'
GO
/****** Object:  Table [dbo].[mmappointment]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmappointment](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xcase] [varchar](50) NOT NULL,
	[xstatus] [varchar](50) NULL,
	[xstatuspharma] [varchar](50) NULL,
	[xdoctor] [varchar](50) NULL,
	[xstaff] [varchar](50) NULL,
	[xbirthdate] [date] NULL,
	[xsex] [varchar](50) NULL,
	[xyearperdate] [int] NULL,
	[xrow] [int] NULL,
	[xapptime] [time](7) NULL,
	[xmobile] [varchar](40) NULL,
	[xappcompleted] [datetime] NULL,
	[xcompleteid] [varchar](50) NULL,
	[xsendpharmadt] [datetime] NULL,
	[xsendpharmaid] [varchar](50) NULL,
	[xage] [varchar](50) NULL,
	[xposition] [varchar](50) NULL,
	[xdeptname] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
	[xdesignation] [varchar](50) NULL,
	[xprofdegree] [varchar](250) NULL,
	[xstatusmodifyid] [varchar](50) NULL,
	[xstatusinvestigation] [varchar](50) NULL,
	[xinvestbillerid] [varchar](50) NULL,
	[xinvestbilldatetime] [datetime] NULL,
	[xdate] [datetime] NULL,
	[xapptype] [varchar](200) NULL,
	[xlong] [varchar](200) NULL,
	[xcode] [varchar](200) NULL,
	[xdependent] [varchar](200) NULL,
	[xpatient] [varchar](500) NULL,
	[xstatusint] [int] NULL,
	[xchiefcomplain] [varchar](2000) NULL,
	[xfamilyother] [varchar](5000) NULL,
	[xsurgicalhistory] [varchar](250) NULL,
	[xfamilyhistory] [varchar](250) NULL,
	[xother] [varchar](1000) NULL,
	[xinvestigationhistroy] [varchar](1000) NULL,
	[xfollowupadvice] [varchar](2000) NULL,
	[xpatadvice] [varchar](50) NULL,
	[xprovdiagnosis] [varchar](250) NULL,
	[xdiagnosis] [varchar](200) NULL,
	[xinvestigation] [varchar](50) NULL,
	[xlasa] [varchar](2000) NULL,
 CONSTRAINT [PK__mmappoin__62BDCCFE38035C4B] PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[departmentview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[departmentview](zid,xtype,xlong,xcode,zactive,xdeptname) AS SELECT a.zid,b.xtype,b.xlong,b.xcode,b.zactive,b.xcode FROM zbusiness a with(nolock) JOIN xcodes b with(nolock) ON a.zid=b.zid WHERE b.xtype='Department Name'
GO
/****** Object:  View [dbo].[designationview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[designationview](zid,xtype,xlong,xcode,zactive,xdesignation) AS SELECT a.zid,b.xtype,b.xlong,b.xcode,b.zactive,b.xcode FROM zbusiness a with(nolock) JOIN xcodes b with(nolock) ON a.zid=b.zid WHERE b.xtype='Designation'
GO
/****** Object:  Table [dbo].[pdmst]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdmst](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xname] [varchar](450) NULL,
	[xfname] [varchar](150) NULL,
	[xmname] [varchar](150) NULL,
	[xmadd] [varchar](250) NULL,
	[xpadd] [varchar](500) NULL,
	[xbirthdate] [date] NULL,
	[xsex] [varchar](50) NULL,
	[xmstat] [varchar](50) NULL,
	[xgroup] [varchar](50) NULL,
	[xvillage] [varchar](50) NULL,
	[xpost] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[xreplaceid] [varchar](40) NULL,
	[xtypeposition] [varchar](40) NULL,
	[xdeptname] [varchar](50) NULL,
	[xsec] [varchar](50) NULL,
	[xdesignation] [varchar](50) NULL,
	[xdatejoin] [date] NULL,
	[xgrade] [varchar](50) NULL,
	[xemail] [varchar](250) NULL,
	[xenddate] [date] NULL,
	[xretdate] [date] NULL,
	[xotapplicable] [varchar](50) NULL,
	[xbatch] [varchar](20) NULL,
	[xsalute] [varchar](50) NULL,
	[xspouse] [varchar](50) NULL,
	[xempstatus] [varchar](50) NULL,
	[xlocation] [varchar](150) NULL,
	[xemname] [varchar](50) NULL,
	[xrelation] [varchar](50) NULL,
	[xemcmobile] [varchar](40) NULL,
	[xmobile] [varchar](40) NULL,
	[xhomephone] [varchar](150) NULL,
	[xempjobstatus] [varchar](50) NULL,
	[xdistrict] [varchar](50) NULL,
	[xdatecom] [date] NULL,
	[xinclude] [varchar](50) NULL,
	[xbank] [varchar](50) NULL,
	[xdesc] [varchar](1000) NULL,
	[xacc] [varchar](50) NULL,
	[xprobenddate] [datetime] NULL,
	[xregino] [varchar](50) NULL,
	[xregiexpdate] [datetime] NULL,
	[xrehire] [varchar](1) NULL,
	[xthana] [varchar](50) NULL,
	[xnid] [varchar](50) NULL,
	[xdatejoinp] [date] NULL,
	[xregtype] [varchar](50) NULL,
	[xposition] [varchar](50) NULL,
	[xtin] [varchar](50) NULL,
	[xempcategory] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xsid] [varchar](50) NULL,
	[xcar] [varchar](50) NULL,
	[ximage] [image] NULL,
	[xbloodgroup] [varchar](50) NULL,
	[xphone] [varchar](150) NULL,
	[xgross] [decimal](20, 2) NULL,
	[xleavecount] [decimal](20, 2) NULL,
	[xhrc1] [varchar](50) NULL,
	[xhrc2] [varchar](50) NULL,
	[xhrc3] [varchar](50) NULL,
	[xhrc4] [varchar](50) NULL,
	[xplan] [varchar](50) NULL,
	[xsplan] [varchar](50) NULL,
	[xhour] [decimal](20, 2) NULL,
	[xrate] [decimal](20, 3) NULL,
	[xnimage] [image] NULL,
	[xreligion] [varchar](50) NULL,
	[xsuperiorsp] [varchar](8) NULL,
	[xemptype] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xsignature] [image] NULL,
	[xfile] [varchar](50) NULL,
	[xidsup] [varchar](8) NULL,
	[xempposition] [varchar](1) NULL,
	[xgrossold] [decimal](20, 2) NULL,
	[xsignatory] [varchar](50) NULL,
	[xrouting] [varchar](50) NULL,
	[xacctype] [varchar](50) NULL,
	[xempbank] [varchar](50) NULL,
	[xemailc] [varchar](50) NULL,
	[xfstname] [varchar](150) NULL,
	[xlstname] [varchar](500) NULL,
	[xbankamt] [decimal](20, 2) NULL,
	[xcashamt] [decimal](20, 2) NULL,
	[xempgrade] [varchar](50) NULL,
	[xsection] [varchar](50) NULL,
	[xshift] [varchar](50) NULL,
	[xemptimein] [varchar](6) NULL,
	[xemptimeout] [varchar](6) NULL,
	[xpfconrate] [decimal](20, 2) NULL,
	[xpfdef] [varchar](50) NULL,
	[xitdef] [varchar](50) NULL,
	[xadminid] [varchar](50) NULL,
	[xweekday] [varchar](50) NULL,
	[xwkndnotapp] [varchar](1) NULL,
	[xnum] [int] NULL,
	[xlastcrdate] [date] NULL,
	[xhdayapp] [varchar](1) NULL,
	[xtaxzone] [varchar](50) NULL,
	[xenddtcontract] [date] NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
	[xsuperior4] [varchar](8) NULL,
	[xsuperior5] [varchar](8) NULL,
	[xsuperior6] [varchar](8) NULL,
	[xstatus1] [varchar](50) NULL,
	[xstatus2] [varchar](50) NULL,
	[xstatus3] [varchar](50) NULL,
	[xnote1] [varchar](5000) NULL,
	[xnote2] [varchar](500) NULL,
	[xtaxamt] [decimal](20, 2) NULL,
	[xdivision] [varchar](50) NULL,
	[xarea] [varchar](50) NULL,
	[xaccname] [varchar](50) NULL,
	[xbasic] [decimal](20, 2) NULL,
	[xmtleavebal] [int] NULL,
	[xplanchange] [varchar](50) NULL,
	[xempunit] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xnationality] [varchar](50) NULL,
	[xjobtitle] [varchar](50) NULL,
	[xmdlname] [varchar](100) NULL,
	[xprofdegree] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mmpreschistory]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmpreschistory](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xrow] [int] NULL,
	[xcase] [varchar](50) NOT NULL,
	[xstaff] [varchar](50) NULL,
	[xdoctor] [varchar](50) NULL,
	[xage] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xsex] [varchar](50) NULL,
	[xyearperdate] [int] NULL,
	[xsuspecteddisease] [varchar](10) NULL,
	[xadvice] [varchar](500) NULL,
	[xrisk] [varchar](250) NULL,
	[xchiefcomplain] [varchar](2000) NULL,
	[xfamilyother] [varchar](5000) NULL,
	[xsurgicalhistory] [varchar](250) NULL,
	[xfamilyhistory] [varchar](250) NULL,
	[xother] [varchar](1000) NULL,
	[xdrughistory] [varchar](250) NULL,
	[xdrughistoryothers] [varchar](250) NULL,
	[xobhistory] [varchar](1000) NULL,
	[xmeandobhistory] [varchar](50) NULL,
	[xcontraception] [varchar](500) NULL,
	[xinvestigationhistroy] [varchar](1000) NULL,
	[xfollowupadvice] [varchar](2000) NULL,
	[xpatadvice] [varchar](50) NULL,
	[xadadvice] [varchar](1000) NULL,
	[xrefdoctor] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[xcode] [varchar](100) NULL,
	[xprovdiagnosis] [varchar](250) NULL,
	[xdiagnosis] [varchar](200) NULL,
	[xlasa] [varchar](1000) NULL,
	[xinvestigation] [varchar](500) NULL,
	[xmorbcheck] [varchar](1) NULL,
	[xsurgcheck] [varchar](1) NULL,
	[xfamcheck] [varchar](1) NULL,
	[xriskcheck] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[vitalssign]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[vitalssign](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xcase] [varchar](50) NOT NULL,
	[xyearperdate] [int] NULL,
	[xdate] [date] NULL,
	[xdoctor] [varchar](50) NULL,
	[xstaff] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xvitalcheckup] [time](7) NULL,
	[xpulserate] [varchar](50) NULL,
	[xbp] [varchar](50) NULL,
	[xtemperature] [varchar](50) NULL,
	[xspo2] [varchar](50) NULL,
	[xrbs] [varchar](50) NULL,
	[xheartrate] [varchar](50) NULL,
	[xbmigyn] [decimal](20, 2) NULL,
	[xheightvit] [varchar](50) NULL,
	[xweightvit] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
	[xibwwom] [decimal](20, 2) NULL,
	[xibwmen] [decimal](20, 2) NULL,
	[xbsa] [decimal](20, 5) NULL,
	[xrem] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[mmappassview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmappassview] (zid,xcase,xdoctor,xpatient,xsurgicalhistory,xchiefcomplain,xfamilyhistory,xdrughistory,xobhistory,xmeandobhistory,xcontraception,xsuspecteddisease,xrisk,xadvice,xfamilyother,xinvestigationhistroy,xlasa,xinvestigation,xdiagnosis,xdate,xposition,xname,xage,xmobile,xother,xdrughistoryothers,designationname,xprofdegree,departmentname,xpatadvice,xfollowupadvice,xrefdoctor) as select b.zid,isnull(b.xcase,''),isnull(b.xdoctor,''),isnull(b.xstaff,''),isnull(b.xsurgicalhistory,''),isnull(b.xchiefcomplain,''),isnull(h.xfamilyhistory,''),isnull(h.xdrughistory,'') ,isnull(h.xobhistory,''),isnull(h.xmeandobhistory,''),isnull(h.xcontraception,''),isnull(h.xsuspecteddisease,''),isnull(h.xrisk,''),isnull(h.xadvice,''),isnull(h.xfamilyother,''), isnull(b.xinvestigationhistroy,''),isnull(b.xlasa,''),isnull(b.xinvestigation,''),isnull(b.xdiagnosis,''),convert(VARCHAR, b.xdate, 106),p.xposition,m.xname,b.xage,b.xmobile,isnull(h.xother,''),isnull(h.xdrughistoryothers,''),y.xlong,isnull(b.xprofdegree,''),x.xlong,isnull(h.xpatadvice,''),isnull(b.xfollowupadvice,''),isnull(h.xrefdoctor,'') from mmappointment (NOLOCK) b left join mmpreschistory (NOLOCK) h on b.zid=h.zid and b.xcase=h.xcase left join vitalssign (NOLOCK) n on b.zid=n.zid and b.xcase=n.xcase left join pdmst (NOLOCK) p on b.zid = p.zid and b.xdoctor=p.xstaff left join pdmst (NOLOCK) m on b.zid = m.zid and b.xstaff=m.xstaff left join departmentview x on b.zid=x.zid and b.xdeptname=x.xcode left join designationview y on b.zid=y.zid and b.xdesignation=y.xcode
GO
/****** Object:  View [dbo].[pdmstview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[pdmstview](ztime, zutime, zauserid, zuuserid, zid,xuserpr, xstaff, xname, xfname, xmname, xmadd, xpadd, xbirthdate, xsex, xmstat, xgroup, xdeptname, xsec, xdesignation, xdatejoin, xgrade,  xemail,  xenddate, xretdate, xempstatus, xlocation, xemname, xrelation, xemcmobile, xmobile, xtaxamt,  xdistrict, xdatecom, xinclude, xbank, xdesc, xacc,  xposition, xtin, xempcategory, xstatus, xbatch, xsid, xcar, ximage, xbloodgroup, xphone, xgross, xhrc1, xhrc2, xhrc3, xhrc4, xplan, xsplan, xhour, xrate, xnimage, xreligion, xemptype, zactive, xsignature, xfile, xidsup, xempposition, xgrossold, xsignatory, xrouting, xacctype, xempbank, xemailc, xfstname, xlstname, xbankamt, xcashamt, xempgrade, xsection, xshift, xemptimein, xemptimeout, xpfconrate, xpfdef, xitdef, xadminid, xweekday, xwkndnotapp,  xhdayapp, xtaxzone, xenddtcontract, xsuperior2, xsuperior3, xsuperior4, xsuperior5, xsuperior6, xstatus1, xstatus2, xstatus3, xsalute, xspouse, xhomephone, xprobenddate, xregino, xregiexpdate, xrehire,  xthana, xnid, xdatejoinp, xotapplicable, xregtype,  xempjobstatus, xvillage, xpost, xlong, xreplaceid, xtypeposition, departmentname, designationname, sectionname,zorg,xempunit,xtso,xwh,xjobtitle,xprofdegree )AS  select p.ztime, p.zutime, p.zauserid, p.zuuserid, p.zid,p.xstaff, p.xstaff,  p.xname,  p.xfname,  p.xmname,  p.xmadd,  p.xpadd,  p.xbirthdate, p.xsex, p.xmstat, p.xgroup,  p.xdeptname, p.xsec, p.xdesignation, p.xdatejoin, p.xgrade,  p.xemail,  p.xenddate, p.xretdate, p.xempstatus, p.xlocation, p.xemname, p.xrelation, p.xemcmobile, p.xmobile, p.xtaxamt, p.xdistrict,  p.xdatecom, p.xinclude, p.xbank, p.xdesc, p.xacc, p.xposition, p.xtin, p.xempcategory, p.xstatus, p.xbatch, p.xsid, p.xcar, p.ximage, p.xbloodgroup, p.xphone,  p.xgross, p.xhrc1, p.xhrc2, p.xhrc3, p.xhrc4, p.xplan, p.xsplan, p.xhour, p.xrate, p.xnimage, p.xreligion, p.xemptype, p.zactive, p.xsignature,  p.xfile, p.xidsup, p.xempposition, p.xgrossold, p.xsignatory, p.xrouting, p.xacctype, p.xempbank, p.xemailc, p.xfstname, p.xlstname, p.xbankamt, p.xcashamt,  p.xempgrade, p.xsection, p.xshift, p.xemptimein, p.xemptimeout, p.xpfconrate, p.xpfdef, p.xitdef, p.xadminid, p.xweekday, p.xwkndnotapp,  p.xhdayapp, p.xtaxzone, p.xenddtcontract, p.xsuperior2, p.xsuperior3, p.xsuperior4, p.xsuperior5, p.xsuperior6, p.xstatus1, p.xstatus2, p.xstatus3, p.xsalute, p.xspouse, p.xhomephone, p.xprobenddate, p.xregino, p.xregiexpdate, p.xrehire,  p.xthana, p.xnid, p.xdatejoinp, p.xotapplicable, p.xregtype, p.xempjobstatus, p.xvillage, p.xpost, p.xlong, p.xreplaceid, p.xtypeposition, b.xlong,  c.xlong, d.xlong,z.zorg,p.xempunit,p.xstaff,p.xwh,p.xjobtitle,p.xprofdegree from pdmst p WITH (NOLOCK) left JOIN xcodes b  WITH (NOLOCK) ON p.zid=b.zid and p.xdeptname = b.xcode and b.xtype='Department Name'left JOIN xcodes c WITH (NOLOCK) ON p.zid=c.zid and p.xdesignation = c.xcode and c.xtype='Designation'left JOIN xcodes d WITH (NOLOCK) ON p.zid=d.zid and p.xsection = d.xcode and d.xtype='Section' left JOIN zbusiness z WITH (NOLOCK) ON z.xcode=p.xempunit
GO
/****** Object:  View [dbo].[calenderview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[calenderview] (zid,xdate,xdatename,xmonth,xmonthname,xyear,xmonthyear,xdayname) as WITH sequence(num) AS (SELECT 0 UNION ALL SELECT num + 1 FROM sequence WHERE num < DATEDIFF(DAY, DATEADD(YEAR, -1, GETDATE()), DATEADD(YEAR, 0, GETDATE()))), d(d) AS (SELECT DATEADD(DAY, num, DATEADD(YEAR, -1, GETDATE())) FROM sequence), src AS (SELECT zid =00000, xdate         = cast(CONVERT(date,d) as datetime), xdatename      = DATENAME(WEEKDAY,d), xmonth        = DATEPART(MONTH,d), xmonthname    = DATENAME(MONTH,d), xyear         = DATEPART(YEAR,d), xmonthyear     = FORMAT(d,'MMM-yyyy'), xdayname     = CONVERT(varchar,CONVERT(date,d),104) FROM d) select z.zid,s.xdate,s.xdatename,s.xmonth,s.xmonthname,s.xyear,s.xmonthyear,s.xdayname from zbusiness z left join src s on z.zid <> s.zid
GO
/****** Object:  Table [dbo].[mmlaborder]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmlaborder](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xrem] [varchar](1000) NULL,
	[xcase] [varchar](50) NOT NULL,
	[xepisodeno] [varchar](50) NULL,
	[xepisodetype] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xpatdate] [date] NULL,
	[xotherinfo] [varchar](2000) NULL,
	[xinvestigationhistroy] [varchar](1000) NULL,
	[xqty] [int] NULL,
	[xqtyord3] [decimal](20, 3) NULL,
	[xinvestigationstatus] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[mmlabphview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[mmlabphview] (zid,xrow,xcase,xitem,xrem,xdesc) AS SELECT a.zid,a.xrow,a.xcase,a.xitem,a.xrem,c.xdesc from mmlaborder a JOIN caitem c on a.zid=c.zid and a.xitem=c.xitem
GO
/****** Object:  Table [dbo].[mmprescription]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmprescription](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xcase] [varchar](50) NOT NULL,
	[xpatient] [varchar](50) NULL,
	[xfdate] [date] NULL,
	[xtdate] [date] NULL,
	[xgenericname] [varchar](100) NULL,
	[xstrengthgn] [varchar](50) NULL,
	[xunit] [varchar](50) NULL,
	[xdrugtype] [varchar](100) NULL,
	[xdesc] [varchar](1000) NULL,
	[xgenericdesc] [varchar](350) NULL,
	[xinst] [varchar](50) NULL,
	[xtemplateinst] [varchar](100) NULL,
	[xdrug] [varchar](50) NULL,
	[xdosage] [varchar](50) NULL,
	[xdose] [varchar](50) NULL,
	[xfrequency] [varchar](50) NULL,
	[xtakingtime] [varchar](50) NULL,
	[xduration1] [varchar](50) NULL,
	[xdurationday] [varchar](50) NULL,
	[xqty] [int] NULL,
	[xrem] [varchar](1000) NULL,
	[xroute] [varchar](50) NULL,
	[xmeddetail] [varchar](500) NULL,
	[xmedcadvice] [varchar](500) NULL,
	[xitem1] [varchar](50) NULL,
	[xitem2] [varchar](50) NULL,
	[xitem3] [varchar](50) NULL,
	[xqtyord1] [decimal](20, 3) NULL,
	[xqtyord2] [decimal](20, 3) NULL,
	[xqtyord3] [decimal](20, 3) NULL,
	[xinst2] [varchar](50) NULL,
	[xinst3] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xwhcomp] [varchar](50) NULL,
	[xqtyord] [decimal](20, 3) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[pdsignph]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[pdsignph](zid,xstaff,xname,xdesignation,xdeptname,xposition,xsignature,departmentname,designationname,xprofdegree,xregino) as select a.zid,a.xstaff,a.xname,a.xdesignation,a.xdeptname,a.xposition,a.xsignature,c.xlong, d.xlong,a.xprofdegree,a.xregino from pdmst (NOLOCK) a left JOIN xcodes c ON a.zid=c.zid and a.xdeptname = c.xcode and c.xtype='Department Name'left JOIN xcodes d ON a.zid=d.zid and a.xdesignation = d.xcode and d.xtype='Designation'
GO
/****** Object:  View [dbo].[mmprescriptionviewrpt]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create view [dbo].[mmprescriptionviewrpt] (ztime,zutime,zauserid,zuuserid,zid,xcase,xgenericdesc,xinst,xtdate,xdosage,xdose,xfrequency,xtakingtime, xdurationday,xqty,xroute,xduration1,xmedcadvice,xpatient,xname,xage,xsex,doctorname,designationname,departmentname,xregino,xrem,xprofdegree,xdoctor,xappcompleted,xrow) as select b.ztime,b.zutime,d.xposition,b.zuuserid,b.zid,b.xcase,isnull(a.xgenericdesc,''),isnull(a.xinst,''),isnull(convert(VARCHAR, b.xdate, 106),''), isnull(a.xdosage,''),isnull(a.xdose,''),isnull(a.xfrequency,''),isnull(a.xtakingtime,''), isnull(a.xdurationday,''),isnull(a.xqty,0),isnull(a.xroute,''),isnull(a.xduration1,''),isnull(a.xmedcadvice,''), b.xstaff,c.xname,b.xage,c.xsex,d.xname,y.xlong,x.xlong,d.xregino, isnull(a.xrem,''),isnull(d.xprofdegree,''),isnull(b.xdoctor,''),b.xappcompleted,a.xrow from mmappointment b left join mmprescription a on b.zid=a.zid and b.xcase=a.xcase left join pdmst c on b.zid=c.zid and b.xstaff=c.xstaff left join pdsignph d on b.zid=d.zid and b.xdoctor=d.xstaff left join departmentview x on b.zid=x.zid and b.xdeptname=x.xcode left join designationview y on b.zid=y.zid and b.xdesignation=y.xcode
GO
/****** Object:  View [dbo].[imstockbatchview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockbatchview] (zid,xwh,xitem,xsign,xbatch,xdateexp,xavail) as SELECT i.zid,i.xwh,i.xitem,1,isnull(i.xbatch,''),i.xdateexp,SUM(i.xqty*i.xsign) FROM imtrn i WITH (NOLOCK) JOIN caitem c ON i.zid=c.zid AND i.xitem=c.xitem WHERE  isnull(i.xwh,'')<>'' and isnull(c.xbatmg,'')='Yes' GROUP BY i.zid,i.xwh,i.xitem,isnull(i.xbatch,''),i.xdateexp UNION ALL SELECT d.zid,h.xfwh,e.xitem,-1,isnull(e.xbatch,''),e.xdateexp,SUM(e.xqtyord) FROM imtordetail d WITH (NOLOCK) JOIN imtorheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xtornum=h.xtornum JOIN caitem c ON d.zid=c.zid AND d.xitem=c.xitem join imtordetailbatch e WITH (NOLOCK) on d.zid=e.zid and d.xtornum=e.xtornum and d.xrow=e.xrow  and isnull(e.xstatusint,0)=0 WHERE isnull(h.xfwh,'')<>'' and h.xstatustor NOT IN ('Confirmed','Transferred','Rejected','Dismissed') AND d.xqtyord>0 and isnull(c.xbatmg,'')='Yes' GROUP BY d.zid,h.xfwh,e.xitem,isnull(e.xbatch,''),e.xdateexp UNION ALL SELECT d.zid,d.xwh,m.xitem,-1,isnull(m.xbatch,''),m.xdateexp,sum(isnull(m.xqtyord,0)) FROM mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase JOIN caitem c on d.zid=c.zid and d.xitem1=c.xitem join mmmedicine m WITH (NOLOCK) on d.zid=m.zid and d.xcase=m.xcase and d.xrow=m.xrow and isnull(m.xstatusint,0)=0 where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 and isnull(h.xstatusint,0)<>35 and isnull(c.xbatmg,'')='Yes' group by d.zid,d.xwh,m.xitem,isnull(m.xbatch,''),m.xdateexp
GO
/****** Object:  View [dbo].[imstockbatchviewsum]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockbatchviewsum] (zid,xwh,xitem,xbatch,xdateexp,xavail) as SELECT zid,xwh,xitem,isnull(xbatch,''),xdateexp,sum(xavail*xsign) FROM imstockbatchview GROUP BY zid,xwh,xitem,isnull(xbatch,''),xdateexp having sum(xavail*xsign)>0
GO
/****** Object:  View [dbo].[zabsysdateview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[zabsysdateview] (zid,xyear,xper,day,xstarttime,xendtime,xtime,xdate,xdatechq,xyearperdate,xdaterec) as select a.zid,CAST(YEAR(GETDATE()) as INT),CAST(Month(GETDATE()) as INT),CAST(Day(GETDATE()) as INT), '00:00:00','23:59:59',CAST(GETDATE() AS TIME),cast(GETDATE() as date),GETDATE(), CAST(CONCAT(SUBSTRING(Convert(varchar,CAST(GETDATE() AS DATE)),1,4), SUBSTRING(Convert(varchar,CAST(GETDATE() AS DATE)),6,2), SUBSTRING(Convert(varchar,CAST(GETDATE() AS DATE)),9,2)) AS INT), CAST(DATEADD(MONTH,-1,CAST(GETDATE() AS DATE)) AS DATETIME) from zbusiness a WITH (NOLOCK)
GO
/****** Object:  View [dbo].[imstockcurview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockcurview] (zid,xwh,xitem,xsign,xavail) as SELECT i.zid,i.xwh,i.xitem,1,SUM(i.xqty*i.xsign) FROM imtrn i with (nolock) JOIN caitem c ON i.zid=c.zid AND i.xitem=c.xitem WHERE  isnull(i.xwh,'')<>'' and c.xtypeobj='Product' GROUP BY i.zid,i.xwh,i.xitem HAVING SUM(i.xqty*i.xsign)>0 UNION ALL SELECT d.zid,h.xfwh,d.xitem,-1,SUM(d.xqtyord) FROM imtordetail d with (nolock) JOIN imtorheader h with (nolock) ON d.zid=h.zid AND d.xtornum=h.xtornum JOIN caitem c ON d.zid=c.zid AND d.xitem=c.xitem WHERE isnull(h.xfwh,'')<>'' and h.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed') AND d.xqtyord>0 and c.xtypeobj='Product'  GROUP BY d.zid,h.xfwh,d.xitem UNION ALL SELECT d.zid,d.xwh,d.xitem1,-1,sum(isnull(d.xqty,0)) FROM mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase JOIN caitem c on d.zid=c.zid and d.xitem1=c.xitem where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 and isnull(h.xstatusint,0)<>35 and c.xtypeobj='Product' group by d.zid,d.xwh,d.xitem1
GO
/****** Object:  View [dbo].[imstockdetview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockdetview] (zactive,zid,xitem,xdesc,xunit,xrate,xminqty,xmaxqty,xwh,xgitem,xinhand,xavail) as SELECT caitem.zactive,caitem.zid,caitem.xitem,caitem.xdesc,caitem.xunit,caitem.xrate,caitem.xminqty,caitem.xmaxqty,xcodes.xcode,caitem.xgitem, isnull((select sum(xqty*xsign) from imtrn WITH (NOLOCK) where imtrn.xitem=caitem.xitem and imtrn.xwh=xcodes.xcode and imtrn.zid=caitem.zid and left(ximtrnnum,2)<>'OT'),0), isnull((select sum(xqty*xsign) from imtrn WITH (NOLOCK) where imtrn.xitem=caitem.xitem and imtrn.xwh=xcodes.xcode and imtrn.zid=caitem.zid and left(ximtrnnum,2)<>'OT'),0) -isnull((select sum(xqtyord) from imtordetail WITH (NOLOCK) where imtordetail.xitem=caitem.xitem and (select imtorheader.xfwh from imtorheader WITH (NOLOCK) where imtorheader.xtornum=imtordetail.xtornum and imtorheader.zid=imtordetail.zid)=xcodes.xcode and imtordetail.zid=caitem.zid and (select imtorheader.xstatustor from imtorheader WITH (NOLOCK) where imtorheader.xtornum=imtordetail.xtornum and imtorheader.zid=imtordetail.zid) not in ('Confirmed','Transferred','','Rejected','Dismissed')),0) FROM caitem(NOLOCK) join xcodes(NOLOCK) on xcodes.xtype='Branch' and caitem.zid=xcodes.zid where caitem.zactive='1' and xcodes.xcode<>''
GO
/****** Object:  View [dbo].[imstockcheckview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockcheckview] (zid, xtornum, xrow, xitem, xdesc,  xfwh, xqtyreq, xunit,xunitsel, xqtycom, xqtyord,xdphqty,xavail,xqtyalc,xstatus,xdatecom)as SELECT a.zid, a.xtornum, b.xrow, b.xitem, c.xdesc,  a.xfwh,isnull(b.xqtyreq, 0),isnull(c.xunitpur,''),isnull(c.xunitsel,''), isnull(b.xqtycom,0), isnull(b.xqtyord,0),isnull(b.xdphqty,0), d.xavail,b.xqtyalc, b.xstype,(case when isnull(b.xqtyalc,0)=0 then null else a.xdatecom end) FROM imtorheader (NOLOCK) a INNER JOIN imtordetail (NOLOCK) b ON a.zid = b.zid AND a.xtornum = b.xtornum INNER JOIN caitem (NOLOCK) c ON c.zid = a.zid AND c.xitem = b.xitem INNER JOIN imstockdetview (NOLOCK) d ON a.zid = d.zid AND b.xitem = d.xitem AND a.xfwh = d.xwh
GO
/****** Object:  View [dbo].[mmprescriptionphview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmprescriptionphview](zid,xrow,xcase,xpatient,xgenericdesc,xqty,xitem1,xqtyord,xwh,xstatuspharma,xinst,xdose) as select d.zid,d.xrow,d.xcase,d.xpatient,d.xgenericdesc,isnull(d.xqty,0),d.xitem1,isnull(d.xqtyord,0),d.xwh,h.xstatuspharma,d.xinst,d.xdose from mmprescription(NOLOCK) d join mmappointment(NOLOCK) h on d.zid =h.zid and d.xcase =h.xcase where isnull(d.xqty,0)>0 and isnull(h.xstatusint,0)<>35
GO
/****** Object:  View [dbo].[userview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[userview](zid,zemail,xposition,xname,xdeptname,xdesignation,zactive,xaccess,xrole,xstaff) as select a.zid,b.zemail,a.xposition,a.xname,a.xdeptname,a.xdesignation,b.zactive,b.xaccess,b.xrole,a.xstaff from pdmst a WITH (NOLOCK) join xusers b WITH (NOLOCK) on a.zid=b.zid and a.xposition=b.xposition
GO
/****** Object:  Table [dbo].[pogrnheader]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pogrnheader](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xgrnnum] [varchar](50) NOT NULL,
	[xdate] [date] NULL,
	[xsup] [varchar](50) NULL,
	[xorg] [varchar](250) NULL,
	[xstatusgrn] [varchar](50) NULL,
	[xref] [varchar](50) NULL,
	[xpornum] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
	[xcur] [varchar](50) NULL,
	[xexch] [decimal](20, 6) NULL,
	[xlcno] [varchar](50) NULL,
	[xinvnum] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[xprimetotamt] [decimal](20, 2) NULL,
	[xbasetotamt] [decimal](20, 2) NULL,
	[xwh] [varchar](50) NULL,
	[xstatusap] [varchar](50) NULL,
	[xstatusjv] [varchar](50) NULL,
	[xdisc] [decimal](5, 2) NULL,
	[xdiscf] [decimal](20, 2) NULL,
	[xdocnum] [varchar](50) NULL,
	[xait] [decimal](20, 2) NULL,
	[xaitf] [decimal](20, 2) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xdiscamt] [decimal](20, 2) NULL,
	[xvoucher] [varchar](50) NULL,
	[xadjustment] [decimal](20, 2) NULL,
	[xdategl] [date] NULL,
	[xtotamt] [decimal](20, 2) NULL,
	[xordernum] [varchar](50) NULL,
	[xcus] [varchar](250) NULL,
	[xsign] [int] NULL,
	[xexpirydate] [date] NULL,
	[xlctype] [varchar](50) NULL,
	[xregi] [varchar](50) NULL,
	[xstaff] [varchar](50) NULL,
	[xpaymentterm] [varchar](50) NULL,
	[xbank] [varchar](50) NULL,
	[xsuperiorsp] [varchar](8) NULL,
	[xporeqnum] [varchar](50) NULL,
	[xtornum] [varchar](50) NULL,
	[xadjnum] [varchar](50) NULL,
	[xpafnum] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xacc] [varchar](50) NULL,
	[xsub] [varchar](50) NULL,
	[xcurbuyer] [varchar](50) NULL,
	[xexchbuyer] [decimal](20, 6) NULL,
	[xttcur] [varchar](50) NULL,
	[xttexch] [decimal](20, 6) NULL,
	[xstatusimgl] [varchar](50) NULL,
	[xprimebuyer] [decimal](20, 2) NULL,
	[xttbuyer] [decimal](20, 2) NULL,
	[xstatusjvgl] [varchar](50) NULL,
	[xstatusapgl] [varchar](50) NULL,
	[xpreparer] [varchar](50) NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsigndate1] [datetime] NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsigndate2] [datetime] NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsigndate3] [datetime] NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsigndate4] [datetime] NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsigndate5] [datetime] NULL,
	[xsignatory6] [varchar](50) NULL,
	[xsigndate6] [datetime] NULL,
	[xsignatory7] [varchar](50) NULL,
	[xsigndate7] [datetime] NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
	[xsignreject] [varchar](50) NULL,
	[xdatereject] [datetime] NULL,
	[xacccr] [varchar](50) NULL,
	[xpercent] [decimal](20, 2) NULL,
	[xtrn] [char](4) NULL,
	[xstatusinsp] [varchar](50) NULL,
	[xfwh] [varchar](50) NULL,
	[xamtother] [decimal](20, 2) NULL,
	[xfreightcost] [decimal](20, 2) NULL,
	[xfreightbase] [decimal](20, 2) NULL,
	[xproject] [varchar](50) NULL,
	[xchallandate] [varchar](50) NULL,
	[xstatusdoc] [varchar](50) NULL,
	[xstatusclaim] [varchar](50) NULL,
	[xstatusreq] [varchar](50) NULL,
	[xtypeobj] [varchar](50) NULL,
	[xbillno] [varchar](50) NULL,
	[xbilldate] [date] NULL,
	[xblno] [varchar](50) NULL,
	[xtankno] [varchar](50) NULL,
	[xempunit] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[xstatuspa] [varchar](50) NULL,
	[xcldate] [date] NULL,
	[xstatusaccrual] [varchar](50) NULL,
	[xvoucheraccrual] [varchar](50) NULL,
	[xassetcode] [varchar](50) NULL,
	[xaitrule] [varchar](50) NULL,
	[xyear] [int] NULL,
	[xper] [int] NULL,
	[xvatamt] [decimal](20, 2) NULL,
	[xaitamt] [decimal](20, 2) NULL,
	[xsignatory8] [varchar](50) NULL,
	[xsigndate8] [datetime] NULL,
	[xsignatory9] [varchar](50) NULL,
	[xsigndate9] [datetime] NULL,
	[xgrninvno] [varchar](50) NULL,
	[xinvvalue] [decimal](20, 3) NULL,
	[xstatusinv] [varchar](50) NULL,
	[xpreparer2] [varchar](50) NULL,
	[xsignreject2] [varchar](50) NULL,
	[xdatereject2] [datetime] NULL,
	[xnote1] [varchar](5000) NULL,
	[xboeno] [varchar](50) NULL,
	[xdateboe] [date] NULL,
	[xdaterebate] [date] NULL,
	[xpodate] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xgrnnum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pogrndetail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pogrndetail](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xgrnnum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xqtygrn] [decimal](20, 3) NULL,
	[xunitpur] [varchar](50) NULL,
	[xrate] [decimal](20, 3) NULL,
	[xlong] [varchar](1000) NULL,
	[xlineamt] [decimal](20, 2) NULL,
	[xbatch] [varchar](20) NULL,
	[xdateexp] [date] NULL,
	[xdisc] [decimal](5, 2) NULL,
	[xdiscf] [decimal](20, 2) NULL,
	[xdocrow] [int] NULL,
	[xcfpur] [decimal](20, 6) NULL,
	[xqtybonus] [int] NULL,
	[xpornum] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[xrategrn] [decimal](20, 4) NULL,
	[xvatamt] [decimal](20, 2) NULL,
	[xserial] [varchar](150) NULL,
	[xbase] [decimal](20, 2) NULL,
	[xqtycom] [decimal](20, 3) NULL,
	[xstatusimgl] [varchar](50) NULL,
	[xqtyinsp] [decimal](20, 3) NULL,
	[xvalpo] [decimal](20, 2) NULL,
	[xqtyprn] [decimal](20, 2) NULL,
	[xqtyrec] [decimal](20, 3) NULL,
	[xqtyclaim] [decimal](20, 3) NULL,
	[xbinref] [varchar](50) NULL,
	[xexchbuyer] [decimal](20, 6) NULL,
	[xttexch] [decimal](20, 6) NULL,
	[xbuyeramt] [decimal](20, 2) NULL,
	[xttamount] [decimal](20, 2) NULL,
	[xacc] [varchar](50) NULL,
	[xcdrate] [decimal](20, 2) NULL,
	[xcdamt] [decimal](20, 2) NULL,
	[xrdrate] [decimal](20, 2) NULL,
	[xrdamt] [decimal](20, 2) NULL,
	[xsupptaxrate] [decimal](20, 2) NULL,
	[xsupptaxamt] [decimal](20, 2) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xait] [decimal](20, 2) NULL,
	[xaitamt] [decimal](20, 2) NULL,
	[xatrate] [decimal](20, 2) NULL,
	[xatamt] [decimal](20, 2) NULL,
	[xqtygain] [decimal](20, 3) NULL,
	[xprimebuyer] [decimal](20, 2) NULL,
	[xttbase] [decimal](20, 2) NULL,
	[xttbuyer] [decimal](20, 2) NULL,
	[xcpoqty] [decimal](20, 2) NULL,
	[xsub] [varchar](50) NULL,
	[xfleet] [varchar](100) NULL,
	[xcode] [varchar](100) NULL,
	[xlot] [varchar](200) NULL,
	[xcottontype] [varchar](200) NULL,
	[xqtybonusgrn] [decimal](20, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xgrnnum] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pogrnitemcost]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pogrnitemcost](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xgrnnum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xcostrow] [int] NOT NULL,
	[xaccdr] [varchar](50) NULL,
	[xacccr] [varchar](50) NULL,
	[xcus] [varchar](250) NULL,
	[xprime] [decimal](20, 2) NULL,
	[xnote] [varchar](5000) NULL,
	[xtype] [varchar](50) NULL,
	[xstatusjv] [varchar](50) NULL,
	[xdate] [date] NULL,
	[xvoucher] [varchar](50) NULL,
	[xtrn] [char](4) NULL,
	[xpornum] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xgrnnum] ASC,
	[xrow] ASC,
	[xcostrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[grnitemcostview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[grnitemcostview] (zid,xgrnnum,xlcno,xitem,xdesc,xqtygrn,xrow,xdutycost)as Select a.zid,a.xgrnnum,a.xlcno,b.xitem,c.xdesc,b.xqtygrn,b.xrow, isnull((select SUM(p.xprime) from pogrnitemcost p join pogrndetail gd on p.zid=gd.zid and p.xgrnnum=gd.xgrnnum and p.xrow=gd.xrow join pogrnheader gh on gh.zid=gd.zid and gh.xgrnnum=gd.xgrnnum WHERE gh.zid=a.zid and gd.xpornum=a.xpornum and gd.xdocrow=b.xrow),0) from pogrnheader a join pogrndetail b on a.zid=b.zid and a.xgrnnum=b.xgrnnum join caitem c on b.zid=c.zid and b.xitem=c.xitem where a.xtype='LC' and a.xlcno<>''
GO
/****** Object:  View [dbo].[mmphprescriptionview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmphprescriptionview](zid,xrow,xcase,xpatient,xname,xgenericdesc,xqtyord,xinst,xdose,xdosage,xfrequency,xtakingtime,xduration1,xdurationday,xmedcadvice,xroute,xrem) as select d.zid,d.xrow,d.xcase,a.xstaff,p.xname,d.xgenericdesc,isnull(d.xqtyord,0),d.xinst,d.xdose,d.xdosage,d.xfrequency,d.xtakingtime,d.xduration1,d.xdurationday,d.xmedcadvice,d.xroute,d.xrem from mmappointment a join mmprescription(NOLOCK) d on a.zid=d.zid and a.xcase=d.xcase Left Join pdmst (NOLOCK) p on a.zid =p.zid and a.xstaff=p.xstaff
GO
/****** Object:  View [dbo].[mmappprescview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmappprescview] (zid,xcase,xdoctor,xorg,xpatient,xstaff,xname,xdate,xstatus,xstatuspharma,xdept) as select a.zid,a.xcase,a.xdoctor,c.xname,a.xpatient,a.xstaff,b.xname,a.xdate,a.xstatus,a.xstatuspharma,c.xdeptname from mmappointment (NOLOCK) a  left join pdsignph (NOLOCK) b on a.zid=b.zid and a.xstaff=b.xstaff left join pdmst (NOLOCK) c on a.zid=c.zid and a.xdoctor=c.xstaff
GO
/****** Object:  View [dbo].[mmappointmentview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmappointmentview] (ztime,zutime,zauserid,zuuserid,zid,xyearperdate,xdate,xdoctor,xcase,xrow,xstaff,xname,xapptime,xmobile,xstatus,xnote,xfdate,xorg,xage,xdeptname,xposition,departmentname,designation,xstatuspharma,xstatusinvestigation,xpatient,xregino,xprofdegree) as select a.ztime,a.zutime,a.zauserid,a.zuuserid,a.zid,a.xyearperdate,convert(VARCHAR, a.xdate, 106),a.xdoctor,a.xcase,a.xrow,isnull(a.xstaff,''), case when isnull(b.xname,'')='' then isnull(a.xstaff,'') else isnull(b.xname,'') end  ,FORMAT(CAST( a.xapptime AS datetime), 'hh:mmtt'),isnull(a.xmobile,''),a.xstatus,a.xnote,a.xdate,p.xname,a.xage,p.xdeptname,a.zauserid,p.departmentname,p.designationname,a.xstatuspharma,a.xstatusinvestigation,a.xpatient,p.xregino,p.xprofdegree from mmappointment (NOLOCK) a LEFT join pdmstview (NOLOCK) p on a.zid=p.zid and a.xdoctor=p.xstaff left join pdmst (NOLOCK) b on a.zid=b.zid and a.xstaff=b.xstaff
GO
/****** Object:  View [dbo].[mmappointview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmappointview] (ztime,zutime,zauserid,zuuserid,zid,xcase,xdoctor,xpatient,xname,xdate,xrow,xyearperdate,xage,xsex,xbirthdate,xapptype,xstatus,departmentname,designation) as select a.ztime,a.zutime,a.zauserid,a.zuuserid,a.zid,a.xcase,a.xdoctor,a.xpatient,b.xname,a.xdate,a.xrow,a.xyearperdate,a.xage,b.xsex,b.xbirthdate,a.xapptype,a.xstatus,b.departmentname,b.designationname from mmappointment (NOLOCK) a  left join pdmstview (NOLOCK) b on a.zid=b.zid and a.xstaff=b.xstaff
GO
/****** Object:  View [dbo].[caitemview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[caitemview](zid,xitem,xdesc,xgitem, xcitem,xcatitem,xunitpur,xhscode,xunit,xtitem,zactive,xpnature,xunitsel,xminqty,xtype,xcus,xitmedept,xitemsubdept,xpackweightnet,xstocktype,xstockcat,xnote,xmaxqty,xgenericname,xgenericdesc,xtypeobj,xgitemdesc,xcfpur,xrate,xcost)as Select c.zid,c.xitem,c.xdesc,c.xgitem, c.xcitem,c.xcatitem,c.xunitpur,c.xhscode,c.xunit,c.xtitem,c.zactive,c.xpnature,c.xunitsel,c.xminqty,c.xtype,c.xcus,c.xitmedept,c.xitemsubdept,c.xpackweightnet,c.xstocktype,c.xstockcat,c.xnote,c.xmaxqty,c.xgenericname,c.xgenericdesc,cc.xtypeobj,cc.xlong,c.xcfpur,c.xrate,c.xcost  from caitem(NOLOCK) c join xcodes(NOLOCK) cc on c.zid=cc.zid and c.xgitem=cc.xcode where cc.xtype='Item Group'
GO
/****** Object:  View [dbo].[mmprescriptionopenqty]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmprescriptionopenqty](zid,xcase,xwh,xitem1,xqty) as select d.zid,d.xcase,d.xwh,d.xitem1,isnull(d.xqty,0) from mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 and h.xstatusint<>35
GO
/****** Object:  View [dbo].[mmlaborderview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmlaborderview] (ztime,zutime,zid,zauserid,zuuserid,xcase,xepisodeno,xepisodetype,xrow,xitem,xdesc,xgitem,xlong,xrem,xstaff,xqty,xqtyord3,xinvestigationstatus,xotherinfo,button) as select a.ztime,a.zutime,a.zid,a.zauserid,a.zuuserid,a.xcase,a.xepisodeno,a.xepisodetype,a.xrow,a.xitem,p.xdesc,p.xgitem,d.xlong,a.xrem,b.xstaff,a.xqty,a.xqtyord3,a.xinvestigationstatus,a.xotherinfo,'<button id="deletebtn" onclick="deletebtn('''+a.xitem+''','''+cast (a.xrow as varchar)+''')" type="button" class="btn btn-danger">Delete</button>' from mmlaborder a WITH (NOLOCK) join caitem p on a.zid=p.zid and a.xitem=p.xitem JOIN caitem c on a.zid=c.zid and a.xitem=c.xitem JOIN xcodes d ON p.zid=d.zid and p.xgitem = d.xcode JOIN mmappointment b WITH (NOLOCK) ON a.zid=b.zid and a.xcase = b.xcase where d.xtype='Item Group'
GO
/****** Object:  Table [dbo].[cacus]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cacus](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xcus] [varchar](250) NOT NULL,
	[xorg] [varchar](250) NULL,
	[xmadd] [varchar](250) NULL,
	[xemail] [varchar](250) NULL,
	[xphone] [varchar](150) NULL,
	[xfax] [varchar](250) NULL,
	[xurl] [varchar](50) NULL,
	[xcrlimit] [decimal](20, 2) NULL,
	[xgcus] [varchar](50) NULL,
	[xgsup] [varchar](50) NULL,
	[xstatuscus] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xterritory] [varchar](50) NULL,
	[xmarket] [varchar](50) NULL,
	[xsp] [varchar](50) NULL,
	[xcrtermtype] [varchar](50) NULL,
	[xcrterms] [int] NULL,
	[xpaymentterm] [varchar](50) NULL,
	[xdateeff] [date] NULL,
	[xdateexp] [date] NULL,
	[xso] [varchar](50) NULL,
	[xroute] [varchar](50) NULL,
	[xlastcrdate] [date] NULL,
	[xinvtoinvcr] [varchar](50) NULL,
	[xallowcrinv] [varchar](50) NULL,
	[xallbndisc] [varchar](50) NULL,
	[xref] [varchar](50) NULL,
	[xcontact] [varchar](250) NULL,
	[xdisc] [decimal](5, 2) NULL,
	[xtype] [varchar](50) NULL,
	[xsupcat] [varchar](50) NULL,
	[xmobile] [varchar](40) NULL,
	[xpaymenttype] [varchar](50) NULL,
	[xcusold] [varchar](50) NULL,
	[xvatregno] [varchar](250) NULL,
	[xvatdef] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xcity] [varchar](50) NULL,
	[xcountry] [varchar](50) NULL,
	[xtin] [varchar](50) NULL,
	[xlicense] [varchar](50) NULL,
	[xpostcode] [varchar](20) NULL,
	[xcontactphn] [varchar](50) NULL,
	[xalias] [varchar](500) NULL,
	[xnum] [int] NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xattn] [varchar](250) NULL,
	[xbank] [varchar](50) NULL,
	[xpadd] [varchar](500) NULL,
	[xoccupation] [varchar](50) NULL,
	[xregion] [varchar](50) NULL,
	[xdiscdetamt] [decimal](20, 2) NULL,
	[xbranch] [varchar](50) NULL,
	[xporamt] [decimal](20, 2) NULL,
	[xprdcounter] [int] NULL,
	[xdeliloc] [varchar](250) NULL,
	[xdesignation] [varchar](50) NULL,
	[xcustype] [varchar](50) NULL,
	[xdelicontact] [varchar](100) NULL,
	[xdelicontactphn] [varchar](50) NULL,
	[xsalesperson] [varchar](50) NULL,
	[xtransshipment] [varchar](50) NULL,
	[xunderateinv] [varchar](50) NULL,
	[xnid] [varchar](50) NULL,
	[xcuscomptype] [varchar](50) NULL,
	[xdateinactive] [date] NULL,
	[xlocation] [varchar](150) NULL,
	[xowner] [varchar](50) NULL,
	[xownerph] [varchar](50) NULL,
	[xbin] [varchar](50) NULL,
	[xircno] [varchar](50) NULL,
	[xerc] [varchar](50) NULL,
	[xownership] [varchar](50) NULL,
	[xecoact] [varchar](50) NULL,
	[xdistrict] [varchar](50) NULL,
	[xdivision] [varchar](50) NULL,
	[xdm] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xacc] [varchar](50) NULL,
	[xacctype] [varchar](50) NULL,
	[xrouting] [varchar](50) NULL,
	[xswiftcode] [varchar](50) NULL,
	[xcur] [varchar](50) NULL,
	[xthana] [varchar](50) NULL,
	[xbeneficiary] [varchar](250) NULL,
	[xstype] [varchar](550) NULL,
	[xpreparer] [varchar](50) NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsigndate1] [datetime] NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsigndate2] [datetime] NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsigndate3] [datetime] NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsigndate4] [datetime] NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsigndate5] [datetime] NULL,
	[xsignatory6] [varchar](50) NULL,
	[xsigndate6] [datetime] NULL,
	[xsignatory7] [varchar](50) NULL,
	[xsigndate7] [datetime] NULL,
	[xsignreject] [varchar](50) NULL,
	[xdatereject] [datetime] NULL,
	[xidsup] [varchar](8) NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
	[xsignplace] [int] NULL,
	[xnote1] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[imstockopdpharma]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockopdpharma] (zactive,zid,xitem,xdesc,xgenericdesc,xtitem,xsumtype,xwh,xpackqty,xorg,xavail) as SELECT caitem.zactive,caitem.zid,caitem.xitem,caitem.xdesc,caitem.xgenericdesc,caitem.xtitem,caitem.xsumtype,'MSD-01',CAST(xcfpur as DECIMAL(20,2)),isnull((select isnull(xorg,'') from cacus where zid=caitem.zid and xcus=caitem.xcus and xtype='Supplier'),''), isnull((select sum(xqty*xsign) from imtrn(NOLOCK) where imtrn.xitem=caitem.xitem and imtrn.xwh='MSD-01' and imtrn.zid=caitem.zid),0) -isnull((select sum(xqtyord) from imtordetail(NOLOCK) where imtordetail.xitem=caitem.xitem and (select imtorheader.xfwh from imtorheader(NOLOCK) where imtorheader.xtornum=imtordetail.xtornum and imtorheader.zid=imtordetail.zid)='MSD-01' and imtordetail.zid=caitem.zid and (select imtorheader.xstatustor from imtorheader(NOLOCK) where imtorheader.xtornum=imtordetail.xtornum and imtorheader.zid=imtordetail.zid) not in ('Confirmed','Transferred','','Rejected','Dismissed')),0) -isnull((select sum(xqty) from mmprescriptionopenqty where mmprescriptionopenqty.zid=caitem.zid  and mmprescriptionopenqty.xitem1=caitem.xitem and mmprescriptionopenqty.xwh='MSD-01' ),0) FROM caitem(NOLOCK)
GO
/****** Object:  View [dbo].[imstockopdhbatch]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[imstockopdhbatch] (zid,xwh,xitem,xdesc,xgenericdesc,xsign,xavail) AS SELECT i.zid,i.xwh,i.xitem,c.xdesc,c.xgenericdesc,1,SUM(i.xqty*i.xsign) FROM imtrn i WITH (NOLOCK) JOIN caitem c ON i.zid=c.zid AND i.xitem=c.xitem GROUP BY i.zid,i.xwh,i.xitem,c.xdesc,c.xgenericdesc UNION ALL SELECT d.zid,h.xfwh,e.xitem,c.xdesc,c.xgenericdesc,-1,SUM(e.xqtyord)  FROM imtordetail d WITH (NOLOCK)  JOIN imtorheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xtornum=h.xtornum JOIN caitem c ON d.zid=c.zid AND d.xitem=c.xitem join imtordetailbatch e WITH (NOLOCK) on d.zid=e.zid and d.xtornum=e.xtornum and d.xrow=e.xrow  and e.xstatusint=0 WHERE isnull(h.xfwh,'')='MSD-01' and h.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed') AND d.xqtyord>0 GROUP BY d.zid,h.xfwh,e.xitem,c.xdesc,c.xgenericdesc UNION ALL SELECT d.zid,d.xwh,d.xitem1,c.xdesc,d.xgenericdesc,-1,sum(isnull(d.xqty,0)) FROM mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase JOIN caitem c on d.zid=c.zid and d.xitem1=c.xitem where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 group by d.zid,d.xwh,d.xitem1,c.xdesc,d.xgenericdesc
GO
/****** Object:  View [dbo].[branchview2]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[branchview2](zid,xtype,xlong,xcode,xmadd,xphone,xfax,xemail,xtypeobj,zactive,xwh,xacc,zbid,xtypestore,xfwh,xacwh) AS SELECT a.zid,b.xtype,b.xlong,b.xcode,b.xmadd,b.xphone,b.xfax,b.xemail,b.xtypeobj,b.zactive,b.xwh,cast(a.zid as varchar(50)),CAST(a.zid as varchar(50)),isnull(xtypestore,''), (CASE WHEN b.xlong like '%QC%' then '' ELSE b.xwh END),(CASE when isnull(b.xwh,'')='' then '01' else isnull(b.xwh,'') END)  FROM zbusiness a JOIN xcodes(NOLOCK) b ON a.zid=b.zid WHERE b.xtype='Branch'
GO
/****** Object:  View [dbo].[caitemrptview2]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[caitemrptview2](zid,xitem,xdesc,xcatitem,xvatamt,xtitem,xbatmg) AS SELECT zid,xitem,xdesc,xcatitem,xvatamt,xtitem,xbatmg FROM caitem (NOLOCK)
GO
/****** Object:  View [dbo].[mmprescriptionview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create view [dbo].[mmprescriptionview] (ztime,zutime,zauserid,zuuserid,zid,xgenericdesc,xinst,xtdate,xdosage,xdose,xfrequency,xtakingtime,xdurationday,xqty,xroute,xduration1, xstaff,xcase,xgenericname,xstrengthgn,xdrug,xqtyord,xdrugtype,xfdate,xrem,xmeddetail,xrow,xmedcadvice,button) as select a.ztime,a.zutime,a.zauserid,a.zuuserid,b.zid,a.xgenericdesc,a.xinst,convert(VARCHAR, b.xdate, 106),a.xdosage,a.xdose,a.xfrequency,a.xtakingtime,a.xdurationday,a.xqty,a.xroute,a.xduration1,b.xstaff,b.xcase,a.xgenericname,a.xstrengthgn,a.xdrug,a.xqtyord,a.xdrugtype,a.xfdate,a.xrem, a.xmeddetail,a.xrow,a.xmedcadvice,'<button id="deletebtn" onclick="deletebtn('''+a.xgenericdesc+''','''+cast (a.xrow as varchar)+''')" type="button" class="btn btn-danger" style="width: 70px;background-color: #dc3545;border-color: #dc3545;line-height: 1.2;color: #fff;display: inline-block;font-weight: 400;font-size: 1rem;padding: 0.375rem 0.75rem;">Delete</button>' from mmappointment b WITH (NOLOCK)  left join mmprescription a WITH (NOLOCK) on a.zid=b.zid and a.xcase=b.xcase
GO
/****** Object:  View [dbo].[projectallview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[projectallview](zid,xtype,xlong,xcode,xmadd,xphone,xfax,xemail,xproject,zactive) AS SELECT a.zid,b.xtype,b.xlong,b.xcode,b.xmadd,b.xphone,b.xfax,b.xemail,b.xcode,b.zactive FROM zbusiness a JOIN xcodes b ON a.zid=b.zid WHERE b.xtype='Project'
GO
/****** Object:  View [dbo].[caitemchargesview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE view [dbo].[caitemchargesview](zid,xitem,xdesc,xgitem, xcitem,xunit,xtitem,xtype,xlong,xgenericname,xgenericdesc) as  SELECT  dbo.caitem.zid,dbo.caitem.xitem, dbo.caitem.xdesc, dbo.caitem.xgitem, dbo.caitem.xcitem, dbo.caitem.xunit, dbo.caitem.xtitem,dbo.xcodes.xtype,dbo.xcodes.xlong,dbo.caitem.xgenericname,dbo.caitem.xgenericdesc FROM   dbo.caitem INNER JOIN dbo.xcodes ON dbo.caitem.zid = dbo.xcodes.zid and dbo.caitem.xgitem = dbo.xcodes.xcode where dbo.xcodes.xtype='Item Group'  and dbo.caitem.zactive='1'
GO
/****** Object:  View [dbo].[mmvitalphview]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[mmvitalphview] (zid,xcase,xdate,xdoctor,xpatient,xrow,xpulserate,xbp,xtemperature,xspo2,xrbs,xheartrate,xbmigyn,xheightvit,xweightvit,xbsa,xnote,xibwwom,xibwmen) as select zid,isnull(xcase,''),xdate,isnull(xdoctor,''),isnull(xstaff,''),isnull(xrow,''),isnull(xpulserate,''),isnull(xbp,''),isnull(xtemperature,''),isnull(xspo2,''),isnull(xrbs,''),isnull(xheartrate,''),isnull(xbmigyn,'0.00'),isnull(xheightvit,'0.00'),isnull(xweightvit,'0.00'),isnull(xbsa,'0'),isnull(xnote,''),isnull(xibwwom,'0.00'),isnull(xibwmen,'0.00')from vitalssign (NOLOCK)
GO
/****** Object:  Table [dbo].[apvprcs]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[apvprcs](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xposition] [varchar](50) NOT NULL,
	[xdesignation] [varchar](50) NULL,
	[xrow] [int] NULL,
	[xstatus] [varchar](50) NULL,
	[xdesc] [varchar](1000) NULL,
	[zactive] [varchar](1) NULL,
	[xsubprcs] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtypetrn] ASC,
	[xposition] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[caitembonus]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[caitembonus](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xitem] [varchar](50) NOT NULL,
	[xwh] [varchar](50) NULL,
	[xdateeff] [date] NULL,
	[xdateexp] [date] NULL,
	[xqty] [int] NULL,
	[xqtybonus] [int] NULL,
	[xrow] [int] NOT NULL,
	[xgiftitem] [varchar](50) NULL,
	[xqty1] [int] NULL,
	[xqtybonus1] [int] NULL,
	[xqty2] [int] NULL,
	[xqtybonus2] [int] NULL,
	[xqty3] [int] NULL,
	[xqtybonus3] [int] NULL,
	[xqty4] [int] NULL,
	[xqtybonus4] [int] NULL,
	[xpaymentterm] [varchar](50) NULL,
	[xbnprintstatus] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xitem] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[caitemwh]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[caitemwh](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtype] [varchar](50) NOT NULL,
	[xcode] [varchar](100) NOT NULL,
	[xitem] [varchar](50) NOT NULL,
	[xminqty] [decimal](20, 3) NULL,
	[xmaxqty] [decimal](20, 3) NULL,
	[xgitem] [varchar](100) NULL,
	[xreordqty] [decimal](20, 3) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtype] ASC,
	[xcode] ASC,
	[xitem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[casup]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[casup](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xsup] [varchar](50) NOT NULL,
	[xorg] [varchar](250) NULL,
	[xmadd] [varchar](250) NULL,
	[xpadd] [varchar](500) NULL,
	[xemail] [varchar](250) NULL,
	[xphone] [varchar](150) NULL,
	[xfax] [varchar](250) NULL,
	[xurl] [varchar](50) NULL,
	[xcontact] [varchar](250) NULL,
	[xmobile] [varchar](40) NULL,
	[xgsup] [varchar](50) NULL,
	[xsupcat] [varchar](50) NULL,
	[xstatussup] [varchar](50) NULL,
	[xait] [decimal](20, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xsup] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[genericmaster]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[genericmaster](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xgenericname] [varchar](100) NOT NULL,
	[xdrugtype] [varchar](100) NULL,
	[xstrength] [varchar](50) NULL,
	[xunit] [varchar](50) NULL,
	[xgenericdesc] [varchar](350) NOT NULL,
	[xthclassification] [varchar](100) NULL,
	[xgindication] [varchar](100) NULL,
	[xdruginteraction] [varchar](100) NULL,
	[xnote1] [varchar](5000) NULL,
	[xfoodinteraction] [varchar](250) NULL,
	[xnote2] [varchar](500) NULL,
	[xdiagnosis] [varchar](200) NULL,
	[xnote3] [varchar](500) NULL,
	[xcontraindication] [varchar](2000) NULL,
	[xpregcat] [varchar](100) NULL,
	[xdrugadjucmentrenal] [varchar](100) NULL,
	[xmaxdose] [int] NULL,
	[xunitdose] [varchar](50) NULL,
	[xcaution] [varchar](2000) NULL,
	[xtemplateinst] [varchar](100) NULL,
	[xlasa] [varchar](2000) NULL,
	[xotherinfo] [varchar](2000) NULL,
	[xdosage] [varchar](50) NULL,
	[xfrequency] [varchar](50) NULL,
	[xroute] [varchar](50) NULL,
	[xtakingtime] [varchar](50) NULL,
	[xdose] [varchar](50) NULL,
	[xitem] [varchar](50) NULL,
	[xgenericid] [varchar](50) NULL,
	[xbrand] [varchar](500) NULL,
	[xname] [varchar](450) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xgenericname] ASC,
	[xgenericdesc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[imstoretag]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imstoretag](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xwh] [varchar](50) NOT NULL,
	[xtype] [varchar](50) NOT NULL,
	[xtwh] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xnote] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xwh] ASC,
	[xtype] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[imtrndetail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imtrndetail](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[ximtrnnum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xdate] [date] NULL,
	[xqty] [int] NULL,
	[xrate] [decimal](20, 3) NULL,
	[xval] [decimal](20, 2) NULL,
	[xdocnum] [varchar](50) NULL,
	[xdocrow] [int] NULL,
	[xnote] [varchar](5000) NULL,
	[xsign] [int] NULL,
	[xunit] [varchar](50) NULL,
	[xref] [varchar](50) NULL,
	[xstatusjv] [varchar](50) NULL,
	[xvoucher] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[ximtrnnum] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mmdocdrugprofile]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmdocdrugprofile](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xgenericname] [varchar](100) NULL,
	[xdesc] [varchar](1000) NULL,
	[xgenericdesc] [varchar](350) NULL,
	[xstrengthgn] [varchar](50) NULL,
	[xdosage] [varchar](50) NULL,
	[xdose] [varchar](50) NULL,
	[xfrequency] [varchar](50) NULL,
	[xtakingtime] [varchar](50) NULL,
	[xduration1] [varchar](50) NULL,
	[xdurationday] [varchar](50) NULL,
	[xrem] [varchar](1000) NULL,
	[xdrugtype] [varchar](100) NULL,
	[xroute] [varchar](50) NULL,
	[xtemplateinst] [varchar](100) NULL,
	[xmeddetail] [varchar](500) NULL,
	[xcollection] [char](1) NULL,
	[xqtyord] [decimal](20, 3) NULL,
	[xinst] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mmdoclabprofile]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmdoclabprofile](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xitem] [varchar](50) NULL,
	[xrem] [varchar](1000) NULL,
	[xcollection] [char](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mmonexam]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmonexam](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xcase] [varchar](50) NOT NULL,
	[xepisodetype] [varchar](50) NULL,
	[xepisodeno] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
	[xrem] [varchar](1000) NULL,
	[xcode] [varchar](100) NULL,
	[xnote] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mmprescriptiondummy]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mmprescriptiondummy](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xcase] [varchar](50) NOT NULL,
	[xpatient] [varchar](50) NULL,
	[xfdate] [date] NULL,
	[xtdate] [date] NULL,
	[xgenericname] [varchar](100) NULL,
	[xstrengthgn] [varchar](50) NULL,
	[xunit] [varchar](50) NULL,
	[xdrugtype] [varchar](100) NULL,
	[xdesc] [varchar](1000) NULL,
	[xgenericdesc] [varchar](350) NULL,
	[xinst] [varchar](50) NULL,
	[xtemplateinst] [varchar](100) NULL,
	[xdrug] [varchar](50) NULL,
	[xdosage] [varchar](50) NULL,
	[xdose] [varchar](50) NULL,
	[xfrequency] [varchar](50) NULL,
	[xtakingtime] [varchar](50) NULL,
	[xduration1] [varchar](50) NULL,
	[xdurationday] [varchar](50) NULL,
	[xqty] [int] NULL,
	[xrem] [varchar](1000) NULL,
	[xroute] [varchar](50) NULL,
	[xmeddetail] [varchar](500) NULL,
	[xmedcadvice] [varchar](500) NULL,
	[xitem1] [varchar](50) NULL,
	[xitem2] [varchar](50) NULL,
	[xitem3] [varchar](50) NULL,
	[xqtyord1] [decimal](20, 3) NULL,
	[xqtyord2] [decimal](20, 3) NULL,
	[xqtyord3] [decimal](20, 3) NULL,
	[xinst2] [varchar](50) NULL,
	[xinst3] [varchar](50) NULL,
	[xwh] [varchar](50) NULL,
	[xwhcomp] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xcase] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[opdef]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[opdef](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NULL,
	[xvatrate] [decimal](20, 2) NULL,
	[xcusserial] [varchar](50) NULL,
	[xsupserial] [varchar](50) NULL,
	[xservicecharge] [decimal](20, 2) NULL,
	[xdisc] [decimal](5, 2) NULL,
	[xyear] [int] NULL,
	[xyesno] [varchar](50) NULL,
	[xinvserial] [varchar](50) NULL,
	[xacc] [varchar](50) NULL,
	[xdiscstatus] [varchar](50) NULL,
	[xoverdelivery] [varchar](50) NULL,
	[xcrnapproval] [varchar](50) NULL,
	[xdiscapproval] [varchar](50) NULL,
	[xallowdues] [varchar](50) NULL,
	[xallowcorporate] [varchar](50) NULL,
	[xnum] [int] NULL,
	[xregbillsettle] [varchar](50) NULL,
	[xwhopd] [varchar](50) NULL,
	[xwhipd] [varchar](50) NULL,
	[xwhem] [varchar](50) NULL,
	[xwhdc] [varchar](50) NULL,
	[xwhpharma] [varchar](50) NULL,
	[xallowduesopd] [varchar](50) NULL,
	[xallowduesipd] [varchar](50) NULL,
	[xsignonoff] [varchar](50) NULL,
	[xipdpharmacy] [varchar](50) NULL,
	[xwhpharmamain] [varchar](50) NULL,
	[xsignonoffdt] [varchar](50) NULL,
	[xstaffrequired] [varchar](50) NULL,
	[xupdateuser] [varchar](50) NULL,
	[xepisodetype] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdappsuperior]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdappsuperior](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xcode] [varchar](100) NOT NULL,
	[xstatus] [varchar](50) NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsignatory6] [varchar](50) NULL,
	[xsignatory7] [varchar](50) NULL,
	[xsignatory8] [varchar](50) NULL,
	[xsignatory9] [varchar](50) NULL,
	[xsignatory1p1] [varchar](50) NULL,
	[xsignatory2p1] [varchar](50) NULL,
	[xsignatory3p1] [varchar](50) NULL,
	[xsignatory4p1] [varchar](50) NULL,
	[xsignatory5p1] [varchar](50) NULL,
	[xsignatory6p1] [varchar](50) NULL,
	[xsignatory7p1] [varchar](50) NULL,
	[xsignatory8p1] [varchar](50) NULL,
	[xsignatory9p1] [varchar](50) NULL,
	[xsignatory1p2] [varchar](50) NULL,
	[xsignatory2p2] [varchar](50) NULL,
	[xsignatory3p2] [varchar](50) NULL,
	[xsignatory4p2] [varchar](50) NULL,
	[xsignatory5p2] [varchar](50) NULL,
	[xsignatory6p2] [varchar](50) NULL,
	[xsignatory7p2] [varchar](50) NULL,
	[xsignatory8p2] [varchar](50) NULL,
	[xsignatory9p2] [varchar](50) NULL,
	[xnum1] [int] NULL,
	[xnum2] [int] NULL,
	[xnum3] [int] NULL,
	[xnum4] [int] NULL,
	[xnum5] [int] NULL,
	[xnum6] [int] NULL,
	[xnum7] [int] NULL,
	[xnum8] [int] NULL,
	[xnum9] [int] NULL,
	[xapstatus1] [varchar](50) NULL,
	[xapstatus2] [varchar](50) NULL,
	[xapstatus3] [varchar](50) NULL,
	[xapstatus4] [varchar](50) NULL,
	[xapstatus5] [varchar](50) NULL,
	[xapstatus6] [varchar](50) NULL,
	[xapstatus7] [varchar](50) NULL,
	[xapstatus8] [varchar](50) NULL,
	[xapstatus9] [varchar](50) NULL,
	[xdeligate1] [varchar](50) NULL,
	[xdeligate2] [varchar](50) NULL,
	[xdeligate3] [varchar](50) NULL,
	[xdeligate4] [varchar](50) NULL,
	[xdeligate5] [varchar](50) NULL,
	[xdeligate6] [varchar](50) NULL,
	[xdeligate7] [varchar](50) NULL,
	[xdeligate8] [varchar](50) NULL,
	[xdeligate9] [varchar](50) NULL,
	[xdeligate1p1] [varchar](50) NULL,
	[xdeligate2p1] [varchar](50) NULL,
	[xdeligate3p1] [varchar](50) NULL,
	[xdeligate4p1] [varchar](50) NULL,
	[xdeligate5p1] [varchar](50) NULL,
	[xdeligate6p1] [varchar](50) NULL,
	[xdeligate7p1] [varchar](50) NULL,
	[xdeligate8p1] [varchar](50) NULL,
	[xdeligate9p1] [varchar](50) NULL,
	[xdeligate1p2] [varchar](50) NULL,
	[xdeligate2p2] [varchar](50) NULL,
	[xdeligate3p2] [varchar](50) NULL,
	[xdeligate4p2] [varchar](50) NULL,
	[xdeligate5p2] [varchar](50) NULL,
	[xdeligate6p2] [varchar](50) NULL,
	[xdeligate7p2] [varchar](50) NULL,
	[xdeligate8p2] [varchar](50) NULL,
	[xdeligate9p2] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xtypetrn] ASC,
	[xcode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pddef]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pddef](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xpfconrate] [decimal](20, 2) NULL,
	[xpfeffdate] [date] NULL,
	[xpfloanintrate] [decimal](20, 2) NULL,
	[xpfloaninteffdate] [date] NULL,
	[xconveyancelimit] [decimal](20, 2) NULL,
	[xinvrebaterate] [decimal](20, 2) NULL,
	[xinvlimitper] [decimal](20, 2) NULL,
	[xinvlimitmax] [decimal](20, 2) NULL,
	[xinveffdate] [date] NULL,
	[xhrentper] [decimal](20, 2) NULL,
	[xhrentmax] [decimal](20, 2) NULL,
	[xhrenteffdate] [date] NULL,
	[xfreefurrate] [decimal](20, 2) NULL,
	[xmedexemption] [decimal](20, 2) NULL,
	[xwelfarefund] [decimal](20, 2) NULL,
	[xutility] [decimal](20, 2) NULL,
	[xhrentdeduct] [decimal](20, 2) NULL,
	[xuniondues] [decimal](20, 2) NULL,
	[xyear] [int] NULL,
	[xothours] [decimal](20, 2) NULL,
	[xotrate] [decimal](20, 2) NULL,
	[xoffset] [int] NULL,
	[xcsrconrate] [decimal](20, 0) NULL,
	[xcode] [varchar](100) NULL,
	[xemail] [varchar](250) NULL,
	[xpassword] [varchar](50) NULL,
	[xstartday] [int] NULL,
	[xendday] [int] NULL,
	[xbimage] [varchar](250) NULL,
	[xnidimage] [varchar](50) NULL,
	[xattimecon] [varchar](6) NULL,
	[xpunchcon] [varchar](6) NULL,
	[xemptimeout] [varchar](6) NULL,
	[xltelfine] [int] NULL,
	[xfine] [decimal](20, 2) NULL,
	[xtypetrn] [varchar](50) NULL,
	[xpfmax] [decimal](20, 2) NULL,
	[xempserial] [varchar](50) NULL,
	[xyesno] [varchar](50) NULL,
	[xhour] [decimal](20, 2) NULL,
	[xprevroster] [varchar](50) NULL,
	[xprevleave] [varchar](50) NULL,
	[xdateexp] [date] NULL,
	[xrateinterest] [decimal](20, 3) NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pddependent]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pddependent](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xname] [varchar](450) NULL,
	[xbirthdate] [date] NULL,
	[xsex] [varchar](50) NULL,
	[xnid] [varchar](50) NULL,
	[xadd] [varchar](500) NULL,
	[xcontact] [varchar](250) NULL,
	[xemail] [varchar](250) NULL,
	[xrelation] [varchar](50) NULL,
	[xfile] [varchar](50) NULL,
	[ximage] [image] NULL,
	[xtype] [varchar](50) NULL,
	[xposition] [varchar](50) NULL,
	[xdependent] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdempat]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdempat](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xdate] [date] NULL,
	[xyearperdate] [int] NOT NULL,
	[xposition] [varchar](50) NULL,
	[xtimein] [datetime] NULL,
	[xtimeout] [datetime] NULL,
	[xstatus] [varchar](50) NULL,
	[xnote] [varchar](5000) NULL,
	[xdayname] [varchar](50) NULL,
	[xemplate] [char](6) NULL,
	[xstatuslate] [varchar](50) NULL,
	[xempearly] [char](6) NULL,
	[xstatusel] [varchar](50) NULL,
	[xshift] [varchar](50) NOT NULL,
	[xstatusabsent] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xyearperdate] ASC,
	[xshift] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdemptransfer]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdemptransfer](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xfdept] [varchar](50) NULL,
	[xtdept] [varchar](50) NULL,
	[xfpos] [varchar](50) NULL,
	[xtpos] [varchar](50) NULL,
	[xdate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdexperience]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdexperience](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xname] [varchar](450) NULL,
	[xduration] [decimal](20, 3) NULL,
	[xnote] [varchar](5000) NULL,
	[xtypetrn] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[xnote1] [varchar](5000) NULL,
	[xsid] [varchar](50) NULL,
	[xdatefrom] [date] NULL,
	[xdateto] [date] NULL,
	[xfdate] [date] NULL,
	[xtdate] [date] NULL,
	[xorg] [varchar](250) NULL,
	[xdesignation] [varchar](50) NULL,
	[xphone] [varchar](150) NULL,
	[xpadd] [varchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdmstdetail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdmstdetail](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xdate] [date] NULL,
	[xlong] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdsignatoryrpt]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdsignatoryrpt](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsignatory6] [varchar](50) NULL,
	[xsignatory7] [varchar](50) NULL,
	[xsignatory8] [varchar](50) NULL,
	[xsignatory9] [varchar](50) NULL,
	[xnofsignatory] [int] NULL,
	[xlong] [varchar](1000) NULL,
	[xsubprcs] [varchar](50) NULL,
	[xrow] [int] NULL,
	[xyesno] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtypetrn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pdsuperior]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pdsuperior](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xshift] [varchar](50) NOT NULL,
	[xreqtype] [varchar](50) NOT NULL,
	[xprime] [decimal](20, 2) NOT NULL,
	[xmaxbal] [decimal](20, 2) NULL,
	[xposition] [varchar](50) NULL,
	[xrow] [int] NULL,
	[xlong] [varchar](1000) NULL,
	[xsuperior2] [varchar](8) NULL,
	[xsuperior3] [varchar](8) NULL,
	[xsuperior4] [varchar](8) NULL,
	[xsuperior5] [varchar](8) NULL,
	[xsuperior6] [varchar](8) NULL,
	[xstatus] [varchar](50) NULL,
	[xsubprcs] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xstaff] ASC,
	[xtypetrn] ASC,
	[xshift] ASC,
	[xreqtype] ASC,
	[xprime] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[processcontroller]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[processcontroller](
	[zid] [int] NULL,
	[xstatrtime] [datetime] NULL,
	[zemail] [varchar](50) NULL,
	[xprocessname] [varchar](100) NULL,
	[xtype] [varchar](100) NULL,
	[xstatusint] [int] NULL,
	[xendtime] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rptcurrentstock]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rptcurrentstock](
	[ztime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zid] [int] NULL,
	[zorg] [varchar](150) NULL,
	[xwh] [varchar](50) NULL,
	[xbrname] [varchar](50) NULL,
	[xitem] [varchar](50) NULL,
	[xdesc] [varchar](150) NULL,
	[xqty] [decimal](20, 2) NULL,
	[xqtyonhand] [decimal](20, 2) NULL,
	[xqtysold] [decimal](20, 2) NULL,
	[xqtyavail] [decimal](20, 2) NULL,
	[xgitem] [varchar](50) NULL,
	[xbooked] [decimal](20, 2) NULL,
	[xgitemdesc] [varchar](100) NULL,
	[xmaxqty] [decimal](20, 2) NULL,
	[xminqty] [decimal](20, 2) NULL,
	[xunit] [varchar](50) NULL,
	[xitemold] [varchar](20) NULL,
	[xreordqty] [decimal](20, 2) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rptcurrentstockbatch]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rptcurrentstockbatch](
	[ztime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zid] [int] NULL,
	[zorg] [varchar](150) NULL,
	[xwh] [varchar](50) NULL,
	[xbrname] [varchar](50) NULL,
	[xitem] [varchar](50) NULL,
	[xdesc] [varchar](150) NULL,
	[xqty] [decimal](20, 2) NULL,
	[xqtyonhand] [decimal](20, 2) NULL,
	[xqtysold] [decimal](20, 2) NULL,
	[xqtyavail] [decimal](20, 2) NULL,
	[xgitem] [varchar](50) NULL,
	[xbooked] [decimal](20, 2) NULL,
	[xgitemdesc] [varchar](100) NULL,
	[xmaxqty] [decimal](20, 2) NULL,
	[xminqty] [decimal](20, 2) NULL,
	[xunit] [varchar](50) NULL,
	[xitemold] [varchar](20) NULL,
	[xbatch] [varchar](20) NULL,
	[xdateexp] [datetime] NULL,
	[xbookedqty] [decimal](20, 2) NULL,
	[xreordqty] [decimal](20, 2) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Temp_CurrentStock]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Temp_CurrentStock](
	[zid] [varchar](50) NULL,
	[xitem] [varchar](50) NULL,
	[xcost] [decimal](20, 2) NULL,
	[xqty] [int] NULL,
	[zauserid] [varchar](100) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Temp_CurrentStock1]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Temp_CurrentStock1](
	[zid] [varchar](50) NULL,
	[xitem] [varchar](50) NULL,
	[xcost] [decimal](20, 2) NULL,
	[xqty] [decimal](20, 2) NULL,
	[zauserid] [varchar](100) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usergroup]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usergroup](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xposition] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xempcategory] [varchar](50) NULL,
	[xgroup] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userpasspolicy]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userpasspolicy](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xppapp] [varchar](50) NULL,
	[xminchr] [int] NULL,
	[xcapitalchr] [varchar](50) NULL,
	[xsmallchr] [varchar](50) NULL,
	[xspecialchr] [varchar](50) NULL,
	[xexpday] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userprodnature]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userprodnature](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xprodnature] [varchar](50) NOT NULL,
	[xposition] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xprodnature] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userrepaccess]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userrepaccess](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xcode] [varchar](100) NOT NULL,
	[xlong] [varchar](1000) NULL,
	[xtype] [varchar](50) NOT NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xcode] ASC,
	[xtype] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userunits]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userunits](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xposition] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xempunit] [varchar](50) NULL,
	[xempcategory] [varchar](50) NULL,
	[xgroup] [varchar](50) NULL,
	[xlong] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xlocals]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xlocals](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtypelocal] [varchar](50) NOT NULL,
	[xname] [varchar](450) NOT NULL,
	[xcaption] [varchar](500) NULL,
	[xlong] [varchar](1000) NULL,
	[xtrns] [varchar](500) NULL,
	[xcodes] [varchar](500) NULL,
	[xprops] [varchar](250) NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtypelocal] ASC,
	[xname] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xmedidose]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xmedidose](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtype] [varchar](50) NOT NULL,
	[xcode] [varchar](100) NOT NULL,
	[xlong] [varchar](1000) NULL,
	[zactive] [varchar](1) NULL,
	[xdose] [varchar](50) NULL,
	[xfrequency] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtype] ASC,
	[xcode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xpermits]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xpermits](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[xmenu] [varchar](50) NOT NULL,
	[xoption] [varchar](50) NOT NULL,
	[xalist] [varchar](100) NULL,
	[zactive] [varchar](1) NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
	[zuip] [varchar](50) NULL,
	[xchild] [varchar](50) NULL,
	[xlocation] [varchar](150) NULL,
	[xnote] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xmenu] ASC,
	[xoption] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xprivilege]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xprivilege](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zemail] [varchar](50) NOT NULL,
	[zscreen] [varchar](50) NOT NULL,
	[xfields] [varchar](50) NOT NULL,
	[xoption] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[zscreen] ASC,
	[xfields] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xrolepermits]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xrolepermits](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xrole] [varchar](50) NOT NULL,
	[xmenu] [varchar](50) NOT NULL,
	[xoption] [varchar](50) NOT NULL,
	[xparentmenu] [varchar](250) NOT NULL,
	[xparentoption] [varchar](250) NOT NULL,
	[zscreen] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xrole] ASC,
	[xmenu] ASC,
	[xoption] ASC,
	[xparentmenu] ASC,
	[xparentoption] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xroleprivilege]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xroleprivilege](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[xrole] [varchar](50) NOT NULL,
	[zscreen] [varchar](50) NOT NULL,
	[xfields] [varchar](50) NOT NULL,
	[xoption] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xrole] ASC,
	[zscreen] ASC,
	[xfields] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xroles]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xroles](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xrole] [varchar](50) NOT NULL,
	[xdesc] [varchar](1000) NULL,
	[xaccess] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xotp] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xrole] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xtrn]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xtrn](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xtrn] [char](4) NOT NULL,
	[xaction] [varchar](50) NULL,
	[xdesc] [varchar](1000) NULL,
	[xnum] [int] NULL,
	[xinc] [int] NULL,
	[xwh] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xacc] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtypetrn] ASC,
	[xtrn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xtrnp]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xtrnp](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtypetrn] [varchar](50) NOT NULL,
	[xtrn] [char](4) NOT NULL,
	[xtyperel] [varchar](50) NOT NULL,
	[xrel] [char](4) NULL,
	[xrelbn] [char](4) NULL,
	[xreltn] [char](4) NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtypetrn] ASC,
	[xtrn] ASC,
	[xtyperel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xuserpermits]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xuserpermits](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[zemail] [varchar](50) NOT NULL,
	[xmenu] [varchar](50) NOT NULL,
	[xoption] [varchar](50) NOT NULL,
	[xparentmenu] [varchar](250) NOT NULL,
	[xparentoption] [varchar](250) NOT NULL,
	[zscreen] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zemail] ASC,
	[xmenu] ASC,
	[xoption] ASC,
	[xparentmenu] ASC,
	[xparentoption] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xusersadmin]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xusersadmin](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xrow] [int] NOT NULL,
	[xstaff] [varchar](50) NOT NULL,
	[xdate] [datetime] NULL,
	[xterritory] [varchar](50) NULL,
	[xzone] [varchar](50) NULL,
	[xdivision] [varchar](50) NULL,
	[xbusinesscheck] [varchar](50) NULL,
	[xactivetype] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[xyesno] [varchar](50) NULL,
	[xyesno1] [varchar](50) NULL,
	[xyesno2] [varchar](50) NULL,
	[xstatus] [varchar](50) NULL,
	[xyesno3] [varchar](50) NULL,
	[xyesno4] [varchar](50) NULL,
	[xyesno5] [varchar](50) NULL,
	[xyesno6] [varchar](50) NULL,
	[xyesno7] [varchar](50) NULL,
	[xtanks] [varchar](50) NULL,
	[xpipes] [varchar](50) NULL,
	[xdoors] [varchar](50) NULL,
	[xsinks] [varchar](50) NULL,
	[xuseractive] [varchar](50) NULL,
	[xappactive] [varchar](50) NULL,
	[xopactive] [varchar](50) NULL,
	[xnum] [int] NULL,
 CONSTRAINT [PK__xusersad__FF1BD74C3D787F92] PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xrow] ASC,
	[xstaff] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zaudittrail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zaudittrail](
	[ztime] [datetime] NULL,
	[zid] [int] NULL,
	[zauditid] [int] IDENTITY(10,1) NOT NULL,
	[zemail] [varchar](50) NULL,
	[zscreen] [varchar](50) NULL,
	[zstatement] [varchar](2000) NULL,
	[zpkey] [varchar](50) NULL,
	[zmessage] [varchar](2000) NULL,
	[zcommand] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
	[zcapstatement] [varchar](2000) NULL,
	[zfstatement] [varchar](2000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zauditid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zaudittraildel]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zaudittraildel](
	[ztime] [datetime] NULL,
	[zid] [int] NULL,
	[zauditid] [int] IDENTITY(10,1) NOT NULL,
	[zemail] [varchar](50) NULL,
	[zscreen] [varchar](50) NULL,
	[zstatement] [varchar](2000) NULL,
	[zpkey] [varchar](50) NULL,
	[zmessage] [varchar](2000) NULL,
	[zcommand] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zauditid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zhashlog]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zhashlog](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtornum] [varchar](50) NOT NULL,
	[xdate] [datetime] NULL,
	[xidsup] [varchar](8) NOT NULL,
	[xencode] [varchar](5000) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtornum] ASC,
	[xidsup] ASC,
	[xencode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zlogintrail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zlogintrail](
	[zid] [int] NULL,
	[zsessionid] [varchar](150) NOT NULL,
	[zremip] [varchar](50) NULL,
	[zserip] [varchar](50) NULL,
	[zemail] [varchar](50) NULL,
	[zlogintime] [datetime] NULL,
	[zlogouttime] [datetime] NULL,
	[zuseragent] [varchar](2000) NULL,
	[xtype] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zsessionid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zlogs]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zlogs](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NULL,
	[zerrorid] [int] IDENTITY(1,1) NOT NULL,
	[zscreen] [varchar](50) NULL,
	[zemail] [varchar](50) NULL,
	[zerrornum] [varchar](50) NULL,
	[zstatement] [varchar](2000) NULL,
	[zmessage] [varchar](2000) NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
	[zuip] [varchar](50) NULL,
 CONSTRAINT [PK__zlogs__6166761E] PRIMARY KEY CLUSTERED 
(
	[zerrorid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zreject]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zreject](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xtrnnum] [varchar](50) NOT NULL,
	[xrow] [int] NOT NULL,
	[xdate] [date] NULL,
	[xdatereject] [datetime] NULL,
	[xcus] [varchar](250) NULL,
	[xnote] [varchar](5000) NULL,
	[xtrn] [char](4) NULL,
	[xpreparer] [varchar](50) NULL,
	[xidsup] [varchar](8) NULL,
	[xsignatory1] [varchar](50) NULL,
	[xsigndate1] [datetime] NULL,
	[xstatus] [varchar](50) NULL,
	[xfwh] [varchar](50) NULL,
	[xtwh] [varchar](50) NULL,
	[xsignatory2] [varchar](50) NULL,
	[xsigndate2] [datetime] NULL,
	[xsignatory3] [varchar](50) NULL,
	[xsigndate3] [datetime] NULL,
	[xsignatory4] [varchar](50) NULL,
	[xsigndate4] [datetime] NULL,
	[xsignatory5] [varchar](50) NULL,
	[xsigndate5] [datetime] NULL,
	[xstatustor] [varchar](50) NULL,
	[xstatusrec] [varchar](50) NULL,
	[xstatusreq] [varchar](50) NULL,
	[xregi] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xtrnnum] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zscreendetail]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zscreendetail](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zemail] [varchar](50) NULL,
	[zscreen] [varchar](50) NOT NULL,
	[xfields] [varchar](50) NULL,
	[xcaption] [varchar](500) NULL,
	[xoption] [varchar](50) NULL,
	[xrow] [int] NOT NULL,
	[xtype] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zscreen] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zscreenheader]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zscreenheader](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zid] [int] NOT NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zemail] [varchar](50) NULL,
	[zscreen] [varchar](50) NOT NULL,
	[xdesc] [varchar](1000) NULL,
	[xfields] [varchar](50) NULL,
	[xoption] [varchar](50) NULL,
	[xtype] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[zscreen] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zstakeholder]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zstakeholder](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xrow] [int] NOT NULL,
	[xstaff] [varchar](50) NULL,
	[xname] [varchar](450) NULL,
	[xtitle] [varchar](1000) NULL,
	[xproject] [varchar](50) NULL,
	[xdeptname] [varchar](50) NULL,
	[xdesignation] [varchar](50) NULL,
	[xposition] [varchar](50) NULL,
	[xmobile] [varchar](40) NULL,
	[xemail] [varchar](250) NULL,
	[xrem] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xrow] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zstatus]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zstatus](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zauserid] [varchar](50) NULL,
	[zuuserid] [varchar](50) NULL,
	[zid] [int] NOT NULL,
	[xnum] [int] NOT NULL,
	[xlong] [varchar](200) NOT NULL,
	[zactive] [varchar](1) NULL,
 CONSTRAINT [PK_zstatus] PRIMARY KEY CLUSTERED 
(
	[zid] ASC,
	[xnum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[zusers]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[zusers](
	[ztime] [datetime] NULL,
	[zutime] [datetime] NULL,
	[zemail] [varchar](50) NOT NULL,
	[xpassword] [varchar](50) NULL,
	[xsalute] [varchar](50) NULL,
	[xfirst] [varchar](50) NULL,
	[xmiddle] [varchar](50) NULL,
	[xlast] [varchar](50) NULL,
	[xtitle] [varchar](50) NULL,
	[xorg] [varchar](50) NULL,
	[xadd1] [varchar](50) NULL,
	[xadd2] [varchar](50) NULL,
	[xcity] [varchar](50) NULL,
	[xstate] [varchar](50) NULL,
	[xzip] [varchar](50) NULL,
	[xcountry] [varchar](50) NULL,
	[xphone] [varchar](40) NULL,
	[xfax] [varchar](50) NULL,
	[xurl] [varchar](50) NULL,
	[zactive] [varchar](1) NULL,
	[zref] [varchar](50) NULL,
	[xemailc] [varchar](50) NULL,
	[zaip] [varchar](50) NULL,
	[zuip] [varchar](50) NULL,
	[rowguid] [uniqueidentifier] ROWGUIDCOL  NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[zemail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[zusers] ADD  DEFAULT (newid()) FOR [rowguid]
GO
ALTER TABLE [dbo].[apvprcs]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[cacus]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[caitem]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[caitembonus]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[caitembonus]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[caitemwh]  WITH CHECK ADD  CONSTRAINT [FK__caitemwh__6E8B6712] FOREIGN KEY([zid], [xtype], [xcode])
REFERENCES [dbo].[xcodes] ([zid], [xtype], [xcode])
GO
ALTER TABLE [dbo].[caitemwh] CHECK CONSTRAINT [FK__caitemwh__6E8B6712]
GO
ALTER TABLE [dbo].[caitemwh]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[caitemwh]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[casup]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[genericmaster]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imstoretag]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imtordetail]  WITH CHECK ADD FOREIGN KEY([zid], [xtornum])
REFERENCES [dbo].[imtorheader] ([zid], [xtornum])
GO
ALTER TABLE [dbo].[imtordetail]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[imtordetail]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imtordetailbatch]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imtordetailbatch]  WITH CHECK ADD FOREIGN KEY([zid], [xtornum], [xrow])
REFERENCES [dbo].[imtordetail] ([zid], [xtornum], [xrow])
GO
ALTER TABLE [dbo].[imtordetailbatch]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[imtorheader]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imtrn]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[imtrn]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[imtrndetail]  WITH CHECK ADD FOREIGN KEY([zid], [ximtrnnum])
REFERENCES [dbo].[imtrn] ([zid], [ximtrnnum])
GO
ALTER TABLE [dbo].[imtrndetail]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[imtrndetail]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmappointment]  WITH CHECK ADD  CONSTRAINT [FK__mmappointme__zid__65370702] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmappointment] CHECK CONSTRAINT [FK__mmappointme__zid__65370702]
GO
ALTER TABLE [dbo].[mmdocdrugprofile]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmdocdrugprofile]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[mmdoclabprofile]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmdoclabprofile]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[mmlaborder]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmmedicine]  WITH CHECK ADD FOREIGN KEY([zid], [xcase], [xrow])
REFERENCES [dbo].[mmprescription] ([zid], [xcase], [xrow])
GO
ALTER TABLE [dbo].[mmmedicine]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmonexam]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmpreschistory]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[mmpreschistory]  WITH CHECK ADD  CONSTRAINT [FK__mmpreschistory__69FBBC1F] FOREIGN KEY([zid], [xcase])
REFERENCES [dbo].[mmappointment] ([zid], [xcase])
GO
ALTER TABLE [dbo].[mmpreschistory] CHECK CONSTRAINT [FK__mmpreschistory__69FBBC1F]
GO
ALTER TABLE [dbo].[mmprescription]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[opdef]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdappsuperior]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdappsuperior]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pddef]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pddependent]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pddependent]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdempat]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pdempat]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdemptransfer]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdemptransfer]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pdexperience]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdexperience]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pdmst]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdmstdetail]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pdmstdetail]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdsignatoryrpt]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pdsuperior]  WITH CHECK ADD FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[pdsuperior]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pogrndetail]  WITH CHECK ADD FOREIGN KEY([zid], [xgrnnum])
REFERENCES [dbo].[pogrnheader] ([zid], [xgrnnum])
GO
ALTER TABLE [dbo].[pogrndetail]  WITH CHECK ADD FOREIGN KEY([zid], [xitem])
REFERENCES [dbo].[caitem] ([zid], [xitem])
GO
ALTER TABLE [dbo].[pogrndetail]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pogrnheader]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pogrnitemcost]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[pogrnitemcost]  WITH CHECK ADD FOREIGN KEY([zid], [xgrnnum], [xrow])
REFERENCES [dbo].[pogrndetail] ([zid], [xgrnnum], [xrow])
GO
ALTER TABLE [dbo].[usergroup]  WITH CHECK ADD  CONSTRAINT [FK__usergroup__22751F6C] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[usergroup] CHECK CONSTRAINT [FK__usergroup__22751F6C]
GO
ALTER TABLE [dbo].[usergroup]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[userpasspolicy]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[userprodnature]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[userprodnature]  WITH CHECK ADD  CONSTRAINT [FK__userprodnature__2645B050] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[userprodnature] CHECK CONSTRAINT [FK__userprodnature__2645B050]
GO
ALTER TABLE [dbo].[userrepaccess]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[userrepaccess]  WITH CHECK ADD  CONSTRAINT [FK__userrepaccess__31B762FC] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[userrepaccess] CHECK CONSTRAINT [FK__userrepaccess__31B762FC]
GO
ALTER TABLE [dbo].[userstore]  WITH CHECK ADD  CONSTRAINT [FK__userstore__3A81B327] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[userstore] CHECK CONSTRAINT [FK__userstore__3A81B327]
GO
ALTER TABLE [dbo].[userstore]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[userunits]  WITH CHECK ADD  CONSTRAINT [FK__userunits__3587F3E0] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[userunits] CHECK CONSTRAINT [FK__userunits__3587F3E0]
GO
ALTER TABLE [dbo].[userunits]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[vitalssign]  WITH CHECK ADD  CONSTRAINT [FK__vitalssign__6DCC4D03] FOREIGN KEY([zid], [xcase])
REFERENCES [dbo].[mmappointment] ([zid], [xcase])
GO
ALTER TABLE [dbo].[vitalssign] CHECK CONSTRAINT [FK__vitalssign__6DCC4D03]
GO
ALTER TABLE [dbo].[vitalssign]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xcodes]  WITH CHECK ADD  CONSTRAINT [FK__xcodes__zid__3D5E1FD2] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xcodes] CHECK CONSTRAINT [FK__xcodes__zid__3D5E1FD2]
GO
ALTER TABLE [dbo].[xlocals]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xmedidose]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xpermits]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xprivilege]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xrolepermits]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xrolepermits]  WITH CHECK ADD FOREIGN KEY([zid], [xrole])
REFERENCES [dbo].[xroles] ([zid], [xrole])
GO
ALTER TABLE [dbo].[xroleprivilege]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xroles]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xtrn]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xtrnp]  WITH CHECK ADD FOREIGN KEY([zid], [xtypetrn], [xtrn])
REFERENCES [dbo].[xtrn] ([zid], [xtypetrn], [xtrn])
GO
ALTER TABLE [dbo].[xtrnp]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xuserpermits]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xuserpermits]  WITH CHECK ADD  CONSTRAINT [FK__xuserpermits__33D4B598] FOREIGN KEY([zid], [zemail])
REFERENCES [dbo].[xusers] ([zid], [zemail])
GO
ALTER TABLE [dbo].[xuserpermits] CHECK CONSTRAINT [FK__xuserpermits__33D4B598]
GO
ALTER TABLE [dbo].[xusers]  WITH CHECK ADD  CONSTRAINT [FK__xusers__zid__29572725] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xusers] CHECK CONSTRAINT [FK__xusers__zid__29572725]
GO
ALTER TABLE [dbo].[xusersadmin]  WITH CHECK ADD  CONSTRAINT [FK__xusersadmin__33BCDD37] FOREIGN KEY([zid], [xstaff])
REFERENCES [dbo].[pdmst] ([zid], [xstaff])
GO
ALTER TABLE [dbo].[xusersadmin] CHECK CONSTRAINT [FK__xusersadmin__33BCDD37]
GO
ALTER TABLE [dbo].[xusersadmin]  WITH CHECK ADD  CONSTRAINT [FK__xusersadmin__zid__32C8B8FE] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[xusersadmin] CHECK CONSTRAINT [FK__xusersadmin__zid__32C8B8FE]
GO
ALTER TABLE [dbo].[zhashlog]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zlogs]  WITH CHECK ADD  CONSTRAINT [FK__zlogs__zid__33572959] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zlogs] CHECK CONSTRAINT [FK__zlogs__zid__33572959]
GO
ALTER TABLE [dbo].[zreject]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zscreendetail]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zscreendetail]  WITH CHECK ADD FOREIGN KEY([zid], [zscreen])
REFERENCES [dbo].[zscreenheader] ([zid], [zscreen])
GO
ALTER TABLE [dbo].[zscreenheader]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zstakeholder]  WITH CHECK ADD FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zstatus]  WITH CHECK ADD  CONSTRAINT [FK__zstatus__zid__27A8135A] FOREIGN KEY([zid])
REFERENCES [dbo].[zbusiness] ([zid])
GO
ALTER TABLE [dbo].[zstatus] CHECK CONSTRAINT [FK__zstatus__zid__27A8135A]
GO
/****** Object:  StoredProcedure [dbo].[Func_getTrn]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO



CREATE  PROC [dbo].[Func_getTrn]
  @zid INT,
  @typetrn varchar(50),@trn varchar(50), @LenV int,
  @strVar varchar(50)  OUTPUT
AS
BEGIN
SET NOCOUNT ON
DECLARE @count int,
	@numVar varchar(50),
	@paddval char(1),
	@lastnum int,
	@inc int,
	@Len int

SET @count=1
SET @paddval = '0'
SET @lastnum = 0;

SELECT @lastnum = xnum from xtrn where zid=@zid AND xtypetrn=@typetrn and xtrn=@trn
SELECT @inc = xinc from xtrn where zid=@zid AND xtypetrn=@typetrn and xtrn=@trn

SET @numVar = @lastnum+@inc

/*updating xtrn*/
UPDATE xtrn set xnum=@numVar where zid=@zid AND xtypetrn=@typetrn and xtrn=@trn
--UPDATE [10.0.0.21].zabdb.dbo.xtrn set xnum=@numVar where xtypetrn=@typetrn and xtrn=@trn

SET @Len=@LenV - LEN(@numVar)
WHILE @count<=@Len
  BEGIN 
    SET @numVar = @paddval + @numVar
    SET @count=@count + 1
  END 
SET @strVar=@trn+@numVar



RETURN
END




GO
/****** Object:  StoredProcedure [dbo].[zabsp_Admin_confirmSR]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--exec sp_Admin_confirmSR 100000,'Tuhin','SR--000856','2018-03-18','08','08','Checked',8
CREATE PROC [dbo].[zabsp_Admin_confirmSR] --#id,#user,xtornum,xdate,xfwh,xtwh,8
	
@zid INT
,@user VARCHAR(50)
,@tornum VARCHAR(50)
,@datecom DATE
,@fwh VARCHAR(50)
,@twh VARCHAR(50)
,@status VARCHAR(50)
,@trnlength INT
AS
BEGIN
SET NOCOUNT ON
DECLARE @trn VARCHAR(50),
@row INT,
@docrow INT,
@costrow INT,
@trnnum VARCHAR(50),
@item VARCHAR(50),
@moprcs VARCHAR(50),
@unit VARCHAR(50),
@regi VARCHAR(50),
@doctype VARCHAR(50),
@qtyord DECIMAL(20,5),
@qtystk DECIMAL(20,5),
@balqty DECIMAL(20,5),
@balance DECIMAL(20,5),
@cost DECIMAL(20,5),
@totqty DECIMAL(20,5),
@totamt DECIMAL(20,5),
@vatrate DECIMAL(20,5),
@vatamt DECIMAL(20,5),
@qtyreq DECIMAL(20,5),
@lineamt DECIMAL(20,5),
@qtyalc DECIMAL(20,5),
@totreq DECIMAL(20,5),
@totalc DECIMAL(20,5),
@isqty DECIMAL(20,5),
@previs DECIMAL(20,5),
@previsarc DECIMAL(20,5),
@qtycom DECIMAL(20,5),
@statustor VARCHAR(50),
@voucherno VARCHAR(50),
@project VARCHAR(50),
@assetcode VARCHAR(50),
@binref VARCHAR(50),
@typeitem VARCHAR(50),
@reustore VARCHAR(50),
@cwipval DECIMAL(20,5),
@dateiss DATE,
@type  VARCHAR(50),
@srstatus VARCHAR(50),
@totqtyord DECIMAL(20,5)
,@qty  DECIMAL(20,5)
,@totqtyin DECIMAL(20,3)
,@costrate DECIMAL(20,5)
,@batch VARCHAR(50)
,@dateexp datetime,
@batchtag VARCHAR(50),
@orderrow INT,
@qtyb DECIMAL(20,2)


-- **************INITIALIZATION****************
SET @dateiss=@datecom--GETDATE()
SET @totamt = 0
SET @vatrate = 0
SET @vatamt = 0
SET @lineamt = 0
SET @qtyreq = 0
SET @cwipval=0


--IF NOT EXISTS(SELECT zemail FROM processcontroller WITH (NOLOCK) WHERE zid=@zid and xprocessname='zabsp_Admin_confirmSR' and xtype=@tornum and isnull(xstatusint,0)=0)
--BEGIN
--INSERT INTO processcontroller([zid],[xstatrtime],[zemail],[xprocessname],[xtype],[xstatusint])
--SELECT @zid,GETDATE(),@user,'zabsp_Admin_confirmSR',@tornum,0

SELECT @statustor = xstatustor
FROM imtorheader WITH (NOLOCK)
WHERE zid=@zid
AND xtornum=@tornum

SELECT @regi=xregi,@moprcs=ISNULL(xmoprcs,''),@project=isnull(xproject,''),@assetcode=isnull(xassetcode,''),@type=isnull(xtypesr,''),@srstatus=isnull(xstatussr,'') 
FROM imtorheader WITH (NOLOCK) 
WHERE zid=@zid AND xtornum=@tornum

SET @assetcode=isnull(@assetcode,'')
-- zabsp_Admin_confirmSR'400010','12688','SR--052733','','CS001','SS027','Receive','6'
--IF @statustor = 'Transfered'
IF ((@statustor = 'Checked' OR @statustor = 'Confirmed' OR @statustor = 'Partial Issue') and @type='Transfer' and @srstatus in ('Open','Transferred','Received','Rejected') )
BEGIN

	DECLARE result_cursor CURSOR FORWARD_ONLY FOR
		SELECT a.xrow,a.xitem,a.xunit,ISNULL(a.xqtyord,0),ISNULL(a.xqtyreq,0),ISNULL(a.xqtyalc,0),ISNULL(a.xqtycom,0),isnull(a.xqty,0),a.xlineamt,isnull(a.xrate,0),a.xvatrate,a.xbinref,b.xbatmg
		FROM imtordetail a WITH (NOLOCK) join caitem b on a.zid=b.zid and a.xitem=b.xitem
		WHERE a.zid=@zid AND a.xtornum=@tornum --and xstype='Stock available'
		OPEN result_cursor
		FETCH FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@qty,@lineamt,@cost,@vatrate,@binref,@batchtag
		WHILE @@FETCH_STATUS = 0
		BEGIN
		SET @isqty=@qtyord
	
		/**************** TRANSACTION FOR RT ****************/
		IF ((@qtyreq-@qtyalc)>=@qtyord AND @qtyord <> 0)
		BEGIN
			IF @status='Checked'
				BEGIN
						IF not exists(select ximtrnnum from imtrn WITH (NOLOCK) where zid=@zid and xdocnum=@tornum and xdocrow=@row and xsign=-1 and xwh=@fwh and xitem=@item and ztime between getdate() and dateadd(MINUTE,-10,getdate()))
							BEGIN
								   Select @totqtyin =isnull(sum(xqty*xsign),0) from imtrnrptview where zid = @zid AND xitem = @item AND left(ximtrnnum,2) not in ('IT','RT','DO') and left(xdocnum,5)<>'SLR-2' AND xdate<=@datecom
									IF @totqtyin > 0
									BEGIN
										Select @costrate = sum(xval*xsign)/sum(xqty*xsign) from imtrnrptview where zid = @zid AND xitem = @item AND left(ximtrnnum,2) not in ('IT','RT','DO') and left(xdocnum,5)<>'SLR-2' AND xdate<=@datecom
										SET @costrate=isnull(@costrate,0)
									END
									IF @costrate < 0 
										SET @costrate = 0	

									IF isnull(@batchtag,'')='Yes'
									BEGIN
										DECLARE result_cursor_b CURSOR FORWARD_ONLY FOR
										SELECT xorderrow,xbatch,xdateexp,xqtyord 
										FROM imtordetailbatch 
										WHERE zid=@zid and xtornum=@tornum and xrow=@row and isnull(xstatusint,0)=0
										OPEN result_cursor_b
										FETCH FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qtyb
										WHILE @@FETCH_STATUS = 0
										BEGIN
										SET @trn = 'IT--'
										EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
										INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
										xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xassetcode,xbinref,xbatch,xdateexp)VALUES
										(GETDATE(),@user,@zid,@trnnum,@item,@fwh,@dateiss,@qtyb,(@qtyb*ISNULL(@costrate,0)),0,@tornum,@row,'Issue to SR',
										-1,@unit,ISNULL(@costrate,0),'','Open',@costrow,@vatrate,(ISNULL(@costrate,0)*@vatrate),@doctype,@qtystk,@regi,@project,@assetcode,@binref,@batch,@dateexp)

										Update imtordetailbatch set xstatusint=1,xstatusrec=0 where zid=@zid and xtornum=@tornum and xrow=@row and xorderrow=@orderrow
							
										FETCH NEXT FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qtyb
										END
										CLOSE result_cursor_b
										DEALLOCATE result_cursor_b
									END
									ELSE
										BEGIN
										SET @trn = 'IT--'
										EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
										INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
										xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xbinref,xbatch,xdateexp)VALUES
										(GETDATE(),@user,@zid,@trnnum,@item,@fwh,@dateiss,@isqty,(@isqty*@costrate),0,@tornum,@row,'Issue to SR',
										-1,@unit,@costrate,'','Open',@costrow,@vatrate,(@isqty*@vatrate),@doctype,@qtystk,@regi,@project,@binref,@batch,@dateexp)
										END

										SELECT @previs=SUM(xqty) FROM imtrn WITH (NOLOCK) WHERE zid=@zid AND xdocnum=@tornum AND xdocrow=@row and xsign<0
										--SELECT @previsarc=SUM(xqty) FROM imtrnarchive WITH (NOLOCK) WHERE zid=@zid AND xdocnum=@tornum AND xdocrow=@row and xsign<0
										Update imtordetail set xqtyalc=isnull(@previs,0),xqtyord=0,xqty=@isqty,xrate=@costrate  WHERE zid=@zid AND xtornum=@tornum AND xrow=@row
										update imtorheader set xdatecom=GETDATE()  WHERE zid=@zid AND xtornum=@tornum 

										SET @costrate = 0

							END
				END

		END


			IF (@status='Receive'  and @qty>0)
			BEGIN
				IF not exists(select ximtrnnum from imtrn WITH (NOLOCK) where zid=@zid and xdocnum=@tornum and xdocrow=@row and xsign=1 and xwh=@twh and xitem=@item and ztime between getdate() and dateadd(MINUTE,-10,getdate()))
				BEGIN
			
					    IF isnull(@batchtag,'')='Yes'
						BEGIN
							DECLARE result_cursor_b CURSOR FORWARD_ONLY FOR
							SELECT xorderrow,xbatch,xdateexp,xqtyord 
							FROM imtordetailbatch WITH (NOLOCK)
							WHERE zid=@zid and xtornum=@tornum and xrow=@row and xstatusint=1 and isnull(xstatusrec,0)=0
							OPEN result_cursor_b
							FETCH FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qtyb
							WHILE @@FETCH_STATUS = 0
							BEGIN
							SET @trn = 'RT--'
							EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
							INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
							xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xbinref,xbatch,xdateexp)VALUES
							(GETDATE(),@user,@zid,@trnnum,@item,@twh,@dateiss,@qtyb,(@qtyb*@cost),0,@tornum,@row,'Received By SR',
							1,@unit,@cost,'','Open',@costrow,@vatrate,(@qtyb*@vatrate),@doctype,@qtystk,@regi,@project,@binref,@batch,@dateexp)

							Update imtordetailbatch set xstatusint=1,xstatusrec=1 where zid=@zid and xtornum=@tornum and xrow=@row and xorderrow=@orderrow
							
							FETCH NEXT FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qtyb
							END
							CLOSE result_cursor_b
							DEALLOCATE result_cursor_b
						END
						ELSE
						BEGIN
							SET @trn = 'RT--'
							EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
							INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
							xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xbinref)VALUES
							(GETDATE(),@user,@zid,@trnnum,@item,@twh,@dateiss,@qty,(@qty*@cost),0,@tornum,@row,'Received By SR',
							1,@unit,@cost,'','Open',@costrow,@vatrate,(@qty*@vatrate),@doctype,@qtystk,@regi,@project,@binref)
						END
				  
						Update imtordetail set xqty=0,xqtycom=0  WHERE zid=@zid AND xtornum=@tornum AND xrow=@row
						--UPDATE imtorheader SET xstatussr='Received' WHERE zid=@zid AND xtornum=@tornum
				
				END
			END

	FETCH NEXT FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@qty,@lineamt,@cost,@vatrate,@binref,@batchtag
	END
	CLOSE result_cursor
	DEALLOCATE result_cursor
	
	IF @status='Receive'
	BEGIN
	UPDATE imtorheader SET xstatussr='Received' WHERE zid=@zid AND xtornum=@tornum
	END
		
	IF @statustor = 'Checked'
	BEGIN
	SELECT @totqtyord=isnull(sum(xqty),0) from imtordetail WITH (NOLOCK) WHERE zid=@zid AND xtornum=@tornum
	IF @totqtyord>0
	BEGIN
	UPDATE imtorheader SET xstatussr='Transferred' WHERE zid=@zid AND xtornum=@tornum
	END

	SELECT @totreq=SUM(ISNULL(xqtyreq,0)),@totalc=SUM(ISNULL(xqtyalc,0)) from imtordetail WITH (NOLOCK) WHERE zid=@zid AND xtornum=@tornum
	IF (@totalc>=@totreq and @totalc>0)
	BEGIN
	UPDATE imtorheader SET xstatustor='Confirmed',zutime=GETDATE(),zuuserid=@user,xdatecom=@datecom WHERE zid=@zid AND xtornum=@tornum
	END
	ELSE IF (@totalc<@totreq and @totalc>0)
	BEGIN
	UPDATE imtorheader SET xstatustor='Partial Issue',zutime=GETDATE(),zuuserid=@user,xdatecom=@datecom WHERE zid=@zid AND xtornum=@tornum
	END
	END


	/************** UPDATING TRANSFER HEADER ****************/
			

END

ELSE IF ((@statustor = 'Checked' OR @statustor = 'Confirmed' OR @statustor = 'Partial Issue') and @type='Transfer' and @srstatus in ('Rejected') )
BEGIN

	DECLARE result_cursor CURSOR FORWARD_ONLY FOR
	SELECT xrow,xitem,xunit,ISNULL(xqtyord,0),ISNULL(xqtyreq,0),ISNULL(xqtyalc,0),ISNULL(xqtycom,0),isnull(xqty,0),xlineamt,isnull(xrate,0),xvatrate,xbinref,xbatch,xdateexp
	FROM imtordetail WITH (NOLOCK) WHERE zid=@zid AND xtornum=@tornum --and xstype='Stock available'
	OPEN result_cursor
	FETCH FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@qty,@lineamt,@cost,@vatrate,@binref,@batch,@dateexp

	WHILE @@FETCH_STATUS = 0
	BEGIN
	SET @isqty=@qtyord
	
	/**************** TRANSACTION FOR RT ****************/
		
	IF (@status='Reject_Receive'  and @qty>0)
		BEGIN
			SET @trn = 'RT--'
			EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
			INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
			xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xbinref,xbatch,xdateexp)VALUES
			(GETDATE(),@user,@zid,@trnnum,@item,@twh,@dateiss,@qty,(@qty*@cost),0,@tornum,@row,'Received Rejected By User',
			1,@unit,@cost,'','Open',@costrow,@vatrate,(@qty*@vatrate),@doctype,@qtystk,@regi,@project,@binref,@batch,@dateexp)
				  
			Update imtordetail set xqtyalc=0,xqtycom=0  WHERE zid=@zid AND xtornum=@tornum AND xrow=@row
			--UPDATE imtorheader SET xstatussr='Received' WHERE zid=@zid AND xtornum=@tornum
				
			----------------------------Update imtordetail----------------------------------
		END
	FETCH NEXT FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@qty,@lineamt,@cost,@vatrate,@binref,@batch,@dateexp
	END
	CLOSE result_cursor
	DEALLOCATE result_cursor
	
	IF @status='Reject_Receive'
	BEGIN
	UPDATE imtorheader SET xstatussr='Received' WHERE zid=@zid AND xtornum=@tornum
	END
			

/************** UPDATING TRANSFER HEADER ****************/
			

END

ELSE
BEGIN
	print ''
END

		


END
GO
/****** Object:  StoredProcedure [dbo].[zabsp_Admin_confirmTO]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--exec sp_Admin_confirmTO 100000,'Tuhin','SR--000856','2018-03-18','08','08','Checked',8
CREATE PROC [dbo].[zabsp_Admin_confirmTO] --#id,#user,xtornum,xdate,xfwh,xtwh,8
	
	 @zid INT
	,@user VARCHAR(50)
	,@tornum VARCHAR(50)
	,@datecom DATE
	,@fwh VARCHAR(50)
	,@twh VARCHAR(50)
	,@status VARCHAR(50)
	,@trnlength INT
AS
BEGIN
SET NOCOUNT ON
DECLARE @trn VARCHAR(50),
	@row INT,
	@docrow INT,
	@costrow INT,
	@trnnum VARCHAR(50),
	@item VARCHAR(50),
	@moprcs VARCHAR(50),
	@unit VARCHAR(50),
	@regi VARCHAR(50),
	@doctype VARCHAR(50),
	@qtyord DECIMAL(20,5),
	@qtystk DECIMAL(20,5),
	@balqty DECIMAL(20,5),
	@balance DECIMAL(20,5),
	@cost DECIMAL(20,5),
	@totqty DECIMAL(20,5),
	@totamt DECIMAL(20,5),
	@vatrate DECIMAL(20,5),
	@vatamt DECIMAL(20,5),
	@qtyreq DECIMAL(20,5),
	@lineamt DECIMAL(20,5),
	@qtyalc DECIMAL(20,5),
	@totreq DECIMAL(20,5),
	@totalc DECIMAL(20,5),
	@isqty DECIMAL(20,5),
	@previs DECIMAL(20,5),
	@previsarc DECIMAL(20,5),
	@qtycom DECIMAL(20,5),
	@statustor VARCHAR(50),
	@voucherno VARCHAR(50),
	@project VARCHAR(50),
	@assetcode VARCHAR(50),
	@binref VARCHAR(50),
	@typeitem VARCHAR(50),
	@reustore VARCHAR(50),
	@cwipval DECIMAL(20,5),
	@dateiss DATE
	,@totqtyin DECIMAL(20,3)
	,@costrate DECIMAL(20,5)
	,@batch VARCHAR(50)
	,@dateexp DATETIME,
	@batchtag VARCHAR(50),
	@orderrow INT,
	@qty DECIMAL(20,2)
		

-- **************INITIALIZATION****************
SET @dateiss=@datecom--GETDATE()
SET @totamt = 0
SET @vatrate = 0
SET @vatamt = 0
SET @lineamt = 0
SET @qtyreq = 0
SET @cwipval=0

IF NOT EXISTS(SELECT zemail FROM processcontroller WITH (NOLOCK) WHERE zid=@zid and zemail=@user and xprocessname='zabsp_Admin_confirmTO' and xtype='zabsp_Admin_confirmTO' and isnull(xstatusint,0)=0)
BEGIN
INSERT INTO processcontroller([zid],[xstatrtime],[zemail],[xprocessname],[xtype],[xstatusint])
								SELECT @zid,GETDATE(),@user,'zabsp_Admin_confirmTO','zabsp_Admin_confirmTO',0

SELECT @statustor = xstatustor
FROM imtorheader WITH (NOLOCK)
WHERE zid=@zid
AND xtornum=@tornum

SELECT @regi=xregi,@moprcs=ISNULL(xmoprcs,''),@project=isnull(xproject,''),@assetcode=isnull(xassetcode,'') from imtorheader WITH (NOLOCK) WHERE zid=@zid AND xtornum=@tornum

SET @assetcode=isnull(@assetcode,'')

--IF @statustor = 'Transfered'
IF (@statustor = 'Checked' OR @statustor = 'Transfered' OR @statustor = 'Partial Issue')
BEGIN

	DECLARE result_cursor CURSOR FORWARD_ONLY FOR
	SELECT a.xrow,a.xitem,a.xunit,ISNULL(a.xqtyord,0),ISNULL(a.xqtyreq,0),ISNULL(a.xqtyalc,0),ISNULL(a.xqtycom,0),a.xlineamt,ISNULL(a.xrate,0),a.xvatrate,a.xbinref,isnull(c.xbatmg,'')
	FROM imtordetail a WITH (NOLOCK) join caitem c on a.zid=c.zid and a.xitem=c.xitem
	WHERE a.zid=@zid AND a.xtornum=@tornum
	OPEN result_cursor

	FETCH FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@lineamt,@cost,@vatrate,@binref,@batchtag

	WHILE @@FETCH_STATUS = 0
	BEGIN
	SET @isqty=@qtyord
		IF (@qtyreq-@qtyalc)>=@qtyord AND @qtyord <> 0 AND @moprcs<>'Production'
		BEGIN

		IF @assetcode<>'' SET @trn = 'CWIP'
		ELSE			  SET @trn = 'IS--'

			IF @status<>'Received'
				BEGIN
				IF not exists(select ximtrnnum from imtrn WITH (NOLOCK) where zid=@zid and xdocnum=@tornum and xdocrow=@row and xsign=-1 and xwh=@fwh and xitem=@item and ztime between getdate() and dateadd(MINUTE,-10,getdate()))
				BEGIN
				Select @totqtyin =isnull(sum(xqty*xsign),0) from imtrnrptview where zid = @zid AND xitem = @item AND left(ximtrnnum,2) not in ('IT','RT','DO') and left(xdocnum,5)<>'SLR-2' AND xdate<=@dateiss
				IF @totqtyin > 0
				BEGIN
				Select @costrate = sum(xval*xsign)/sum(xqty*xsign) from imtrnrptview where zid = @zid AND xitem = @item AND left(ximtrnnum,2) not in ('IT','RT','DO') and left(xdocnum,5)<>'SLR-2' AND xdate<=@dateiss
				SET @costrate=isnull(@costrate,0)
				END
				IF @costrate < 0 SET @costrate = 0	



				IF isnull(@batchtag,'')='Yes'
					BEGIN
							DECLARE result_cursor_b CURSOR FORWARD_ONLY FOR
								SELECT xorderrow,xbatch,xdateexp,xqtyord 
								FROM imtordetailbatch 
								WHERE zid=@zid and xtornum=@tornum and xrow=@row and isnull(xstatusint,0)=0
							OPEN result_cursor_b
							FETCH FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qty
							WHILE @@FETCH_STATUS = 0
							BEGIN
									EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
									INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
									xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xassetcode,xbinref,xbatch,xdateexp)VALUES
									(GETDATE(),@user,@zid,@trnnum,@item,@fwh,@dateiss,@qty,(@qty*ISNULL(@costrate,0)),0,@tornum,@row,'Issue to SR',
									-1,@unit,ISNULL(@costrate,0),'','Open',@costrow,@vatrate,(ISNULL(@costrate,0)*@vatrate),@doctype,@qtystk,@regi,@project,@assetcode,@binref,@batch,@dateexp)
									Update imtordetailbatch set xstatusint=1 where zid=@zid and xtornum=@tornum and xrow=@row and xorderrow=@orderrow
							
							FETCH NEXT FROM result_cursor_b INTO @orderrow,@batch,@dateexp,@qty
							END
							CLOSE result_cursor_b
							DEALLOCATE result_cursor_b
					END
				ELSE
					BEGIN

						EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
						INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
						xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xassetcode,xbinref)VALUES
						(GETDATE(),@user,@zid,@trnnum,@item,@fwh,@dateiss,@isqty,(@isqty*ISNULL(@costrate,0)),0,@tornum,@row,'Issue to SR',
						-1,@unit,ISNULL(@costrate,0),'','Open',@costrow,@vatrate,(ISNULL(@costrate,0)*@vatrate),@doctype,@qtystk,@regi,@project,@assetcode,@binref)
					END
			Update imtordetail set xrate=ISNULL(@costrate,0),xlineamt=@isqty*@costrate WHERE zid=@zid and xtornum=@trnnum and xrow=@row
			 update imtorheader set xdatecom=GETDATE()  WHERE zid=@zid AND xtornum=@tornum 


			SET @costrate=0
			END
			END
			IF @status='Received'
				BEGIN
					IF not exists(select ximtrnnum from imtrn WITH (NOLOCK) where zid=@zid and xdocnum=@tornum and xdocrow=@row and xsign=1 and xwh=@twh and xitem=@item and ztime between getdate() and dateadd(MINUTE,-10,getdate()))
					BEGIN
					SET @trn = 'RT--'
					EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength,@strVar=@trnnum OUTPUT
					INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
					xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xregi,xproject,xbinref,xbatch,xdateexp)VALUES
					(GETDATE(),@user,@zid,@trnnum,@item,@fwh,@dateiss,@isqty,(@isqty*@cost),0,@tornum,@row,'Received By SR',
					1,@unit,@cost,'','Open',@costrow,@vatrate,(@isqty*@vatrate),@doctype,@qtystk,@regi,@project,@binref,@batch,@dateexp)
					END
				END

			--=================Item If Reusable====================================================
			SELECT @typeitem=isnull(xtypeitem,'') from caitem where zid=@zid AND xitem=@item
			IF @typeitem='Reusable'
				BEGIN 
					SELECT @reustore=isnull(xtwh,'') from imstoretag WHERE zid=@zid AND xwh=@fwh AND xtype='Reusable' 
			
					EXEC Func_getTrn @zid,'Inventory Transaction','RRE-',@trnlength,@strVar=@trnnum OUTPUT
			
					INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum, xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
					xsign,xunit,xrate,xref,xstatusjv,xcostrow,xvatrate,xvatamt,xdoctype,xqtystk,xserial,xcus,xproject)VALUES
					(GETDATE(),@user,@zid,@trnnum,@item,@reustore,@dateiss,@isqty,0,0,@tornum,@row,'Reusable Item Received from : '+@fwh,
					1,@unit,0,'','Open',@costrow,0,0,@doctype,0,'','',@project)
				END 
			--=================END Transaction====================================================

			 ----------------------------Update imtordetail----------------------------------
			SELECT @previs=SUM(xqty) FROM imtrn WHERE zid=@zid AND xdocnum=@tornum AND xdocrow=@row and xsign<0
			--SELECT @previsarc=SUM(xqty) FROM imtrnarchive WITH (NOLOCK) WHERE zid=@zid AND xdocnum=@tornum AND xdocrow=@row and xsign<0
			Update imtordetail set xqtyalc=isnull(@previs,0)+isnull(@previsarc,0),xqtyord=0,xqtycom=0  WHERE zid=@zid AND xtornum=@tornum AND xrow=@row
	END
	
	------------------------------Production-----------------------------
	
	----------------------------End of Production------------------------
	
	FETCH NEXT FROM result_cursor INTO @row,@item,@unit,@qtyord,@qtyreq,@qtyalc,@qtycom,@lineamt,@cost,@vatrate,@binref,@batchtag
	END
	CLOSE result_cursor
	DEALLOCATE result_cursor

--============================Fixed Asset CWIP Register================================================
			
--============================ END Fixed Asset CWIP Register================================================		

	/************** UPDATING TRANSFER HEADER ****************/
			SELECT @totreq=SUM(ISNULL(xqtyreq,0)),@totalc=SUM(ISNULL(xqtyalc,0)) from imtordetail WHERE zid=@zid AND xtornum=@tornum
			IF @totalc>=@totreq
			BEGIN
			UPDATE imtorheader SET xstatustor='Confirmed',zutime=GETDATE(),zuuserid=@user,xdatecom=@datecom WHERE zid=@zid AND xtornum=@tornum
			END
			ELSE IF @totalc<@totreq
			BEGIN
			UPDATE imtorheader SET xstatustor='Partial Issue',zutime=GETDATE(),zuuserid=@user,xdatecom=@datecom WHERE zid=@zid AND xtornum=@tornum
			END

END
UPDATE processcontroller
		SET xstatusint=1,xendtime=GETDATE()
		WHERE zid=@zid
		AND zemail=@user
		AND xprocessname='zabsp_Admin_confirmTO'
		AND xtype='zabsp_Admin_confirmTO'
		AND xstatusint=0
END
ELSE
	RETURN
END
GO
/****** Object:  StoredProcedure [dbo].[zabsp_Dismissed_Request]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--exec zabsp_Dismissed_Request '100050','Tuhin','10474','100000','LRE-000026','Loan Request Denied'

CREATE PROC [dbo].[zabsp_Dismissed_Request] --#id,#user,xtornum,xmoprcs,xbatch
	
	@zid int,
	@user varchar(50),
	@position varchar(50),
	@zbid VARCHAR(50),
	@reqnum varchar(50),
	@request VARCHAR(50)

AS

DECLARE	@xstatus varchar(50),
		@shift varchar(50),
		@trn varchar(50),
		@preparer varchar(50),
		@superior varchar(50),
		@superior2 varchar(50),
		@superior3 varchar(50),
		@delegate1 varchar(50),
		@delegate2 varchar(50),
		@delegate3 varchar(50),
		@voucher VARCHAR(50),
		@type varchar(50),
		@fwh varchar(50),
		@twh varchar(50),
		@date DATETIME,
		@poreqnum varchar(50),
		@pornum varchar(50),
		@pafnum varchar(50),
		@tonum varchar(50),
		@staff varchar(50),
		@totreqqty DECIMAL(20,2),
		@totprqty DECIMAL(20,2),
		@tomail VARCHAR(150),
		@mail1 varchar(50),
		@mail2 varchar(50),
		@mail3 varchar(50),
		@totqty DECIMAL(18,2),
		@qty DECIMAL(18,2),
		@qtypor DECIMAL(18,2),
		@qtyalc DECIMAL(18,2),
		@qtygrn DECIMAL(18,2),
		@item varchar(50)

---------init-------
SET @totreqqty=0
SET @totprqty=0
SET @tomail=''
SET @mail1=''
SET @mail2=''
SET @mail3=''
SET @delegate1=''
SET @delegate2=''
SET @delegate3=''
SET @totqty=0
SET @qty=0
SET @qtyalc=0
SET @qtypor=0
SET @qtygrn=0

---zabsp_Dismissed_Request 100030,'mamun.talukdar','01027','100000','','Loan Request Denied'
--															zbid,   @reqnum, 
------------------------Loan request----------------------------------------------
IF @request ='Loan Request Denied' AND @reqnum<>''
BEGIN
Select @totqty=isnull(sum(isnull(xqtyalc,0)),0) from imtordetail where zid=@zbid and xtornum=@reqnum
--Select isnull(sum(isnull(xqtyalc,0)),0) from imtordetail where zid=100000 and xtornum=''
SET @totqty=isnull(@totqty,0)
select @preparer=xname from pdmst WHERE zid=@zid and xposition=@position
-- select xname from pdmst WHERE zid=100030 and xposition='01027'
IF @totqty<=0
BEGIN
Update imtorheader set xstatustor='Denied',xidsup='',xsuperior2='',xsuperior3='',xnote=CASE WHEN isnull(xnote,'')='' then '' 
else (isnull(xnote,'')+'. ') END +'Request Denied by '+@preparer + ', on '+ CAST (GETDATE() AS VARCHAR(50))  where zid=@zbid and xtornum=@reqnum and left(xtornum,1)='L'

-- Update imtorheader set xstatusloan='Denied',xnote=CASE WHEN isnull(xnote,'')='' then '' else (isnull(xnote,'')+'. ') END +'Request Denied by '+'Md. Mamun Talukdar' + ', on '+ CAST(GETDATE() AS VARCHAR(50))  where zid=100000 and xtornum=''
Delete from imtorheader WHERE zid=@zid and xtornum in (select xtornum from imtorheader where xacc=@zbid and xloannum=@reqnum and left(xtornum,1)='L') 
END
END

IF @request ='Self Request Denied'  AND @reqnum<>''
BEGIN
Select @totqty=isnull(sum(isnull(xqtyalc,0)),0) from imtorheader a join imtordetail b on a.zid=b.zid AND a.xtornum=b.xtornum where a.zid=@zbid and a.xloannum=@reqnum
SET @totqty=isnull(@totqty,0)
select @preparer=xname from pdmst WHERE zid=@zid and xposition=@position
IF @totqty<=0
BEGIN
Update imtorheader set xstatustor='Denied',xidsup='',xsuperior2='',xsuperior3='', xnote=CASE WHEN isnull(xnote,'')='' then '' --xstatusloan='Denied',
else (isnull(xnote,'')+'. ') END +'Request Denied by '+@preparer + ', on '+ CAST (GETDATE() AS VARCHAR(50))  where zid=@zid and xtornum=@reqnum
Delete from imtorheader WHERE zid=@zbid and xloannum=@reqnum  and left(xtornum,1)='L'
--Delete from imtorheader WHERE zid=@zid and xtornum in (select xtornum from imtorheader where xacc=@zbid and xloannum=@reqnum and left(xtornum,1)='L') and left(xtornum,1)='L'
END
END



ELSE Return

GO
/****** Object:  StoredProcedure [dbo].[zabsp_IM_CheckMORequisition]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- EXEC zabsp_IM_CheckMORequisition 100070,'203','SR--000605','2018-09-29','0108','Checked'
-- exec zabsp_IM_CheckMORequisition 100040,'','TO--000033','2014-03-28','01','Open' --#id,#user,xtornum,xdate,xfwh,xstatustor
CREATE PROC [dbo].[zabsp_IM_CheckMORequisition] 

	 @zid INT
	,@user VARCHAR(50)
	,@tornum VARCHAR(50)
	,@date DATE 
	,@wh VARCHAR(50)
	,@statustor VARCHAR(50)
	
AS
SET NOCOUNT ON

DECLARE  @item VARCHAR(50)
		,@fwh VARCHAR(50)
		,@trn VARCHAR(50)
		,@unit VARCHAR(50)
		,@qtyalc DECIMAL(20,3)
		,@stockdt DECIMAL(20,3)
		,@avail DECIMAL(20,3)
		,@qtyord DECIMAL(20,3)
		,@qtybal DECIMAL(20,3)
		,@qtyreq DECIMAL(20,3)
		,@qtycom DECIMAL(20,3)
		,@qtyavail DECIMAL(20,3)
		,@row INT
		,@statusmor VARCHAR(50)
		,@stype VARCHAR(50)
		,@binref VARCHAR(50)
		,@xstatustor VARCHAR(50)
		,@batch VARCHAR(50)
		,@batchtag VARCHAR(50)
		,@dateexp datetime
		,@bookstock DECIMAL(20, 2)

SET @stockdt=0	SET @qtyalc = 0 SET @qtybal = 0
SET @avail= 0	SET @qtycom = 0	SET @qtyreq = 0 
SET  @qtyord = 0	SET @qtyavail = 0


		/************** RAW MATERIAL ISSUE FROM STORE ***********/

		SELECT @xstatustor=xstatustor FROM imtorheader WHERE zid=@zid AND xtornum=@tornum
		IF (@xstatustor='Checked' OR @xstatustor='Partial Issue')
		BEGIN
		UPDATE imtorheader
				SET xstatustor='Open'
				WHERE zid=@zid
				AND xtornum=@tornum
		Update imtordetail set xqtycom=0,xqtyord=0,xstype='' WHERE zid=@zid and xtornum=@tornum
		END

		
		SET @avail=0	
		SET @qtycom=0
		SET @qtyord=0
		SET @qtybal=0
		DECLARE result_cursor CURSOR FORWARD_ONLY 
		FOR SELECT a.xrow,a.xitem,isnull(a.xqtyreq,0),ISNULL(a.xqtyord,0),ISNULL(a.xqtyalc,0),ISNULL(a.xqtycom,0),isnull(a.xbinref,'')
		FROM imtordetail a join caitem c on a.zid=c.zid and a.xitem=c.xitem
		WHERE a.zid=@zid 
		AND a.xtornum = @tornum
	--	AND (xqtyreq-ISNULL(xqtyord,0))>0
		AND (isnull(a.xqtyreq,0)-ISNULL(a.xqtyalc,0))>0
		AND c.zactive=1


		OPEN result_cursor
		FETCH FROM result_cursor INTO @row,@item,@qtyreq,@qtyord,@qtyalc,@qtyavail,@binref
		WHILE @@FETCH_STATUS = 0
		BEGIN
		
		IF @binref <>''
		SELECT @stockdt=ISNULL(SUM(xqty*xsign),0) from imtrn WHERE zid=@zid AND xwh=@wh AND xitem=@item AND xdate<=CAST (GETDATE() as DATE) and isnull(xbinref,'')=@binref
		ELSE 
		BEGIN
		
				/*	--SELECT TOP 1 @batch=xbatch,@stockdt=SUM(xqty*xsign) from imtrn 
					--WHERE zid=@zid AND xwh=@wh AND xitem=@item AND xdate<=CAST (GETDATE() as DATE)
					------group by isnull(xbatch,''),xdateexp
					--HAVING SUM(xqty*xsign)>0
					----order by xdateexp asc
					SELECT TOP 1 @dateexp=xdateexp,@batch=isnull(xbatch,''),@stockdt=SUM(xqty*xsign) from imtrn where zid=@zid AND xwh=@wh AND xitem=@item and isnull(xbatch,'')<>'' AND xdate<=CAST (GETDATE() as DATE)
					group by xdateexp,isnull(xbatch,'')
					HAVING SUM(xqty*xsign)>0
					order by xdateexp asc

	 
				SELECT @stockdt=ISNULL(SUM(xqty*xsign),0) from imtrn WHERE zid=@zid AND xwh=@wh AND xitem=@item and ISNULL(xbatch,'')=ISNULL(@batch,'') AND xdate<=CAST (GETDATE() as DATE)

				SELECT @avail = sum(xavail)
					FROM imstockbatchviewsum 
					WHERE zid=@zid
					AND xwh=@wh
					AND xitem=@item
					AND xbatch=@batch

					SELECT @dateexp=xdateexp from imtrn where zid=@zid and xwh=@wh and xitem=@item and xbatch=@batch 
					SET @avail = ISNULL(@avail,0)
				END
			ELSE
				BEGIN
				*/
				SELECT @stockdt=ISNULL(SUM(xqty*xsign),0) from imtrn WHERE zid=@zid AND xwh=@wh AND xitem=@item AND xdate<=CAST (GETDATE() as DATE)
					SELECT @avail = xavail
					FROM imstockdetview 
					WHERE zid=@zid
					AND xwh=@wh
					AND xitem=@item
						
						IF @wh = 'MSD-01'
						BEGIN
							SELECT @bookstock = isnull(sum(isnull(xqty, 0)), 0)
							FROM ZABDB.dbo.mmprescriptionphview WITH (NOLOCK)
							WHERE zid = @zid AND xwh = 'SS032' AND xstatuspharma = 'In Process' AND xitem1 = @item
							SET @avail = ISNULL(@avail,0) - isnull(@bookstock, 0)
						END

					SET @avail = ISNULL(@avail,0)
				--END
		END
				SET @stype = ''
				--SET @qtybal = @qtyreq-@qtyord
				SET @qtybal = @qtyreq-@qtyalc
				
				--**********************************
				

			
				
				-------------------If No Stock--------------------------------			
				IF @avail <= 0
				BEGIN
					SET @qtycom = 0--+@qtyalc
					SET @stype = 'No Stock'
					Update imtordetail set xqtyord=0 WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END

				---------------------------IF No Stock in date----------------------
				ELSE IF @stockdt <= 0
				BEGIN
					SET @qtycom = 0--+@qtyalc
					SET @stype = 'No Stock'
					Update imtordetail set xqtyord=0 WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END
				
				---------------------------IF both Stock in date----------------------
				ELSE IF @qtybal <=@avail AND @qtybal<=@stockdt --AND @avail>0
				BEGIN
					SET @qtycom = @qtybal--+@qtyalc
					SET @stype = 'Stock available'
					Update imtordetail set xqtyord=@qtybal WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END
				
				---------------------------IF not both Stock in date----------------------
				ELSE IF @qtybal <= @avail AND @qtybal>@stockdt
				BEGIN
					SET @avail=@stockdt
					SET @qtycom = @avail--+@qtyalc
					SET @stype = 'Stock Partially available'
					IF @avail<0
							set @avail=0
					Update imtordetail set xqtyord=@avail WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END
				
				---------------------------IF not both Stock in date----------------------
				ELSE IF @qtybal > @avail AND @qtybal>=@stockdt
				BEGIN
					IF @avail>@stockdt
						SET @avail=@stockdt

					
					SET @stype = 'Stock Partially available'
					IF @avail<0
							set @avail=0
					
					SET @qtycom = @avail--+@qtyalc
					Update imtordetail set xqtyord=@avail WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END

				---------------------------IF not both Stock in date----------------------
				ELSE IF @qtybal > @avail AND @qtybal<@stockdt
				BEGIN
					
					SET @stype ='Stock Partially available'
					IF @avail<0
							set @avail=0
					SET @qtycom = @avail--+@qtyalc
					Update imtordetail set xqtyord=@avail WHERE zid=@zid AND xtornum=@tornum AND xitem=@item AND xrow=@row
				END
				/*
				SELECT @qtyord = SUM(xqty)
				FROM imtrn
				WHERE zid=@zid
				AND xdocnum=@tornum
				AND xdocrow=@row
				AND LEFT(ximtrnnum,4)='IT--'
				
				SET @qtyord = ISNULL(@qtyord,0)
				*/
				IF @qtycom<0
						Set @qtycom=0
			--	BEGIN
				UPDATE imtordetail
				SET xqtycom=@qtycom,xstype=@stype
				WHERE zid=@zid
				AND xtornum=@tornum
				AND xrow=@row
			--	END
				
				UPDATE imtorheader
				SET xstatustor='Checked'
				WHERE zid=@zid
				AND xtornum=@tornum
				set @batch=''
				set @dateexp=NULL

				
			select @batchtag=isnull(xbatmg,'') from caitem where zid=@zid and xitem=@item
			IF (@batchtag='Yes')
				BEGIN
					
					EXEC zabsp_PRCS_SR_BatchTag @zid,@user,@tornum,@row
				END
				
			FETCH NEXT FROM result_cursor INTO @row,@item,@qtyreq,@qtyord,@qtyalc,@qtyavail,@binref
			
			
		END
		CLOSE result_cursor
		DEALLOCATE result_cursor
	
				UPDATE imtorheader
				SET xstatustor='Checked'
				WHERE zid=@zid
				AND xtornum=@tornum	

GO
/****** Object:  StoredProcedure [dbo].[zabsp_PO_confirmGRN]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- exec zabsp_PO_confirmGRN 100000,'1940','GRN-000001','2024-11-10','MSD-01',8
CREATE PROC [dbo].[zabsp_PO_confirmGRN] --#id,#user,xgrnnum,xdate,xwh,trnlength
	
	@zid INT,
	@user VARCHAR(50),
	@grnnum VARCHAR(50),
	@date DATE,
	@wh VARCHAR(50),
	@trnlength INT

 AS

DECLARE @trn VARCHAR(50),
	@row INT,
	@docrow INT,
	@year VARCHAR(2),
	@trnnum VARCHAR(50),
	@lcno VARCHAR(50),
	@reqnum VARCHAR(50),
	@item VARCHAR(50),
	@unitpur VARCHAR(50),
	@partno VARCHAR(50),
	@ref VARCHAR(50),
	@tornum VARCHAR(50),
	@email VARCHAR(50),
	@gitem VARCHAR(50),
	@lot VARCHAR(50),
	@regi VARCHAR(50),
	@cottontype VARCHAR(50),
	@pornum VARCHAR(50),
	@voucherno VARCHAR(50),
	@type  VARCHAR(50),
	@unitsel VARCHAR(50),
	@qtyord DECIMAL(20,5),
	@exch DECIMAL(20,5),
	@qtygrn DECIMAL(20,5),
	@qtypur DECIMAL(20,5),
	@cost DECIMAL(20,5),
	@rate DECIMAL(20,5),
	@vatamt DECIMAL(20,5),
	@aitamt DECIMAL(20,5),
	@totamt DECIMAL(20,5),
	@lineamt DECIMAL(20,5),
	@totcost DECIMAL(20,5),
	@totqty DECIMAL(20,5),
	@wvgcost DECIMAL(20,5),
	@cfpur DECIMAL(20,5),
	@dateexp DATE,
	@batch VARCHAR(50),
	@balance DECIMAL(20,5),
	@base DECIMAL(20,5),
	@openingcost DECIMAL(20,5),
	@dutycost DECIMAL(20,5),
	@totpoqty DECIMAL(20,4),
	@totgrnqty DECIMAL(20,4),
	@qtyclaim DECIMAL(20,4),
	@qtygain DECIMAL(20,4),
	@poamt DECIMAL(20,4), 
	@grnamt DECIMAL(20,4), 
	@podisc DECIMAL(20,4), 
	@povat DECIMAL(20,4),
	@povatrate DECIMAL(20,4),
	@podiscrate DECIMAL(20,4),
	@podiscf DECIMAL(20,4),
	@pofreight DECIMAL(20,4), 
	@qtyrec DECIMAL(20,4), 
	@differ  DECIMAL(20,4),
	@lastGRN VARCHAR(50),
	@lastPO VARCHAR(50),
	@project VARCHAR(50),
	@binref VARCHAR(50),
	@lastrow INT,
	@qtybonusgrn DECIMAL(20,4)


-- initialization
--SELECT @sb=xoutlet from zbusiness 
SET @trn='DG--'
SET @totamt = 0		SET @totgrnqty=0	SET @totpoqty=0		SET @differ=0		SET @lastrow=0		SET @lastGRN=''
SET @lineamt = 0	SET @totcost = 0	SET @totqty = 0		SET @vatamt=0		SET @dutycost=0		SET @type=''
SET @base=0			SET @openingcost= 0	SET @lastPO = ''	SET @poamt = 0		SET @poamt = 0		SET @podisc = 0
SET @povat = 0		SET @pofreight = 0	SET @povatrate=0	SET @podiscrate=0	SET @podiscf=0		SET @qtyrec = 0
SET @qtyclaim = 0	SET @qtygain = 0


--SET @year = RIGHT(YEAR(@date),2)

/******** GETTING TRANSACTION NO **************/
IF EXISTS(Select xstatusgrn from pogrnheader WHERE zid=@zid AND xgrnnum=@grnnum and xstatusgrn<>'Confirmed')
BEGIN

SELECT @ref = xref,@type=Isnull(xtype,''),@lcno=isnull(xlcno,''),@regi=xregi,@exch=isnull(xexch,0),@project=isnull(xproject,'')
FROM pogrnheader
WHERE zid=@zid
AND xgrnnum=@grnnum

IF @exch=0 SET @exch=1

SELECT @trn = xrel 
FROM xtrnp  
WHERE xtypetrn='GRN Number' 
AND xtrn=LEFT(@grnnum,4) 
AND xtyperel='Inventory Transaction' 
AND zactive=1

DECLARE result_cursor CURSOR FORWARD_ONLY FOR

SELECT a.xrow,a.xitem,c.xunit,a.xqtygrn,isnull(a.xqtyrec,0),a.xrategrn,a.xlineamt,isnull(c.xcfpur,0),a.xbatch,
a.xdateexp,a.xlot,a.xcottontype,a.xserial,a.xdocrow,a.xpornum,isnull(a.xbinref,''),isnull(a.xqtyclaim,0),isnull(a.xqtygain,0),isnull(a.xqtybonusgrn,0),c.xgitem
FROM pogrndetail a join caitem c on a.zid=c.zid and a.xitem=c.xitem 
WHERE a.zid=@zid 
AND a.xgrnnum=@grnnum 

OPEN result_cursor
FETCH FROM result_cursor INTO @row,@item,@unitpur,@qtygrn,@qtyrec,@rate,@lineamt,@cfpur,@batch,@dateexp,@lot,@cottontype,@partno,@docrow,@pornum,@binref,@qtyclaim,@qtygain,@qtybonusgrn,@gitem
WHILE @@FETCH_STATUS = 0

BEGIN


IF @type='LC'
BEGIN
SET @lineamt=0

--Select @qtypur=isnull(xqtypur,0),@openingcost=isnull(xopencost,0)--,@dutycost=isnull(xdutycost,0) --,@base=isnull(xbaseamt,0)
--from grnitemcostview WHERE zid=@zid and xitem=@item and xlcno=@lcno and xrow=@docrow and xpornum=@pornum

Select @base=isnull(xbase,0) from pogrndetail WHERE zid=@zid and xitem=@item and xgrnnum=@grnnum and xdocrow=@docrow and xpornum=@pornum



SET @openingcost=(@openingcost/isnull(@qtypur,1))*isnull(@qtygrn,0)

Select @dutycost=isnull(sum(xprime),0) from pogrnitemcost WHERE zid=@zid AND xgrnnum=@grnnum AND xrow=@row

SET @lineamt=@openingcost+@base+@dutycost
Set @lineamt=isnull(@lineamt,0)

SET @rate=@lineamt/@qtygrn
/*Print @item
print 'Purchase Qty : '+cast(@qtypur as varchar(50))
print 'Opening Cost  : '+cast(@openingcost as varchar(50))
print 'Base Value : '+cast(@base as varchar(50))
print 'Duty Cost : '+cast(@dutycost as varchar(50))
print 'Tot Value : '+cast(@lineamt as varchar(50))
print 'Rate : '+cast(@rate as varchar(50))
Print '-----------End printint ----------'
*/
END

ELSE 
BEGIN
IF @exch=0 SET @exch=1
SET @rate=@rate*@exch
SET @lineamt=@qtygrn*@rate
END

--exec zabsp_PO_confirmGRN 100000,'','GRN-000079','2017-06-09','08',8
print 'Start'
	EXEC Func_getTrn @zid,'Inventory Transaction','DG--',@trnlength, @strVar=@trnnum OUTPUT
	SET @trnnum=@trn+RIGHT(@trnnum,@trnlength)
--	SET @trnnum = @trn+CAST(@year AS VARCHAR(2))+RIGHT(@trnnum,8)
print @trnnum
	SELECT @unitsel = xunit 
	FROM caitem 
	WHERE zid = @zid 
	AND xitem=@item
	
	IF @cfpur=0
		SET @cfpur=1
/*	SET @totqty = @qtygrn*ISNULL(@cfpur,1)
	SET @rate = ROUND((@lineamt/@totqty),2)	
	SET @totamt=@lineamt
*/
print @qtyrec
	SET @totqty = @qtyrec*ISNULL(@cfpur,1)
	print @totqty
	SET @rate = ROUND((@lineamt/@totqty),5)	
	SET @totamt=@lineamt

	INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum,xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
			  xsign,xunit,xrate,xpiref,xbatch,xref,xlot,xcottontype,xregi,xserial,xproject,xbinref,xcqtyuse,xdateexp,xgitem)
	VALUES(GETDATE(),@user,@zid,@trnnum,@item,@wh,CAST(GETDATE() as date),@totqty,@totamt,0,@grnnum,
			  @row,'',1,@unitsel,@rate,'',@batch,@ref,@lot,@cottontype,@regi,@partno,@project,@binref,@qtyclaim,@dateexp,@gitem)
print @trnnum
	--IF @qtyclaim > 0
	--BEGIN
	--	EXEC Func_getTrn @zid,'Inventory Transaction','QS--',@trnlength, @strVar=@trnnum OUTPUT
	--	SET @trnnum=@trn+@sb+RIGHT(@trnnum,@trnlength)
	--	INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum,xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
	--		  xsign,xunit,xrate,xpiref,xbatch,xref,xlot,xcottontype,xregi,xserial,xproject,xbinref,xdateexp,xgitem)VALUES
	--		  (GETDATE(),@user,@zid,@trnnum,@item,@wh,CAST(GETDATE() as date),@qtyclaim,(@rate*@qtyclaim),0,@grnnum+'.',
	--		  @row,'',-1,@unitsel,@rate,'',@batch,@ref,@lot,@cottontype,@regi,@partno,@project,@binref,@dateexp,@gitem)
	--END
	--IF @qtygain > 0
	--BEGIN
	--	EXEC Func_getTrn @zid,'Inventory Transaction',@trn,@trnlength, @strVar=@trnnum OUTPUT
	--	SET @trnnum=@trn+@sb+RIGHT(@trnnum,@trnlength)
	--	INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum,xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
	--		  xsign,xunit,xrate,xpiref,xbatch,xref,xlot,xcottontype,xregi,xserial,xproject,xbinref,xdateexp,xgitem)VALUES
	--		  (GETDATE(),@user,@zid,@trnnum,@item,@wh,CAST(GETDATE() as date),@qtygain,0,0,@grnnum,
	--		  @row,'Quantiy Gain',1,@unitsel,0,'',@batch,@ref,@lot,@cottontype,@regi,@partno,@project,@binref,@dateexp,@gitem)
	--END
	--IF @qtybonusgrn > 0
	--BEGIN
	--	EXEC Func_getTrn @zid,'Inventory Transaction','BN--',@trnlength, @strVar=@trnnum OUTPUT
	--		SET @trnnum='BN--'+@sb+RIGHT(@trnnum,@trnlength)
	--	INSERT INTO imtrn(ztime,zauserid,zid,ximtrnnum,xitem,xwh,xdate,xqty,xval,xvalpost,xdocnum,xdocrow,xnote,
	--		  xsign,xunit,xrate,xpiref,xbatch,xref,xlot,xcottontype,xregi,xserial,xproject,xbinref,xdateexp,xgitem)VALUES
	--		  (GETDATE(),@user,@zid,@trnnum,@item,@wh,CAST(GETDATE() as date),@qtybonusgrn,0,0,@grnnum,
	--		  @row,'Bonus Quantiy',1,@unitsel,0,'',@batch,@ref,@lot,@cottontype,@regi,@partno,@project,@binref,@dateexp,@gitem)
	--END

	FETCH NEXT FROM result_cursor INTO @row,@item,@unitpur,@qtygrn,@qtyrec,@rate,@lineamt,@cfpur,@batch,@dateexp,@lot,@cottontype,@partno,@docrow,@pornum,@binref,@qtyclaim,@qtygain,@qtybonusgrn,@gitem
END

CLOSE result_cursor

DEALLOCATE result_cursor

UPDATE pogrnheader 
SET xstatusgrn='Confirmed',xstatus='Confirmed',zutime=GETDATE(),zuuserid=@user 
WHERE zid=@zid 
AND xgrnnum=@grnnum

--IF @type ='LC'
--BEGIN
--Select @voucherno =xvoucheraccrual from pogrnheader where zid = @zid AND xgrnnum = @grnnum
--Update imtrn set xvoucher = @voucherno where zid = @zid AND xdocnum = @grnnum
--END
--========================GRN & PO Rate Equity==========================
--Select @lastpo=xpornum from pogrndetail WHERE zid=@zid AND xgrnnum=@grnnum
--Select @totpoqty=isnull(sum(xqtypur),0),@totgrnqty=isnull(SUM(xqtygrn),0) from poorddetail WHERE zid=@zid AND xpornum=@lastPO
--IF @totpoqty=@totgrnqty
--BEGIN
--Select @poamt= sum(d.xlineamt),@povatrate=isnull(h.xvatrate,0),@podiscrate=isnull(h.xdisc,0),@podiscf=isnull(h.xdiscf,0),@pofreight=isnull(xtransport,0)
--from poordheader h join poorddetail d on h.zid=d.zid and h.xpornum=d.xpornum
--WHERE h.zid=@zid AND h.xpornum=@lastPO group by isnull(h.xvatrate,0),isnull(h.xdisc,0),isnull(h.xdiscf,0),isnull(xtransport,0) 

--SET @podisc=isnull((((@poamt*@podiscrate)/100)+@podiscf),0)
--SET @povat=isnull(((((@poamt-@podisc)*@povatrate)/100)),0)

--SET @podisc=isnull(@podisc,0)
--SET @povat=isnull(@povat,0)

--SET @poamt=@poamt+@povat+@pofreight-@podisc
----Print 'VAT'		Print @povat	Print 'DISC'	Print @podisc	Print 'Freight'	Print @pofreight	Print 'PO AMT'	Print @poamt

--SELECT @grnamt=isnull(sum(xlineamt),0) from pogrndetail WHERE zid=@zid AND xpornum=@lastPO
----Print 'GRN AMT'	Print @grnamt

--SET @differ=@poamt-@grnamt
--	IF @differ<>0
--	BEGIN
--	Select @lastGRN=xgrnnum,@lastrow=xrow from pogrndetail WHERE  zid=@zid AND xpornum=@lastPO
--	IF @differ>-1 AND @differ<1
--	BEGIN
--	Update pogrndetail set xlineamt=xlineamt+@differ WHERE zid=@zid AND xgrnnum=@lastGRN AND xrow=@lastrow
--	Update imtrn set xval=xval+@differ WHERE zid=@zid AND xdocnum=@lastGRN AND xdocrow=@lastrow
--	END
--	ELSE RETURN
--	END
--END
--======================================================================



DECLARE result_cursor CURSOR FORWARD_ONLY FOR

SELECT i.xitem from imtrn i join caitem c 
on i.zid=c.zid and i.xitem=c.xitem
WHERE i.zid=@zid 
AND i.xwh=@wh
AND i.xdocnum=@grnnum 
--AND isnull(c.xtatusmrp,'NO')='NO'
group by i.xitem

OPEN result_cursor
FETCH FROM result_cursor INTO @item
WHILE @@FETCH_STATUS = 0

BEGIN

--EXEC zabsp_PRCS_Markuppolicy @zid,@user,@item,@wh

FETCH NEXT FROM result_cursor INTO @item
END

CLOSE result_cursor

DEALLOCATE result_cursor





/*
SET @pornum=isnull(@pornum,'')
IF @pornum='' 
Select @pornum=xpornum from pogrnheader WHERE zid=@zid AND xgrnnum=@grnnum

IF @zid=100080
Update poordheader set xstatuspor='GRN Created' WHERE zid=@zid and xpornum=@pornum

--================================================================================
Select @tornum=xtornum,@reqnum=xporeqnum from pogrnheader WHERE zid=@zid AND xgrnnum=@grnnum
IF @tornum<>'' 
BEGIN
exec zabsp_sendmail @zid,'',@tornum,'Preparer','GRN'
exec zabsp_sendmail @zid,'',@reqnum,'POPreparer','GRN'
END
*/
END
ELSE RETURN
GO
/****** Object:  StoredProcedure [dbo].[zabsp_PRCS_SR_BatchTag]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[zabsp_PRCS_SR_BatchTag] 
	-- Add the parameters for the stored procedure here
	@zid INT,
	@user VARCHAR(50),
	@srnum VARCHAR(50),
	@row INT

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DECLARE
	@item VARCHAR(50),
	@qtydel DECIMAL(20,2),
	@wh VARCHAR(50),
	@batch VARCHAR(50),
	@dateexp DATETIME,
	@availqty DECIMAL(20,2),
	@remainqty  DECIMAL(20,2),
	@qty DECIMAL(20,2),
	@orderrow INT


    -- Insert statements for procedure here
	SELECT @item=a.xitem,@qtydel=a.xqtyord,@wh=b.xfwh 
	FROM imtordetail a WITH (NOLOCK)  join imtorheader b WITH (NOLOCK)  on a.zid=b.zid and a.xtornum=b.xtornum
	WHERE a.zid=@zid and a.xtornum=@srnum and a.xrow=@row 

	IF EXISTS (SELECT xitem FROM caitem WHERE zid=@zid and xitem=@item and isnull(xbatmg,'')='Yes')
		BEGIN
				DELETE FROM imtordetailbatch where zid=@zid and xtornum=@srnum and xrow=@row and isnull(xstatusint,0)=0
				SET @remainqty=@qtydel

					DECLARE batch_cursor Cursor FORward_Only FOR 
					SELECT xbatch,xdateexp,sum(xavail*xsign) FROM imstockbatchview WHERE zid=@zid and xwh=@wh and xitem=@item and isnull(xbatch,'')<>''
					group by xbatch,xdateexp
					having sum(xavail*xsign)>0
					order by xdateexp asc

					OPEN batch_cursor 
					Fetch FROM batch_cursor INTO @batch,@dateexp,@availqty  
					While @@Fetch_Status = 0
					BEGIN

						IF (@remainqty<=@availqty and @remainqty>0)
								BEGIN
									set @qty=@remainqty
									
								END
						ELSE IF (@remainqty>@availqty and @remainqty>0)
							BEGIN
								set @qty=@availqty 
							END
						ELSE
							set @qty=0
								SELECT @orderrow=MAX(xorderrow) from imtordetailbatch where zid=@zid and xtornum=@srnum and xrow=@row
								SET @orderrow=isnull(@orderrow,0)+1
							IF @qty>0
								BEGIN
							INSERT INTO imtordetailbatch (zid,ztime,zauserid,xtornum,xrow,xorderrow,xwh,xitem,xbatch,xdateexp,xqtyord,xstatusint)
							VALUES (@zid,GETDATE(),@user,@srnum,@row,@orderrow,@wh,@item,@batch,@dateexp,@qty,0)
							SET @remainqty=@remainqty-@qty
								END

					Fetch Next FROM batch_cursor INTO @batch,@dateexp,@availqty  
					END
					CLOSE batch_cursor
					DEALLOCATE batch_cursor
		END
	
END
GO
/****** Object:  StoredProcedure [dbo].[zabsp_RPT_IM_Currentstockbatch]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Author:		<Moshiur,,Rahman>
-- Create date: <31-06-2022,,>
-- Description:	<Current Product Stock WITH ZERO stock>
-- EXEC zabsp_RPT_IM_Currentstockbatch 400010,'safayet','SS033','1031','','Stock'
CREATE PROCEDURE [dbo].[zabsp_RPT_IM_Currentstockbatch] 
	@zid INT,
	@user VARCHAR(50),
	@wh VARCHAR(50),
	@gitem VARCHAR(50),
	@item VARCHAR(50),
	@zero varchar(20)
AS
BEGIN
SET NOCOUNT ON
	
 -- exec zabsp_RPT_IM_Currentstock_withZero'400010','SS033','SS033','118156','Stock'

DECLARE @zorg VARCHAR(250),
	@brname VARCHAR(50),
	@desc VARCHAR(150),
	@itemname VARCHAR(150),
	@unit VARCHAR(20),
	@minqty DECIMAL(20, 2),
	@itemn VARCHAR(20),
	@maxqty DECIMAL(20, 2),
	@itemold VARCHAR(20),
	@groupname VARCHAR(50),
	@stockval DECIMAL(20, 2),
	@salesdata DECIMAL(20, 2),
	@gitemname VARCHAR(50),
	@issuedata DECIMAL(20, 2),
	@qty DECIMAL(20, 2),
	@bookstock DECIMAL(20, 2),
	@StartTime datetime,
	@gitemm varchar(20),
	@imtrnqty decimal(20,2),
	@zidl int,
	@giteml varchar(20),
	@whl varchar(20),
	@iteml varchar(20),
	@qtyonhand DECIMAL(20, 2),
	@qtysoldopd DECIMAL(20, 2),
	@qtysoldipd DECIMAL(20, 2),
	@qtyavail DECIMAL(20, 2),
	@qtybooked DECIMAL(20, 2),
	@whipdph varchar(20),
	@whopdph varchar(20),
	@batch VARCHAR(20),
	@dateexp datetime,
	@bookedqty DECIMAL(20, 2),
	@reordqty DECIMAL(20, 2),
	@whipdph2 varchar(20)


DECLARE @table TABLE (
	ztime DATETIME,
	zauserid VARCHAR(50),
	zid INT,
	zorg VARCHAR(150),
	xwh VARCHAR(50),
	xbrname VARCHAR(50),
	xitem VARCHAR(50),
	xdesc VARCHAR(150),
	xqty DECIMAL(20, 2),
	xqtyonhand DECIMAL(20, 2),
	xqtysold DECIMAL(20, 2),
	xqtyavail DECIMAL(20, 2),
	xgitem VARCHAR(50),
	xbooked DECIMAL(20, 2),
	xgitemdesc VARCHAR(100),
	xmaxqty DECIMAL(20, 2),
	xminqty DECIMAL(20, 2),
	xunit VARCHAR(50),
	xitemold VARCHAR(20),
	xbatch varchar(20),
	xdateexp datetime,
	xbookedqty DECIMAL(20, 2),
	xreordqty DECIMAL(20, 2)
	)
	--SET @StartTime = SysUTCDateTime()

SET @zidl=@zid
SET @iteml=@item
SET @giteml=@gitem
SET @whl = @wh

SET @salesdata = 0
SET @issuedata = 0
SET @qty=0
SELECT @zorg = zorg FROM zbusiness  WHERE zid = @zidl

SELECT @brname=xlong from branchview where zid=@zidl and xcode=@whl
SELECT @whipdph=xipdpharmacy,@whopdph=xwhpharma  FROM opdef WHERE zid=@zidl 
set @whipdph2='SS051'  


IF (ISNULL(@item,'') ='' AND @gitem<>'' AND ISNULL(@wh,'')<>'')
BEGIN

			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
				SELECT i.xwh,c.xgitem,g.xlong,i.xitem,c.xdesc,isnull(i.xbatch,''),i.xdateexp,ISNULL(c.xunit,''),ISNULL(cw.xminqty, 0),ISNULL(cw.xmaxqty, 0),ISNULL(cw.xreordqty, 0),ISNULL(c.xitemold,''),isnull(SUM(i.xqty*i.xsign),0)
				FROM imtrn i WITH (NOLOCK) 
				JOIN caitemviewbatch c ON i.zid=c.zid AND i.xitem=c.xitem 
				join gitemview g on c.zid=g.zid and c.xgitem=g.xcode
				left join caitemwh cw on cw.zid=i.zid and cw.xitem=i.xitem and cw.xcode=@whl
				WHERE i.zid=@zid and c.xgitem=@gitem
				and isnull(i.xwh,'')=@whl
				--and i.xitem=@iteml
				GROUP BY i.xwh,c.xgitem,g.xlong,i.xitem,c.xdesc,isnull(i.xbatch,''),i.xdateexp,c.xunit,cw.xminqty,cw.xmaxqty,cw.xreordqty,c.xitemold
				having isnull(SUM(i.xqty*i.xsign),0)<>0
			OPEN item_cursor
			FETCH FROM item_cursor	INTO @wh,@gitemm,@gitemname,@itemn,@itemname,@batch,@dateexp,@unit,@minqty,@maxqty,@reordqty,@itemold,@imtrnqty
										 
		WHILE @@FETCH_STATUS = 0
		BEGIN

				SELECT @salesdata=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				AND ISNULL(d.xstatusord,'0')<>'1' 
				AND h.xstatusprint='Printed'

				SELECT @qtysoldopd=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				and ISNULL(h.xstatusprint,'') <> ('Cancelled') 
				AND ISNULL(d.xstatusord,'0')<>'1' 
				and h.xstatusprint='Open' 
				and d.xepisodetype='Opd'
				
				SELECT @qtysoldipd=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE d.zid = @zid 
				and isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				and ISNULL(h.xstatusprint,'') <> ('Cancelled') 
				AND ISNULL(d.xstatusord,'0')<>'1' 
				and h.xstatusprint in ('Open','On Hold')
				and d.xepisodetype<>'Opd'

				SET @qtyonhand=ISNULL(@imtrnqty,0)-ISNULL(@salesdata,0)-ISNULL(@qtysoldipd,0)

				SELECT @issuedata=isnull(sum(e.xqtyord),0)
				FROM imtordetail d WITH (NOLOCK) 
				JOIN imtorheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xtornum=h.xtornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				join imtordetailbatch e WITH (NOLOCK) on d.zid=e.zid and d.xtornum=e.xtornum and d.xrow=e.xrow  and isnull(e.xstatusint,0)=0 
				WHERE d.zid = @zid and isnull(h.xfwh,'')=@wh and isnull(e.xbatch,'')=@batch and e.xdateexp=@dateexp
				and d.xitem = @itemn
				and h.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed') 
				AND d.xqtyord>0

			
				SET @qtybooked=ISNULL(@qtysoldopd,0)+ISNULL(@issuedata,0)

			--SET @qty = isnull(@imtrnqty,0) - (isnull(@salesdata,0) + isnull(@issuedata,0))
		
		
			IF @wh=@whipdph
			BEGIN
				SELECT @bookstock = isnull(sum(xqtyalc), 0)
				FROM ZABDB.dbo.ipdpharmabookstockrpt WITH (NOLOCK)
				WHERE zid = @zidl AND xwh = @whipdph AND xitem = @itemn and xbatch=@batch and xdateexp=@dateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)

			END
			ELSE IF @wh =@whipdph2
			BEGIN
				SELECT @bookstock = isnull(sum(xqtyalc), 0)
				FROM ZABDB.dbo.ipdpharmabookstockrpt WITH (NOLOCK)
				WHERE zid = @zidl AND xwh = @whipdph2 AND xitem = @itemn and xbatch=@batch and xdateexp=@dateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)

			END
			ELSE IF @wh=@whopdph
			BEGIN
				SELECT @qtybooked=sum(isnull(m.xqtyord,0))
					FROM mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase 
					JOIN caitemviewbatchopd c on d.zid=c.zid and d.xitem1=c.xitem 
					join mmmedicine m WITH (NOLOCK) on d.zid=m.zid and d.xcase=m.xcase and d.xrow=m.xrow and isnull(m.xstatusint,0)=0
					where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 and h.xdate=CAST(GETDATE() as date) 
					and m.xwh=@whopdph and m.xitem=@itemn and m.xbatch=@batch and m.xdateexp=@dateexp
					group by d.zid,d.xwh,c.xgitem,m.xitem,c.xdesc,isnull(m.xbatch,''),m.xdateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)
			END


			SET @qtyavail=ISNULL(@qtyonhand,0)-ISNULL(@qtybooked,0)
			

		INSERT INTO @table (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xunit,xreordqty,xitemold,xgitemdesc,xqtyonhand,xqtysold,xqtyavail,xbatch,xdateexp)
		VALUES (GETDATE(),@user,@zid,@zorg,@wh,@brname,@itemn,@gitemm,@itemname,ISNULL(@qty,0),ISNULL(@qtybooked,0),ISNULL(@minqty,0),ISNULL(@maxqty,0),@unit,@reordqty,@itemold,@gitemname,ISNULL(@qtyonhand,0),ISNULL(@salesdata,0),ISNULL(@qtyavail,0),@batch,@dateexp)


		FETCH NEXT FROM item_cursor INTO @wh,@gitemm,@gitemname,@itemn,@itemname,@batch,@dateexp,@unit,@minqty,@maxqty,@reordqty,@itemold,@imtrnqty
		END
		CLOSE item_cursor
		DEALLOCATE item_cursor
END

ELSE IF (ISNULL(@item,'') <>'' AND ISNULL(@wh,'')<>'')
BEGIN

			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
				SELECT i.xwh,c.xgitem,g.xlong,i.xitem,c.xdesc,isnull(i.xbatch,''),i.xdateexp,ISNULL(c.xunit,''),ISNULL(cw.xminqty, 0),ISNULL(cw.xmaxqty, 0),ISNULL(cw.xreordqty, 0),ISNULL(c.xitemold,''),isnull(SUM(i.xqty*i.xsign),0)
				FROM imtrn i WITH (NOLOCK) 
				JOIN caitemviewbatch c ON i.zid=c.zid AND i.xitem=c.xitem 
				join gitemview g on c.zid=g.zid and c.xgitem=g.xcode
				left join caitemwh cw on cw.zid=i.zid and cw.xitem=i.xitem and cw.xcode=@whl
				WHERE i.zid=@zid 
				and isnull(i.xwh,'')=@whl
				and i.xitem=@iteml
				GROUP BY i.xwh,c.xgitem,g.xlong,i.xitem,c.xdesc,isnull(i.xbatch,''),i.xdateexp,c.xunit,cw.xminqty,cw.xmaxqty,cw.xreordqty,c.xitemold
				having isnull(SUM(i.xqty*i.xsign),0)<>0
			OPEN item_cursor
			FETCH FROM item_cursor	INTO @wh,@gitemm,@gitemname,@itemn,@itemname,@batch,@dateexp,@unit,@minqty,@maxqty,@reordqty,@itemold,@imtrnqty
										 
		WHILE @@FETCH_STATUS = 0
		BEGIN

					SELECT @salesdata=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				AND ISNULL(d.xstatusord,'0')<>'1' 
				AND h.xstatusprint='Printed'

				SELECT @qtysoldopd=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				and ISNULL(h.xstatusprint,'') <> ('Cancelled') 
				AND ISNULL(d.xstatusord,'0')<>'1' 
				and h.xstatusprint='Open' 
				and d.xepisodetype='Opd'
				
				SELECT @qtysoldipd=ISNULL(sum(d.xqtyord*d.xsign),0)
				FROM opdodetail d WITH (NOLOCK) 
				JOIN opdoheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xdornum=h.xdornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				WHERE d.zid = @zid 
				and isnull(d.xwh,'')=@wh 
				and d.xitem=@itemn and d.xbatch=@batch and d.xdateexp=@dateexp
				and ISNULL(h.xstatusprint,'') <> ('Cancelled') 
				AND ISNULL(d.xstatusord,'0')<>'1' 
				and h.xstatusprint in ('Open','On Hold') 
				and d.xepisodetype<>'Opd'

				SET @qtyonhand=ISNULL(@imtrnqty,0)-ISNULL(@salesdata,0)-ISNULL(@qtysoldipd,0)

				SELECT @issuedata=isnull(sum(e.xqtyord),0)
				FROM imtordetail d WITH (NOLOCK) 
				JOIN imtorheader h WITH (NOLOCK) ON d.zid=h.zid AND d.xtornum=h.xtornum 
				JOIN caitemviewbatch c ON d.zid=c.zid AND d.xitem=c.xitem 
				join imtordetailbatch e WITH (NOLOCK) on d.zid=e.zid and d.xtornum=e.xtornum and d.xrow=e.xrow  and isnull(e.xstatusint,0)=0 
				WHERE d.zid = @zid and isnull(h.xfwh,'')=@wh and isnull(e.xbatch,'')=@batch and e.xdateexp=@dateexp
				and d.xitem = @itemn
				and h.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed') 
				AND d.xqtyord>0

			
				SET @qtybooked=ISNULL(@qtysoldopd,0)+ISNULL(@issuedata,0)

			--SET @qty = isnull(@imtrnqty,0) - (isnull(@salesdata,0) + isnull(@issuedata,0))
		
		
			IF @wh=@whipdph
			BEGIN
				SELECT @bookstock = isnull(sum(xqtyalc), 0)
				FROM ZABDB.dbo.ipdpharmabookstockrpt WITH (NOLOCK)
				WHERE zid = @zidl AND xwh = @whipdph AND xitem = @itemn and xbatch=@batch and xdateexp=@dateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)

			END
			ELSE IF @wh=@whipdph2
			BEGIN
				SELECT @bookstock = isnull(sum(xqtyalc), 0)
				FROM ZABDB.dbo.ipdpharmabookstockrpt WITH (NOLOCK)
				WHERE zid = @zidl AND xwh = @whipdph2 AND xitem = @itemn and xbatch=@batch and xdateexp=@dateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)

			END
			ELSE IF @wh=@whopdph
			BEGIN
				SELECT @qtybooked=sum(isnull(m.xqtyord,0))
					FROM mmprescription d WITH (NOLOCK) join mmappointment h WITH (NOLOCK) on d.zid =h.zid and d.xcase =h.xcase 
					JOIN caitemviewbatchopd c on d.zid=c.zid and d.xitem1=c.xitem 
					join mmmedicine m WITH (NOLOCK) on d.zid=m.zid and d.xcase=m.xcase and d.xrow=m.xrow and isnull(m.xstatusint,0)=0
					where h.xstatuspharma = 'In Process' and isnull(d.xqty,0)>0 and h.xdate=CAST(GETDATE() as date) 
					and m.xwh=@whopdph and m.xitem=@itemn and m.xbatch=@batch and m.xdateexp=@dateexp
					group by d.zid,d.xwh,c.xgitem,m.xitem,c.xdesc,isnull(m.xbatch,''),m.xdateexp

				SET @qtybooked=ISNULL(@qtybooked,0)+ISNULL(@bookstock,0)
			END


			SET @qtyavail=ISNULL(@qtyonhand,0)-ISNULL(@qtybooked,0)
			
		INSERT INTO @table (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xunit,xreordqty,xitemold,xgitemdesc,xqtyonhand,xqtysold,xqtyavail,xbatch,xdateexp)
		VALUES (GETDATE(),@user,@zid,@zorg,@wh,@brname,@itemn,@gitemm,@itemname,ISNULL(@qty,0),ISNULL(@qtybooked,0),ISNULL(@minqty,0),ISNULL(@maxqty,0),@unit,@reordqty,@itemold,@gitemname,ISNULL(@qtyonhand,0),ISNULL(@salesdata,0),ISNULL(@qtyavail,0),@batch,@dateexp)
		SET @qtybooked=0
		SET @qtyavail=0
		SET @bookstock=0
		SET @qtyonhand=0
		SET @salesdata=0
		SET @qtysoldopd=0
		SET @issuedata=0
		SET @qtysoldipd=0
		FETCH NEXT FROM item_cursor INTO @wh,@gitemm,@gitemname,@itemn,@itemname,@batch,@dateexp,@unit,@minqty,@maxqty,@reordqty,@itemold,@imtrnqty
		END
		CLOSE item_cursor
		DEALLOCATE item_cursor
END

ELSE 
PRINT ''

Delete from rptcurrentstockbatch where ztime<CAST(GETDATE() AS DATE)
Delete from rptcurrentstockbatch where zauserid = @user
INSERT INTO rptcurrentstockbatch (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xunit,xreordqty,xitemold,xgitemdesc,xqtyonhand,xqtysold,xqtyavail,xbatch,xdateexp)
SELECT ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xunit,xreordqty,xitemold,xgitemdesc,xqtyonhand,xqtysold,xqtyavail,xbatch,xdateexp FROM @table where xqtyonhand<>0 order by xitem
END
SET NOCOUNT OFF
--SELECT * FROM @table Where xqtyavail<>0

/*
drop table  rptcurrentstockbatch where xitem='117661'
delete from  rptcurrentstockbatch where zauserid='simtuhin'
Create TABLE rptcurrentstockbatch (
	ztime DATETIME,
	zauserid VARCHAR(50),
	zid INT,
	zorg VARCHAR(150),
	xwh VARCHAR(50),
	xbrname VARCHAR(50),
	xitem VARCHAR(50),
	xdesc VARCHAR(150),
	xqty DECIMAL(20, 2),
	xqtyonhand DECIMAL(20, 2),
	xqtysold DECIMAL(20, 2),
	xqtyavail DECIMAL(20, 2),
	xgitem VARCHAR(50),
	xbooked DECIMAL(20, 2),
	xgitemdesc VARCHAR(100),
	xmaxqty DECIMAL(20, 2),
	xminqty DECIMAL(20, 2),
	xunit VARCHAR(50),
	xitemold VARCHAR(20),
	xbatch VARCHAR(20),
	xdateexp datetime,
	xbookedqty DECIMAL(20,2),
	xreordqty DECIMAL(20, 2)
	)
	*/





GO
/****** Object:  StoredProcedure [dbo].[zabsp_RPT_IM_Currentstockqty]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Author:		<Moshiur,,Rahman>
-- Create date: <31-06-2022,,>
-- Description:	<Current Product Stock WITH ZERO stock>
-- EXEC zabsp_RPT_IM_Currentstockqty 100000,'','MSD-01','','','Stock'
CREATE PROCEDURE [dbo].[zabsp_RPT_IM_Currentstockqty] 
	@zid INT,
	@user VARCHAR(50),
	@wh VARCHAR(50),
	@gitem VARCHAR(50),
	@item VARCHAR(50),
	@zero varchar(20)
AS
BEGIN
SET NOCOUNT ON
	
 -- exec zabsp_RPT_IM_Currentstock_withZero'400010','cs004','1031','',''

DECLARE @zorg VARCHAR(250),
	@brname VARCHAR(50),
	@desc VARCHAR(150),
	@itemname VARCHAR(150),
	@unit VARCHAR(20),
	@minqty DECIMAL(20, 2),
	@itemn VARCHAR(20),
	@maxqty DECIMAL(20, 2),
	@itemold VARCHAR(20),
	@groupname VARCHAR(50),
	@stockval DECIMAL(20, 2),
	@salesdata DECIMAL(20, 2),
	@gitemname VARCHAR(50),
	@issuedata DECIMAL(20, 2),
	@qty DECIMAL(20, 2),
	@bookstock DECIMAL(20, 2),
	@StartTime datetime,
	@gitemm varchar(20),
	@imtrnqty decimal(20,2),
	@zidl int,
	@giteml varchar(20),
	@whl varchar(20),
	@iteml varchar(20),
	@qtyonhand DECIMAL(20, 2),
	@qtysoldopd DECIMAL(20, 2),
	@qtysoldipd DECIMAL(20, 2),
	@qtyavail DECIMAL(20, 2),
	@qtybooked DECIMAL(20, 2),
	@whipdph varchar(20),
	@whopdph varchar(20),
	@reordqty DECIMAL(20, 2),
	@whipdph2 varchar(20)

DECLARE @table TABLE (
	ztime DATETIME,
	zauserid VARCHAR(50),
	zid INT,
	zorg VARCHAR(150),
	xwh VARCHAR(50),
	xbrname VARCHAR(50),
	xitem VARCHAR(50),
	xdesc VARCHAR(150),
	xqty DECIMAL(20, 2),
	xqtyonhand DECIMAL(20, 2),
	xqtysold DECIMAL(20, 2),
	xqtyavail DECIMAL(20, 2),
	xgitem VARCHAR(50),
	xbooked DECIMAL(20, 2),
	xgitemdesc VARCHAR(100),
	xmaxqty DECIMAL(20, 2),
	xminqty DECIMAL(20, 2),
	xunit VARCHAR(50),
	xitemold VARCHAR(20),
	xreordqty DECIMAL(20, 2))
	--SET @StartTime = SysUTCDateTime()

SET @zidl=@zid
SET @iteml=@item
SET @giteml=@gitem
SET @whl = @wh

SET @salesdata = 0
SET @issuedata = 0
SET @qty=0
SELECT @zorg = zorg FROM zbusiness  WHERE zid = @zidl

SELECT @brname = isnull(xlong,'') FROM ZABDB.dbo.branchview 
WHERE zid = @zidl AND xcode = @whl AND zactive = '1'

--SELECT @whipdph=xipdpharmacy,@whopdph=xwhpharma  FROM opdef WHERE zid=@zidl 
--set @whipdph2='SS051'

IF (isnull(@item,'')<>'' AND isnull(@wh,'')<>'')
BEGIN
	IF @zero <>'Zero'
	BEGIN
		SELECT @itemn= c.xitem,	@itemname=isnull(c.xdesc,''),@unit=isnull(c.xunit,''),@minqty=isnull(e.xminqty, 0),@maxqty=isnull(e.xmaxqty, 0),@reordqty=isnull(e.xreordqty, 0),@gitem=c.xgitem,@imtrnqty=isnull(sum(im.xqty*im.xsign),0)
		FROM caitem c WITH (NOLOCK)
		join imtrn im WITH (NOLOCK)  ON c.zid = im.zid AND c.xitem = im.xitem
		left join caitemwh e on c.zid=e.zid and c.xitem=e.xitem and e.xcode=@whl
		WHERE c.zid=@zidl
		AND im.xwh=@whl
		AND c.xitem=@iteml
		GROUP BY c.xitem,isnull(c.xdesc,''),isnull(c.xunit,''),	isnull(e.xminqty, 0),isnull(e.xmaxqty, 0),isnull(e.xreordqty, 0),c.xgitem
		HAVING isnull(sum(im.xqty*im.xsign),0)<>0
	
	
		SELECT @salesdata = isnull(sum(p.xqty),0)
		FROM mmprescription p JOIN mmappointment a
		ON p.zid=a.zid and p.xcase=a.xcase
		WHERE p.zid = @zidl
		AND p.xwh = @whl
		AND p.xitem1 = @itemn
		AND isnull(a.xstatuspharma,'')='Completed'
			

		SET @qtyonhand=ISNULL(@imtrnqty,0)-ISNULL(@salesdata,0)

		SELECT @issuedata = isnull(sum(xqtyord),0)
		FROM ZABDB.dbo.imtordetail b WITH (NOLOCK) 
		INNER JOIN ZABDB.dbo.imtorheader a WITH (NOLOCK)  ON a.zid = b.zid
		AND a.xtornum = b.xtornum
		WHERE a.zid = @zidl AND b.xitem = @itemn	AND a.xfwh = @whl AND xqtyord>0
		AND a.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed')
	
		SET @qtybooked=ISNULL(@issuedata,0)

		--SET @qty = isnull(@imtrnqty,0) - (isnull(@salesdata,0) + isnull(@issuedata,0))
		
	


	--SET @bookstock=isnull(@bookstock,0)+@issuedata
		SET @qtyavail=ISNULL(@qtyonhand,0)-ISNULL(@qtybooked,0)
	END
	ELSE IF @zero='Zero'
	BEGIN
		SELECT @itemn = a.xitem,@itemname=a.xdesc,@unit=a.xunit,@minqty=b.xminqty,@maxqty=b.xmaxqty,@reordqty=b.xreordqty,@gitem=a.xgitem,@qtyavail=a.xavail
		FROM imstockdetview a left join caitemwh b on a.zid=b.zid and a.xitem=b.xitem and b.xcode=@whl
		WHERE a.zid=@zidl AND a.xwh=@whl AND a.xitem=@iteml AND (a.xavail<0.0001 AND a.xinhand < 0.001)
	END

	SELECT @gitemname =xlong FROM ZABDB.dbo.xcodes WHERE zid=@zid AND xcode=@gitem AND xtype='Item Group' AND xtypeobj='Product'

	INSERT INTO @table (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xreordqty,xunit,xgitemdesc,xqtyonhand,xqtysold,xqtyavail)
	VALUES (GETDATE(),@user,@zid,@zorg,@wh,@brname,@itemn,@gitem,@itemname,ISNULL(@qty,0),ISNULL(@qtybooked,0),ISNULL(@minqty,0),ISNULL(@maxqty,0),ISNULL(@reordqty,0),@unit,@gitemname,ISNULL(@qtyonhand,0),ISNULL(@salesdata,0),ISNULL(@qtyavail,0))


END 
--ELSE 
ELSE IF (ISNULL(@item,'') ='' AND @gitem<>'' AND ISNULL(@wh,'')<>'')
BEGIN
		IF @zero<>'Zero'


		BEGIN
			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
			SELECT c.xitem,	ISNULL(c.xdesc,''),	ISNULL(c.xunit,''),	ISNULL(e.xminqty, 0),ISNULL(e.xmaxqty, 0),ISNULL(e.xreordqty, 0),c.xgitem,c.xgitemdesc,
			ISNULL(sum(im.xqty*im.xsign),0),
			ISNULL((SELECT isnull(sum(p.xqty),0) FROM mmprescription p JOIN mmappointment a ON p.zid=a.zid and p.xcase=a.xcase
			WHERE p.zid = @zidl AND p.xwh = @whl AND isnull(a.xstatuspharma,'')='Completed'),0),
			isnull((SELECT isnull(sum(xqtyord),0) FROM ZABDB.dbo.imtordetail b WITH (NOLOCK) INNER JOIN ZABDB.dbo.imtorheader a WITH (NOLOCK)  ON a.zid = b.zid
					AND a.xtornum = b.xtornum WHERE a.zid = @zidl AND b.xitem = c.xitem	AND a.xfwh = @whl AND xqtyord>0
					AND a.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed')),0)
			FROM ZABDB.dbo.caitemview c WITH  (NOLOCK)  
			join ZABDB.dbo.imtrn im WITH (NOLOCK) ON c.zid = im.zid AND c.xitem = im.xitem
			left join caitemwh e on c.zid=e.zid and c.xitem=e.xitem and e.xcode=@whl
			WHERE c.zid=@zidl
			AND im.xwh=@whl
			AND c.xgitem = @giteml
			GROUP BY c.xitem,	ISNULL(c.xdesc,''),	ISNULL(c.xunit,''),	ISNULL(e.xminqty, 0),
			ISNULL(e.xmaxqty, 0),ISNULL(e.xreordqty, 0),c.xgitem,c.xgitemdesc
			HAVING ISNULL(sum(im.xqty*im.xsign),0)<>0
		END
		ELSE IF @zero='Zero'
		BEGIN
			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
			SELECT a.xitem,a.xdesc,a.xunit,b.xminqty,b.xmaxqty,b.xreordqty,a.xgitem,'',a.xavail,0,0,0,0 FROM imstockdetview a
			left join caitemwh b on a.zid=b.zid and a.xitem=b.xitem and b.xcode=@whl
			WHERE a.zid=@zidl AND a.xwh=@whl AND a.xgitem=@giteml AND (a.xavail<0.0001 AND a.xinhand < 0.001)
		END

			OPEN item_cursor
			FETCH FROM item_cursor	INTO @itemn,@itemname,@unit,@minqty,@maxqty,@reordqty,@gitemm,@gitemname,@imtrnqty,@salesdata,@issuedata		

		WHILE @@FETCH_STATUS = 0
		BEGIN
			

			SET @qtyonhand=ISNULL(@imtrnqty,0)-ISNULL(@salesdata,0)
			SET @qtybooked=ISNULL(@issuedata,0)

				IF @zero='Zero'
				BEGIN
					SELECT @gitemname =xlong FROM ZABDB.dbo.xcodes WHERE zid=@zid AND xcode=@gitemm AND xtype='Item Group' AND xtypeobj='Product'
					SET @qtyavail=@imtrnqty
				END
		
				
				--SET @bookstock=ISNULL(@bookstock,0)+@issuedata
				SET @qtyavail=ISNULL(@qtyonhand,0)-ISNULL(@qtybooked,0)


		INSERT INTO @table (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xreordqty,xunit,xgitemdesc,xqtyonhand,xqtysold,xqtyavail)
		VALUES (GETDATE(),@user,@zid,@zorg,@wh,@brname,@itemn,@gitem,@itemname,ISNULL(@qty,0),ISNULL(@qtybooked,0),ISNULL(@minqty,0),ISNULL(@maxqty,0),ISNULL(@reordqty,0),@unit,@gitemname,ISNULL(@qtyonhand,0),ISNULL(@salesdata,0),ISNULL(@qtyavail,0))


		SET @salesdata = 0
		SET @issuedata = 0
		SET @bookstock=0
		SET @qtyavail = 0
		SET @qtybooked = 0

		FETCH FROM item_cursor	INTO @itemn,@itemname,@unit,@minqty,@maxqty,@reordqty,@gitemm,@gitemname,@imtrnqty,@salesdata,@issuedata		
		END
		CLOSE item_cursor
		DEALLOCATE item_cursor
END
ELSE IF (ISNULL(@item,'') ='' AND @gitem='' AND ISNULL(@wh,'')<>'')
BEGIN
		IF @zero<>'Zero'
		BEGIN
			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
			SELECT c.xitem,	ISNULL(c.xdesc,''),	ISNULL(c.xunit,''),	ISNULL(e.xminqty, 0),ISNULL(e.xmaxqty, 0),ISNULL(e.xreordqty, 0),c.xgitem,c.xgitemdesc,
			ISNULL(sum(im.xqty*im.xsign),0),
			ISNULL((SELECT isnull(sum(p.xqty),0) FROM mmprescription p JOIN mmappointment a ON p.zid=a.zid and p.xcase=a.xcase
			WHERE p.zid = @zidl AND p.xwh = @whl AND isnull(a.xstatuspharma,'')='Completed'),0),
			
			isnull((SELECT isnull(sum(xqtyord),0) FROM ZABDB.dbo.imtordetail b WITH (NOLOCK) INNER JOIN ZABDB.dbo.imtorheader a WITH (NOLOCK)  ON a.zid = b.zid
					AND a.xtornum = b.xtornum WHERE a.zid = @zidl AND b.xitem = c.xitem	AND a.xfwh = @whl AND xqtyord>0
					AND a.xstatustor NOT IN ('Confirmed','Transferred','','Rejected','Dismissed')),0)
			FROM ZABDB.dbo.caitemview c WITH  (NOLOCK)  
			join ZABDB.dbo.imtrn im WITH (NOLOCK) ON c.zid = im.zid AND c.xitem = im.xitem
			left join caitemwh e on c.zid=e.zid and c.xitem=e.xitem and e.xcode=@whl
			WHERE c.zid=@zidl
			AND im.xwh=@whl
			GROUP BY c.xitem,	ISNULL(c.xdesc,''),	ISNULL(c.xunit,''),	ISNULL(e.xminqty, 0),
			ISNULL(e.xmaxqty, 0),ISNULL(e.xreordqty, 0),c.xgitem,c.xgitemdesc
			HAVING ISNULL(sum(im.xqty*im.xsign),0)<>0
		END
		ELSE IF @zero='Zero'
		BEGIN
			DECLARE item_cursor CURSOR FORWARD_ONLY	FOR
			SELECT a.xitem,a.xdesc,a.xunit,b.xminqty,b.xmaxqty,b.xreordqty,a.xgitem,'',a.xavail,0,0,0,0 FROM imstockdetview a
			left join caitemwh b on a.zid=b.zid and a.xitem=b.xitem and b.xcode=@whl
			WHERE a.zid=@zidl AND a.xwh=@whl AND (a.xavail<0.0001 AND a.xinhand < 0.001)
		END

			OPEN item_cursor
			FETCH FROM item_cursor	INTO @itemn,@itemname,@unit,@minqty,@maxqty,@reordqty,@gitemm,@gitemname,@imtrnqty,@salesdata,@issuedata		

		WHILE @@FETCH_STATUS = 0
		BEGIN
			

			SET @qtyonhand=ISNULL(@imtrnqty,0)-ISNULL(@salesdata,0)
			SET @qtybooked=ISNULL(@issuedata,0)

				IF @zero='Zero'
				BEGIN
					SELECT @gitemname =xlong FROM ZABDB.dbo.xcodes WHERE zid=@zid AND xcode=@gitemm AND xtype='Item Group' AND xtypeobj='Product'
					SET @qtyavail=@imtrnqty
				END
		
				
				--SET @bookstock=ISNULL(@bookstock,0)+@issuedata
				SET @qtyavail=ISNULL(@qtyonhand,0)-ISNULL(@qtybooked,0)

		INSERT INTO @table (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xreordqty,xunit,xgitemdesc,xqtyonhand,xqtysold,xqtyavail)
		VALUES (GETDATE(),@user,@zid,@zorg,@wh,@brname,@itemn,@gitem,@itemname,ISNULL(@qty,0),ISNULL(@qtybooked,0),ISNULL(@minqty,0),ISNULL(@maxqty,0),ISNULL(@reordqty,0),@unit,@gitemname,ISNULL(@qtyonhand,0),ISNULL(@salesdata,0),ISNULL(@qtyavail,0))


		SET @salesdata = 0
		SET @issuedata = 0
		SET @bookstock=0
		SET @qtyavail = 0
		SET @qtybooked = 0

		FETCH FROM item_cursor	INTO @itemn,@itemname,@unit,@minqty,@maxqty,@reordqty,@gitemm,@gitemname,@imtrnqty,@salesdata,@issuedata		
		END
		CLOSE item_cursor
		DEALLOCATE item_cursor
END
ELSE 
PRINT ''

Delete from rptcurrentstock where ztime<CAST(GETDATE() AS DATE)
Delete from rptcurrentstock where zauserid = @user
INSERT INTO rptcurrentstock (ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xreordqty,xunit,xgitemdesc,xqtyonhand,xqtysold,xqtyavail)
SELECT ztime,zauserid,zid,zorg,xwh,xbrname,xitem,xgitem,xdesc,xqty,xbooked,xminqty,xmaxqty,xreordqty,xunit,xgitemdesc,xqtyonhand,xqtysold,xqtyavail FROM @table 
END
SET NOCOUNT OFF
--SELECT * FROM @table 

/*
select * from rptcurrentstock
drop table rptcurrentstock
Create TABLE rptcurrentstock (
	ztime DATETIME,
	zauserid VARCHAR(50),
	zid INT,
	zorg VARCHAR(150),
	xwh VARCHAR(50),
	xbrname VARCHAR(50),
	xitem VARCHAR(50),
	xdesc VARCHAR(150),
	xqty DECIMAL(20, 2),
	xqtyonhand DECIMAL(20, 2),
	xqtysold DECIMAL(20, 2),
	xqtyavail DECIMAL(20, 2),
	xgitem VARCHAR(50),
	xbooked DECIMAL(20, 2),
	xgitemdesc VARCHAR(100),
	xmaxqty DECIMAL(20, 2),
	xminqty DECIMAL(20, 2),
	xunit VARCHAR(50),
	xitemold VARCHAR(20),
	xreordqty DECIMAL(20, 2))

*/
GO
/****** Object:  StoredProcedure [dbo].[zabsp_vital_calc]    Script Date: 11/25/2024 5:22:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
--  exec zabsp_vital_calc 400010,'OPD-20230117HS0193602'
CREATE PROCEDURE  [dbo].[zabsp_vital_calc] 
	-- Add the parameters for the stored procedure here
	 @zid INT
	,@case varchar(50)
	
AS
	SET NOCOUNT ON;
	DECLARE
		@xrow int
	   ,@bsa DECIMAL(20,5)
	   ,@bmi DECIMAL(20,5)
	   ,@xheightvit DECIMAL(20,5)
	   ,@xweightvit DECIMAL(20,5)
	   ,@xavpu DECIMAL(20,5)
	   ,@xtrauma DECIMAL(20,5)
	   ,@xbmigyn DECIMAL(20,5)
	   ,@xbsa DECIMAL(20,5)
	   ,@height DECIMAL(20,5)
	   ,@weight DECIMAL(20,5)
	   ,@xofc DECIMAL(20,5)
	   ,@ibmen DECIMAL(20,5)
	   ,@ibwmen DECIMAL(20,5)
	   ,@dummyht DECIMAL(20,5)
	   ,@dummyht2 DECIMAL(20,5)

		
   DECLARE cursor_bulk CURSOR FORWARD_ONLY FOR

		select xrow,cast (ISNULL(xheightvit,0) as decimal(20,5)),cast(isnull(xweightvit,0) as decimal(20,5))
		from dbo.vitalssign WITH (NOLOCK) 
		where zid=@zid and xcase=@case

		--print 'Hey'
	OPEN cursor_bulk
	FETCH FROM cursor_bulk INTO @xrow,@height,@weight
		

	WHILE @@FETCH_STATUS = 0
	BEGIN
		set @xheightvit= isnull(@weight,0)/isnull(@height,1)
		set @xofc=isnull(@xheightvit,0)/isnull(@height,1)
		set @bmi=isnull(@xofc,0)*10000
		set @xavpu= isnull(@height,0)*isnull(@weight,0)
		set @xtrauma=isnull(@xavpu,0)/3600
		set @bsa=SQRT(@xtrauma)
		set @dummyht=isnull(@height,0)-152.40
		set @dummyht2=0.91*isnull(@dummyht,0)
		set @ibmen=50.00+isnull(@dummyht2,0)
		set @ibwmen=45.50+isnull(@dummyht2,0)

	
		update dbo.vitalssign SET xbmigyn=@bmi,xbsa=@bsa,xibwmen=@ibwmen,xibwwom=@ibmen where zid=@zid and xcase=@case
		--print @bmi
		--print @bsa
		FETCH NEXT FROM cursor_bulk INTO @xrow,@height,@weight
		END
	CLOSE cursor_bulk
	DEALLOCATE cursor_bulk

	--ALTER TABLE vitalssign
	--ADD xrem VARCHAR(200)
   
		
	
	
	
	


GO
