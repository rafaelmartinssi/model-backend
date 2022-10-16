create table grupo (
	id bigint not null auto_increment, 
	nome varchar(255), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao (
	grupo_id bigint not null, 
	permissao_id bigint not null, 
	primary key (grupo_id, permissao_id)
) engine=InnoDB default charset=utf8;

create table permissao (
	id bigint not null auto_increment, 
	descricao varchar(255), 
	nome varchar(255), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario (
	id bigint not null auto_increment, 
	email varchar(255), nome varchar(255), 
	senha varchar(255), primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario_grupo (
	usuario_id bigint not null, 
	grupo_id bigint not null, 
	primary key (usuario_id, grupo_id)
) engine=InnoDB default charset=utf8;

alter table grupo_permissao add constraint fk_grupo_permissao_permissao
foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint fk_grupo_permissao_grupo
foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint fk_usuario_grupo_grupo
foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint fk_usuario_grupo_usuario
foreign key (usuario_id) references usuario (id);