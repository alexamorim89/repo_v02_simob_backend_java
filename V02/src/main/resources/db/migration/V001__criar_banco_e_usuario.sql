-- Criar banco para versão V01
DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'simob_v01'
   ) THEN
      CREATE DATABASE simob_v01
         WITH OWNER = usuario_v01
         ENCODING = 'UTF8'
         LC_COLLATE = 'pt_BR.UTF-8'
         LC_CTYPE = 'pt_BR.UTF-8'
         TABLESPACE = pg_default
         CONNECTION LIMIT = -1;
   END IF;
END
$$;


-- Criar banco para versão V02
DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'simob_v02'
   ) THEN
      CREATE DATABASE simob_v02
         WITH OWNER = usuario_v02
         ENCODING = 'UTF8'
         LC_COLLATE = 'pt_BR.UTF-8'
         LC_CTYPE = 'pt_BR.UTF-8'
         TABLESPACE = pg_default
         CONNECTION LIMIT = -1;
   END IF;
END
$$;

-- Opcional: criar usuários separados para cada versão
DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles WHERE rolname = 'usuario_v01'
   ) THEN
      CREATE ROLE usuario_v01 LOGIN PASSWORD 'senha_v01';
   END IF;
END
$$;

DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles WHERE rolname = 'usuario_v02'
   ) THEN
      CREATE ROLE usuario_v02 LOGIN PASSWORD 'senha_v02';
   END IF;
END
$$;

-- Conceder permissões
GRANT ALL PRIVILEGES ON DATABASE simob_v01 TO usuario_v01;
GRANT ALL PRIVILEGES ON DATABASE simob_v02 TO usuario_v02;