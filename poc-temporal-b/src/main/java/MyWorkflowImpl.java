import io.temporal.workflow.Workflow;

// Implementación del Workflow en Servicio B
public class MyWorkflowImpl implements MyWorkflow {

    private String signalData;

    @Override
    public void startWorkflow() {
        // El Workflow espera la señal antes de continuar
        Workflow.await(() -> signalData != null);

        // Una vez recibida la señal, el Workflow puede continuar y completarse
        System.out.println("Workflow is completing after receiving signal: " + signalData);
    }

    @Override
    public void receiveSignal(String signalData) {
        this.signalData = signalData;
        // Manejar la señal recibida
        System.out.println("Received signal: " + signalData);
    }
}
