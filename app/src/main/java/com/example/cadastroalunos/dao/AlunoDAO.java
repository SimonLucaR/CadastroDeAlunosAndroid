package com.example.cadastroalunos.dao;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.cadastroalunos.model.Aluno;


import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO {
    public static long salvar(Aluno aluno){
        try{
            return aluno.save();
        }catch (Exception ex){
            Log.e("Erro", "Erro ao salvar o aluno: "+ex.getMessage());
            return -1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<Aluno> getAluno(int id){
        try{
            Aluno aluno = Aluno.findById(Aluno.class,id);
            Optional<Aluno> opt = Optional.ofNullable(aluno);
            return opt;
        }catch (Exception ex){
            Log.e("Erro", "Erro ao salvar o aluno: "+ex.getMessage());
            return null;
        }
    }

    public static List<Aluno> retornaAlunos(String where, String[] whereArgs, String orderBy){
        List<Aluno> list = new ArrayList<>();
        try {
            list = Aluno.find(Aluno.class,where,whereArgs,"",orderBy,"");
        }catch (Exception ex){
            Log.e("Erro", "Erro ao buscar os alunos: "+ex.getMessage());
        }
        return list;
    }

    public static boolean delete(Aluno aluno){
        try {
            aluno.delete();
            return true;
        }catch (Exception ex){
            Log.e("Erro","Erro ao excluir o aluno: "+ex.getMessage());
            return false;
        }
    }
}
