import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

// Interfaz del Workflow que implementa Servicio B
@WorkflowInterface
public interface MyWorkflow {
    @WorkflowMethod
    void startWorkflow();

    @SignalMethod
    void receiveSignal(String signalData);
}
