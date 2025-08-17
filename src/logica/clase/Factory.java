package logica.clase;

import logica.clase.IEstacionTrabajo;
import logica.clase.EstacionTrabajo;

public class Factory {
    public IEstacionTrabajo getEstacionTrabajo(){
        return EstacionTrabajo.Instance;
    }
}
