package cl.inacap.simpson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.simpson.adapters.PersonajeAdapter;
import cl.inacap.simpson.dto.Personaje;

public class MainActivity extends AppCompatActivity {
    private Spinner cantidadConsejosSp;
    private Button solicitarConsejoBtn;
    private ListView listaPersonajesLv;
    private List<Personaje> personajes = new ArrayList<>();
    private PersonajeAdapter adaptador;
    private RequestQueue queue;
    private String cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        listaPersonajesLv = findViewById(R.id.listaPersonajesLv);
        cantidadConsejosSp = (Spinner)findViewById(R.id.cantidadConsejosSp);
        solicitarConsejoBtn = (Button)findViewById(R.id.solicitarConsejoBtn);
        String[] CantidadConsejos = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> consejosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CantidadConsejos);
        cantidadConsejosSp.setAdapter(consejosAdapter);
        solicitarConsejoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad = cantidadConsejosSp.getSelectedItem().toString();
                getPersonajes();
            }
        });
    }

    private void getPersonajes() {
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest (Request.Method.GET, "https://thesimpsonsquoteapi.glitch.me/quotes?count=" + cantidad, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    personajes.clear();
                    for (int i = 0; i < Integer.parseInt(cantidad); i++) {
                        Personaje p = new Gson().fromJson(response.getString(i), Personaje.class);
                        personajes.add(p);
                    }
                } catch (Exception ex) {
                    personajes.clear();
                    Log.e("MAIN_ACTIVITY", "Error de peticion");
                } finally {
                    adaptador.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                personajes.clear();
                Log.e("MAIN_ACTIVITY", "Error de respuesta");
                adaptador.notifyDataSetChanged();
            }
        });
        queue.add(jsonRequest);
        adaptador = new PersonajeAdapter(this, R.layout.list_personajes, personajes);
        listaPersonajesLv.setAdapter(adaptador);
    }
}