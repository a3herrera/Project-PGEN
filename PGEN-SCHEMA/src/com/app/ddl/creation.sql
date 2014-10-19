/*
      ACCESOS
*/
CREATE TABLE ACCESOS(
      ID_ACCESO               NUMBER(19)                          NOT NULL,
      ID_ACCESO_PADRE         NUMBER(19),
      ES_PADRE                NUMBER(1)         DEFAULT 0         NOT NULL,
      ESTADO                  NUMBER(1)         DEFAULT 0         NOT NULL,
      TITULO                  VARCHAR2(25)                        NOT NULL,
      URL                     VARCHAR2(255),
      REDIRECCIONAMIENTO      NUMBER(1)         DEFAULT 0         NOT NULL,
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      PRIMARY KEY (ID_ACCESO)
);

ALTER TABLE ACCESOS 
    ADD CONSTRAINT FK_TBL_ACC_ID_ACC_PADRE 
    FOREIGN KEY (ID_ACCESO_PADRE)
    REFERENCES ACCESOS (ID_ACCESO);
   
ALTER TABLE ACCESOS 
    ADD CONSTRAINT CK_TBL_ACC_VALORES 
    CHECK ((ES_PADRE BETWEEN 0 AND 1) and (ESTADO BETWEEN 0 AND 1) AND (REDIRECCIONAMIENTO BETWEEN 0 AND 1));
    
CREATE SEQUENCE SEQ_ACCESO_ID 
      START WITH 20
      INCREMENT BY 1;
      
/*
    GRUPOS
*/      
CREATE TABLE GRUPOS(
      ID_GRUPO                NUMBER(19)                          NOT NULL,
      NOMBRE                  VARCHAR2(50)                        NOT NULL,
      DESCRIPCION             CLOB              DEFAULT EMPTY_CLOB()  NOT NULL,
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      PRIMARY KEY (ID_GRUPO)
);

ALTER TABLE GRUPOS 
    ADD CONSTRAINT CNN_UN_GRUPOS_NOMBRE 
    UNIQUE (NOMBRE);    

  
CREATE SEQUENCE SEQ_GRUPO_ID
      START WITH 20
      INCREMENT BY 1;

/*
    GRUPOS ACCESOS
*/
      
CREATE TABLE GRUPO_ACCESOS (
      ID_GRP_ACC              NUMBER(19)                          NOT NULL, 
      ID_ACCESO               NUMBER(19)                          NOT NULL, 
      ID_GRUPO                NUMBER(19)                          NOT NULL, 
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      PRIMARY KEY (ID_GRP_ACC)
);

ALTER TABLE GRUPO_ACCESOS 
    ADD CONSTRAINT FK_GRUPO_ACCESOS_GRUPO 
    FOREIGN KEY (ID_GRUPO) 
    REFERENCES GRUPOS (ID_GRUPO);
    
ALTER TABLE GRUPO_ACCESOS 
    ADD CONSTRAINT FK_GRUPO_ACCESOS_ACCESO 
    FOREIGN KEY (ID_ACCESO) 
    REFERENCES ACCESOS (ID_ACCESO);

CREATE INDEX IDX_GRUPO_ACCESOS 
    ON GRUPO_ACCESOS (ID_GRUPO, ID_ACCESO);
    
CREATE SEQUENCE SEQ_GRP_ACC_ID 
    START WITH 20
    INCREMENT BY 1;

/*
    PERFILES
*/
CREATE TABLE PERFILES(
      ID_PERFIL               NUMBER(19)                          NOT NULL,
      ID_GRUPO                NUMBER(19)                          NOT NULL, 
      NOMBRE                  VARCHAR2(50)                        NOT NULL,
      ESTADO				  NUMBER(1,0)		DEFAULT 0		  NOT NULL,
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      PRIMARY KEY (ID_PERFIL)
);

ALTER TABLE PERFILES 
    ADD CONSTRAINT CK_TBL_PRF_ESTADO 
    CHECK (ESTADO BETWEEN 0 AND 1);

ALTER TABLE PERFILES 
    ADD CONSTRAINT FK_PERFILES_ID_GRUPO 
    FOREIGN KEY (ID_GRUPO) 
    REFERENCES GRUPOS (ID_GRUPO);
    
CREATE SEQUENCE SEQ_PERFIL_ID 
    START WITH 20
    INCREMENT BY 1; 

/*
    PERFIL ACCESOS
*/

CREATE TABLE PERFIL_ACCESOS (
      ID_PRF_ACC              NUMBER(19)                          NOT NULL, 
      ID_ACCESO               NUMBER(19)                          NOT NULL, 
      ID_PERFIL               NUMBER(19)                          NOT NULL, 
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      ESTADO				  NUMBER(1)			DEFAULT 0		  NOT NULL,
      PRIMARY KEY (ID_PRF_ACC)
);

ALTER TABLE PERFIL_ACCESOS 
    ADD CONSTRAINT FK_PERFIL_ACCESOS_PERFIL 
    FOREIGN KEY (ID_PERFIL) 
    REFERENCES PERFILES (ID_PERFIL);
    
ALTER TABLE PERFIL_ACCESOS 
    ADD CONSTRAINT FK_PERFIL_ACCESOS_ACCESO 
    FOREIGN KEY (ID_ACCESO) 
    REFERENCES ACCESOS (ID_ACCESO);
  
CREATE SEQUENCE SEQ_PRF_ACC_ID 
    START WITH 20
    INCREMENT BY 1;
    
/*
    ACCESOS ACCIONES    
 */
 
CREATE TABLE PERFIL_ACCIONES (
      PERFIL_ID               NUMBER(19)                          NOT NULL, 
      ACCION                  VARCHAR2(20)                       NOT NULL
);

ALTER TABLE PERFIL_ACCIONES 
    ADD CONSTRAINT FK_PERFIL_ACCIONES_PERFIL 
    FOREIGN KEY (PERFIL_ID) REFERENCES PERFIL_ACCESOS (ID_PRF_ACC);
/*
  USUARIOS
*/
CREATE TABLE USUARIOS (
      ID_USUARIO              NUMBER(19)                          NOT NULL, 
      ID_PERFIL               NUMBER(19)                          NOT NULL, 
      USUARIO                 VARCHAR2(255)                       NOT NULL, 
      CONTRASENA              VARCHAR2(255)                       NOT NULL, 
      ESTADO                  VARCHAR2(255)                       NOT NULL, 
      REG_CREACION            TIMESTAMP         DEFAULT SYSDATE   NOT NULL,
      REG_ULT_MODIFICACION    TIMESTAMP,
      VERSION                 NUMBER(19),
      PRIMARY KEY (ID_USUARIO)
);

ALTER TABLE USUARIOS 
    ADD CONSTRAINT CNN_UN_USARIO_NOMBRE 
    UNIQUE (USUARIO);

ALTER TABLE USUARIOS 
    ADD CONSTRAINT FK_USUARIOS_ID_PERFIL 
    FOREIGN KEY (ID_PERFIL) 
    REFERENCES PERFILES (ID_PERFIL);
    
CREATE SEQUENCE SEQ_USUARIOS_ID 
    START WITH 20
    INCREMENT BY 1;