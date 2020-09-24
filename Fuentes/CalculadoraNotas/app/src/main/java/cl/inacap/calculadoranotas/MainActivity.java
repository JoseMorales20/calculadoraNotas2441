package cl.inacap.calculadoranotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.calculadoranotas.dto.Nota;

public class MainActivity extends AppCompatActivity {

    private int porcentajeActual=0;
    private EditText notaTxt;
    private EditText porcentajetxt;
    private Button agregarBtn;
    private Button limpiarBtn;
    private ListView notaLv;
    private List<Nota> notas = new ArrayList<>();
    private ArrayAdapter<Nota> adapter;
    private LinearLayout promedioLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.promedioTxt = findViewById(R.id.promedioTxt);
        this.promedioLl = findViewById(R.id.promedioLl);
        this.notaTxt= findViewById(R.id.notaTxt);
        this.porcentajetxt= findViewById(R.id.porcentajeTxt);
        this.agregarBtn= findViewById(R.id.agregarBtn);
        this.limpiarBtn= findViewById(R.id.limpiarBtn);
        this.notaLv= findViewById(R.id.notaLv);
        this.adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        this.notaLv.setAdapter(adapter);
        this.limpiarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limpiar los editText
                notaTxt.setText("");
                porcentajetxt.setText("");
                //ocultar el linear layout
                promedioLl.setVisibility(View.INVISIBLE);
                //volver el % a 0
                porcentajeActual=0;
                // limpiar la lista
                notas.clear();
                adapter.notifyDataSetChanged();
            }
        });
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<String> errores = new ArrayList<>();
                String notasStr = notaTxt.getText().toString().trim();
                String porcStr = porcentajetxt.getText().toString().trim();
                int porcentaje =0;
                double nota=0;
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

                if(errores.isEmpty()) {
                    if (porcentaje + porcentajeActual > 100) {
                        //se excede el valor permitido
                        Toast.makeText(MainActivity.this, "el porcentaje no puede ser mayor que 100"
                                , Toast.LENGTH_SHORT), show();
                    }else{
                        //agregar la nota
                     Nota n = new Nota();
                     n.setValor(nota);
                     n.setPorcentaje(porcentaje);
                     notas.add(n);
                     porcentajeActual += porcentaje;
                     // cada vez que modifique el recurso se debe usar este adaptador
                     adapter.notifyDataSetChanged();
                     mostrarPromedio();
                    }



                }else{
                    mostrarErrores(errores);
                }

            }


        });

        private void mostrarPromedio(){
            double promedio =0;
            for(Nota n: notas){
              promedio+= = (n.getValor()* n.getPorcentaje()/100);
            }
            this.promedioTxt.setTtext(String.format("%.1f",promedio));
            if (promedio < 4.0){
                this.promedioTxt.setTextColor(ContextCompat.getColor(this, R.color.error));

            }else{
                this.promedioTxt.SetTextColor(ContextCompat.getColor(this,R.color.exito));

            }
            this.promedioLl.setVisibility(View.VISIBLE);
        }

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