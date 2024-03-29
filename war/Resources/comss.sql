USE [master]
GO
/****** Object:  Database [comss]    Script Date: 12/18/2019 5:07:45 PM ******/
CREATE DATABASE [comss]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'comss', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\comss.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'comss_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\comss_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [comss] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [comss].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [comss] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [comss] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [comss] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [comss] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [comss] SET ARITHABORT OFF 
GO
ALTER DATABASE [comss] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [comss] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [comss] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [comss] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [comss] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [comss] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [comss] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [comss] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [comss] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [comss] SET  DISABLE_BROKER 
GO
ALTER DATABASE [comss] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [comss] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [comss] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [comss] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [comss] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [comss] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [comss] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [comss] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [comss] SET  MULTI_USER 
GO
ALTER DATABASE [comss] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [comss] SET DB_CHAINING OFF 
GO
ALTER DATABASE [comss] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [comss] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [comss] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [comss] SET QUERY_STORE = OFF
GO
USE [comss]
GO
/****** Object:  Table [dbo].[apps]    Script Date: 12/18/2019 5:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[apps](
	[idApp] [int] IDENTITY(1,1) NOT NULL,
	[appName] [nvarchar](100) NULL,
 CONSTRAINT [PK_apps] PRIMARY KEY CLUSTERED 
(
	[idApp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ciclos]    Script Date: 12/18/2019 5:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ciclos](
	[idCiclos] [int] IDENTITY(1,1) NOT NULL,
	[ciclosName] [nchar](100) NULL,
	[version_fk] [int] NULL,
 CONSTRAINT [PK_ciclos] PRIMARY KEY CLUSTERED 
(
	[idCiclos] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[metrics]    Script Date: 12/18/2019 5:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[metrics](
	[idMetrics] [int] IDENTITY(1,1) NOT NULL,
	[metricName] [nchar](100) NULL,
	[metricValue] [float] NULL,
	[ciclos_fk] [int] NULL,
 CONSTRAINT [PK_metrics] PRIMARY KEY CLUSTERED 
(
	[idMetrics] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[version]    Script Date: 12/18/2019 5:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[version](
	[idVersion] [int] IDENTITY(1,1) NOT NULL,
	[versionName] [nvarchar](10) NOT NULL,
	[app_fk] [int] NOT NULL,
 CONSTRAINT [PK_version] PRIMARY KEY CLUSTERED 
(
	[idVersion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ciclos]  WITH CHECK ADD  CONSTRAINT [FK_ciclos_version] FOREIGN KEY([version_fk])
REFERENCES [dbo].[version] ([idVersion])
GO
ALTER TABLE [dbo].[ciclos] CHECK CONSTRAINT [FK_ciclos_version]
GO
ALTER TABLE [dbo].[metrics]  WITH CHECK ADD  CONSTRAINT [FK_metrics_ciclos] FOREIGN KEY([ciclos_fk])
REFERENCES [dbo].[ciclos] ([idCiclos])
GO
ALTER TABLE [dbo].[metrics] CHECK CONSTRAINT [FK_metrics_ciclos]
GO
ALTER TABLE [dbo].[version]  WITH CHECK ADD  CONSTRAINT [FK_version_apps] FOREIGN KEY([app_fk])
REFERENCES [dbo].[apps] ([idApp])
GO
ALTER TABLE [dbo].[version] CHECK CONSTRAINT [FK_version_apps]
GO
/****** Object:  StoredProcedure [dbo].[report]    Script Date: 12/18/2019 5:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[report] (
	-- Add the parameters for the stored procedure here
	@idVersion varchar(30))
AS
BEGIN
	DECLARE @Columns as VARCHAR(MAX), @query as VARCHAR(MAX), @query2 as VARCHAR(MAX)
select DISTINCT  ciclosName into #tempColname from metrics inner join ciclos on  metrics.ciclos_fk = ciclos.idCiclos inner join version on ciclos.version_fk = @idVersion; 
 
 set @Columns = STUFF((select ',' + QUOTENAME(t.ciclosName) from #tempColname t for xml path (''), type).value('.','NVARCHAR(MAX)'),1,1,'')



 set @query = '

select metricName, '+ @Columns+' from(
select metricName, metricValue, ciclosName from ciclos inner join metrics on ciclos.idCiclos = metrics.ciclos_fk ) rest
PIVOT (AVG(metricValue) for ciclosName in ( '+ @Columns+')) aux'

 set @query2 = '

select metricName, '+ @Columns+' from(
select metricName, metricValue, ciclosName from metrics inner join ciclos on  metrics.ciclos_fk = ciclos.idCiclos inner join version on ciclos.version_fk = '+@idVersion+') rest
PIVOT (AVG(metricValue) for ciclosName in ( '+ @Columns+')) aux'

execute (@query2)
drop table #tempColname; 
END
GO
USE [master]
GO
ALTER DATABASE [comss] SET  READ_WRITE 
GO
