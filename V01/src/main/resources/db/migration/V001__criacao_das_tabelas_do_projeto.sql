CREATE TABLE atendimento (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_atend uuid UNIQUE NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    nome_cliente varchar(50) NOT NULL,
    telefone_cliente varchar(20) NOT NULL,
    email_cliente text NULL,
    fk_usuario_codigo int
);

CREATE TABLE atendimento_cliente (
    fk_atendimento_codigo int NULL,
    fk_cliente_codigo int NULL
);

CREATE TABLE imovel (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_imov uuid NOT NULL UNIQUE,
    nome varchar(200) NOT NULL,
    quantidade_quarto int NOT NULL,
    quantidade_banheiro int NOT NULL,
    quantidade_suite int NOT NULL,
    quantidade_garagem int NOT NULL,
    area_total varchar(30) NOT NULL,
    descricao text NOT NULL,
    tipo_imovel varchar(20) NOT NULL,
    tipo_venda varchar(20) NOT NULL,
    status varchar(20) NOT NULL,
    valor NUMERIC NOT NULL,
    valor_mensal NUMERIC NOT NULL,
    tem_area_servico BOOLEAN NOT NULL,
    tem_armario_cozinha BOOLEAN NOT NULL,
    tem_armario_quarto BOOLEAN NOT NULL,
    tem_armario_projetado BOOLEAN NOT NULL,
    tem_box_banheiro BOOLEAN NOT NULL,
    tem_ceramica BOOLEAN NOT NULL,
    tem_cercado BOOLEAN NOT NULL,
    tem_closet BOOLEAN NOT NULL,
    tem_cobertura BOOLEAN NOT NULL,
    tem_condominio_fechado BOOLEAN NOT NULL,
    tem_conjugada BOOLEAN NOT NULL,
    tem_copa BOOLEAN NOT NULL,
    tem_corredor BOOLEAN NOT NULL,
    tem_cozinha BOOLEAN NOT NULL,
    tem_cozinha_americana BOOLEAN NOT NULL,
    tem_despensa BOOLEAN NOT NULL,
    tem_escritorio BOOLEAN NOT NULL,
    tem_esquadria BOOLEAN NOT NULL,
    tem_gradeado BOOLEAN NOT NULL,
    fk_proprietario_codigo int,
    fk_financeiro_codigo int,
    fk_endereco_codigo int
);

CREATE TABLE imovel_imagens (
    codigo SERIAL NOT NULL PRIMARY KEY,
    nome varchar(200) NULL,
    foto bytea NULL,
    fk_imovel_codigo int
);

CREATE TABLE imovel_endereco (
    codigo SERIAL NOT NULL PRIMARY KEY,
    rua varchar(100) NOT NULL,
    numero varchar(20) NOT NULL,
    complemento varchar(50) NULL,
    bairro varchar(30) NOT NULL,
    cidade varchar(40) NOT NULL,
    estado varchar(20) NOT NULL,
    cep varchar(15) NOT NULL
);

CREATE TABLE proprietario (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_prop uuid NOT NULL,
    nome varchar(50) NOT NULL,
    email text NULL,
    telefone1 varchar(20) NOT NULL,
    telefone2 varchar(20) NULL,
    RG varchar(15) NULL,
    CPF varchar(16) NULL,
    data_nascimento DATE NULL,
    CNPJ varchar(20) NULL,
    inscricao_estadual varchar(20) NULL,
    razao_estadual varchar(80) NULL,
    proprietario_tipo varchar(20) NOT NULL,
    UNIQUE (RG, CPF, CNPJ, inscricao_estadual, codigo_simob_prop)
);

CREATE TABLE agendamento (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_ag uuid UNIQUE NOT NULL,
    nome_cliente varchar(50) NOT NULL,
    telefone_cliente varchar(20) NOT NULL,
    email_cliente text NULL,
    data DATE NOT NULL,
    hora varchar(30) NOT NULL,
    informacoes text NULL,
    fk_atendimento_codigo int,
    fk_visita_codigo int
);

CREATE TABLE visita (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_vi uuid UNIQUE NOT NULL,
    nome_cliente varchar(50) NOT NULL,
    telefone_cliente varchar(20) NOT NULL,
    email_cliente text NULL,
    data DATE NOT NULL,
    hora varchar(30) NOT NULL,
    fk_anotacao_codigo int
);

CREATE TABLE anotacao (
    codigo SERIAL NOT NULL PRIMARY KEY,
    descricao text NULL
);

CREATE TABLE cliente (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_cli uuid NOT NULL,
    nome varchar(50) NOT NULL,
    email text NULL,
    telefone1 varchar(20) NOT NULL,
    telefone2 varchar(20) NULL,
    RG varchar(15) NULL,
    CPF varchar(16) NULL,
    data_nascimento DATE NULL,
    CNPJ varchar(20) NULL,
    inscricao_estadual varchar(20) NULL,
    razao_social varchar(50) NULL,
    cliente_tipo varchar(20) NOT NULL,
    UNIQUE (CPF, RG, CNPJ, inscricao_estadual, codigo_simob_cli)
);

CREATE TABLE cliente_endereco (
    codigo SERIAL NOT NULL PRIMARY KEY,
    rua varchar(100) NOT NULL,
    numero varchar(20) NOT NULL,
    complemento varchar(50) NULL,
    bairro varchar(50) NOT NULL,
    cidade varchar(50) NOT NULL,
    estado varchar(20) NOT NULL,
    cep varchar(15) NOT NULL,
    fk_cliente_codigo int
);

CREATE TABLE usuario (
    codigo SERIAL NOT NULL PRIMARY KEY,
    matricula varchar(50) NOT NULL,
    nome varchar(60) NOT NULL,
    email text NOT NULL,
    CPF varchar(16) NOT NULL,
    RG varchar(15) NOT NULL,
    CRECI varchar(20) NULL,
    senha text NOT NULL,
    ativo boolean NOT NULL,
    fk_usuario_endereco_codigo int NOT NULL,
    UNIQUE (matricula, CPF, RG, CRECI, email)
);

CREATE TABLE usuario_endereco (
    codigo SERIAL NOT NULL PRIMARY KEY,
    rua varchar(100) NOT NULL,
    numero int  NULL,
    complemento varchar(50) NULL,
    cep varchar(15) NOT NULL,
    bairro varchar(30) NOT NULL,
    cidade varchar(40) NOT NULL,
    estado varchar(20) NOT NULL
);

CREATE TABLE usuario_perfil (
    fk_usuario_codigo int NULL,
    fk_perfil_codigo int NULL
);

CREATE TABLE perfil (
    codigo SERIAL NOT NULL PRIMARY KEY,
    tipo varchar(20) NOT NULL
);

CREATE TABLE imovel_atribuido_usuario (
    fk_usuario_codigo int NULL,
    fk_Imovel_codigo int NULL
);

CREATE TABLE financeiro (
    codigo SERIAL NOT NULL PRIMARY KEY,
    codigo_simob_fin uuid UNIQUE NOT NULL,
    valor NUMERIC NOT NULL,
    valor_mensal NUMERIC NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    situacao varchar(20) NOT NULL,
    fk_atendimento_codigo int,
    fk_cliente_codigo int
);

ALTER TABLE imovel ADD CONSTRAINT FK_imovel_2 FOREIGN KEY (fk_proprietario_codigo) REFERENCES proprietario (codigo) ON DELETE RESTRICT;
ALTER TABLE imovel ADD CONSTRAINT FK_imovel_3 FOREIGN KEY (fk_financeiro_codigo) REFERENCES financeiro (codigo) ON DELETE RESTRICT;
ALTER TABLE imovel ADD CONSTRAINT FK_imovel_4 FOREIGN KEY (fk_endereco_codigo) REFERENCES imovel_endereco (codigo);

ALTER TABLE atendimento ADD CONSTRAINT FK_atendimento_2 FOREIGN KEY (fk_usuario_codigo) REFERENCES usuario (codigo) ON DELETE RESTRICT;

ALTER TABLE usuario ADD CONSTRAINT FK_usuario_2 FOREIGN KEY (fk_usuario_endereco_codigo) REFERENCES usuario_endereco (codigo);

ALTER TABLE agendamento ADD CONSTRAINT FK_agendamento_2 FOREIGN KEY (fk_atendimento_codigo) REFERENCES atendimento (codigo) ON DELETE RESTRICT;
ALTER TABLE agendamento ADD CONSTRAINT FK_agendamento_3 FOREIGN KEY (fk_visita_codigo) REFERENCES visita (codigo);

ALTER TABLE visita ADD CONSTRAINT FK_visita_2 FOREIGN KEY (fk_anotacao_codigo) REFERENCES anotacao (codigo);

ALTER TABLE financeiro ADD CONSTRAINT FK_financeiro_2 FOREIGN KEY (fk_atendimento_codigo) REFERENCES atendimento (codigo) ON DELETE RESTRICT;
ALTER TABLE financeiro ADD CONSTRAINT FK_financeiro_3 FOREIGN KEY (fk_cliente_codigo) REFERENCES cliente (codigo) ON DELETE RESTRICT;

ALTER TABLE cliente_endereco ADD CONSTRAINT FK_cliente_endereco_2 FOREIGN KEY (fk_cliente_codigo) REFERENCES cliente (codigo) ON DELETE RESTRICT;

ALTER TABLE usuario_perfil ADD CONSTRAINT FK_usuario_perfil_1 FOREIGN KEY (fk_usuario_codigo) REFERENCES usuario (codigo) ON DELETE RESTRICT;
ALTER TABLE usuario_perfil ADD CONSTRAINT FK_usuario_perfil_2 FOREIGN KEY (fk_perfil_codigo) REFERENCES perfil (codigo) ON DELETE RESTRICT;

ALTER TABLE imovel_atribuido_usuario ADD CONSTRAINT FK_imovel_atribuido_usuario_1 FOREIGN KEY (fk_usuario_codigo) REFERENCES usuario (codigo) ON DELETE RESTRICT;
ALTER TABLE imovel_atribuido_usuario ADD CONSTRAINT FK_imovel_atribuido_usuario_2 FOREIGN KEY (fk_Imovel_codigo) REFERENCES imovel (codigo) ON DELETE RESTRICT;

ALTER TABLE imovel_imagens ADD CONSTRAINT FK_imovel_imagens_2 FOREIGN KEY (fk_imovel_codigo) REFERENCES imovel (codigo);
 
ALTER TABLE atendimento_cliente ADD CONSTRAINT FK_atendimento_cliente_1 FOREIGN KEY (fk_atendimento_codigo) REFERENCES atendimento (codigo);
ALTER TABLE atendimento_cliente ADD CONSTRAINT FK_atendimento_cliente_2 FOREIGN KEY (fk_cliente_codigo) REFERENCES cliente (codigo);