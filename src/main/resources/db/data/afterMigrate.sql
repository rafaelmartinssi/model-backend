set foreign_key_checks = 0;

delete from usuario;
delete from grupo;
delete from permissao;
delete from grupo_permissao;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;

insert into permissao (id, nome, descricao) values (1, 'CREATE_USER', 'Permite cadastrar usuário');
insert into permissao (id, nome, descricao) values (2, 'EDIT_USER', 'Permite editar usuário');

insert into grupo (id, nome) values (1, 'Admin');

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2);

insert into usuario (id, nome, email, senha) values
(1, 'João da Silva', 'email1@gmail.com.br', '$2a$10$GOP888TBozG5knqJp1iGDuahz6WduZlcWJ3Gw.9vX7AVGFjoooWTq'),
(2, 'Maria de Jesus', 'email2@gmail.com.br', '$2a$10$GOP888TBozG5knqJp1iGDuahz6WduZlcWJ3Gw.9vX7AVGFjoooWTq'),
(3, 'José de Souza', 'email3@gmail.com.br', '$2a$10$GOP888TBozG5knqJp1iGDuahz6WduZlcWJ3Gw.9vX7AVGFjoooWTq');

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1);