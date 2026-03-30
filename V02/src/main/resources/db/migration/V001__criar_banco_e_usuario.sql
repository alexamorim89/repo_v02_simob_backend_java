-- Criar banco para versão V01
CREATE DATABASE simob_v01
    WITH OWNER = usuario_v01
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Criar banco para versão V02
CREATE DATABASE simob_v02
    WITH OWNER = usuario_v02
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Opcional: criar usuários separados para cada versão
CREATE USER usuario_v01 WITH ENCRYPTED PASSWORD 'senha_v01';
CREATE USER usuario_v02 WITH ENCRYPTED PASSWORD 'senha_v02';

-- Conceder permissões
GRANT ALL PRIVILEGES ON DATABASE simob_v01 TO usuario_v01;
GRANT ALL PRIVILEGES ON DATABASE simob_v02 TO usuario_v02;