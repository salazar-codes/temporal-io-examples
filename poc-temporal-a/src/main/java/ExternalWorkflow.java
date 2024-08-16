import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

// Interfaz del Workflow que será utilizado por Servicio B
@WorkflowInterface
public interface ExternalWorkflow {
    @WorkflowMethod
    void startWorkflow();

    @SignalMethod
    void receiveSignal(String signalData);
}
