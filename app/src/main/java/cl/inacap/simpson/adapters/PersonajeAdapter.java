package cl.inacap.simpson.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.simpson.R;
import cl.inacap.simpson.dto.Personaje;

public class PersonajeAdapter extends ArrayAdapter<Personaje> {
    private List<Personaje> personajes;
    private Activity activity;

    public PersonajeAdapter(@NonNull Activity context, int resource, @NonNull List<Personaje> objects) {
        super(context, resource, objects);
        this.personajes = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View fila = inflater.inflate(R.layout.list_personajes, null, true);
        TextView nombrePersonajeTv = fila.findViewById(R.id.nombrePersonajeTv);
        TextView frasePersonajeTv = fila.findViewById(R.id.frasePersonajeTv);
        ImageView imagenPersonajeIv = fila.findViewById(R.id.imagenPersonajeIv);
        nombrePersonajeTv.setText(personajes.get(position).getCharacter());
        frasePersonajeTv.setText(personajes.get(position).getQuote());
        Picasso.get().load(this.personajes.get(position).getImage()).resize(300, 500).centerCrop().into(imagenPersonajeIv);
        return fila;
    }
}
