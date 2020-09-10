package cl.inacap.calculadoranotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.calculadoranotas.dto.Nota;

public class MainActivity extends AppCompatActivity {

    private EditText notaTxt;
    private EditText porcentajetxt;
    private Button agregarBtn;
    private Button limpiarBtn;
    private ListView notaLv;
    private List<Nota> notas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.notaTxt= findViewById(R.id.notaTxt);
        this.porcentajetxt= findViewById(R.id.porcentajeTxt);
        this.agregarBtn= findViewById(R.id.agregarBtn);
        this.limpiarBtn= findViewById(R.id.limpiarBtn);
        this.notaLv= findViewById(R.id.notaLv);

        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();
                String notasStr = notaTxt.getText().toString().trim();
                String porcStr = porcentajetxt.getText().toString().trim();
                int porcentaje;
                double nota;
                try{
                    nota = Double.parseDouble(notasStr);
                    if(nota < 1.0 || nota > 7.0){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException Ex){
                    errores.add("la nota debe ser entre 1.0 y 7.0 ");
                }

                try{
                    porcentaje = Integer.parseInt(porcStr);
                    if(porcentaje < 1 || porcentaje > 100){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException Ex){
                    errores.add("el porcentaje debe ser entre 1 y 100");
                }

                if(errores.isEmpty()){

                }else{
                    mostrarErrores(errores);
                }

            }


        });

        private void mostrarErrores(List<String> errores){
            String mensaje = "";
            for(String e: errores){
                mensaje+= "-" + e + "\n";
            }
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validacion")
                    .setMessage(mensaje)
                    .setPositiveButton( "Aceptar", null)
                    .create()
                    .show();
    }

}