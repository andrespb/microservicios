-- Conectar al PDB
ALTER SESSION SET CONTAINER = BANKINGDB;

-- Crear usuario para la aplicación bancaria
CREATE USER banking_user IDENTIFIED BY banking_password;

-- Otorgar permisos necesarios
GRANT CONNECT, RESOURCE TO banking_user;
GRANT CREATE SESSION TO banking_user;
GRANT CREATE TABLE TO banking_user;
GRANT CREATE SEQUENCE TO banking_user;
GRANT UNLIMITED TABLESPACE TO banking_user;
GRANT DBA TO banking_user;

-- Conectarse como el usuario de la aplicación
CONNECT banking_user/banking_password@BANKINGDB;

-- Crear secuencias
CREATE SEQUENCE PERSONA_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE CUENTA_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE MOVIMIENTO_SEQ START WITH 1 INCREMENT BY 1;

-- Commit de los cambios
COMMIT;