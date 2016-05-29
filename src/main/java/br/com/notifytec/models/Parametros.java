package br.com.notifytec.models;

import java.util.UUID;

public class Parametros {

    public class Tabelas {

        public static final String TABELA_ALUNO = "ALUNO";
        public static final String TABELA_ALUNO_NOTIFICACAO = "ALUNONOTIFICACAO";
        public static final String TABELA_ALUNO_PERIODO = "ALUNOPERIODO";
        public static final String TABELA_CURSO = "CURSO";
        public static final String TABELA_DEPARTAMENTO = "DEPARTAMENTO";
        public static final String TABELA_NOTIFICACAO = "NOTIFICACAO";
        public static final String TABELA_NOTIFICACAO_OPCAO = "NOTIFICACAOOPCAO";
        public static final String TABELA_PERIODO = "PERIODO";
        public static final String TABELA_PESSOA = "PESSOA";
        public static final String TABELA_SEMESTRE = "SEMESTRE";
        public static final String TABELA_USUARIO = "USUARIO";
    }

    public static final UUID emptyUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
}
