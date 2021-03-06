package com.example.cadastroalunos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cadastroalunos.dao.AlunoDAO;
import com.example.cadastroalunos.model.Aluno;
import com.example.cadastroalunos.util.CpfMask;
import com.example.cadastroalunos.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import fr.ganfra.materialspinner.MaterialSpinner;

import java.util.Calendar;

public class CadastroAlunoActivity extends AppCompatActivity {

    private TextInputEditText edRaAluno;
    private TextInputEditText edNomeAluno;
    private TextInputEditText edCPFAluno;
    private TextInputEditText edDtNascimentoAluno;
    private TextInputEditText edDtInscricaoAluno;
    private MaterialSpinner   spCursos;
    private MaterialSpinner   spPeriodos;
    private LinearLayout      llPrincipal;

    private int vAno;
    private int vMes;
    private int vDia;
    private View dataSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        edRaAluno           = findViewById(R.id.edRaAluno);
        edNomeAluno         = findViewById(R.id.edNomeAluno);
        edCPFAluno          = findViewById(R.id.edCPFAluno);
        edDtNascimentoAluno = findViewById(R.id.edDtNascimentoAluno);
        edDtInscricaoAluno  = findViewById(R.id.edDtInscricaoAluno);

        llPrincipal         = findViewById(R.id.llPrincipal);

        edDtInscricaoAluno.setFocusable(false);
        edDtNascimentoAluno.setFocusable(false);

        edCPFAluno.addTextChangedListener(CpfMask.insert(edCPFAluno));
        iniciaSpinner();

        setDataAtual();
    }

    private void setDataAtual() {
        Calendar calendar = Calendar.getInstance();
        vDia = calendar.get(Calendar.DAY_OF_MONTH);
        vMes = calendar.get(Calendar.MONTH);
        vAno = calendar.get(Calendar.YEAR);
    }

    private void iniciaSpinner(){
        spCursos            = findViewById(R.id.spCursos);
        spPeriodos          = findViewById(R.id.spPeriodos);

        spPeriodos.setVisibility(View.GONE);

        String cursos[] = new String[]{"An??lise e Desenv. Sistemas",
                                       "Administra????o",
                                       "Ci??ncias Cont??beis",
                                       "Direito",
                                       "Farm??cia",
                                       "Nutri????o"};

        String periodos[] = new String[]{"1?? S??rie",
                                         "2?? S??rie",
                                         "3?? S??rie",
                                         "4?? S??rie"};

        ArrayAdapter adapterCursos   = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cursos);
        ArrayAdapter adapterPeriodos = new ArrayAdapter(this, android.R.layout.simple_list_item_1,periodos);
        spCursos.setAdapter(adapterCursos);
        spPeriodos.setAdapter(adapterPeriodos);

        //A????o ao selecionar o item da lista
        spCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Button btADS = new Button(getBaseContext());
                if(i == 0){
                    spPeriodos.setVisibility(View.VISIBLE);
                    btADS.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    btADS.setText("Bot??o ADS");
                    btADS.setBackgroundColor(getColor(R.color.teal_200));
                    llPrincipal.addView(btADS);
                }else{
                    spPeriodos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void validaCampos(){
        if(edRaAluno.getText().toString().isEmpty()){
            edRaAluno.setError("Informe o RA do Aluno!");
            edRaAluno.requestFocus();
            return;
        }
        if(edCPFAluno.getText().toString().isEmpty()){
            edCPFAluno.setError("Informe o CPF do Aluno! ");
            edCPFAluno.requestFocus();
            return;
        }
        if(edNomeAluno.getText().toString().isEmpty()){
            edNomeAluno.setError("Informe o Nome do Aluno! ");
            edNomeAluno.requestFocus();
            return;
        }
        if(edDtNascimentoAluno.getText().toString().isEmpty()){
            edDtNascimentoAluno.setError("Informe a data de nascimento do Aluno! ");
            edDtNascimentoAluno.requestFocus();
            return;
        }
        if(edDtInscricaoAluno.getText().toString().isEmpty()){
            edDtInscricaoAluno.setError("Informe a data de inscri????o do Aluno! ");
            edDtInscricaoAluno.requestFocus();
            return;
        }
        salvarAluno();
    }

    public void salvarAluno(){
        Aluno aluno = new Aluno();
        aluno.setRa(Integer.parseInt(edRaAluno.getText().toString()));
        aluno.setNome(edNomeAluno.getText().toString());
        aluno.setCpf(edCPFAluno.getText().toString());
        aluno.setDtMatricula(edDtInscricaoAluno.getText().toString());
        aluno.setDtNascimento(edDtNascimentoAluno.getText().toString());
        aluno.setCurso(spCursos.getSelectedItem().toString());
        aluno.setPeriodo(spPeriodos.getSelectedItem().toString());

        if (AlunoDAO.salvar(aluno) > 0) {
            finishActivity(RESULT_OK);
        }
        else
            Util.customSnackBar(llPrincipal,"Erro ao salvar o aluno ("+aluno.getNome()+").",0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.menu_cadastro,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_limpar:
                limparCampos();
                return true;
            case R.id.mn_salvar:
                validaCampos();
                return true;
            case R.id.mn_listar:
                finish();
                //listarAlunos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void listarAlunos() {
        Intent intent = new Intent(this, ListaAlunoActivity.class);
        startActivityForResult(intent,1);
    }

    private void limparCampos() {
        edRaAluno.setText("");
        edCPFAluno.setText("");
        edNomeAluno.setText("");
        edDtInscricaoAluno.setText("");
        edDtNascimentoAluno.setText("");
    }

    public void selecionarData(View view) {
        dataSelecionada = view;
        showDialog(0);
    }

    private DatePickerDialog.OnDateSetListener setDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            vAno = year;
            vMes = month;
            vDia = day;

            atualizaData();
        }

    };

    private void atualizaData() {
        TextInputEditText edit = (TextInputEditText)dataSelecionada;
        edit.setText(new StringBuilder().append(vDia).append("/").append(vMes + 1).append("/").append(vAno));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        setDataAtual();
        return new DatePickerDialog(this,setDatePicker,vAno,vMes,vDia);
    }
}