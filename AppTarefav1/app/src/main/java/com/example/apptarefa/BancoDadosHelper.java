package com.example.apptarefa;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDadosHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "tarefas.db";
    private static final int VERSAO_BANCO = 2; // Atualize a versão do banco de dados
    private static final String TABELA_TAREFAS = "tarefas";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_TITULO = "titulo";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_DATA_TERMINO = "data_termino";
    private static final String COLUNA_COMPLETA = "completa"; // Nova coluna

    public BancoDadosHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarTabela = "CREATE TABLE " + TABELA_TAREFAS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_TITULO + " TEXT, " +
                COLUNA_DESCRICAO + " TEXT, " +
                COLUNA_DATA_TERMINO + " TEXT, " +
                COLUNA_COMPLETA + " INTEGER DEFAULT 0)"; // Nova coluna
        db.execSQL(criarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABELA_TAREFAS + " ADD COLUMN " + COLUNA_COMPLETA + " INTEGER DEFAULT 0");
        }
    }

    public Cursor obterTodasTarefas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABELA_TAREFAS, null);
    }

    public boolean adicionarTarefa(String titulo, String descricao, String dataTermino) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TITULO, titulo);
        valores.put(COLUNA_DESCRICAO, descricao);
        valores.put(COLUNA_DATA_TERMINO, dataTermino);
        valores.put(COLUNA_COMPLETA, 0); // Tarefa inicialmente incompleta
        long resultado = db.insert(TABELA_TAREFAS, null, valores);
        return resultado != -1;
    }

    public int obterIdTarefa(String titulo, String descricao, String dataTermino) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUNA_ID + " FROM " + TABELA_TAREFAS + " WHERE " +
                        COLUNA_TITULO + "=? AND " + COLUNA_DESCRICAO + "=? AND " + COLUNA_DATA_TERMINO + "=?",
                new String[]{titulo, descricao, dataTermino});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        } else {
            cursor.close();
            return -1;
        }
    }

    public boolean atualizarTarefa(int id, String titulo, String descricao, String dataTermino) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TITULO, titulo);
        valores.put(COLUNA_DESCRICAO, descricao);
        valores.put(COLUNA_DATA_TERMINO, dataTermino);
        int resultado = db.update(TABELA_TAREFAS, valores, COLUNA_ID + "=?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public boolean excluirTarefa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete(TABELA_TAREFAS, COLUNA_ID + "=?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public boolean marcarTarefaComoConcluida(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_COMPLETA, 1);
        int resultado = db.update(TABELA_TAREFAS, valores, COLUNA_ID + "=?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public Cursor obterTarefaPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABELA_TAREFAS + " WHERE " + COLUNA_ID + "=?", new String[]{String.valueOf(id)});
    }







    // LOGIN E CADASTRO ------------------

        private static final String DATABASE_NAME = "tarefas.db";
        private static final int DATABASE_VERSION = 1;

        public void onCreateUsuarios (SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT, senha TEXT)");
            // Outras tabelas (tarefas, etc.)
        }

        // Metodo para cadastrar um usuário
        public boolean cadastrarUsuario(String nome, String email, String senha) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nome", nome);
            values.put("email", email);
            values.put("senha", senha);

            long resultado = db.insert("usuarios", null, values);
            return resultado != -1; // Retorna true se o cadastro foi bem-sucedido
        }

        // Metodo para verificar login
        public boolean verificarUsuario(String email, String senha) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM usuarios WHERE email = ? AND senha = ?",
                    new String[]{email, senha}
            );

            boolean existe = cursor.moveToFirst();
            cursor.close();
            return existe; // Retorna true se as credenciais forem válidas
        }
    }


//