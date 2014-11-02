CREATE TABLE ACCESOS (ID_ACCESO NUMBER(19) NOT NULL, ES_PADRE NUMBER(1) default 0 NOT NULL, ESTADO NUMBER(1) default 0 NOT NULL, REDIRECCIONAMIENTO NUMBER(1) default 0 NOT NULL, TITULO VARCHAR2(25) NOT NULL, URL VARCHAR2(255) NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_ACCESO_PADRE NUMBER(19) NULL, PRIMARY KEY (ID_ACCESO))
CREATE TABLE GRUPO_ACCESOS (ID_GRP_ACC NUMBER(19) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_ACCESO NUMBER(19) NOT NULL, ID_GRUPO NUMBER(19) NOT NULL, PRIMARY KEY (ID_GRP_ACC))
CREATE INDEX IDX_GRUPO_ACCESOS ON GRUPO_ACCESOS (ID_GRUPO, ID_ACCESO)
CREATE TABLE PERFIL_ACCESOS (ID_PRF_ACC NUMBER(19) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_ACCESO NUMBER(19) NOT NULL, ID_PERFIL NUMBER(19) NOT NULL, PRIMARY KEY (ID_PRF_ACC))
CREATE TABLE GRUPOS (ID_GRUPO NUMBER(19) NOT NULL, DESCRIPCION CLOB NOT NULL, NOMBRE VARCHAR2(50) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, PRIMARY KEY (ID_GRUPO))
CREATE INDEX IDX_GRUPOS_NOMBRE ON GRUPOS (NOMBRE)
CREATE TABLE PERFILES (ID_PERFIL NUMBER(19) NOT NULL, ESTADO NUMBER(1) default 0 NOT NULL, NOMBRE VARCHAR2(50) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_GRUPO NUMBER(19) NOT NULL, PRIMARY KEY (ID_PERFIL))
CREATE TABLE USUARIOS (ID_USUARIO NUMBER(19) NOT NULL, CONTRASENA VARCHAR2(255) NOT NULL, ESTADO VARCHAR2(255) NOT NULL, USUARIO VARCHAR2(255) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_PERFIL NUMBER(19) NOT NULL, ID_PERSONA NUMBER(19) NULL, PRIMARY KEY (ID_USUARIO))
CREATE TABLE CATG_MONEDAS (ID_MONEDA NUMBER(19) NOT NULL, ABREVIATURA VARCHAR2(255) NULL, ESTADO NUMBER(1) default 0 NULL, MONEDA VARCHAR2(255) NOT NULL, SIMBOLO VARCHAR2(255) NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, PRIMARY KEY (ID_MONEDA))
CREATE TABLE CATG_DOCUMENTOS (ID_DOCUMENTO NUMBER(19) NOT NULL, DESCRIPCION CLOB NULL, ESTADO NUMBER(1) default 0 NULL, FORMATO VARCHAR2(50) NULL, NOMBRE VARCHAR2(25) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, PRIMARY KEY (ID_DOCUMENTO))
CREATE TABLE INFO_PERSONAS (ID_PERSONA NUMBER(19) NOT NULL, TYPE_ VARCHAR2(31) NULL, FECHA_NACIMIENTO DATE NULL, PRIMER_APELLIDO VARCHAR2(25) NULL, PRIMER_NOMBRE VARCHAR2(25) NOT NULL, SEGUNDO_APELLIDO VARCHAR2(25) NULL, SEGUNDO_NOMBRE VARCHAR2(25) NULL, TERCER_NOMBRE VARCHAR2(25) NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, PRIMARY KEY (ID_PERSONA))
CREATE TABLE BANCOS (ID_BANCO NUMBER(19) NOT NULL, ESTADO NUMBER(1) default 0 NULL, NOMBRE VARCHAR2(50) NOT NULL, PAGINA_WEB VARCHAR2(50) NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, PRIMARY KEY (ID_BANCO))
CREATE TABLE CHEQUERAS (ID_CHEQUERA NUMBER(19) NOT NULL, CODIGO VARCHAR2(255) NOT NULL, ESTADO VARCHAR2(20) NOT NULL, FECHA_AUTORIZACION DATE NOT NULL, FECHA_BAJA DATE NULL, NUMERACION_FINAL NUMBER(19) NOT NULL, NUMERACION_INICIAL NUMBER(19) NOT NULL, TOTAL_CHEQUES NUMBER(10) NOT NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_CUENTA NUMBER(19) NOT NULL, PRIMARY KEY (ID_CHEQUERA))
CREATE TABLE CUENTAS (ID_CUENTA NUMBER(19) NOT NULL, FECHA_APERTURA TIMESTAMP NULL, CUENTA_NO VARCHAR2(255) NULL, SALDO NUMBER(20,4) NULL, TIPO VARCHAR2(255) NULL, VERSION NUMBER(19) NULL, REG_CREACION TIMESTAMP NULL, REG_ULT_MODIFICACION TIMESTAMP NULL, ID_BANCO NUMBER(19) NOT NULL, MONEDA NUMBER(19) NOT NULL, PRIMARY KEY (ID_CUENTA))
CREATE TABLE PERFIL_ACCIONES (PERFIL_ID NUMBER(19) NULL, ACCION VARCHAR2(20) NOT NULL)
ALTER TABLE GRUPOS ADD CONSTRAINT CNN_UN_GRUPOS_NOMBRE UNIQUE (NOMBRE)
ALTER TABLE USUARIOS ADD CONSTRAINT CNN_UN_USARIO_NOMBRE UNIQUE (USUARIO)
ALTER TABLE ACCESOS ADD CONSTRAINT FK_ACCESOS_ID_ACCESO_PADRE FOREIGN KEY (ID_ACCESO_PADRE) REFERENCES ACCESOS (ID_ACCESO)
ALTER TABLE GRUPO_ACCESOS ADD CONSTRAINT FK_GRUPO_ACCESOS_ID_GRUPO FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
ALTER TABLE GRUPO_ACCESOS ADD CONSTRAINT FK_GRUPO_ACCESOS_ID_ACCESO FOREIGN KEY (ID_ACCESO) REFERENCES ACCESOS (ID_ACCESO)
ALTER TABLE PERFIL_ACCESOS ADD CONSTRAINT FK_PERFIL_ACCESOS_ID_PERFIL FOREIGN KEY (ID_PERFIL) REFERENCES PERFILES (ID_PERFIL)
ALTER TABLE PERFIL_ACCESOS ADD CONSTRAINT FK_PERFIL_ACCESOS_ID_ACCESO FOREIGN KEY (ID_ACCESO) REFERENCES ACCESOS (ID_ACCESO)
ALTER TABLE PERFILES ADD CONSTRAINT FK_PERFILES_ID_GRUPO FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
ALTER TABLE USUARIOS ADD CONSTRAINT FK_USUARIOS_ID_PERFIL FOREIGN KEY (ID_PERFIL) REFERENCES PERFILES (ID_PERFIL)
ALTER TABLE USUARIOS ADD CONSTRAINT FK_USUARIOS_ID_PERSONA FOREIGN KEY (ID_PERSONA) REFERENCES INFO_PERSONAS (ID_PERSONA)
ALTER TABLE CHEQUERAS ADD CONSTRAINT FK_CHEQUERAS_ID_CUENTA FOREIGN KEY (ID_CUENTA) REFERENCES CUENTAS (ID_CUENTA)
ALTER TABLE CUENTAS ADD CONSTRAINT FK_CUENTAS_MONEDA FOREIGN KEY (MONEDA) REFERENCES CATG_MONEDAS (ID_MONEDA)
ALTER TABLE CUENTAS ADD CONSTRAINT FK_CUENTAS_ID_BANCO FOREIGN KEY (ID_BANCO) REFERENCES BANCOS (ID_BANCO)
ALTER TABLE PERFIL_ACCIONES ADD CONSTRAINT FK_PERFIL_ACCIONES_PERFIL_ID FOREIGN KEY (PERFIL_ID) REFERENCES PERFIL_ACCESOS (ID_PRF_ACC)
CREATE SEQUENCE seq_banco_id INCREMENT BY 50 START WITH 50
CREATE SEQUENCE SEQ_ACCESO_ID START WITH 20
CREATE SEQUENCE seq_catg_monedas START WITH 20
CREATE SEQUENCE SEQ_PERFIL_ID START WITH 20
CREATE SEQUENCE SEQ_PRF_ACC_ID START WITH 20
CREATE SEQUENCE seq_catg_documentos START WITH 20
CREATE SEQUENCE SEQ_GRUPO_ID START WITH 20
CREATE SEQUENCE SEQ_CUENTA_ID INCREMENT BY 50 START WITH 50
CREATE SEQUENCE SEQ_PERSONA_ID START WITH 20
CREATE SEQUENCE SEQ_CHEQUERA_ID INCREMENT BY 50 START WITH 50
CREATE SEQUENCE SEQ_GRP_ACC_ID START WITH 20
CREATE SEQUENCE SEQ_USUARIOS_ID START WITH 20
