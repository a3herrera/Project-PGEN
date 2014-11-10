--Debitos
create or replace TRIGGER CHK_MOVIMIENTOS_DEBITOS  
  BEFORE INSERT ON DEBITOS
  REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW
  
  DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
  V_SALDO_ACTUAL        NUMBER(18,2);
  CONTEO_CUENTA         NUMBER(4);
  BEGIN
      BEGIN
        SELECT COUNT(*) INTO CONTEO_CUENTA FROM CUENTAS WHERE ID_CUENTA = :NEW.CUENTA_NO;
        IF CONTEO_CUENTA = 0 THEN
          :NEW.MENSAJE := 'No Cuenta no valido';
        ELSE 
          :NEW.MENSAJE := 'PROCESADO CON EXITO';
          :NEW.ESTADO := 1;
          UPDATE CUENTAS SET SALDO = SALDO + :NEW.MONTO WHERE ID_CUENTA = :NEW.CUENTA_NO;
        END IF;
      END;
      COMMIT;
  END;
  
  
--CREDITOS
  
  
create or replace TRIGGER CHK_MOVIMIENTOS_CREDITOS 
  BEFORE INSERT ON CREDITOS
  REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW
  
  DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
  V_SALDO_ACTUAL        NUMBER(18,2);
  CONTEO_CUENTA         NUMBER(4);
  BEGIN
      BEGIN
        SELECT COUNT(*) INTO CONTEO_CUENTA FROM CUENTAS WHERE ID_CUENTA = :NEW.CUENTA_NO;
        IF CONTEO_CUENTA = 0 THEN
          :NEW.MENSAJE := 'No_Cuenta no valido';
          :NEW.ESTADO := 1;
        ELSE 
          IF :NEW.MONTO <= 0 THEN
            :NEW.MENSAJE := 'MONTO NO ES CORRECTO';    
            :NEW.ESTADO := 1;
          END IF;
          IF :NEW.MONTO >0 THEN
              SELECT SALDO INTO V_SALDO_ACTUAL FROM CUENTAS WHERE ID_CUENTA = :NEW.CUENTA_NO;
              IF V_SALDO_ACTUAL < :NEW.MONTO THEN
                :NEW.MENSAJE := 'SALDO INSUFICIENTE';
                :NEW.ESTADO := 1;  
              END IF;
              
              IF (V_SALDO_ACTUAL >= :NEW.MONTO ) THEN
                :NEW.MENSAJE := 'PROCESADO CON EXITO';
                :NEW.ESTADO := 1;
                UPDATE CUENTAS SET SALDO = (SALDO - :NEW.MONTO) WHERE ID_CUENTA = :NEW.CUENTA_NO;
                commit;
              END IF;
          END IF;
        END IF;
      END;
  END;