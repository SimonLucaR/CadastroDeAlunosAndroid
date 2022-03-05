package com.example.cadastroalunos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cadastroalunos.util.CpfMask;
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

        String cursos[] = new String[]{"Análise e Desenv. Sistemas",
                                       "Administração",
                                       "Ciências Contábeis",
                                       "Direito",
                                       "Farmácia",
                                       "Nutrição"};

        String periodos[] = new String[]{"1ª Série",
                                         "2ª Série",
                                         "3ª Série",
                                         "4ª Série"};

        ArrayAdapter adapterCursos   = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cursos);
        ArrayAdapter adapterPeriodos = new ArrayAdapter(this, android.R.layout.simple_list_item_1,periodos);
        spCursos.setAdapter(adapterCursos);
        spPeriodos.setAdapter(adapterPeriodos);

        //Ação ao selecionar o item da lista
        spCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Button btADS = new Button(getBaseContext());
                if(i == 0){
                    spPeriodos.setVisibility(View.VISIBLE);
                    btADS.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    btADS.setText("Botão ADS");
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
            edDtInscricaoAluno.setError("Informe a data de inscrição do Aluno! ");
            edDtInscricaoAluno.requestFocus();
            return;
        }

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
            default:
                return super.onOptionsItemSelected(item);
        }
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