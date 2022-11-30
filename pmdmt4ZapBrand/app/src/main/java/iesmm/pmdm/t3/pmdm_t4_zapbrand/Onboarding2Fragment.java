package iesmm.pmdm.t3.pmdm_t4_zapbrand;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Onboarding2Fragment extends Fragment {

    Button botonSiguiente;
    Button botonSkip;
    //1. Recuperamos el NavController con findNavController()
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        botonSiguiente = view.findViewById(R.id.botonSiguiente2);

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navegar al onboarding home

                //2. Llamamos al metodo navigate para que navegue al 3 Fragment cuando
                //hagamos click

                navController.navigate(R.id.action_onboarding2Fragment_to_onboarding3Fragment);
            }
        });

        botonSkip = view.findViewById(R.id.botonSkip2);
        botonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navegar al homeOnboarding
                //llamamos al metodo navigate para que navegue al home cuando hagamos click

                navController.navigate(R.id.action_onboarding2Fragment_to_homeFragment);
            }
        });
    }
}